import java.util.HashMap;
import java.util.List;
import java.util.Map;

class OutOfStockException extends Exception {          // checked, carries the shortfall
    private final int shortfall;
    OutOfStockException(String item, int shortfall) {
        super("Out of stock: " + item + " (short by " + shortfall + ")");
        this.shortfall = shortfall;
    }
    int getShortfall() { return shortfall; }
}

class InvalidQuantityException extends Exception {
    InvalidQuantityException(int qty) { super("Invalid quantity: " + qty); }
}

class Warehouse {
    private final Map<String, Integer> stock = new HashMap<>();

    Warehouse() {
        stock.put("Laptop", 5);
        stock.put("Mouse", 20);
        stock.put("Keyboard", 3);
    }

    void issue(String item, int qty) throws OutOfStockException, InvalidQuantityException {
        if (qty <= 0) throw new InvalidQuantityException(qty);
        int available = stock.getOrDefault(item, 0);
        if (qty > available) throw new OutOfStockException(item, qty - available);
        stock.put(item, available - qty);
        System.out.println("Issued " + qty + " x " + item + " (left: " + stock.get(item) + ")");
    }
}

public class WarehouseDemo {
    record Request(String item, int qty) { }

    public static void main(String[] args) {
        Warehouse w = new Warehouse();
        List<Request> requests = List.of(
            new Request("Laptop", 2), new Request("Mouse", 25), new Request("Keyboard", 0),
            new Request("Keyboard", 3), new Request("Laptop", 4), new Request("Mouse", 10)
        );

        for (Request r : requests) {            // failures never stop the run
            try {
                w.issue(r.item(), r.qty());
            } catch (OutOfStockException e) {
                System.out.println("FAILED: " + e.getMessage() + " [shortfall=" + e.getShortfall() + "]");
            } catch (InvalidQuantityException e) {
                System.out.println("FAILED: " + e.getMessage());
            }
        }
        System.out.println("All requests processed.");
    }
}
