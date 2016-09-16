package algos.twoDiff;

import java.util.*;

public class Solution {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int t = scanner.nextInt();
		for (int i = 0; i < t; i++) {
			int amount = scanner.nextInt();
			int len = scanner.nextInt();
			int[] stack = new int[len];
			Map<Integer, List<Integer>> priceDiff = new HashMap<>();
			for (int j = 0; j < stack.length; j++) {
				stack[j] = scanner.nextInt();
				int diff = amount - stack[j];
				if (diff > 0) {
					List<Integer> list = priceDiff.get(diff);
					if (list == null) {
						list = new ArrayList<>();
						priceDiff.put(diff, list);
					}
					list.add(j);
				}
			}
			boolean found = false;
			for (int j = 0; j < stack.length; j++) {
				List<Integer> list = priceDiff.get(stack[j]);
				if (list != null) {
					for (int c : list) {
						if (c != j) {
							int a = Math.min(c, j);
							int b = Math.max(c, j);
							System.out.println((a + 1) + " " + (b + 1));
							found = true;
							break;
						}
					}
					if (found)
						break;
				}
			}
		}
	}
}