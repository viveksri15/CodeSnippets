package algos.hackerEarchSept16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class SuperString {
    public static void main(String[] args) throws IOException {
//        test();
        real();
    }

    private static void test() throws IOException {
        long t0 = System.currentTimeMillis();
        Random random = new Random();
        int t = 500;
        for (int i = 0; i < t; i++) {
            int n = 50;
            int k = 1;//1000;//1 + random.nextInt(1000);
            if (k > n)
                k = n;
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < n; j++)
                stringBuilder.append((char) ('a' + random.nextInt('z' - 'a')));
            solve1(n, k, stringBuilder.toString());
            solve(n, k, stringBuilder.toString());
        }
        System.out.println(System.currentTimeMillis() - t0);
    }

    private static void real() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        int t = Integer.parseInt(line);
        for (int i = 0; i < t; i++) {
            line = br.readLine();
            String[] split = line.split("\\s");
            int n = Integer.parseInt(split[0]);
            int k = Integer.parseInt(split[1]);
            line = br.readLine();
            solve(n, k, line);
        }
    }

    private static void solve1(int n, int k, String line) {
        //        System.out.println("line=" + line);
        int count = 0;
        String smallest = null;

        String smallestK = line;

        for (int i = 0; i < k; i++) {
            line = smallestK;
            for (int l = 0; l < n; l++) {
                for (int r = l; r < n; r++) {
                    count++;
                    if (l == 0 && r == n - 1)
                        continue;
                    String substring1 = getSubstring(line, 0, l - 1);
                    String substring2 = getSubstring(line, r + 1, n - 1);
                    String line1 = substring1 + substring2;
                    if ("".equals(line1))
                        continue;
                    if (smallestK == null)
                        smallestK = line1;
                    else if (smallestK.compareTo(line1) > 0)
                        smallestK = line1;

//                    System.out.println("k=" + i + ", l=" + l + ", r=" + r + ", line1 = " + line1 + ", line=" + line + ", substring1=" + substring1 + ", substring2=" + substring2);
                }
            }
            if (smallestK == null)
                continue;
            if (smallest == null)
                smallest = smallestK;
            else if (smallest.compareTo(smallestK) > 0)
                smallest = smallestK;
            else if (smallest.compareTo(smallestK) == 0)
                break;
        }
//        System.out.println("n=" + n + ", k=" + k + ", smallest = " + smallest + ", count = " + count);
        System.out.println(smallest);
    }

    private static void solve(int n, int k, String line) {
        char small = 'z';
        for (char c : line.toCharArray()) {
            if (c < small)
                small = c;
        }
        if (k >= 2 || line.charAt(0) == small) {
            System.out.println(small);
        } else {
            String smallest = null;
            char[] charArray = line.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                char c = charArray[i];
                if (c == small) {
                    String line1 = line.substring(i, line.length());
                    if (smallest == null)
                        smallest = line1;
                    else if (smallest.compareTo(line1) > 0)
                        smallest = line1;
                }
            }
            System.out.println(smallest);
        }
    }

    private static String getSubstring(String line, int beginIndex, int endIndex) {
        if (beginIndex > endIndex)
            return "";
        if (endIndex < 0)
            return "";
        if (endIndex >= line.length())
            return "";
        return line.substring(beginIndex, endIndex + 1);
    }
}
