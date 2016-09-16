package algos.median;

import java.util.Arrays;

/**
 * Created by viveksrivastava on 07/06/16.
 */
public class Solution {

	public static void main(String[] args) throws Exception {
		int[] nums1 = new int[]{1, 1};
		int[] nums2 = new int[]{1, 1};
		ArrayPair arrayPair = new ArrayPair(nums1, nums2);
		System.out.println("medianSortedArrays = " + arrayPair.get(3));
	}
}

class ArrayPair {
	private int[] a, b;

	public ArrayPair(int[] a, int[] b) throws Exception {
		if (a == null || b == null)
			throw new Exception("Bad Data");
		this.a = a;
		this.b = b;
	}

	public int length() {
		return a.length + b.length;
	}

	public int get(int pos) throws Exception {
		return get(0, a.length - 1, 0, b.length - 1, pos, 0, a.length - 1, 0, b.length - 1);
	}

	private int get(int as, int ae, int bs, int be, int pos, int asb, int aeb, int bsa, int bea) {
		int am = as + (ae - as) / 2;
		int bm = bs + (be - bs) / 2;

		System.out.println(as + "," + ae + "," + bsa + "," + bea + "," + bs + "," + be + "," + asb + "," + aeb);


		int aInB = bea;

		if (bsa < b.length && bea < b.length)
			aInB = find(b, a[am], bsa, bea);

		if (aInB + am == pos)
			return a[am];

		int bInA = aeb;

		if (asb < a.length && aeb < a.length)
			bInA = find(a, b[bm], asb, aeb);

		if (bInA + bm == pos)
			return b[bm];

		System.out.println(as + "," + ae + "," + bsa + "," + bea + "," + bs + "," + be + "," + asb + "," + aeb + ";" + aInB + "," + bInA);


		if (aInB + am > pos) {
			ae = am - 1;
			bea = aInB - 1;
		} else {
			as = am + 1;
			bsa = aInB + 1;
		}

		if (bInA + bm > pos) {
			be = bm - 1;
			aeb = bInA - 1;
		} else {
			bs = bm + 1;
			asb = bInA + 1;
		}

		return get(as, ae, bs, be, pos, asb, aeb, bsa, bea);
	}

	private int find(int[] arr, int elem, int start, int end) {
		int pos = Arrays.binarySearch(arr, start, end + 1, elem);
		if (pos < 0)
			return -pos;
		return pos;
	}
}