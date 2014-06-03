package in.viveksrivastava.problems;

/*
 * You are given N counters, initially set to 0, and you have two possible operations on them:
		increase(X) − counter X is increased by 1,
		max counter − all counters are set to the maximum value of any counter.
	A non-empty zero-indexed array A of M integers is given. This array represents consecutive operations:
	if A[K] = X, such that 1 ≤ X ≤ N, then operation K is increase(X),
	if A[K] = N + 1 then operation K is max counter.
	The goal is to calculate the value of every counter after all operations.
	Write a function:
	class Solution { public int[] solution(int N, int[] A); }
	that, given an integer N and a non-empty zero-indexed array A consisting of M integers, returns a sequence of integers representing the values of the counters.
	N and M are integers within the range [1..100,000];
	each element of array A is an integer within the range [1..N + 1].
	Complexity:


	expected worst-case time complexity is O(N+M);
	expected worst-case space complexity is O(N), beyond input storage (not counting the storage required for input arguments).

	Elements of input arrays can be modified.
 */

public class CounterIncrement {
    public int[] solution(int N, int[] A) {

        int currentMax = 0, lastMax = 0;
        int[] B = new int[N];
        for (int i = 0; i < N; i++) {
            B[i] = 0;
        }

        for (int i = 0; i < A.length; i++) {
            if (A[i] == N + 1) {
                lastMax = currentMax;
            } else if (A[i] <= N && A[i] >= 1) {
                int index = A[i] - 1;
                if (B[index] < lastMax) {
                    B[index] = lastMax;
                }

                B[index]++;
                if (B[index] > currentMax)
                    currentMax = B[index];
            }
        }
        for (int i = 0; i < N; i++) {
            if (B[i] < lastMax)
                B[i] = lastMax;
        }

        return B;
    }

    public static void main(String[] args) {
        int[] arr = new CounterIncrement().solution(5, new int[]{3, 4, 4, 6, 1,
                4, 4});
        for (int r : arr) {
            System.out.print(r + " ");
        }
    }
}