package algos.sherlockPair;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by viveksrivastava on 19/05/16.
 */
public class Solution {
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		int t = scanner.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scanner.nextInt();
			Integer[] a = new Integer[n];
			for (int j = 0; j < n; j++) {
				a[j] = scanner.nextInt();
			}
			Arrays.sort(a);
			long count = 1, sum = 0;
			for (int j = 1; j <= n - 1; j++) {
				if (!a[j].equals(a[j - 1])) {
					count = 1;
				} else {
					sum += count * 2;
					count++;
				}

			}
			System.out.println(sum);
		}
	}
}
