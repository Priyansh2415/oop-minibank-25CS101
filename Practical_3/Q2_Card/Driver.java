public class Driver {
    public static void main(String[] args) {
        Card[] incoming = {
            new Card("Queen", "Hearts"), new Card("Ace", "Spades"),
            new Card("10", "Diamonds"),  new Card("Ace", "Spades"),
            new Card("King", "Clubs")
        };
        Card[] hand = new Card[incoming.length];
        int count = 0;

        // (d) check each card against the earlier ones before adding it
        for (Card c : incoming) {
            boolean duplicate = false;
            for (int i = 0; i < count; i++) {
                if (hand[i].equals(c)) {
                    duplicate = true;
                    break;
                }
            }
            if (duplicate) {
                // (e) report first repeat and stop
                System.out.println("Duplicate found: " + c);
                break;
            }
            hand[count++] = c;
            System.out.println("Added: " + c);
        }
    }
}
