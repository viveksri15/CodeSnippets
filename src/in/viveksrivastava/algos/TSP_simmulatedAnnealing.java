package in.viveksrivastava.algos;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.JFrame;

import org.apache.commons.io.LineIterator;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

public class TSP_simmulatedAnnealing {

    private static final boolean debug = false;
    ArrayList<double[]> coorinates = new ArrayList<double[]>();
    double[] standardDeviation;
    double[][] distances;
    Integer[][] orderedVertices; //For each vertex, which is the nearest vertex comes first
    int numberOfCoordinates;
    int[] numberOfConnectingNodes; //can be 0, 1 or 2
    Set<Integer> zeroConnectedNodes = new HashSet<Integer>();
    Set<Integer> oneConnectedNodes = new HashSet<Integer>();
    Set<Integer> twoConnectedNodes = new HashSet<Integer>();
    double smallestDistance;
    int[] bestSolution;
    double time = 0.1d;
    long startTime = System.currentTimeMillis();

    public TSP_simmulatedAnnealing(int numberOfCoordinates) {
        this.numberOfCoordinates = numberOfCoordinates;
        standardDeviation = new double[numberOfCoordinates];
        numberOfConnectingNodes = new int[numberOfCoordinates];
        bestSolution = new int[numberOfCoordinates];
    }

    private double calculateScore(int[] solution) {
        double score = 0;

        int next = solution[0];
        for (int i = 0; i < solution.length - 1; i++) {
            double d = distances[next][solution[i + 1]];
            /*if (debug)
                System.err.println("DISTANCE BETWEEN " + next + " "
						+ solution[i + 1] + "=" + d);*/
            score += d;
            next = solution[i + 1];
        }
        score += distances[next][solution[0]];
        return score;
    }

    public static void main(String[] args) throws IllegalArgumentException,
            FileNotFoundException, CloneNotSupportedException {

        TSP_simmulatedAnnealing tsp = null;

        String fileName = "D:\\study\\descrete optimization\\assignments-tsp\\tsp\\data\\tsp_51_1";
        //		String fileName = args[0];

        LineIterator iterator = new LineIterator(new FileReader(fileName));
        int i = 0;
        while (iterator.hasNext()) {
            String line = iterator.next();
            if (i == 0) {
                int numberOfCoordinates = Integer.parseInt(line);
                tsp = new TSP_simmulatedAnnealing(numberOfCoordinates);
            } else {
                String[] c = line.split("\\s");
                double x = Double.parseDouble(c[0]);
                double y = Double.parseDouble(c[1]);
                tsp.addCoordinate(x, y);
            }
            i++;
        }
        iterator.close();
        tsp.prepare();
        tsp.solveByGreedySearch();
        //		tsp.visualize("GREEDY");

        System.err.println("SCORE-BY-GREEDY-SEARCH="
                + tsp.calculateScore(tsp.bestSolution));

        while (System.currentTimeMillis() - tsp.startTime < tsp.time * 60 * 60 * 1000) {
            for (i = 0; i < tsp.numberOfCoordinates - 1; i++) {
                int j = i + 1;
                if (debug)
                    System.err.println("TRYING FOR " + i + " " + j);
                tsp.kOpt(i, j);
            }
        }
        tsp.kOpt(tsp.numberOfCoordinates - 1, 0);

        int x = tsp.bestSolution[tsp.numberOfCoordinates - 1];
        for (i = tsp.numberOfCoordinates - 1; i > 0; i--)
            tsp.bestSolution[i] = tsp.bestSolution[i - 1];
        tsp.bestSolution[0] = x;

        double score = tsp.calculateScore(tsp.bestSolution);
        System.out.println(score + " 0");
        tsp.printFinal();
    }

