package algos.cityOfLights;

import java.util.*;

/**
 * Created by viveksrivastava on 28/05/16.
 */
public class Solutions {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int m = scanner.nextInt();
		Map<Integer, Node> nodes = new HashMap<>();
		for (int i = 0; i < m; i++) {
			int n1 = scanner.nextInt();
			int n2 = scanner.nextInt();
			int w = scanner.nextInt();
			Node node1 = nodes.get(n1);
			Node node2 = nodes.get(n2);
			if (node1 == null) {
				node1 = new Node(n1);
				nodes.put(n1, node1);
			}

			if (node2 == null) {
				node2 = new Node(n2);
				nodes.put(n2, node2);
			}

			node1.addNode(node2, w);
		}

		ShortestPath shortestPath = new ShortestPath();

		int q = scanner.nextInt();
		Cache cache = new Cache();
		for (int i = 0; i < q; i++) {
			int n1 = scanner.nextInt();
			int n2 = scanner.nextInt();
			HashSet<Integer> visitedNodes = new HashSet<>();
			visitedNodes.add(n1);
			int distance = shortestPath.getShortestPath(nodes.get(n1), nodes.get(n2), visitedNodes, cache, nodes);
			System.out.println(distance);
//			System.out.println(n1 + " " + n2 + " " + distance);
		}
//		cache.print();
	}
}

class Node {
	private Map<Integer, Integer> nodes = new HashMap<>();
	private int data;

	Node(int data) {
		this.data = data;
	}

	public int getData() {
		return data;
	}

	public void addNode(Node node, int distance) {
		nodes.put(node.data, distance);
	}

	public Map<Integer, Integer> getNodes() {
		return nodes;
	}

	@Override
	public String toString() {
		return data + "";
	}
}


class PQEntry {
	Node node;
	int distance;

	public PQEntry(Node node, int distance) {
		this.node = node;
		this.distance = distance;
	}
}

class Cache {
	private Map<Integer, Map<Integer, Integer>> distanceMap = new HashMap<>();

	public void print() {
		System.out.println(distanceMap);
	}

	public void addToCache(int n1, int n2, int w) {
		Map<Integer, Integer> map = distanceMap.get(n1);
		if (map == null) {
			map = new HashMap<>();
			distanceMap.put(n1, map);
		}
		map.put(n2, w);
	}

	public int getFromCache(int n1, int n2) {
		Map<Integer, Integer> map = distanceMap.get(n1);
		if (map == null)
			return -2;
		Integer distance = map.get(n2);
		if (distance == null)
			return -2;
		return distance;
	}
}

class ShortestPath {


	int getShortestPath(Node node1, Node node2, Set<Integer> visitedNodes, Cache cache, Map<Integer, Node> nodes) {

		if (node1.getData() == node2.getData())
			return 0;

		int fromCache = cache.getFromCache(node1.getData(), node2.getData());

		if (fromCache > -1)
			return fromCache;


		int shortestDistance = -1;

		Map<Integer, Integer> node1Nodes = node1.getNodes();

		for (Integer node : node1Nodes.keySet()) {

			Integer distance = node1Nodes.get(node);

			if (node == node2.getData() && shortestDistance == -1)
				shortestDistance = distance;

			if (visitedNodes.contains(node))
				continue;
			visitedNodes.add(node);

			int nodeShortestPath = getShortestPath(nodes.get(node), node2, visitedNodes, cache, nodes);
			visitedNodes.remove(node);
//			System.out.println("shortest path between " + node + "," + node2 + "=" + nodeShortestPath + " " + shortestDistance + " " + distance);
			if (nodeShortestPath > -1) {
				if (shortestDistance == -1 || shortestDistance > distance + nodeShortestPath) {
					shortestDistance = distance + nodeShortestPath;
				}
			}
//			System.out.println(" till now path between " + node1 + "," + node2 + "=" + shortestDistance);
		}
//		System.out.println("shortest path between1 " + node1 + "," + node2 + "=" + shortestDistance);
		cache.addToCache(node1.getData(), node2.getData(), shortestDistance);
		return shortestDistance;
	}

}