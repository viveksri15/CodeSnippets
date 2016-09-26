package algos.heSept2016.monica;

import java.io.*;
import java.util.*;

public class TestClass {
    public static void main(String[] args) {
        long t0 = System.currentTimeMillis();
        long maxTime = 2800;

        InputReader in = new InputReader(System.in);
        OutputWriter out = new OutputWriter(System.out);
//        int n = 1000;
//        Random random = new Random();
        int n = in.readInt();

//        final int table1Size = 600;
//        final int table2Size = 600;

        final int table1Size = in.readInt();
        final int table2Size = in.readInt();
        long table1Cost = 0, table2Cost = 0;
        int[][] people = new int[n][];
        int[] totalPeopleCost = new int[n];

        for (int i = 0; i < n; i++) {
            people[i] = new int[n];
            for (int j = 0; j < n; j++) {
                people[i][j] = in.readInt();
            }
        }

/*
        for (int i = 0; i < n; i++) {
            people[i] = new int[n];
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int rand = random.nextInt(1000);
                people[i][j] = rand;
                people[j][i] = rand;
            }
        }
*/

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                totalPeopleCost[i] += people[i][j];
            }
        }

//        for (int i = 0; i < n; i++) {
//            System.out.println(Arrays.toString(people[i]) + ", " + totalPeopleCost[i]);
//        }

        List<PersonOnTable> table1 = new ArrayList<>(table1Size);
        List<PersonOnTable> table2 = new ArrayList<>(table2Size);

        for (int i = 0; i < n; i++) {
            if (table1.size() < table1Size) {
                table1Cost = addPersonToTable(table1, i, people);
            } else {
                table2Cost = addPersonToTable(table2, i, people);
            }
        }


        long oldCost = table1Cost + table2Cost;

        while (true) {
            if (timeExceeded(maxTime, t0))
                break;

            Collections.sort(table1);
            Collections.sort(table2);

            long oldCostTemp = oldCost;

            if (table2.size() == table2Size) {
//                System.out.println("REPLACING_1=" + oldCost);
                //replace
                int i = 0, j = 0;
                while (i < table1.size() && j < table2.size()) {

                    if (timeExceeded(maxTime, t0))
                        break;

                    PersonOnTable p1 = table1.get(i);
                    PersonOnTable p2 = table2.get(j);
                    long newCost = knowReplacementCost(table1, p1, table2, p2, table1Cost, table2Cost, i, j, people, totalPeopleCost);
                    if (newCost < oldCost) {
                        long[] newCosts = replacePeople(table1, p1, table2, p2, table1Cost, table2Cost, i, j, people);
                        table1Cost = newCosts[0];
                        table2Cost = newCosts[1];
                        oldCost = table1Cost + table2Cost;
//                        System.out.println("NEW DATA = newCost=" + newCost + ", oldCost=" + oldCost + ", table1=" + table1 + ", t1cost=" + getCost(table1) + ", table1Cost=" + table1Cost + ", table2=" + table2 + ", t2cost=" + getCost(table2) + ", table2Cost=" + table2Cost);
                        i++;
                        j++;
                    } else {
                        j++;
                        if (j == table2.size()) {
                            i++;
                            j = 0;
                        }
                    }
                }
            } else if (table2.size() < table2Size) {
//                System.out.println("INSERTING_1=" + oldCost);
                if (timeExceeded(maxTime, t0))
                    break;

                //pick from 1 and put in 2
                int currentTable1Size = table1.size();
                int index = 0;
                for (int i = 0; i < currentTable1Size; i++) {
                    PersonOnTable p1 = table1.get(index);
                    long newTable2Cost = getCostWithNewPerson(p1, totalPeopleCost, table2Cost);
                    long newTable1Cost = getCostWithoutPerson(p1, totalPeopleCost, table1Cost);
                    long newCost = newTable2Cost + newTable1Cost;
                    if (newCost < oldCost) {
                        table2Cost = addPersonToTable(table2, p1.id, people);
                        table1Cost = removePersonFromTable(table1, p1.id, people);
//                        System.out.println("X_NEW DATA = newCost=" + newCost + ", oldCost=" + oldCost + ", table1=" + table1 + ", t1cost=" + getCost(table1) + ", table1Cost=" + table1Cost + ", table2=" + table2 + ", t2cost=" + getCost(table2) + ", table2Cost=" + table2Cost);
                        oldCost = table2Cost + table1Cost;
//                        System.out.println("X_newCost = " + newCost + ", oldCost=" + oldCost);
                        if (table2.size() == table2Size)
                            break;
                    } else
                        index++;
                }
            }

//            System.out.println("newCost1=" + oldCost + ", oldCost=" + oldCostTemp);

            Collections.sort(table1);
            Collections.sort(table2);

            if (table1.size() == table1Size) {
//                System.out.println("REPLACING_2=" + oldCost);
                //replace
                int i = 0, j = 0;
                while (i < table1.size() && j < table2.size()) {
                    if (timeExceeded(maxTime, t0))
                        break;

                    PersonOnTable p1 = table1.get(i);
                    PersonOnTable p2 = table2.get(j);
                    long newCost = knowReplacementCost(table1, p1, table2, p2, table1Cost, table2Cost, i, j, people, totalPeopleCost);
                    if (newCost < oldCost) {
                        long[] newCosts = replacePeople(table1, p1, table2, p2, table1Cost, table2Cost, i, j, people);
                        table1Cost = newCosts[0];
                        table2Cost = newCosts[1];
                        oldCost = table1Cost + table2Cost;
                        i++;
                        j++;
                    } else {
                        j++;
                        if (j == table2.size()) {
                            i++;
                            j = 0;
                        }
                    }
                }
            } else if (table1.size() < table1Size) {
                //pick from 2 and put in 1
//                System.out.println("INSERTING_2=" + oldCost);

                int currentTable2Size = table2.size();
                int index = 0;
                for (int i = 0; i < currentTable2Size; i++) {

                    if (timeExceeded(maxTime, t0))
                        break;

                    PersonOnTable p2 = table2.get(index);

                    long newTable1Cost = getCostWithNewPerson(p2, totalPeopleCost, table1Cost);
                    long newTable2Cost = getCostWithoutPerson(p2, totalPeopleCost, table2Cost);

                    long newCost = newTable2Cost + newTable1Cost;
//                    System.out.println("INSERTING_2=newCost=" + newCost + ",oldCost=" + oldCost + ", i=" + p2);
                    if (newCost < oldCost) {
                        table1Cost = addPersonToTable(table1, p2.id, people);
                        table2Cost = removePersonFromTable(table2, p2.id, people);
                        oldCost = table1Cost + table2Cost;
//                        System.out.println("Y_newCost = " + newCost + ", oldCost=" + oldCost);
                        if (table1.size() == table1Size)
                            break;
                    } else
                        index++;
                }
            }

//            System.out.println("newCost=" + oldCost + ", oldCost=" + oldCostTemp);
            if (oldCostTemp == oldCost) {
                break;
            }

        }

        List<Integer> table1P = new ArrayList<>();
        for (PersonOnTable personOnTable : table1)
            table1P.add(personOnTable.id);
        Collections.sort(table1P);

        out.print(table1P.size(), "\n");
        for (int i = 0, table1Size1 = table1P.size(); i < table1Size1; i++) {
            int p = table1P.get(i);
            if (i != table1Size1 - 1)
                out.print(p + 1, " ");
            else
                out.print(p + 1);
        }

