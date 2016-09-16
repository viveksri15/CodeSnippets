package algos.codeChefSept16;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by vivek on 02/09/16.
 */
public class MatrixDivision1 {

    Scanner sc = null;

    public static void main(String[] args) {
        MatrixDivision1 matrixDivision = new MatrixDivision1();
        matrixDivision.solve();
    }

    private int[][] a;
    //    private long[][] sum;
    int n, p;
    List<Integer> hl = new ArrayList<>();
    List<Integer> vl = new ArrayList<>();
    List<Integer> hlM = new ArrayList<>();
    List<Integer> vlM = new ArrayList<>();
    long maxSum = 0;

    private void solve() {
        n = ni();
        p = ni();
        a = new int[n][n];
//        sum = new long[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int e = ni();
                a[i][j] = e;
            }
            System.out.println();
        }

        randomize();
    }

    private void randomize() {
        
    }

    private long getSum(int x, int y) {
        long s = 0;


        int minx = 0, miny = 0;
        if (hl.size() > 0)
            minx = hl.get(hl.size() - 1);

        if (vl.size() > 0)
            miny = vl.get(vl.size() - 1);

        for (int i = x; i >= minx; i--)
            for (int j = y; j >= miny; j--)
                s += a[i][j];
//        System.out.println("x=" + x + " y=" + y + " minx=" + minx + " miny=" + miny + " s=" + s);
        return s;
    }

    long dp(int x, int y, int h, int v) {
        if (x >= n || y >= n)
            return 0;

        System.out.println(x + "," + y + " ;; " + hl + " , " + vl + ", h,v=" + h + "," + v);

        long max = 0l;

        //pass

        max = dp(x + 1, y + 1, h, v);

        //hr
        if (x > 0) {
            max = Math.max(max, getSum(x, y));
            ah(x);
            if (h + 1 == p - 1)
                max = Math.max(max, getSum(n - 1, n - 1));
            else if (hl.size() < p - 1)
                max = Math.max(max, dp(x + 1, y, h + 1, v));
        }

        if (y > 0) {
            //both
            av(y);
            if (hl.size() == p - 1 && vl.size() == p - 1) {
                long sum = getSum(n - 1, n - 1);
                System.out.println("sum = " + sum);
                max = Math.max(max, sum);
                System.out.println("max = " + max);
                System.out.println(hl);
                System.out.println(vl);
            } else if (vl.size() < p - 1 && hl.size() < p - 1)
                max = Math.max(max, dp(x + 1, y + 1, h + 1, v + 1));
        }
        if (x > 0)
            //vr
            rh();

        if (v + 1 == p - 1)
            max = Math.max(max, getSum(n - 1, n - 1));
        else if (vl.size() < p - 1)
            max = Math.max(max, dp(x, y + 1, h, v + 1));

        if (y > 0)
            rv();

        return max;
    }

    private void rh() {
        if (hl.size() > 0)
            hl.remove(hl.size() - 1);
    }

    private void rv() {
        if (vl.size() > 0)
            vl.remove(vl.size() - 1);
    }

    private void av(int y) {
        vl.add(y);
    }

    private void ah(int x) {
        hl.add(x);
    }

    private int ni() {
        if (sc == null)
            sc = new Scanner(System.in);
        return sc.nextInt();
    }
}
