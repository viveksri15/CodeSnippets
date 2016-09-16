package algos.splitArray;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) throws Exception {
        InputManager in = Factories.getNewInputManager();
        int t = in.getNextInteger();
        Validator validator = Factories.getValidator();

        validator.assertTestCases(t);

        for (int i = 0; i < t; i++) {
            int n = in.getNextInteger();
            validator.assertInputSize(n);
            int[] arr = new int[n];
            long sum = 0l;
            for (int j = 0; j < n; j++) {
                arr[j] = in.getNextInteger();
                sum += arr[j];
            }
            int result = Factories.getSolver().getCount(sum, arr, 0, n - 1);
            Factories.getNewOutputManager().sendResult(result);
        }
    }
}

interface InputManager {
    int getNextInteger();
}

class STDIOInputManager implements InputManager {
    private Scanner sc;

    STDIOInputManager() {
        sc = new Scanner(System.in);
    }

    public int getNextInteger() {
        return sc.nextInt();
    }
}

interface OutputManager {
    void sendResult(int result);
}

class STDOutputManager implements OutputManager {
    public void sendResult(int result) {
        System.out.println(result);
    }
}

class Validator {
    void assertTestCases(int t) throws Exception {
        if (t <= 0)
            throw new Exception("Bad Input");
    }

    void assertInputSize(int n) throws Exception {
        if (n <= 0)
            throw new Exception("Bad Input");
    }
}

interface PartitionSolver {
    int getCount(long sum, int[] arr, int startIndex, int endIndex) throws IOException;
}

class DPPartitionSolver implements PartitionSolver {
    private int[][] dp;
    private FileWriter fileWriter = new FileWriter("/tmp/x");

    DPPartitionSolver() throws IOException {
    }

    public int getCount(long sum, int[] arr, int startIndex, int endIndex) throws IOException {
        if (dp == null) {
            dp = new int[arr.length][];
            for (int i = 0; i < dp.length; i++) {
                dp[i] = new int[arr.length];
                Arrays.fill(dp[i], -1);
            }
        }

        if (startIndex == endIndex || startIndex - 1 == endIndex)
            return 0;

        int result = getFromDP(startIndex, endIndex);

        if (result >= 0)
            return result;

        long s = 0;
        int count = 0;
        for (int i = startIndex; i < endIndex; i++) {
            s += arr[i];
            sum -= arr[i];
            if (s == sum) {
                if (count == 0)
                    count = 1;

                count = Math.max(count, getCount(s, arr, startIndex, i) + 1);
                count = Math.max(count, getCount(sum, arr, i + 1, endIndex) + 1);
            }
        }
        putInDP(startIndex, endIndex, count);
        fileWriter.append(sum + "," + startIndex + "," + endIndex + "," + result + "," + count + "\n");
        return count;
    }

    private int getFromDP(int startIndex, int endIndex) {
        return dp[startIndex][endIndex];
    }

    private void putInDP(int startIndex, int endIndex, int count) {
        dp[startIndex][endIndex] = count;
    }
}

class Factories {
    static InputManager getNewInputManager() {
        return new STDIOInputManager();
    }

    static OutputManager getNewOutputManager() {
        return new STDOutputManager();
    }

    static Validator getValidator() {
        return new Validator();
    }

    static PartitionSolver getSolver() throws IOException {
        return new DPPartitionSolver();
    }
}