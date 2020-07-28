package classloading;

public class ConstClass {
    static{
        System.out.println("ConstClass init!");
    }
    public static final String HELLOWORLD = "hello world";
}

class NotInitialization3{
    public static void main(String[] args){
        System.out.println(ConstClass.HELLOWORLD);
    }
}