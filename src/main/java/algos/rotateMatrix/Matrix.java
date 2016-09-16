package algos.rotateMatrix;

/**
 * Created by viveksrivastava on 14/05/16.
 */
public class Matrix {

	public long[][] rotate(long[][] matrix, long r) {
		int iTop = 0, jLeft = 0, iBottom = matrix.length - 1, jRight = matrix[0].length - 1;
		long[][] rotatedMatrix = new long[matrix.length][matrix[0].length];
		int count = 0;
		int elementCount = 2 * (iBottom + jRight - iTop - jLeft) + 1;
		while (true) {
			count++;
			if (count == elementCount) {
				iTop++;
				jLeft++;
				iBottom--;
				jRight--;
				if (iTop > iBottom || jLeft > jRight)
					break;
				count = 0;
				elementCount = 2 * (iBottom + jRight - iTop - jLeft) + 1;
				continue;
			}
			Coordinates top = new Coordinates(iTop, jLeft);
			Coordinates bottom = new Coordinates(iBottom, jRight);
			for (int j = jLeft; j <= jRight; j++) {
				Coordinates coordinates = new Coordinates(iTop, j);
				Coordinates rotatedC = rotate(coordinates, top, bottom, r);
				rotatedMatrix[rotatedC.getI()][rotatedC.getJ()] = matrix[iTop][j];
			}

			for (int j = jLeft; j <= jRight; j++) {
				Coordinates coordinates = new Coordinates(iBottom, j);
				Coordinates rotatedC = rotate(coordinates, top, bottom, r);
				rotatedMatrix[rotatedC.getI()][rotatedC.getJ()] = matrix[iBottom][j];
			}

			for (int i = iTop + 1; i < iBottom; i++) {
				Coordinates coordinates = new Coordinates(i, jLeft);
				Coordinates rotatedC = rotate(coordinates, top, bottom, r);
				rotatedMatrix[rotatedC.getI()][rotatedC.getJ()] = matrix[i][jLeft];
			}

			for (int i = iTop + 1; i < iBottom; i++) {
				Coordinates coordinates = new Coordinates(i, jRight);
				Coordinates rotatedC = rotate(coordinates, top, bottom, r);
				rotatedMatrix[rotatedC.getI()][rotatedC.getJ()] = matrix[i][jRight];
			}
		}
		return rotatedMatrix;
	}

	public Coordinates rotate(Coordinates coordinates, Coordinates topLeft, Coordinates bottomRight, long rOrig) {
		int i = coordinates.getI();
		int j = coordinates.getJ();

		int iTop = topLeft.getI();
		int jLeft = topLeft.getJ();
		int iBottom = bottomRight.getI();
		int jRight = bottomRight.getJ();

		int perimeter = 2 * (iBottom + jRight - iTop - jLeft);
		int r = (int) (rOrig % perimeter);
		if (r == 0)
			return coordinates;

		if (j == jLeft)
			i = i + r;
		else if (i == iBottom)
			j = j + r;
		else if (j == jRight)
			i = i - r;
		else
			j = j - r;

		while (true) {
//			System.out.println(i + "," + j);
			if (i >= iTop && i <= iBottom && j >= jLeft && j <= jRight)
				break;
			if (i > iBottom) {
				j = jLeft + (i - iBottom);
				i = iBottom;
			}

//			System.out.println(i + "," + j);

			if (j > jRight) {
				i = iBottom - (j - jRight);
				j = jRight;
			}

//			System.out.println(i + "," + j);

			if (i < iTop) {
				j = jRight - (iTop - i);
				i = iTop;
			}

//			System.out.println(i + "," + j);

			if (j < jLeft) {
				i = iTop + (jLeft - j);
				j = jLeft;
			}

//			System.out.println(i + "," + j);
		}

		return new Coordinates(i, j);
	}

	public static class Coordinates {
		private final int i, j;

		public Coordinates(int i, int j) {
			this.i = i;
			this.j = j;
		}

		public int getI() {
			return i;
		}

		public int getJ() {
			return j;
		}
	}
}
