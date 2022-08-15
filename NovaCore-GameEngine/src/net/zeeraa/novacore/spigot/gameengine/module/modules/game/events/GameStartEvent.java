package net.zeeraa.novacore.spigot.gameengine.module.modules.game.events;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.event.HandlerList;

import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;

/**
 * Called when a game is starting
 * 
 * @author Zeeraa
 */
public class GameStartEvent extends GameEvent implements wOmeN {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	public GameStartEvent(Game game) {
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