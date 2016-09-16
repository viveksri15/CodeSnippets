package in.viveksrivastava.problems;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Vivek on 28-05-2014.
 * <p/>
 * *Let A be a non-empty zero-indexed array consisting of N integers.
 * The abs sum of two for a pair of indices (P, Q) is the absolute value |A[P] + A[Q]|, for 0 ≤ P ≤ Q < N.

 * Write a function:
  * that, given a non-empty zero-indexed array A consisting of N integers, returns the minimal abs sum of two for any pair of indices in this array.
 */
public class MinAbsSumOfTwo {
    public int solution(int[] A) {
        int minSum = Integer.MAX_VALUE, l = 0, r = 0, sum = 0;

        if (A == null || A.length == 0)
            return -1;
        if (A.length == 1)
            return A[0];

        r = A.length - 1;

        Arrays.sort(A);

        for (int i = 0; i < A.length; i++) {
            if (A[i] != Integer.MAX_VALUE && A[i + 1] != Integer.MAX_VALUE) {
                sum = A[l] + A[r];
                if (sum < 0) {
                    l++;
                }

                if (sum > 0) {
                    r--;
                }
                int s = Math.abs(sum);
                if (s < minSum) {
                    minSum = s;
                }
                if (l == r)
                    break;
            }
        }

        return minSum;
    }

    public static void main(String[] args) {
        int[] A = new int[]{-8, 4, 5, -10, 3};
        /*Random r = new Random();
        for (int i = 0; i < 5000; i++) {
            A[i] = r.nextInt(Integer.MAX_VALUE);
        }*/
        System.out.println(new MinAbsSumOfTwo().solution(A));
    }
}