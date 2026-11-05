public class Driver {
    public static void main(String[] args) {
        // (e) equivalent fractions
        Fraction a = new Fraction(1, 2);
        Fraction b = new Fraction(2, 4);
        Fraction c = new Fraction(3, 6);
        Fraction d = new Fraction(1, 3);

        System.out.println("new Fraction(1,2) prints " + a);
        System.out.println("new Fraction(2,4) prints " + b);
        System.out.println("new Fraction(3,6) prints " + c);
        System.out.println("new Fraction(-4,-8) prints " + new Fraction(-4, -8));
        System.out.println();
        System.out.println("1/2 equals 2/4 : " + a.equals(b));
        System.out.println("2/4 equals 3/6 : " + b.equals(c));
        System.out.println("1/2 equals 1/3 : " + a.equals(d));
        System.out.println("Same hashCode (1/2, 3/6): " + (a.hashCode() == c.hashCode()));
    }
}
