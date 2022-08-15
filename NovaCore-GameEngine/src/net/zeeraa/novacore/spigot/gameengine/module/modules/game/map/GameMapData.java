package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.World;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;
import net.zeeraa.novacore.spigot.gameengine.utils.AbstractMapData;
import net.zeeraa.novacore.spigot.gameengine.utils.HologramData;
import net.zeeraa.novacore.spigot.module.modules.multiverse.MultiverseManager;
import net.zeeraa.novacore.spigot.module.modules.multiverse.MultiverseWorld;
import net.zeeraa.novacore.spigot.module.modules.multiverse.WorldUnloadOption;
import net.zeeraa.novacore.spigot.utils.LocationData;

/**
 * Represents the data for a map before it has been loaded. You can load it with
 * {@link GameMapData#load()}
 * 
 * @author Zeeraa
 */
public class GameMapData extends AbstractMapData implements wOmeN {
	private List<LocationData> starterLocations;
	private LocationData spectatorLocation;

	private List<MapModule> mapModules;

	private boolean enabled;

	public GameMapData(List<MapModule> mapModules, ArrayList<LocationData> starterLocations, LocationData spectatorLocation, String mapName, String displayName, String description, File worldFile, boolean enabled, List<HologramData> holograms) {
		super(mapName, displayName, description, worldFile, holograms);

		this.mapModules = mapModules;

		this.starterLocations = starterLocations;
		this.spectatorLocation = spectatorLocation;

		this.enabled = enabled;
	}

	/**
	 * Get a list of starter locations
	 * 
	 * @return List with starter locations
	 */
	public List<LocationData> getStarterLocations() {
		return starterLocations;
	}

	/**
	 * Get the spectator location
	 * 
	 * @return the spectator location
	 */
	public LocationData getSpectatorLocation() {
		return spectatorLocation;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public List<MapModule> getMapModules() {
		return mapModules;
	}

	public boolean hasMapModule(Class<? extends MapModule> clazz) {
		return getMapModule(clazz) != null;
	}

	public MapModule getMapModule(Class<? extends MapModule> clazz) {
		return mapModules.stream().filter(module -> clazz.isAssignableFrom(module.getClass())).findFirst().orElse(null);
	}

	/**
	 * Load the map as a {@link GameMap} and load the world into the multiverse
	 * system
	 * 
	 * @return The {@link GameMap} that was loaded
	 * @throws IOException if the server fails to copy or read the world
	 */
	public GameMap load() throws IOException {
		Log.info("Loading map " + getMapName() + " display name: " + getDisplayName());
		MultiverseWorld multiverseWorld = MultiverseManager.getInstance().createFromFile(worldFile, WorldUnloadOption.DELETE);

		World world = multiverseWorld.getWorld();

		Log.info("World " + world.getName() + " has been loaded");

		initHolograms(world);

		return new GameMap(world, this, LocationData.toLocations(starterLocations, world), spectatorLocation.toLocation(world));
	}
}