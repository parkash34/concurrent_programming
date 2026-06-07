import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BankLoan{
    public static void main(String[] args) throws InterruptedException, ExecutionException{
        ExecutorService es = Executors.newFixedThreadPool(10);
        AtomicInteger loanTotal = new AtomicInteger(0);
        ArrayList<Future<Integer>> futures = new ArrayList<>();
        int[] loanTotalInt = new int[1];
        int[] clientTotals = new int[10];
        for(int i = 0; i < 10; i++){
            int fi = i;
            futures.add(es.submit(() -> {
                try{
                    for(int j = 0; j < 10000; j++){
                        int loanAmt = ThreadLocalRandom.current().nextInt(100, 1000+1);
                        loanTotal.addAndGet(loanAmt);
                        synchronized(loanTotalInt){loanTotalInt[0] += loanAmt;}
                        clientTotals[fi] += loanAmt;
                    }
                    return clientTotals[fi];
                } catch(Exception e) {e.printStackTrace(); return null;}
            }));
        }
        es.shutdown();
        es.awaitTermination(5, TimeUnit.SECONDS);
        es.shutdownNow();
        int total = 0;
        for(int i = 0; i < clientTotals.length; i++){
            total += futures.get(i).get();
        }
        System.out.println("Loan Total: " + loanTotal.get() + " Non-safe " + loanTotalInt[0] + " Check total: "+ Arrays.stream(clientTotals).sum() + " Future total: "+ total);
    }
}