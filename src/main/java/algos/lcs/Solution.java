package algos.lcs;

import java.util.Scanner;

/**
 * Created by viveksrivastava on 13/05/16.
 */
public class Solution {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int m = scn.nextInt();
		int[] aStr = new int[n];
		int[] bStr = new int[m];
		for (int i = 0; i < n; i++)
			aStr[i] = scn.nextInt();
		for (int i = 0; i < m; i++)
			bStr[i] = scn.nextInt();
		LCS lcs = new LCS(aStr, bStr);
		System.out.println(lcs.find(0, 0).trim());
	}
}


class LCS {
	private final int[] aStr, bStr;
	private String[][] dp;

	public LCS(int[] aStr, int[] bStr) {
		this.aStr = aStr;
		this.bStr = bStr;
		dp = new String[aStr.length + 1][bStr.length + 1];
	}

	public String find(int i, int j) {

		if (i == aStr.length || j == bStr.length) {
			return "";
		}

		if (i < aStr.length && j < bStr.length && aStr[i] == bStr[j]) {

			String s = getDP(i + 1, j + 1);
			if (s != null) {
				addToDP(i, j, aStr[i] + " " + s);
				System.err.println(i + "," + j + " dp=" + aStr[i] + " " + s);
				return aStr[i] + " " + s;
			}
			s = aStr[i] + " " + find(i + 1, j + 1);
			addToDP(i, j, s);
			System.err.println(i + "," + j + ", found=" + aStr[i] + " " + s);
			return s;
		} else {

			String s1 = getDP(i + 1, j);
			String s2 = getDP(i, j + 1);

			if (s1 == null) {
				s1 = find(i + 1, j);
				addToDP(i + 1, j, s1);
			}
			if (s2 == null) {
				s2 = find(i, j + 1);
				addToDP(i, j + 2, s2);
			}
		}
		return "";
	}

	private void addToDP(int i, int j, String s) {
		if (i == aStr.length || j == bStr.length)
			return;
		System.err.println(i + "," + j + " put=" + s);
		dp[i][j] = s;
	}

	private String getDP(int i, int j) {
		if (i == aStr.length || j == bStr.length)
			return "";
		return dp[i][j];
	}
}