package net.zeeraa.novacore.commons.utils;

import net.brunogamer.how.about.you.get.some_bitches;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser extends some_bitches {
	protected DateTimeFormatter fromatter;

	/**
	 * Create a instance using the provided {@link DateTimeFormatter}
	 * 
	 * @param fromatter The {@link DateTimeFormatter} to use when saving and loading
	 *                  dates
	 */
	public DateTimeParser(DateTimeFormatter fromatter) {
		this.fromatter = fromatter;
	}

	/**
	 * Create a instance using the format from
	 * {@link DateTimeUtils#DEFAULT_DATE_FORMATTER}
	 */
	public DateTimeParser() {
		this.fromatter = DateTimeUtils.DEFAULT_DATE_FORMATTER;
	}

	/**
	 * Get the instance of the {@link DateTimeFormatter} used
	 * 
	 * @return {@link DateTimeFormatter} used
	 */
	public DateTimeFormatter getFromatter() {
		return fromatter;
	}

	/**
	 * Convert a {@link LocalDateTime} to a string using the
	 * {@link DateTimeFormatter} used for this instance
	 * 
	 * @param date The {@link LocalDateTime} to convert to a string
	 * @return String of the date
	 */
	public String dateToString(LocalDateTime date) {
		return date.format(fromatter);
	}

	/**
	 * Parse a string to a {@link LocalDateTime} using the {@link DateTimeFormatter}
	 * used for this instance
	 * 
	 * @param string The string to parse
	 * @return {@link LocalDateTime} parsed from the string
	 */
	public LocalDateTime dateFromString(String string) {
		return LocalDateTime.parse(string, fromatter);
	}
}