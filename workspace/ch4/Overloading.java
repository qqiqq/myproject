package ch4;
import java.util.*;

class tree{
	int height;
	tree(){
		prt("planting a seeding");
		height = 0;
	}
	tree( int i){
		prt("Creating new Tree that is " + i + " feet tall");
		height = i;
	}
	void info(){
		prt("Tree is "+height+" feet tall");
	}
	void info(String s){
		prt(s+": Tree is "+height+" feet tall");
	}
	static void prt(String s){
		System.out.println(s);
	}
}
public class Overloading {
	public static void main(String[] args){
		for (int i = 0; i<5; i++){
			tree t = new tree(i);
			t.info();
			t.info("overload method");
		}
		new tree();
	}

}
