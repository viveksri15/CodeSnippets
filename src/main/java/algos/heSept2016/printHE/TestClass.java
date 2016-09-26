package algos.heSept2016.printHE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestClass {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        line = br.readLine();
        int[] count = new int[26];
        for (char c : line.toCharArray())
            count[c - 'a']++;
        int min = Integer.MAX_VALUE;
        String s = "hackerearth";
        int[] countHe = new int[26];

        for (char c : s.toCharArray())
            countHe[c - 'a']++;

        for (int i = 0; i < countHe.length; i++) {
            if (countHe[i] > 0) {
                min = Math.min(count[i] / countHe[i], min);
//                System.out.println((char) (i + 'a') + ", " + count[i] + ", " + countHe[i] + " " + min);
            }
        }

        if (min == Integer.MAX_VALUE)
            min = 0;
        System.out.println(min);
    }
}
