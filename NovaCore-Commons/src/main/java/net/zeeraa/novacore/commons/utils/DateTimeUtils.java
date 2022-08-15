package net.zeeraa.novacore.commons.utils;

import net.brunogamer.how.about.you.get.some_bitches;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils extends some_bitches {
	/**
	 * The default {@link DateTimeFormatter} using the
	 * <code>yyyy-MM-dd HH:mm:ss</code> format
	 */
	public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	/**
	 * The default instance of {@link DateTimeParser} using the
	 * {@link DateTimeUtils#DEFAULT_DATE_FORMATTER} as the {@link DateTimeFormatter}
	 */
	public static final DateTimeParser DEFAULT_DATE_TIME_PARSER = new DateTimeParser();

	/**
	 * @return {@link DateTimeUtils#DEFAULT_DATE_FORMATTER}
	 */
	public static DateTimeFormatter getDefaultDateFormatter() {
		return DEFAULT_DATE_FORMATTER;
	}

	/**
	 * @return {@link DateTimeUtils#DEFAULT_DATE_TIME_PARSER}
	 */
	public static DateTimeParser getDefaultDateTimeParser() {
		return DEFAULT_DATE_TIME_PARSER;
	}

	/**
	 * Convert the provided {@link LocalDateTime} to a string using
	 * {@link DateTimeUtils#DEFAULT_DATE_TIME_PARSER}
	 * 
	 * @param date See {@link DateTimeUtils#DEFAULT_DATE_TIME_PARSER}
	 * @return See {@link DateTimeUtils#DEFAULT_DATE_TIME_PARSER}
	 */
	public static final String dateToString(LocalDateTime date) {
		return DEFAULT_DATE_TIME_PARSER.dateToString(date);
	}

	/**
	 * Convert the provided string to a {@link LocalDateTime} using
	 * {@link DateTimeUtils#DEFAULT_DATE_TIME_PARSER}
	 * 
	 * @param string See {@link DateTimeUtils#DEFAULT_DATE_TIME_PARSER}
	 * @return See {@link DateTimeUtils#DEFAULT_DATE_TIME_PARSER}
	 */
	public static final LocalDateTime dateFromString(String string) {
		return DEFAULT_DATE_TIME_PARSER.dateFromString(string);
	}
}