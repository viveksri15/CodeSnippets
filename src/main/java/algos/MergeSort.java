package algos;

import java.util.Arrays;

/**
 * Merge Sort
 */

class MergeSort {
	public static void main(String[] args) {
//		int[] toSort = new int[]{2, 3, 9, 1, 10, 3, 5, 20, 15, 30, 1, 5, 0, -1};
		int[] toSort = new int[]{0, 0, 0, 0, 0, -1, 1};
		new MergeSort().sort(toSort, 0, toSort.length - 1);
		System.out.println("toSort = " + Arrays.toString(toSort));
	}

	public void sort(int[] arrayToSort, int start, int end) {

		if (arrayToSort == null || arrayToSort.length == 0)
			return;

		if (start == end) {
			return;
		}

		if (start + 1 == end) {
			if (arrayToSort[start] > arrayToSort[end])
				return;
			else {
				int temp = arrayToSort[end];
				arrayToSort[end] = arrayToSort[start];
				arrayToSort[start] = temp;
				return;
			}
		}

		int mid = (start + end) / 2;

		sort(arrayToSort, start, mid);
		sort(arrayToSort, mid + 1, end);
		merge(arrayToSort, start, mid + 1, end);
	}

	private void merge(int[] arrayToMerge, int start, int mid, int end) {
		int[] mergedArray = new int[end - start + 1];
		for (int i = 0, j = start, k = mid; i < mergedArray.length; i++) {
			if (j < mid && k <= end && arrayToMerge[j] > arrayToMerge[k]) {
				mergedArray[i] = arrayToMerge[j];
				j++;
			} else if (k <= end) {
				mergedArray[i] = arrayToMerge[k];
				k++;
			} else if (j < mid) {
				mergedArray[i] = arrayToMerge[j];
				j++;
			}
		}

		System.arraycopy(mergedArray, 0, arrayToMerge, start, mergedArray.length);
	}
}