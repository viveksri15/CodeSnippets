package algos.hackerEarchSept16;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;


public class SightSeeing {
    public static void main(String args[]) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        int t = Integer.parseInt(line);
        for (int i = 0; i < t; i++) {
            line = br.readLine();
            String[] split = line.split("\\s");
            int n = Integer.parseInt(split[0]);
            int m = Integer.parseInt(split[1]);
            long[] h = new long[m];
            line = br.readLine();
            split = line.split("\\s");
            for (int j = 0; j < split.length; j++) {
                h[j] = Long.parseLong(split[j]);
            }
            Map<Integer, List<Integer>> path = new HashMap<>();
            for (int j = 0; j < m; j++) {
                line = br.readLine();
                split = line.split("\\s");
                int a = Integer.parseInt(split[0]) - 1;
                int b = Integer.parseInt(split[1]) - 1;
                List<Integer> integers = path.get(a);
                if (integers == null) {
                    integers = new ArrayList<>();
                    path.put(a, integers);
                }
                integers.add(b);
            }
            long hDiff = findMax(path, h, n);
            System.out.println(hDiff);
        }
    }

    private static long findMax(Map<Integer, List<Integer>> path, long[] h, int n) {
        long maxDiff = 0L;
        List<Integer> queue = new LinkedList<>();
        queue.add(0);
        Set<Integer> notVisited = new HashSet<>();

        for (int i = 0; i < n; i++)
            notVisited.add(i);

        Map<Integer, Integer> ancestors = new HashMap<>();
        while (queue.size() > 0) {
            int element = queue.remove(0);
            notVisited.remove(element);
            Integer ancestor = ancestors.get(element);
            if (ancestor == null)
                ancestor = element;
            long h0 = h[element];
            long h1 = h[ancestor];
            long minH = Math.min(h0, h1);
            List<Integer> nbrs = path.get(element);
            if (nbrs != null) {
                for (int next : nbrs) {
                    long ht = h[next];
                    if (ht - minH > maxDiff) {
                        maxDiff = ht - minH;
                    }
                    int nextAncestor = Math.min(ht, minH) == ht ? next : Math.min(h0, h1) == h0 ? element : ancestor;
                    Integer oldNextAncestor = ancestors.get(next);
                    if (oldNextAncestor == null || h[nextAncestor] < h[oldNextAncestor]) {
                        ancestors.put(next, nextAncestor);
                        queue.add(next);
                    }
                }
            }
            if (queue.size() == 0 && notVisited.size() > 0) {
                queue.add(notVisited.iterator().next());
            }
        }
        return maxDiff;
    }
}