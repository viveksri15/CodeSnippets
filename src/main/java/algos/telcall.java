package algos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by viveksrivastava on 05/12/15.
 */
public class telcall {
	public static void main(String[] args) {
		String regex = "You have initiated a purchase transaction on your ICICI Bank card xx(\\d{4}) that needs an OTP. DONT SHARE THE OTP WITH ANYONE. BANK NEVER CALLS TO VERIFY OTP. The OTP is (\\d{6}) and is valid for 15 min. The OTP is confidential and you are responsible for safeguarding it.|You have initiated a purchase transaction on your ICICI Bank Card (\\d{4}). OTP is (\\d{6}). DONT SHARE IT WITH ANYONE. BANK NEVER CALLS TO VERIFY OTP.";
		String sms = "You have initiated a purchase transaction on your ICICI Bank Card 4574. OTP is 321676. DONT SHARE IT WITH ANYONE. BANK NEVER CALLS TO VERIFY OTP.";
		Pattern compile = Pattern.compile(regex);
		Matcher matcher = compile.matcher(sms);
		System.out.println("matcher.group() = " + matcher.group(1));
//		while (matcher.find()) {
//			System.out.println("matcher.group() = " + matcher.group());
//		}
	}
}
