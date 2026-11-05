@FunctionalInterface
interface Notifier {
    void send(String message);
}

// marker interface - no methods, only tags a sender as urgent
interface Urgent { }

public class NotificationDemo {
    public static void main(String[] args) {
        // senders written as lambdas
        Notifier email = msg -> System.out.println("[EMAIL] " + msg);
        Notifier sms   = msg -> System.out.println("[SMS]   " + msg);
        // intersection cast lets a lambda also carry the Urgent marker
        Notifier push  = (Notifier & Urgent) msg -> System.out.println("[PUSH]  " + msg);

        Notifier[] senders = { email, sms, push };

        String message = "Server maintenance at 11 PM";
        System.out.println("Broadcasting: " + message + "\n");
        for (Notifier n : senders) {
            n.send(message);
            if (n instanceof Urgent) {          // urgent senders send twice
                n.send(message + " (urgent repeat)");
            }
        }
    }
}
