package algos.directConnection.correctSolution;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;

final class City implements Comparable<City> {
    private final int population;
    private final int coordinate;

    public City(int population, int coordinate) {
        this.population = population;
        this.coordinate = coordinate;
    }

    public int getPopulation() {
        return population;
    }

    public int getCoordinate() {
        return coordinate;
    }

    public int compareTo(City other) {
        // - is safe due to fact that population is limited to 10000
        return this.population - other.population;
    }
}

final class DirectConnectionsDataSet {
    private int length;
    private int size;
    private int[] counts;
    private long[] xsums;
    private int[] realCoordinates;

    public DirectConnectionsDataSet(int size, int[] realCoordinates) {
        this.size = size;
        length = 1;
        while (length <= this.size) {
            length <<= 1;
        }
        counts = new int[length + this.size];
        xsums = new long[length + this.size];
        this.realCoordinates = realCoordinates;
    }

    private int getCount(int l, int r) {
        l += length;
        r += length;
        int count = 0;
        while (l <= r) {
            if ((l & 1) == 1) {
                count += counts[l];
                l++;
            }
            l >>>= 1;
            if ((r & 1) == 0) {
                count += counts[r];
                r--;
            }
            r >>>= 1;
        }
        return count;
    }

    private long getXsum(int l, int r) {
        l += length;
        r += length;
        long xsum = 0;
        while (l <= r) {
            if ((l & 1) == 1) {
                xsum += xsums[l];
                l++;
            }
            l >>>= 1;
            if ((r & 1) == 0) {
                xsum += xsums[r];
                r--;
            }
            r >>>= 1;
        }
        return xsum;
    }

    private void update(int c) {
        long rc = realCoordinates[c];
        c += length;
        while (c > 0) {
            counts[c]++;
            xsums[c] += rc;
            c >>= 1;
        }
    }

    // - connects given city to network and returns required cable length
    public BigInteger connectCity(City city) {
        BigInteger cable = BigInteger.valueOf(0);
        int coordinate = city.getCoordinate();
        long realCoordinate = realCoordinates[coordinate];
        long xsum;
        long count;
        // - left
        xsum = getXsum(0, coordinate - 1);
        count = getCount(0, coordinate - 1);
        cable = cable.add(BigInteger.valueOf(count * realCoordinate - xsum));
        // - right
        xsum = getXsum(coordinate + 1, size - 1);
        count = getCount(coordinate + 1, size - 1);
        cable = cable.add(BigInteger.valueOf(xsum - count * realCoordinate));
        cable = cable.multiply(BigInteger.valueOf(city.getPopulation()));
        // - update data set using this city information
        update(city.getCoordinate());
        return cable;
    }
}

public class Solution {

    static private final long FINITE_FIELD_MODULO = 1000000007;

    static private int[] indexCoordinates(int[] coordinates) {
        int n = coordinates.length;
        long[] indexer = new long[n];
        for (int i = 0; i < n; i++) {
            indexer[i] = ((long) coordinates[i] << 32) + i;
        }
        Arrays.sort(indexer);
        int prev = -1;
        int index = -1;
        int[] realCoordinates = new int[n];
        for (int i = 0; i < n; i++) {
            int value = (int) (indexer[i] >>> 32);
            int indice = (int) (indexer[i] & 0xFFFFFFFF);
            if (value != prev)
                index++;
            coordinates[indice] = index;
            realCoordinates[index] = value;
            prev = value;
        }
        return realCoordinates;
    }

    static public void main(String[] args) {

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 64 << 10);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out), 64 << 10);
            int testsNumber = Integer.parseInt(br.readLine().trim());
            for (int test = 0; test < testsNumber; test++) {
                int n = Integer.parseInt(br.readLine().trim());
                int[] coordinates = new int[n];
                StringTokenizer tokenizer;
                tokenizer = new StringTokenizer(br.readLine());
                // - as far as I understood, coordinates are not guaranteed to fit into 32-bit signed integer
                BigInteger[] rc = new BigInteger[n];
                rc[0] = new BigInteger(tokenizer.nextToken());
                BigInteger minX = rc[0];
                for (int i = 1; i < n; i++) {
                    rc[i] = new BigInteger(tokenizer.nextToken());
                    minX = minX.min(rc[i]);
                }
                // -- but the difference IS
                for (int i = 0; i < n; i++) {
                    coordinates[i] = rc[i].subtract(minX).intValue();
                }
                // - index coordinates and initialize problem specific data set
                DirectConnectionsDataSet dataSet = new DirectConnectionsDataSet(n, indexCoordinates(coordinates));
                // - print populations
                int[] populations = new int[n];
                tokenizer = new StringTokenizer(br.readLine());
                for (int i = 0; i < n; i++) {
                    populations[i] = Integer.parseInt(tokenizer.nextToken());
                }
                City[] cities = new City[n];
                for (int i = 0; i < n; i++) {
                    cities[i] = new City(populations[i], coordinates[i]);
                }
                // - free for GC
                rc = null;
                populations = null;
                coordinates = null;
                // - sort cities by their natural order (population)
                Arrays.sort(cities);
                // - evaluate amount of cable
                BigInteger cable = BigInteger.valueOf(0);
                for (int i = 0; i < n; i++) {
                    cable = cable.add(dataSet.connectCity(cities[i]));
                }
                cable = cable.mod(BigInteger.valueOf(FINITE_FIELD_MODULO));
                System.out.println(cable.toString());
            }
            bw.close();
        } catch (Exception e) {
            System.err.println("Error:" + e.getMessage());
        }
    }
}