package in.viveksrivastava.problems;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vivek on 30-05-2014.
 */
public class FirstUniqNumber {
    public int solution(int[] A) {

        int uniq = -1;
        if (A == null || A.length == 0)
            return uniq;
        Map<Integer, Integer> counter = new HashMap<Integer, Integer>();
        for (int a : A) {
            Integer c = counter.get(a);
            if (c == null) {
                c = 0;
            }
            c++;
            counter.put(a, c);
        }
        for (int a : A) {
            Integer c = counter.get(a);
            if (c == 1) {
                uniq = a;
                break;
            }
        }
        return uniq;
    }
}
