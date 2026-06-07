import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Meetings {
    public static void main(String[] args) throws InterruptedException {
        Map<Long, String> meetings = Collections.synchronizedMap(new HashMap<>());
        ExecutorService es = Executors.newFixedThreadPool(10+10+1);
        AtomicInteger meetingCounter = new AtomicInteger();
        ArrayList<Future<?>> futures = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            futures.add(es.submit(() -> {
                for(int j = 0; j < 5000; j++){
                    long time = System.currentTimeMillis()/(long)(1000*60*10) +
                        ThreadLocalRandom.current().nextLong(0, 60*24*20);
                    if(meetings.putIfAbsent(time, "Meeting #" + meetingCounter.incrementAndGet()) != null){
                        j--;
                    }
                }
            }));
        }
        for(int i = 0; i < 10; i++){
            futures.add(es.submit(() ->{
                for(int j = 0; j < 2500; j++){
                    try{
                        synchronized(meetings){
                            long time = meetings.keySet().iterator().next();
                            if(meetings.remove(time) == null) j--;
                        }
                    } catch(NoSuchElementException e){j--;}
                }
            }));
        }
        // ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        // ses.scheduleAtFixedRate(() -> {}, 0, 10, TimeUnit.MILLISECONDS);
        // ses.shutdown();
        Future<?> printFuture = es.submit(() -> {
            while(true){
                long curTime = System.currentTimeMillis()/(long)(1000*60*10);
                Long nextTime = null;
                String meetingName = null;
                synchronized(meetings){
                    for(Map.Entry<Long, String> entry : meetings.entrySet()){
                        long time = entry.getKey();
                        if(time > curTime && (nextTime == null || time < nextTime)){
                            nextTime = time;
                            meetingName = entry.getValue();
                        }
                    }
                }
                System.out.println("Next meeting at "+ nextTime + " (" + meetingName + ")");
                try {Thread.sleep(10);}
                catch(InterruptedException e){break;}
            }
        });
        es.shutdown();
        // es.awaitTermination(5, TimeUnit.SECONDS);
        for(Future<?> f : futures){
            try{
                f.get();
            } catch(ExecutionException e){
                e.printStackTrace();
            }
        }
        es.shutdownNow(); 
        System.out.println(meetings.size());
    }
}
