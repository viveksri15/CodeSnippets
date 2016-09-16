package algos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by viveksrivastava on 24/07/15.
 */
public class CreditCardUtils {
	public static final String mst = "(^(5[0678])\\d{11,18}$)" +
			"|(^(6[^0357])\\d{11,18}$)" +
			"|(^(601)[^1]\\d{9,16}$)" +
			"|(^(6011)\\d{9,11}$)" +
			"|(^(6011)\\d{13,16}$)" +
			"|(^(65)\\d{11,13}$)" +
			"|(^(65)\\d{15,18}$)" +
			"|(^(633)[^34](\\d{9,16}$))" +
			"|(^(6333)[0-4](\\d{8,10}$))" +
			"|(^(6333)[0-4](\\d{12}$))" +
			"|(^(6333)[0-4](\\d{15}$))" +
			"|(^(6333)[5-9](\\d{8,10}$))" +
			"|(^(6333)[5-9](\\d{12}$))" +
			"|(^(6333)[5-9](\\d{15}$))" +
			"|(^(6334)[0-4](\\d{8,10}$))" +
			"|(^(6334)[0-4](\\d{12}$))" +
			"|(^(6334)[0-4](\\d{15}$))" +
			"|(^(67)[^(59)](\\d{9,16}$))" +
			"|(^(6759)](\\d{9,11}$))" +
			"|(^(6759)](\\d{13}$))" +
			"|(^(6759)](\\d{16}$))" +
			"|(^(67)[^(67)](\\d{9,16}$))" +
			"|(^(6767)](\\d{9,11}$))" +
			"|(^(6767)](\\d{13}$))" +
			"|(^(6767)](\\d{16}$))";
	//	"^(5[0678])[0-9]*$";
	public static final String mstFirst4 = "(^(5[0678])\\d\\d+$)" +
			"|(^(6[^0357])\\d\\d+$)" +
			"|(^(601)[^1]\\d\\d+$)" +
			"|(^(6011)\\d{2}\\d+$)" +
			"|(^(6011)\\d{2}\\d+$)" +
			"|(^(65)\\d{2}\\d+$)" +
			"|(^(65)\\d{2}\\d+$)" +
			"|(^(633)[^34](\\d\\d+$))" +
			"|(^(6333)[0-4](\\d\\d+$))" +
			"|(^(6333)[0-4](\\d\\d+$))" +
			"|(^(6333)[0-4](\\d\\d+$))" +
			"|(^(6333)[5-9](\\d\\d+$))" +
			"|(^(6333)[5-9](\\d\\d+$))" +
			"|(^(6333)[5-9](\\d\\d+)$)" +
			"|(^(6334)[0-4](\\d\\d+$))" +
			"|(^(6334)[0-4](\\d\\d+$))" +
			"|(^(6334)[0-4](\\d\\d+$))" +
			"|(^(67)[^(59)](\\d\\d+$))" +
			"|(^(6759)](\\d{2}\\d+$))" +
			"|(^(6759)](\\d{2}\\d+$))" +
			"|(^(6759)](\\d{2}\\d+$))" +
			"|(^(67)[^(67)](\\d\\d+$))" +
			"|(^(6767)](\\d{2}\\d+$))" +
			"|(^(6767)](\\d{2}\\d+$))" +
			"|(^(6767)](\\d{2}\\d+$))";
	// See: http://www.regular-expressions.info/creditcard.html
	public static final String REGX_VISA = "^4[0-9]{15}?";// VISA 16
	public static final String REGX_MC = "^5[1-5][0-9]{14}$"; // MC 16
	public static final String REGX_MAESTRO = mst;
	//			"^(?:5[0678]\\d\\d|6304|6390|67\\d\\d)\\d{15}$"; // MC 16
//	"(^(5[0678])\\d{11,18})$"
	public static final String REGX_AMEX = "^3[47][0-9]{13}$";// AMEX 15
	public static final String REGX_DISCOVER = "^6(?:011|5[0-9]{2})[0-9]{12}$";// Discover
	// 16
	public static final String REGX_DINERS_CLUB = "^3(?:0[0-5]|[68][0-9])[0-9]{11}$";// DinersClub
	public static final int CC_LEN_FOR_TYPE = 4; // number of characters to
	// 14
	// //
	// 38812345678901
	public static final String REGX_AMEX_REG_TYPE = "^3[47][0-9]{2}[0-9]*$";// AMEX 15
	// determine length
	public static final String REGX_DINERS_CLUB_TYPE = "^3(?:0[0-5]|[68][0-9])[0-9]*$";// DinersClub
	public static final String REGX_VISA_TYPE = "^4[0-9]{3}?[0-9]*";// VISA 16
	public static final String REGX_MC_TYPE = "^5[1-5][0-9]{2}[0-9]*$";// MC 16
	public static final String REGX_MAESTRO_TYPE = mstFirst4;
	//			"^(5[0678])[0-9]*$";
//			"^(?:5[0678]\\d\\d|6304|6390|67\\d\\d)\\d{8,15}$";// MC 16
	public static final String REGX_DISCOVER_TYPE = "^6(?:011|5[0-9]{2})[0-9]*$";// Discover

