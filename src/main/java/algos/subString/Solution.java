package algos.subString;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Solution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int testCases = sc.nextInt();
		sc.nextLine();
		for (int i = 0; i < testCases; i++) {
			String line1 = sc.nextLine();
			String line2 = sc.nextLine();
			Set<Integer> setA = new HashSet<>();
			for (char c : line1.toCharArray())
				setA.add((int) c);
			boolean found = false;
			for (char c : line2.toCharArray()) {
				if (setA.contains((int) c)) {
					System.out.println("YES");
					found = true;
					break;
				}
			}
			if (!found) {
				System.out.println("NO");
			}
		}
	}
}