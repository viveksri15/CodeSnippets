package algos.heSept2016.fredo.racing;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import static java.math.BigDecimal.ROUND_HALF_UP;


public class TestClass {

    public static void main(String[] args) {
        InputReader inputReader = new InputReader(System.in);
        int n = inputReader.readInt();
        int m = inputReader.readInt();
        int a = inputReader.readInt();
        int s = inputReader.readInt() - 1;
        int f = inputReader.readInt() - 1;

        List<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < n; i++)
            pairs.add(new Pair(i, inputReader.readInt(), inputReader.readInt()));

        Map<Integer, PairRoad> pairRoadMap = new HashMap<>();
        for (int i = 0; i < m; i++) {
            int pairI1 = inputReader.readInt() - 1;
            int pairI2 = inputReader.readInt() - 1;
            Pair pair1 = pairs.get(pairI1);
            Pair pair2 = pairs.get(pairI2);
            PairRoad pairRoad1 = pairRoadMap.get(pairI1);
            if (pairRoad1 == null) {
                pairRoad1 = new PairRoad(pair1);
                pairRoadMap.put(pairI1, pairRoad1);
            }
            PairRoad pairRoad2 = pairRoadMap.get(pairI2);
            if (pairRoad2 == null) {
                pairRoad2 = new PairRoad(pair2);
                pairRoadMap.put(pairI2, pairRoad2);
            }
            pairRoad1.roads.add(pair2);
            pairRoad2.roads.add(pair1);
        }

        String distance = findDistance(s, f, a, pairs, pairRoadMap);
        System.out.println(distance);
    }

    private static String findDistance(int s, int f, int a, List<Pair> pairs, Map<Integer, PairRoad> roads) {
        TreeMap<Double, List<DistanceFromSource>> queue = new TreeMap<>();

        if (s >= pairs.size() || f >= pairs.size() || s < 0 || f < 0)
            return "-1";

        Pair source = pairs.get(s);
        Pair destination = pairs.get(f);

        if (source == null || destination == null)
            return "-1";

        if (s == f)
            return "0.000";

        List<DistanceFromSource> value = new ArrayList<>();
        value.add(new DistanceFromSource(0, source, null));
        queue.put(0D, value);

        double minTime = Double.MAX_VALUE;

        while (!queue.isEmpty()) {
//            System.out.println("queue = " + queue + "," + minTime);
            Map.Entry<Double, List<DistanceFromSource>> nodeList = queue.firstEntry();
            double distance = nodeList.getKey();
            queue.remove(distance);

            for (DistanceFromSource node : nodeList.getValue()) {
                Pair pair = node.pair;
                if (pair.getN() == destination.getN()) {
                    minTime = Math.min(minTime, distance);
                    return BigDecimal.valueOf(minTime).setScale(3, ROUND_HALF_UP).toString();
//                    continue;
                }

                PairRoad pairRoad = roads.get(pair.getN());
                Pair parent = node.parent;

                for (Pair p : pairRoad.roads) {

                    if (parent != null && p.getN() == parent.getN())
                        continue;

                    double angle = 0;

                    if (parent != null)
                        angle = getAngle(parent.getX(), parent.getY(), pair.getX(), pair.getY(), p.getX(), p.getY());

                    double newDistance = distance + getDistance(p, pair);

                    if (angle <= a) {
                        DistanceFromSource distanceFromSource = new DistanceFromSource(newDistance, p, pair);
                        List<DistanceFromSource> distanceFromSources = queue.get(newDistance);
                        if (distanceFromSources == null) {
                            distanceFromSources = new ArrayList<>();
                            queue.put(newDistance, distanceFromSources);
                        }
                        distanceFromSources.add(distanceFromSource);
                    }
                }
            }
        }

        if (minTime == Double.MAX_VALUE)
            return "-1";

        return BigDecimal.valueOf(minTime).setScale(3, ROUND_HALF_UP).toString();
    }

    private static double getDistance(Pair pair, Pair p) {

        int x = p.getX() - pair.getX();
        int y = p.getY() - pair.getY();
        double sum = Math.pow(x, 2) + Math.pow(y, 2);
        return Math.sqrt(sum);
    }


    private static double getAngle(double x1, double y1, double x2, double y2, double x3, double y3) {
        double v1X = x2 - x1;
        double v1Y = y2 - y1;
        double v2X = x3 - x2;
        double v2Y = y3 - y2;
        return Math.toDegrees(
                Math.abs(Math.atan2(
                        (v1X * v2Y) - (v1Y * v2X),
                        (v1X * v2X) + (v1Y * v2Y))
                ));
    }
}

class DistanceFromSource implements Comparable<DistanceFromSource> {
    double distance = 0;
    Pair pair = null;
    Pair parent = null;

    public DistanceFromSource(double distance, Pair pair, Pair parent) {
        this.distance = distance;
        this.pair = pair;
        this.parent = parent;
    }

    @Override
    public int compareTo(DistanceFromSource o) {
        return (distance < o.distance) ? -1 : 1;
    }

    @Override
    public String toString() {
        if (parent != null)
            return (pair.getN() + 1) + "," + (parent.getN() + 1) + "," + distance;

        return (pair.getN() + 1) + ",-1," + distance;
    }
}

class PairRoad {
    private final Pair pair;
    List<Pair> roads = new ArrayList<>();

    PairRoad(Pair pair) {
        this.pair = pair;
    }

    @Override
    public String toString() {
        return pair + "," + roads;
    }
}

class Pair {
    private final int n, x, y;

    Pair(int n, int x, int y) {
        this.n = n;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getN() {
        return n;
    }

    @Override
    public String toString() {
        return (n + 1) + "";
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