package algos.abbreviations;

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        for (int i = 0; i < n; i++) {
            solve(in);
        }
    }

    private static void solve(Scanner in) {
        String a = in.nextLine();
        String b = in.nextLine();

        char[] ac = a.toCharArray();
        char[] bc = b.toCharArray();
        boolean found = findByDP(ac, bc);

        if (found)
            System.out.println("YES");
        else
            System.out.println("NO");
    }

    private static boolean findByDP(char[] ac, char[] bc) {
        Solution solution = new Solution();
        return solution.find(ac, bc, 0, 0);
    }

    private Boolean[][] dp;

    private boolean find(char[] ac, char[] bc, int i, int j) {

        if (dp == null) {
            dp = new Boolean[ac.length + 1][bc.length + 1];
        }

        if (dp[i][j] != null)
            return dp[i][j];

        if (i > ac.length - 1 && j > bc.length - 1) {
            dp[i][j] = true;
            return true;
        }

        if (i > ac.length - 1 && j <= bc.length - 1) {
            dp[i][j] = false;
            return false;
        }

        if (j > bc.length - 1 && i <= ac.length - 1) {
            while (i < ac.length) {
                if (ac[i] >= 'a' && ac[i] <= 'z')
                    i++;
                else {
                    dp[i][j] = false;
                    return false;
                }
            }
            dp[i][j] = true;
            return true;
        }


        if (bc[j] == ac[i]) {
            boolean ret = find(ac, bc, i + 1, j + 1);
            dp[i][j] = ret;
            return ret;
        }

        if (ac[i] >= 'a' && bc[j] == (ac[i] - 'a' + 'A')) {
            boolean ret = find(ac, bc, i + 1, j) || find(ac, bc, i + 1, j + 1);
            dp[i][j] = ret;
            return ret;
        }


        if (ac[i] >= 'A' && ac[i] <= 'Z') {
            dp[i][j] = false;
            return false;
        }

        boolean ret = find(ac, bc, i + 1, j);
        dp[i][j] = ret;
        return ret;
    }
}
