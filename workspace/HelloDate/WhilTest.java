//: c03:WhileTest.java
// Demonstrates the while loop.

public class WhilTest {
	public static void main(String[] args){
		double r = 0;
		while(r < 0.99d){
			System.out.println(r);
			r = Math.random();
		}
		System.out.println(r);
	}
}


