package virina;

import java.io.BufferedReader;
import java.io.File;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.HttpClients;
import java.io.FileReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
//import java.nio.file.Path;


public class FolderContents
{
    class Api{
        // 使用するapiのエンジンの記載
        private final String API_URL = "https://api.openai.com/v1/engines/gpt-3.5/completions";
        // api keyの記載
        private final String API_KEY = "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";

    }
    public void main(String... args)
    {
        if (args.length == 0) {
            System.out.println("フォルダを指定してください。");
//            return;
        } else {
            this.fileChecker(args[0]);
        }
    }
    public void fileChecker(String folders)
    {
        String folderPath = folders;
        File folder = new File(folderPath);

        if (!folder.exists()) {
            System.out.println("指定されたフォルダが存在しません。");
            return;
        }

        if (!folder.isDirectory()) {
            System.out.println("指定されたパスはフォルダではありません。");
            return;
        }

//        File[] files = folder.listFiles();
        this.fileFinder(folder);

//        if (files != null && files.length > 0) {
//            System.out.println("フォルダの中身:");
//            for (File file : files) {
//                if(file.exists()) {
//                    this.api(file);
//                    // System.out.println(file.getName());
//                }
//            }
//        }
//        else {
//            System.out.println("フォルダは空です。");
//        }
    }
    public void fileFinder(File folder)
    {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    fileFinder(file); // 再帰的にフォルダの中身を表示
                } else {
                    this.api(file);
//                    System.out.println(file.getName());
                }
            }
        }
    }


    public void api(File file) {
        try {
            Api api = new Api();
            String apiUrl = api.API_URL;
            String apiKey = api.API_KEY;

            // ファイルからソースコードを読み取る
            String sourceCodeFilePath = file.toString();
            StringBuilder sourceCodeContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(sourceCodeFilePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sourceCodeContent.append(line).append("\n");
                }
            }

            // 質問内容
            String question = "You will be asked to determine if the source code you are about to present was written by you.If it is, answer \"yes\"; if not, answer \"no\".";

            // ソースコードと質問をパッケージ化
            String payload = "{\"question\": \"" + question + "\", \\\"source_code\\\": \\\"\" + sourceCodeContent.toString() + \"\\\"}";

            // httpクライアントの初期化
            HttpClient httpClient = HttpClient.newHttpClient();

            // POSTリクエストを作成し、パッケージ化したデータを送信
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();

            // APIにリクエストを送信
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
