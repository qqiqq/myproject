package classloading;

public class FieldResolution {
    interface i0{
        int A = 0;
    }
    interface i1 extends i0{
        int A = 1;
    }
    interface i2{
        int A = 2;
    }

    static class par implements i1{
        public static int A=3;
    }
    static class sub extends par implements i2{
        public static int A = 4;
    }

    public static void main(String[] args){
        System.out.println(sub.A);
    }
}

