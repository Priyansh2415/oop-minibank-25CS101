public class Thermostat {

    // (a) fields
    private String location;
    private int temperature;
    private static final int MIN = 16;
    private static final int MAX = 30;
    private static int activeCount = 0;

    // (b) Constructor(location, startTemp)
    public Thermostat(String location, int startTemp) {
        this.location = location;
        if (startTemp >= MIN && startTemp <= MAX) {
            this.temperature = startTemp;
        } else {
            this.temperature = 22;
        }
        activeCount++;
    }

    // (c) Constructor(location) chains to the main constructor
    public Thermostat(String location) {
        this(location, 22);
    }

    // (d) raise
    public void raise() {
        if (temperature < MAX) {
            temperature++;
        } else {
            System.out.println("Already at maximum (30)");
        }
    }

    // (e) lower
    public void lower() {
        if (temperature > MIN) {
            temperature--;
        } else {
            System.out.println("Already at minimum (16)");
        }
    }

    // (f) getters only - no setter for temperature
    public int getTemperature() {
        return temperature;
    }

    public String getLocation() {
        return location;
    }

    public static int getActiveCount() {
        return activeCount;
    }

    // (g) main
    public static void main(String[] args) {
        Thermostat t1 = new Thermostat("Living Room");          // default 22
        Thermostat t2 = new Thermostat("Bedroom", 40);          // invalid start -> 22
        System.out.println(t1.getLocation() + " starts at " + t1.getTemperature());
        System.out.println(t2.getLocation() + " starts at " + t2.getTemperature() + " (40 was out of range)");

        System.out.println("\n--- raise() x 10 ---");
        for (int i = 1; i <= 10; i++) {
            t1.raise();
            System.out.println("Temperature: " + t1.getTemperature());
        }

        System.out.println("\n--- lower() x 20 ---");
        for (int i = 1; i <= 20; i++) {
            t1.lower();
            System.out.println("Temperature: " + t1.getTemperature());
        }

        System.out.println("\nActive thermostats: " + Thermostat.getActiveCount());
    }
}
