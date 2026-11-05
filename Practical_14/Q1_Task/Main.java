import java.util.Scanner;

// MENU layer
public class Main {
    public static void main(String[] args) {
        TaskService service = new TaskService();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("\n1.Add  2.List  3.Remove  4.Exit > ");
            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> {
                        System.out.print("Title: ");
                        Task t = service.add(sc.nextLine());
                        System.out.println("Added: " + t);
                    }
                    case "2" -> {
                        System.out.println("Tasks (" + service.count() + "):");
                        service.all().forEach(t -> System.out.println("  " + t));
                    }
                    case "3" -> {
                        System.out.print("Task id: ");
                        service.remove(Integer.parseInt(sc.nextLine().trim()));
                        System.out.println("Removed.");
                    }
                    case "4" -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid option");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
