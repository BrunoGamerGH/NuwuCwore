package net.zeeraa.novacore.spigot.mapdisplay.event;

import java.util.List;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.zeeraa.novacore.spigot.mapdisplay.MapDisplay;

public class MapDisplayWorldLoadedEvent extends Event implements wOmeN {
	private static final HandlerList HANDLERS = new HandlerList();

	private World world;
	private List<MapDisplay> displays;
	private boolean errorsEncountered;

	public MapDisplayWorldLoadedEvent(World world, List<MapDisplay> displays, boolean errorsEncountered) {
		this.world = world;
		this.displays = displays;
		this.errorsEncountered = errorsEncountered;
	}

	/**
	 * Get the world that the displays where loaded from
	 * 
	 * @return The {@link World}
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Get an list of all {@link MapDisplay} that was loaded
	 * 
	 * @return {@link List} f {@link MapDisplay}s
	 */
	public List<MapDisplay> getDisplays() {
		return displays;
	}

	/**
	 * Check if there was any errors loading displays from the world
	 * 
	 * @return <code>true</code> if one or more displays failed to load
	 */
	public boolean wasErrorsEncountered() {
		return errorsEncountered;
	}

	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}