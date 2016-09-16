package algos;/*

-- problem statement is to implement a static algos.Arrays.sort(Object[] objs)
-- Known that that Object does not extent Comparable, hence taking a comparator


Input:
--N2,N3,N1,N4,N5,N3



Output:
1,2,3,3,4,5

N2,N3,N1
N4,N5,N3

Obv:
	objs != null
	objs size == 0
	objs should be of same type


- test cases
- evolve gracefully
	- Algorithm strategy.
		- Quick sort etc etc..
- comparator

*/


import java.util.Comparator;
import java.util.Random;

interface ISortingStrategy {
	void sort(Object[] objs, CompareStrategy compareStrategy) throws Exception;
}

class CompareStrategy<T> {

	private Comparator comparator;

	public CompareStrategy(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public int compareTo(Object object1, Object object2) {
		return comparator.compare((T) object1, (T) object2);
	}
}

public class Arrays {
	private static CompareStrategy compareStrategy = null;

	public static void addCompareStrategy(CompareStrategy compareStrategy) {
		if (Arrays.compareStrategy == null) {
			synchronized (Arrays.class) {
				if (Arrays.compareStrategy == null) {
					Arrays.compareStrategy = compareStrategy;
				}
			}
		}
	}

	public static void sort(Object[] objs) throws Exception {
		InputValidator.assertNotNull(objs);
		InputValidator.assertCompareStrategyValid(compareStrategy);
		SortingStrategyFactory.getInstance().sort(objs, compareStrategy);
	}
}

class InputValidator {
	public static void assertNotNull(Object[] objs) throws Exception {
		if (objs == null || objs.length == 0)
			throw new Exception("Bad Data");
		for (Object obj : objs) {
			if (obj == null)
				throw new Exception("Bad Data");
		}
	}

	public static void assertCompareStrategyValid(CompareStrategy compareStrategy) throws Exception {
		if (compareStrategy == null)
			throw new Exception("Bad Comparator");
	}
}

class SortingStrategyFactory {
	public static ISortingStrategy getInstance() {
		//Returning hardcoded sorting strategy for now
		return new QuickSortingStrategy();
	}
}

class QuickSortingStrategy implements ISortingStrategy {
	public void sort(Object[] objs, CompareStrategy compareStrategy) throws Exception {
		//Assuming validations are done before passing anything to this method
		//todo
		//Get a pivot -- mid element for now
		//Divide it into 2 parts
		//Sort each of them individually
		//Merge

		quickSort(objs, 0, objs.length - 1, compareStrategy);
	}

	private void quickSort(Object[] objs, int startIndex, int endIndex, CompareStrategy compareStrategy) {

		if (startIndex >= endIndex) {
			//none or One element
			return;
		}
		if (startIndex == endIndex - 1) {
			//Two elements
			if (compareStrategy.compareTo(objs[startIndex], objs[endIndex]) < 0)
				return;

			Object obj = objs[startIndex];
			objs[startIndex] = objs[endIndex];
			objs[endIndex] = obj;
			return;
		}

		//Choosing a pivot, which is NOT the last element to prevent a possible infinite recursive call
		int pivotIndex = startIndex + new Random().nextInt(endIndex - startIndex);
		Object pivotValue = objs[pivotIndex];

		//Swapping
		for (int i = startIndex; i <= endIndex; i++) {
			if (compareStrategy.compareTo(objs[i], pivotValue) < 0) {
				Object tempObj = objs[i];
				objs[i] = objs[pivotIndex];
				objs[pivotIndex] = tempObj;
				pivotIndex = i;
			}
		}

		quickSort(objs, startIndex, pivotIndex, compareStrategy);
		quickSort(objs, pivotIndex + 1, endIndex, compareStrategy);
	}
}

/*Dry Run
	3,2,1,4
		si = 0, ei = 3

		pi = 0
		pv = 3

	2,3,1,4
		pi = 1

	2,1,3,4
		pi = 2

	qs(0, 2)
	qs(3, 3)
		//return;

	qs(0, 2)
		2,1,3

		pi = 1
		pv = 1

		1,2,3
			pi = 0

		1,2,3

		qs(0,0)
		qs(1,2)

			2,3

	1,2,3,4

*/