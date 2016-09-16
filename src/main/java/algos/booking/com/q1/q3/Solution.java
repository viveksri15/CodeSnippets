package algos.booking.com.q1.q3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {
    public static void main(String[] args) {
        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String s = br.readLine();
            String[] numbers = s.split("\\s");
            StringBuilder stringBuilder = new StringBuilder();
            int last = 0, count = 0;
            for (String n : numbers) {
                if (count == 0)
                    append(stringBuilder, Integer.parseInt(n), count);
                else
                    append(stringBuilder, Integer.parseInt(n) - last, count);
                last = Integer.parseInt(n);
                count++;
            }

            System.out.println(stringBuilder.toString().trim());
        } catch (
                IOException e) {

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private static void append(StringBuilder stringBuilder, int n, int count) {
        if (count > 0 && (n < -127 || n > 127))
            stringBuilder.append("-128 ");
        stringBuilder.append(n).append(" ");
    }
}
