import java.util.List;
import java.util.ArrayList;

public class Sync {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> arr = new ArrayList<>();
        Runnable r1 = null, r2 = null;
        for(int i = 0; i < 2; i++){
            int fi = i;
            Runnable r = () -> {
                for(int j = 1+fi; j <= 1_000_000; j+=2){
                    synchronized(arr){
                        if((arr.size() == 0 ? 0 : arr.get(arr.size()-1)) == j-1)
                            arr.add(j);
                        else j -= 2;
                    }
                }
            };
            if (i == 0) r1 = r;
            else r2 = r;
        }
        Thread[] ts = new Thread[]{new Thread(r1), new Thread(r2)};
        ts[0].start();
        ts[1].start();
        ts[0].join();
        ts[1].join();
        int inversions = 0;
        for(int i = 0; i < arr.size() - 1; i++){
            if(arr.get(i+1) == null) continue;
            if(arr.get(i) == null || arr.get(i) > arr.get(i+1)) inversions++;
        }
        System.out.println("Number of elements: "+ arr.size() + 
            " Inversions: "+inversions);
    }
}
