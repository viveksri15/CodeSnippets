package algos.rotateMatrix;

import java.util.Scanner;

/**
 * Created by viveksrivastava on 14/05/16.
 */
public class Solution {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int m = scanner.nextInt();
		int n = scanner.nextInt();
		long r = scanner.nextLong();
		long[][] matrix = new long[m][n];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				matrix[i][j] = scanner.nextInt();
		Matrix matrixOps = new Matrix();
		long[][] rotatedMatrix = matrixOps.rotate(matrix, r);
//		long[][] rotatedMatrix = matrix;
		for (int i = 0; i < rotatedMatrix.length; i++) {
			for (int j = 0; j < rotatedMatrix[i].length; j++) {
				System.out.print(rotatedMatrix[i][j]);
				if (j < rotatedMatrix[i].length - 1)
					System.out.print(" ");
			}
			System.out.println();
		}
	}
}
