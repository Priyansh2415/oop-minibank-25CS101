public class Driver {
    public static void main(String[] args) {
        // (a) template + parallel arrays ({date} is not supplied)
        String template = "Dear {name}, order {id} ships {date}.";
        String[] names  = { "name", "id" };
        String[] values = { "Riya", "A07" };

        System.out.println("Template : " + template);
        // (d) print the filled template
        System.out.println("Filled   : " + TemplateFiller.fill(template, names, values));
    }
}
