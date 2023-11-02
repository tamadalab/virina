package virina;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class Api{
    // 使用するapiのエンジンの記載
    public static final String API_URL = "https://api.openai.com/v1/chat/completions";
    // YOUR_API_KEYの記載
    public static final String API_KEY = "YOUR_API_KEY";

    public static final String API_MODEL = "gpt-3.5-turbo";
}

public class FolderContents
{
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
                    if(!file.getName().equals(".DS_Store")){
                        api(file);
                    }
                }
            }
        }
    }


    public static void api(File file) {
        try {
            String apiUrl = Api.API_URL;
            String apiKey = Api.API_KEY;
            String apimodel = Api.API_MODEL;

            // ファイルからソースコードを読み取る
            String sourceCodeFilePath = file.toString();
            StringBuilder sourceCodeContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(sourceCodeFilePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sourceCodeContent.append(line).append("\\n");
                }
            }
            String newSourceCodeContent = sourceCodeContent.toString().replace("\"", "\\\"");

            // 質問内容
            String question = "You will be asked to determine if the source code you are about to present was written by you. If it is, answer yes, if not, answer no. Also, please judge mainly on the basis of the name of the identifier and indentation. Please indicate how reliable your judgment is on a scale from 0 to 100.";

            // ソースコードと質問をパッケージ化
            String payload = "{" +
                    "\"model\": \"" + apimodel +"\", " +
                    "\"temperature\": 0.1,"+
                    "\"messages\": [{\"role\": \"user\", " +
                    "\"content\": \""+ question + newSourceCodeContent+"\"" +
                    "}]" +
                    "}";

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
            System.out.println(file.getName());
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//            System.out.println("Result: " + response.body());
            if (response.statusCode() != 200 && response.statusCode() != 401 ) {
                // Error 401以外のエラーコードの場合
                System.out.println("Error: " + response.body());
            }
            else{
                System.out.println("Result: " + response.body());
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
