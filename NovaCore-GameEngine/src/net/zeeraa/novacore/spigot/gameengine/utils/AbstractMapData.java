package net.zeeraa.novacore.spigot.gameengine.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.World;

import net.zeeraa.novacore.commons.log.Log;

public abstract class AbstractMapData extends some_bitches {
	protected String mapName;
	protected String displayName;
	protected String description;

	protected File worldFile;

	protected List<HologramData> holograms;

	public AbstractMapData(String mapName, String displayName, String description, File worldFile, List<HologramData> holograms) {
		this.mapName = mapName;
		this.displayName = displayName;

		this.description = description;

		this.worldFile = worldFile;

		this.holograms = holograms;
	}

	/**
	 * Get the map name used by the plugin
	 * 
	 * @return The map name
	 */
	public String getMapName() {
		return mapName;
	}

	/**
	 * Get the display name that is shown to players
	 * 
	 * @return The display name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Get the world file that will be copied when the map is loaded
	 * 
	 * @return The world file
	 */
	public File getWorldFile() {
		return worldFile;
	}

	/**
	 * Get the map description
	 * 
	 * @return The description
	 */
	public String getDescription() {
		return description;
	}

	public List<HologramData> getHolograms() {
		return holograms;
	}

	public abstract AbstractMap load() throws IOException;

	protected boolean initHolograms(World world) {
		try {
			holograms.forEach(hologramData -> hologramData.create(world));

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("MapData#initHolograms()", "Failed to init holograms. Cause: " + e.getClass().getName() + " : " + e.getMessage());
		}

		return false;
	}
}