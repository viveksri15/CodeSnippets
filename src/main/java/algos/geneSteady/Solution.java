package algos.geneSteady;

import java.util.Arrays;

/**
 * Created by viveksrivastava on 09/05/16.
 */
public class Solution {
	public static void main(String[] args) {
		Gene gene = new Gene();
		int count = gene.makeSteady("AABBCDCDCCCBBCDA".toCharArray());
		System.out.println(count);
	}
}

class Gene {
	int makeSteady(char[] geneChars) {
		int[] extrasOrMissing = count(geneChars);
		int pointer1 = 0, pointer2 = geneChars.length - 1;

		while (true) {
			System.out.println("extrasOrMissing = " + Arrays.toString(extrasOrMissing));
			int pointer1Char = getIntFromChar(geneChars[pointer1]);
			int pointer2Char = getIntFromChar(geneChars[pointer2]);

			System.out.println(pointer1 + " = " + pointer1Char);
			System.out.println(pointer2 + " = " + pointer2Char);

			if (extrasOrMissing[pointer1Char] <= 0) {
				pointer1++;
				if (extrasOrMissing[pointer1Char] < 0)
					extrasOrMissing[pointer1Char]++;
				continue;
			}

			if (extrasOrMissing[pointer2Char] <= 0) {
				pointer2--;
				if (extrasOrMissing[pointer1Char] < 0)
					extrasOrMissing[pointer2Char]++;
				continue;
			}

			break;
		}

		return pointer2 - pointer1 + 1;
	}

	private int[] count(char[] geneChars) {
		int[] count = new int[4];
		count[0] = 0;
		count[1] = 0;
		count[2] = 0;
		count[3] = 0;
		for (int i = 0; i < geneChars.length; i++)
			count[getIntFromChar(geneChars[i])]++;

		for (int i = 0; i < 4; i++)
			count[i] = count[i] - geneChars.length / 4;

		return count;
	}

	private int getIntFromChar(char c) {
		if (c == 'A')
			return 0;
		if (c == 'C')
			return 1;
		if (c == 'G')
			return 2;
		return 3;
	}
}