	public static void main(String[] args) {
		String cards = "374432\tAMERICAN EXPRESS\tCREDIT\n" +
				"402741\tCORPORATION BANK\tDEBIT\n" +
				"414767\tKOTAK MAHINDRA\tCREDIT\n" +
				"418157\tICICI BANK\tDEBIT\n" +
				"419607\tSTANDARD CHARTERED\tCREDIT\n" +
				"421424\tHDFC BANK\tDEBIT\n" +
				"421627\tICICI BANK\tDEBIT\n" +
				"428096\tKOTAK MAHINDRA\tDEBIT\n" +
				"429393\tKOTAK MAHINDRA\tDEBIT\n" +
				"436303\tHDFC BANK\tDEBIT\n" +
				"438624\tHDFC BANK\tDEBIT\n" +
				"438628\tCITI BANK\tCREDIT\n" +
				"447692\tHSBC BANK\tCREDIT\n" +
				"454198\tSTANDARD CHARTERED\tCREDIT\n" +
				"457274\tICICI BANK\tDEBIT\n" +
				"459200\tSBI\tDEBIT\n" +
				"461786\tHDFC BANK\tCREDIT\n" +
				"462271\tSTANDARD CHARTERED\tCREDIT\n" +
				"469375\tICICI BANK\tDEBIT\n" +
				"472642\tSBI\tCREDIT\n" +
				"489377\tHDFC BANK\tCREDIT\n" +
				"490016\tICICI BANK\tDEBIT\n" +
				"504433\tINDIAN BANK\tDEBIT\n" +
				"508159\tCITI BANK\tDEBIT\n" +
				"517252\tSBI\tCREDIT\n" +
				"524133\tCITI BANK\tCREDIT\n" +
				"524181\tHDFC BANK\tCREDIT\n" +
				"524193\tICICI BANK\tCREDIT\n" +
				"524216\tHDFC BANK\tCREDIT\n" +
				"524508\tAXIS BANK\tCREDIT\n" +
				"532666\tICICI BANK\tDEBIT\n" +
				"532702\tAXIS BANK\tDEBIT\n" +
				"534680\tAXIS BANK\tDEBIT\n" +
				"538655\tCITI BANK\tDEBIT\n" +
				"539998\tICICI BANK\tDEBIT\n" +
				"549777\tCITI BANK\tDEBIT\n" +
				"553682\tCITI BANK\tDEBIT\n" +
				"554637\tCITI BANK\tCREDIT\n" +
				"557684\tCORPORATION BANK\tDEBIT\n" +
				"622018\tSBI\tDEBIT\n" +
				"676111\tCITI BANK\tDEBIT";

		for (String card : cards.split("\n")) {
			String[] s = card.split("\t");
			String cardNum = s[0];
			System.out.println("'" + s[0] + "'\t'" + findCardType(cardNum) + "'\t'" + s[1] + "'\t'" + s[2] + "'\t'IN'");
		}
	}
	// 16

	public static String cleanNumber(String number) {
		return number.replaceAll("\\s", "");
	}

	public static CardType findCardType(String number) {

		if (number.length() < CC_LEN_FOR_TYPE) {
			return CardType.INVALID;

		}

		String reg = null;

		for (CardType type : CardType.values()) {
			switch (type) {
				case AMEX:
					reg = REGX_AMEX_REG_TYPE;
					break;
				case DISCOVER:
					reg = REGX_DISCOVER_TYPE;
					break;
				case MASTERCARD:
					reg = REGX_MC_TYPE;
					break;
				case MAESTRO:
					reg = REGX_MAESTRO_TYPE;
					break;
				case VISA:
					reg = REGX_VISA_TYPE;
					break;
				default:
					break;
			}

			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(number.substring(0,
					CC_LEN_FOR_TYPE));

			if (matcher.matches()) {
				return type;
			}
		}

		return CardType.INVALID;
	}


	public enum CardType {
		VISA, MASTERCARD, AMEX, DISCOVER, INVALID, MAESTRO;
	}

}
