package net.zeeraa.novacore.spigot.gameengine.module.modules.game.mapselector.selectors.guivoteselector;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.GameMapData;

/**
 * Called when a player uses the {@link GUIMapVote} map selector
 * 
 * @author Zeeraa
 */
public class GUIMapSelectorPlayerVotedEvent extends Event implements wOmeN {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	private Player player;
	private boolean forceVote;
	private GameMapData map;

	public GUIMapSelectorPlayerVotedEvent(Player player, boolean forceVote, GameMapData map) {
		this.player = player;
		this.forceVote = forceVote;
		this.map = map;
	}

	/**
	 * Get the player that voted
	 * 
	 * @return The {@link Player}
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Check if the vote was a force vote by an admin
	 * 
	 * @return <code>true</code> if its a force vote
	 */
	public boolean isForceVote() {
		return forceVote;
	}

	/**
	 * Get the map data of the map the player voted for
	 * 
	 * @return {@link GameMapData} for the map
	 */
	public GameMapData getMap() {
		return map;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}