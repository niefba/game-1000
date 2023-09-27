import java.util.Scanner;

public class PlayerSimple {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while (true) {
            int diceCount = in.nextInt();
            int diceValue = 0;
            for (int i = 0; i < diceCount; i++) {
                diceValue = in.nextInt();
                System.err.println(String.format("%d", diceValue));
            }
            System.out.println("pass");


        }
    }
}
