package algos.poisonousPlants;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

interface IPoisonousPlant {
    int getDays(int[] plants);
}

interface IO {
    void read(Scanner scanner);

    void process();

    void write(OutputStream outputStream) throws IOException;
}

final class PoisonousPlantImpl implements IPoisonousPlant {
    private final Deque<Integer> stack1 = new ArrayDeque<>();
    private final Deque<Integer> stack2 = new ArrayDeque<>();
    private Deque<Integer> primaryStack;
    private Deque<Integer> secondaryStack;

    public PoisonousPlantImpl() {
        primaryStack = stack1;
        secondaryStack = stack2;
    }

    private void swapStacks() {
        Deque<Integer> tempStack = primaryStack;
        primaryStack = secondaryStack;
        secondaryStack = tempStack;
        secondaryStack.clear();
    }

    private void initPrimary(int[] plants) {
        for (int i = 0; i < plants.length; i++) {
            primaryStack.addLast(plants[i]);
        }
    }

    private void feedFromPrimaryToSecondary() {
        Integer plant = -1;
        int i = 0;
        Integer leftPlant = -1;
        while ((plant = primaryStack.pollFirst()) != null) {
            if (i == 0) {
                secondaryStack.addLast(plant);
                leftPlant = plant;
                i++;
                continue;
            }
            if (isLeftPlantWeek(leftPlant, plant))
                secondaryStack.addLast(plant);
            leftPlant = plant;
            i++;
        }
        swapStacks();
        System.out.println("primaryStack = " + primaryStack);
    }

    private boolean isLeftPlantWeek(Integer leftPlant, Integer plant) {
        return plant <= leftPlant;
    }

    public int getDays(int[] plants) {
        int dayCount = 0;
        int size;
        initPrimary(plants);
        System.out.println("primaryStack = " + primaryStack);
        do {
            size = primaryStack.size();
            feedFromPrimaryToSecondary();
            if (primaryStack.size() == size)
                return dayCount;
            dayCount++;
        } while (true);
    }
}

final class PoisonousPlantImplO1 implements IPoisonousPlant {
    private final Deque<Integer> stack1 = new ArrayDeque<>();
    private int days = 0, maxDays = 0;

    private void initPrimary(int[] plants) {
        for (int plant : plants) {
            stack1.addLast(plant);
        }
    }

    private boolean isPlantWeak(Integer leftPlant, Integer plant) {
        return plant > leftPlant;
    }

    public int getDays(int[] plants) {
//        initPrimary(plants);
        System.out.println("primaryStack = " + stack1);
        int lastValue = Integer.MAX_VALUE;
        Integer currentLeftValue = -1;
        for (int plant : plants) {
//            boolean dayIncrement = false;
            System.out.println("-----------------");
            System.out.println("plant = " + plant);
            System.out.println("lastValue = " + lastValue);
            if (stack1.isEmpty()) {
                stack1.addLast(plant);
            } else {
                if (!isPlantWeak(lastValue, plant)) {
                    if (days == 0)
                        currentLeftValue = stack1.peekLast();
                    System.out.println("currentLeftValue = " + currentLeftValue);
                    if (isPlantWeak(currentLeftValue, plant) || currentLeftValue == plant) {
                        if (!isPlantWeak(stack1.peekLast(), plant))
                            stack1.addLast(plant);

                        if (plant == currentLeftValue) {
                            days++;
                        } else
                            currentLeftValue = plant;

                        System.out.println("days = " + days);
                    } else {
                        stack1.addLast(plant);
                        maxDays = Math.max(days, maxDays);
                        days = 0;
                    }
                }

                if (isPlantWeak(lastValue, plant) && days == 0)
                    days = 1;
            }
            System.out.println("stack1 = " + stack1);
            lastValue = plant;
        }
        maxDays = Math.max(days, maxDays);
        return maxDays;
    }
}

final class StdIO implements IO {

    private int[] dataI = null;
    private int dayCount = 0;

    @Override
    public void read(Scanner scanner) {
        scanner.nextLine();
        String data = scanner.nextLine();
        String[] dataA = data.split("\\W");
        dataI = new int[dataA.length];
        for (int i = 0; i < dataA.length; i++) {
            String s = dataA[i];
            dataI[i] = Integer.valueOf(s);
        }
    }

    @Override
    public void process() {
        IPoisonousPlant iPoisonousPlant = new PoisonousPlantImplO1();
        dayCount = iPoisonousPlant.getDays(dataI);
    }

    @Override
    public void write(OutputStream outputStream) throws IOException {
        outputStream.write((dayCount + "\n").getBytes());
    }
}

public class Solution {

    public static void main(String[] args) throws IOException {
        IO io = new StdIO();
        io.read(new Scanner(System.in));
        long t0 = System.currentTimeMillis();
        io.process();
        System.out.println("t0 = " + (System.currentTimeMillis() - t0));
        io.write(System.out);
    }
}