package in.viveksrivastava.forkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Created by Vivek on 21-04-2014.
 */
public class MergeSort extends RecursiveAction {

    private int[] arrayToSort;
    private int start, end;

    public MergeSort(int[] arrayToSort, int start, int end) {
        this.start = start;
        this.end = end;
        this.arrayToSort = arrayToSort;
    }

    @Override
    protected void compute() {
        print("COMPUTE=", arrayToSort);
        if (end - start <= 10) {
            sort();
            return;
        }
        invokeAll(new MergeSort(arrayToSort, start, (end + start) / 2), new MergeSort(arrayToSort, (start + end) / 2, end));
        merge();
    }

    private void merge() {
        int counterSmall = start, counterLarge = (end + start) / 2, counter = 0;


        print("PRE_MERGE=", arrayToSort);
        while (counter < (end - start)) {

            System.out.println("counterSmall = " + counterSmall);
            System.out.println("counterLarge = " + counterLarge);
            System.out.println("counter = " + counter);

            if (arrayToSort[counterSmall] <= arrayToSort[counterLarge]) {
                counterSmall++;
                counter++;
                print("MERGE=", arrayToSort);
            } else {
                int tmp = arrayToSort[counterSmall];
                arrayToSort[counterSmall] = arrayToSort[counterLarge];
                arrayToSort[counterLarge] = tmp;
                counterSmall++;
                counter++;
                print("MERGE=", arrayToSort);
                int counterLarge1 = counterLarge;
                while (counterLarge1 < end - 1 && arrayToSort[counterLarge1] > arrayToSort[counterLarge1 + 1]) {
                    System.out.println("counterSmall = " + counterSmall);
                    System.out.println("counterLarge = " + counterLarge1);
                    System.out.println("counter = " + counter);

                    int tmp1 = arrayToSort[counterLarge1];
                    arrayToSort[counterLarge1] = arrayToSort[counterLarge1 + 1];
                    arrayToSort[counterLarge1 + 1] = tmp1;
                    counterLarge1++;
//                    counter++;
                    print("MERGE=", arrayToSort);
                }
            }
        }
    }

    private void sort() {
        for (int i = 0; i < (end - start); i++) {
            int large = arrayToSort[start], counter = start;
            for (int j = start; j < end - i; j++) {
                if (arrayToSort[j] > large) {
                    large = arrayToSort[j];
                    counter = j;
                }
            }
            arrayToSort[counter] = arrayToSort[end - i - 1];
            arrayToSort[end - i - 1] = large;
        }
    }

    public static void main(String[] args) {
        int[] arrayToSort = new int[]{9, 8, 3, 10, 2, 12, 21, 11, 10, -1, 0, 15};
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new MergeSort(arrayToSort, 0, arrayToSort.length));
        print("FINAL", arrayToSort);
    }

    private synchronized static void print(String tag, int[] arrayToSort) {
        System.out.print(tag + "= ");
        for (int i = 0; i < arrayToSort.length; i++) {
            System.out.print(arrayToSort[i] + " ");
        }
        System.out.println();
    }
}