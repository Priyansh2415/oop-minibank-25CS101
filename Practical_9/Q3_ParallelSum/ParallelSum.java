public class ParallelSum {

    static long unsafeTotal = 0;
    static long syncTotal = 0;
    static long partialTotal = 0;

    static synchronized void addSync(long v)    { syncTotal += v; }
    static synchronized void addPartial(long v) { partialTotal += v; }

    interface Worker { void work(int from, int to); }

    static long time(int threads, int n, Worker w) throws InterruptedException {
        long t0 = System.nanoTime();
        Thread[] ts = new Thread[threads];
        int chunk = n / threads;
        for (int i = 0; i < threads; i++) {
            final int from = i * chunk, to = (i == threads - 1) ? n : from + chunk;
            ts[i] = new Thread(() -> w.work(from, to));
            ts[i].start();
        }
        for (Thread t : ts) t.join();
        return (System.nanoTime() - t0) / 1_000_000;
    }

    public static void main(String[] args) throws InterruptedException {
        final int N = 5_000_000, THREADS = 4;
        int[] data = new int[N];
        long expected = 0;
        for (int i = 0; i < N; i++) { data[i] = (i % 10) + 1; expected += data[i]; }
        System.out.println("Array size = " + N + ", threads = " + THREADS);
        System.out.println("Expected sum (single thread) = " + expected + "\n");

        long t1 = time(THREADS, N, (from, to) -> {
            for (int i = from; i < to; i++) unsafeTotal += data[i];            // data race
        });
        System.out.println("1. Shared total, no locking     : " + unsafeTotal
                + (unsafeTotal == expected ? "" : "  WRONG") + "  (" + t1 + " ms)");

        long t2 = time(THREADS, N, (from, to) -> {
            for (int i = from; i < to; i++) addSync(data[i]);                  // fix A: lock on every add
        });
        System.out.println("2. Fix A: synchronized add each : " + syncTotal
                + (syncTotal == expected ? "  correct" : "  WRONG") + "  (" + t2 + " ms)");

        long t3 = time(THREADS, N, (from, to) -> {
            long local = 0;                                                    // fix B: private partial sum
            for (int i = from; i < to; i++) local += data[i];
            addPartial(local);                                                 // one lock per thread
        });
        System.out.println("3. Fix B: partial sums, 1 lock  : " + partialTotal
                + (partialTotal == expected ? "  correct" : "  WRONG") + "  (" + t3 + " ms)");
    }
}
