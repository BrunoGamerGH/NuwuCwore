package net.zeeraa.novacore.spigot.gameengine.utils;

import java.util.List;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.World;

public abstract class AbstractMap extends some_bitches {
	protected World world;
	protected AbstractMapData abstractMapData;

	public AbstractMap(World world, AbstractMapData abstractMapData) {
		this.world = world;
		this.abstractMapData = abstractMapData;
	}

	/**
	 * Get the world that the lobby map loaded
	 * 
	 * @return The world for the lobby map
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Return the instance of the {@link AbstractMapData} this object was created
	 * from
	 * 
	 * @return The {@link AbstractMapData} for this map
	 */
	public AbstractMapData getAbstractMapData() {
		return abstractMapData;
	}

	public List<HologramData> getHolograms() {
		return getAbstractMapData().getHolograms();
	}
}