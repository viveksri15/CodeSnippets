package algos.hanoi;

import java.util.Stack;

/**
 * Created by viveksrivastava on 17/05/16.
 */
public class Solver {
	public static void main(String[] args) {
		Solver solver = new Solver();
		Stack<Integer> aStack = new Stack<>();
		Stack<Integer> bStack = new Stack<>();
		Stack<Integer> cStack = new Stack<>();
		aStack.push(2);
		aStack.push(1);
		aStack.push(0);
		solver.move(aStack.size(), aStack, bStack, cStack, 0, 1, 2);
	}

	public void move(int elementsToMove, Stack<Integer> aStack, Stack<Integer> bStack, Stack<Integer> cStack, int a, int b, int c) {
		move(elementsToMove - 1, aStack, cStack, bStack, a, c, b);
		Integer disk = aStack.pop();
		bStack.push(disk);
		System.out.println("move disk " + disk + " from " + a + " " + b);
		move(elementsToMove - 1, cStack, bStack, aStack, c, b, a);
	}
}