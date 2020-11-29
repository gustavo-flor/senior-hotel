package ogustaflor.com.github.api.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

	private static final String DEFAULT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DEFAULT_FORMAT);
	
	public static Timestamp toTimestamp(String date) throws ParseException {
		return new Timestamp(SIMPLE_DATE_FORMAT.parse(date).getTime());
	}
	
	public static String toString(Timestamp date) {
		return SIMPLE_DATE_FORMAT.format(date);
	}
	
	public static int getWorkingDaysBetweenTwoDates(Timestamp startDate, Timestamp endDate) {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		
		int workDays = 0;
		
		if (startCalendar.getTimeInMillis() == endCalendar.getTimeInMillis()) {
			return 0;
		}
		
		if (startCalendar.getTimeInMillis() > endCalendar.getTimeInMillis()) {
			startCalendar.setTime(endDate);
			endCalendar.setTime(startDate);
		}
		
		do {
			startCalendar.add(Calendar.DAY_OF_MONTH, 1);
			if (startCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
					&& startCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				++workDays;
			}
		} while (startCalendar.getTimeInMillis() < endCalendar.getTimeInMillis());
		
		return workDays;
	}
	
	public static int getHoursFromDate(Timestamp date) {
		return date.toLocalDateTime().getHour();
	}
	
	public static int getMinutesFromDate(Timestamp date) {
		return date.toLocalDateTime().getMinute();
	}
	
	public static int getNotWorkingDaysBetweenTwoDates(Timestamp startDate, Timestamp endDate) {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		
		int notWorkDays = 0;
		
		if (startCalendar.getTimeInMillis() == endCalendar.getTimeInMillis()) {
			return 0;
		}
		
		if (startCalendar.getTimeInMillis() > endCalendar.getTimeInMillis()) {
			startCalendar.setTime(endDate);
			endCalendar.setTime(startDate);
		}
		
		do {
			startCalendar.add(Calendar.DAY_OF_MONTH, 1);
			if (startCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
				|| startCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				++notWorkDays;
			}
		} while (startCalendar.getTimeInMillis() < endCalendar.getTimeInMillis());
		
		return notWorkDays;
	}
	
	public static boolean isOnWeekends(Timestamp date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
			|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
	}
	
}
