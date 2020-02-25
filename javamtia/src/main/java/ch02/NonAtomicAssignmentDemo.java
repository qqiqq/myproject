package ch02;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class NonAtomicAssignmentDemo implements Runnable {
    static long value = 0;
    private final long valueToSet;

    public NonAtomicAssignmentDemo(long valueToSet){
        this.valueToSet = valueToSet;
    }
    public static void main(String[] args){
        Thread updateThread1 = new Thread(new NonAtomicAssignmentDemo(0L));
        Thread updateThread2 = new Thread(new NonAtomicAssignmentDemo(-1L));

        PrintStream ps = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        });

        updateThread1.start();
        updateThread2.start();

        long snapshot;
        while(0 == (snapshot = value) || -1 == snapshot){
            ps.print(snapshot);
        }

        System.err.printf("Unexpected data: %d(0x%016x)", snapshot, snapshot);
        ps.close();
        System.exit(0);

    }

    public void run(){
        for(;;){
            value = valueToSet;
        }
    }
}
