package algos;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by vivek on 25/09/16.
 */
public class WordBreaker {
    private Set<String> words = new HashSet<>();

    public static void main(String[] args) {
        WordBreaker wordBreaker = new WordBreaker();
        wordBreaker.words.add("a");
        wordBreaker.words.add("hi");
        wordBreaker.words.add("hello");
        wordBreaker.words.add("how");
        wordBreaker.words.add("are");
        wordBreaker.words.add("you");

        String s = "ahellohi";
        boolean isWord = wordBreaker.getCount(s, 0, s.length() - 1);
        System.out.println(isWord);
    }

    public boolean getCount(String s, int i, int j) {

        String substring = s.substring(i, j + 1);

        System.out.println(i + "," + j + "," + substring);
        if (words.contains(substring))
            return true;

        for (int k = i + 1; k < j; k++) {
            if (getCount(s, i, k - 1) && getCount(s, k, j))
                return true;
        }

        return false;
    }
}
