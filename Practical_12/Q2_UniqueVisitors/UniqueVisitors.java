import java.util.*;

public class UniqueVisitors {
    public static void main(String[] args) {
        List<String> logins = List.of(
            "riya", "aman", "riya", "neha", "karan", "aman", "neha", "dev", "riya", "meera");
        System.out.println("Login log     : " + logins);
        System.out.println("Total logins  : " + logins.size());

        // HashSet - removes repeats but order is not guaranteed
        Set<String> unique = new HashSet<>(logins);
        System.out.println("\nUnique users (HashSet)          : " + unique.size());
        System.out.println("HashSet order (not guaranteed)  : " + unique);

        // LinkedHashSet - removes repeats AND keeps first-seen order
        Set<String> ordered = new LinkedHashSet<>(logins);
        System.out.println("\nLinkedHashSet (first-seen order): " + ordered);

        System.out.print("First three distinct names      : ");
        int shown = 0;
        for (String name : ordered) {
            if (shown++ == 3) break;
            System.out.print(name + (shown < 3 ? ", " : ""));
        }
        System.out.println();
    }
}
