package algos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface IStringIntegerMapManager {
	String getMapping(int input) throws Exception;
}

/**
 * Created by viveksrivastava on 20/03/16.
 */
public class StringComboIntegerOps {

	private String[] result;
	private IStringIntegerMapManager stringIntegerMapManager;

	public StringComboIntegerOps() {
		stringIntegerMapManager = StringIntegerMapManagerFactory.getInstance();
	}

	public static void main(String[] args) throws Exception {
		StringComboIntegerOps stringComboIntegerOps = new StringComboIntegerOps();
		stringComboIntegerOps.doOps(123);
		String[] result = stringComboIntegerOps.getResult();
		for (String s : result)
			System.out.println("s = " + s);
	}

	public String[] getResult() {
		return result;
	}

	public void doOps(int input) throws Exception {
		String stringVal = input + "";
		result = comboSubString(stringVal, 0, stringVal.length() - 1);
	}

	private String[] comboSubString(String input, int start, int end) throws Exception {
		System.out.println("input = " + input);
		System.out.println("start = " + start);
		System.out.println("end = " + end);
		//If I am given an input of single input
		if (start == end)
			return new String[]{stringIntegerMapManager.getMapping(Integer.parseInt(input.charAt(start) + ""))};

		//If I am given 2 digit input

		if (start == end - 1) {
			String[] mappings = null;
			Integer input1 = Integer.valueOf(Character.toString(input.charAt(start)) + "" + Character.toString(input.charAt(end)));
			if (input1 <= 26) {
				mappings = new String[3];
				mappings[0] = stringIntegerMapManager.getMapping(Integer.valueOf(input.charAt(start) + ""));
				mappings[1] = stringIntegerMapManager.getMapping(Integer.valueOf(input.charAt(end) + ""));
				mappings[2] = stringIntegerMapManager.getMapping(input1);
			} else {
				mappings = new String[2];
				mappings[0] = stringIntegerMapManager.getMapping(Integer.valueOf(input.charAt(0) + ""));
				mappings[1] = stringIntegerMapManager.getMapping(Integer.valueOf(input.charAt(1) + ""));
			}

			System.out.println("algos.Arrays.tomappings = " + java.util.Arrays.toString(mappings));

			return mappings;
		}

		List<String> strings = new ArrayList<>();

		//start = 0
		//end = 2
		for (int i = start; i <= end; i++) {

			//123
			//i=0

			String leftInput = input.substring(start, i - start + 1);
			//leftInput = 123.algos.subString(0, 1) = 1

			System.out.println("i - start + 1 = " + (i - start + 1));
			System.out.println("end - start - i = " + (end - start - i));
			String rightInput = input.substring(i - start + 1, end - start + 1);
			//rightInput = 123.algos.subString(1, 2)= 23

			String[] leftCombo = comboSubString(leftInput, start, i + 1);
			String[] rightCombo = comboSubString(rightInput, i + 1, end);
			int count = 0;
			for (String s : leftCombo) {
				for (String s1 : rightCombo) {
					strings.add(s + s1);
				}
			}
		}

		String[] finalString = new String[strings.size()];
		int count = 0;
		for (String s : strings)
			finalString[count++] = s;

		return finalString;
	}
}

class StringIntegerMapManagerFactory {
	public static IStringIntegerMapManager getInstance() {
		return new ComboStringIntegerMapManager();
	}
}

class ComboStringIntegerMapManager implements IStringIntegerMapManager {
	private Map<Integer, String> intToString = new HashMap<>();

	public ComboStringIntegerMapManager() {
		//init
		for (int i = 0; i < 26; i++) {
			intToString.put(i, Character.toString((char) ('A' + i)));
		}
		intToString.put(0, "+");

		System.out.println("intToString = " + intToString);
	}

	public String getMapping(int i) throws Exception {
		if (i > 26 || i < 0)
			throw new Exception("BadMapping");
		return intToString.get(i);
	}
}