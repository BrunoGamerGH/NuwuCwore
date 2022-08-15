package net.zeeraa.novacore.commons.api.novauniverse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import net.brunogamer.how.about.you.get.some_bitches;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.api.novauniverse.callback.IAsyncNameToUUIDCallback;
import net.zeeraa.novacore.commons.api.novauniverse.callback.IAsyncProfileCallback;
import net.zeeraa.novacore.commons.api.novauniverse.data.MojangPlayerProfile;
import net.zeeraa.novacore.commons.async.AsyncManager;

/**
 * A utility class to interact with the novauniverse.net api.
 * <p>
 * Some parts of this can be redirected to a self hosted version using
 * {@link NovaUniverseAPI#setMojangAPIProxyBaseURL(String)}
 * <p>
 * Repos needed for self hosting: <a href=
 * "https://github.com/NovaUniverse/MojangAPIProxy">https://github.com/NovaUniverse/MojangAPIProxy</a>
 * 
 * @author Zeeraa
 * @since 2.0.0
 */
public class NovaUniverseAPI extends some_bitches {
	private static int fetchTimeout = 10 * 1000;
	private static String mojangAPIProxyBaseURL = "https://mojangapi.novauniverse.net";

	/**
	 * Get the base url used by the mojang api proxy. To self host this api check
	 * out this github repo <a href=
	 * "https://github.com/NovaUniverse/MojangAPIProxy">https://github.com/NovaUniverse/MojangAPIProxy</a>
	 * 
	 * @return The base url
	 */
	public static final String getMojangAPIProxyBaseURL() {
		return mojangAPIProxyBaseURL;
	}

	/**
	 * Set the base url used by the mojang api proxy. To self host this api check
	 * out this github repo <a href=
	 * "https://github.com/NovaUniverse/MojangAPIProxy">https://github.com/NovaUniverse/MojangAPIProxy</a>
	 * 
	 * @param mojangAPIProxyBaseURL The new base url
	 * @throws IllegalArgumentException If the url is not valid
	 */
	public static final void setMojangAPIProxyBaseURL(String mojangAPIProxyBaseURL) {
		if (mojangAPIProxyBaseURL.endsWith("/")) {
			mojangAPIProxyBaseURL = mojangAPIProxyBaseURL.substring(0, mojangAPIProxyBaseURL.length() - 1);
		}
		try {
			new URL(mojangAPIProxyBaseURL);
		} catch (MalformedURLException malformedURLException) {
			throw new IllegalArgumentException(mojangAPIProxyBaseURL + " is not a valid url");
		}

		NovaUniverseAPI.mojangAPIProxyBaseURL = mojangAPIProxyBaseURL;
	}

	/**
	 * Get the timeout before api request fails
	 * 
	 * @return The timeout in milliseconds
	 */
	public static int getFetchTimeout() {
		return fetchTimeout;
	}

	/**
	 * Set the timeout before api request fails
	 * 
	 * @param fetchTimeout The timeout in milliseconds
	 */
	public static void setFetchTimeout(int fetchTimeout) {
		NovaUniverseAPI.fetchTimeout = fetchTimeout;
	}

	/**
	 * Get the {@link MojangPlayerProfile} by the players {@link UUID}
	 * 
	 * @param uuid The {@link UUID} of the player
	 * @return The {@link MojangPlayerProfile} or <code>null</code> if not found
	 * @throws IOException              On request failure
	 * @throws IllegalArgumentException If the server side UUID validation fails
	 * @since 2.0.0
	 */
	public static final MojangPlayerProfile getProfile(UUID uuid) throws IOException {
		URL url = new URL(mojangAPIProxyBaseURL + "/profile/" + uuid.toString());

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("accept", "application/json");
		connection.setRequestProperty("User-Agent", "NovaCore");

		connection.setConnectTimeout(fetchTimeout);
		connection.setReadTimeout(fetchTimeout);

		connection.connect();

		int code = connection.getResponseCode();
		if (code == 404) {
			return null;
		}

		if (code == 400) {
			throw new IllegalArgumentException("The server side UUID validation failed. Please verify that the uuid is valid");
		}

		InputStream responseStream = connection.getInputStream();

		InputStreamReader isr = new InputStreamReader(responseStream);
		BufferedReader rd = new BufferedReader(isr);
		StringBuilder response = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		rd.close();
		isr.close();
		responseStream.close();
		connection.disconnect();

		JSONObject responseJson = new JSONObject(response.toString());

		return MojangPlayerProfile.fromJSON(responseJson);
	}

