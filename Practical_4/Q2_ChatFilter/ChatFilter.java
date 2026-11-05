public class ChatFilter {

    // Result of a filter run
    record Result(int matches, int skipped, String report) { }

    // (c) + (d) split each line, skip malformed ones, keep keyword matches
    static Result filter(String[] logs, String keyword) {
        int matches = 0, skipped = 0;
        StringBuilder report = new StringBuilder();
        for (String line : logs) {
            String[] parts = line.split(" ", 3);       // time / user / message
            if (parts.length < 3) {
                skipped++;
                continue;
            }
            String time = parts[0], user = parts[1], message = parts[2];
            if (message.toLowerCase().contains(keyword.toLowerCase())) {
                matches++;
                report.append(time).append(' ').append(user).append(": ")
                      .append(message).append('\n');
            }
        }
        return new Result(matches, skipped, report.toString());
    }
}
