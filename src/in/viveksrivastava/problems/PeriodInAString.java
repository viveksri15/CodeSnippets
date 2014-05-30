package in.viveksrivastava.problems;

/**
 * Created by Vivek on 30-05-2014.
 */
public class PeriodInAString {
    //find period in a string
    public int solution(int N) {
        if (N < 0)
            return -1;
        int period = -1;
        String binary = Integer.toBinaryString(N);
        int lenToCheck = 1;
        //		System.out.println(binary);
        while (lenToCheck < binary.length() / 2) {
            String s = binary.substring(0, lenToCheck);
            int missingIndex = -1;
            boolean foundPeriod = true;
            for (int k = lenToCheck; k < binary.length(); k += s.length()) {
                if (k + s.length() > binary.length()) {
                    break;
                }
                String s1 = binary.substring(k, k + s.length());
                //				System.out.println("lenToCheck=" + lenToCheck + " k=" + k
                //						+ " s=" + s + " s1=" + s1);
                if (!s.equals(s1)) {
                    missingIndex = k;
                    foundPeriod = false;
                    break;
                }
            }
            if (foundPeriod) {
                period = lenToCheck;
                break;
            }
            lenToCheck += missingIndex;
            //			System.out.println("lenToCheck=" + lenToCheck + " missingIndex="
            //					+ missingIndex);
        }

        return period;
    }
}
