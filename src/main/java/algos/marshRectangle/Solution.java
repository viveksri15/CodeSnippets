package algos.marshRectangle;

/**
 * Created by viveksrivastava on 05/05/16.
 */
public class Solution {
	public static void main(String[] args) {
		int[][] marsh = new int[][]{
				{0, 0, 0, 0},
				{0, 0, 0, 0},
				{0, 0, 0, -1},
				{-1, 0, 0, 0},
				{0, 0, 0, 0},
		};
		Marsh marshObj = new Marsh(marsh);
		marshObj.largestRectangle(0,0,0,0);
	}
}

class Marsh {

	private final int X = 1;
	private final int Y = 0;
	private int[][] arr;

	public Marsh(int[][] arr) {
		this.arr = arr;
	}

	int largestRectangle(int x, int y, int xSize, int ySize) {

		if (y == arr.length)
			return 0;
		if (x == arr[0].length)
			return 0;

		for (int i = 1; i <= ySize; i++) {
			for (int j = 1; j <= xSize; j++) {
				if (i == 1 && j == 1) {
					//can I make 1X1 fence
				} else {

				}
			}
		}


		/*int[][][] data = new int[arr.length][arr[0].length ][2];
		for (int i = arr.length - 1; i >= 0; i--) {
			for (int j = arr[0].length - 1; j >= 0; j--) {
				data[i][j][0] = 0;
				data[i][j][1] = 0;
			}
		}

		for (int i = arr.length - 1; i >= 0; i--) {
			for (int j = arr[0].length - 1; j >= 0; j--) {

				int tl = arr[i][j];
//				int bl = arr[i + 1][j];
//				int tr = arr[i][j + 1];
//				int br = arr[i + 1][j + 1];
//				if (tl == -1 || bl == -1 || tr == -1 || br == -1)
//					continue;
				if(tl == -1)
					continue;

				int x = 0, y = 0;
				if (j < arr[0].length - 1) {
					x = data[i][j + 1][algos.X] + 1;
				}else
					x =1;
				if (i < arr.length - 1) {
					y = data[i + 1][j][Y] + 1;
				}else
					y = 1;
				data[i][j][algos.X] = x;
				data[i][j][Y] = y;
			}
		}
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				System.out.print(data[i][j][algos.X] + "," + data[i][j][Y] + "::");
			}
			System.out.println();
		}
		return 0;*/
		return 0;
	}
}
