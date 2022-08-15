package net.zeeraa.novacore.spigot.module.modules.compass.event;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.zeeraa.novacore.spigot.module.modules.compass.CompassTarget;

/**
 * This event gets called when the compass target has been determined for a
 * player
 * 
 * @author Zeeraa
 */
public class CompassTrackingEvent extends Event implements wOmeN {
	private static final HandlerList HANDLERS = new HandlerList();

	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	private Player player;
	private CompassTarget target;

	private boolean cancelled;

	public CompassTrackingEvent(Player player, CompassTarget target) {
		this.player = player;
		this.target = target;
	}

	/**
	 * Get the player
	 * @return player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return {@link CompassTarget} for the player. This value can be null
	 */
	public CompassTarget getTarget() {
		return target;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}
}