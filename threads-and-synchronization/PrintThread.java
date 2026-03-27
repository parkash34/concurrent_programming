public class PrintThread {
    public static void main(String[] args) throws InterruptedException {
        Thread ts[] = new Thread[2];
        for(int i = 0; i < ts.length; i++){
            String s = i == 0 ? "Hello" : "World";
            // new Thread(new Runnable(){public void run()})
            ts[i] = new Thread(()-> {
                for(int j = 0; j < 10_000; j++){
                    System.out.println(s);
                }
            });
        }
        ts[0].start();
        ts[1].start();
        ts[0].join();
        ts[1].join();
    }
}

// class ChildThead extends Thread{ChildThread(){}public void run(){}}

// void main(){
//     // IO.println();
    
// }
