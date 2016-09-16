package algos.largestSubsequentProduct;

/**
 * Created by viveksrivastava on 13/06/16.
 */
public class IntOps {

	public static void main(String[] args) {
		IntOps intOps = new IntOps();
		Integer lsp = intOps.lsp(new int[]{2, 2});
		System.out.println("lsp = " + lsp);
	}

	public Integer lsp(int[] arr) {
		if (arr == null || arr.length == 0)
			return null;

		if (arr.length == 1)
			return arr[0];

		int max = 1;

		int neg = 1, pos = 1;

		for (int n : arr) {
			System.out.println(pos + "," + neg + "," + n + "," + max);
			if (n == 0) {

				max = Math.max(max, neg);
				max = Math.max(max, pos);
				neg = 1;
				pos = 1;

			} else {
				if (n > 0) {
					pos = pos * n;
					neg = neg * n;
				} else {
					neg = neg * n;
					pos = pos * n;
					int a = neg;
					neg = pos;
					pos = a;
				}
			}
			max = Math.max(pos, max);
		}

		return max;
	}
}
