package algos.derrangements;

import java.util.Arrays;

/**
 * Created by viveksrivastava on 05/05/16.
 */
public class Solution {
	public static void main(String[] args) {
		Derrangements derrangements = new Derrangements();

		int count = derrangements.count(5);
		derrangements.derangement(5);
		System.out.println("count = " + count);
	}
}

class Derrangements {

	public void derangement(int n) {
		derangement(new int[n], new boolean[n], 0);
	}

	public void derangement(int[] a, boolean[] used, int level) {
		if (level == a.length) {
			System.out.println(Arrays.toString(a));
		}
		for (int i = 0; i < a.length; i++) {
			if (used[i] || i == level) continue;
			used[i] = true;
			a[level] = i + 1;
			derangement(a, used, level + 1);
			used[i] = false;
		}
	}

	int count(int n) {
		if (n < 2)
			return 0;
		if (n == 2)
			return 1;
		return (n - 1) * (count(n - 1) + count(n - 2));
	}
}