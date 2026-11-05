import java.util.*;

public class WordFrequency {
    public static void main(String[] args) {
        String paragraph = "Java is simple. Java is object oriented. Java is secure and Java is portable. "
                + "Collections in Java make data handling simple and powerful.";

        // split into lowercase words (letters only)
        String[] words = paragraph.toLowerCase().split("[^a-z]+");

        // count each word in a HashMap
        Map<String, Integer> freq = new HashMap<>();
        for (String w : words) {
            if (w.isEmpty()) continue;
            freq.merge(w, 1, Integer::sum);
        }

        // sort entries: highest frequency first, ties alphabetically
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(freq.entrySet());
        entries.sort(Comparator
                .comparing((Map.Entry<String, Integer> e) -> e.getValue()).reversed()
                .thenComparing(Map.Entry::getKey));

        System.out.println("Paragraph: " + paragraph + "\n");
        System.out.printf("%-14s %s%n", "Word", "Count");
        System.out.println("---------------------");
        for (Map.Entry<String, Integer> e : entries) {
            System.out.printf("%-14s %d%n", e.getKey(), e.getValue());
        }
    }
}
