package tests;

/**
 * Created by viveksrivastava on 11/05/16.
 */
public class BitShift {
	public static void main(String[] args) {
		int i = (Integer.MAX_VALUE + 1) >>> 1;
		int j = 1 + (Integer.MAX_VALUE - 1) / 2;
		System.out.println("i = " + i);
		System.out.println("j = " + j);
	}
}