package ch01;

public class welcomApp {
    public static void main(String[] args) {
        Thread welcomeThread = new WelcomeThread();
        welcomeThread.start();
        System.out.printf("1.Welcome! I'm %s.%n", Thread.currentThread().getName());
        welcomeThread.start();
    }
}