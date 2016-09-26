package algos.heSept2016.fredo.rescuer;

import java.util.StringTokenizer;

/**
 * Created by vivek on 20/09/16.
 */
public class InfixFullParantEx {

    public static String evaluateInfix(String exps) {

        exps = exps.replaceAll("\\s+", "");
        MyGenericsStack<String> stack = new MyGenericsStack<String>(exps.length());
        StringTokenizer tokens = new StringTokenizer(exps, "{}()*/+-", true);
        while (tokens.hasMoreTokens()) {
            String tkn = tokens.nextToken();
            if (tkn.equals("(")
                    || tkn.equals("{")
                    || tkn.matches("[0-9]+")
                    || tkn.equals("*")
                    || tkn.equals("/")
                    || tkn.equals("+")
                    || tkn.equals("^")
                    || tkn.equals("-")) {
                stack.push(tkn);
            } else if (tkn.equals("}") || tkn.equals(")")) {
                try {
                    double op2 = Double.parseDouble(stack.pop());
                    String oprnd = stack.pop();
                    double op1 = Double.parseDouble(stack.pop());
                    /**Below pop removes either } or ) from stack**/
                    if (!stack.isStackEmpty()) {
                        stack.pop();
                    }
                    double result = 0;
                    if (oprnd.equals("*")) {
                        result = op1 * op2;
                    } else if (oprnd.equals("/")) {
                        result = op1 / op2;
                    } else if (oprnd.equals("+")) {
                        result = op1 + op2;
                    } else if (oprnd.equals("-")) {
                        result = op1 - op2;
                    }
                    stack.push(result + "");
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
        String finalResult = "";
        try {
            finalResult = stack.pop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalResult;
    }

    public static void main(String a[]) {
        String expr = "((2*5)+(6/2))";
        System.out.println("Expression: " + expr);
        System.out.println("Final Result: " + evaluateInfix(expr));
        expr = "(((2 * 5) - (1 * 2)) / (11 - 9))";
        System.out.println("Expression: " + expr);
        System.out.println("Final Result: " + evaluateInfix(expr));

    }
}

class MyGenericsStack<T extends Object> {

    private int stackSize;
    private T[] stackArr;
    private int top;

    /**
     * constructor to create stack with size
     *
     * @param size
     */
    @SuppressWarnings("unchecked")
    public MyGenericsStack(int size) {
        this.stackSize = size;
        this.stackArr = (T[]) new Object[stackSize];
        this.top = -1;
    }

    /**
     * This method adds new entry to the top
     * of the stack
     *
     * @param entry
     * @throws Exception
     */
    public void push(T entry) {
        if (this.isStackFull()) {
            System.out.println(("Stack is full. Increasing the capacity."));
            this.increaseStackCapacity();
        }
        System.out.println("Adding: " + entry);
        this.stackArr[++top] = entry;
    }

    /**
     * This method removes an entry from the
     * top of the stack.
     *
     * @return
     * @throws Exception
     */
    public T pop() throws Exception {
        if (this.isStackEmpty()) {
            throw new Exception("Stack is empty. Can not remove element.");
        }
        T entry = this.stackArr[top--];
        System.out.println("Removed entry: " + entry);
        return entry;
    }

    /**
     * This method returns top of the stack
     * without removing it.
     *
     * @return
     */
    public T peek() {
        return stackArr[top];
    }

    private void increaseStackCapacity() {

        @SuppressWarnings("unchecked")
        T[] newStack = (T[]) new Object[this.stackSize * 2];
        for (int i = 0; i < stackSize; i++) {
            newStack[i] = this.stackArr[i];
        }
        this.stackArr = newStack;
        this.stackSize = this.stackSize * 2;
    }

    /**
     * This method returns true if the stack is
     * empty
     *
     * @return
     */
    public boolean isStackEmpty() {
        return (top == -1);
    }

    /**
     * This method returns true if the stack is full
     *
     * @return
     */
    public boolean isStackFull() {
        return (top == stackSize - 1);
    }
}