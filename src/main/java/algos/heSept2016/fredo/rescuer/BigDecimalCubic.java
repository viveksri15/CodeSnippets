package algos.heSept2016.fredo.rescuer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import static java.math.BigDecimal.ROUND_HALF_UP;

public class BigDecimalCubic {

    private static final double TWO_PI = 2.0 * Math.PI;
    private static final double FOUR_PI = 4.0 * Math.PI;

    public int nRoots;

    public double x1;

    public double x2;

    public double x3;


    public void solve
            (BigDecimal a,
             BigDecimal b,
             BigDecimal c,
             BigDecimal d) throws Exception {

        // Normalize coefficients.
        BigDecimal denom = a;
        a = b.divide(denom, MathContext.DECIMAL64);
        b = c.divide(denom, MathContext.DECIMAL64);
        c = d.divide(denom, MathContext.DECIMAL64);
        BigInteger TWO = BigInteger.valueOf(2);

        // Commence solution.
        BigDecimal a_over_3 = a.divide(BigDecimal.valueOf(3), MathContext.DECIMAL64);
        BigDecimal Q = (BigDecimal.valueOf(3).multiply(b).subtract(a.pow(2))).divide(BigDecimal.valueOf(9), MathContext.DECIMAL64);
        BigDecimal Q_CUBE = Q.pow(3);
        BigDecimal R = (BigDecimal.valueOf(9).multiply(a).multiply(b)
                .subtract(BigDecimal.valueOf(27).multiply(c)).subtract(BigDecimal.valueOf(2).multiply(a.pow(3))))
                .divide(BigDecimal.valueOf(54), MathContext.DECIMAL64);
        BigDecimal R_SQR = R.pow(2);
        BigDecimal D = Q_CUBE.add(R_SQR);

        if (D.compareTo(BigDecimal.ZERO) < 0) {
            // Three unequal real roots.
            nRoots = 3;
            double theta = Math.acos(R.divide(sqrt(BigDecimal.ZERO.subtract(Q_CUBE), 150), MathContext.DECIMAL32).doubleValue());
            double SQRT_Q = Math.sqrt(BigDecimal.ZERO.subtract(Q).doubleValue());
            x1 = 2.0 * SQRT_Q * Math.cos(theta / 3.0) - a_over_3.doubleValue();
            x2 = 2.0 * SQRT_Q * Math.cos((theta + TWO_PI) / 3.0) - a_over_3.doubleValue();
            x3 = 2.0 * SQRT_Q * Math.cos((theta + FOUR_PI) / 3.0) - a_over_3.doubleValue();
            sortRoots();
        } else if (D.compareTo(BigDecimal.ZERO) > 0) {
            // One real root.
            nRoots = 1;
            BigDecimal SQRT_D = sqrt(D, 150);
            double S = Math.cbrt(R.add(SQRT_D).doubleValue());
            double T = Math.cbrt(R.subtract(SQRT_D).doubleValue());
            x1 = (S + T) - a_over_3.doubleValue();
            x2 = Double.NaN;
            x3 = Double.NaN;
        } else {
            nRoots = 3;
            double CBRT_R = Math.cbrt(R.doubleValue());
            x1 = 2 * CBRT_R - a_over_3.doubleValue();
            x2 = x3 = CBRT_R - a_over_3.doubleValue();
            sortRoots();
        }
    }

    private void sortRoots() {
        if (x1 < x2) {
            double tmp = x1;
            x1 = x2;
            x2 = tmp;
        }
        if (x2 < x3) {
            double tmp = x2;
            x2 = x3;
            x3 = tmp;
        }
        if (x1 < x2) {
            double tmp = x1;
            x1 = x2;
            x2 = tmp;
        }
    }

    public static BigDecimal sqrt(BigDecimal A, final int SCALE) {
        BigDecimal x0 = new BigDecimal("0");
        BigDecimal x1 = new BigDecimal(Math.sqrt(A.doubleValue()));
        while (!x0.equals(x1)) {
            x0 = x1;
            x1 = A.divide(x0, SCALE, ROUND_HALF_UP);
            x1 = x1.add(x0);
            x1 = x1.divide(BigDecimal.valueOf(2), SCALE, ROUND_HALF_UP);

        }
        return x1;
    }

    public static void main(String[] args) throws Exception {
        BigDecimalCubic cubic = new BigDecimalCubic();
        cubic.solve(BigDecimal.TEN, BigDecimal.valueOf(6), BigDecimal.valueOf(3), BigDecimal.valueOf(20));
        System.out.println(cubic.x1);
        System.out.println(cubic.x2);
        System.out.println(cubic.x3);
    }
}