    private void kOpt(int i, int j) {

        Set<String> exploredEdges = getExploredEdges(bestSolution);
        int[] solutionToVerify = new int[numberOfCoordinates];
        double scoreTillNow = calculateScore(bestSolution);
        System.arraycopy(bestSolution, 0, solutionToVerify, 0,
                numberOfCoordinates);
        while (true) {

			/*System.err.println("OLD_SCORE=" + scoreTillNow);
            print_r("GRAPH", bestSolution);*/

            int t1 = solutionToVerify[i];
            int t2 = solutionToVerify[j];

            if (debug)
                System.err.println("CHECKING " + t1 + " " + t2 + " = "
                        + distances[t1][t2]);

            double edgeDistance = distances[t1][t2];
            Integer[] t3_p = getNextBestEdgeForJ_SimAnnealing(t2, edgeDistance,
                    exploredEdges, solutionToVerify);

            int t3 = t3_p[0];
            int k = t3_p[1];

            if (t3 == -1) {
                if (debug)
                    System.err.println("GOT NOTHING FOR " + solutionToVerify[i]
                            + " " + solutionToVerify[j]);
                break;
            }

            int[] otherEdges = otherTwoEdges(solutionToVerify, k);
            double d1 = distances[t1][otherEdges[0]];
            double d2 = distances[t2][otherEdges[1]];

            if (debug)
                System.err.println("K EDGES=" + t3 + " " + k + " "
                        + otherEdges[0] + " " + otherEdges[1]);

            int m = otherEdges[0];
            if (d2 > d1
                    || (exploredEdges.contains(t1 + "-" + otherEdges[0]) && !exploredEdges
                    .contains(t1 + "-" + otherEdges[1]))) {
                m = otherEdges[1];
            }

            int t4 = solutionToVerify[m];

            if (exploredEdges.contains(t1 + "-" + t4)) {
                if (debug)
                    System.err.println("T1_T4 already connected " + t1 + " "
                            + t4);
                break;
            }

            if (debug)
                System.err.println("STARTINGWITH " + t1 + " " + t2 + " " + t3
                        + " " + t4);

            if (debug)
                print_r("GRAPH_old", solutionToVerify);

            //t4 is new t2

            int l = m + j;
            if (l % 2 == 0)
                l = l / 2;
            else
                l = l / 2 + 1;
            for (int x = j; x < l; x++) {
                int y = solutionToVerify[x];
                solutionToVerify[x] = solutionToVerify[m + j - x];
                solutionToVerify[m + j - x] = y;
            }

            //			solutionToVerify[j] = solutionToVerify[m];

            exploredEdges.add(t1 + "-" + t4);
            exploredEdges.add(t4 + "-" + t1);
            exploredEdges.add(t2 + "-" + t3);
            exploredEdges.add(t3 + "-" + t2);

            //t2 is now t4
            //			solutionToVerify[m] = t2;

            if (debug)
                print_r("GRAPH_new", solutionToVerify);

            if (debug)
                System.err.println("GOTFOR " + t1 + " " + t4 + " " + t2 + " "
                        + t3);

            double newScore = calculateScore(solutionToVerify);
            if (scoreTillNow > newScore) {
                System.arraycopy(solutionToVerify, 0, bestSolution, 0,
                        numberOfCoordinates);
                scoreTillNow = newScore;
                System.err.println("IMPROVEMENT :)" + newScore);
				/*GraphVisualizer graphVisualizer = new GraphVisualizer();
				graphVisualizer.setTitle(newScore + "");
				graphVisualizer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				graphVisualizer.setBounds(100, 100, 2000, 2000);
				graphVisualizer.visualize(solutionToVerify, coorinates,
						distances);
				graphVisualizer.setVisible(true);*/
            }

			/*GraphVisualizer graphVisualizer = new GraphVisualizer();
			graphVisualizer.setTitle(newScore + "");
			graphVisualizer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			graphVisualizer.setBounds(100, 100, 2000, 2000);
			graphVisualizer.visualize(solutionToVerify, coorinates, distances);
			graphVisualizer.setVisible(true);
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/

            if (debug)
                System.err.println("GOT_SCORE=" + newScore + " "
                        + exploredEdges.size());
            //			print_r("GRAPH", solutionToVerify);
            //			System.err.println("---------------");

            //i = j;
            j = m;
        }
    }

    private int[] otherTwoEdges(int[] solutionToVerify, int t3_position) {

        int[] ret = new int[2];

        if (t3_position == 0) {
            ret[0] = 1;
            ret[1] = numberOfCoordinates - 1;
        } else if (t3_position == numberOfCoordinates - 1) {
            ret[0] = 0;
            ret[1] = numberOfCoordinates - 2;
        } else {
            ret[0] = t3_position - 1;
            ret[1] = t3_position + 1;
        }

        return ret;
    }

    private Integer[] getNextBestEdgeForJ_SimAnnealing(int t2,
                                                       double edgeDistance, Set<String> exploredEdges,
                                                       int[] solutionToVerify) {

        ArrayList<Integer[]> edgesToConsider = new ArrayList<Integer[]>();
        ArrayList<Double> probability = new ArrayList<Double>();

        for (int k = 0; k < orderedVertices[t2].length; k++) {
            int t3 = orderedVertices[t2][k];
            if (t2 == t3 || exploredEdges.contains(t2 + "-" + t3))
                continue;
			/*if (debug)
				System.err.println("DISTANCE BETWEEN " + t2 + " " + t3 + " = "
						+ distances[t2][t3] + " compto=" + edgeDistance);*/
            double d1 = distances[t2][t3];
            if (d1 > edgeDistance) {
                break;
            } else {
                int m = 0;
                for (int i = 0; i < solutionToVerify.length; i++) {
                    if (t3 == solutionToVerify[i]) {
                        m = i;
                        break;
                    }
                }
                edgesToConsider.add(new Integer[]{t3, m});
                double p = Math
                        .pow(2.71, -((d1 - edgeDistance) / edgeDistance));
                if (probability.size() == 0)
                    probability.add(p * 10);
                else
                    probability.add(probability.get(probability.size() - 1) + p
                            * 10);
            }
        }

        if (probability.size() == 0)
            return new Integer[]{-1, -1};
        if (debug) {
            System.err.println("PROBABILITIES=" + probability);
        }

        int rand = new Random().nextInt(probability.get(probability.size() - 1)
                .intValue());

        if (debug) {
            System.err
                    .println("PROBABILITIES=" + probability + " RAND=" + rand);
        }

        int prev = probability.size() - 1;
        for (int i = probability.size() - 2; i >= 0; i--) {
            if (rand > probability.get(i) && rand < probability.get(prev))
                return edgesToConsider.get(prev);
            prev = i;
        }

        return new Integer[]{-1, -1};
    }

