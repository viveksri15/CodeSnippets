package algos;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by viveksrivastava on 02/09/15.
 */
public class TimeZone {

	public static void main(String[] args) {
		convert("2015-09-01 19:57:47");
	}

	private static Calendar getISTCalendar() {
		return Calendar.getInstance(java.util.TimeZone.getTimeZone("Asia/Kolkata"));
	}

	private static Calendar getGMTCalendar() {
		return Calendar.getInstance(java.util.TimeZone.getTimeZone("UTC"));
	}

	public static void convert(String date) {

		SimpleDateFormat sdfmad = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdfmad.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Kolkata"));

		SimpleDateFormat sdfgmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdfgmt.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));

		Date parse = null;
		try {
			parse = sdfgmt.parse(date);
			Timestamp timestamp1 = new Timestamp(parse.getTime());
			System.out.println("timestamp1 = " + timestamp1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
