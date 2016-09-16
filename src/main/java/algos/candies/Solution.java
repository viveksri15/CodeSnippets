package algos.candies;

import java.util.Scanner;

class CandieSolver {

	private int[] rankings;

	public CandieSolver(int[] rankings) {
		this.rankings = rankings;
	}

	public int calculate() {
		int m = rankings.length;
		int[] upwardTrend = new int[m];
		int[] downwardTrend = new int[m];
		int candies = 0;
		upwardTrend[0] = 1;
		for (int i = 1; i < m; i++)
			upwardTrend[i] = rankings[i] > rankings[i - 1] ? upwardTrend[i - 1] + 1 : 1;
		downwardTrend[m - 1] = 1;
		for (int i = m - 2; i >= 0; i--)
			downwardTrend[i] = rankings[i] > rankings[i + 1] ? downwardTrend[i + 1] + 1 : 1;
		for (int i = 0; i < m; i++)
			candies += Math.max(upwardTrend[i], downwardTrend[i]);
		return candies;
	}
}

public class Solution {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int rankCount = scanner.nextInt();
		int[] rankings = new int[rankCount];
		for (int i = 0; i < rankCount; i++)
			rankings[i] = scanner.nextInt();
		CandieSolver solver = new CandieSolver(rankings);
		System.out.println(solver.calculate());
	}
}