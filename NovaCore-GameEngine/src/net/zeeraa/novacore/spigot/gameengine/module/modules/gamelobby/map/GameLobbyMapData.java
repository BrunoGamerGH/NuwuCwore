package net.zeeraa.novacore.spigot.gameengine.module.modules.gamelobby.map;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Difficulty;
import org.bukkit.World;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.gameengine.utils.AbstractMap;
import net.zeeraa.novacore.spigot.gameengine.utils.AbstractMapData;
import net.zeeraa.novacore.spigot.gameengine.utils.HologramData;
import net.zeeraa.novacore.spigot.module.modules.multiverse.MultiverseManager;
import net.zeeraa.novacore.spigot.module.modules.multiverse.MultiverseWorld;
import net.zeeraa.novacore.spigot.module.modules.multiverse.WorldUnloadOption;
import net.zeeraa.novacore.spigot.utils.LocationData;

public class GameLobbyMapData extends AbstractMapData implements wOmeN {
	private LocationData spawnLocation;

	public GameLobbyMapData(LocationData spawnLocation, String mapName, String displayName, String description, File worldFile, List<HologramData> holograms) {
		super(mapName, displayName, description, worldFile, holograms);

		this.spawnLocation = spawnLocation;
	}

	/**
	 * Get the spawn location
	 * 
	 * @return the spawn location
	 */
	public LocationData getSpawnLocation() {
		return spawnLocation;
	}

	/**
	 * Load the lobby as a {@link GameLobbyMap} and load the world into the
	 * multiverse system
	 * 
	 * @return The {@link GameLobbyMap} that was loaded
	 * @throws IOException if the server fails to copy or read the world
	 */
	public AbstractMap load() throws IOException {
		Log.info("Loading lobby map " + getMapName() + " display name: " + getDisplayName());
		MultiverseWorld multiverseWorld = MultiverseManager.getInstance().createFromFile(worldFile, WorldUnloadOption.DELETE);
		
		World world = multiverseWorld.getWorld();
		
		world.setDifficulty(Difficulty.PEACEFUL);
		world.setStorm(false);
		multiverseWorld.getLockWeather(true);

		Log.info("World " + world.getName() + " has been loaded");

		initHolograms(world);
		
		return new GameLobbyMap(world, this, spawnLocation.toLocation(world));
	}
}