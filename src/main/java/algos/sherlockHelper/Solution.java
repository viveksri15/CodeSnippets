package algos.sherlockHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Solution {
	public static void main(String[] args) {
		SherlockHelper sher = new SherlockHelper();
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		for (int i = 0; i < cases; i++) {
			sher.print(sc.nextInt());
		}
	}
}

class SherlockHelper {
	public void print(int n) {
		boolean found = true;
		Map<Integer, Integer> counts = new HashMap<>();
		counts.put(5, n);

		counts.put(3, 0);
		while (!validate(counts)) {
			int value = counts.get(5) - 5;
			if (value < 0) {
				found = false;
				break;
			}
			counts.put(5, value);
			counts.put(3, counts.get(3) + 5);
		}
		if (found) {
			for (int j = 0; j < counts.get(5); j++)
				System.out.print(5);
			for (int j = 0; j < counts.get(3); j++)
				System.out.print(3);
			System.out.println();
		} else
			System.out.println(-1);
	}

	private boolean validate(Map<Integer, Integer> counts) {
		return (counts.get(5) % 3 == 0 && counts.get(5) % 3 == 0);
	}
}