import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public class Pipeline2 {
    private static <T> List<T> nCopyList(int count, IntFunction<T> makeElem) {
        return IntStream.range(0, count).mapToObj(i -> makeElem.apply(i)).toList();
    }

    public static void main(String[] args) throws Exception {
        List<Function<Integer, Integer>> funs = List.of(
            n -> n + 1,
            n -> 2 * n + 1,
            n -> -n
        );

        var NO_FURTHER_INPUT = Integer.MAX_VALUE;

        List<BlockingQueue<Integer>> queues = nCopyList(funs.size() + 1,
            n -> new ArrayBlockingQueue<>(n == 0 ? 8 : 1) /* TODO create the nth queue */);

        var pool = Executors.newCachedThreadPool();

        pool.submit(() -> {
            var data = List.of(1, 2, 3, 4, 5, 6, 100);
            queues.get(0).addAll(data);
            queues.get(0).add(NO_FURTHER_INPUT);
        });

        for (int i = 0; i < funs.size(); i++) {
            int idx = i;
            pool.submit(() -> {
                try {
                    // TODO prev queue ====> num ===> use function, get new num ===> next queue
                    while(true){
                        Integer val = queues.get(idx).take();
                        if(val == NO_FURTHER_INPUT){
                            queues.get(idx + 1).put(val);
                            break;
                        }
                        queues.get(idx + 1).put(funs.get(idx).apply(val));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        pool.submit(() -> {
            try {
                while (true) {
                    // TODO print the elements of the last queue
                    Integer val = queues.get(queues.size() - 1).take();
                    if(val == NO_FURTHER_INPUT) break;
                    System.out.println(val);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
    }
}
