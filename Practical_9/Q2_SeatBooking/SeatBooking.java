import java.util.concurrent.atomic.AtomicInteger;

public class SeatBooking {

    static class UnsafeTheatre {
        int seatsLeft = 5;
        // check-then-act without a lock: several threads can pass the check together
        boolean book() {
            if (seatsLeft > 0) {
                pause();                    // simulates payment/processing time
                seatsLeft--;
                return true;
            }
            return false;
        }
    }

    static class SafeTheatre {
        int seatsLeft = 5;
        synchronized boolean book() {       // check and update happen atomically
            if (seatsLeft > 0) {
                pause();
                seatsLeft--;
                return true;
            }
            return false;
        }
    }

    static void pause() {
        try { Thread.sleep(10); } catch (InterruptedException ignored) { }
    }

    interface Bookable { boolean book(); }

    static int simulate(Bookable theatre, int customers) throws InterruptedException {
        AtomicInteger success = new AtomicInteger();
        Thread[] ts = new Thread[customers];
        for (int i = 0; i < customers; i++) {
            ts[i] = new Thread(() -> { if (theatre.book()) success.incrementAndGet(); });
            ts[i].start();
        }
        for (Thread t : ts) t.join();
        return success.get();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Seats available: 5, customers (threads): 10\n");

        UnsafeTheatre u = new UnsafeTheatre();
        int okUnsafe = simulate(u::book, 10);
        System.out.println("WITHOUT synchronization -> successful bookings: " + okUnsafe
                + (okUnsafe > 5 ? "  (OVERSOLD by " + (okUnsafe - 5) + ")" : ""));
        System.out.println("                           seatsLeft = " + u.seatsLeft);

        SafeTheatre s = new SafeTheatre();
        int okSafe = simulate(s::book, 10);
        System.out.println("WITH synchronized book() -> successful bookings: " + okSafe
                + (okSafe == 5 ? "  (exactly 5)" : ""));
        System.out.println("                           seatsLeft = " + s.seatsLeft);
    }
}
