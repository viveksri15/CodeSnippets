package algos.codeChefSept16.cookies;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < t; i++) {
            int n = Integer.parseInt(sc.nextLine());
            int[] scores = new int[n];
            for (int j = 0; j < n; j++) {
                scores[j] = solve(sc);
            }
            int max = scores[0];
            String res = "chef";
            for (int j = 1; j < n; j++) {
                if (max == scores[j])
                    res = "tie";
                else if (scores[j] > max) {
                    max = scores[j];
                    res = (j + 1) + "";
                }
            }
            System.out.println(res);
            sc.nextLine();
        }
    }

    private static int solve(Scanner sc) {
        int n = sc.nextInt();
        int score = n;
        int[] x = new int[6];
        Arrays.fill(x, 0);
        for (int k = 0; k < n; k++) {
            int i = sc.nextInt() - 1;
            x[i]++;
        }


        while (true) {
            int c = 0;
            for (int i = 0, xLength = x.length; i < xLength; i++) {
                int aX = x[i];
                if (aX > 0) {
                    c++;
                    x[i]--;
                }
                if (c == 6)
                    break;
            }
            if (c == 6)
                score += 4;
            else if (c == 5)
                score += 2;
            else if (c == 4)
                score += 1;
            else
                break;
        }

        return score;
    }
}
