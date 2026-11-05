import java.util.LinkedList;
import java.util.Queue;

class Buffer {
    private final Queue<Integer> queue = new LinkedList<>();
    private final int capacity;

    Buffer(int capacity) { this.capacity = capacity; }

    synchronized void put(int value) throws InterruptedException {
        while (queue.size() == capacity) {
            System.out.println("   (buffer full - producer waits)");
            wait();                                  // release lock until consumer notifies
        }
        queue.add(value);
        System.out.println("Produced " + value + "   buffer=" + queue);
        notifyAll();                                 // wake the consumer
    }

    synchronized int take() throws InterruptedException {
        while (queue.isEmpty()) {
            System.out.println("   (buffer empty - consumer waits)");
            wait();
        }
        int value = queue.remove();
        System.out.println("Consumed " + value + "   buffer=" + queue);
        notifyAll();                                 // wake the producer
        return value;
    }
}

public class ProducerConsumer {
    public static void main(String[] args) throws InterruptedException {
        final int ITEMS = 8;
        Buffer buffer = new Buffer(3);
        int[] received = new int[ITEMS];

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= ITEMS; i++) { buffer.put(i); Thread.sleep(50); }
            } catch (InterruptedException ignored) { }
        }, "Producer");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < ITEMS; i++) { received[i] = buffer.take(); Thread.sleep(150); }
            } catch (InterruptedException ignored) { }
        }, "Consumer");

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        System.out.println("\nReceived in order: " + java.util.Arrays.toString(received));
    }
}
