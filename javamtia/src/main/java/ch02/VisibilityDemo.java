package ch02;

import util.Tools;

public class VisibilityDemo {
    public static void main(String[] args) throws InterruptedException{
        TimeConsumingTask timeConsumingTask = new TimeConsumingTask();
        Thread thread = new Thread(new TimeConsumingTask());
        thread.start();

        Thread.sleep(100);
        timeConsumingTask.cancel();
    }
}

class TimeConsumingTask implements Runnable{
    private  boolean toCancel = false;
    public void run(){
        while(!toCancel){
            if(doExecute()){
                break;
            }
        }
        if(toCancel){
            System.out.println("Task was canceled.");
        } else{
            System.out.println("Task done.");
        }
    }

    private boolean doExecute(){
        boolean isDone = false;
        System.out.println("executing ...");

        Tools.randomPause(50);

        return isDone;
    }

    public void cancel(){
        toCancel = true;
        System.out.println(this + " canceled.");
    }
}
