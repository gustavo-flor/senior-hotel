package ogustaflor.com.github.api.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtils {

	private static final String DEFAULT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DEFAULT_FORMAT);
	
	public static Timestamp toTimestamp(String date) throws ParseException {
		return new Timestamp(SIMPLE_DATE_FORMAT.parse(date).getTime());
	}
	
	public static String toString(Timestamp date) {
		return SIMPLE_DATE_FORMAT.format(date);
	}
	
}
