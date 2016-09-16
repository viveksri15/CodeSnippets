package algos.hackerEarchSept16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class MaxSafety {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        int t = Integer.parseInt(line);
        for (int i = 0; i < t; i++) {
            line = br.readLine();
            String[] split = line.split("\\s");
            int n = Integer.parseInt(split[0]);
            int m = Integer.parseInt(split[1]);
            Map<Integer, Node> nodes = new HashMap<>();
            for (int j = 0; j < n - 1; j++) {
                line = br.readLine();
                if ("".equals(line)) {
                    j--;
                    continue;
                }
                split = line.split("\\s");
                int a = Integer.parseInt(split[0]);
                int b = Integer.parseInt(split[1]);

//                int a1 = Math.min(a, b);
//                int b1 = Math.max(a, b);

                Node nodeA = getNode(nodes, a);
                Node nodeB = getNode(nodes, b);
                nodeA.children.add(b);
//                nodeB.children.add(a);
            }

            createAncestors(nodes, getNode(nodes, 1), 0);

//            for (int j = 1; j <= n; j++) {
//                Node node = getNode(nodes, j);
//                System.out.println(node);
//            }

            int d = 0;
            for (int j = 0; j < m; j++) {
                line = br.readLine();
                if ("".equals(line)) {
                    j--;
                    continue;
                }
                split = line.split("\\s");
                int a1 = Integer.parseInt(split[0]);
                int b1 = Integer.parseInt(split[1]);
//                Random random = new Random();
//                int a1 = 1 + random.nextInt(n - 1);
//                int b1 = 1 + random.nextInt(n - 1);
                int a = (a1 - 1 + d) % n + 1;
                int b = (b1 - 1 + d) % n + 1;
                d = getDistance(nodes, a, b);
                System.out.println(d);
//                System.out.println(a + "," + b + "," + d);
            }
        }
    }

    private static int getDistance(Map<Integer, Node> nodes, int a, int b) {
        if (a == b)
            return 0;

        Node nodeA = getNode(nodes, a);
        Node nodeB = getNode(nodes, b);

        if (nodeB.ancestors.containsKey(a)) {
            return nodeB.deepestNodeHeight + nodeB.ancestors.get(a);
        }

        if (nodeA.ancestors.containsKey(b)) {
            return getDepth(nodes, nodeB, nodeA);
        }

        TreeMap<Integer, Integer> ancestorsA = nodeA.ancestors;
        TreeMap<Integer, Integer> ancestorsB = nodeB.ancestors;

        TreeMap<Integer, Integer> small = ancestorsA.size() <= ancestorsB.size() ? ancestorsA : ancestorsB;
        TreeMap<Integer, Integer> large = ancestorsA.size() > ancestorsB.size() ? ancestorsA : ancestorsB;

        int closestRoot = 1, closestRootHeight = Integer.MAX_VALUE;
        for (int root : small.keySet()) {
            if (large.containsKey(root)) {
                int ht = small.get(root);
                if (ht < closestRootHeight) {
                    closestRoot = root;
                    ht = closestRootHeight;
                }
            }
        }

        System.err.println(closestRoot);
        return nodeA.ancestors.get(closestRoot) + nodeB.ancestors.get(closestRoot) + nodeB.deepestNodeHeight;
    }

    private static int getDepth(Map<Integer, Node> nodes, Node nodeB, Node nodeA) {

        if (nodeA.number == nodeB.number)
            return nodeA.deepestNodeHeight;

        int maxDepth = 1;
        int aParentbChild = nodeA.number;
        int distance = nodeA.ancestors.get(nodeB.number);
        for (int child : nodeB.children) {
            if (!nodeA.ancestors.containsKey(child) && child != nodeA.number) {
                int d = getNode(nodes, child).deepestNodeHeight + distance + 1;
                if (d > maxDepth) {
                    maxDepth = d;
                }
            } else
                aParentbChild = child;
        }

        int d = getDepth(nodes, getNode(nodes, aParentbChild), nodeA);
        if (d > maxDepth) {
            maxDepth = d;
        }

        return maxDepth;
    }

    private static void createAncestors(Map<Integer, Node> nodes, Node node, int depth) {
        if (node.children.size() == 0) {
            for (int parent : node.ancestors.keySet()) {
                int height = node.ancestors.get(parent);
                Node parentNode = nodes.get(parent);
                if (parentNode.deepestNodeHeight < height) {
                    parentNode.deepestNode = node.number;
                    parentNode.deepestNodeHeight = height;
                }
            }
        } else {
            for (int child : node.children) {
                Node childNode = getNode(nodes, child);
                for (int ancestor : node.ancestors.keySet()) {
                    childNode.ancestors.put(ancestor, node.ancestors.get(ancestor) + 1);
                }
                childNode.ancestors.put(node.number, 1);
                createAncestors(nodes, childNode, depth + 1);
            }
        }
    }

    private static Node getNode(Map<Integer, Node> nodes, int x) {
        Node node = nodes.get(x);
        if (node != null)
            return node;

        node = new Node();
        node.number = x;
        nodes.put(x, node);
        return node;
    }
}

class Node {
    int number;
    List<Integer> children = new ArrayList<>();
    TreeMap<Integer, Integer> ancestors = new TreeMap<>();
    Integer deepestNode = 0;
    Integer deepestNodeHeight = 0;

    @Override
    public String toString() {
        return number + ", deepestNode=" + deepestNode + ", deepestNodeHeight=" + deepestNodeHeight + ", ancestors=" + ancestors + ", children=" + children;
    }
}