    private int[] getNextBestEdgeForJ(int t2, double edgeDistance,
                                      Set<String> exploredEdges, int[] solutionToVerify) {

        for (int k = 0; k < orderedVertices[t2].length; k++) {
            int t3 = orderedVertices[t2][k];
            if (t2 == t3 || exploredEdges.contains(t2 + "-" + t3))
                continue;
            if (debug)
                System.err.println("DISTANCE BETWEEN " + t2 + " " + t3 + " = "
                        + distances[t2][t3] + " compto=" + edgeDistance);
            if (distances[t2][t3] >= edgeDistance) {
                break;
            } else {
                int m = 0;
                for (int i = 0; i < solutionToVerify.length; i++) {
                    if (t3 == solutionToVerify[i]) {
                        m = i;
                        break;
                    }
                }
                return new int[]{t3, m};
            }
        }

        return new int[]{-1, -1};
    }

    private Set<String> getExploredEdges(int[] bestSolution2) {
        Set<String> exploredEdges = new HashSet<String>();
        for (int i = 0; i < bestSolution2.length - 1; i++) {
            exploredEdges.add(bestSolution2[i] + "-" + bestSolution2[i + 1]);
            exploredEdges.add(bestSolution2[i + 1] + "-" + bestSolution2[i]);
        }
        exploredEdges.add(bestSolution2[numberOfCoordinates - 1] + "-"
                + bestSolution2[0]);
        exploredEdges.add(bestSolution2[0] + "-"
                + bestSolution2[numberOfCoordinates - 1]);
        return exploredEdges;
    }

    private void printFinal() {
        for (int i = 0; i < numberOfCoordinates; i++) {
            System.out.print(bestSolution[i] + " ");
        }
        System.out.println();
    }

    private double getDistance(double x1, double y1, double x2, double y2) {
        double x = Math.pow((x2 - x1), 2);
        double y = Math.pow((y2 - y1), 2);
        double pow = Math.pow(x + y, 0.5);
        return pow;
    }

