package algos.directConnection.longestIncreasingSubsequence;

import java.util.*;

/*
    Framework of the problem
    1. Input and validations
    2. Output and checked exceptions if any
    3. Assumptions
    4. Limitation on memory and compute time, data size etc
    5. Core Algo
    6. DS
    7. algos.A small test FW if time permits
 */

/*
    Assumptions and limitation: that data should fit in memory.
       n elements, I need 3n * 4 bytes. For a 4GB memory: (1024*1024*1024*4)/4/3 = 35million Integers

     running time of the algo will be
        n^2 solution
 */

//specification
interface LongestIncreasingSubsequence {
    int find(int[] array);
}

//Implementation
final class LongestIncreasingSubsequenceDPImpl implements LongestIncreasingSubsequence {

    //    private int[] dpArray;
    private long tsort = -1l;

    private class DPArrayObj implements Comparable<DPArrayObj> {
        public final int value;
        public final int length;

        private DPArrayObj(int value, int length) {
            this.value = value;
            this.length = length;
        }

        @Override
        public int compareTo(DPArrayObj o) {
            return this.value - o.value;
        }

        @Override
        public String toString() {
            return value + "," + length;
        }
    }

    private List<DPArrayObj> dpArray = new ArrayList<>();

    private int[] array;
    //dpArray[][0] = length
    //dpArray[][1] = tail

    @Override
    public int find(int[] array) {
        this.array = array;
        tsort = 0l;
        dpArray.add(new DPArrayObj(array[0], 1));

        int longestLength = 0;
        long t0 = System.currentTimeMillis();
        for (int i = 1; i < array.length; i++) {
            addElement(array[i], i);
//            System.out.println("dpArray = " + dpArray);
        }

        for (DPArrayObj e : dpArray) {
            longestLength = Math.max(longestLength, e.length);
        }
        System.out.println("t0 = " + (System.currentTimeMillis() - t0) + ", tsort=" + tsort);
        return longestLength;
    }

    private void addElement(int element, int index) {
        //selected case
        int length = 0;
        int startIndex = searchLargestSmallIndex(element, 0, index - 1);
//        System.out.println("startIndex = " + startIndex + " element=" + element + " " + dpArray);
        for (int i = startIndex; i >= 0; i--) {
            if (dpArray.get(i).value < element) {
                if (dpArray.get(i).length > length)
                    length = dpArray.get(i).length;
            }
        }
        length++;
        dpArray.add(new DPArrayObj(element, length));
        long t0 = System.currentTimeMillis();
        Collections.sort(dpArray);
        tsort += (System.currentTimeMillis() - t0);
    }

    private int searchLargestSmallIndex(int element, int start, int end) {
        int mid = -1;
        while (true) {
            mid = start + (end - start) / 2;
            if (mid == start || mid == end)
                break;
            if (dpArray.get(mid + 1).value > element && dpArray.get(mid).value <= element)
                break;
            else if (dpArray.get(mid + 1).value > element)
                end = mid;
            else
                start = mid;
        }
        return mid;
    }
}

//Implementation
final class LongestIncreasingSubsequenceNlogNImpl implements LongestIncreasingSubsequence {
    int[] pathArray;
    int[] tailIndex;
    int lastLengthArrayIndex = -1;
    private int[] array;

    @Override
    public int find(int[] array) {
        this.array = array;

        pathArray = new int[array.length];
        tailIndex = new int[array.length];
        for (int i = 0; i < pathArray.length; i++)
            pathArray[i] = -1;

        for (int i = 0; i < array.length; i++) {
            int element = array[i];
            if (lastLengthArrayIndex == -1) {
                lastLengthArrayIndex++;
                tailIndex[0] = array[0];
            } else {
                int maxElement = tailIndex[lastLengthArrayIndex];
                System.out.println("----------------");
                System.out.println("i = " + i);
                System.out.println("array = " + Arrays.toString(array));
                System.out.println("maxElement = " + maxElement);
                int minElement = tailIndex[0];
                System.out.println("minElement = " + minElement);
                System.out.println("element = " + element);
                System.out.println("lastLengthArrayIndex = " + lastLengthArrayIndex);
                if (element > maxElement) {
                    lastLengthArrayIndex++;
                    tailIndex[lastLengthArrayIndex] = array[i];
                    pathArray[i] = tailIndex[lastLengthArrayIndex - 1];
                } else if (element < minElement) {
                    tailIndex[0] = array[i];
                } else {
                    int ceil = getCeiling(element);
                    System.out.println("ceil = " + ceil);
                    tailIndex[ceil] = array[i];
                    pathArray[i] = ceil;
                }
            }
            System.out.println("lengthTillNow = " + lastLengthArrayIndex + 1);
            System.out.println("tailIndex = " + Arrays.toString(tailIndex));
            System.out.println("pathArray = " + Arrays.toString(pathArray));
        }
        return lastLengthArrayIndex + 1;
    }

    private int getCeiling(int key) {
        int l = -1;
        int r = lastLengthArrayIndex;
        while (r - l > 1) {
            int m = l + (r - l) / 2;
            if (tailIndex[m] >= key)
                r = m;
            else
                l = m;
        }

        return r;
    }

    private int getCeiling1(int element) {
        int mid = -1;
        int start = 1, end = lastLengthArrayIndex;
        while (true) {

            mid = start + (end - start) / 2;

            //0,1,2,3,4,5; s=0, mid=3, e=5
            //3,4,5 s=3, m=4, e=5
            //4,5 s=4, m=4, e=5
            if (start == end)
                break;
            if (start == end - 1) {
                if (array[tailIndex[start]] > element) {
                    mid = start;
                    break;
                } else if (array[tailIndex[end]] > element) {
                    mid = end;
                    break;
                } else {
                    mid = end + 1;
                    break;
                }
            }

            int elementToCheck = array[mid];
            int elementToCheck_prev = array[tailIndex[mid - 1]];
            if (elementToCheck_prev < element && elementToCheck > element)
                break;
            else if (elementToCheck > element)
                end = mid;
            else
                start = mid;
        }
        return mid;
    }
}


public class Solution {

    public static void main(String[] args) {
        LongestIncreasingSubsequence longestIncreasingSubsequence = new LongestIncreasingSubsequenceNlogNImpl();

        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = scanner.nextInt();
        }
//        int size = LIS.LongestIncreasingSubsequenceLength(array, length);
        int size = longestIncreasingSubsequence.find(array);
        System.out.println(size);
    }
}