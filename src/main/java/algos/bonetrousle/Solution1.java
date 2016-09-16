package algos.bonetrousle;

import java.math.BigInteger;
import java.util.*;

public class Solution1 {
    private int count = 0;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        for (int i = 0; i < n; i++) {
            solve(in);
        }
    }

    private static void solve(Scanner in) {
        BigInteger n = BigInteger.valueOf(in.nextLong());
        BigInteger k = BigInteger.valueOf(in.nextLong());
        BigInteger b = BigInteger.valueOf(in.nextLong());
        Solution1 solution = new Solution1();

        boolean found = solution.find(k, n, k, b);

        if (!found)
            System.out.println(-1);
        else
            solution.print();
    }

    private List<BigInteger> sol = new ArrayList<>();

    private Map<BigInteger, Map<BigInteger, Map<BigInteger, Boolean>>> integerMapMap = new HashMap<>();
    private static BigInteger TWO = BigInteger.valueOf(2);

    private boolean find(BigInteger i, BigInteger n, BigInteger k, BigInteger b) {

        Boolean fromDP = getFromDP(i, n, b);

        if (fromDP != null)
            return fromDP;

        if (n.equals(BigInteger.ZERO) && b.equals(BigInteger.ZERO)) {
            return true;
        }

        if (preCheck(i, n, b))
            return false;

        ++count;
        System.out.println(i + " " + n + " " + " b=" + b + " " + count);


        long STEPS = Math.max(n.divide(BigInteger.TEN).longValue(), 100);
        for (long j = Math.min(i.longValue(), STEPS); j >= 1; j /= 2) {

            BigInteger jB = BigInteger.valueOf(j);
            BigInteger jBI = i.subtract(jB);
            boolean found = find(jBI, n.subtract(i), k, b.subtract(BigInteger.ONE));

            if (found) {
                sol.add(i);
                addToDP(i, n, b, true);
                return true;
            }

            found = find(jBI, n, k, b);

            if (found)
                addToDP(i, n, b, true);
            else
                addToDP(i, n, b, false);

            if (found)
                return true;
        }
        return false;
    }

    private boolean preCheck(BigInteger i, BigInteger n, BigInteger b) {
        if (n.compareTo(BigInteger.ZERO) <= 0 || b.compareTo(BigInteger.ZERO) <= 0)
            return true;

        if (i.compareTo(BigInteger.ZERO) <= 0)
            return true;

        if (i.compareTo(b) > 0)
            return false;

        if (b.compareTo(BigInteger.ONE) == 0 && sol.contains(n))
            return false;

        if (i.multiply(i.add(BigInteger.ONE)).divide(TWO).compareTo(n) < 0)
            return true;

        if (b.compareTo(BigInteger.valueOf(100)) <= 0) {
            BigInteger sum = BigInteger.ZERO;
            boolean found = false;
            for (int j = b.intValue(); j >= 1; j--) {
                if (!sol.contains(BigInteger.valueOf(j)))
                    sum = sum.add(BigInteger.valueOf(j));
                if (sum.compareTo(n) >= 0) {
                    found = true;
                    break;
                }
                if (!found)
                    return false;
            }
        }

        if (i.compareTo(BigInteger.valueOf(1000)) <= 0) {
            long sum = 0L;
            for (int j = 1; j < i.intValue(); j++)
                if (!sol.contains(BigInteger.valueOf(j)))
                    sum += j;
            if (BigInteger.valueOf(sum).compareTo(n) < 0)
                return true;
        }

        return false;
    }

    private Boolean getFromDP(BigInteger i, BigInteger n, BigInteger b) {
        Map<BigInteger, Map<BigInteger, Boolean>> integerMapMap = this.integerMapMap.get(i);
        if (integerMapMap == null)
            return null;
        Map<BigInteger, Boolean> integerBooleanMap = integerMapMap.get(n);
        if (integerBooleanMap == null)
            return null;
        return integerBooleanMap.get(b);
    }

    private void addToDP(BigInteger i, BigInteger n, BigInteger b, boolean value) {
        Map<BigInteger, Map<BigInteger, Boolean>> integerMapMap = this.integerMapMap.get(i);
        if (integerMapMap == null) {
            integerMapMap = new HashMap<>();
            this.integerMapMap.put(i, integerMapMap);
        }
        Map<BigInteger, Boolean> integerBooleanMap = integerMapMap.get(n);
        if (integerBooleanMap == null) {
            integerBooleanMap = new HashMap<>();
            integerMapMap.put(n, integerBooleanMap);
        }
        integerBooleanMap.put(b, value);
    }

    private void print() {
        StringBuilder stringBuilder = new StringBuilder();
        for (BigInteger i : sol)
            stringBuilder.append(i.longValue()).append(" ");
        System.out.println(stringBuilder.toString().trim());
    }
}