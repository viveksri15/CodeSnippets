package tests;

/**
 * Created by viveksrivastava on 29/05/16.
 */
public class SwapBits {
	public static void main(String[] args) {
		System.out.println(Integer.toBinaryString(0xaaaaaaaa));
		System.out.println(Integer.toBinaryString(0x55555555));
		int n = Integer.parseInt("01111111111111111111110011010101", 2);

		int nL = n << 1;
		int nR = n >> 1;
		int nLR = (n & nL) | (n & nR);

		System.out.println(Integer.toBinaryString(~n & ~nLR));
	}
}
