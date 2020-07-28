package classloading;

public class DeadLoopClass {
    static {
        if(true){
            System.out.println(Thread.currentThread() + "init DeadLoopClass");
            while(true){}
        }
    }

    public static void main(String[] args){
        Runnable sc = new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread() + "start");
                DeadLoopClass dlc = new DeadLoopClass();
                System.out.println(Thread.currentThread() + " run over");
            }
        };
        Thread th1 = new Thread(sc);
        Thread th2 = new Thread(sc);
        th1.start();
        th2.start();
    }
}
