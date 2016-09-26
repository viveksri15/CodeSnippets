package algos.heSept2016.strings.v1;

import java.io.*;
import java.util.*;

public class TestClass {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
//        int t = in.readInt();
//        for (int i = 0; i < t; i++) {
//            in.readInt();
        String s = in.readString();
//        StringBuilder stringBuilder = new StringBuilder();
//        String s = stringBuilder.toString();
        System.out.println(s);
        Trie root = new Trie();
        root.generateTrie(s);
        System.out.println("TRIE DONE " + root.counts);

        long count = 0;

        Map<String, Long> dp = new HashMap<>();

        for (int j = 0; j < s.length(); j++) {
            for (int length = 0; length < s.length() - j; length++) {
                String substring = s.substring(j, j + length + 1);
                Long countInCache = dp.get(substring);
                if (countInCache == null) {
                    countInCache = calculateSize(root, substring);
                    dp.put(substring, countInCache);
                }
                count += countInCache;
            }
        }

        System.out.println((int) (count % ((Math.pow(10, 9) + 7))));
    }

    private static long calculateSize(Trie root, String s) {
        long count = 0;

        /*for (Character c : s.toCharArray()) {
            root = root.getChildMaps().get(c);
            count += root.getData();
        }*/
        return count;
    }
}

class Trie {
    Map<Character, List<Integer>> counts = new HashMap<>();

    public void generateTrie(String s) {
        int length = s.length();
        for (char c : s.toCharArray()) {
            List<Integer> longs = counts.get(c);
            if (longs == null) {
                longs = new ArrayList<>();
                counts.put(c, longs);
            }
            longs.add(length--);
        }
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