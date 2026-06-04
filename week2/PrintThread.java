import java.io.IOException;
import java.io.PrintWriter;

public class PrintThread{
    public static void main(String[] args) throws IOException, InterruptedException {   
        try(PrintWriter pw = new PrintWriter("out.txt")){

            Thread t1 = null, t2 = null;
            for(int i = 0; i < 2; i++){
                String s = i == 0 ? "Hello" : "World";
                Thread t = new Thread(() -> {
                    for(int j = 0; j < 10_000; j++){
                        if(j != 0){
                            try{
                                Thread.sleep(5);
                            } catch(InterruptedException e){
                                // ignore
                                Thread.currentThread().interrupt(); //re-interrupts
                                break;
                            }
                        }
                        pw.println(s);
                    }
                    pw.println("ready");
                });
                if (i == 0) t1 = t;
                else t2 = t;

            }
            t1.start();
            t2.start();

            Thread.sleep(1000);
            t1.interrupt();
            t2.interrupt();

            t1.join();
            t2.join();
            System.out.println();
        }        
    }
}
