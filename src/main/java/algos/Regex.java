package algos;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by vivek on 26/09/16.
 */
public class Regex {

    public static void main(String[] args) {
        Regex regex = new Regex();
        boolean matches = regex.matches("vig", "v.*g");
        System.out.println(matches);
    }

    ArrayList<String> paths = new ArrayList<>();

    public boolean matches(String str, String regex) {

        if (str == null || regex == null || str.equals("") || regex.equals(""))
            return false;

        /*

        //chars and . , *

        str="vivievivek"


        regex="v.*", "*iv*", "vi.*k", ".*vivek.*", "vi.*e"

        . means anything
        * means prev char any number of times

         */

        char[] charArray = str.toCharArray();
        char[] rcharArray = regex.toCharArray();

        return find(charArray, rcharArray, 0, 0);
    }

    private boolean find(char[] charArray, char[] rcharArray, int i, int j) {
        if (i == charArray.length && j == rcharArray.length)
            return true;
        else if (i == charArray.length || j == rcharArray.length)
            return false;

        char c = charArray[i];
        char r = rcharArray[j];
        char rL = '.';
        if (j < charArray.length - 1)
            rL = rcharArray[j + 1];

        if (c == r)
            return find(charArray, rcharArray, i + 1, j + 1);
        else {
            if (r == '.') {
                return find(charArray, rcharArray, i + 1, j + 1);
            } else if (r == '*') {
                return (find(charArray, rcharArray, i + 1, j) ||
                        find(charArray, rcharArray, i + 1, j + 1));
            } else
                return false;
        }
    }
}
