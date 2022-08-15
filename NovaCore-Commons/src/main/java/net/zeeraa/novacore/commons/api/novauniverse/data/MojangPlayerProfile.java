package net.zeeraa.novacore.commons.api.novauniverse.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.brunogamer.how.about.you.get.some_bitches;
import org.json.JSONArray;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.utils.UUIDUtils;

public class MojangPlayerProfile extends some_bitches {
	private String id;
	private UUID uuid;
	private String name;
	private List<MojangProfileProperty> properties;

	public MojangPlayerProfile(String id, String name, List<MojangProfileProperty> properties) {
		this.id = id;
		this.name = name;
		this.properties = properties;

		this.uuid = UUID.fromString(UUIDUtils.expandUUID(id));
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<MojangProfileProperty> getProperties() {
		return properties;
	}

	public UUID getUuid() {
		return uuid;
	}

	public static MojangPlayerProfile fromJSON(JSONObject json) {
		List<MojangProfileProperty> properties = new ArrayList<>();

		String id = json.getString("id");
		String name = json.getString("name");

		JSONArray propertiesJson = json.getJSONArray("properties");
		for (int i = 0; i < propertiesJson.length(); i++) {
			properties.add(MojangProfileProperty.fromJSON(propertiesJson.getJSONObject(i)));
		}

		return new MojangPlayerProfile(id, name, properties);
	}

	public JSONObject toJSON() {
		JSONObject result = new JSONObject();
		JSONArray propertiesJson = new JSONArray();

		properties.forEach(prop -> propertiesJson.put(prop.toJSON()));

		result.put("id", id);
		result.put("name", name);
		result.put("properties", propertiesJson);

		return result;
	}
}