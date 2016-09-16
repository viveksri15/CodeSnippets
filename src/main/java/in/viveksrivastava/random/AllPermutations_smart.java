package in.viveksrivastava.random;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class AllPermutations_smart {
    public static void main(String[] args) throws IllegalArgumentException,
            FileNotFoundException {
        List<char[]> characters = new ArrayList<char[]>();
        for (int i = 0; i < 5; i++) {
            int k = new Random().nextInt(4);
            if (k == 0)
                k = 1;
            char[] chars = new char[k];
            StringBuffer buffer = new StringBuffer();
            for (int j = 0; j < k; j++) {
                chars[j] = (char) (new Random().nextInt(10) + '0');
                buffer.append(chars[j]).append(",");
            }
            System.out.println(buffer.toString());
            characters.add(chars);
        }
        System.out.println("started");
        char[] chars = new char[5];
        permute(characters, 0, 0, chars);
    }

    private static void permute(List<char[]> result, int i, int j,
                                char[] chars) {

        // if the entire buffer is not full
        if (i < result.size() - 1) {

            if (j == 0) {

                char[] characters = result.get(i);

                chars[i] = characters[j];
                check(chars, i);
                i++;
                while (characters.length == 1 && i < result.size() - 1) {
                    characters = result.get(i);
                    chars[i] = characters[0];
                    check(chars, i);
                    i++;
                }
                i--;

                permute(result, i + 1, 0, chars);

            }

            char[] characters_1 = result.get(i);
            int k = j + 1;

            if (k < characters_1.length) {
                chars[i] = characters_1[k];
                check(chars, i);

                i++;
                while (characters_1.length == 1 && i < result.size() - 1) {
                    characters_1 = result.get(i);
                    chars[i] = characters_1[0];
                    check(chars, i);
                    i++;
                }
                i--;

                permute(result, i + 1, 0, chars);
            }

        } else
            // if the entire buffer IS full
            if (i == result.size() - 1) {
                char[] characters = result.get(i);

                for (int k = 0; k < characters.length; k++) {

                    chars[i] = characters[k];
                    check(chars, i);
                }
            }
    }

    private static void check(char[] chars, int i) {
        StringBuffer buffer = new StringBuffer();
        for (char c : chars) {
            if (c <= '9' && c >= '0')
                buffer.append(c);
        }
        String result1 = buffer.toString();

        if (buffer.length() == chars.length)
            System.out.println("result1 = " + result1);
    }
}
