package algos.codeChefSept16.friends;

import java.util.*;

public class Main1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int i = 0; i < t; i++) {
            boolean solution = new Main1().solve(sc);
            if (solution)
                System.out.println("YES");
            else
                System.out.println("NO");
        }
    }

    private boolean solve(Scanner sc) {
        int n = sc.nextInt();
        int m = sc.nextInt();
        if (n == 0)
            return true;

        if (m == 0 && n <= 2)
            return true;

        if (m == 0)
            return false;

        Map<Integer, Set<Integer>> map = new HashMap<>();
        List<Integer> table1 = new ArrayList<>();
        List<Integer> table2 = new ArrayList<>();
        Set<Integer> table1BL = new HashSet<>();
        Set<Integer> table2BL = new HashSet<>();

        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            add(map, a, b);
            add(map, b, a);
        }

        for (int i = 1; i <= n; i++) {
            boolean added = sit(table1, table2, i, map, table1BL, table2BL) || sit(table2, table1, i, map, table2BL, table1BL);
            if (!added)
                return false;
        }
//        System.out.println(table1);
//        System.out.println(table2);
        return true;
    }

    private boolean sit(List<Integer> table, List<Integer> otherTable, int a, Map<Integer, Set<Integer>> map,
                        Set<Integer> table1BL, Set<Integer> otherTableBL) {
        if (table1BL.contains(a))
            return false;
        Set<Integer> set = map.get(a);
        if (set == null && table.size() == 0) {
            table.add(a);
            return true;
        } else if (set == null)
            return false;
        for (int i = 0; i < table.size(); i++) {
            int p = table.get(i);
            if (!set.contains(p)) {
                if (!sit(otherTable, table, p, map, otherTableBL, table1BL)) {
                    return false;
                } else {
                    table1BL.add(p);
                    table.remove(i);
                    i--;
                }
            }
        }

        table.add(a);
        return true;
    }

    private void add(Map<Integer, Set<Integer>> map, int b, int a) {
        Set<Integer> m = map.get(b);
        if (m == null) {
            m = new HashSet<>();
            map.put(b, m);
        }
        m.add(a);
    }
}
