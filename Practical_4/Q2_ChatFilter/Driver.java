import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        // (a) log lines, one of them malformed
        String[] logs = {
            "10:05 alice Hello there",
            "10:06 bob Meeting at five",
            "malformed-line",
            "10:09 carol Say HELLO to bob",
            "10:11 dave Thanks everyone"
        };

        // (b) read the keyword
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine().trim();

        // (e) print count and report
        ChatFilter.Result r = ChatFilter.filter(logs, keyword);
        System.out.println("Matches: " + r.matches());
        System.out.print(r.report());
        System.out.println("(skipped malformed lines: " + r.skipped() + ")");
    }
}
