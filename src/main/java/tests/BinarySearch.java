package tests;

import java.util.Arrays;

/**
 * Created by viveksrivastava on 11/05/16.
 */
public class BinarySearch {
	public static void main(String[] args) {
		char[] cArr = "vivekismyname".toCharArray();
		Arrays.sort(cArr);
		System.out.println("cArr = " + Arrays.toString(cArr));
		int newPosition = Arrays.binarySearch(cArr, 0, cArr.length, 'l');
		System.out.println("newPosition = " + newPosition);
	}
}