//        System.out.println("TIME=" + (System.currentTimeMillis() - t0) + ", cost=" + (oldCost));
        out.flush();
        out.close();
    }

    private static long getCost(List<PersonOnTable> table2) {
        long cost = 0;
        for (PersonOnTable personOnTable : table2)
            cost += personOnTable.cost;
        return cost;
    }

    private static long knowReplacementCost(List<PersonOnTable> table1, PersonOnTable p1, List<PersonOnTable> table2,
                                            PersonOnTable p2, long table1Cost, long table2Cost, int i, int j,
                                            int[][] people, int[] totalPeopleCost) {

        long p1NewCost = totalPeopleCost[p1.id] - p1.cost - people[p1.id][p2.id];
        long p2NewCost = totalPeopleCost[p2.id] - p2.cost - people[p1.id][p2.id];

        table1Cost = table1Cost - 2 * p1.cost + 2 * p2NewCost;
        table2Cost = table2Cost - 2 * p2.cost + 2 * p1NewCost;

        return table1Cost + table2Cost;
    }

    private static boolean timeExceeded(long maxTime, long t0) {
//        return System.currentTimeMillis() - t0 > maxTime;
        return false;
    }

    private static long removePersonFromTable(List<PersonOnTable> table, int id, int[][] people) {
        long cost = 0;
        int index = -1;
        for (int i = 0, tableSize = table.size(); i < tableSize; i++) {
            PersonOnTable p = table.get(i);
            if (p.id == id) {
                index = i;
                continue;
            }
            p.cost -= people[p.id][id];
            cost += p.cost;
        }
        table.remove(index);
        return cost;
    }

    private static long getCostWithoutPerson(PersonOnTable p, int[] totalPeopleCost, long tableCost) {

//        long pNewCost = totalPeopleCost[p.id] - p.cost;

        return tableCost - 2 * p.cost;
    }

    private static long getCostWithNewPerson(PersonOnTable p, int[] totalPeopleCost, long tableCost) {

        long pNewCost = totalPeopleCost[p.id] - p.cost;

        return tableCost + 2 * pNewCost;
    }

    private static long[] replacePeople(List<PersonOnTable> table1, PersonOnTable p1, List<PersonOnTable> table2, PersonOnTable p2,
                                        long table1Cost, long table2Cost, int i, int j, int[][] people) {

        long p1Cost = p1.cost;
        long p2Cost = p2.cost;
        p1.cost = 0;
        p2.cost = 0;

        table1Cost = replacePerson(p2, table1, p1, table1Cost, i, people, p1Cost);
        table2Cost = replacePerson(p1, table2, p2, table2Cost, j, people, p2Cost);

        return new long[]{table1Cost, table2Cost};
    }

    private static long replacePerson(PersonOnTable pToAdd, List<PersonOnTable> table,
                                      PersonOnTable pToRemove, long tableCost,
                                      int index, int[][] people, long removalCost) {
        for (PersonOnTable p : table) {
            if (p.id == pToRemove.id)
                continue;

            p.cost += people[p.id][pToAdd.id];
            tableCost += people[p.id][pToAdd.id];

            p.cost -= people[p.id][pToRemove.id];
            tableCost -= people[p.id][pToRemove.id];

            pToAdd.cost += people[p.id][pToAdd.id];
        }

        tableCost += pToAdd.cost - removalCost;
        table.set(index, pToAdd);

        return tableCost;
    }

    private static long addPersonToTable(List<PersonOnTable> table, int person, int[][] people) {
        long tableCost = 0;
        long personsCost = 0;

        for (PersonOnTable personOnTable : table) {
            personOnTable.cost += people[person][personOnTable.id];
            personsCost += people[person][personOnTable.id];
            tableCost += personOnTable.cost;
        }

        PersonOnTable personOnTable = new PersonOnTable();
        personOnTable.id = person;
        personOnTable.cost = personsCost;
        tableCost += personsCost;
        table.add(personOnTable);

        return tableCost;
    }
}

