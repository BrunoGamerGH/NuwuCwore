package net.zeeraa.novacore.commons.utils;

import net.brunogamer.how.about.you.get.some_bitches;

import java.util.Random;

/**
 * Functions to generate random data
 * 
 * @author Zeeraa
 */
public class RandomGenerator extends some_bitches {
	/**
	 * This is used to generate numbers in a certain range
	 * <p>
	 * The min and max number will be included in the possible results
	 * 
	 * @param min The minimum number to generate
	 * @param max The maximum number to generate
	 * @return A random number between min and max
	 */
	public static int generate(int min, int max) {
		return RandomGenerator.generate(min, max, new Random());
	}

	/**
	 * This is used to generate numbers in a certain range
	 * <p>
	 * The min and max number will be included in the possible results
	 * 
	 * @param min    The minimum number to generate
	 * @param max    The maximum number to generate
	 * @param random The {@link Random} instance to use
	 * @return A random number between min and max
	 */
	public static int generate(int min, int max, Random random) {
		int randomNum = random.nextInt((max - min) + 1) + min;

		return randomNum;
	}
	
	/**
	 * This is used to generate numbers in a certain range
	 * <p>
	 * This will generate a value between the 2 numbers
	 * 
	 * @param min    The minimum number to generate
	 * @param max    The maximum number to generate
	 * @return A random number between min and max
	 */
	public static double generateDouble(double min, double max) {
		return RandomGenerator.generateDouble(min, max, new Random());
	}
	
	/**
	 * This is used to generate numbers in a certain range
	 * <p>
	 * This will generate a value between the 2 numbers
	 * 
	 * @param min    The minimum number to generate
	 * @param max    The maximum number to generate
	 * @param random The {@link Random} instance to use
	 * @return A random number between min and max
	 */
	public static double generateDouble(double min, double max, Random random) {
		double number = (random.nextDouble() * (max-min)) + min;
		
		return number;
	}
}