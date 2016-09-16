package algos;

/**
 * Created by viveksrivastava on 23/06/15.
 */
public class Split {
	public static void main(String[] args) {
		String decryptedExpiryDate = "2016/08";
		String[] split = decryptedExpiryDate.split("\\D");
		int month = 0, year = 1;
		if (split[0].length() == 4) {
			month = 1;
			year = 0;
		}
		System.out.println("month = " + month);
	}
}
