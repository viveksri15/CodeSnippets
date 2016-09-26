package algos;

import java.util.Scanner;

/* Name of the class has to be "Main" only if the class is public. */
class Ideone {
    public static void main(String[] args) throws java.lang.Exception {
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        in.nextLine();
        while (num-- > 0) {
            String q = in.nextLine();
            System.out.println(q);
            char front = q.charAt(0);
            int h = 0;
            if (q.length() > 1) {
                for (int i = 1; i < q.length(); i++) {
                    if (q.charAt(i) != front) {
                        h++;
                    }
                }
                if (h == 1) {
                    System.out.println("Yes " + h);
                } else
                    System.out.println("No " + h);
            } else
                System.out.println("No " + h);
        }
    }
}