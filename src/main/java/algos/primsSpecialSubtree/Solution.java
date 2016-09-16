package algos.primsSpecialSubtree;

//https://www.hackerrank.com/challenges/dijkstrashortreach

import java.util.*;

/*
    DS (PQ + Adj Mx), Algo, Assuptions, Limitation, Input and validations, Output and checked exception handling if any, TestFW
*/

//Specification
interface Prims {
    Map<Integer, Integer> solve(List<Map<Integer, Integer>> graph, int startingNode);
}

//Impl
class PrimsSolver implements Prims {

    private int[] distances;

    private class Item implements Comparable<Item> {
        int index = -1;

        public Item(int index) {
            this.index = index;
        }

        @Override
        public int compareTo(Item item) {
            return distances[index] - distances[item.index];
        }

        @Override
        public String toString() {
            return index + " ";
        }
    }

    public Map<Integer, Integer> solve(List<Map<Integer, Integer>> graph, int startingNode) {

        distances = new int[graph.size()];
        boolean[] added = new boolean[graph.size()];
        Map<Integer, Integer> path = new HashMap<>();

        for (int i = 0; i < distances.length; i++) {
            added[i] = false;
            if (i != startingNode)
                distances[i] = -1;
            else {
                distances[i] = 0;
                added[i] = true;
            }
        }

        int n = graph.size(); //nodes
        PriorityQueue<Item> pq = new PriorityQueue<>(n);
        Map<Integer, Integer> startingMap = graph.get(startingNode);
        for (int adjNode : startingMap.keySet()) {
            distances[adjNode] = startingMap.get(adjNode);
        }

        for (int adjNode : startingMap.keySet()) {
            pq.offer(new Item(adjNode));
            path.put(adjNode, startingNode);
            added[adjNode] = true;
        }

        System.out.println("pq = " + Arrays.toString(pq.toArray()));
        System.out.println("distances = " + Arrays.toString(distances));

        for (int node = 0; node < n; node++) {
            while (pq.peek() != null) {
                System.out.println("pq = " + Arrays.toString(pq.toArray()));
                Item adjNode = pq.poll();

                int currentDistance = distances[adjNode.index];

                Map<Integer, Integer> adjMap = graph.get(adjNode.index);
                System.out.println("adjMap = " + adjMap);
                for (int adjAdjNode : adjMap.keySet()) {

                    int adjAdjDistance = distances[adjAdjNode];

                    Integer adjNodeDistance = adjMap.get(adjAdjNode);
                    int newDistance = currentDistance + adjNodeDistance;


//                    if (newDistance < adjAdjDistance || adjAdjDistance == -1) {
                    if (adjNodeDistance < adjAdjDistance || adjAdjDistance == -1) {
//                        distances[adjAdjNode] = newDistance;
                        distances[adjAdjNode] = adjNodeDistance;
                        added[adjAdjNode] = true;
                        path.put(adjAdjNode, adjNode.index);
                        pq.offer(new Item(adjAdjNode));
                    } else if (!added[adjAdjNode]) {
                        added[adjAdjNode] = true;
                        path.put(adjNode.index, adjAdjNode);
                        pq.offer(new Item(adjAdjNode));
                    }
                    System.out.println("path = " + path);
                    System.out.println("adjNode=" + adjNode + ", currentDistance = " + currentDistance + ", adjAdjNode = " + adjAdjNode + ", adjNodeDistance=" + adjNodeDistance + ", adjAdjDistance=" + adjAdjDistance + " " + distances[adjAdjNode]);

                }

                System.out.println("distances = " + Arrays.toString(distances));
            }
        }
        System.out.println("path = " + path);
        System.out.println("finaldistances = " + Arrays.toString(distances));
        return path;
    }
}

public class Solution {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
//        int testCases = scanner.nextInt();
//        for (int i = 0; i < testCases; i++) {
        int vertices = scanner.nextInt();
        int edges = scanner.nextInt();
        List<Map<Integer, Integer>> graph = new ArrayList<>();
        for (int j = 0; j < vertices; j++)
            graph.add(new HashMap<Integer, Integer>());

        for (int j = 0; j < edges; j++) {
            int node1 = scanner.nextInt() - 1;
            int node2 = scanner.nextInt() - 1;
            int distance = scanner.nextInt();

            Map<Integer, Integer> adjMap = graph.get(node1);
            Integer d = adjMap.get(node2);
//                if (d != null)
//                    System.out.println("d = " + d + ", node1=" + node1 + " node2=" + node2);
            if (d == null || d > distance) {
                adjMap.put(node2, distance);
                graph.get(node2).put(node1, distance);
            }
        }
        int toFind = scanner.nextInt() - 1;
        Prims completeDjakastra = new PrimsSolver();
        Map<Integer, Integer> path = completeDjakastra.solve(graph, toFind);

        long sum = 0;
        for (int j : path.keySet())
            if (j != toFind)
                sum += graph.get(j).get(path.get(j));
        System.out.println(sum);
//        }
    }
}