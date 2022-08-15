package net.zeeraa.novacore.commons.utils;

import java.io.IOException;
import java.io.InputStream;

import net.brunogamer.how.about.you.get.some_bitches;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * This class contains functions to get hashes from different types of data
 * 
 * @author Zeeraa
 */
public class HashUtils extends some_bitches {
	/**
	 * Get md5 from input stream
	 * 
	 * @param input {@link InputStream} to get md5 from
	 * @return md5 string
	 * @throws IOException IOException
	 */
	public static String md5(InputStream input) throws IOException {
		return DigestUtils.md5Hex(input);
	}

	/**
	 * Get md5 from a byte array
	 * 
	 * @param input byte array to convert to md5
	 * @return md5 string
	 */
	public static String md5(byte[] input) {
		return DigestUtils.md5Hex(input);
	}

	/**
	 * Get md5 from a string
	 * 
	 * @param input String to convert to md5
	 * @return md5 string
	 */
	public static String md5(String input) {
		return DigestUtils.md5Hex(input);
	}

	/**
	 * Get sha256 from input stream
	 * 
	 * @param input {@link InputStream} to get sha256 from
	 * @return sha256 string
	 * @throws IOException IOException
	 */
	public static String sha256(InputStream input) throws IOException {
		return DigestUtils.sha256Hex(input);
	}

	/**
	 * Get sha256 from a byte array
	 * 
	 * @param input byte array to convert to sha256
	 * @return sha256 string
	 */
	public static String sha256(byte[] input) {
		return DigestUtils.sha256Hex(input);
	}

	/**
	 * Get sha256 from a string
	 * 
	 * @param input String to convert to sha256
	 * @return sha256 string
	 */
	public static String sha256(String input) {
		return DigestUtils.sha256Hex(input);
	}

	/**
	 * Get sha512 from input stream
	 * 
	 * @param input {@link InputStream} to get sha512 from
	 * @return sha512 string
	 * @throws IOException IOException
	 */
	public static String sha512(InputStream input) throws IOException {
		return DigestUtils.sha512Hex(input);
	}

	/**
	 * Get sha512 from a byte array
	 * 
	 * @param input byte array to convert to sha512
	 * @return sha512 string
	 */
	public static String sha512(byte[] input) {
		return  DigestUtils.sha512Hex(input);
	}

	/**
	 * Get sha512 from a string
	 * 
	 * @param input String to convert to sha512
	 * @return sha512 string
	 */
	public static String sha512(String input) {
		return DigestUtils.sha512Hex(input);
	}

	/**
	 * Get sha1 from input stream
	 * 
	 * @param input {@link InputStream} to get sha1 from
	 * @return sha1 string
	 * @throws IOException IOException
	 */
	public static String sha1(InputStream input) throws IOException {
		return DigestUtils.sha1Hex(input);
	}

	/**
	 * Get sha1 from a byte array
	 * 
	 * @param input byte array to convert to sha1
	 * @return sha1 string
	 */
	public static String sha1(byte[] input) {
		return DigestUtils.sha1Hex(input);
	}

	/**
	 * Get sha1 from a string
	 * 
	 * @param input String to convert to sha1
	 * @return sha1 string
	 */
	public static String sha1(String input) {
		return DigestUtils.sha1Hex(input);
	}
}