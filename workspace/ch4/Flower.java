package ch4;

public class Flower {
int petalCount = 0;
String s = new String("null");
Flower(int petals) {
petalCount = petals;
System.out.println(
"Constructor w/ int arg only, petalCount= "
+ petalCount);
}
Flower(String ss) {
System.out.println(
"Constructor w/ String arg only, s=" + ss);
s = ss;
}
Flower(String s, int petals) {
this(petals);
//! this(s); // Can't call two!
this.s = s; // Another use of "this"(ע�������this.s ���������s ���Ⱥź����s �ǲ���s �����ǲ�ͬ�Ŀ��������System.out.println(this.s)��System.out.printlln(s)��ȷ��һ����ӡnull һ����ӡhi)
System.out.println("String & int args");
}
Flower() {
this("hi", 47);
System.out.println(
"default constructor (no args)");
}
void print() {
//! this(11); // Not inside non-constructor!
	System.out.println(
			"petalCount = " + petalCount + " s = "+ s);
			}
			public static void main(String[] args) {
			Flower x = new Flower();
			x.print();
			}
			} ///:~(