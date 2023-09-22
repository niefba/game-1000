import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Player1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            List<String> dices = Arrays.asList(input.split(" "));
            int count1 = Collections.frequency(dices, "1");
            int count5 = Collections.frequency(dices, "5");
            if (count1 > 0 || count5 > 0) {
                System.out.println("pass");
            } else {
                System.out.println(dices.get(0));
            }


        }
    }
}
