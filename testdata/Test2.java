import java.util.Random;

public class Test2
{
    public static void main(String... args)
    {
        Random randnum = new Random();
        Integer createNum = rand.nextInt(101);

        if(createNum % 2 == 0)
        {
            System.out.println(createNum+"は偶数です。");
        }
        else
        {
            System.out.println(createNum+"は奇数です。");
        }
    }
}