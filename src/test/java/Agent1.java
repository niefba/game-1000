import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Agent1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            /*
            int diceValue = scanner.nextInt();
            System.err.println(diceValue);
            if (diceValue == 1 || diceValue == 5) {
                System.out.println("pass");
            } else {
                System.out.println(diceValue);
            }*/
            String input = scanner.nextLine();
            System.err.println("input:" + input);
            List<String> dices = Arrays.asList(input.split(" "));
            if (dices.contains("1") || dices.contains("5")) {
                System.out.println("pass");
            } else {
                System.out.println(dices.get(0));
            }


        }
    }
}
