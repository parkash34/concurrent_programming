import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

public class SyncList {
    static void syncIterate(Collection<Integer> c, int num){
        synchronized(c){
            nonSyncIterate(c,num);
        }
    }
    static void nonSyncIterate(Collection<Integer> c, int num){
        for(Integer i : c){
            System.out.print(num + " " + i + " ");
        }
    }
    public static void main(String[] args) throws InterruptedException{
        //          non-sync sync get java.util.ConcurrentModificationException
        //ArrayList  X        X
        //LinkedList X        X
        //Vector     X
        //syncColl   X
        //syncList   X
        for(Collection<Integer> coll : List.of(
            new ArrayList<Integer>(), 
            new LinkedList<Integer>(),
            new Vector<Integer>(), 
            Collections.synchronizedCollection(new ArrayList<Integer>()), 
            Collections.synchronizedList(new ArrayList<Integer>()))){
            Thread[] ts = new Thread[2];
            AtomicBoolean running = new AtomicBoolean(true);
            for(int i = 0; i < ts.length; i++){
                int fi = i;
                ts[i] = new Thread(() -> {
                    while(running.get()){
                        if(fi == 0) nonSyncIterate(coll, fi);
                        else syncIterate(coll, fi);
                    }
                });
            }
            // for(int i = 0; i < ts.length; i++) ts[i].start();
            // for(int i = 0; i < 100_000; i++) coll.add(i);
            // running.set(false);
            // for(int i = 0; i < ts.length; i++) ts[i].join();
        }      
    }
}
