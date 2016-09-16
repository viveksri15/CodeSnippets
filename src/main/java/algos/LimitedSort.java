package algos;

import java.util.PriorityQueue;


interface Sortable {
	int[] sort(int[] numbers, int leftLimit);
}

public class LimitedSort implements Sortable {

	public static void main(String[] args) {
		LimitedSort limitedSort = new LimitedSort();
		int[] sortedResult = limitedSort.sort(new int[]{2, 3, 9, 1, 4, 3, 5, 9}, 2);
		System.out.println(java.util.Arrays.toString(sortedResult));
	}

	public int[] sort(int[] numbers, int leftLimit) {
		if (numbers == null || leftLimit < 0)
			return numbers;

		int[] maxSortedArray = new int[numbers.length];

		PriorityQueue<Integer> queue = new PriorityQueue<>();

		int count = 0;
		for (int i = 0; i < numbers.length; i++) {
			queue.add(numbers[i]);
			if (i >= leftLimit) {
				Integer number = queue.poll();
				maxSortedArray[count++] = number;
			}
		}

		while (!queue.isEmpty()) {
			Integer number = queue.poll();
			maxSortedArray[count++] = number;
		}
		return maxSortedArray;
	}
}