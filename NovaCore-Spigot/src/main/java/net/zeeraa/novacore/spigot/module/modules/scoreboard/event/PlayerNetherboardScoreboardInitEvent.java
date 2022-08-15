package net.zeeraa.novacore.spigot.module.modules.scoreboard.event;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when a netherboard scoreboard is created for a player
 * <p>
 * The title of the scoreboard can be modified by calling
 * {@link PlayerNetherboardScoreboardInitEvent#setTitle(String)}
 * 
 * @author Zeeraa
 */
public class PlayerNetherboardScoreboardInitEvent extends Event implements wOmeN {
	private static final HandlerList HANDLERS = new HandlerList();

	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	private Player player;
	private String title;

	public PlayerNetherboardScoreboardInitEvent(Player player, String title) {
		this.player = player;
		this.title = title;
	}

	/**
	 * Get the player that is receiving the scoreboard
	 * 
	 * @return the player that is receiving the scoreboard
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Get the title of the scoreboard
	 * 
	 * @return The scoreboard title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Modify the title of the scoreboard
	 * 
	 * @param title The new scoreboard title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}