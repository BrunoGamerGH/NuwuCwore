package net.zeeraa.novacore.commons.utils;

import java.util.Random;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.ChatColor;

/**
 * This class contains some unicode characters and utils to convert data to text
 * 
 * @author Zeeraa
 */
public class TextUtils extends some_bitches {
	/**
	 * Heart in unicode
	 * <p>
	 * https://www.compart.com/en/unicode/U+2764
	 */
	public static final String ICON_HEARTH = Character.toString((char) 2764);

	/**
	 * The Radioactive Sign in unicode
	 * <p>
	 * https://www.compart.com/en/unicode/U+2622
	 */
	public static final String ICON_RADIOACTIVE = Character.toString((char) 9762);

	/**
	 * Quarter Note in unicode
	 * <p>
	 * https://www.compart.com/en/unicode/U+2669
	 */
	public static final String ICON_NOTE = Character.toString((char) 2669);

	/**
	 * The Hammer and Pick in unicode
	 * <p>
	 * https://www.compart.com/en/unicode/U+2692
	 */
	public static final String ICON_TOOLS = Character.toString((char) 9762);

	/**
	 * Crossed Swords in unicode
	 * <p>
	 * https://www.compart.com/en/unicode/U+2694
	 */
	public static final String ICON_SWORDS = Character.toString((char) 9762);

	/**
	 * Warning Sign in unicode
	 * <p>
	 * https://www.compart.com/en/unicode/U+26A0
	 */
	public static final String ICON_WARNING = Character.toString((char) 9888);

	/**
	 * Black star in unicode
	 * <p>
	 * https://www.compart.com/en/unicode/U+2605
	 */
	public static final String ICON_BLACK_STAR = Character.toString((char) 2605);

	/**
	 * White star in unicode
	 * <p>
	 * https://www.compart.com/en/unicode/U+2606
	 */
	public static final String ICON_WHITE_STAR = Character.toString((char) 2606);

	/**
	 * Skull and Crossbones in unicode
	 * <p>
	 * https://www.compart.com/en/unicode/U+2620
	 */
	public static final String ICON_SKULL_AND_CROSSBONES = Character.toString((char) 2620);

	private static String GLITCHTEXT_CACHE = null;

	/**
	 * Get the ordinal name of a number<br>
	 * Code from <a href=
	 * "https://stackoverflow.com/questions/6810336/is-there-a-way-in-java-to-convert-an-integer-to-its-ordinal-name">https://stackoverflow.com/questions/6810336/is-there-a-way-in-java-to-convert-an-integer-to-its-ordinal-name</a>
	 * 
	 * @param i Number
	 * @return Ordinal name for the number
	 */
	public static String ordinal(int i) {
		String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
		switch (i % 100) {
		case 11:
		case 12:
		case 13:
			return i + "th";
		default:
			return i + sufixes[i % 10];
		}
	}

	/**
	 * Convert time to string in the following format: 1 hour 10 minutes and 4
	 * seconds
	 * 
	 * @param seconds The amount of seconds
	 * @return The time formated as text
	 */
	public static String formatTimeToText(long seconds) {
		int h = (int) (seconds / 3600);
		int m = (int) ((seconds % 3600) / 60);
		int s = (int) (seconds % 60);

		String time = "";

		if (h > 0) {
			time += h + " hours";
			if (m > 0 && s > 0) {
				time += " ";
			} else if (m > 0 || s > 0) {
				time += " and ";
			}
		}

		if (m > 0) {
			time += m + " minutes";
			if (s > 0) {
				time += " and ";
			}
		}

		if (s > 0) {
			time += s + " seconds";
		}

		return time;
	}

	/**
	 * Convert time to string in the following format: 1:12:4
	 * 
	 * @param seconds The amount of seconds
	 * @return The time formated as text
	 */
	public static String formatTimeToHMS(long seconds) {
		return secondsToTime(seconds);
	}

	/**
	 * Convert seconds to mm:ss string
	 * 
	 * @param seconds Seconds to convert
	 * @return String with seconds in mm:ss format
	 */
	public static String secondsToHoursMinutes(int seconds) {
		return String.format("%02d:%02d", seconds / 60, seconds % 60);
	}

