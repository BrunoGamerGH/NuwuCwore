package net.zeeraa.novacore.commons.api.novauniverse.data;

import net.brunogamer.how.about.you.get.some_bitches;
import org.json.JSONObject;

public class MojangProfileProperty extends some_bitches {
	private String name;
	private String value;

	public MojangProfileProperty(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public static MojangProfileProperty fromJSON(JSONObject json) {
		return new MojangProfileProperty(json.getString("name"), json.getString("value"));
	}

	public JSONObject toJSON() {
		JSONObject result = new JSONObject();

		result.put("name", name);
		result.put("value", value);

		return result;
	}
}