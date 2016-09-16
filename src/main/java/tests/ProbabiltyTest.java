package tests;

import java.util.HashMap;
import java.util.Map;

public class ProbabiltyTest {
	private static Map<Integer, Map<Long, Long>> dp = new HashMap<>();

	public static void main(String[] args) {
		long count = countOfSumMod7(0, 0, 66);
		System.out.println("probabilty = " + (count / Math.pow(2, 66)));
	}

	public static long countOfSumMod7(int i, long sum, int n) {
		Map<Long, Long> dpMap = dp.get(i);
		if (dpMap != null) {
			Long s = dpMap.get(sum);
			if (s != null)
				return s;
		}
		if (i >= n) {
			if (sum % 7 == 0 && sum > 0)
				return 1;
			return 0;
		}
		long s = countOfSumMod7(i + 1, sum + i + 1, n) + countOfSumMod7(i + 1, sum, n);
		if (dpMap == null) {
			dpMap = new HashMap<>();
			dp.put(i, dpMap);
		}
		dpMap.put(sum, s);
		return s;
	}
}
