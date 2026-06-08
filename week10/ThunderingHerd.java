import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ThunderingHerd {
    public static void main(String[] args) throws InterruptedException {
        Thread[] ts = new Thread[100];
        Object o = new Object();
        AtomicBoolean hasResource = new AtomicBoolean();
        AtomicInteger resource = new AtomicInteger();

        for(int i = 0; i < ts.length; i++){
            ts[i] = new Thread(() -> {
                try{
                    for(int j = 0; j < 2000; j++){
                        synchronized(o){
                            o.wait();
                        }
                        if(hasResource.compareAndExchange(false, true)){
                            resource.incrementAndGet();
                            hasResource.set(false);
                        }
                    }
                } catch (InterruptedException e){}
            });
        }
        for(int i = 0; i < ts.length; i++) ts[i].start();
        for(int i = 0; i < 2000; i++){
            synchronized(o){
                o.notifyAll();
            }
            Thread.sleep(1);
        }
        for(int i = 0; i < ts.length; i++) ts[i].interrupt();
        for(int i = 0; i < ts.length; i++) ts[i].join();
        System.out.println(resource.get());

    }
}
