package algos.wiggle;

import java.util.Arrays;

/**
 * Created by viveksrivastava on 13/06/16.
 */
public class WiggleSort {
	public static void main(String[] args) {
		WiggleSort wiggleSort = new WiggleSort();
		int[] arr = {6, 4, 3, 2, 6, 5, 4, 3, 7, 5, 4, 3};
		wiggleSort.sort(arr);
		System.out.println("arr = " + Arrays.toString(arr));
	}

	public void sort(int[] arr) {
		if (arr == null || arr.length < 3)
			return;
		boolean largeFirst = true;
		for (int i = 0; i < arr.length - 2; i++) {
			int a = Math.min(arr[i], Math.min(arr[i + 1], arr[i + 2]));
			int c = Math.max(arr[i], Math.max(arr[i + 1], arr[i + 2]));
			int b = arr[i];
			if (b == a)
				b = arr[i + 1];
			if (b == c)
				b = arr[i + 2];
			if (largeFirst) {
				arr[i] = c;
				arr[i + 1] = a;
				arr[i + 2] = b;
			} else {
				arr[i] = a;
				arr[i + 1] = c;
				arr[i + 2] = b;
			}
			largeFirst = !largeFirst;
		}
	}
}