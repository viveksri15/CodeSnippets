package algos.stockMaximize;

import java.util.Scanner;

/**
 * Created by viveksrivastava on 02/05/16.
 */
public class Solution {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int testCases = sc.nextInt();
		for (int i = 0; i < testCases; i++) {
			FindProfit findProfit = new FindProfit();
			int count = sc.nextInt();
			int[] arr = new int[count];
			for (int j = 0; j < count; j++)
				arr[j] = sc.nextInt();
			long profit = findProfit.findProfit(arr);
			System.out.println(profit);
		}
	}
}

class FindProfit {

	public long findProfit(int[] price) {
		int[] maxArray = new int[price.length];
		maxArray[price.length - 1] = 0;
		int max = price[price.length - 1];

		for (int i = price.length - 2; i >= 0; i--) {
			maxArray[i] = max;
			if (price[i] > max)
				max = price[i];
		}

		long sum = 0;
		for (int i = 0; i < price.length; i++)
			if (maxArray[i] > price[i])
				sum += maxArray[i] - price[i];
		return sum;
	}
}
