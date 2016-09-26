package algos;


import java.math.BigDecimal;

/**
 * Created by vivek on 21/09/16.
 */
public class AngleTest {

    public static void main(String[] args) {
        getAngle(-1, 1, 0, 0, -1, -1);
        getAngle(1, 1, 2, 2, 3, 3);
        getAngle(1, 1, 3, 3, 2, 2);
        getAngle(0, 0, -1, 1, 1, 1);
        getAngle(0, 0, -1, 1, -2, 1);
        getAngle(0, 0, -1, 1, -2, 0);
        getAngle(0, 0, -1, 1, -3, 0);
        getAngle(0, 0, -1, 1, 0, 1);
        getAngle(0, 0, -1, 1, 0, -1);
        getAngle(0, 0, -1, 1, -1, 2);
        getAngle(0, 0, -1, 1, -1, 3);
        getAngle(0, 0, -3, 3, -4, 5);
        getAngle(-3, 3, -1, 1, -1, 3);
    }

    private static double getAngle(int x1, int y1, int x2, int y2, int x3, int y3) {

        BigDecimal v1X = BigDecimal.valueOf(x2 - x1);
        BigDecimal v1Y = BigDecimal.valueOf(y2 - y1);
        BigDecimal v2X = BigDecimal.valueOf(x3 - x2);
        BigDecimal v2Y = BigDecimal.valueOf(y3 - y2);
        double angle = Math.toDegrees(
                Math.abs(Math.atan2(
                        v1X.multiply(v2Y).subtract(v1Y.multiply(v2X)).doubleValue(),
                        v1X.multiply(v2X).add(v1Y.multiply(v2Y)).doubleValue())
                ));
        System.out.println(angle);

        return angle;
    }
}
