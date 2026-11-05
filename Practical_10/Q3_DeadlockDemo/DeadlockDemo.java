import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class DeadlockDemo {

    static class Account {
        final int id;
        long balance = 1000;
        Account(int id) { this.id = id; }
    }

    // BAD: locks 'from' then 'to' - opposite transfers can lock in opposite order
    static void transferBad(Account from, Account to, long amt) {
        synchronized (from) {
            sleep(200);                                  // makes the deadlock easy to reproduce
            synchronized (to) { from.balance -= amt; to.balance += amt; }
        }
    }

    // GOOD: always lock the account with the smaller id first
    static void transferGood(Account from, Account to, long amt) {
        Account first = from.id < to.id ? from : to;
        Account second = first == from ? to : from;
        synchronized (first) {
            sleep(200);
            synchronized (second) { from.balance -= amt; to.balance += amt; }
        }
    }

    static void sleep(long ms) { try { Thread.sleep(ms); } catch (InterruptedException ignored) { } }

    interface Transfer { void run(Account a, Account b, long amt); }

    static Thread[] start(Transfer t, Account a, Account b, boolean daemon) {
        Thread t1 = new Thread(() -> t.run(a, b, 100), "T1 (A->B)");
        Thread t2 = new Thread(() -> t.run(b, a, 50),  "T2 (B->A)");
        t1.setDaemon(daemon); t2.setDaemon(daemon);
        t1.start(); t2.start();
        return new Thread[] { t1, t2 };
    }

    public static void main(String[] args) throws InterruptedException {
        Account a = new Account(1), b = new Account(2);

        System.out.println("=== Part 1: opposite lock order (deadlock) ===");
        start(DeadlockDemo::transferBad, a, b, true);
        Thread.sleep(1000);
        ThreadMXBean mx = ManagementFactory.getThreadMXBean();
        long[] ids = mx.findDeadlockedThreads();
        if (ids != null) {
            System.out.println("DEADLOCK DETECTED between " + ids.length + " threads:");
            for (var info : mx.getThreadInfo(ids)) {
                System.out.println("  " + info.getThreadName() + " is BLOCKED waiting for lock held by "
                        + info.getLockOwnerName());
            }
        } else {
            System.out.println("No deadlock occurred this time.");
        }

        System.out.println("\n=== Part 2: consistent lock order (fixed) ===");
        Account c = new Account(1), d = new Account(2);
        Thread[] ts = start(DeadlockDemo::transferGood, c, d, false);
        for (Thread t : ts) t.join();
        System.out.println("Both transfers completed.");
        System.out.println("Balances -> account1 = " + c.balance + ", account2 = " + d.balance);
    }
}
