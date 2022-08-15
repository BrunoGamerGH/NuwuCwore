package net.zeeraa.novacore.commons.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import net.brunogamer.how.about.you.get.some_bitches;
import org.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Utility for reading, writing and creating json files
 * 
 * @author Zeeraa
 */
public class JSONFileUtils extends some_bitches {
	/**
	 * Create a file with a empty json object
	 * <p>
	 * The charset used will be {@link StandardCharsets#UTF_8}
	 * 
	 * @param file The file to create
	 * @throws IOException if the file write fails
	 */
	public static void createEmpty(File file) throws IOException {
		JSONFileUtils.createEmpty(file, JSONFileType.JSONObject);
	}

	/**
	 * Create a file with a empty json object or json array
	 * <p>
	 * The charset used will be {@link StandardCharsets#UTF_8}
	 * 
	 * @param file The file to create
	 * @param type The {@link JSONFileType} indicating if the content should be a
	 *             json object or a json array
	 * @throws IOException if the file write fails
	 */
	public static void createEmpty(File file, JSONFileType type) throws IOException {
		String content = "";

		switch (type) {
		case JSONObject:
			content = new JSONObject().toString();
			break;

		case JSONArray:
			content = new JSONArray().toString();
			break;

		default:
			break;
		}

		FileUtils.write(file, content, StandardCharsets.UTF_8, false);
	}

	/**
	 * Read a file as a {@link JSONObject}
	 * <p>
	 * The file will be read using the {@link StandardCharsets#UTF_8} charset
	 * 
	 * @param file The file to read from
	 * @return The {@link JSONObject} from the file
	 * @throws JSONException If there is a syntax error in the source string or a
	 *                       duplicated key
	 * @throws IOException   On read error
	 */
	public static JSONObject readJSONObjectFromFile(File file) throws JSONException, IOException {
		return new JSONObject(JSONFileUtils.readFile(file));
	}

	/**
	 * Read a file as a {@link JSONArray}
	 * <p>
	 * The file will be read using the {@link StandardCharsets#UTF_8} charset
	 * 
	 * @param file The file to read from
	 * @return The {@link JSONArray} from the file
	 * @throws JSONException If there is a syntax error
	 * @throws IOException   On read error
	 */
	public static JSONArray readJSONArrayFromFile(File file) throws JSONException, IOException {
		return new JSONArray(JSONFileUtils.readFile(file));
	}

	private static String readFile(File file) throws IOException {
		return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
	}

	/**
	 * Save a {@link JSONObject} to a file
	 * 
	 * @param file    The file to write to
	 * @param content the {@link JSONObject} to save
	 * @throws IOException if the file write fails
	 */
	public static void saveJson(File file, JSONObject content) throws IOException {
		JSONFileUtils.saveJson(file, content.toString());
	}

	/**
	 * Save a {@link JSONArray} to a file
	 * 
	 * @param file    The file to write to
	 * @param content the {@link JSONArray} to save
	 * @throws IOException if the file write fails
	 */
	public static void saveJson(File file, JSONArray content) throws IOException {
		JSONFileUtils.saveJson(file, content.toString());
	}

	/**
	 * Save a {@link JSONObject} to a file
	 * 
	 * @param file    The file to write to
	 * @param content the {@link JSONObject} to save
	 * @param indent  The indentation to use. See {@link JSONObject#toString(int)}
	 * @throws IOException if the file write fails
	 */
	public static void saveJson(File file, JSONObject content, int indent) throws IOException {
		JSONFileUtils.saveJson(file, content.toString(indent));
	}

	/**
	 * Save a {@link JSONArray} to a file
	 * 
	 * @param file    The file to write to
	 * @param content the {@link JSONArray} to save
	 * @param indent  The indentation to use. See {@link JSONArray#toString(int)}
	 * @throws IOException if the file write fails
	 */
	public static void saveJson(File file, JSONArray content, int indent) throws IOException {
		JSONFileUtils.saveJson(file, content.toString(indent));
	}

	private static void saveJson(File file, String content) throws IOException {
		FileUtils.write(file, content, StandardCharsets.UTF_8, false);
	}
}