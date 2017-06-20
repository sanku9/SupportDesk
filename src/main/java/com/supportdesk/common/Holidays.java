package com.supportdesk.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public enum Holidays {

	/**
	 * See <a href="http://www.wikiwand.com/en/Public_holidays_in_France">http://www.wikiwand.com/en/Public_holidays_in_France</a>.
	 */
	FRANCE {

		@Override
		protected void addFixedHolidays(Set<Holiday> holidays) {
			holidays.add(new Holiday(Calendar.JANUARY, 1));
			holidays.add(new Holiday(Calendar.MAY, 1));
			holidays.add(new Holiday(Calendar.MAY, 8));
			holidays.add(new Holiday(Calendar.JULY, 14));
			holidays.add(new Holiday(Calendar.AUGUST, 15));
			holidays.add(new Holiday(Calendar.NOVEMBER, 1));
			holidays.add(new Holiday(Calendar.NOVEMBER, 11));
			holidays.add(new Holiday(Calendar.DECEMBER, 25));
		}

		@Override
		protected void addVariableHolidays(int year, Set<Holiday> holidays) {
			Date easterSunday = getEasterSunday(year);
			holidays.add(new Holiday(getEasterMonday(easterSunday)));
			holidays.add(new Holiday(getAscensionThursday(easterSunday)));
			holidays.add(new Holiday(getPentecostMonday(easterSunday)));
		}

	},

	/**
	 * See <a href="http://www.wikiwand.com/en/Public_holidays_in_the_United_Kingdom">http://www.wikiwand.com/en/Public_holidays_in_the_United_Kingdom</a>.
	 */
	ENGLAND {

		@Override
		protected void addFixedHolidays(Set<Holiday> holidays) {
			holidays.add(new Holiday(Calendar.JANUARY, 1));
			holidays.add(new Holiday(Calendar.DECEMBER, 25));
			holidays.add(new Holiday(Calendar.DECEMBER, 26));
		}

		@Override
		protected void addVariableHolidays(int year, Set<Holiday> holidays) {
			Date easterSunday = getEasterSunday(year);
			holidays.add(new Holiday(getGoodFriday(easterSunday)));
			holidays.add(new Holiday(getEasterMonday(easterSunday)));
			holidays.add(new Holiday(get(WeekdayIndex.FIRST, Calendar.MONDAY, Calendar.MAY, year)));
			holidays.add(new Holiday(get(WeekdayIndex.LAST, Calendar.MONDAY, Calendar.MAY, year)));
			holidays.add(new Holiday(get(WeekdayIndex.LAST, Calendar.MONDAY, Calendar.AUGUST, year)));
			Holiday christmasDay = new Holiday(Calendar.DECEMBER, 25);
			if (christmasDay.isWeekend(year)) {
				holidays.add(new Holiday(Calendar.DECEMBER, 27));
			}
			Holiday boxingDay = new Holiday(Calendar.DECEMBER, 26);
			if (boxingDay.isWeekend(year)) {
				holidays.add(new Holiday(Calendar.DECEMBER, 28));
			}
		}

	};

	public class HolidayException extends Exception {

		private static final long serialVersionUID = 1L;

		private HolidayException(String message) {
			super(message);
		}

	}

	/**
	 * A holiday is defined by a {@link Calendar#MONTH} and a {@link Calendar#DAY_OF_MONTH}.
	 */
	private class Holiday {

		private final int day;
		private final int month;

		public Holiday(Date date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			month = calendar.get(Calendar.MONTH);
			day = calendar.get(Calendar.DAY_OF_MONTH);
		}

		public Holiday(int month, int day) {
			this.month = month;
			this.day = day;
		}

		public Date toDate(int year) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, day);
			return calendar.getTime();
		}

		public boolean isWeekend(int year) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(toDate(year));
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			} else if (!(obj instanceof Holiday)) {
				return false;
			} else {
				Holiday holiday = (Holiday) obj;
				return holiday.month == month && holiday.day == day;
			}
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(new int[] { month, day });
		}

	}

	/**
	 * Use with {@link Holidays#get(WeekdayIndex, int, int, int)}.<br />
	 * <br />
	 * Example: <code>Holidays.get(WeekdayIndex.FIRST, Calendar.MONDAY, Calendar.MAY, 2000)</code>.
	 */
	public enum WeekdayIndex {

		FIRST(1), SECOND(2), THIRD(3), FOURTH(4), LAST(null);

		private final Integer index;

		private WeekdayIndex(Integer index) {
			this.index = index;
		}

		private boolean is(int count) {
			return index != null && index == count;
		}

	}

	private final Set<Holiday> fixedHolidays = new HashSet<Holiday>();

	private final Map<Integer, Set<Holiday>> variableHolidays = new HashMap<Integer, Set<Holiday>>();

	private Holidays() {
		addFixedHolidays(fixedHolidays);
	}

	protected abstract void addFixedHolidays(Set<Holiday> holidays);

	protected abstract void addVariableHolidays(int year, Set<Holiday> holidays);

	/**
	 * Returns the number of business days between two dates.
	 * 
	 * @param d1
	 *          The first date.
	 * @param d2
	 *          The second date.
	 * @return The number of business days between the two provided dates.
	 * @throws HolidayException
	 *           If <code>d1</code> or <code>d2</code> is not a business day.
	 */
	public int getBusinessDayCount(Date d1, Date d2) throws HolidayException {
		Calendar calendar = Calendar.getInstance();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			d1 = formatter.parse(formatter.format(d1));
			d2 = formatter.parse(formatter.format(d2));
		} catch (ParseException ignore) {
			// cannot happen
		}
		if (!isBusinessDay(d1) /*|| !isBusinessDay(d2)*/) {
			throw new HolidayException("Input date 1 must be business days");
		}
		int businessDayCount = 0;
		Date min = d1.before(d2) ? d1 : d2;
		Date max = min.equals(d2) ? d1 : d2;
		calendar.setTime(min);
		while (calendar.getTime().before(max)) {
			if (isBusinessDay(calendar.getTime())) {
				businessDayCount++;
			}
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		if (isBusinessDay(calendar.getTime())) {
			businessDayCount++;
		}
		return businessDayCount;
	}

	/**
	 * Returns whether a date is a business day.
	 * 
	 * @param date
	 *          The date.
	 * @return <code>true</code> if the <code>date</code> is a business day, <code>false</code> otherwise.
	 */
	public boolean isBusinessDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
			return false;
		} else if (isFixedHoliday(date)) {
			return false;
		} else if (isVariableHoliday(date)) {
			return false;
		}
		return true;
	}

	private boolean isFixedHoliday(Date date) {
		return fixedHolidays.contains(new Holiday(date));
	}

	private boolean isVariableHoliday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		Set<Holiday> yearHolidays;
		if (!variableHolidays.containsKey(year)) {
			// variable holidays have not been calculated for this year yet
			yearHolidays = new HashSet<Holiday>();
			addVariableHolidays(year, yearHolidays);
			variableHolidays.put(year, yearHolidays);
		} else {
			yearHolidays = variableHolidays.get(year);
		}
		return yearHolidays.contains(new Holiday(date));
	}

	public static Date getEasterSunday(int year) {
		// credits: https://www.wikiwand.com/en/Computus#/Anonymous_Gregorian_algorithm
		Calendar calendar = Calendar.getInstance();
		int initialYear = year;
		if (year < 1900) {
			year += 1900;
		}
		int a = year % 19;
		int b = year / 100;
		int c = year % 100;
		int d = b / 4;
		int e = b % 4;
		int f = (b + 8) / 25;
		int g = (b - f + 1) / 3;
		int h = (19 * a + b - d - g + 15) % 30;
		int i = c / 4;
		int j = c % 4;
		int k = (32 + 2 * e + 2 * i - h - j) % 7;
		int l = (a + 11 * h + 22 * k) / 451;
		int m = (h + k - 7 * l + 114) % 31;
		int month = (h + k - 7 * l + 114) / 31 - 1;
		int day = m + 1;
		calendar.set(initialYear, month, day);
		return calendar.getTime();
	}

	public static Date getGoodFriday(Date easterSunday) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(easterSunday);
		calendar.add(Calendar.DAY_OF_MONTH, -2);
		return calendar.getTime();
	}

	public static Date getEasterMonday(Date easterSunday) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(easterSunday);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	public static Date getAscensionThursday(Date easterSunday) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(easterSunday);
		calendar.add(Calendar.DAY_OF_MONTH, 39);
		return calendar.getTime();
	}

	public static Date getPentecostMonday(Date easterSunday) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(easterSunday);
		calendar.add(Calendar.DAY_OF_MONTH, 50);
		return calendar.getTime();
	}

	public static Date get(WeekdayIndex weekdayIndex, int dayOfWeek, int month, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, 1);
		int count = 0;
		Date last = null;
		do {
			if (calendar.get(Calendar.DAY_OF_WEEK) == dayOfWeek) {
				count++;
				last = calendar.getTime();
				if (weekdayIndex.is(count)) {
					return last;
				}
			}
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		} while (calendar.get(Calendar.MONTH) == month);
		if (weekdayIndex.equals(WeekdayIndex.LAST)) {
			return last;
		}
		return null;
	}

	public Date getNextBusinessDate(Date date) throws HolidayException {
		Calendar calendar = Calendar.getInstance();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = formatter.parse(formatter.format(date));
		} catch (ParseException ignore) {
			// cannot happen
		}
		calendar.setTime(date);
		while (!isBusinessDay(calendar.getTime())) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		return calendar.getTime();

	}

	public Date calculateResponseDateTime(Date issueReportedAt, long responseHours, int fromHour, int fromMinute, int toHour, int toMinute) {
		Date end = calculateResponseTime(issueReportedAt, responseHours, fromHour, fromMinute, toHour, toMinute);
		try {
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(issueReportedAt);
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(end);
			System.out.println(calendar1.getTime() + " " +calendar2.getTime());
			int businessDays = Holidays.FRANCE.getBusinessDayCount(issueReportedAt, end);

			long diff = end.getTime() - issueReportedAt.getTime();
			long total = (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 2);

			calendar2.add(Calendar.DATE, (int)total - businessDays);
			calendar2.setTime(Holidays.FRANCE.getNextBusinessDate(calendar2.getTime()));
		} catch (HolidayException e) {
			e.printStackTrace();
		}
		return end;
	}

	public Date calculateResponseTime(Date issueReportedAt, long responseHours, int fromHour, int fromMinute, int toHour, int toMinute) {

		Date end = null;
		int minRemained = 0;
		Calendar responseTime = Calendar.getInstance();
		responseTime.setTime(issueReportedAt);

		int hourOfDay = responseTime.get(Calendar.HOUR_OF_DAY);
		int dayOfWeek = responseTime.get(Calendar.DAY_OF_WEEK);

		if (hourOfDay < fromHour) {
			responseTime.set(Calendar.HOUR, fromHour);
		}

		if (hourOfDay >= toHour || dayOfWeek == 1) {
			responseTime.add(Calendar.DATE, 1);
			try {
				Date nextBusinessDate = Holidays.FRANCE.getNextBusinessDate(responseTime.getTime());
				responseTime.setTime(nextBusinessDate);
			} catch (HolidayException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			responseTime.set(Calendar.HOUR_OF_DAY, fromHour);
			responseTime.set(Calendar.MINUTE, fromMinute);

		} else if (dayOfWeek == 7) {
			responseTime.add(Calendar.DATE, 2);
			try {
				Date nextBusinessDate = Holidays.FRANCE.getNextBusinessDate(responseTime.getTime());
				responseTime.setTime(nextBusinessDate);
			} catch (HolidayException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			responseTime.set(Calendar.HOUR_OF_DAY, fromHour);
			responseTime.set(Calendar.MINUTE, fromMinute);

		}

		int hour = responseTime.get(Calendar.HOUR_OF_DAY);
		int minute = responseTime.get(Calendar.MINUTE);

		long dateMilliseconds = ((hour * 60) + minute) * 60 * 1000;
		long dayPartEndMilleseconds = ((toHour * 60) + toMinute) * 60 * 1000;
		long millisecondsInThisDayPart = dayPartEndMilleseconds
				- dateMilliseconds;

		long durationMilliseconds = responseHours * 60 * 60 * 1000;

		if (durationMilliseconds < millisecondsInThisDayPart) {
			end = new Date(responseTime.getTimeInMillis()
					+ durationMilliseconds);
		} else {
			long remainder = (durationMilliseconds - millisecondsInThisDayPart) / 60 / 60 / 1000;
			long hoursRemain = ((durationMilliseconds - millisecondsInThisDayPart) / 60 / 60) % 1000;
			minRemained = (int) ((hoursRemain *60)/1000);
			Date dayPartEndDate = new Date(responseTime.getTimeInMillis()
					+ millisecondsInThisDayPart);

			responseTime.setTime(dayPartEndDate);

			end = calculateResponseTime(responseTime.getTime(), remainder, fromHour, fromMinute, toHour, toMinute);
		}
		responseTime.setTime(end);
		responseTime.set(Calendar.MINUTE, minRemained);
		return responseTime.getTime();

	}

}
