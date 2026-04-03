import java.io.PrintWriter;
import java.io.IOException;


class ChildThread extends Thread{
    String str;

    public ChildThread(String str){
        this.str = str;
    }
    public void run(){
        for(int i = 0; i < 10_000; i++){
            System.out.println(str);
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException{
        try(PrintWriter pw = new PrintWriter("out.txt")){
            Thread t1 = new ChildThread("Hello"),
            t2 = new ChildThread("world");
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        } // catch (Exception e){e.printStackTrace(); System.exit(-1);}
    } 
}


