package algos.codeChefSept16.friends;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int i = 0; i < t; i++) {
            Main main = new Main();
            boolean solution = main.solve(sc);
            if (solution)
                System.out.println("YES");
            else
                System.out.println("NO");

        }
    }


    private boolean solve(Scanner sc) {
        List<Integer> table1 = new ArrayList<>();
        List<Integer> table2 = new ArrayList<>();

        int n = sc.nextInt();
        int m = sc.nextInt();

        int[][] friendMap = new int[n][n];

        for (int i = 0; i < m; i++) {
            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;
            friendMap[a][b] = 1;
            friendMap[b][a] = 1;
        }


        table1.add(0);

        for (int i = 0; i < n; i++)
            friendMap[i][i] = 1;

        for (int j = 1; j < n; j++) {
            table2.add(j);
        }

        int size = table2.size();
        for (int i = 0; i < size; i++) {
            Integer person = table2.get(i);
            int conflict = calculateConflict(person, table2, friendMap, person);
            if (conflict > 0) {
                int otherConflict = calculateConflict(person, table1, friendMap, -1);
                if (otherConflict == 0) {
                    table1.add(table2.remove(i));
                    i--;
                    size--;
                } else {
                    for (int j = 0; j < table1.size(); j++) {
                        otherConflict = calculateConflict(person, table1, friendMap, table1.get(j));
                        if (otherConflict == 0) {
                            Integer person1 = table1.remove(j);
                            table1.add(person);
                            table2.remove(i);
                            table2.add(person1);
                            i--;
                            size--;
                            break;
                        }
                    }
                }
            }
        }

        boolean isOK = true;
        for (int i = 0; i < table2.size(); i++) {
            Integer person = table2.get(i);
            for (int j = i + 1; j < table2.size(); j++) {
                if (friendMap[person][table2.get(j)] == 0) {
                    isOK = false;
                    break;
                }
            }
            if (!isOK)
                break;
        }

        return isOK;
    }

    private int calculateConflict(int person, List<Integer> table, int[][] friendMap, int ignore) {
        int conflict = 0;

        for (int i = 0; i < table.size(); i++)
            if (table.get(i) != ignore)
                conflict += 1 - friendMap[person][table.get(i)];

        return conflict;
    }
}
