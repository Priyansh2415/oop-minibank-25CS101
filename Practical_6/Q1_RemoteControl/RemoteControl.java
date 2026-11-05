interface Switchable {
    void on();
    void off();
    boolean isOn();
    String name();

    // default method shared by every implementation
    default void toggle() {
        if (isOn()) off(); else on();
    }
}

class Fan implements Switchable {
    private boolean running;
    public void on()  { running = true;  System.out.println("Fan is now ON"); }
    public void off() { running = false; System.out.println("Fan is now OFF"); }
    public boolean isOn() { return running; }
    public String name() { return "Fan"; }
}

class Light implements Switchable {
    private boolean lit;
    public void on()  { lit = true;  System.out.println("Light is now ON"); }
    public void off() { lit = false; System.out.println("Light is now OFF"); }
    public boolean isOn() { return lit; }
    public String name() { return "Light"; }
}

// functional interface: may this device be switched on at this hour?
@FunctionalInterface
interface SwitchPolicy {
    boolean canSwitchOn(Switchable device, int hour);
}

public class RemoteControl {
    public static void main(String[] args) {
        Switchable[] devices = { new Fan(), new Light() };

        System.out.println("--- toggle once ---");
        for (Switchable d : devices) d.toggle();
        System.out.println("--- toggle again ---");
        for (Switchable d : devices) d.toggle();

        // policy as an ANONYMOUS CLASS: light only after 6 PM
        SwitchPolicy anonymous = new SwitchPolicy() {
            @Override
            public boolean canSwitchOn(Switchable device, int hour) {
                return device.name().equals("Light") ? hour >= 18 : true;
            }
        };

        // the same policy as a LAMBDA
        SwitchPolicy lambda = (device, hour) -> !device.name().equals("Light") || hour >= 18;

        System.out.println("\n--- policy check (anonymous class vs lambda) ---");
        int[] hours = { 10, 20 };
        for (int h : hours) {
            for (Switchable d : devices) {
                System.out.println(h + ":00  " + d.name()
                        + "  anonymous=" + anonymous.canSwitchOn(d, h)
                        + "  lambda=" + lambda.canSwitchOn(d, h));
            }
        }
    }
}
