import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FolderReport {
    public static void main(String[] args) throws IOException {
        // sample folder tree so the program is self-contained
        Path root = Paths.get("project");
        Files.createDirectories(root.resolve("src/util"));
        Files.createDirectories(root.resolve("docs"));
        Files.writeString(root.resolve("README.md"), "# Demo project\nA small sample tree.\n");
        Files.writeString(root.resolve("src/Main.java"), "public class Main { }\n");
        Files.writeString(root.resolve("src/util/Helper.java"), "class Helper { void help() { } }\n");
        Files.writeString(root.resolve("docs/notes.txt"), "Some notes for the project.\n");

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
        List<String> report = new ArrayList<>();
        long[] totals = new long[2];          // [0] = files, [1] = bytes

        // walk the tree with NIO
        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
                totals[0]++;
                totals[1] += attr.size();
                report.add(String.format("%-24s %5d bytes   %s",
                        root.relativize(file), attr.size(), fmt.format(attr.lastModifiedTime().toInstant())));
                return FileVisitResult.CONTINUE;
            }
        });

        report.add("Total: " + totals[0] + " files, " + totals[1] + " bytes");
        Files.write(Paths.get("report.txt"), report);          // write the summary to a file

        System.out.println("Folder report for '" + root + "':\n");
        report.forEach(System.out::println);
        System.out.println("\nSummary written to report.txt (" + Files.size(Paths.get("report.txt")) + " bytes)");
    }
}
