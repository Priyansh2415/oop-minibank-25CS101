// Each media type calculates its own late fee (polymorphism)
abstract class Media {
    protected final String title;
    protected final int daysLate;

    Media(String title, int daysLate) {
        this.title = title;
        this.daysLate = daysLate;
    }
    abstract double lateFee();
    abstract String type();
}

class Book extends Media {              // Rs 2 per day
    Book(String t, int d) { super(t, d); }
    double lateFee() { return daysLate * 2.0; }
    String type() { return "Book"; }
}

class Dvd extends Media {               // Rs 5 per day, capped at Rs 100
    Dvd(String t, int d) { super(t, d); }
    double lateFee() { return Math.min(daysLate * 5.0, 100.0); }
    String type() { return "DVD"; }
}

class Magazine extends Media {          // first 3 days free, then Rs 3 per day
    Magazine(String t, int d) { super(t, d); }
    double lateFee() { return Math.max(0, daysLate - 3) * 3.0; }
    String type() { return "Magazine"; }
}

public class LibraryFees {
    public static void main(String[] args) {
        Media[] returned = {
            new Book("Clean Code", 6),
            new Dvd("Inception", 30),
            new Magazine("National Geographic", 2),
            new Magazine("Wired", 10),
            new Book("Effective Java", 0)
        };

        double total = 0;
        for (Media m : returned) {
            double fee = m.lateFee();
            total += fee;
            System.out.printf("%-9s %-22s %2d days late -> Rs %6.2f%n",
                    m.type(), m.title, m.daysLate, fee);
        }
        System.out.printf("%nTotal late fees for the batch: Rs %.2f%n", total);
    }
}
