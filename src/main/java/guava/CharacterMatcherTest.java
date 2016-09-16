package guava;

import com.google.common.base.CharMatcher;

/**
 * Created by viveksrivastava on 10/05/16.
 */
public class CharacterMatcherTest {
	public static void main(String[] args) {
		CharMatcher charMatcher = CharMatcher.anyOf("myNameIsVivek");
		System.out.println(charMatcher.countIn("qqq"));
	}
}
