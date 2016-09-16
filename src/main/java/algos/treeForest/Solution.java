package algos.treeForest;

/*

	https://www.hackerrank.com/challenges/even-tree
	Framework
		Input and validations
		Output and formatting, catching exceptions
		Algorithm
		DS used for algo above
		Assumptions
		Limitations
		TestFW
*/


/*
Core algo

L/R == 1
	cannot remove
L/R == 2
	remove
else if(child.count %2 == 0)
	remove
else
	cannot remove

DS used:

	Graph

	counter

*/

import java.util.*;

interface IForest {
	int createForest(Node root);
}

class Node {
	private final int nodeNumber;
	private Set<Node> children;

	public Node(int nodeNumber) {
		this.nodeNumber = nodeNumber;
		children = new HashSet<>();
	}

	public Set<Node> getChildren() {
		return children;
	}

	public int getNodeNumber() {
		return this.nodeNumber;
	}

	public void addChild(Node node) {
		children.add(node);
	}

	public void removeChild(Node node) {
		children.remove(node);
	}

	@Override
	public String toString() {
		return nodeNumber + "";
	}
}

class ForestCreator implements IForest {
	public Map<Node, Integer> subtreeCount = new HashMap<Node, Integer>();
	public int counter = 0;

	public int createForest(Node node) {
		makeCounters(node);
//		System.out.println("subtreeCount = " + subtreeCount);
		removeEdges(node);
		return counter;
	}

	private void removeEdges(Node node) {
		Iterator<Node> iterator = node.getChildren().iterator();
		while (iterator.hasNext()) {

			Node child = iterator.next();

			removeEdges(child);

			if (subtreeCount.get(child) % 2 == 0) {

				int childCount = subtreeCount.get(child);
				int nodeCount = subtreeCount.get(node);

//				System.out.println("removing = " + node + " " + child);
				iterator.remove();

				int newNodeCount = nodeCount - childCount;
				subtreeCount.put(node, newNodeCount);

				counter++;
			}
		}
	}

	private void makeCounters(Node node) {

//		System.out.println("node = " + node);

		if (subtreeCount.containsKey(node))
			return;

		if (node.getChildren().size() == 0) {
			subtreeCount.put(node, 1);
		}

		int sum = 0;

		for (Node child : node.getChildren()) {
//			System.out.println("child = " + child);
			makeCounters(child);
			sum += subtreeCount.get(child);
		}

		subtreeCount.put(node, sum + 1);
	}
}

public class Solution {
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int m = scanner.nextInt();
		Map<Integer, Node> dataToNode = new HashMap<>();
		Node root = null;
		for (int i = 0; i < m; i++) {
			int parent = scanner.nextInt();
			int child = scanner.nextInt();

			Node parentNode = dataToNode.get(parent);
			Node childNode = dataToNode.get(child);

			if (parentNode != null && childNode != null)
				throw new Exception("Cycle detected");

			if (childNode != null) {

				//swap
				parentNode = childNode;
				childNode = new Node(parent);

			} else if (parentNode != null) {

				childNode = new Node(child);

			} else {

				parentNode = new Node(parent);
				childNode = new Node(child);
			}

			if (root == null)
				root = parentNode;

			parentNode.addChild(childNode);
			dataToNode.put(childNode.getNodeNumber(), childNode);
			dataToNode.put(parentNode.getNodeNumber(), parentNode);

		}


		IForest forest = new ForestCreator();
		int counter = forest.createForest(root);
		System.out.println(counter);
	}
}