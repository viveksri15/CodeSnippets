package algos.hackerEarchSept16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Map;
import java.util.WeakHashMap;

public class KGCD {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        int t = Integer.parseInt(line);
        for (int i = 0; i < t; i++) {
            line = br.readLine();
            BigInteger n = new BigInteger(line);
//            BigInteger count = BigInteger.valueOf(0);

            /*for (BigInteger j = BigInteger.ONE; n.compareTo(j) >= 0; j = j.add(BigInteger.ONE)) {
                for (BigInteger k = BigInteger.ONE; n.compareTo(k) >= 0; k = k.add(BigInteger.ONE)) {
                    boolean kgcd = kgcd(j, k);
//                    System.out.println(j + " " + k + " " + kgcd);
                    if (kgcd) {
                        count = count.add(BigInteger.ONE);
                    }
                }
            }*/
            BigInteger count1 = getCount(n);
//            System.out.println(count + ", " + count1);
            System.out.println(count1);
        }
    }

    private static BigInteger getCount(BigInteger n) {
        BigInteger count = BigInteger.valueOf(-1);

        for (BigInteger j = BigInteger.ONE; n.compareTo(j) >= 0; j = j.add(BigInteger.ONE)) {
            count = count.add(n.divide(j)).add(BigInteger.ONE);
//            for (BigInteger k = BigInteger.valueOf(2); k.compareTo(j) < 0; k = k.add(BigInteger.ONE)) {
//                if (kgcd(j, k))
//                    count = count.add(BigInteger.ONE);
//            }
        }

        return count;
    }

    private static Map<BigInteger, Map<BigInteger, Boolean>> cache = new WeakHashMap<>();

    private static boolean kgcd(BigInteger a, BigInteger b) {
        BigInteger a1 = a, b1 = b;
        while (a.compareTo(BigInteger.ZERO) > 0 && b.compareTo(BigInteger.ZERO) > 0) {
            Map<BigInteger, Boolean> c1 = cache.get(a);
            if (c1 != null) {
                Boolean isPresent = c1.get(b);
                if (isPresent != null) {
                    return isPresent;
                }
            }

            a = a.subtract(b);
            BigInteger t = a;
            a = b;
            b = t;
        }
        boolean val = a.add(b).compareTo(gcd(a1, b1)) == 0;
        Map<BigInteger, Boolean> c1 = cache.get(a1);
        if (c1 == null) {
            c1 = new WeakHashMap<>();
            cache.put(a1, c1);
        }
        c1.put(b1, val);
        return val;
    }

    private static BigInteger gcd(BigInteger a, BigInteger b) {
        while (b.compareTo(BigInteger.ZERO) > 0) {
            a = a.mod(b);
            BigInteger t = a;
            a = b;
            b = t;
        }
        return a;
    }
}
