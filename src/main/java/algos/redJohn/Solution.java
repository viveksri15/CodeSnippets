package algos.redJohn;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by viveksrivastava on 03/05/16.
 */
public class Solution {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int cases = scanner.nextInt();
		RedJohn redJohn = new RedJohn();
		for (int i = 0; i < cases; i++) {
			int primes = redJohn.find(scanner.nextInt());
			System.out.println(primes);
		}
	}
}

class RedJohn {

	private static Set<Integer> primeSet = new HashSet<>();
	private long[] p;

	public int find(int n) {
		p = new long[n];
		for (int i = 0; i < n; i++)
			p[i] = -1;
		long numberOfWays = numberOfWays(0, n);
//		System.out.println("numberOfWays = " + numberOfWays);
		return getNumberOfPrimes(numberOfWays);
	}

	private int getNumberOfPrimes(long numberOfWays) {
		int sum = 0;
		for (int i = 2; i <= numberOfWays; i++) {
			boolean prime = isNotPrime(i);
			if (!prime)
				sum += 1;
		}
		return sum;
	}

	private boolean isNotPrime(int num) {
		if (primeSet.contains(num))
			return false;

		int sqRoot = (int) Math.sqrt(num);
		for (int i = 2; i <= sqRoot; i++) {
			if (i * (num / i) == num) {
				return true;
			}
		}
		primeSet.add(num);
		return false;
	}

	private long numberOfWays(int index, int limit) {
		if (limit == 0)
			return 0;
		if (index == limit)
			return 1l;
		if (index > limit)
			return 0l;

		if (p[index] != -1)
			return p[index];

		long ar1 = numberOfWays(index + 1, limit);
//		System.out.println((index + 1) + "=" + ar1);
		long ar2 = numberOfWays(index + 4, limit);
//		System.out.println((index + 4) + "=" + ar2);
		if (index + 1 < limit)
			p[index + 1] = ar1;
		if (index + 4 < limit)
			p[index + 4] = ar2;
		return ar1 + ar2;
	}
}