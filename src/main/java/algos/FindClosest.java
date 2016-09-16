package algos;

import java.util.logging.Logger;

/**
 * --- Closest search in a sorted array
 * ---1,2,3,4,5,7,8,9,10
 */

class FindClosest {

	private static Logger logger = Logger.getLogger(FindClosest.class.getName());

	public static void main(String[] args) {
		int[] testArray = new int[]{1, 2, 3, 4, 5, 6, 9, 10, 11, 12};
		FindClosest findClosest = new FindClosest();
		logger.info("findClosest for 7=" + findClosest.findClosestNumber(testArray, 7));
		logger.info("findClosest for 5=" + findClosest.findClosestNumber(testArray, 5));
		logger.info("findClosest for 13=" + findClosest.findClosestNumber(testArray, 13));
		logger.info("findClosest for -1=" + findClosest.findClosestNumber(testArray, -1));
		logger.info("findClosest for 8=" + findClosest.findClosestNumber(testArray, 8));
	}

	public int findClosestNumber(int[] source, int numberToSearch) {
		//assertSorted(source);
		int start = 0, end = source.length - 1;
		int closest = source[0];
		while (Math.abs(closest - numberToSearch) != 0) {
//			logger.info("Closest=" + closest);
			int mid = start + (end - start) / 2;
			if (start == end) {
				if (Math.abs(source[start] - numberToSearch) < Math.abs(closest - numberToSearch))
					closest = source[start];
				return closest;
			}
			if (start == end - 1) {
				if (Math.abs(source[start] - numberToSearch) < Math.abs(closest - numberToSearch))
					closest = source[start];
				if (Math.abs(source[end] - numberToSearch) < Math.abs(closest - numberToSearch))
					closest = source[end];
				return closest;
			}

			int distance = Math.abs(source[mid] - numberToSearch);

			if (distance < Math.abs(closest - numberToSearch))
				closest = source[mid];

			if (source[mid] < numberToSearch)
				start = mid + 1;
			else
				end = mid;
		}

		return closest;
	}
}