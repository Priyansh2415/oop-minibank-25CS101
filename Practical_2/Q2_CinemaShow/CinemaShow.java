public class CinemaShow {

    // (a) fields
    private String title;
    private int seatsAvailable;
    private final int capacity;
    private static int totalBooked = 0;

    // (b) constructors
    public CinemaShow(String title, int capacity) {
        this.title = title;
        this.capacity = capacity;
        this.seatsAvailable = capacity;
    }

    public CinemaShow(String title) {
        this(title, 100);
    }

    // (c) book
    public boolean book(int n) {
        if (n <= seatsAvailable) {
            seatsAvailable -= n;
            totalBooked += n;
            return true;
        }
        return false;
    }

    // (d) cancel - never above capacity
    public void cancel(int n) {
        seatsAvailable = Math.min(capacity, seatsAvailable + n);
    }

    // (e) getters
    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public static int getTotalBooked() {
        return totalBooked;
    }

    // (f) main
    public static void main(String[] args) {
        CinemaShow show = new CinemaShow("Interstellar", 50);
        CinemaShow other = new CinemaShow("Dune");   // capacity 100 via this(title, 100)
        System.out.println("Show: " + show.title + " (capacity " + show.capacity + ")");
        System.out.println("Show: " + other.title + " (capacity " + other.capacity + ")\n");

        System.out.println("book(20)  -> " + show.book(20)  + " | seats left: " + show.getSeatsAvailable());
        System.out.println("book(25)  -> " + show.book(25)  + " | seats left: " + show.getSeatsAvailable());
        System.out.println("book(10)  -> " + show.book(10)  + " | seats left: " + show.getSeatsAvailable());
        show.cancel(15);
        System.out.println("cancel(15)           | seats left: " + show.getSeatsAvailable());
        System.out.println("book(10)  -> " + show.book(10)  + " | seats left: " + show.getSeatsAvailable());
        show.cancel(100);
        System.out.println("cancel(100)          | seats left: " + show.getSeatsAvailable() + " (capped at capacity)");
        System.out.println("Dune book(30) -> " + other.book(30) + " | seats left: " + other.getSeatsAvailable());

        System.out.println("\nTotal booked: " + CinemaShow.getTotalBooked());
    }
}
