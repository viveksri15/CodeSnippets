package algos.tree;

/**
 * Created by vivek on 24/09/16.
 */
public class Tree {

    private int maxRight = 0, maxLeft = 0;

    public int getDiameter(Node root) {
        if (root == null)
            return 0;

        traverse(root, 0);
        return -maxLeft + maxRight + 1;
    }

    private void traverse(Node node, int count) {
        if (node == null)
            return;

        traverse(node.getLeft(), count - 1);
        traverse(node.getRight(), count + 1);

        maxLeft = Math.min(maxLeft, count);
        maxRight = Math.max(maxRight, count);
    }

    private Node ancestor;

    public Node getFirstCommonAncestor(Node root, int a, int b) {
        findCommonAncestor(root, a, b);

        return ancestor;
    }

    private int findCommonAncestor(Node root, int a, int b) {

        int count = 0;
        if (root == null)
            return 0;

        if (root.getData() == a || root.getData() == b) {
            count++;
        }

        if (count < 2) {
            count += findCommonAncestor(root.getLeft(), a, b);
        }

        if (count < 2) {
            count += findCommonAncestor(root.getRight(), a, b);
        }

        if (count == 2 && ancestor == null)
            ancestor = root;

//        System.out.println(root.getData() + "," + count + "," + ancestor);
        return count;
    }
}
