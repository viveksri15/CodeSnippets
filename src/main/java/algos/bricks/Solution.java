package algos.bricks;

import java.util.Scanner;

/**
 * Created by viveksrivastava on 07/05/16.
 */
public class Solution {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int t = scanner.nextInt();
		for (int i = 0; i < t; i++) {
			int len = scanner.nextInt();
			int[] stack = new int[len];
			for (int j = 0; j < stack.length; j++)
				stack[j] = scanner.nextInt();

			Score score = new Score(stack);
			Long[] score1 = score.calcMaxScore(0);

//		System.out.println("score1 = " + algos.Arrays.toString(score1));
			System.out.println(score1[1]);
		}
	}
}

class Score {
	private Long[][] dp;
	private int[] stack;

	public Score(int[] stack) {
		this.stack = stack;
		dp = new Long[stack.length][2];
	}

	public Long[] calcMaxScore(int i) {

		if (i >= stack.length)
			return new Long[]{0l, 0l};

		if (dp[i] != null && dp[i][0] != null)
			return dp[i];

		long[] forwardScores = new long[3];
		Long[] opponentScore = new Long[3];
		for (int j = 1; j <= 3; j++) {
			for (int k = 0; k < j; k++)
				forwardScores[j - 1] += getStackValue(i + k);

			Long[] takenByOpponent = calcMaxScore(i + j);
			long count = takenByOpponent[0];
			long value = takenByOpponent[1];
			opponentScore[j - 1] = value;
//			System.out.println("takenByOpponent = " + algos.Arrays.toString(takenByOpponent));
			forwardScores[j - 1] += calcMaxScore(i + j + (int) count)[1];

//			System.out.println("j = " + j + " i=" + i + " " + forwardScores[j - 1]);
		}
//		System.out.println("score2 = " + algos.Arrays.toString(forwardScores) + " " + i + " opp=" + algos.Arrays.toString(opponentScore));

		int fMax = threeMax(forwardScores[0], forwardScores[1], forwardScores[2]);
		dp[i] = new Long[]{(long) fMax + 1, forwardScores[fMax]};
		return dp[i];
	}

	public int getStackValue(int i) {
		if (i >= stack.length || i < 0)
			return 0;
		return stack[i];
	}

	int threeMax(long a, long b, long c) {
		return a >= b && a >= c ? 0 : b >= c ? 1 : 2;
	}
}
