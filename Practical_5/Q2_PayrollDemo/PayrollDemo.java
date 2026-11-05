abstract class Employee {
    private final String name;
    private final int id;

    Employee(String name, int id) {      // shared data handled once in the base class
        this.name = name;
        this.id = id;
    }
    String getName() { return name; }
    int getId()      { return id; }
    abstract double monthlySalary();
}

class FullTime extends Employee {
    private final double fixed;
    FullTime(String name, int id, double fixed) { super(name, id); this.fixed = fixed; }
    double monthlySalary() { return fixed; }
}

class PartTime extends Employee {
    private final int hours;
    private final double rate;
    PartTime(String name, int id, int hours, double rate) {
        super(name, id);
        this.hours = hours;
        this.rate = rate;
    }
    double monthlySalary() { return hours * rate; }
}

class Intern extends Employee {
    private final double stipend;
    Intern(String name, int id, double stipend) { super(name, id); this.stipend = stipend; }
    double monthlySalary() { return stipend; }
}

public class PayrollDemo {
    public static void main(String[] args) {
        Employee[] staff = {
            new FullTime("Asha", 101, 55000),
            new PartTime("Rohan", 102, 80, 400),
            new Intern("Meera", 103, 12000),
            new FullTime("Karan", 104, 72000),
            new Intern("Dev", 105, 10000)
        };

        double total = 0;
        for (Employee e : staff) {
            double pay = e.monthlySalary();
            total += pay;
            String note = (e instanceof Intern) ? "  <- Intern: stipend only, no benefits" : "";
            System.out.printf("%-6s (ID %d) : Rs %9.2f%s%n", e.getName(), e.getId(), pay, note);
        }
        System.out.printf("%nTotal monthly payroll: Rs %.2f%n", total);
    }
}
