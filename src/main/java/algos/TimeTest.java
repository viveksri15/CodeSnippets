package algos;

import java.util.Calendar;

/**
 * Created by viveksrivastava on 07/09/15.
 */
public class TimeTest {
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 5);
		long l = calendar.getTime().getTime() / (1000 * 3600);
		System.out.println("l = " + l);
	}
}
