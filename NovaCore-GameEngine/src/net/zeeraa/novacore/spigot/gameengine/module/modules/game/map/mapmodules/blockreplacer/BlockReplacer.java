package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.blockreplacer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.commons.utils.JSONFileUtils;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.GameMap;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;
import net.zeeraa.novacore.spigot.utils.LocationUtils;

public class BlockReplacer extends MapModule implements wOmeN {
	private List<BlockReplacerConfigEntry> configEntries;
	private Random random;

	public BlockReplacer(JSONObject json) {
		super(json);

		random = new Random();

		configEntries = new ArrayList<>();

		JSONArray entries = json.getJSONArray("entries");

		for (int i = 0; i < entries.length(); i++) {
			JSONObject entry = entries.getJSONObject(i);

			String fileName = entry.getString("file_name");
			List<Material> materials = new ArrayList<>();
			JSONObject materialsJson = entry.getJSONObject("materials");
			materialsJson.keySet().forEach(key -> {
				int chanse = materialsJson.getInt(key);
				Material material;
				try {
					material = Material.valueOf(key);

					for (int j = 0; j < chanse; j++) {
						materials.add(material);
					}
				} catch (Exception e) {
					Log.error("BlockReplacer", "Invalid material: " + key);
				}
			});

			configEntries.add(new BlockReplacerConfigEntry(fileName, materials));
		}
	}

	@Override
	public void onMapLoad(GameMap map) {
		File worldFile = new File(Bukkit.getServer().getWorldContainer().getAbsolutePath() + File.separator + map.getWorld().getName());

		File blockReplacerDataFolder = new File(worldFile.getAbsolutePath() + File.separator + "novadata" + File.separator + "blockreplacer");

		if (!blockReplacerDataFolder.exists()) {
			Log.fatal("BlockReplacer", "Data folder " + blockReplacerDataFolder.getAbsolutePath() + " is missing from world " + map.getWorld().getName());
			return;
		}

		configEntries.forEach(entry -> {
			if (entry.getMaterialList().size() == 0) {
				Log.error("BlockReplacer", "No materials defined in list for " + entry.getDataFile());
				return;
			}

			File dataFile = new File(blockReplacerDataFolder.getAbsolutePath() + File.separator + entry.getDataFile());
			if (dataFile.exists()) {
				List<Location> locations = new ArrayList<>();
				try {
					JSONArray cache = JSONFileUtils.readJSONArrayFromFile(dataFile);

					for (int i = 0; i < cache.length(); i++) {
						JSONObject locationJson = cache.getJSONObject(i);
						locations.add(LocationUtils.fromJSONObject(locationJson, map.getWorld()));
					}
				} catch (JSONException | IOException e) {
					e.printStackTrace();
					Log.error("BlockReplacer", "Failed to read data file " + dataFile.getAbsolutePath() + ". " + e.getClass().getName() + " " + e.getMessage());
					return;
				}

				Log.debug("BlockReplacer", "Processing data from " + entry.getDataFile());

				locations.forEach(location -> {
					Material material = entry.getMaterialList().get(random.nextInt(entry.getMaterialList().size()));
					location.getBlock().setType(material);
				});
			} else {
				Log.error("BlockReplacer", "Missing data file " + dataFile.getAbsolutePath());
			}
		});
	}
}