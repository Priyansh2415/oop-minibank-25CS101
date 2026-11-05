import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRunner {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(3);     // only 3 worker threads
        System.out.println("Submitting 10 tasks to a pool of 3 threads\n");

        for (int id = 1; id <= 10; id++) {
            final int taskId = id;
            pool.submit(() -> {
                System.out.println("Task " + taskId + " started  on " + Thread.currentThread().getName());
                try { Thread.sleep(300); } catch (InterruptedException ignored) { }
                System.out.println("Task " + taskId + " finished on " + Thread.currentThread().getName());
            });
        }

        pool.shutdown();                                            // no new tasks
        pool.awaitTermination(1, TimeUnit.MINUTES);                 // wait for the running ones
        System.out.println("\nAll tasks completed. Pool terminated: " + pool.isTerminated());
    }
}
