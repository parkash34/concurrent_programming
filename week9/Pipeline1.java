import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Pipeline1 {
    public static void main(String[] args) throws Exception {
        String NO_FURTHER_INPUT1 = "";
        Integer NO_FURTHER_INPUT2 = -1;

        BlockingQueue<String> bq1 = new ArrayBlockingQueue<>(6); // TODO create the queue
        BlockingQueue<Integer> bq2 = new ArrayBlockingQueue<>(1); // TODO create the queue

        ExecutorService pool = Executors.newCachedThreadPool();

        pool.submit(() -> {
            bq1.addAll(List.of("a", "bb", "ccccccc", "ddd", "eeee", NO_FURTHER_INPUT1));
        });

        pool.submit(() -> {
            try {
                while (true) {
                    // TODO queue #1 ====> txt  len ===> queue #2
                    // TODO also handle NO_FURTHER_INPUTs
                    String s = bq1.take();
                    if(s == NO_FURTHER_INPUT1) {
                        bq2.put(NO_FURTHER_INPUT2);
                        break;
                    }
                    bq2.put(s.length());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        pool.submit(() -> {
            try {
                while (true) {
                    // TODO queue #2 ====> len ====> print it
                    // TODO also handle NO_FURTHER_INPUTs

                    Integer i = bq2.take();
                    if(i == NO_FURTHER_INPUT2) break;
                    System.out.println(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
    }
}
