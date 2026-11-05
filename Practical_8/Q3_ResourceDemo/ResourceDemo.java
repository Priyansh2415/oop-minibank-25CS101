// Resource modelled as a database connection
class DbConnection implements AutoCloseable {
    private final String name;
    private final boolean failOnClose;

    DbConnection(String name, boolean failOnClose) {
        this.name = name;
        this.failOnClose = failOnClose;
        System.out.println("  [open ] " + name);
    }

    void query(boolean fail) {
        System.out.println("  [query] running on " + name);
        if (fail) throw new IllegalStateException("query failed on " + name);
    }

    @Override
    public void close() {
        System.out.println("  [close] " + name);
        if (failOnClose) throw new RuntimeException("close() failed on " + name);
    }
}

public class ResourceDemo {
    public static void main(String[] args) {
        System.out.println("Case 1: normal execution");
        try (DbConnection c = new DbConnection("conn-1", false)) {
            c.query(false);
        }

        System.out.println("\nCase 2: exception inside the try block");
        try (DbConnection c = new DbConnection("conn-2", false)) {
            c.query(true);
        } catch (IllegalStateException e) {
            System.out.println("  caught: " + e.getMessage() + "  (resource was still closed)");
        }

        System.out.println("\nCase 3: exception in the block AND in close()");
        try (DbConnection c = new DbConnection("conn-3", true)) {
            c.query(true);
        } catch (Exception e) {
            System.out.println("  reported error : " + e.getMessage());
            for (Throwable t : e.getSuppressed()) {
                System.out.println("  suppressed     : " + t.getMessage());
            }
        }
    }
}
