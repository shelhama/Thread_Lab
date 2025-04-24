import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;

public class CarQueue {
    private Queue<Integer> queue;
    private Random rand;

    public CarQueue() {
        queue = new LinkedList<>();
        rand = new Random();

        // Pre-fill the queue with 6 directions
        for (int i = 0; i < 6; i++) {
            queue.add(rand.nextInt(4));  // 0 = up, 1 = down, 2 = right, 3 = left
        }
    }

    // Adds random directions to the queue in a background thread
    public void addToQueue() {
        Runnable r = new Runnable() {
            public void run() {
                try {
                    while (true) {
                        int direction = rand.nextInt(4); // 0 to 3
                        synchronized (queue) {
                            queue.add(direction);
                        }
                        Thread.sleep(1000); // Add one direction every second
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread = new Thread(r);
        thread.start();
    }

    // Removes and returns a direction from the queue
    public Integer deleteQueue() {
        synchronized (queue) {
            return queue.isEmpty() ? null : queue.poll();
        }
    }
}
