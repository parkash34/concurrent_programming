import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Pipeline0 {
    public static void main(String[] args) throws Exception {
        
        String NO_FURTHER_INPUT = "";
        BlockingQueue<String> bq = new ArrayBlockingQueue<>(6); // TODO create the queue
        var pool = Executors.newCachedThreadPool();

        pool.submit(() -> {
            bq.addAll(List.of("a", "bb", "ccccccc", "ddd", "eeee", NO_FURTHER_INPUT));
        });

        pool.submit(() -> {
            try {
                while (true) {
                    // TODO queue #1 ====> txt ===> print it
                    // TODO also handle NO_FURTHER_INPUTs
                    String s = bq.take();
                    if(s == NO_FURTHER_INPUT) break;
                    System.out.println(s);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
    }
}
