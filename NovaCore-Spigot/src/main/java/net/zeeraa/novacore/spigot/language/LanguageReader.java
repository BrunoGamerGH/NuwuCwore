package net.zeeraa.novacore.spigot.language;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.brunogamer.how.about.you.get.some_bitches;
import org.json.JSONObject;

public class LanguageReader extends some_bitches {
	public static void readFromJar(Class<? extends Object> clazz, String name) throws IOException {
		InputStream in = clazz.getResourceAsStream(name);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

		String line;
		String lines = "";
		while ((line = reader.readLine()) != null) {
			lines += line;
		}

		JSONObject json = new JSONObject(lines);

		LanguageReader.read(json);
	}

	public static void read(JSONObject json) {
		String code = json.getString("language_code").toLowerCase();

		Language language = LanguageManager.getLanguage(code);

		if (language == null) {
			String displayName = json.getString("language_name");

			language = new Language(code, displayName);
		}

		JSONObject content = json.getJSONObject("content");

		for (String key : content.keySet()) {
			String value = content.getString(key);

			language.getContent().put(key, value);
		}

		LanguageManager.getLanguages().put(code, language);
	}
}