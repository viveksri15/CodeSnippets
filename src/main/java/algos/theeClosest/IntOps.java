package algos.theeClosest;

import java.util.Arrays;

/**
 * Created by viveksrivastava on 13/06/16.
 */
public class IntOps {
	public static void main(String[] args) {
		IntOps intOps = new IntOps();
		int[] threeSum = intOps.getThreeSum(new int[]{-1, -2, -3, 3, 1, 3}, 7);
		System.out.println("threeSum = " + Arrays.toString(threeSum));
	}

	public int[] getThreeSum(int[] arr, int t) {
		if (arr == null || arr.length < 3)
			return arr;
		java.util.Arrays.sort(arr);
		
		int minSum = Integer.MAX_VALUE;
		int[] elements = new int[3];
		for (int i = 0; i < arr.length - 2; i++) {
			int j = i + 1;
			int k = arr.length - 1;
			int target = t - arr[i];
			int[] minelements = new int[3];
			minelements[0] = arr[i];
			int sum = Integer.MAX_VALUE;

			while (k > j) {
				int nsum1 = arr[j] + arr[k];
				if (Math.abs(target - nsum1) < Math.abs(target - sum)) {
					minelements[1] = arr[j];
					minelements[2] = arr[k];
					sum = nsum1;
				}

				if (nsum1 == target) {
					break;
				} else if (nsum1 < target) {
					j++;
				} else {
					k--;
				}
			}

			if (Math.abs(t - sum - arr[i]) < Math.abs(t - minSum)) {
				minSum = sum + arr[i];
				elements[0] = minelements[0];
				elements[1] = minelements[1];
				elements[2] = minelements[2];
			}
		}
		return elements;
	}
}