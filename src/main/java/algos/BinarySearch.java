package algos;

/**
 * Created by viveksrivastava on 14/04/16.
 */
public class BinarySearch {
    private int findIndex(int[] array, int element) {
        //end is non-inclusive
        //start is inclusive
        //2,3,4,6,6,8,9,10
        //start = 0
        //end = 8
        //mid = 4
        int start = 0, end = array.length;
        do {
            int mid = start + (end - start) / 2;
            if (array[mid] == element)
                return mid;

            if (mid == start)
                return -1;

            if (array[mid] < element) {
                start = mid + 1;
            } else {
                end = mid;
            }
        } while (true);
    }

    public static void main(String[] args) {
        BinarySearch binarySearch = new BinarySearch();
        for (int i = -1; i <= 20; i++) {
            int index = binarySearch.findIndex(new int[]{2, 4, 6, 8, 10, 12, 14, 16}, i);
            System.out.println(i + "=" + index);
        }

//        int index = binarySearch.findIndex(new int[]{2, 4, 6, 8, 10, 12, 14, 16}, 6);
    }
}
