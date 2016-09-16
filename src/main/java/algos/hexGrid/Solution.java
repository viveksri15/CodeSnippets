package algos.hexGrid;

/**
 * Created by viveksrivastava on 25/05/16.
 */
public class Solution {
}

class Grid {
	private int[][] grid;

	public Grid(int[][] grid) {
		this.grid = grid;
	}

	public boolean solve(int row1, int row2) {
		return false;
	}

	private boolean isBlack1(int row) {
		return grid[0][row] == 1;
	}

	private boolean isBlack2(int row) {
		return grid[1][row] == 1;
	}

	private boolean tryArrange(int row1, int row2) {
		return grid[0][row1] == 0 && grid[1][row2] == 0;
	}

//	private boolean
}
