package algos.booking.com.q1.q4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {
    public static void main(String args[]) throws Exception {

        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(System.in));
        try {
            long n = getaLong(br);
            long m = getaLong(br);
            Map<Long, Long> diffs = new HashMap<>();
            List<Long> vals = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                Long a = getaLong(br);
                Long count = diffs.get(a);
                if (count == null)
                    count = 0l;
                count++;
                diffs.put(a, count);
                vals.add(a);
            }

            boolean found = false;
            for (int i = 0; i < vals.size(); i++) {
                long a = vals.get(i);
                if (diffs.containsKey(n - a)) {
                    if (a == n - a) {
                        Long count = diffs.get(a);
                        if (count != null && count > 1) {
                            found = true;
                            break;
                        }
                    } else {
                        found = true;
                        break;
                    }
                }
            }
            if (found)
                System.out.println(1);
            else
                System.out.println(0);
        } catch (Exception e) {
            System.out.println(0);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private static long getaLong(BufferedReader br) throws IOException {
        return Long.parseLong(br.readLine().trim());
    }
}
