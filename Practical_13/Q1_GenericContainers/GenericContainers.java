// generic container for one value of any type
class Box<T> {
    private T value;
    Box(T value) { this.value = value; }
    T get() { return value; }
    void set(T value) { this.value = value; }
}

// generic pair of two (possibly different) types
class Pair<A, B> {
    private final A first;
    private final B second;
    Pair(A first, B second) { this.first = first; this.second = second; }
    A getFirst()  { return first; }
    B getSecond() { return second; }
    @Override public String toString() { return "(" + first + ", " + second + ")"; }
}

public class GenericContainers {

    // generic method: first element of any array
    static <T> T first(T[] array) {
        return array[0];
    }

    public static void main(String[] args) {
        Box<Integer> intBox = new Box<>(42);
        Box<String> strBox = new Box<>("Hello");
        int n = intBox.get();               // no cast needed
        String s = strBox.get();            // no cast needed
        System.out.println("Box<Integer> holds " + n);
        System.out.println("Box<String>  holds " + s);
        intBox.set(100);
        System.out.println("Box<Integer> after set(100): " + intBox.get());
        // intBox.set("text");              // compile-time ERROR - type safety

        Pair<String, Integer> p1 = new Pair<>("Riya", 21);
        Pair<Double, Boolean> p2 = new Pair<>(9.5, true);
        System.out.println("\nPair<String,Integer>  : " + p1 + "  -> " + p1.getFirst().toUpperCase() + ", " + (p1.getSecond() + 1));
        System.out.println("Pair<Double,Boolean>  : " + p2);

        Integer[] nums = { 7, 8, 9 };
        String[] names = { "Aman", "Neha" };
        System.out.println("\nfirst(Integer[]) = " + first(nums));
        System.out.println("first(String[])  = " + first(names));
    }
}
