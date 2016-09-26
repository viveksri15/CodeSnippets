package algos.tree;

/**
 * Created by vivek on 24/09/16.
 */
public class Diameter {
    public static void main(String[] args) {
        Tree tree = new Tree();

        Node root = new Node(3);
        root.setLeft(new Node(1)).setLeft(new Node(2));
        root.setRight(new Node(1)).setLeft(new Node(2)).setLeft(new Node(4)).setLeft(new Node(5)).setLeft(new Node(4)).setLeft(new Node(5));

        int diameter = tree.getDiameter(root);
        System.out.println(diameter);
    }
}
