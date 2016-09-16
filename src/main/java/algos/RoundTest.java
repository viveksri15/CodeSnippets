package algos;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Created by viveksrivastava on 30/08/15.
 */
public class RoundTest {
	public static void main(String[] args) {
		BigDecimal settlementAmount = null;

		settlementAmount = new BigDecimal(9);
		test(settlementAmount);
		settlementAmount = new BigDecimal(10);
		test(settlementAmount);
		settlementAmount = new BigDecimal(10);
		test(settlementAmount);
		settlementAmount = new BigDecimal(50);
		test(settlementAmount);
		settlementAmount = new BigDecimal(50);
		test(settlementAmount);
		settlementAmount = new BigDecimal(51);
		test(settlementAmount);
		settlementAmount = new BigDecimal(58);
		test(settlementAmount);
		settlementAmount = new BigDecimal(66);
		test(settlementAmount);
		settlementAmount = new BigDecimal(86);
		test(settlementAmount);
		settlementAmount = new BigDecimal(89);
		test(settlementAmount);
		settlementAmount = new BigDecimal(92);
		test(settlementAmount);
		settlementAmount = new BigDecimal(97);
		test(settlementAmount);
		settlementAmount = new BigDecimal(99);
		test(settlementAmount);
		settlementAmount = new BigDecimal(100);
		test(settlementAmount);
		settlementAmount = new BigDecimal(100);
		test(settlementAmount);
		settlementAmount = new BigDecimal(109);
		test(settlementAmount);
		settlementAmount = new BigDecimal(114);
		test(settlementAmount);
		settlementAmount = new BigDecimal(148);
		test(settlementAmount);
		settlementAmount = new BigDecimal(150);
		test(settlementAmount);
		settlementAmount = new BigDecimal(167);
		test(settlementAmount);
		settlementAmount = new BigDecimal(174);
		test(settlementAmount);
		settlementAmount = new BigDecimal(197);
		test(settlementAmount);
		settlementAmount = new BigDecimal(232);
		test(settlementAmount);
		settlementAmount = new BigDecimal(243);
		test(settlementAmount);
		settlementAmount = new BigDecimal(249);
		test(settlementAmount);
		settlementAmount = new BigDecimal(252);
		test(settlementAmount);
		settlementAmount = new BigDecimal(252);
		test(settlementAmount);
		settlementAmount = new BigDecimal(280);
		test(settlementAmount);
		settlementAmount = new BigDecimal(297);
		test(settlementAmount);
		settlementAmount = new BigDecimal(297);
		test(settlementAmount);
		settlementAmount = new BigDecimal(308);
		test(settlementAmount);
		settlementAmount = new BigDecimal(306);
		test(settlementAmount);
		settlementAmount = new BigDecimal(339);
		test(settlementAmount);
		settlementAmount = new BigDecimal(343);
		test(settlementAmount);
		settlementAmount = new BigDecimal(355);
		test(settlementAmount);
		settlementAmount = new BigDecimal(367);
		test(settlementAmount);
		settlementAmount = new BigDecimal(372);
		test(settlementAmount);
		settlementAmount = new BigDecimal(374);
		test(settlementAmount);
		settlementAmount = new BigDecimal(499);
		test(settlementAmount);
		settlementAmount = new BigDecimal(520);
		test(settlementAmount);
		settlementAmount = new BigDecimal(591);
		test(settlementAmount);
		settlementAmount = new BigDecimal(717);
		test(settlementAmount);
		settlementAmount = new BigDecimal(946);
		test(settlementAmount);
		settlementAmount = new BigDecimal(1022);
		test(settlementAmount);
		settlementAmount = new BigDecimal(1595);
		test(settlementAmount);
		settlementAmount = new BigDecimal(3235);
		test(settlementAmount);
		settlementAmount = new BigDecimal(3247);
		test(settlementAmount);
		settlementAmount = new BigDecimal(3500);
		test(settlementAmount);
	}

	private static void test(BigDecimal settlementAmount) {
		BigDecimal bigDecimal1 = settleAmount(settlementAmount, 1.8d);
		BigDecimal bigDecimal2 = settleAmount(settlementAmount, 0.75d);
		BigDecimal bigDecimal3 = settleAmount(settlementAmount, 1d);
		System.out.println(settlementAmount + "\t" + bigDecimal1 + "\t" + bigDecimal2 + "\t" + bigDecimal3);
	}

	private static BigDecimal settleAmount(BigDecimal settlementAmount, double v) {
		BigDecimal multiply = settlementAmount.subtract(settlementAmount.multiply(new BigDecimal(1 - (v / 100)))).round(MathContext.DECIMAL32);
//		System.out.println("bigDecimal = " + multiply + "\t" + multiply.setScale(2, BigDecimal.ROUND_HALF_UP));
		multiply = multiply.setScale(2, BigDecimal.ROUND_HALF_UP);
//		System.out.println("bigDecimal = " + multiply);
		BigDecimal bigDecimal = multiply.add(multiply.multiply(new BigDecimal(0.14)).round(MathContext.DECIMAL32).setScale(2, BigDecimal.ROUND_HALF_UP));
//		System.out.println("bigDecimal = " + bigDecimal);
//		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
//		System.out.println("bigDecimal = " + bigDecimal);
		return settlementAmount.subtract(bigDecimal).round(MathContext.DECIMAL32).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
}
