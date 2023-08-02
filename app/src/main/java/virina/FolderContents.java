package virina;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class Api{
    public static final String API_URL = "https://api.openai.com/v1/engines/gpt-3.5-turbo/completions";
    public static final String API_KEY = "YOUR_API_KEY";
    // 使用するapiのエンジンの記載
//    private static final String API_URL = "https://api.openai.com/v1/engines/gpt-3.5-turbo/completions";
    // api keyの記載
//    private static final String API_KEY = "YOUR_API_KEY";

}

public class FolderContents
{
//    class Api{
//        // 使用するapiのエンジンの記載
//        private final String API_URL = "https://api.openai.com/v1/engines/gpt-3.5-turbo/completions";
//        // api keyの記載
//        private final String API_KEY = "YOUR_API_KEY";
//
//    }
    public static void main(String... args)
    {
        if (args.length == 0) {
            System.out.println("フォルダを指定してください。");
//            return;
        } else {
            fileChecker(args[0]);
        }
    }
    public static void fileChecker(String folders)
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

        fileFinder(folder);

    }
    public static void fileFinder(File folder)
    {
        File[] files = folder.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                if (file.isDirectory())
                {
                    fileFinder(file); // 再帰的にフォルダの中身を表示
                }
                else
                {
                    api(file);
                }
            }
        }
    }


    public static void api(File file) {
        try {
//            Api api = new Api();
            String apiUrl = Api.API_URL;
            String apiKey = Api.API_KEY;

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
            String payload = "{\"question\": \"" + question + "\", \"source_code\": \"" + sourceCodeContent.toString().replace("\"", "\\\"") + "\"}";
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
