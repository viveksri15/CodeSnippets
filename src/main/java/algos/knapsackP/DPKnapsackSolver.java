package algos.knapsackP;

/**
 * Created by viveksrivastava on 06/04/16.
 */
class DPKnapsackSolver {

    private int[][] weightValueMatrix;

    public int solve(int[] inputArray, int maxSum) {
        this.weightValueMatrix = new int[inputArray.length + 1][maxSum + 1];
        for (int j = 0; j < maxSum; j++) {
            weightValueMatrix[0][j] = 0;
        }

        for (int i = 0; i <= inputArray.length; i++) {
            weightValueMatrix[i][0] = 0;
        }

        for (int i = 1; i <= inputArray.length; i++) {
            for (int j = 1; j <= maxSum; j++) {
                if (inputArray[i - 1] > j)
                    weightValueMatrix[i][j] = weightValueMatrix[i - 1][j];
                else {
                    int itemNotSelectedWeight = weightValueMatrix[i - 1][j];
                    int itemSelectedOnce = inputArray[i - 1] + weightValueMatrix[i - 1][j - inputArray[i - 1]];
                    int itemSelectedAgain = inputArray[i - 1] + weightValueMatrix[i][j - inputArray[i - 1]];
                    int maxWeight = Math.max(Math.max(itemNotSelectedWeight, itemSelectedOnce), itemSelectedAgain);
                    weightValueMatrix[i][j] = maxWeight;
                }
            }
        }
        for (int i = 0; i <= maxSum; i++) {
            System.out.print(i + "  ");
        }
        System.out.println("\n------");
        for (int i = 0; i <= inputArray.length; i++) {
            for (int j = 0; j <= maxSum; j++) {
                System.out.print(weightValueMatrix[i][j] + "  ");
            }
            System.out.println();
        }

        return weightValueMatrix[inputArray.length][maxSum];
    }

    public static void main(String[] args) {
        DPKnapsackSolver dpKnapsackSolver = new DPKnapsackSolver();
        int result = dpKnapsackSolver.solve(new int[]{6, 9, 3}, 1000);
        System.out.println("result = " + result);
    }
}
