package algos.subStringDiff;

import java.util.Arrays;

/**
 * Created by viveksrivastava on 19/05/16.
 */
public class Solution {
	public static void main(String[] args) {
		SubString subString = new SubString("hello".toCharArray(), "helow".toCharArray(), 2);
		int length = subString.solve();
		System.out.println(length);
	}
}

class SubString {

	private char[] strA, strB;
	private int maxMismatch;
	private int[][][] dp;

	public SubString(char[] strB, char[] strA, int maxMismatch) {
		this.strB = strB;
		this.strA = strA;
		this.maxMismatch = maxMismatch;
		dp = new int[strA.length][][];
		for (int i = 0; i < strA.length; i++) {
			dp[i] = new int[strA.length][];
			for (int j = 0; j < strA.length; j++) {
				dp[i][j] = new int[strA.length];
				Arrays.fill(dp[i][j], -1);
			}
		}
	}

	public int solve() {
		int maxLength = 0;
		for (int l = 1; l <= strA.length; l++) {
			for (int i = strA.length - l; i >= 0; i -= l) {
				for (int j = strA.length - l; j >= 0; j -= l) {
					int count = diff(i, j, l);
					System.out.println(i + "," + j + "," + l + "=" + count);
					if (count <= maxMismatch && l > maxLength)
						maxLength = l;
				}
			}
		}
		return maxLength;
	}

	public int diff(int x, int y, int l) {

//		int dpDiff = getFromDP(x, y, l);
//		if (dpDiff > -1)
//			return dpDiff;

		if (x < strA.length && y < strB.length && strA[x] == strB[y])
			return diff(x + 1, y + 1, l - 1);

		int diff = 0;
		for (int i = x; i <= x + l - 1; i++) {
			boolean found = false;
			for (int j = y; j <= y + l - 1; j++) {
				if (strA[i] == strB[j]) {
					found = true;
					break;
				}
			}
			if (!found)
				diff++;
		}

		addToDP(x, y, l, diff);

		return diff;
	}

	private int getFromDP(int x, int y, int l) {
		if (l <= 0)
			return 0;
		return dp[x][y][l - 1];
	}

	private void addToDP(int x, int y, int l, int diff) {
		if (l > 0)
			dp[x][y][l - 1] = diff;
	}
}
