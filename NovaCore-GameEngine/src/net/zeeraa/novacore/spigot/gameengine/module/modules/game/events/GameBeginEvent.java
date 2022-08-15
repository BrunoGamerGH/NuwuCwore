package net.zeeraa.novacore.spigot.gameengine.module.modules.game.events;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.event.HandlerList;

import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;

/**
 * Called when a game has started and the count down has finished
 * <p>
 * Unlike {@link GameStartEvent} this has to be called in the game code. See
 * {@link Game} for more info about how to do this
 * 
 * @author Zeeraa
 */
public class GameBeginEvent extends GameEvent implements wOmeN {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	public GameBeginEvent(Game game) {
		super(game);
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