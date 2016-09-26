package algos.tree;

/**
 * Created by vivek on 25/09/16.
 */
public class BST {
    public boolean isBST(Node node) {

        if (node == null)
            return true;

        int fromLeft = getFromLeft(node.getLeft());
        int fromRight = getFromRight(node.getRight());
        System.out.println(fromLeft + "," + node.getData() + "," + fromRight);
        return !(node.getData() <= fromLeft || node.getData() >= fromRight);

    }

    private int getFromRight(Node node) {
        //getMin.. left or root

        if (node == null)
            return Integer.MAX_VALUE;

        if (node.getLeft() == null && node.getRight() == null)
            return node.getData();

        int fromLeft = getFromLeft(node.getLeft());
        int fromRight = getFromRight(node.getRight());
        System.out.println(fromLeft + "," + node.getData() + "," + fromRight);

        if (node.getData() <= fromLeft || node.getData() >= fromRight)
            return Integer.MIN_VALUE;

        if (node.getLeft() == null)
            return node.getLeft().getData();

        return fromLeft;
    }

    private int getFromLeft(Node node) {
        //getMax.. right or root

        if (node == null)
            return Integer.MIN_VALUE;

        if (node.getLeft() == null && node.getRight() == null)
            return node.getData();

        int fromLeft = getFromLeft(node.getLeft());
        int fromRight = getFromRight(node.getRight());
        System.out.println(fromLeft + "," + node.getData() + "," + fromRight);

        if (node.getData() <= fromLeft || node.getData() >= fromRight)
            return Integer.MAX_VALUE;

        if (node.getLeft() == null)
            return node.getRight().getData();

        return fromRight;
    }

    public static void main(String[] args) {
        Node node = new Node(6);
        Node l1 = new Node(4);
        Node ll1 = new Node(3);
        Node lr1 = new Node(5);
        Node r1 = new Node(10);
        Node rl1 = new Node(7);
        Node rr1 = new Node(100);

        node.setLeft(l1);
        node.setRight(r1);
        l1.setLeft(ll1);
        l1.setRight(lr1);

        r1.setLeft(rl1);
        r1.setRight(rr1);

        System.out.println(new BST().isBST(node));
    }
}
