package virina;

import java.io.File;

public class FolderContents{
    private static final String API_URL = "https://api.openai.com/v1/engines/gpt-4.0/completions";
    private static final String API_KEY = "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";
    public static void main(String... args) {
        if (args.length == 0) {
            System.out.println("フォルダを指定してください。");
            return;
        }

        String folderPath = args[0];
        File folder = new File(folderPath);

        if (!folder.exists()) {
            System.out.println("指定されたフォルダが存在しません。");
            return;
        }

        if (!folder.isDirectory()) {
            System.out.println("指定されたパスはフォルダではありません。");
            return;
        }

        File[] files = folder.listFiles();

        if (files != null && files.length > 0) {
            System.out.println("フォルダの中身:");
            for (File file : files) {
                api(file);
                // System.out.println(file.getName());
            }
        } else {
            System.out.println("フォルダは空です。");
        }
    }
    public static void api(File file)
    {
        System.out.println(file.getName());
        
    }
}
