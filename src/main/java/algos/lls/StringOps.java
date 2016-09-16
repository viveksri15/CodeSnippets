package algos.lls;

import java.util.Arrays;

/**
 * Created by viveksrivastava on 13/06/16.
 */
public class StringOps {
	private int[][] dp;

	public static void main(String[] args) {
		StringOps stringOps = new StringOps();
		int len = stringOps.getLongestSubSequence("axbycwarpbqc");
		System.out.println("len = " + len);
	}

	public int getLongestSubSequence(String s) {
		if (s == null || "".equals(s) || s.length() == 1)
			return 0;
		return getLLS(s, 0, 1);
	}

	private int getLLS(String s, int i, int j) {
		if (dp == null) {
			dp = new int[s.length()][];
			for (int k = 0; k < s.length(); k++) {
				dp[k] = new int[s.length()];
				Arrays.fill(dp[k], 0);
			}
		}

		if (i == s.length() || j == s.length())
			return 0;

		if (dp[i][j] > 0)
			return dp[i][j];

		char[] c = s.toCharArray();

		int res = 0;
		if (i != j && c[i] == c[j])
			res = 1 + getLLS(s, i + 1, j + 1);
		else
			res = Math.max(getLLS(s, i + 1, j), getLLS(s, i, j + 1));

		dp[i][j] = res;

		return res;
	}
}
