package ch01;

public class sleepTimer {
    private static int count;

    public static void main(String[] args){
        count = args.length>=1 ? Integer.valueOf(args[0]) : 60;
        int remaining;
        while(true){
            remaining = countDown();
            if(remaining == 0){
                break;
            } else{
                System.out.println("Remaining " + count + " second(s)");
            }

            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        System.out.println("Done!");
    }

    private static int countDown(){
        count --;
        return count;
    }
}
