package algos.directConnection;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;

//https://www.hackerrank.com/challenges/direct-connections
//Conceptual
/*

    Framework of the problem:

    1. Input and Validation Modules
    2. Output and checked exception handler module
    3. Algorithm Strategy
    4. Set of DS
    5. Small test Fw if time permits
    6. Limitations: Memory size, execution time etc
    7. Assumptions if any
    8. Dry run

*/

/*
    Assumptions

    Limitations
        Operations will be in-memory

    Example;

*/

/*
    DS + Algo

    for DS:
        long for storing current total cost
        Map<Integer, Integer> : map of distance and population

    1st options is an n^2 solution

    --
    5 55 555 55555 555555
    3333 333 333 33 35

    total distance, population
    0
    3333, 50
    333, 500

    3333
    333
    333
*/

//Specification:
interface ICostCalculator {
    void addCost(DistancePolulationPair distancePolulationPair);

    BigInteger getCost();
}

interface IO {
    void putData(DistancePolulationPair distancePolulationPair);

    void publishData(OutputStream stream) throws IOException;

    void reset();
}

//Implementation
class CostCalculatorImps implements ICostCalculator {
    private BigInteger totalCost = new BigInteger("0");
    private BigInteger sumOfPreviousDistances = new BigInteger("0");
    private List<Integer> previousDistances = new ArrayList<>();
    private int totalCount = 0;
    private BigInteger COST_MOD = new BigInteger("1000000007");

    public void addCost(DistancePolulationPair distancePolulationPair) {
//        System.out.println("totalCount = " + totalCount);

        BigInteger sumLessThanOrZero = BigInteger.ZERO;
        int countLessThanOrZero = 0;
        BigInteger sumMoreThan = BigInteger.ZERO;
        int countMoreThan = 0;
        for (Integer previousDistance : previousDistances) {
            if (previousDistance.compareTo(distancePolulationPair.distance) <= 0) {
                sumLessThanOrZero = sumLessThanOrZero.add(BigInteger.valueOf(previousDistance));
                countLessThanOrZero++;
            } else {
                countMoreThan++;
                sumMoreThan = sumMoreThan.add(BigInteger.valueOf(previousDistance));
            }
        }
        BigInteger localCost1 = BigInteger.valueOf(distancePolulationPair.population).multiply(BigInteger.valueOf(countLessThanOrZero).multiply(BigInteger.valueOf(distancePolulationPair.distance)).subtract(sumLessThanOrZero));
        BigInteger localCost2 = BigInteger.valueOf(distancePolulationPair.population).multiply(sumMoreThan.subtract(BigInteger.valueOf(countMoreThan).multiply(BigInteger.valueOf(distancePolulationPair.distance))));

//        System.out.println("localCost = " + localCost1);
//        System.out.println("localCost = " + localCost2);
        totalCost = totalCost.add(localCost2).add(localCost1);
        totalCount++;
        sumOfPreviousDistances = sumOfPreviousDistances.add(BigInteger.valueOf(distancePolulationPair.distance));
        previousDistances.add(distancePolulationPair.distance);
    }

    public BigInteger getCost() {
        return totalCost.mod(COST_MOD);
    }
}

class CostCalculatorFactory {
    public static ICostCalculator getCostCalculator() {
        return new CostCalculatorImps();
    }
}

class IOImps implements IO {
    private ICostCalculator calculator = CostCalculatorFactory.getCostCalculator();

    public void putData(DistancePolulationPair distancePolulationPair) {
        calculator.addCost(distancePolulationPair);
    }

    public void publishData(OutputStream stream) throws IOException {
        stream.write((calculator.getCost() + "\n").getBytes());
    }

    public void reset() {
        calculator = CostCalculatorFactory.getCostCalculator();
    }
}

class DistancePolulationPair {
    final int distance;
    final int population;

    DistancePolulationPair(int distance, int population) throws Exception {
        if (distance < 0 || population < 0)
            throw new Exception("Bad Data");
        this.distance = distance;
        this.population = population;
    }

    @Override
    public String toString() {
        return population + "," + distance;
    }
}

public class Solution {

    public static void main(String[] args) {

        final Scanner scanner = new Scanner(System.in);
        int scenarioCount = Integer.parseInt(scanner.nextLine());
        long t0 = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(scenarioCount);
        List<Future<IO>> futures = new ArrayList<>();
        for (int i = 0; i < scenarioCount; i++) {
            final int cities = Integer.parseInt(scanner.nextLine());
            final String[] coordinates = scanner.nextLine().split("\\W");
            final String[] populations = scanner.nextLine().split("\\W");
            futures.add(executorService.submit(new Callable<IO>() {
                @Override
                public IO call() throws Exception {
                    long t01 = System.currentTimeMillis();
                    IO io = new IOImps();
                    List<DistancePolulationPair> distancePolulationPairs = new ArrayList<DistancePolulationPair>();
                    for (int j = 0; j < cities; j++) {
                        DistancePolulationPair distancePolulationPair = new DistancePolulationPair(Integer.parseInt(coordinates[j]), Integer.parseInt(populations[j]));
                        distancePolulationPairs.add(distancePolulationPair);
                    }
                    Collections.sort(distancePolulationPairs, new Comparator<DistancePolulationPair>() {
                        @Override
                        public int compare(DistancePolulationPair o1, DistancePolulationPair o2) {
                            return o1.population - o2.population;
                        }
                    });
                    for (DistancePolulationPair distancePolulationPair : distancePolulationPairs)
                        io.putData(distancePolulationPair);
//                    System.out.println("t1 = " + (System.currentTimeMillis() - t01));
                    return io;
                }
            }));

        }
        for (Future<IO> ioFuture : futures) {
            try {
                ioFuture.get().publishData(System.out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
//        System.out.println("t0 = " + (System.currentTimeMillis() - t0));
    }
}