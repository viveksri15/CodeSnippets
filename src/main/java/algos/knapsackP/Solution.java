package algos.knapsackP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Solution {

    public static void main(String[] args) {
        IOInput ioInput = new IOInput();
        List<InputData> datas = ioInput.read();
        for (InputData inputData : datas) {
            DPKnapsackSolver dpKnapsackSolver = new DPKnapsackSolver();
            int solution = dpKnapsackSolver.solve(inputData.inputArray, inputData.maxSum);
            System.out.println(solution);
        }
    }
}

class IOInput {
    private List<InputData> inputDataList = new LinkedList<>();

    public List<InputData> read() {

        int linesRead = 0;
        try {
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(System.in));

            String input;
            int inputs = -1, counter = 0;
            int maxSum = -1, elementCount = -1;
            int[] inputElements = null;
            while ((input = br.readLine()) != null) {
                if (inputs == -1) {
                    inputs = Integer.parseInt(input);
                    if (inputs == 0)
                        System.out.println(0);
                } else if (linesRead < inputs) {
                    String[] inpA = input.split("\\W");
                    switch (counter) {
                        case 0:
                            elementCount = Integer.parseInt(inpA[0]);
                            inputElements = new int[elementCount];
                            maxSum = Integer.parseInt(inpA[1]);
                            counter++;
                            if (elementCount == 0 || maxSum == 0) {
                                System.out.println(0);
                                counter = 0;
                                linesRead++;
                            }
                            break;
                        case 1:
                            linesRead++;
                            int i = 0;
                            for (String e : inpA)
                                inputElements[i++] = Integer.parseInt(e);
                            InputData inputData = new InputData(inputElements, maxSum);
                            inputDataList.add(inputData);
                            counter = 0;
                            break;
                    }
                }

                if (linesRead == inputs) {
                    break;
                }
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
        return getInputDataList();
    }

    public List<InputData> getInputDataList() {
        return inputDataList;
    }
}

class InputData {
    public final int[] inputArray;
    public final int maxSum;

    public InputData(int[] inputArray, int maxSum) {
        this.inputArray = inputArray;
        this.maxSum = maxSum;
    }
}