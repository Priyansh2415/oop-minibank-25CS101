import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class LogAnalyzer {
    public static void main(String[] args) throws IOException {
        Path dir = Paths.get("logs");
        Files.createDirectories(dir);

        // create a few text files
        Files.write(dir.resolve("app.log"), List.of(
            "INFO  server started", "ERROR disk almost full", "INFO  user login", "ERROR timeout on db"));
        Files.write(dir.resolve("auth.log"), List.of(
            "INFO  login ok", "WARN  bad password", "ERROR account locked"));
        Files.write(dir.resolve("db.log"), List.of(
            "INFO  connected", "INFO  query ok"));

        String keyword = "ERROR";
        int totalLines = 0, matches = 0;

        // read each file line by line with Path / Files
        try (DirectoryStream<Path> files = Files.newDirectoryStream(dir, "*.log")) {
            for (Path p : files) {
                List<String> lines = Files.readAllLines(p);
                int fileMatches = 0;
                for (String line : lines) {
                    if (line.contains(keyword)) fileMatches++;
                }
                totalLines += lines.size();
                matches += fileMatches;

                BasicFileAttributes attr = Files.readAttributes(p, BasicFileAttributes.class);
                System.out.printf("%-10s size = %3d bytes | lines = %d | '%s' lines = %d%n",
                        p.getFileName(), attr.size(), lines.size(), keyword, fileMatches);
            }
        }
        System.out.println("\nTotal lines            : " + totalLines);
        System.out.println("Lines containing '" + keyword + "': " + matches);
    }
}
