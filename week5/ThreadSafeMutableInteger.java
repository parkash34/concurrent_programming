import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

public class ThreadSafeMutableInteger {
    private volatile int value;
    public ThreadSafeMutableInteger(){}
    public ThreadSafeMutableInteger(int value){set(value);}
    public int get(){return value;}
    public void set(int value){ this.value = value;}

    synchronized int getAndIncrement(){
        return value++;
    }
    synchronized int IncrementAndGet(){
        return ++value;
    }
    synchronized int getAndDecrement(){
        return value--;
    }
    synchronized int DecrementAndGet(){
        return --value;
    }
    synchronized int getAndAdd(int v){
        int temp = value;
        value += v;
        return temp;
    }
    synchronized int AddAndget(int v){
        return value += v;
    }

    synchronized int getAndUpdate(IntUnaryOperator iuo){
        int oldValue = value;
        value = iuo.applyAsInt(value);
        return oldValue;
    }
    synchronized int updateAndGet(IntUnaryOperator iuo){
        return value = iuo.applyAsInt(value);
    }
    public static void main(String[] args) throws InterruptedException {
        Thread[] ts = new Thread[10];
        ThreadSafeMutableInteger tsmi = new ThreadSafeMutableInteger();
        AtomicInteger ai = new AtomicInteger();
        for(int i = 0; i < ts.length; i++){
            int fi = i;
            ts[i] = new Thread(() -> {
                for(int j = 0; j < 10_000_000; j++)
                    // synchronized(tsmi){
                    //     tsmi.set(tsmi.get() + 1);
                    // }
                    // tsmi.getAndIncrement();
                    // if (fi < 5) tsmi.getAndIncrement();
                    // else tsmi.getAndDecrement();
                    // tsmi.updateAndGet(x -> Math.min(10_000_000, x+2));
                    ai.getAndIncrement();
            });
        }   

        // long l = System.nanoTime();
        // for(int i = 0; i < ts.length; i++)  ts[i].start();
        // for(int i = 0; i < ts.length; i++)  ts[i].join();
        // System.out.println("Time: "+ (System.nanoTime() - l)*1e-9 + "s");
        // System.out.println("Result: "+ tsmi.get());
    }
}