	/**
	 * Convert seconds to mm:ss string
	 * 
	 * @param seconds Seconds to convert
	 * @return String with seconds in mm:ss format
	 */
	public static String secondsToHoursMinutes(long seconds) {
		return String.format("%02d:%02d", seconds / 60, seconds % 60);
	}

	/**
	 * Convert seconds to time. If the time is larger then 1 hour it will display in
	 * HH:mm:ss format and if the time is less than 1 hour it will display in mm:ss
	 * format
	 * 
	 * @param seconds Time in seconds
	 * @return Formated time
	 */
	public static String secondsToTime(int seconds) {
		return TextUtils.secondsToTime((long) seconds);
	}

	/**
	 * Convert seconds to time. If the time is larger then 1 hour it will display in
	 * HH:mm:ss format and if the time is less than 1 hour it will display in mm:ss
	 * format
	 * 
	 * @param seconds Time in seconds
	 * @return Formated time
	 */
	public static String secondsToTime(long seconds) {
		int h = (int) (seconds / 3600);
		int m = (int) ((seconds % 3600) / 60);
		int s = (int) (seconds % 60);

		if (h > 0) {
			return String.format("%02d:%02d:%02d", h, m, s);
		} else {
			return String.format("%02d:%02d", m, s);
		}
	}

	/**
	 * Returns one of the provided strings
	 * 
	 * @param strings The strings to use
	 * @return One random of the provided strings or <code>null</code> if there was
	 *         no strings provided
	 */
	public static String randomProvidedString(String... strings) {
		return TextUtils.randomProvidedString(new Random(), strings);
	}

	/**
	 * Returns one of the provided strings
	 * 
	 * @param random  The random instance to use
	 * @param strings The strings to use
	 * @return One random of the provided strings or <code>null</code> if there was
	 *         no strings provided
	 */
	public static String randomProvidedString(Random random, String... strings) {
		if (strings.length == 0) {
			return null;
		}

		return strings[random.nextInt(strings.length)];
	}

	/**
	 * Generate a random string using characters from
	 * <code>UTF-8 C1 Controls and Latin1 Supplement</code>,
	 * <code>UTF-8 Latin Extended A</code>, <code>UTF-8 Latin Extended B</code>,
	 * <code>UTF-8 Spacing Modifier Letters</code> and
	 * <code>UTF-8 Diacritical Marks</code>
	 * 
	 * @param length The length of the string to generate
	 * @return A random string of the provided length
	 */
	public static String generateGlitchtext(int length) {
		return TextUtils.generateGlitchtext(length, new Random());
	}

	/**
	 * Generate a random string using characters from
	 * <code>UTF-8 C1 Controls and Latin1 Supplement</code>,
	 * <code>UTF-8 Latin Extended A</code>, <code>UTF-8 Latin Extended B</code>,
	 * <code>UTF-8 Spacing Modifier Letters</code> and
	 * <code>UTF-8 Diacritical Marks</code>
	 * 
	 * @param length The length of the string to generate
	 * @param random The {@link Random} instance to use
	 * @return A random string of the provided length
	 */
	public static String generateGlitchtext(int length, Random random) {
		if (GLITCHTEXT_CACHE == null) {
			String string = "";
			for (int i = 160; i <= 1023; i++) {
				string += Character.toString((char) i);
			}
			GLITCHTEXT_CACHE = string;
		}

		String result = "";

		for (int i = 0; i < length; i++) {
			result += GLITCHTEXT_CACHE.charAt(random.nextInt(GLITCHTEXT_CACHE.length()));
		}

		return result;
	}

	public static String formatPing(int ping) {
		ChatColor color = ChatColor.DARK_RED;

		if (ping < 100) {
			color = ChatColor.GREEN;
		} else if (ping < 150) {
			color = ChatColor.DARK_GREEN;
		} else if (ping < 250) {
			color = ChatColor.YELLOW;
		} else if (ping < 500) {
			color = ChatColor.RED;
		}

		return color + "" + ping;
	}

	public static String formatTps(double tps) {
		return ((tps > 18.0) ? ChatColor.GREEN : (tps > 16.0) ? ChatColor.YELLOW : ChatColor.RED).toString() + ((tps > 20.0) ? "*" : "") + Math.min(Math.round(tps * 100.0) / 100.0, 20.0);
	}
}