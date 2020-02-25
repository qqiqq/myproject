package ch01;

public class WelcomeTask implements Runnable{
    public void run(){
        System.out.printf("2.Welcome! I'm %s.%n", Thread.currentThread().getName());
    }
}
