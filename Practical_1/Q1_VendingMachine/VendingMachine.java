import java.util.Scanner;

public class VendingMachine {

    // (a) Coins accepted by the machine
    enum Coin { ONE, TWO, FIVE, TEN }

    public static void main(String[] args) {
        // (b) snack price, running total and Scanner
        final int price = 15;
        int total = 0;
        Scanner sc = new Scanner(System.in);

        // (d) stop once total >= price
        while (total < price) {
            System.out.print("Insert coin (ONE/TWO/FIVE/TEN): ");
            Coin coin;
            try {
                coin = Coin.valueOf(sc.next().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid coin, try again.");
                continue;
            }

            // (c) switch expression converts Coin -> value
            int value = switch (coin) {
                case ONE  -> 1;
                case TWO  -> 2;
                case FIVE -> 5;
                case TEN  -> 10;
            };
            total += value;
            System.out.println("Total so far: " + total);
        }

        // (e) change to return
        System.out.println("Paid. Change: " + (total - price));
    }
}
