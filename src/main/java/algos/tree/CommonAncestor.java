package algos.tree;

/**
 * Created by vivek on 24/09/16.
 */
public class CommonAncestor {
    public static void main(String[] args) {
        Tree tree = new Tree();

        Node root = new Node(1);
        Node node1 = new Node(2);
        Node node2 = new Node(3);
        Node node3 = new Node(4);
        Node node4 = new Node(5);
        Node node5 = new Node(6);
        Node node6 = new Node(7);
        Node node7 = new Node(8);

        root.setLeft(node1);
        root.setRight(node2);

        node1.setLeft(node3);
        node1.setRight(node4).setLeft(node5);

        node2.setLeft(node6);
        node2.setRight(node7);


        Node ancestor = tree.getFirstCommonAncestor(root, 8, 7);
        if (ancestor != null)
            System.out.println(ancestor.getData());
        else
            System.out.println("no ancestor");
    }
}
