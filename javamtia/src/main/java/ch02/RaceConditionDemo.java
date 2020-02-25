package ch02;

import util.Tools;

public class RaceConditionDemo {
    public static void main(String[] args) throws Exception{
        int numberOfThread = args.length>0 ? Short.valueOf(args[0]) : Runtime.getRuntime().availableProcessors();
        Thread[] workerThreads = new Thread[numberOfThread];
        for(int i=0; i<numberOfThread; i++){
            workerThreads[i] = new WorkerThread(i,10);
        }
        for(Thread ct: workerThreads){
            ct.start();
        }

    }
    static class WorkerThread extends Thread{
        private final int requestCount;
        public WorkerThread(int i,int requestCount){
            super("worker-" + i);
            this.requestCount = requestCount;
        }
        public void run(){
            int i = requestCount;
            String requestID;
            RequestIDGenerator requestIDGen = RequestIDGenerator.getInstance();
            while(i --> 0){
                requestID = requestIDGen.nextID();
                processRequest(requestID);
            }
        }
        private void processRequest(String requestID){
            Tools.randomPause(50);
            System.out.printf("%s got requesetID: %s %n", Thread.currentThread().getName(),requestID);
        }
    }
}
