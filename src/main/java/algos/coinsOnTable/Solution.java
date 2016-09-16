package algos.coinsOnTable;

import java.util.Scanner;

/**
 * Created by viveksrivastava on 08/05/16.
 */
public class Solution {
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int m = scanner.nextInt();
		int k = scanner.nextInt();

		String[][] grid = new String[n][m];
		for (int i = 0; i < n; i++) {
			String next = scanner.next();
			int j = 0;
			for (char c : next.toCharArray())
				grid[i][j++] = c + "";
		}

		MoveManager moveManager = new MoveManager(grid.length, grid[0].length, grid, k);
		moveManager.tryMoves(0, 0, k, 0);
		int minMoves = moveManager.getMinOps();
		System.out.println(minMoves);
	}
}

class MoveManager {

	private final int n, m;
	private final String[][] currPos;
	private int maxMoves;
	private int[][][] dpMinOpsCount;
	private int minOps = -1;

	public MoveManager(int n, int m, String[][] currPos, int k) {
		this.n = n;
		this.m = m;
		this.currPos = currPos;
		maxMoves = k;
		dpMinOpsCount = new int[n][m][k + 1];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				for (int ki = 0; ki < k + 1; ki++)
					dpMinOpsCount[i][j][ki] = -2;
	}

	public int getMinOps() {
		/*for (int i = 0; i < n; i++)
//		int i = 0;
			for (int j = 0; j < m; j++)
				for (int ki = 0; ki < maxMoves + 1; ki++)
					if (dpMinOpsCount[i][j][ki] > -1)
						System.out.println("dpMinOpsCount[" + i + "][" + j + "][" + ki + "] = " + dpMinOpsCount[i][j][ki]);*/
		return minOps;
	}

	public int tryMoves(int i, int j, int k, int count) {

		if (i < 0 || i >= n)
			return -1;

		if (j < 0 || j >= m)
			return -1;

		if (minOps > -1 && count >= minOps)
			return -1;

		String situation = currPos[i][j];

		if ("*".equals(situation)) {
//			System.out.println("reached in " + count);
			if (this.minOps > count || this.minOps == -1)
				this.minOps = count;
			return 0;
		}

		/*{
			int minOps = getDPGridValue(i, j, k, situation);
			if (i == 1 && j == 2)
				System.out.println("minOps = " + minOps);
			if (minOps > -2) {
				if (minOps > -1 && (minOps + count < this.minOps || this.minOps == -1)) {
					this.minOps = minOps + count;
					System.out.println("*reached in " + this.minOps);
				}
				return minOps;
			}
		}*/

		if (k <= 0)
			return -1;

		int minOps = moveOnSituation(situation, i, j, k - 1, count);

		putDPGridValue(i, j, k, minOps);

		if (minOps == -1) {
//			minOps = getMinOps(i, j, k, count, situation, minOps, "U");
			minOps = getMinOps(i, j, k, count, situation, minOps, "R");
			minOps = getMinOps(i, j, k, count, situation, minOps, "D");
//			minOps = getMinOps(i, j, k, count, situation, minOps, "L");
		}

//		if (i == 1 && (j == 1 || j == 2))
//			System.out.println(i + "," + j + "," + k + "," + count + "," + situation + " " + minOps);

		return minOps;
	}

	private int getMinOps(int i, int j, int k, int count, String situation, int minOps, String nextSituation) {

		if (!nextSituation.equals(situation)) {
			int u = moveOnSituation(nextSituation, i, j, k - 1, count + 1);
			if (u > minOps)
				minOps = u + 1;
			putPGGridValue(i, j, k, u);
		}
		return minOps;
	}

	private void putPGGridValue(int i, int j, int k, int u) {
		if (u > -1)
			putDPGridValue(i, j, k, u + 1);
		else
			putDPGridValue(i, j, k, -1);
	}

	private void putDPGridValue(int i, int j, int k, int minMove) {
		if (minMove < dpMinOpsCount[i][j][k] || dpMinOpsCount[i][j][k] == -2)
			dpMinOpsCount[i][j][k] = minMove;
	}

	private int getSituationIn(String situation) {
		int sitI = 0;
		if ("D".equals(situation))
			sitI = 1;
		else if ("L".equals(situation))
			sitI = 2;
		else if ("R".equals(situation))
			sitI = 3;
		return sitI;
	}

	private int getDPGridValue(int i, int j, int k, String situation) {
//		int sitI = getSituationIn(situation);
		int minDPVal = dpMinOpsCount[i][j][k];
		for (int index = 0; index < k; index++)
			if (minDPVal < dpMinOpsCount[i][j][index] && dpMinOpsCount[i][j][index] > -1)
				minDPVal = dpMinOpsCount[i][j][index];
		return minDPVal;
	}

	int moveOnSituation(final String situation, int i, int j, int k, int count) {
//		System.out.println(i + "," + j + "," + k + "," + count + "," + situation);
		switch (situation) {
			case "U":
				return tryMoves(i - 1, j, k, count);
			case "L":
				return tryMoves(i, j - 1, k, count);
			case "D":
				return tryMoves(i + 1, j, k, count);
			default:
				return tryMoves(i, j + 1, k, count);
		}
	}
}