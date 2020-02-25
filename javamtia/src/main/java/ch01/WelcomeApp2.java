package ch01;

public class WelcomeApp2 {
    public static void main(String[] args){
        WelcomeTask wt = new WelcomeTask();
        Thread welcomeThread1 = new Thread(wt){
            public void run(){
                System.out.printf("2.Welcome! I'm %s.%n", Thread.currentThread().getName());
            }
        };
        welcomeThread1.start();
        welcomeThread1.run();
        System.out.printf("1.Welcome! I'm %s.%d", Thread.currentThread().getName(), Thread.currentThread().getId());
    }
}
