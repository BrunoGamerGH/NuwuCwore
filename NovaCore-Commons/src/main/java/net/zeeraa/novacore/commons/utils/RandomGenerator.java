package net.zeeraa.novacore.commons.utils;

import java.util.Random;

/**
 * Functions to generate random data
 * 
 * @author Zeeraa
 */
public class RandomGenerator {
	/**
	 * This is used to generate numbers in a certain range
	 * 
	 * @param min The minimum number to generate
	 * @param max The maximum number to generate
	 * @return A random number between min and max
	 */
	public static int generate(int min, int max) {
		Random rand = new Random();

		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}
}