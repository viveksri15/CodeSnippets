package algos.tree;

/**
 * Created by vivek on 24/09/16.
 */
public class Node {
    private final int data;
    private Node left, right;

    public Node(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public Node getLeft() {
        return left;
    }

    public Node setLeft(Node left) {
        this.left = left;
        return left;
    }

    public Node getRight() {
        return right;
    }

    public Node setRight(Node right) {
        this.right = right;
        return right;
    }

    @Override
    public String toString() {
        return "root=" + data;
    }
}
