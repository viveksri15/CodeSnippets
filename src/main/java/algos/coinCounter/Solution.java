package algos.coinCounter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by viveksrivastava on 30/04/16.
 */
public class Solution {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int m = scanner.nextInt();
		int[] coins = new int[m];
		for (int i = 0; i < coins.length; i++)
			coins[i] = scanner.nextInt();
		CoinCounter coinCounter = new CoinCounter(coins);
		long coin = coinCounter.countCoin(n, 0);
		System.out.println(coin);
	}
}

class CoinCounter {
	private int[] coins;
	private long[][] coinDP;
	private Map<Integer, Integer> coinPosMap = new HashMap<>();

	public CoinCounter(int[] coins) {
		Arrays.sort(coins);
		this.coins = coins;
		for (int i = 0; i < coins.length; i++)
			coinPosMap.put(coins[i], i);
	}

	public long countCoin(int n, int minCoin) {
		if (coinDP == null) {
			coinDP = new long[n][coins.length];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < coins.length; j++)
					coinDP[i][j] = -1;
			}
		}
		if (coins == null)
			return 0;
		if (n == 0) {
			return 1;
		}
		if (n < 0)
			return 0;

		long sum = 0l;
		Integer minCoinPos = coinPosMap.get(minCoin);
		if (minCoinPos == null)
			minCoinPos = 0;

		for (int i = minCoinPos; i < coins.length; i++) {
			int coin = coins[i];
			if (coin >= minCoin) {
				int newN = n - coin;
				if (newN == 0)
					sum += 1;
				else {
					Integer coinPos = coinPosMap.get(coin);
					if (newN > 0) {
						long countCoin = coinDP[(newN)][coinPos];
						if (countCoin == -1)
							countCoin = countCoin(newN, coin);
						sum += countCoin;
						coinDP[newN][coinPos] = countCoin;
					}
				}
			}
		}
		return sum;
	}
}