    private void prepare() {
		/*
		 * Assigning distances
		 */
        distances = new double[numberOfCoordinates][];
        for (int i = 0; i < numberOfCoordinates; i++) {
            distances[i] = new double[numberOfCoordinates];
            for (int j = 0; j < numberOfCoordinates; j++) {
                if (i == j) {
                    distances[i][j] = 0;
                } else if (distances[j] != null && distances[j][i] != 0) {
                    distances[i][j] = distances[j][i];
                } else {
                    distances[i][j] = getDistance(coorinates.get(i)[0],
                            coorinates.get(i)[1], coorinates.get(j)[0],
                            coorinates.get(j)[1]);
                }
            }
            numberOfConnectingNodes[i] = 0;
            zeroConnectedNodes.add(i);
        }

		/*
		 * Now, sort
		 */
        orderedVertices = new Integer[numberOfCoordinates][];
        for (int i = 0; i < numberOfCoordinates; i++) {
            orderedVertices[i] = new Integer[numberOfCoordinates];
            for (int j = 0; j < numberOfCoordinates; j++) {
                orderedVertices[i][j] = j;
            }
            final int k = i;
            Arrays.sort(orderedVertices[i], new Comparator<Integer>() {

                @Override
                public int compare(Integer o1, Integer o2) {
                    if (distances[k][o1] < distances[k][o2])
                        return -1;
                    else if (distances[k][o1] > distances[k][o2])
                        return 1;
                    return 0;
                }
            });
        }

		/*
		 * Finally the standard deviation
		 */
        for (int i = 0; i < numberOfCoordinates; i++) {
            StandardDeviation deviation = new StandardDeviation();
            double dev = deviation.evaluate(distances[i]);
            standardDeviation[i] = dev;
        }
    }

    private void print_d(String string, double[] distances,
                         Integer[] orderedVertices2) {
        StringBuffer buffer = new StringBuffer(string);
        for (int i : orderedVertices2) {
            buffer.append(" ").append(i).append("=").append(distances[i]);
        }
        System.err.println(buffer);
    }

    private void print_r(String string, int[] integers) {
        StringBuffer buffer = new StringBuffer(string);
        for (int i : integers) {
            buffer.append(" ").append(i);
        }
        System.err.println(buffer);
    }

    private void addCoordinate(double x, double y) {
        coorinates.add(new double[]{x, y});
    }

    private void solveByGreedySearch() {
        int nextNum = 0;
        for (int i = 0; i < numberOfCoordinates - 1; i++) {
            int j = getNextBestConnectedNode(nextNum);
            bestSolution[i] = j;
            if (zeroConnectedNodes.contains(j)) {
                zeroConnectedNodes.remove(j);
            }
            if (oneConnectedNodes.contains(j)) {
                oneConnectedNodes.remove(j);
                twoConnectedNodes.add(j);
            } else
                oneConnectedNodes.add(j);
            if (zeroConnectedNodes.contains(nextNum)) {
                zeroConnectedNodes.remove(nextNum);
            }
            if (oneConnectedNodes.contains(nextNum)) {
                oneConnectedNodes.remove(nextNum);
                twoConnectedNodes.add(nextNum);
            } else
                oneConnectedNodes.add(nextNum);
			/*System.err.println("CONNECTING " + nextNum + " TO " + j + " "
					+ zeroConnectedNodes + " " + oneConnectedNodes);*/
            nextNum = j;
        }
        bestSolution[numberOfCoordinates - 1] = 0;
    }

    private int getNextBestConnectedNode(int i) {
        Integer[] orderedV = orderedVertices[i];
        for (int j = 0; j < numberOfCoordinates; j++) {
            int toCheck = orderedV[j];
            if (toCheck == i)
                continue;
            if (zeroConnectedNodes.contains(toCheck)
                    && !twoConnectedNodes.contains(toCheck))
                return toCheck;
        }
        for (int j = 0; i < numberOfCoordinates; j++) {
            int toCheck = orderedV[j];
            if (toCheck == i)
                continue;
            if (oneConnectedNodes.contains(toCheck)
                    && !twoConnectedNodes.contains(toCheck))
                return toCheck;
        }
        System.err.println("HOW COULD IT BE? " + i + " " + zeroConnectedNodes
                + " " + oneConnectedNodes);
        System.exit(-1);
        return -1;
    }
}