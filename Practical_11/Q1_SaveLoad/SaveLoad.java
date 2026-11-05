import java.io.*;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    int roll;
    transient String password;       // transient -> NOT written to the file

    Student(String name, int roll, String password) {
        this.name = name;
        this.roll = roll;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Student{name=" + name + ", roll=" + roll + ", password=" + password + "}";
    }
}

public class SaveLoad {
    public static void main(String[] args) throws Exception {
        Student[] original = {
            new Student("Riya", 1, "riya@123"),
            new Student("Aman", 2, "aman@456"),
            new Student("Neha", 3, "neha@789")
        };

        System.out.println("Original objects:");
        for (Student s : original) System.out.println("  " + s);

        // save the whole array to a file
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("students.ser"))) {
            out.writeObject(original);
        }
        System.out.println("\nSaved to students.ser (" + new File("students.ser").length() + " bytes)");

        // read it back into fresh objects
        Student[] loaded;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("students.ser"))) {
            loaded = (Student[]) in.readObject();
        }

        System.out.println("\nLoaded objects:");
        for (Student s : loaded) System.out.println("  " + s);

        boolean same = true;
        for (int i = 0; i < original.length; i++) {
            same &= original[i].name.equals(loaded[i].name) && original[i].roll == loaded[i].roll;
        }
        System.out.println("\nname/roll survived : " + same);
        System.out.println("password (transient): " + loaded[0].password + "  <- not saved");
    }
}
