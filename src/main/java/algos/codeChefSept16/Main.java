package algos.codeChefSept16;

import java.util.*;

public class Main {

    private Scanner sc = null;

    public static void main(String[] args) {
        Main matrixDivision = new Main();
//        long t0 = System.currentTimeMillis();
//        matrixDivision.solveRand();
        matrixDivision.solve();
//        System.err.println(System.currentTimeMillis() - t0);
    }

    private int[][] a;
    private long[][] mSum;
    int n, lines;
    List<Integer> hl = new ArrayList<>();
    List<Integer> vl = new ArrayList<>();
    List<Integer> hlM = new ArrayList<>();
    List<Integer> vlM = new ArrayList<>();
    long minSum = 0;

    private void solveRand() {
        Random random = new Random();
        n = 451 + random.nextInt(50);
        lines = random.nextInt(500);
        System.out.println(n + "," + lines);
        a = new int[n][n];
        mSum = new long[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int e = random.nextInt(Integer.MAX_VALUE);
                a[i][j] = e;
                if (j == 0)
                    mSum[i][j] = e;
                else
                    mSum[i][j] = mSum[i][j - 1] + e;
            }
        }

        shiftingSolution(0, 0);
        String h = "";
        String v = "";
        Collections.sort(hlM);
        Collections.sort(vlM);
        for (int j = 0; j < lines; j++) {
            h += hlM.get(j) + " ";
            v += vlM.get(j) + " ";
        }
        System.out.println(minSum);
        System.out.println(minSum / Math.pow((n / (lines + 1)), 2));
        System.out.println(hlM);
        System.out.println(vlM);
    }

    private void solve() {
        n = ni();
        lines = ni() - 1;
        a = new int[n][n];
        mSum = new long[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int e = ni();
                a[i][j] = e;
                if (j == 0)
                    mSum[i][j] = e;
                else
                    mSum[i][j] = mSum[i][j - 1] + e;
            }
        }


        shiftingSolution(0, 0);
        String h = "";
        String v = "";
        Collections.sort(hlM);
        Collections.sort(vlM);
        for (int j = 0; j < lines; j++) {
            h += hlM.get(j) + " ";
            v += vlM.get(j) + " ";
        }
        System.out.println(h.trim());
        System.out.println(v.trim());
    }

    private long getSum1(List<Integer> hl, List<Integer> vl, List<Integer> hl_b, List<Integer> vl_r) {

        List<Integer> hla = new LinkedList<>(hl);
        hla.addAll(hl_b);
        List<Integer> vla = new LinkedList<>(vl);
        vla.addAll(vl_r);

        long maxS = Long.MIN_VALUE;

        for (int i = hla.size() - 1; i >= 0; i--) {
            long s = 0;
            for (int j = vla.size() - 1; j >= 0; j--) {
                long l = 0;
                if (j - 1 >= 0)
                    l = mSum[hla.get(i)][vla.get(j - 1)];
                s += (mSum[hla.get(i)][vla.get(j)] - l);
            }
            if (s > maxS)
                maxS = s;
        }

        return maxS;
    }

    private long getSum2(int x, int y, List<Integer> hl, List<Integer> vl) {
        long maxS = Long.MIN_VALUE;

        int miny = -1;

        if (vl.size() > 0)
            miny = vl.get(vl.size() - 1) - 1;
        int count = hl.size() - 1;
//        System.out.println("hl=" + hl + ", miny=" + miny + ", x=" + x);
        do {

            long s = 0;
            Integer upper = 0;
            if (count >= 0)
                upper = hl.get(count);

            count--;

            for (int i = x; i >= upper; i--) {
                long l = 0;
                if (miny >= 0)
                    l = mSum[i][miny];
                s += (mSum[i][y] - l);
            }
//            System.out.println("x=" + x + ", upper=" + upper + ", count=" + count + ", s=" + s);

            x = upper - 1;
            if (s > maxS)
                maxS = s;
        } while (count >= 0);
        return maxS;
    }

    private long getSum(int x, int y, List<Integer> hl, List<Integer> vl) {
        long s = 0;

        int minx = 0, miny = -1;
        if (this.hl.size() > 0)
            minx = this.hl.get(this.hl.size() - 1);

        if (vl.size() > 0)
            miny = vl.get(vl.size() - 1) - 1;

        for (int i = x; i >= minx; i--) {
            long l = 0;
            if (miny >= 0)
                l = mSum[i][miny];
            s += (mSum[i][y] - l);
        }
        //System.out.println("x=" + x + " y=" + y + " minx=" + minx + " miny=" + miny + " s=" + s + " " + hl + " " + vl);
        return s;
    }

    private void shiftingSolution(int x, int y) {
        //place all lines
        for (int i = 1; i <= lines; i++) {
            vl.add(i);
            hl.add(i);
        }

//        minSum = getSum(n - 1, n - 1, hl, vl);
        minSum = Long.MAX_VALUE;
//        hlM.addAll(hl);
//        vlM.addAll(hl);
//        System.out.println(minSum + ", " + hlM + ", " + vlM);

        List<Integer> hl_bottom = new ArrayList<>();

        int iStart = n - 1;
        //we will shift the number of spaces, since n and p can be very close together
        List<Integer> vl_right = new ArrayList<>();
        while (true) {
            int jStart = n - 1;
            List<Integer> vl1 = new ArrayList<>();
            vl1.addAll(vl);
            vl_right.clear();


            while (true) {
                long sum = getSum2(iStart, jStart, hl, vl1);
//                long sum = getSum1(hl, vl1, hl_bottom, vl_right);
//                System.out.println(sum + ", " + iStart + ", " + jStart + ", " + hl + "," + hl_bottom + ";" + vl1 + "," + vl_right);
                if (sum < minSum) {
                    minSum = sum;
//                    System.out.println("MINSUM = " + minSum);
                    hlM.clear();
                    hlM.addAll(hl);
                    hlM.addAll(hl_bottom);
                    vlM.clear();
                    vlM.addAll(vl1);
                    vlM.addAll(vl_right);
                }
                if (vl1.size() > 0) {
                    vl1.remove(vl1.size() - 1);
                    vl_right.add(jStart);
                } else
                    break;
                jStart--;
                if (jStart < 0)
                    break;
            }
            if (hl.size() > 0) {
                hl.remove(hl.size() - 1);
                hl_bottom.add(iStart);
            } else
                break;
            iStart--;
            if (iStart < 0)
                break;
        }
    }

    private int ni() {
        if (sc == null)
            sc = new Scanner(System.in);
        return sc.nextInt();
    }
}
