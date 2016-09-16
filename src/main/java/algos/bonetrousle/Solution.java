package algos.bonetrousle;

import java.util.*;

public class Solution {

    private long k;
    private long n;
    private long b;
    Random randGen = new Random();

    public Solution(long k, long n, long b) {

        this.k = k;
        this.n = n;
        this.b = b;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        for (int i = 0; i < n; i++) {
            solve(in);
        }
    }

    private static void solve(Scanner in) {
        String line = in.nextLine();
        StringTokenizer stringTokenizer = new StringTokenizer(line, " ");
        long n = Long.parseLong(stringTokenizer.nextToken());
        long k = Long.parseLong(stringTokenizer.nextToken());
        long b = Long.parseLong(stringTokenizer.nextToken());

        Solution solution = new Solution(k, n, b);
        boolean found = solution.find(k, n, b);

        if (!found)
            System.out.println(-1);
        else
            solution.print();
    }

    private List<Long> sol = new ArrayList<>();
    private List<Long> time = new ArrayList<>();

    private Map<Long, Map<Long, Map<Long, Boolean>>> integerMapMap = new HashMap<>();
    private int count = 0;
    private boolean useOnce = true;

    private boolean find(long pos, long n, long b) {

        if (n < 0 || b < 0 || pos < 0)
            return false;

        count++;

        if (find(n, b)) {
            addToDP(pos, n, b, true);
            return true;
        }

        Boolean fromDP = getFromDP(pos, n, b);
        if (fromDP != null)
            return fromDP;

        long t0 = System.currentTimeMillis();
        int random = randGen.nextInt(10);
        boolean found = false;
        if (pos < 15000 || random > 5) {
            found = find(pos - 1, n - pos, b - 1);
            if (found) {
                sol.add(pos);
                time.add(System.currentTimeMillis() - t0);
                addToDP(pos, n, b, true);
                return true;
            }
        }
        found = find(pos - 1, n, b);
        if (found) {
            return true;
        }
        addToDP(pos, n, b, false);
        return false;
    }

    private boolean find(long n, long b) {
        return n == 0 && b == 0;
    }

    private Boolean getFromDP(long i, long n, long b) {
        Map<Long, Map<Long, Boolean>> integerMapMap = this.integerMapMap.get(i);
        if (integerMapMap == null)
            return null;
        Map<Long, Boolean> integerBooleanMap = integerMapMap.get(n);
        if (integerBooleanMap == null)
            return null;
        return integerBooleanMap.get(b);
    }

    private void addToDP(long i, long n, long b, boolean value) {
        Map<Long, Map<Long, Boolean>> integerMapMap = this.integerMapMap.get(i);
        if (integerMapMap == null) {
            integerMapMap = new HashMap<>();
            this.integerMapMap.put(i, integerMapMap);
        }
        Map<Long, Boolean> integerBooleanMap = integerMapMap.get(n);
        if (integerBooleanMap == null) {
            integerBooleanMap = new HashMap<>();
            integerMapMap.put(n, integerBooleanMap);
        }
        integerBooleanMap.put(b, value);
    }

    private void print() {
        long sum = 0, count = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i1 = 0; i1 < sol.size(); i1++) {
            Long i = sol.get(i1);
            sum += i;
            count++;
            stringBuilder.append(i).append(" ");
//            stringBuilder.append(i).append("(").append(time.get(i1)).append(")").append(" ");
        }
        System.out.println(stringBuilder.toString().trim());
//        System.out.println("count = " + count);
//        System.out.println("count = " + this.count);
//        System.out.println("sum = " + sum);
    }
}