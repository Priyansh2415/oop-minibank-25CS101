import java.util.Scanner;

public class TollBooth {

    // (a) Vehicle record
    record Vehicle(String number, String type) { }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // (b) running total and counters
        int totalToll = 0;
        int bike = 0, car = 0, truck = 0;

        // (c) loop until the user types "done"
        while (true) {
            System.out.print("Vehicle number (or 'done'): ");
            String number = sc.nextLine().trim();
            if (number.equalsIgnoreCase("done")) {
                break;
            }
            System.out.print("Vehicle type (bike/car/truck): ");
            Vehicle v = new Vehicle(number, sc.nextLine().trim().toLowerCase());

            int toll = switch (v.type()) {
                case "bike"  -> 20;
                case "car"   -> 50;
                case "truck" -> 150;
                default      -> 0;
            };
            if (toll == 0) {
                System.out.println("Unknown vehicle type, ignored.");
                continue;
            }
            totalToll += toll;
            switch (v.type()) {
                case "bike"  -> bike++;
                case "car"   -> car++;
                case "truck" -> truck++;
            }
            System.out.println(v + " -> toll " + toll);
        }

        // (d) total toll and most frequent type
        String most = "bike";
        int max = bike;
        if (car > max)   { most = "car";   max = car; }
        if (truck > max) { most = "truck"; max = truck; }

        System.out.println("Total toll: " + totalToll);
        System.out.println("Most frequent: " + (max == 0 ? "none" : most));
    }
}
