package algos.kaprekarExec;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

/*
https://www.hackerrank.com/challenges/kaprekar-numbers
Framework of the problem:
1. Input and validations
2. Output, formattor and checked exception handling if any
3. Core algorithm
4. DataStructure
5. Assumptions if any
6. Limitations if any
7. Small test FW if possible
*/

/*
    Assumption:
        0<p<q<100000 (10^5)
        so, q^2 = 10^10 ~= 2^31. Int operations possible

    Limitation:
        In memory operations

*/

/*
    Core algo:

    (10^a + b)^2

    = 10^2a + b^2 + 2*10^2*b

    d digits

    d^2 gives 2d or 2d-1 digits

    incase 2d: d + d

    incase 2d-1: [d + (d - 1)] OR [(d-1) + d]

    2d % (10^d) = last d digits
    2d/10^d = last d digits

    (2d - 1) % (10^d) = last (d-1) digits

    .. No special DS required.

*/

//Specification
//core algo
interface IKaprekar {
    boolean isKaprekar(int number);
}

interface IO {
    void findKaprekar(int p, int q, OutputStream out) throws IOException;
}

//Implementation
//core algo
class KaprekarImpl implements IKaprekar {
    public boolean isKaprekar(int number) {
        if (number < 1 || number > 100000)
            return false;
        int length = (number + "").length();
        long number2 = (long) number * (long) number;
//        System.out.println("number2 = " + number2);
        int length2 = (number2 + "").length();
        int number_firstD = -1, number_lastD = -1;
        if (length2 == 2 * length) {
            //Assuming 2D length
            number_firstD = (int) (number2 / Math.pow(10, length));
            number_lastD = (int) (number2 % Math.pow(10, length));
            if (number_lastD > 0 && number_lastD + number_firstD == number)
                return true;
        } else {
            //first D+1
            number_firstD = (int) (number2 / Math.pow(10, length - 1));
            number_lastD = (int) (number2 % Math.pow(10, length - 1));
//            System.out.println("number_firstD = " + number_firstD);
//            System.out.println("number_lastD = " + number_lastD);
            if (number_lastD > 0 && number_lastD + number_firstD == number)
                return true;

            //first D+1
            number_firstD = (int) (number2 / Math.pow(10, length));
            number_lastD = (int) (number2 % Math.pow(10, length));
//            System.out.println("number_firstD = " + number_firstD);
//            System.out.println("number_lastD = " + number_lastD);
            if (number_lastD > 0 && number_lastD + number_firstD == number)
                return true;
        }

        return false;
    }
}

class KaprekarFactory {
    public static IKaprekar getInstance() {
        return new KaprekarImpl();
    }
}

class StdIO implements IO {
    public void findKaprekar(int p, int q, OutputStream out) throws IOException {
        if (p < 0 || q < 1 || p > 100000 || q > 100000)
            return;
        IKaprekar kaprekar = KaprekarFactory.getInstance();
        boolean found = false;
        for (int i = p; i <= q; i++) {
            if (kaprekar.isKaprekar(i)) {
                out.write((i + " ").getBytes());
                found = true;
            }
        }
        if (!found)
            out.write(("INVALID RANGE\n").getBytes());
    }
}

public class Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int p = scanner.nextInt();
        int q = scanner.nextInt();
        IO io = new StdIO();
        try {
            io.findKaprekar(p, q, System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}