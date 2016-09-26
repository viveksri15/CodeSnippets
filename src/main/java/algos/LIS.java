package algos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vivek on 26/09/16.
 */
public class LIS {

    public static void main(String[] args) {
        LIS lis = new LIS();
        int length = lis.getLongestLength(new int[]{
                1, 8, 19, 1, 2, 4, 5, 0, 1, 2, 3, 4, 5
        });
        System.out.println(length);
    }

    private int getLongestLength(int[] arr) {
        int maxL = 0;

        List<Integer> maxTillNow = new ArrayList<>();
        List<Integer> maxTillNowLength = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            int index = Collections.binarySearch(maxTillNow, arr[i]);
            if (index < 0) {
                int pos = -(index + 1);
                int last = 0;

                if (pos == maxTillNow.size()) {
                    if (pos > 0)
                        last = maxTillNowLength.get(pos - 1);
                    maxTillNow.add(arr[i]);
                    maxTillNowLength.add(last + 1);

                    maxL = Math.max(maxL, last + 1);
                } else {
                    maxTillNow.set(pos, arr[i]);
                    if (pos > 0)
                        last = maxTillNowLength.get(pos - 1);
                    maxTillNowLength.set(pos, last + 1);
                    maxL = Math.max(maxL, last + 1);
                }
            } else {
                int curLen = maxTillNowLength.get(index);
                int last = 0;
                if (index > 0)
                    last = maxTillNowLength.get(index - 1);
                last++;
                if (curLen < last) {
                    maxTillNow.set(index, arr[i]);
                    maxTillNowLength.set(index, last);
                    maxL = Math.max(maxL, last);
                }
            }
        }

        return maxL;
    }
}
