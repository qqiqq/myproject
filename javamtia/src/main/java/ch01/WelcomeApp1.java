package ch01;

public class WelcomeApp1 {
    public static void main(String[] args){
        WelcomeTask wt = new WelcomeTask();
        Thread welcomeThread1 = new Thread(wt);
        Thread welcomeThread2 = new Thread(wt);
        welcomeThread1.start();
        welcomeThread2.start();
        System.out.printf("1.Welcome! I'm %s.%n", Thread.currentThread().getName());
    }
}
