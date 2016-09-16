package algos;

import java.util.Random;

/**
 * Created by viveksrivastava on 15/05/16.
 */
public class LogCutter {
	private int[] marks;
	private long[][] dp;

	public LogCutter(int[] marks) {
		this.marks = marks;

		//row denotes: position at which I am cutting my rod
		//column denotes remaining length
		dp = new long[marks.length + 1][marks.length + 1];
		for (int i = 0; i < marks.length + 1; i++)
			java.util.Arrays.fill(dp[i], Long.MAX_VALUE);
	}

	public static void main(String[] args) {
		int MAX = 10;
		int[] marks = new int[MAX];
		Random random = new Random();
		for (int pos = 0; pos < marks.length; pos++) {
			marks[pos] = 1 + random.nextInt(pos + 10);
		}
		System.out.println(java.util.Arrays.toString(marks));
		long cost = Long.MAX_VALUE;
		LogCutter cutter = new LogCutter(marks);
//		for (int i = 0; i < marks.length; i++) {
//			long cost_t = cutter.cut(0, i, marks.length);
//			System.out.println("FINAL= " + i + "," + marks.length + "," + cost_t);
//			if (cost_t < cost)
//				cost = cost_t;
//		}
		cost = cutter.cut(0, 0, marks.length);
		System.out.println(cost);
		cost = cutter.cut(0, 1, marks.length);
		System.out.println(cost);
	}

	private static int getMark(int[] marks, int position) {
		if (position < 0)
			return 0;
		return marks[position];
	}

	public long cut(int startIndex, int pos, int len) {
//		System.out.println("w= " + startIndex + "," + pos + "," + len);
		if (pos > len - 1)
			return 0;
		long cost = marks[pos - startIndex];
		long cost_lp = getFromDP(startIndex, pos);
		long cost_hp = getFromDP(pos + 1, len);
//		int cost_lp = Integer.MAX_VALUE;
//		int cost_hp = Integer.MAX_VALUE;

		if (cost_lp == Long.MAX_VALUE) {
			for (int i = startIndex; i < pos; i++) {
				long cost_t = cut(startIndex, i, pos);
				if (cost_t < cost_lp)
					cost_lp = cost_t;
			}
		}
		if (cost_lp < Long.MAX_VALUE)
			saveInDP(startIndex, pos, cost_lp);

		if (cost_hp == Long.MAX_VALUE) {
			for (int i = pos + 1; i < len; i++) {

				long cost_t = cut(pos + 1, i, len);

				if (cost_t < cost_hp)
					cost_hp = cost_t;
			}
		}
		if (cost_hp < Long.MAX_VALUE)
			saveInDP(pos + 1, len, cost_hp);


//		System.out.println(startIndex + "," + pos + "," + len + "," + cost + "," + cost_hp + "," + cost_lp);

		if (cost_lp < Long.MAX_VALUE)
			cost += cost_lp;
		if (cost_hp < Long.MAX_VALUE)
			cost += cost_hp;

		return cost;
	}

	private void saveInDP(int i, int j, long cost) {
//		System.out.println("dpS = " + i + "," + j + "," + cost);
		dp[i][j] = cost;
	}

	private long getFromDP(int i, int j) {
		long dpV = dp[i][j];
//		if (dpV >= 0)
//			System.out.println("dpV = " + i + "," + j + "," + dpV);
		return dpV;
	}
}