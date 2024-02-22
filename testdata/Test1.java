public class Test1 { // AIが書いたもの
    public static void main(String[] args) {
        // 引数が提供されているかどうかを確認
        if (args.length < 1) {
            System.out.println("エラー: 文字列引数を入力してください");
            System.exit(1);  // エラーコード 1 でプログラムを終了
        }

        // 引数で渡された最初の文字列を取得
        String inputString = args[0];

        // 入力文字列を出力
        System.out.println("入力文字列: " + inputString);

        // 文字列を逆順にする
        String reversedString = new StringBuilder(inputString).reverse().toString();

        // 逆順の文字列を出力
        System.out.println("逆順文字列: " + reversedString);
    }
}

