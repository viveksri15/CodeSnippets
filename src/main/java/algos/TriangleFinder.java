package algos;//--Given a set of numbers find if a triplet can form a triangle a+b > c , b+c > a and c+a > b. The result to display all possible combinations of triplets. [ 10 5 3 4 7 1] [5,3,4 ] is one possible triplet and there can be many more.

/*
	[ 10 5 3 4 7 1]
	//Sort
	//Find the first number c such that, c>a, c>b, c>a+b and i of algos.A[i]==c, and algos.A[i-1]<=a+b
*/

import org.apache.commons.lang.time.StopWatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

class Triangle {

	private int sideA, sideB, sideC;

	public Triangle(int a, int b, int c) {
		this.sideA = a;
		this.sideB = b;
		this.sideC = c;
	}

	public static List<Triangle> findAll(int[] sides) {
		if (sides == null)
			return null;
		List<Triangle> triangles = new ArrayList<>();
		Arrays.sort(sides);
		for (int i = 0; i < sides.length - 2; i++) {
			if (sides[i] <= 0)
				continue;
			for (int j = i + 1; j < sides.length - 1; j++) {
				int a = sides[i], b = sides[j];
				int cIndex = j + 1;
				int end = sides.length - 1;
				int mid = cIndex + (end - cIndex) / 2;

				while (true) {
					boolean valid1 = !isPartialValid(sides[cIndex - 1], a, b);
					boolean valid2 = isPartialValid(sides[cIndex], a, b);
					if (valid1 && valid2) {
						break;
					} else if (cIndex == end - 1) {
						if (valid2)
							break;
						if (isPartialValid(sides[end], a, b)) {
							cIndex = end;
							break;
						}
						cIndex = -1;
						break;
					} else if (cIndex == end) {
						cIndex = -1;
						break;
					}

					if (isPartialValid(sides[mid], a, b)) {
						end = mid;
						mid = cIndex + (mid - cIndex) / 2;
					} else {
						cIndex = mid;
						mid = mid + (end - mid) / 2;
					}
				}

				if (cIndex > 0) {
					for (int k = cIndex; k < sides.length; k++) {
						if (isValid(sides[i], sides[j], sides[k])) {
							Triangle triangle = new Triangle(sides[i], sides[j], sides[k]);
							triangles.add(triangle);
						}
					}
				}
			}
		}

		return triangles;
	}

	public static boolean isPartialValid(int sideA, int sideB, int sideC) {
		return sideA + sideB > sideC;
	}

	public static boolean isValid(int sideA, int sideB, int sideC) {
		boolean v1 = sideA + sideB > sideC;
		boolean v2 = sideA + sideC > sideB;
		boolean v3 = sideB + sideC > sideA;
		return (v1 && v2 && v3);
	}

	@Override
	public String toString() {
		return sideA + "," + sideB + "," + sideC;
	}
}

public class TriangleFinder {
	public static void main(String[] args) {
		StopWatch stopWatch = new StopWatch();
		int testCases = 1000;
		int[] sides = new int[testCases];
		Random random = new Random();
		for (int i = 0; i < testCases; i++)
			sides[i] = random.nextInt(30);
		stopWatch.start();
		List<Triangle> triangles = Triangle.findAll(sides);
		stopWatch.stop();
//		for (algos.Triangle triangle : triangles) {
//			System.out.println(triangle);
//		}
		System.out.println("stopWatch.getTime() = " + stopWatch.getTime());
	}
}