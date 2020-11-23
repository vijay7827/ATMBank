package com.atm.banking.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class DateUtils {
	
	public static final long MILLIS_IN_A_DAY = 86400000;

	public static long getCurrentTimeInUTC() {
		return OffsetDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli();
       
	}
	
	public static Long getMillisFromTimestamp(String date, String pattern) {
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault());
		LocalDate newDate = LocalDate.parse(date, inputFormatter);
		return newDate.toEpochDay() * MILLIS_IN_A_DAY;
	}

	public static String getTimestampfromMillis(Long millis, String pattern) {
		if(Objects.isNull(millis) || Objects.isNull(pattern) || millis == 0) {
			return "";
		}
		DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return formatter.format(calendar.getTime());
	}
	

}
