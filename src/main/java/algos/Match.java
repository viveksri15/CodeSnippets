package algos;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by viveksrivastava on 22/06/15.
 */
public class Match {
	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
		String text = "Sorry, the payment for recharge of number 1124230218 for amount Rs.500 failed.\nYour reference Id is 1231212.";
		String htmlMessage = getHTMLMessage(text);
		System.out.println("responseMap = " + htmlMessage);
	}

	private static String getHTMLMessage(String message) {


		message = message.replaceAll("\n", "<br/><br/>");
		Pattern pattern = Pattern.compile("([0-9]+(\\.[0-9]{2})?)");
		Matcher matcher = pattern.matcher(message);
		String message1 = matcher.replaceAll("<strong>$1</strong>");

		pattern = Pattern.compile("(CREDIT|WALLET|CARD|ACCOUNT|BANK)", Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(message1);

		message1 = matcher.replaceAll("<strong>$1</strong>");

		pattern = Pattern.compile("(\"[^\"]+\")", Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(message1);
		message1 = matcher.replaceAll("<strong>$1</strong>");

		message1 = message1.replace("Rs.", "&#8377;");
		return message1;
	}
}
