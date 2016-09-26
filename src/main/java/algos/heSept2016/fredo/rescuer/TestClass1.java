package algos.heSept2016.fredo.rescuer;

import java.io.*;
import java.math.BigDecimal;
import java.util.InputMismatchException;

public class TestClass1 {
    public static void main(String[] args) {
        InputReader1 in = new InputReader1(System.in);

        int t = in.readInt();
        for (int i = 0; i < t; i++) {

            int x1 = in.readInt();
            int y1 = in.readInt();
            int x2 = in.readInt();
            int y2 = in.readInt();
            int v1 = in.readInt();
            int v2 = in.readInt();


            double minTime = solve(x1, y1, x2, y2, v1, v2);
            String minTimeS = BigDecimal.valueOf((double) Math.round(minTime * 100000) / 100000).setScale(5, BigDecimal.ROUND_HALF_UP).toString();
            System.out.println(minTimeS);
        }
    }

    private static double solve(int x1, int y1, int x2, int y2, int v1, int v2) {

        int x = x2;

        if (v1 < v2)
            x = x1;

        double a1 = Math.sqrt((Math.pow(x2 - x, 2)) + Math.pow(y2, 2)) / v2;
        double b1 = Math.sqrt((Math.pow(x - x1, 2)) + Math.pow(y1, 2)) / v1;

        return a1 + b1;
    }
}

class InputReader1 {

    private InputStream stream;
    private byte[] buf = new byte[1024 * 8];
    private int curChar;
    private int numChars;
    private SpaceCharFilter filter;

    public InputReader1(InputStream stream) {
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

class OutputWriter1 {
    private final PrintWriter writer;

    public OutputWriter1(OutputStream outputStream) {
        writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
    }

    public OutputWriter1(Writer writer) {
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
