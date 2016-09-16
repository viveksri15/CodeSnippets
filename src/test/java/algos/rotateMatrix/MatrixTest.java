package algos.rotateMatrix;

import junit.framework.TestCase;

/**
 * Created by viveksrivastava on 14/05/16.
 */
public class MatrixTest extends TestCase {

	public void testRotateWithSmallR() throws Exception {
		Matrix matrix = new Matrix();
		Matrix.Coordinates coordinate = new Matrix.Coordinates(1, 1);
		Matrix.Coordinates topLeft = new Matrix.Coordinates(1, 1);
		Matrix.Coordinates bottomRight = new Matrix.Coordinates(3, 3);
		int r = 1;
		Matrix.Coordinates rotatedCoordinate = matrix.rotate(coordinate, topLeft, bottomRight, r);
		assertEquals(rotatedCoordinate.getI(), 2);
		assertEquals(rotatedCoordinate.getJ(), 1);
	}

	public void testRotateWithLargeR() throws Exception {
		Matrix matrix = new Matrix();
		Matrix.Coordinates coordinate = new Matrix.Coordinates(1, 1);
		Matrix.Coordinates topLeft = new Matrix.Coordinates(1, 1);
		Matrix.Coordinates bottomRight = new Matrix.Coordinates(3, 3);
		int r = 4;
		Matrix.Coordinates rotatedCoordinate = matrix.rotate(coordinate, topLeft, bottomRight, r);
		assertEquals(rotatedCoordinate.getI(), 3);
		assertEquals(rotatedCoordinate.getJ(), 3);
	}

	public void testRotateWithLargerR() throws Exception {
		Matrix matrix = new Matrix();
		Matrix.Coordinates coordinate = new Matrix.Coordinates(1, 1);
		Matrix.Coordinates topLeft = new Matrix.Coordinates(1, 1);
		Matrix.Coordinates bottomRight = new Matrix.Coordinates(3, 3);
		int r = 6;
		Matrix.Coordinates rotatedCoordinate = matrix.rotate(coordinate, topLeft, bottomRight, r);
		assertEquals(rotatedCoordinate.getI(), 1);
		assertEquals(rotatedCoordinate.getJ(), 3);
	}

	public void testRotateWithLargestR() throws Exception {
		Matrix matrix = new Matrix();
		Matrix.Coordinates coordinate = new Matrix.Coordinates(1, 1);
		Matrix.Coordinates topLeft = new Matrix.Coordinates(1, 1);
		Matrix.Coordinates bottomRight = new Matrix.Coordinates(3, 3);
		int r = 8;
		Matrix.Coordinates rotatedCoordinate = matrix.rotate(coordinate, topLeft, bottomRight, r);
		assertEquals(rotatedCoordinate.getI(), 1);
		assertEquals(rotatedCoordinate.getJ(), 1);
	}

	public void testRotateWithCompleteRotation() throws Exception {
		Matrix matrix = new Matrix();
		Matrix.Coordinates coordinate = new Matrix.Coordinates(1, 1);
		Matrix.Coordinates topLeft = new Matrix.Coordinates(1, 1);
		Matrix.Coordinates bottomRight = new Matrix.Coordinates(3, 3);
		int r = 9;
		Matrix.Coordinates rotatedCoordinate = matrix.rotate(coordinate, topLeft, bottomRight, r);
		assertEquals(rotatedCoordinate.getI(), 2);
		assertEquals(rotatedCoordinate.getJ(), 1);
	}

	public void testRotate() throws Exception {
		long[][] matrix = new long[][]{
				{1, 2, 3, 4},
				{10, 11, 12, 13},
				{14, 15, 16, 17}
		};
		Matrix matrixOps = new Matrix();
		long[][] rotated = matrixOps.rotate(matrix, 3);
		int[][] rotatedExpcted = new int[][]{
				{4, 13, 17, 16},
				{3, 12, 11, 15},
				{2, 1, 10, 14},
		};
		for (int i = 0; i < rotated.length; i++) {
			for (int j = 0; j < rotated[i].length; j++) {
				assertEquals(rotated[i][j], rotatedExpcted[i][j]);
				System.out.print(rotated[i][j]);
				System.out.print(",");
			}
			System.out.println();
		}


	}
}