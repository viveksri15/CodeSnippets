package algos.sherlockAnagram;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by vivek on 10/08/16.
 */
public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < t; i++) {
            String s = sc.nextLine();
            int count = findAnagrams(s);
            System.out.println(count);
        }
    }

    private static int findAnagrams(String s) {
        char[] c = s.toCharArray();
        int[] cCount = new int[26];
        Arrays.fill(cCount, 0);
        for (char ch : c) {
            cCount[ch - 'a']++;
        }
        return findAnagrams(cCount, 0, "");
    }

    private static int findAnagrams(int[] cCount, int startIndex, String anagram) {
        System.out.println(anagram);
        int count = 0;
        for (int i = startIndex; i < cCount.length; i++) {
            if (cCount[i] >= 2) {
                //do not take
                System.out.println("notTaking = " + i);
                count += findAnagrams(cCount, i + 1, anagram);
                //take
                cCount[i] -= 2;
                System.out.println("taking = " + i);
                count += findAnagrams(cCount, i, anagram + (char) (i + 'a')) + 1;
                cCount[i] += 2;
            }
        }
        return count;
    }
}
