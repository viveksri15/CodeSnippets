package tests;

import java.util.regex.Matcher;

/**
 * Created by viveksrivastava on 02/06/16.
 */
public class Pattern {
	public static void main(String[] args) {
		String regex = "(?s)\\\\s*Bill\\\\s+of\\\\s+(?:Rs\\\\.?|INR)(?:\\\\s*)([0-9,]+(?:\\\\.[0-9]+)?|\\\\.[0-9]+)\\\\s+dated\\\\s+([-A-Za-z0-9]+)\\\\s+for\\\\s+your\\\\s+airtel\\\\s+mobile\\\\s+([0-9]+)\\\\s+is\\\\s+due\\\\s+on\\\\s+([-a-zA-Z0-9]+)\\\\.\\\\s+Please\\\\s+pay\\\\s+on\\\\s+time\\\\s+to\\\\s+avoid\\\\s+late\\\\s+fee.*\n";
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
		Matcher matcher = pattern.matcher("airtel update: Your bill dated 12-APR-16 of Rs 1154 for your airtel fixedline 08086039319_kk is due on 01-MAY-16. Please ignore if paid");
		boolean b = matcher.find();
		System.out.println("b = " + b);
	}
}
