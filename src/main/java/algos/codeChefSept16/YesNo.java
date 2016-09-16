package algos.codeChefSept16;

import java.util.Scanner;

public class YesNo {

    public static void main(String[] args) {
        YesNo numberOfDigits = new YesNo();
        numberOfDigits.solve();
    }

    private void solve() {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < n; i++) {
            String l = sc.nextLine();
            int z = 0, o = 0;
            char[] chars = l.toCharArray();

            for (char c : chars) {
                if (c == '0')
                    z++;
                else
                    o++;
            }
            if (z == 1 || o == 1)
                System.out.println("Yes");
            else
                System.out.println("No");
        }
    }
}