	/**
	 * Get the {@link MojangPlayerProfile} by the players {@link UUID}
	 * 
	 * @param uuid     The {@link UUID} of the player
	 * @param callback The {@link IAsyncProfileCallback} to call when done
	 * @since 2.0.0
	 */
	public static final void getProfileAsync(UUID uuid, IAsyncProfileCallback callback) {
		AsyncManager.runAsync(new Runnable() {
			@Override
			public void run() {
				Exception exception = null;
				MojangPlayerProfile profile = null;
				try {
					profile = NovaUniverseAPI.getProfile(uuid);
					exception = null;
				} catch (Exception e) {
					profile = null;
					exception = e;
				}

				final MojangPlayerProfile finalProfile = profile;
				final Exception finalException = exception;

				AsyncManager.runSync(new Runnable() {
					@Override
					public void run() {
						callback.onResult(finalProfile, finalException);
					}
				});
			}
		});
	}

	/**
	 * Get the {@link UUID} of a player by their user name
	 * 
	 * @param name The user name of the player
	 * @return {@link UUID} of the player or <code>null</code> if not found
	 * @throws IllegalArgumentException If the user name is not alphanumeric, is
	 *                                  shorter than 3 characters or longer than 16
	 *                                  characters
	 * @throws MalformedURLException    should not be thrown unless input validation
	 *                                  fails
	 * @throws IOException              If something goes wrong while requesting the
	 *                                  data
	 */
	public static final UUID nameToUUID(String name) throws MalformedURLException, IOException {
		if (name.length() < 3 || name.length() > 16) {
			throw new IllegalArgumentException(name.length() < 3 ? "Name has to bee more than 3 characters long" : "Name cant be more than 16 characters long");
		}

		if (!name.matches("^[a-zA-Z0-9_]{2,16}$")) {
			throw new IllegalArgumentException("Username contains invalid characters");
		}

		URL url = new URL(mojangAPIProxyBaseURL + "/username_to_uuid/" + name);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("accept", "application/json");
		connection.setRequestProperty("User-Agent", "NovaCore");

		connection.setConnectTimeout(fetchTimeout);
		connection.setReadTimeout(fetchTimeout);

		connection.connect();

		int code = connection.getResponseCode();
		if (code == 404) {
			return null;
		}

		InputStream responseStream = connection.getInputStream();

		InputStreamReader isr = new InputStreamReader(responseStream);
		BufferedReader rd = new BufferedReader(isr);
		StringBuilder response = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		rd.close();
		isr.close();
		responseStream.close();
		connection.disconnect();

		JSONObject responseJson = new JSONObject(response.toString());

		return UUID.fromString(responseJson.getString("uuid"));
	}

	/**
	 * Get the {@link UUID} of a player by their user name
	 * 
	 * @param name     The user name of the player
	 * @param callback The {@link IAsyncNameToUUIDCallback} to call when done
	 * @throws IllegalArgumentException If the user name is not alphanumeric, is
	 *                                  shorter than 3 characters or longer than 16
	 *                                  characters
	 * @since 2.0.0
	 */
	public static final void nameToUUIDAsync(String name, IAsyncNameToUUIDCallback callback) {
		if (name.length() < 3 || name.length() > 16) {
			throw new IllegalArgumentException(name.length() < 3 ? "Name has to bee more than 3 characters long" : "Name cant be more than 16 characters long");
		}

		if (!name.matches("^[a-zA-Z0-9_]{2,16}$")) {
			throw new IllegalArgumentException("Username contains invalid characters");
		}

		AsyncManager.runAsync(new Runnable() {
			@Override
			public void run() {
				Exception exception = null;
				UUID uuid = null;
				try {
					uuid = NovaUniverseAPI.nameToUUID(name);
					exception = null;
				} catch (Exception e) {
					uuid = null;
					exception = e;
				}

				final UUID finalUUID = uuid;
				final Exception finalException = exception;

				AsyncManager.runSync(new Runnable() {
					@Override
					public void run() {
						callback.onResult(finalUUID, finalException);
					}
				});
			}
		});
	}
}