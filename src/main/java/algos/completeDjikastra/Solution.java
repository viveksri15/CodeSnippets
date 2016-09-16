package algos.completeDjikastra;

//https://www.hackerrank.com/challenges/bfsshortreach

import java.util.*;

/*
    DS (PQ + Adj Mx), Algo, Assuptions, Limitation, Input and validations, Output and checked exception handling if any, TestFW
*/

//Specification
interface CompleteDjakastra {
    int[] solve(List<Set<Integer>> graph, int startingNode);
}

//Impl
class CompleteDjakastraSolver implements CompleteDjakastra {


    private class Item implements Comparable<Item> {
        int index = -1;
        private int[] distance;

        public Item(int index, int[] distance) {
            this.index = index;
            this.distance = distance;
        }

        @Override
        public int compareTo(Item item) {
            return distance[index] - distance[item.index];
        }

        @Override
        public String toString() {
            return index + " ";
        }
    }

    public int[] solve(List<Set<Integer>> graph, int startingNode) {

        int[] distances = new int[graph.size()];
        boolean[] added = new boolean[graph.size()];

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
        for (int adjNode : graph.get(startingNode)) {

            pq.add(new Item(adjNode, distances));

            distances[adjNode] = 6;
            added[adjNode] = true;
        }

//        System.out.println("pq = " + algos.Arrays.toString(pq.toArray()));
//        System.out.println("distances = " + algos.Arrays.toString(distances));

        for (int node = 0; node < n; node++) {
            while (pq.peek() != null) {
//                System.out.println("pq = " + algos.Arrays.toString(pq.toArray()));
                Item adjNode = pq.poll();

                int currentDistance = distances[adjNode.index];
//                System.out.println("adjNode=" + adjNode + ", currentDistance = " + currentDistance);

                for (int adjAdjNode : graph.get(adjNode.index)) {

                    int adjAdjDistance = distances[adjAdjNode];
                    int newDistance = currentDistance + 6;

//                    System.out.println("adjAdjNode = " + adjAdjNode + ", adjAdjDistance=" + adjAdjDistance + ", newDistance=" + newDistance);

                    if (newDistance < adjAdjDistance || adjAdjDistance == -1) {
                        distances[adjAdjNode] = newDistance;
                    }

                    if (!added[adjAdjNode]) {
                        added[adjAdjNode] = true;
                        pq.add(new Item(adjAdjNode, distances));
                    }
                }

//                System.out.println("distances = " + algos.Arrays.toString(distances));
            }
        }
//        System.out.println("finaldistances = " + algos.Arrays.toString(distances));
        return distances;
    }
}

public class Solution {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int testCases = scanner.nextInt();
        for (int i = 0; i < testCases; i++) {
            int vertices = scanner.nextInt();
            int edges = scanner.nextInt();
            List<Set<Integer>> graph = new ArrayList<>();
            for (int j = 0; j < vertices; j++)
                graph.add(new HashSet<Integer>());
            for (int j = 0; j < edges; j++) {
                int node1 = scanner.nextInt() - 1;
                int node2 = scanner.nextInt() - 1;
                graph.get(node1).add(node2);
                graph.get(node2).add(node1);
            }
            int toFind = scanner.nextInt() - 1;
            CompleteDjakastra completeDjakastra = new CompleteDjakastraSolver();
            int[] solve = completeDjakastra.solve(graph, toFind);

            for (int j = 0; j < vertices; j++)
                if (j != toFind)
                    System.out.print(solve[j] + " ");

            System.out.println();
        }
    }
}