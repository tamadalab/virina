import java.lang.StringBuilder;
public class Test2
{ // 実際に書いたもの
    public static void main(String... args)
    {
        String normalString = args[0];
        String reverseString = new StringBuilder(args[0]).reverse().toString();
        System.out.println("入力した文字: "+normalString);
        System.out.println("逆順出力: "+reverseString);
    }
}