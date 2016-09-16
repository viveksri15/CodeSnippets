package algos.biggerGreater;

import java.util.Arrays;
import java.util.Scanner;

public class Solution {

	private static final String NA = "no answer";

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int count = sc.nextInt();
		sc.nextLine();
		for (int i = 0; i < count; i++) {
			printNext(sc.nextLine());
		}
	}

	private static void printNext(String str) {
		if (str.length() == 1) {
			System.out.println(NA);
			return;
		}

		char[] cArr = str.toCharArray();
		for (int i = cArr.length - 2; i >= 0; i--) {
//			System.err.println("cArr = " + new String(cArr));
			Arrays.sort(cArr, i + 1, cArr.length);
//			System.err.println("cArr1 = " + new String(cArr));
			int newPosition = Arrays.binarySearch(cArr, i + 1, cArr.length, cArr[i]);
//			System.err.println("newPosition = " + newPosition + " " + cArr[i]);
			if (newPosition >= 0) {
				for (int k = newPosition; k < cArr.length; k++)
					if (cArr[k] != cArr[i]) {
						swap(cArr, k, i);
						System.out.println(new String(cArr));
						return;
					}
			}
			if (newPosition < 0 && newPosition != -(cArr.length + 1)) {
				newPosition = (-newPosition) - 1;
				swap(cArr, i, newPosition);
				System.out.println(new String(cArr));
				return;
			}
		}
		System.out.println(NA);
	}

	private static void swap(char[] cArr, int i, int j) {
		char temp = cArr[i];
		cArr[i] = cArr[j];
		cArr[j] = temp;
	}
}