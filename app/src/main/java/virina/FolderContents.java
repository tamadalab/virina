import java.io.File;

public class FolderContents{
    public static void main(String[] args) {
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
                System.out.println(file.getName());
            }
        } else {
            System.out.println("フォルダは空です。");
        }
    }
}
