package algos.twoDPatternMatch;

/*
    https://www.hackerrank.com/challenges/the-grid-search

	Framework the problem:

	0. Interfaces
	1. Input and validations
	2. Output and checked exceptions
	3. Core algorithm
		a. Set of DataStructures
	4. Test Framework
	5. Assumptions
	6. Size of the input for which we are writing the code

*/

/*
    Assumptions and data size
	- contains only integers?
	-Will my data fit in memory? - YES
	- matrices are square matrix
*/

/*
    Core Algorithm:
		I will using rolling hash which would depend upon i and j

		7 for i and 11 for j

		main matrix V is N * M
		small matrix v is n * m


		hash of 0,0
			h(0,0) = 7 * V(0,0)
				for adding element 0,1
					11 * h(0,0) + 11 * 7 * V(0,1) = 11 * 7 * V(0,0) + 7 * V(0,1)

				V(0,0..j) = 7*(11^j * V(0,0) + 11^(j-1) * V(0,1) ..... 11^0 * V(0,j))

				V(0,1..j) = 7 * (V(0, 0..j) - 11^(j) * V(0,0))/11

		if my hash matches: great. Possible check. Validate.




*/

import java.util.Scanner;

interface PatternFinder {
    boolean find(int[][] canvas, int[][] patter);
}

class RollingHashPatterFinder implements PatternFinder {
    private final int ROW_PRIME = 11, COLUMN_PRIME = 7;

    public boolean find(int[][] canvas, int[][] pattern) {
        boolean isValid = isValid(canvas, pattern);
        if (!isValid)
            return false;

        long patternHash = computeHash(pattern, 0, pattern.length, 0, pattern[0].length);
//        System.out.println("patternHash = " + patternHash);
        long canvasPattern = computeHash(canvas, 0, pattern.length, 0, pattern[0].length);
//        System.out.println("canvasPattern = " + canvasPattern);
        if (patternHash == canvasPattern) {
            if (validate(pattern, canvas, 0, pattern.length, 0, pattern[0].length))
                return true;
        }

        long topCanvasPattern = canvasPattern;

        int i = 0;
        do {
            long rightSlidingCanvasPattern = topCanvasPattern;
            for (int j = 1; j < canvas[0].length - pattern[0].length + 1; j++) {
                rightSlidingCanvasPattern = computeHashSlideRight(canvas, j, j + pattern[0].length - 1, i, pattern.length, rightSlidingCanvasPattern);
//                long newRightPattern = computeHash(canvas, i, pattern.length, j, pattern[0].length);
//                System.out.println("newRightPattern = " + newRightPattern);
//                System.out.println("rightSlidingCanvasPattern = " + rightSlidingCanvasPattern);
                if (patternHash == rightSlidingCanvasPattern) {
                    if (validate(pattern, canvas, i, pattern.length, j, pattern[0].length))
                        return true;
                }
            }

            i++;
            int endRow = i + pattern.length - 1;
            if (endRow == canvas.length)
                break;
            topCanvasPattern = computeHashSlideDown(canvas, i, endRow, 0, pattern[0].length, topCanvasPattern);
//            System.out.println(i + "; computeHashSlideDown = " + topCanvasPattern);
//            long topCanvasPattern1 = computeHash(canvas, i, pattern.length, 0, pattern[0].length);
//            System.out.println("computeHashSlideDown = " + topCanvasPattern);
//            assert topCanvasPattern == topCanvasPattern1;
            if (patternHash == topCanvasPattern) {
                if (validate(pattern, canvas, i, pattern.length, 0, pattern[0].length))
                    return true;
            }
        } while (i <= canvas.length - pattern.length + 1);

        return false;
    }

    private boolean validate(int[][] pattern, int[][] canvas, int canvasStartRowIndex, int canvasEndRowIndex, int canvasStartColIndex, int canvasEndColIndex) {
        for (int ip = 0, ic = canvasStartRowIndex; ip < pattern.length && ic < canvasEndRowIndex; ip++, ic++) {
            for (int jp = 0, jc = canvasStartColIndex; jp < pattern[0].length && jc < canvasEndColIndex; jp++, jc++) {
                if (pattern[ip][jp] != canvas[ic][jc])
                    return false;
            }
        }
        return true;
    }

    private boolean isValid(int[][] canvas, int[][] pattern) {
        if (canvas == null || pattern == null || canvas.length == 0 || pattern.length == 0)
            return false;
        if (pattern.length > canvas.length)
            return false;
        return pattern[0].length <= canvas[0].length;

    }

    private long computeHash(int[][] pattern, int startRow, int rowLength, int startCol, int columnLength) {
        long hash = 0l;
        for (int i = 0; i < rowLength; i++) {
            long columnHash = 0l;
            for (int j = 0; j < columnLength; j++) {
                columnHash = (COLUMN_PRIME * columnHash) + pattern[startRow + i][startCol + j];
            }
            hash = ROW_PRIME * hash + columnHash;
        }

        return hash;
    }

    private long computeHashSlideDown(int[][] pattern, int startRow, int endRow, int startColumn, int columnLength, long knownHash) {
        long hashOfRowToRemove = 0l;
        for (int i = 0; i < columnLength; i++) {
            hashOfRowToRemove = (COLUMN_PRIME * hashOfRowToRemove + pattern[startRow - 1][startColumn + i]);
        }

        long hashOfRowToAdd = 0l;
        for (int i = 0; i < columnLength; i++) {
            hashOfRowToAdd = (COLUMN_PRIME * hashOfRowToAdd + pattern[endRow][startColumn + i]);
        }

        double v = Math.pow(ROW_PRIME, endRow - startRow) * hashOfRowToRemove;
        return (long) (((knownHash - v) * ROW_PRIME) + hashOfRowToAdd);
    }

    private long computeHashSlideRight(int[][] pattern, int startCol, int endCol, int startRow, int rowLength, long knownHash) {
        long hashOfColumnToRemove = 0l;
        for (int i = 0; i < rowLength; i++) {
            hashOfColumnToRemove = ROW_PRIME * hashOfColumnToRemove + pattern[startRow + i][startCol - 1];
        }

        long hashOfColumnToAdd = 0l;
        for (int i = 0; i < rowLength; i++) {
            hashOfColumnToAdd = ROW_PRIME * hashOfColumnToAdd + pattern[startRow + i][endCol];
        }

        return (long) (((knownHash - (Math.pow(COLUMN_PRIME, endCol - startCol) * hashOfColumnToRemove)) * COLUMN_PRIME) + hashOfColumnToAdd);
    }
}

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();

        for (int a0 = 0; a0 < t; a0++) {
            int[][] pattern = null;
            int[][] canvas = null;
            int R = in.nextInt();
            int C = in.nextInt();
            canvas = new int[R][C];
            for (int G_i = 0; G_i < R; G_i++) {
                String line = in.next();
                for (int j = 0; j < line.toCharArray().length; j++) {
                    canvas[G_i][j] = line.charAt(j) - '0';
                }
            }
            int r = in.nextInt();
            int c = in.nextInt();
            pattern = new int[r][c];
            for (int P_i = 0; P_i < r; P_i++) {
                String line = in.next();
                for (int j = 0; j < line.toCharArray().length; j++) {
                    pattern[P_i][j] = line.charAt(j) - '0';
                }
            }
            PatternFinder patternFinder = new RollingHashPatterFinder();
            boolean found = patternFinder.find(canvas, pattern);
            System.out.println(found ? "YES" : "NO");
        }
    }
}
