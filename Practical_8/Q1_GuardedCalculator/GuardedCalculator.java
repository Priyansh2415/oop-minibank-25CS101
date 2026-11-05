import java.util.Scanner;

// custom (checked) exception for division by zero
class DivideByZeroException extends Exception {
    DivideByZeroException(String message) { super(message); }
}

public class GuardedCalculator {

    static double calculate(double a, double b, char op) throws DivideByZeroException {
        return switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> {
                if (b == 0) throw new DivideByZeroException("Cannot divide by zero");
                yield a / b;
            }
            default -> throw new IllegalArgumentException("Unknown operator: " + op);
        };
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int attempt = 0;
        boolean done = false;

        while (!done) {                       // loop until a valid calculation succeeds
            attempt++;
            try {
                System.out.print("First number : ");
                String s1 = sc.nextLine();
                System.out.print("Second number: ");
                String s2 = sc.nextLine();
                System.out.print("Operator (+ - * /): ");
                char op = sc.nextLine().trim().charAt(0);

                double result = calculate(Double.parseDouble(s1), Double.parseDouble(s2), op);
                System.out.println("Result = " + result);
                done = true;
            } catch (DivideByZeroException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Error: invalid number entered");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                System.out.println("[LOG] attempt " + attempt + " finished\n");
            }
        }
    }
}
