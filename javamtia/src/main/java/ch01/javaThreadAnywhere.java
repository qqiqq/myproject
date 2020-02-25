package ch01;

import com.sun.tools.corba.se.idl.toJavaPortable.Helper;

public class javaThreadAnywhere {
    public static void main(String[] args){
        Thread currentThread = Thread.currentThread();

        String currentThreadName = currentThread.getName();
        System.out.printf("The main method was executed by thread: %s", currentThreadName);
        System.out.println();
        Thread helper1 = new Thread(new Helper("Java Thread AnyWhere I"));
        helper1.run();
        Thread helper2 = new Thread(new Helper("Java Thread AnyWhere II"));
        helper2.start();

    }

    static class Helper implements Runnable{
        private final String msg;
        public Helper(String msg){
            this.msg = msg;
        }
        private void doSomething(String msg){
            Thread currentThread = Thread.currentThread();
            String currentThreadName = currentThread.getName();
            System.out.printf("The main method was executed by thread: %s", currentThreadName);
            System.out.println();
            System.out.println("Do something with " + msg);
        }

        public void run(){
            doSomething(msg);
        }
    }

}
