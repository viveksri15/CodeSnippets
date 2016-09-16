package tests;

interface A {
	String getA();
}

/**
 * Created by viveksrivastava on 30/06/16.
 */
public class GSonTest {
	public static void main(String[] args) {
		String s = new String();
	}
}

class B implements A {

	@Override
	public String getA() {
		return "BB";
	}
}

class C implements A {

	@Override
	public String getA() {
		return "CC";
	}
}

class GSonO {
	private A a;
	private String xyz;
	private GSonO1 gSonO1;

	public GSonO(A a) {
		this.xyz = a.getA();
	}

	public String getXyz() {
		gSonO1 = new GSonO1(xyz);
		return a.getA();
	}
}

class GSonO1 {
	private String abc = "xx";

	public GSonO1(String x) {
		this.abc = x;
	}
}
