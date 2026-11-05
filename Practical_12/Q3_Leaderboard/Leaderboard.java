import java.util.*;

public class Leaderboard {

    // top-N entries sorted by score (high to low), ties broken by name (A to Z)
    static List<Map.Entry<String, Integer>> topN(Map<String, Integer> scores, int n) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());
        list.sort((a, b) -> {
            int byScore = Integer.compare(b.getValue(), a.getValue());
            return byScore != 0 ? byScore : a.getKey().compareTo(b.getKey());
        });
        return list.subList(0, Math.min(n, list.size()));
    }

    public static void main(String[] args) {
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Riya", 920);
        scores.put("Aman", 870);
        scores.put("Neha", 920);      // ties with Riya
        scores.put("Karan", 640);
        scores.put("Dev", 870);       // ties with Aman
        scores.put("Meera", 780);
        scores.put("Zoya", 455);

        int n = 5;
        System.out.println("Top " + n + " players");
        System.out.println("----------------------");
        int rank = 1;
        for (Map.Entry<String, Integer> e : topN(scores, n)) {
            System.out.printf("%d. %-6s %4d%n", rank++, e.getKey(), e.getValue());
        }
    }
}
