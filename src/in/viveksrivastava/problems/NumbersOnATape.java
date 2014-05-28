package in.viveksrivastava.problems;

/**
 * Created by Vivek on 28-05-2014.
 * <p/>
 * *A non-empty zero-indexed array A consisting of N integers is given. Array A represents numbers on a tape.
 * Any integer P, such that 0 < P < N, splits this tape into two non-empty parts: A[0], A[1], ..., A[P − 1] and A[P], A[P + 1], ..., A[N − 1].
 * The difference between the two parts is the value of: |(A[0] + A[1] + ... + A[P − 1]) − (A[P] + A[P + 1] + ... + A[N − 1])|
 * In other words, it is the absolute difference between the sum of the first part and the sum of the second part.
 * Write a function:
 * that, given a non-empty zero-indexed array A of N integers, returns the minimal difference that can be achieved.
 */


public class NumbersOnATape {

    public int solution(int[] A) {

        if (A == null)
            return -1;
        if (A.length == 1)
            return A[0];

        int sumR = 0, sumL = A[0], minDiff = 0;
        for (int i = 1; i < A.length; i++) {
            sumR += A[i];
        }

        minDiff = Math.abs(sumL - sumR);

        for (int i = 1; i < A.length; i++) {
            sumL += A[i];
            sumR -= A[i];

            int diff = Math.abs(sumL - sumR);
            if (minDiff > diff)
                minDiff = diff;
        }

        return minDiff;
    }

    public static void main(String[] args) {
        int[] A = new int[]{3, 1, 2, 4, 3};
        System.out.println(new NumbersOnATape().solution(A));
    }
}