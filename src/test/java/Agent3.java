import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Agent3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            List<String> dices = Arrays.asList(input.split(" "));

            int count1 = Collections.frequency(dices, "1");
            int count2 = Collections.frequency(dices, "2");
            int count3 = Collections.frequency(dices, "3");
            int count4 = Collections.frequency(dices, "4");
            int count5 = Collections.frequency(dices, "5");
            int count6 = Collections.frequency(dices, "6");

            if (count1 > 2 || count2 > 2 || count3 > 2 || count4 > 2 || count5 > 2 || count6 > 2){
                System.out.println("pass");
            }
            else {
                System.out.println("none");
            }


        }
    }
}
