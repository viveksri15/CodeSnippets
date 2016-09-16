package algos.painter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by viveksrivastava on 09/06/16.
 */
class House {
	private final int houseNumber;
	private final Map<Integer, Integer> costMap;

	public House(int houseNumber, Map<Integer, Integer> costMap) {
		this.houseNumber = houseNumber;
		this.costMap = costMap;
	}

	public int getHouseNumber() {
		return houseNumber;
	}

	public int getCost(int color) {
		return costMap.get(color);
	}
}

public class Painter {

	private int dp[][];

	public static void main(String[] args) {
		Painter painter = new Painter();

		House[] houses = new House[3];

		Map<Integer, Integer> costMap = new HashMap<>();
		costMap.put(0, 10);
		costMap.put(1, 5);
		costMap.put(2, 10);
		houses[0] = new House(0, costMap);


		costMap = new HashMap<>();
		costMap.put(0, 5);
		costMap.put(1, 15);
		costMap.put(2, 10);
		houses[1] = new House(1, costMap);

		costMap = new HashMap<>();
		costMap.put(0, 1);
		costMap.put(1, 5);
		costMap.put(2, 10);
		houses[2] = new House(2, costMap);

		int minCost = painter.getMinCost(houses, 3);
		System.out.println(minCost);

	}

	public int getMinCost(House[] houses, int colors) {

		//[3][3]

		dp = new int[houses.length][colors];

		for (int i = 0; i < dp.length; i++)
			Arrays.fill(dp[i], 0);

		if (houses == null || houses.length == 0)
			return 0;

		return getCost(houses, 0, colors, -1);
	}

	private int getCost(House[] houses, int i, int colors, int prevColor) {


		if (i >= houses.length)
			return 0;

		//-1
		if (prevColor > -1) {
			if (dp[i][prevColor] > 0)
				return dp[i][prevColor];
		}

		int sum = Integer.MAX_VALUE;

		for (int k = 0; k < colors; k++) {

			if (k == prevColor)
				continue;

			int cost = houses[i].getCost(k) + getCost(houses, i + 1, colors, k);

			sum = Math.min(sum, cost);
		}

		if (prevColor >= 0)
			dp[i][prevColor] = sum;

		return sum;
	}
}