package ch01;

public class WelcomeThread extends  Thread{
    public void run(){
        System.out.printf("2.Welcome! I'm %s.%n", Thread.currentThread().getName());
    }
}

