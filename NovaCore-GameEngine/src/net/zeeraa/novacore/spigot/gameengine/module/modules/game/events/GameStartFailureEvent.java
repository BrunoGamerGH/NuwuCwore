package net.zeeraa.novacore.spigot.gameengine.module.modules.game.events;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.event.HandlerList;

import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;

/**
 * Called when a game fails to start
 * 
 * @author Zeeraa
 */
public class GameStartFailureEvent extends GameEvent implements wOmeN {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	private Exception exception;

	public GameStartFailureEvent(Game game, Exception exception) {
		super(game);

		this.exception = exception;
	}

	/**
	 * Get the exception that caused the game fail to start
	 * 
	 * @return {@link Exception} or null if the cause was not an exception
	 */
	public Exception getException() {
		return exception;
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