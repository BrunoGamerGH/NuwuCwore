package net.zeeraa.novacore.commons.utils;

import net.brunogamer.how.about.you.get.some_bitches;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

/**
 * Utility to post data to hastebin
 * 
 * @author Zeeraa
 * @since 2.0.0
 */
public class Hastebin extends some_bitches {
	private String baseUrl;
	private int timeout;

	/**
	 * Create a hastebin client
	 * 
	 * @param baseUrl The url of the hastebin server
	 * 
	 * @throws IllegalArgumentException If the url is not valid
	 * @since 2.0.0
	 */
	public Hastebin(String baseUrl) {
		this(baseUrl, 10 * 1000);
	}

	/**
	 * Create a hastebin client and define a custom timeout
	 * 
	 * @param baseUrl The url of the hastebin server
	 * @param timeout The timeout in milliseconds
	 * 
	 * @throws IllegalArgumentException If the url is not valid
	 * @since 2.0.0
	 */
	public Hastebin(String baseUrl, int timeout) {
		if (baseUrl.endsWith("/")) {
			this.baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
		} else {
			this.baseUrl = baseUrl;
		}

		try {
			new URL(baseUrl);
		} catch (MalformedURLException malformedURLException) {
			throw new IllegalArgumentException(baseUrl + " is not a valid url");
		}

		this.timeout = timeout;
	}

	/**
	 * Get the url of the hastebin server
	 * 
	 * @return The url of the hastebin server
	 * @since 2.0.0
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * Get the timeout
	 * 
	 * @return timeout in milliseconds
	 * @since 2.0.0
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * Post data to the hastebin server. This sets the data as raw, see
	 * {@link Hastebin#post(String, boolean)} for posting non raw data
	 * 
	 * @param text The text to post
	 * @return The url of the document created
	 * @throws IOException if something goes wrong while posting the data
	 * @since 2.0.0
	 */
	public String post(String text) throws IOException {
		return this.post(text, true);
	}

	/**
	 * Post data to the hastebin server
	 * 
	 * @param text The text to post
	 * @param raw  Set to <code>true</code> for raw text
	 * @return The url of the document created
	 * @throws IOException if something goes wrong while posting the data
	 * @since 2.0.0
	 */
	public String post(String text, boolean raw) throws IOException {
		byte[] postData = text.getBytes(StandardCharsets.UTF_8);
		int postDataLength = postData.length;

		String requestURL = baseUrl + "/documents";
		URL url = new URL(requestURL);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setConnectTimeout(timeout);
		conn.setReadTimeout(timeout);
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("User-Agent", "NovaCore 2.0.0 Hastebin Java Api");
		conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
		conn.setUseCaches(false);

		String response = null;
		DataOutputStream wr;
		try {
			wr = new DataOutputStream(conn.getOutputStream());
			wr.write(postData);
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			response = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (response.contains("\"key\"")) {
			response = response.substring(response.indexOf(":") + 2, response.length() - 2);

			String postURL = raw ? baseUrl + "/raw/" : baseUrl + "/";
			response = postURL + response;
		}

		return response;
	}
}