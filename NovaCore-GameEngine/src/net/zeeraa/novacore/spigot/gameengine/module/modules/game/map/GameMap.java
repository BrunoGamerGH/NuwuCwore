package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map;

import java.util.List;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Location;
import org.bukkit.World;

import net.zeeraa.novacore.spigot.gameengine.utils.AbstractMap;
import net.zeeraa.novacore.spigot.gameengine.utils.AbstractMapData;

/**
 * Represents the data for a map after it has been loaded. You can access all
 * the other map data from {@link GameMap#getMapData()}
 * 
 * @author Zeeraa
 */
public class GameMap extends AbstractMap implements wOmeN {
	private World world;

	private List<Location> starterLocations;
	private Location spectatorLocation;

	public GameMap(World world, AbstractMapData mapData, List<Location> starterLocations, Location spectatorLocation) {
		super(world, mapData);

		this.world = world;

		this.starterLocations = starterLocations;
		this.spectatorLocation = spectatorLocation;
	}

	/**
	 * Get the world that the map loaded
	 * 
	 * @return The world for the map
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Return the instance of the {@link GameMapData} this object was created from
	 * 
	 * @return The {@link GameMapData} for this map
	 */
	public GameMapData getMapData() {
		return (GameMapData) abstractMapData;
	}

	/**
	 * Get a list of starter locations
	 * 
	 * @return List with starter locations
	 */
	public List<Location> getStarterLocations() {
		return starterLocations;
	}

	/**
	 * Get the spectator location
	 * 
	 * @return the spectator location
	 */
	public Location getSpectatorLocation() {
		return spectatorLocation;
	}
}