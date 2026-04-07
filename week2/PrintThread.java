// package week2;

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
}

public class PrintThread {
        public static void main(String[] args) throws InterruptedException, IOException{
        try(PrintWriter pw = new PrintWriter("out.txt")){

            // Thread t1 = new ChildThread("Hello"),
            // t2 = new ChildThread("world");

            // Thread t1 = new Thread(){
            //     public void run(){
            //         for(int i = 0; i < 10_000; i++){
            //             pw.println("Hello");
            //         }
            //     }
            // }, t2 = new Thread(new Runnable(){
            //     public void run(){
            //         for(int i = 0; i < 10_000; i++){
            //             pw.println("world");
            //         }
            //     }
            // });
            Thread t1 = null, t2 = null;
            for(int i = 0; i < 2; i++){
                String s = i == 0 ? "Hello" : "world";
                Thread t = new Thread(() -> {
                    for(int j = 0; j < 10_000; j++){
                        pw.println(s);
                    }
                });
                if(i == 0) t1 = t;
                else t2 = t;
            }

            t1.start();
            t2.start();
            t1.join();
            t2.join();
        } // catch (Exception e){e.printStackTrace(); System.exit(-1);}
    }
}