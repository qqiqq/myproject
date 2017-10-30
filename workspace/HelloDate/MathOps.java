//: c03:MathOps.java
// Demonstrates the mathematical operators.
import java.util.*;

public class MathOps {
	// Create a shorthand
	static void prt(String s){
		System.out.println(s);
	}
	
	//shorthand to print
	static void pInt(int i,String s){
		prt(s + " = " + i);
	}
	static void pFlt(float f,String s){
		prt(s + " = " + f);
	}
	
	public static void main(String[] args){
		//Create a random number
		//Seeds with current time by default;
		Random rand = new Random();
		int i,j,k;
		//'%' limits max num value to 99;
		j = rand.nextInt() % 100;
		k = rand.nextInt() % 100;
		pInt(j,"j");pInt(k,"k");
		pInt(j++,"j++");pInt(++k,"++k");
		pInt(++j,"++j");pInt(++k,"++k");
		pInt(j,"j");pInt(++k,"++k");
		i = j + k;pInt(i,"j + k");
		i = j - k;pInt(i,"j - k");
		i = j / k;pInt(i,"j / k");
		i = j * k;pInt(i,"j * k");
		i = j % k;pInt(i,"j % k");
		j %= k; pInt(j,"j %= k");
		// Floating-point number tests:
		float u,v,w; // applies to doubles, too
		v = rand.nextFloat();
		w = rand.nextFloat();
		pFlt(v,"v");pFlt(w,"w");
		u = v + w; pFlt(u, "v + w");
		u = v - w; pFlt(u, "v - w");
		u = v * w; pFlt(u, "v * w");
		u = v / w; pFlt(u, "v / w");
		// the following also works for
		// char, byte, short, int, long,
		// and double:
		u += v; pFlt(u,"u += v");
		u -= v; pFlt(u,"u -= v");
		u *= v; pFlt(u,"u *= v");
		u /= v; pFlt(u,"u /= v");
	}
}///:~
