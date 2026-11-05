import java.util.Objects;

public class Card {

    // (a) fields and constructor
    private String rank, suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    // (b) toString -> "Queen of Hearts"
    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    // (c) equals/hashCode on rank + suit
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card c = (Card) o;
        return rank.equals(c.rank) && suit.equals(c.suit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
}
