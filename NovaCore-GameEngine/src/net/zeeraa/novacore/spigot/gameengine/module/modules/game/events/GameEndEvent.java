package net.zeeraa.novacore.spigot.gameengine.module.modules.game.events;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.event.HandlerList;

import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.GameEndReason;

/**
 * Called when a game is starting
 * 
 * @author Zeeraa
 */
public class GameEndEvent extends GameEvent implements wOmeN {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	private GameEndReason reason;

	public GameEndEvent(Game game, GameEndReason reason) {
		super(game);
		this.reason = reason;
	}

	/**
	 * Get the reason why the game ended
	 * 
	 * @return {@link GameEndReason}
	 */
	public GameEndReason getReason() {
		return reason;
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