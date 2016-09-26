package algos.heSept2016.fredo;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class TestClass {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        OutputWriter out = new OutputWriter(System.out);

        int n = in.readInt();
        LinkedHashMap<BigDecimal, Long[]> countMap = new LinkedHashMap<>();
        TreeMap<Long, BigDecimal[]> atleastAndExactFrequencyMap = new TreeMap<>();


        for (int i = 0; i < n; i++) {
            BigDecimal bigDecimal = new BigDecimal(in.readString());
            Long[] counterAndFirstIndex = countMap.get(bigDecimal);
            if (counterAndFirstIndex == null) {
                counterAndFirstIndex = new Long[2];
                counterAndFirstIndex[1] = (long) i;
                countMap.put(bigDecimal, counterAndFirstIndex);
            }
            Long counter = counterAndFirstIndex[0];
            if (counter == null)
                counter = 0L;

            counter++;
            counterAndFirstIndex[0] = counter;

            BigDecimal[] atleastFrequencyA = atleastAndExactFrequencyMap.get(counter);
            BigDecimal atleastFrequency = null;

            if (atleastFrequencyA != null)
                atleastFrequency = atleastFrequencyA[0];

            if (atleastFrequency == null) {
                if (atleastFrequencyA == null) {
                    atleastFrequencyA = new BigDecimal[2];
                    atleastAndExactFrequencyMap.put(counter, atleastFrequencyA);
                }
                atleastFrequencyA[0] = bigDecimal;
            } else {
                Long[] counterAndFirstIndex1 = countMap.get(atleastFrequency);

                if (counterAndFirstIndex[1] < counterAndFirstIndex1[1]) {
                    atleastFrequencyA[0] = bigDecimal;
                }
            }
        }


        /*for (BigDecimal bigDecimal : countMap.keySet()) {
            Long[] longs = countMap.get(bigDecimal);
            System.out.print(bigDecimal + "=" + longs[0] + "," + longs[1] + "\t");
        }
        System.out.println();

        for (Long l : atleastAndExactFrequencyMap.keySet()) {
            BigDecimal[] bigDecimals = atleastAndExactFrequencyMap.get(l);
            if (bigDecimals != null) {
                System.out.print(l + "=" + bigDecimals[0] + "," + bigDecimals[1] + "\t");
            } else
                System.out.print(l + "=" + null + "\t");
        }
        System.out.println();
*/
        for (Map.Entry<BigDecimal, Long[]> entry : countMap.entrySet()) {
            BigDecimal number = entry.getKey();
            Long[] counterAndFirstIndex = entry.getValue();
            Long counter = counterAndFirstIndex[0];

            BigDecimal[] atleastFrequencyA = atleastAndExactFrequencyMap.get(counter);
            if (atleastFrequencyA == null) {
                atleastFrequencyA = new BigDecimal[2];
                atleastAndExactFrequencyMap.put(counter, atleastFrequencyA);
            }

            BigDecimal decimal = atleastFrequencyA[1];
            if (decimal == null) {
                atleastFrequencyA[1] = number;
            }
        }

        int q = in.readInt();

        for (int i = 0; i < q; i++) {
            int type = in.readInt();

            BigDecimal frequency = new BigDecimal(in.readInt());
            Long l1 = -1L;
            if (frequency.compareTo(BigDecimal.valueOf(Long.MAX_VALUE)) <= 0)
                l1 = frequency.longValue();
            if (l1 >= 0) {
                if (type == 0) {
                    l1 = atleastAndExactFrequencyMap.ceilingKey(l1);
                    BigDecimal answer = BigDecimal.ZERO;
                    if (l1 != null) {
                        BigDecimal[] bigDecimal = atleastAndExactFrequencyMap.get(l1);
                        if (bigDecimal != null && bigDecimal[0] != null)
                            answer = bigDecimal[0];
                    }
                    out.print(answer, "\n");
                } else {
                    BigDecimal[] bigDecimal = atleastAndExactFrequencyMap.get(l1);
                    BigDecimal answer = BigDecimal.ZERO;
                    if (bigDecimal != null && bigDecimal[1] != null)
                        answer = bigDecimal[1];

                    out.print(answer, "\n");
                }
            } else
                out.print(0, "\n");
        }

        out.flush();
        out.close();
    }
}


class InputReader {

    private InputStream stream;
    private byte[] buf = new byte[1024 * 8];
    private int curChar;
    private int numChars;
    private InputReader.SpaceCharFilter filter;

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