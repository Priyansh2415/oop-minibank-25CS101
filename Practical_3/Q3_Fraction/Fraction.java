import java.util.Objects;

public class Fraction {

    // (a) fields
    private int num, den;

    // (b) constructor reduces to lowest terms
    public Fraction(int num, int den) {
        if (den == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero");
        }
        if (den < 0) {          // keep the sign on the numerator
            num = -num;
            den = -den;
        }
        int g = gcd(Math.abs(num), den);
        this.num = num / g;
        this.den = den / g;
    }

    private static int gcd(int a, int b) {
        return b == 0 ? (a == 0 ? 1 : a) : gcd(b, a % b);
    }

    // (c) toString
    @Override
    public String toString() {
        return num + "/" + den;
    }

    // (d) equals/hashCode on the reduced values
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fraction)) return false;
        Fraction f = (Fraction) o;
        return num == f.num && den == f.den;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, den);
    }
}
