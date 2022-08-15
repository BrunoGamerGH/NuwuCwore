package net.zeeraa.novacore.spigot.module.modules.lootdrop.event;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LootDropSpawnEvent extends Event implements wOmeN {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	private Location location;
	private String lootTable;
	
	private boolean cancel;

	public LootDropSpawnEvent(Location location, String lootTable) {
		this.location = location;
		this.lootTable = lootTable;
		
		this.cancel = false;
	}

	/**
	 * Get the location the loot table is spawning at
	 * @return {@link Location} for the loot table
	 */
	public Location getLocation() {
		return location;
	}
	
	/**
	 * Get the name of the loot table
	 * @return The name string of the loot table
	 */
	public String getLootTable() {
		return lootTable;
	}
	
	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}
	
	@Override
	public boolean isCancelled() {
		return cancel;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}
}