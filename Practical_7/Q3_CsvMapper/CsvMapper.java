import java.lang.annotation.*;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Column { String name(); }

class Student {
    @Column(name = "id")    int id;
    @Column(name = "name")  String name;
    @Column(name = "marks") double marks;
    @Column(name = "city")  String city;      // column missing in the data -> default is kept

    @Override
    public String toString() {
        return "Student{id=" + id + ", name=" + name + ", marks=" + marks + ", city=" + city + "}";
    }
}

public class CsvMapper {

    // match annotated fields with header names and convert the text to the field type
    static <T> T map(Class<T> cls, String[] header, String[] row) throws Exception {
        T obj = cls.getDeclaredConstructor().newInstance();
        for (Field f : cls.getDeclaredFields()) {
            Column c = f.getAnnotation(Column.class);
            if (c == null) continue;

            int idx = -1;
            for (int i = 0; i < header.length; i++) {
                if (header[i].equalsIgnoreCase(c.name())) { idx = i; break; }
            }
            if (idx < 0) {                                   // missing column: warn and keep default
                System.out.println("  warning: column '" + c.name() + "' not found, field '"
                        + f.getName() + "' left at default");
                continue;
            }
            String text = row[idx].trim();
            f.setAccessible(true);
            if (f.getType() == int.class)         f.setInt(obj, Integer.parseInt(text));
            else if (f.getType() == double.class) f.setDouble(obj, Double.parseDouble(text));
            else                                  f.set(obj, text);
        }
        return obj;
    }

    public static void main(String[] args) throws Exception {
        String[] header = "id,name,marks".split(",");
        String[] row1 = "101,Riya,88.5".split(",");
        String[] row2 = "102,Aman,76".split(",");

        System.out.println("Header: " + String.join(" | ", header));
        System.out.println("Row 1 :");
        System.out.println("  " + map(Student.class, header, row1));
        System.out.println("Row 2 :");
        System.out.println("  " + map(Student.class, header, row2));
    }
}
