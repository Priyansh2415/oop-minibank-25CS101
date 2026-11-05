public class ParkingLot {

    // (a) fields
    private int twoWheelers;
    private int fourWheelers;
    private final int twoCap;
    private final int fourCap;
    private static long revenue = 0;

    public ParkingLot(int twoCap, int fourCap) {
        this.twoCap = twoCap;
        this.fourCap = fourCap;
    }

    // (b) park
    public void park(String type) {
        if (type.equals("two")) {
            if (twoWheelers < twoCap) {
                twoWheelers++;
                revenue += 20;
                System.out.println("Parked two-wheeler  (+20)");
            } else {
                System.out.println("Full - two-wheeler rejected");
            }
        } else if (type.equals("four")) {
            if (fourWheelers < fourCap) {
                fourWheelers++;
                revenue += 40;
                System.out.println("Parked four-wheeler (+40)");
            } else {
                System.out.println("Full - four-wheeler rejected");
            }
        } else {
            System.out.println("Unknown vehicle type: " + type);
        }
    }

    // (c) leave - never below 0
    public void leave(String type) {
        if (type.equals("two")) {
            if (twoWheelers > 0) { twoWheelers--; System.out.println("Two-wheeler left"); }
            else System.out.println("No two-wheeler to remove");
        } else if (type.equals("four")) {
            if (fourWheelers > 0) { fourWheelers--; System.out.println("Four-wheeler left"); }
            else System.out.println("No four-wheeler to remove");
        }
    }

    public void printStatus() {
        System.out.println("Occupancy -> two: " + twoWheelers + "/" + twoCap
                + ", four: " + fourWheelers + "/" + fourCap);
    }

    // (d) main
    public static void main(String[] args) {
        ParkingLot lot = new ParkingLot(2, 2);
        String[] events = {
            "park two", "park two", "park two",
            "park four", "park four", "park four",
            "leave two", "park two",
            "leave four", "leave four", "leave four"
        };
        for (String e : events) {
            String[] p = e.split(" ");
            System.out.print(String.format("%-11s : ", e));
            if (p[0].equals("park")) lot.park(p[1]); else lot.leave(p[1]);
        }
        System.out.println("\n--- Final report ---");
        lot.printStatus();
        System.out.println("Revenue: Rs " + revenue);
    }
}
