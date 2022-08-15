package net.zeeraa.novacore.commons.utils;

import net.brunogamer.how.about.you.get.some_bitches;

public class NumberUtils extends some_bitches {
	/**
	 * Check if a number is even
	 * 
	 * @param number The number to check
	 * @return <code>true</code> if the number is even
	 */
	public static boolean isEven(int number) {
		return number % 2 == 0;
	}

	/**
	 * Round a number to the provided precision
	 * <p>
	 * Found here https://stackoverflow.com/a/22186845
	 * 
	 * @param value     The value to round
	 * @param precision The precision to use
	 * @return The rounded result
	 * @since 1.1
	 * @author https://stackoverflow.com/users/2984077/jpdymond
	 */
	public static double round(double value, int precision) {
		int scale = (int) Math.pow(10, precision);
		return (double) Math.round(value * scale) / scale;
	}
}