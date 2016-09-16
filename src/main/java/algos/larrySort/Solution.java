package algos.larrySort;

/**
 * Created by viveksrivastava on 08/05/16.
 */

import java.util.Scanner;

interface Sort {
	boolean check(int[] arr);
}

class Solution {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Sort sort = new RotateSort();
		int t = sc.nextInt();
		for (int i = 0; i < t; i++) {
			int count = sc.nextInt();
			int[] arr = new int[count];
			for (int j = 0; j < count; j++)
				arr[j] = sc.nextInt();
			boolean isSortable = sort.check(arr);
			if (isSortable)
				System.out.println("YES");
			else
				System.out.println("NO");
		}
	}
}

class RotateSort implements Sort {
	public boolean check(int[] arr) {
		for (int i = arr.length - 3; i >= 0; i--) {
			sort(arr, i);
			checkRotate(arr, i);
		}
		for (int i = 0; i < arr.length - 1; i++)
			if (arr[i] > arr[i + 1])
				return false;
		return true;
	}

	private void sort(int[] arr, int i) {
		if (i >= arr.length - 2)
			return;
		checkRotate(arr, i);
//		System.out.println(i + " " + algos.Arrays.toString(arr));
		sort(arr, i + 1);
	}

	private void checkRotate(int[] arr, int i) {
		int a = arr[i];
		int b = arr[i + 1];
		int c = arr[i + 2];
		if (c <= a && c <= b) {
			rotate(arr, i);
			rotate(arr, i);
		} else if (b <= a && b <= c)
			rotate(arr, i);
	}

	private void rotate(int[] arr, int i) {
		int t1 = arr[i];
		arr[i] = arr[i + 1];
		arr[i + 1] = arr[i + 2];
		arr[i + 2] = t1;
	}
}