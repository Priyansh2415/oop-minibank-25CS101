public class CounterRace {

    static int unsafeCount = 0;
    static int safeCount = 0;

    // plain increment - read, add, write are three separate steps.
    // an occasional yield() between read and write widens the race window so the bug is visible
    // even on a single-core machine (on multi-core CPUs it appears without it too).
    static void unsafeIncrement() {
        int temp = unsafeCount;
        if (temp % 1000 == 0) Thread.yield();
        unsafeCount = temp + 1;
    }

    // same steps, but synchronized - only one thread at a time can run them
    static synchronized void safeIncrement() {
        int temp = safeCount;
        if (temp % 1000 == 0) Thread.yield();
        safeCount = temp + 1;
    }

    static void runThreads(int threads, int perThread, Runnable task) throws InterruptedException {
        Thread[] ts = new Thread[threads];
        for (int i = 0; i < threads; i++) {
            ts[i] = new Thread(() -> {
                for (int j = 0; j < perThread; j++) task.run();
            });
            ts[i].start();
        }
        for (Thread t : ts) t.join();
    }

    public static void main(String[] args) throws InterruptedException {
        final int THREADS = 4, PER_THREAD = 50_000;
        final int expected = THREADS * PER_THREAD;
        System.out.println("Threads = " + THREADS + ", increments per thread = " + PER_THREAD);
        System.out.println("Expected total = " + expected + "\n");

        for (int run = 1; run <= 3; run++) {
            unsafeCount = 0;
            runThreads(THREADS, PER_THREAD, CounterRace::unsafeIncrement);
            System.out.println("Run " + run + " WITHOUT synchronization: " + unsafeCount
                    + (unsafeCount == expected ? "" : "   <-- lost " + (expected - unsafeCount) + " updates"));
        }
        System.out.println();
        for (int run = 1; run <= 3; run++) {
            safeCount = 0;
            runThreads(THREADS, PER_THREAD, CounterRace::safeIncrement);
            System.out.println("Run " + run + " WITH synchronized     : " + safeCount
                    + (safeCount == expected ? "   <-- correct" : "   <-- WRONG"));
        }
    }
}
