public class BoundedStats {

    // N must be a Number (Integer, Double, Long ...) so doubleValue() is always available
    static <N extends Number> double average(N[] values) {
        double sum = 0;
        for (N v : values) sum += v.doubleValue();
        return values.length == 0 ? 0 : sum / values.length;
    }

    public static void main(String[] args) {
        Integer[] ints   = { 10, 20, 30, 40 };
        Double[]  doubles = { 1.5, 2.5, 3.5 };
        Long[]    longs  = { 100L, 200L, 300L };

        System.out.println("average(Integer[]) = " + average(ints));
        System.out.println("average(Double[])  = " + average(doubles));
        System.out.println("average(Long[])    = " + average(longs));

        // String[] words = { "a", "b" };
        // average(words);   // compile-time ERROR: String does not extend Number
        System.out.println("\nNote: average(String[]) is rejected at compile time because the bound");
        System.out.println("<N extends Number> only allows Number subclasses.");
    }
}
