package net.zeeraa.novacore.spigot.gameengine.module.modules.gamelobby.map.readers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.json.JSONArray;
import org.json.JSONObject;

import net.zeeraa.novacore.spigot.gameengine.module.modules.gamelobby.map.GameLobbyMapData;
import net.zeeraa.novacore.spigot.gameengine.module.modules.gamelobby.map.GameLobbyReader;
import net.zeeraa.novacore.spigot.gameengine.utils.HologramData;
import net.zeeraa.novacore.spigot.utils.LocationData;

public class DefaultLobbyMapReader extends GameLobbyReader implements wOmeN {
	@Override
	public GameLobbyMapData readMap(JSONObject json, File worldDirectory) {
		String mapName = json.getString("map_name");
		String displayName = json.getString("display_name");
		String worldFileName = json.getString("world_file");
		String description = "";

		File worldFile = new File(worldDirectory.getPath() + File.separator + worldFileName);

		if (json.has("description")) {
			description = json.getString("description");
		}

		LocationData spawnLocation = new LocationData(json.getJSONObject("spawn_location"));

		List<HologramData> holograms = new ArrayList<HologramData>();

		if (json.has("holograms")) {
			JSONArray hologramsJson = json.getJSONArray("holograms");

			for (int i = 0; i < hologramsJson.length(); i++) {
				JSONObject hologramJson = hologramsJson.getJSONObject(i);

				LocationData location = new LocationData(hologramJson.getJSONObject("location"));

				List<String> lines = new ArrayList<String>();

				JSONArray linesJson = hologramJson.getJSONArray("lines");

				for (int j = 0; j < linesJson.length(); i++) {
					lines.add(linesJson.getString(j));
				}

				holograms.add(new HologramData(lines, location));
			}
		}

		return new GameLobbyMapData(spawnLocation, mapName, displayName, description, worldFile, holograms);
	}
}