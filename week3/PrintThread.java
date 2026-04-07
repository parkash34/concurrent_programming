import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class PrintThread{
        public static void main(String[] args) throws InterruptedException, IOException{
        Thread[] ts = new Thread[10];
        for(int i = 0; i < ts.length; i++){
            // int fi = i;
            ts[i] = new Thread(() -> {
                String name = Thread.currentThread().getName();
                // String name = ts[fi].getName();
                try(PrintWriter pw = new PrintWriter(name)){
                    for(int j = 1; j <= 1_000_000; j++){
                        pw.println(j);
                    }
                } catch(IOException e){}
            });
        }
        for(int i = 0; i < ts.length; i++){
            ts[i].start();
        }
        Thread.sleep(50);
        for(int i = 0; i < ts.length; i++){
            try(BufferedReader br = new BufferedReader(new FileReader(ts[i].getName()))){
                String last = null, s = null;
                while((s = br.readLine()) != null){
                    last = s;
                }
                System.out.println(last);
            }
        }
        for(int i = 0; i < ts.length; i++){
            ts[i].join();
        }

        try(PrintWriter pw = new PrintWriter("out.txt")){
            
            Thread t1 = null, t2 = null;
            for(int i = 0; i < 2; i++){
                String s = i == 0 ? "Hello" : "world";
                Thread t = new Thread(() -> {
                    for(int j = 0; j < 10_000; j++){
                        if(j != 0){
                            try{
                                Thread.sleep(5);
                            } catch(InterruptedException e){
                                //ignore
                                Thread.currentThread().interrupt(); //re-interrupts
                                break; // exit
                            }
                        }
                        pw.println(s);
                    }
                    pw.println("ready");
                });
                if(i == 0) t1 = t;
                else t2 = t;
            }

            t1.start();
            t2.start();
            Thread.sleep(1000);
            t1.interrupt();
            t2.interrupt();
            t1.join();
            t2.join();
        }
    }
}