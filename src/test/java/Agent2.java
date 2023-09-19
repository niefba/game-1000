import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Agent2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            /*
            int diceValue = scanner.nextInt();
            scanner.reset();
            if (diceValue == 1 || diceValue == 5) {
                System.out.println("pass");
            } else {
                System.out.println(diceValue);
            }
            */
            String input = scanner.nextLine();
            System.err.println("input:" + input);
            List<String> dices = Arrays.asList(input.split(" "));

            int count1 = Collections.frequency(dices, "1");
            int count2 = Collections.frequency(dices, "2");
            int count3 = Collections.frequency(dices, "3");
            int count4 = Collections.frequency(dices, "4");
            int count5 = Collections.frequency(dices, "5");
            int count6 = Collections.frequency(dices, "6");

            if (dices.size() <= 4 || count1 > 2 || count2 > 2 || count3 > 2 || count4 > 2 || count5 > 2 || count6 > 2){
                System.out.println("pass");
            }
            else if (count1 == 2) {
                System.out.println("1 1");
            }
            else if (count5 == 2) {
                System.out.println("5 5");
            }
            else if (count1 == 1) {
                System.out.println("1");
            } else if (count5 == 1) {
                System.out.println("5");
            }
            else {
                System.out.println("");
            }


        }
    }
}
