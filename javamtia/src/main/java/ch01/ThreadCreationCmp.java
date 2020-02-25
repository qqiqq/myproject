package ch01;

import util.Tools;

public class ThreadCreationCmp {
    public static void main(String[] args){
        Thread t;
        CountingTask cnt = new CountingTask();
        final int numberOfProcessors = Runtime.getRuntime().availableProcessors();

        for(int i=0; i<2*numberOfProcessors; i++){
            t = new Thread(cnt);
            t.start();
        }

        for(int i=0; i<2*numberOfProcessors; i++){
            t = new CountingThread();
            t.start();
        }
    }

    static class Counter{
        private int count = 0;
        public void inc(){
            count++;
        }
        public int value(){
            return count;
        }
    }

    static class CountingTask implements Runnable{
        private Counter counter = new Counter();

        public void run(){
            for(int i=0; i<100; i++){
                doSomething();
                counter.inc();
            }
            System.out.println("CountingTask: " + counter.value());
        }

        private void doSomething(){
            Tools.randomPause(80);
        }
    }

    static class CountingThread extends Thread{
        private Counter counter = new Counter();

        public void run(){
            for(int i=0; i<100; i++){
                doSomething();
                counter.inc();
            }
            System.out.println("CountingThread: " + counter.value());
        }

        private void doSomething(){
            Tools.randomPause(80);
        }
    }
}
