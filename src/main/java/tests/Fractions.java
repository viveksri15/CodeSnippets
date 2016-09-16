package tests;

import java.math.BigDecimal;

/**
 * Created by viveksrivastava on 10/06/16.
 */
public class Fractions {
	public static void main(String[] args) {
		BigDecimal res = BigDecimal.valueOf(11).divide(BigDecimal.valueOf(9));
		System.out.println("res = " + res);
	}
}
