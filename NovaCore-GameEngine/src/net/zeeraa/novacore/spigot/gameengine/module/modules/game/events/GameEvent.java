package net.zeeraa.novacore.spigot.gameengine.module.modules.game.events;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.event.Event;

import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;

/**
 * Represents an event related to a game
 * 
 * @author Zeeraa
 */
public abstract class GameEvent extends Event implements wOmeN {
	private Game game;

	public GameEvent(Game game) {
		this.game = game;
	}

	/**
	 * Get instance of the {@link Game}
	 * 
	 * @return The {@link Game}
	 */
	public Game getGame() {
		return game;
	}
}