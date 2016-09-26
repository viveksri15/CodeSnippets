package in.viveksrivastava.algos;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Vivek on 27-05-2014.
 */
public class NQueenProblem_Solver {

    private int queens;
    private int[] column;
    private int[] queen_violation;
    private BigInteger totalViolations = BigInteger.valueOf(0);
    private Set<Integer> removed = new HashSet<Integer>();
    private LinkedHashMap<Integer, LinkedHashSet<Integer>> tabuPair = new LinkedHashMap<Integer, LinkedHashSet<Integer>>();
    private boolean debug = false;

    public static void main(String[] args) {
        NQueenProblem_Solver nQueenProblem_solver = new NQueenProblem_Solver(1000);
        nQueenProblem_solver.solve();
    }

    public NQueenProblem_Solver(int queens) {
        this.queens = queens;
        column = new int[queens];
        queen_violation = new int[queens];
        init();
    }

    private void init() {
        for (int i = 0; i < queens; i++) {
            column[i] = i;
            queen_violation[i] = (queens - 1);
            totalViolations = totalViolations.add(BigInteger
                    .valueOf(queens - 1));
        }
    }

    private int getHighestViolatedQueen() {
        int highV = -1, highVQ = -1;
        for (int i = 0; i < queens; i++) {
            if (removed.contains(i))
                continue;
            if (queen_violation[i] > highVQ) {
                highVQ = queen_violation[i];
                highV = i;
            }
        }

        return highV;
    }

    private int getHighestViolatedQueen(int viol1, Set<Integer> tabu) {
        int highV = -1, highVQ = -1;
        for (int i = 0; i < queens; i++) {
            if (i == viol1)
                continue;
            if (removed.contains(i))
                continue;
            if (tabu.contains(i))
                continue;
            if (queen_violation[i] > highVQ) {
                highVQ = queen_violation[i];
                highV = i;
            }
        }

        return highV;
    }

	/*
     * ORDER:
	 * 	Remove
	 * 	Update column integer
	 * 	Add
	 */

    private void addQueenToColumn(int colNum) {

        int queenRow = column[colNum];
        if (!removed.contains(colNum))
            return;

        BigInteger totalViolations = BigInteger.valueOf(0);
        for (int i = 0; i < queens; i++) {
            if (colNum == i)
                continue;
            if (removed.contains(i))
                continue;
            int qrow = column[i];

            if (qrow + i == queenRow + colNum) {
                //they are on a downColumn
                queen_violation[i]++;
                queen_violation[colNum]++;
                if (debug)
                    System.err.println(queen_violation[i] + " QUEEN (" + i
                            + "," + qrow + ") IN VIOATION WITH (" + queenRow
                            + "," + colNum + ")");
            } else if (qrow - i == queenRow - colNum) {
                //they are on downColumn
                queen_violation[i]++;
                queen_violation[colNum]++;
                if (debug)
                    System.err.println(queen_violation[i] + " QUEEN (" + i
                            + "," + qrow + ") IN VIOATION WITH (" + queenRow
                            + "," + colNum + ")");
            }
            totalViolations = totalViolations.add(BigInteger
                    .valueOf(queen_violation[i]));
        }
        totalViolations = totalViolations.add(BigInteger
                .valueOf(queen_violation[colNum]));
        this.totalViolations = totalViolations;
        removed.remove(colNum);
        if (debug)
            System.err.println("TOTAL_VIOLATION_AFTER_ADD=" + totalViolations
                    + " " + queen_violation[colNum]);
    }

    private void removeQueenFromColumn(int colNum) {

        int queenRow = column[colNum];
        if (removed.contains(colNum))
            return;

        BigInteger totalViolations = BigInteger.valueOf(0);
        for (int i = 0; i < queens; i++) {
            if (colNum == i)
                continue;
            if (removed.contains(i))
                continue;
            int qrow = column[i];

            if (qrow + i == queenRow + colNum) {
                //they are on a downColumn
                queen_violation[i]--;
                if (debug)
                    System.err.println(queen_violation[i] + " QUEEN (" + i
                            + "," + qrow + ") IN VIOATION WITH (" + queenRow
                            + "," + colNum + ")");
            } else if (qrow - i == queenRow - colNum) {
                //they are on downColumn
                queen_violation[i]--;
                if (debug)
                    System.err.println(queen_violation[i] + " QUEEN (" + i
                            + "," + qrow + ") IN VIOATION WITH (" + queenRow
                            + "," + colNum + ")");
            }
            totalViolations = totalViolations.add(BigInteger.valueOf(queen_violation[i]));
        }
        queen_violation[colNum] = 0;
        removed.add(colNum);
        this.totalViolations = totalViolations;
        if (debug)
            System.err.println("TOTAL_VIOLATION_AFTER_REMOVE="
                    + totalViolations + " " + queen_violation[colNum]);
    }

	/*public static void main(String[] args) {
        NQueenSolver nQueenSolver = new NQueenSolver(8);
		System.err.println("TOTAL_VIOLATIONS=" + nQueenSolver.totalViolations);
		nQueenSolver.swapColumns(0, 1);
		//nQueenSolver.swapColumns(0, 1);
		System.err.println("TOTAL_VIOLATIONS=" + nQueenSolver.totalViolations);
	}*/

    public void swapColumns(int column1, int column2) {
        int row1 = column[column1];
        int row2 = column[column2];
        removeQueenFromColumn(column1);
        removeQueenFromColumn(column2);
        column[column1] = row2;
        column[column2] = row1;
        addQueenToColumn(column1);
        addQueenToColumn(column2);
    }

    public BigInteger getTotalViolations() {
        return totalViolations;
    }

    public void solve() {
        while (true) {
            int viol1 = getHighestViolatedQueen();
            LinkedHashSet<Integer> tabuList = tabuPair.get(viol1);
            if (tabuList == null) {
                tabuList = new LinkedHashSet<Integer>();
                tabuPair.put(viol1, tabuList);
            }

            if (viol1 == -1) {
                viol1 = tabuPair.keySet().iterator().next();
            }

            int viol2 = getHighestViolatedQueen(viol1, tabuList);
            if (viol2 == -1) {
                viol2 = tabuList.iterator().next();
                tabuList.remove(viol2);
            }
            tabuList.add(viol2);

            LinkedHashSet<Integer> tabuList_2 = tabuPair.get(viol2);
            if (tabuList_2 == null) {
                tabuList_2 = new LinkedHashSet<Integer>();
                tabuPair.put(viol2, tabuList_2);
            }
            tabuList_2.add(viol1);

            System.err.println("TO SWAP " + viol1 + " " + viol2);

            BigInteger violationBeforeSwap = totalViolations;
            swapColumns(viol1, viol2);
            System.err.println("VIOLATION_AFTER_SWAP=" + violationBeforeSwap
                    + " " + totalViolations);

            if (totalViolations.compareTo(violationBeforeSwap) > 0) {
                swapColumns(viol1, viol2);
                System.err.println("VIOLATION_AFTER_SWAP 1="
                        + violationBeforeSwap + " " + totalViolations);
            }

            if (totalViolations.compareTo(BigInteger.valueOf(0)) == 0) {
                System.err.println("DONE " + totalViolations);
                break;
            }
        }
    }

    public void print() {
        for (int r : column)
            System.out.print(r + " ");
    }
}