class PersonOnTable implements Comparable<PersonOnTable> {
    int id;
    long cost;

    @Override
    public int compareTo(PersonOnTable o) {
        //so that the largest comes first
        return (int) ((o.cost - cost) % Integer.MAX_VALUE);
    }

    @Override
    public String toString() {
        return id + "," + cost;
    }
}

class InputReader {

    private InputStream stream;
    private byte[] buf = new byte[1024 * 8];
    private int curChar;
    private int numChars;
    private SpaceCharFilter filter;

    public InputReader(InputStream stream) {
        this.stream = stream;
    }

    public int read() {
        if (numChars == -1)
            throw new InputMismatchException();
        if (curChar >= numChars) {
            curChar = 0;
            try {
                numChars = stream.read(buf);
            } catch (IOException e) {
                throw new InputMismatchException();
            }
            if (numChars <= 0)
                return -1;
        }
        return buf[curChar++];
    }

    public int readInt() {
        int c = read();
        while (isSpaceChar(c))
            c = read();
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = read();
        }
        int res = 0;
        do {
            if (c < '0' || c > '9')
                throw new InputMismatchException();
            res *= 10;
            res += c - '0';
            c = read();
        } while (!isSpaceChar(c));
        return res * sgn;
    }

    public String readString() {
        int c = read();
        while (isSpaceChar(c))
            c = read();
        StringBuilder res = new StringBuilder();
        do {
            res.appendCodePoint(c);
            c = read();
        } while (!isSpaceChar(c));
        return res.toString();
    }

    public boolean isSpaceChar(int c) {
        if (filter != null)
            return filter.isSpaceChar(c);
        return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
    }

    public String next() {
        return readString();
    }

    public interface SpaceCharFilter {
        public boolean isSpaceChar(int ch);
    }
}

class OutputWriter {
    private final PrintWriter writer;

    public OutputWriter(OutputStream outputStream) {
        writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
    }

    public OutputWriter(Writer writer) {
        this.writer = new PrintWriter(writer);
    }

    public void print(Object... objects) {
        for (int i = 0; i < objects.length; i++) {
            if (i != 0)
                writer.print(' ');
            writer.print(objects[i]);
        }
    }

    public void printLine(Object... objects) {
        print(objects);
        writer.println();
    }

    public void close() {
        writer.close();
    }

    public void flush() {
        writer.flush();
    }

}

class IOUtils {

    public static int[] readIntArray(InputReader in, int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++)
            array[i] = in.readInt();
        return array;
    }

}