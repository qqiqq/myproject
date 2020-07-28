package classloading;

public class Test {
    static {
        i=0;
//        System.out.print(i);
    }
    static int i = 1;

    static class par{
        public static int A=1;
        static {
            A=2;
            System.out.println("init field A! ");
        }
    }
    static class sub extends par{
        public static int B = A;
    }

    public static void main(String[] args){
        System.out.print(sub.B);
    }
}
