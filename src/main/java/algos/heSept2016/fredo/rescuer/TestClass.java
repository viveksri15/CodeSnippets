package algos.heSept2016.fredo.rescuer;

import java.io.*;
import java.math.BigDecimal;
import java.util.InputMismatchException;

public class TestClass {


    public static void main(String[] args) throws Exception {

        InputReader in = new InputReader(System.in);

        int t = in.readInt();
        for (int i = 0; i < t; i++) {

            int x1 = in.readInt();
            int y1 = in.readInt();
            int x2 = in.readInt();
            int y2 = in.readInt();
            int v1 = in.readInt();
            int v2 = in.readInt();

            double minTime = solve(x1, y1, x2, y2, v1, v2);
            String minTimeS = BigDecimal.valueOf(minTime).setScale(5, BigDecimal.ROUND_HALF_UP).toString();
            System.out.println(minTimeS);
        }

    }

    private static double solve(int x1, int y1, int x2, int y2, int v1, int v2) throws Exception {

        double a = (double) x1;
        double c = (double) x2;

        int steps = 4;
        int iterations = 10000;
        double time = Double.MAX_VALUE;

        double nMinX = getTime((double) x1, (double) y1, (double) x2, (double) y2, a, c, (double) v1, (double) v2, steps, iterations);
        double mtime = getTime((double) x1, (double) y1, (double) x2, (double) y2, (double) v1, (double) v2, nMinX);

        time = Math.min(time, mtime);
        return time;
    }

    private static double getTime(double aOrig, double bOrig, double cOrig, double dOrig, double a, double c, double m, double n, int steps, int iterations) {
        double minx = -1;
        double count = 0;
        do {
            double xstep = (c - a) / steps;
            double[] x = new double[steps + 1];

            x[0] = a;
            x[steps] = c;
            for (int i = 1; i < steps; i++)
                x[i] = a + xstep * i;

            boolean found = false;

            for (int i = 1; i < x.length - 1; i++) {
                count++;
                double time1 = BigDecimal.valueOf(getTime(aOrig, bOrig, cOrig, dOrig, m, n, x[i - 1])).setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue();
                double time2 = BigDecimal.valueOf(getTime(aOrig, bOrig, cOrig, dOrig, m, n, x[i])).setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue();
                double time3 = BigDecimal.valueOf(getTime(aOrig, bOrig, cOrig, dOrig, m, n, x[i + 1])).setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue();

                /*System.out.println(time1 + ", " + time2 + ", " + time3
                        + "," + x[i - 1] + "," + x[i] + "," + x[i + 1]
                );*/

                if (time1 == time2 || time2 == time3) {
                    minx = x[i];
                    found = true;
                    break;
                } else if (time1 >= time2 && time2 <= time3) {
                    a = x[i - 1];
                    c = x[i + 1];
                    break;
                }

                if (i == x.length - 2) {
                    if (time1 < time2) {
                        c = x[1];
                    } else {
                        a = x[i];
                    }
                }
            }

            if (found)
                break;
        } while (count < iterations);
        return minx;
    }

    private static double getTime(double a, double b, double c,
                                  double d, double m, double n, double x) {


        double a1 = Math.sqrt((Math.pow(c - x, 2)) + Math.pow(d, 2)) / n;
        double b1 = Math.sqrt((Math.pow(x - a, 2)) + Math.pow(b, 2)) / m;

        return a1 + b1;
    }

    private static double getSlope(double a, double b, double c,
                                   double d, double m, double n, double x) {
        return ((x - a) / (m * Math.sqrt(Math.pow((x - a), 2) + Math.pow(b, 2)))) +
                ((x - c) / (m * Math.sqrt(Math.pow((x - c), 2) + Math.pow(d, 2))));
    }
}

class InputReader {

    private InputStream stream;
    private byte[] buf = new byte[1024 * 8];
    private int curChar;
    private int numChars;
    private SpaceCharFilter filter;

    public InputReader(InputStream stream) {
        this.stream = stream;
    }

    public int read() {
        if (numChars == -1)
            throw new InputMismatchException();
        if (curChar >= numChars) {
            curChar = 0;
            try {
                numChars = stream.read(buf);
            } catch (IOException e) {
                throw new InputMismatchException();
            }
            if (numChars <= 0)
                return -1;
        }
        return buf[curChar++];
    }

    public int readInt() {
        int c = read();
        while (isSpaceChar(c))
            c = read();
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = read();
        }
        int res = 0;
        do {
            if (c < '0' || c > '9')
                throw new InputMismatchException();
            res *= 10;
            res += c - '0';
            c = read();
        } while (!isSpaceChar(c));
        return res * sgn;
    }

    public String readString() {
        int c = read();
        while (isSpaceChar(c))
            c = read();
        StringBuilder res = new StringBuilder();
        do {
            res.appendCodePoint(c);
            c = read();
        } while (!isSpaceChar(c));
        return res.toString();
    }

    public boolean isSpaceChar(int c) {
        if (filter != null)
            return filter.isSpaceChar(c);
        return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
    }

    public String next() {
        return readString();
    }

    public interface SpaceCharFilter {
        public boolean isSpaceChar(int ch);
    }
}

class OutputWriter {
    private final PrintWriter writer;

    public OutputWriter(OutputStream outputStream) {
        writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
    }

    public OutputWriter(Writer writer) {
        this.writer = new PrintWriter(writer);
    }

    public void print(Object... objects) {
        for (int i = 0; i < objects.length; i++) {
            if (i != 0)
                writer.print(' ');
            writer.print(objects[i]);
        }
    }

    public void printLine(Object... objects) {
        print(objects);
        writer.println();
    }

    public void close() {
        writer.close();
    }

    public void flush() {
        writer.flush();
    }

}

class IOUtils {

    public static int[] readIntArray(InputReader in, int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++)
            array[i] = in.readInt();
        return array;
    }

}