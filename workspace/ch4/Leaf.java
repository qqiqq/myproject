package ch4;

public class Leaf {
	int i = 0;
	Leaf increament(){
		i++;
		return this;
	}
	void print(){
		System.out.println("i = "+i);
	}
	public static void main(String[] args){
		Leaf x = new Leaf();
		x.increament().increament().increament().print();
	}

}
