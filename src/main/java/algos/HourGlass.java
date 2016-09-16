package algos;

import java.util.*;

class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int arr[][] = new int[6][6];
        for (int arr_i = 0; arr_i < 6; arr_i++) {
            for (int arr_j = 0; arr_j < 6; arr_j++) {
                arr[arr_i][arr_j] = in.nextInt();
            }
        }

        int sum = findMaxSum(arr);
        System.out.println(sum);
    }

    private static int findMaxSum(int[][] arr) {
        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int sum = arr[i][j] + arr[i][j+1] + arr[i][j+2] + arr[i+2][j] + arr[i + 2][j + 1] + arr[i + 2][j + 2] + arr[i + 1][j + 1];
                maxSum = Math.max(sum, maxSum);
                System.out.println(i + "," + j + ", sum = "+ sum);
            }
        }
        return maxSum;
    }
}

/**
 * Created by viveksrivastava on 17/04/16.
 */
public class HourGlass {
    public static void main(String[] args) {
        Solution.main(args);
    }
}
