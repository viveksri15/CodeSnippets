package algos.closestTo3;

import java.util.Arrays;

/*
    2,2,2,2,2,2,2
    8

    -1,-2,-3,4,5,9

    3
*/
public class Solution {
	public static void main(String[] args) {
		int ans = new Solution().threeSumClosest(new int[]{0, 1, 2}, 1);
		System.out.println("ans = " + ans);
	}

	public int threeSumClosest(int[] nums, int target) {
		Arrays.sort(nums);
		int n = nums.length;

		int minVal = Integer.MAX_VALUE;
		for (int i = 0; i < n; i++) {
			int k = n - 1;
			int minDiff = Integer.MAX_VALUE;
			int lookup = target - nums[i];
			for (int j = i + 1; ; ) {

				if (k == i) {
					k--;
					continue;
				}
				if (j == n || k < 0)
					break;

				int found = nums[j] + nums[k];
				int diff = lookup - found;

				if (diff == 0)

					return target;

				if (Math.abs(diff) < Math.abs(target - minDiff)) {
					minDiff = found + nums[i];
				}

				if (lookup < found)
					k--;
				else
					j++;
			}
			if (Math.abs(target - minVal) > Math.abs(target - minDiff))
				minVal = minDiff;
		}
		return minVal;
	}
}