package algos.increaseSum;

import java.util.Arrays;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int q = sc.nextInt();
        Solution s = new Solution();
        for (int i = 0; i < q; i++)
            s.solve(sc);
    }

    private void solve(Scanner sc) {
        int n = sc.nextInt();
        int[][] m = new int[2 * n][2 * n];
        for (int i = 0; i < 2 * n; i++) {
            for (int j = 0; j < 2 * n; j++)
                m[i][j] = sc.nextInt();
        }

        long sum = findBest(m, 0);
        System.out.println(sum);
    }

    private long findBest(int[][] m, int depth) {
        long sum = 0;
        int half = m.length / 2;
        int full = m.length;
        for (int i = 0; i < half; i++) {
            for (int j = 0; j < half; j++) {
                sum += getMax(m[i][j], m[full - 1 - i][j], m[i][full - 1 - j], m[full - 1 - i][full - 1 - j]);
            }
        }
        return sum;
    }

    private long getMax(int a, int b, int c, int d) {
        int[] arr = new int[]{a, b, c, d};
        Arrays.sort(arr);
        return arr[arr.length - 1];
    }
}