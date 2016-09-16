package algos.booking.com.q1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {
    public static void main(String args[]) throws Exception {
        int[] sides = new int[4];
        int sq = 0, rct = 0, op = 0;

        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                try {
                    String s = br.readLine();
                    if (s == null || "".equals(s.trim()))
                        break;
                    String[] split = s.split("\\s");
                    sides[0] = Integer.parseInt(split[0]);
                    sides[1] = Integer.parseInt(split[1]);
                    sides[2] = Integer.parseInt(split[2]);
                    sides[3] = Integer.parseInt(split[3]);
                    if (isInvalid(sides))
                        op++;
                    else if (isSquare(sides))
                        sq++;
                    else
                        rct++;
                } catch (Exception e) {

                }
            }

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        System.out.println(sq + " " + rct + " " + op);
    }

    private static boolean isSquare(int[] sides) {
        if (sides[0] == sides[1] && sides[1] == sides[2] && sides[2] == sides[3])
            return true;
        return false;
    }

    private static boolean isInvalid(int[] sides) {
        for (int i = 0; i < sides.length; i++)
            if (sides[i] <= 0)
                return true;

        if (sides[0] != sides[2] || sides[1] != sides[3])
            return true;

        return false;
    }
}