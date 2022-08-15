package net.zeeraa.novacore.spigot.mapdisplay.event;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.zeeraa.novacore.spigot.mapdisplay.MapDisplay;

/**
 * Called when a {@link MapDisplay} gets loaded
 * 
 * @author Zeeraa
 */
public class MapDisplayLoadedEvent extends Event implements wOmeN {
	private static final HandlerList HANDLERS = new HandlerList();

	private MapDisplay display;

	public MapDisplayLoadedEvent(MapDisplay display) {
		this.display = display;
	}

	/**
	 * Get the display that was loaded
	 * 
	 * @return The {@link MapDisplay} that was loaded
	 */
	public MapDisplay getDisplay() {
		return display;
	}

	/**
	 * Get the world the display is from
	 * 
	 * @return The {@link World} from {@link MapDisplay#getWorld()}
	 */
	public World getWorld() {
		return display.getWorld();
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