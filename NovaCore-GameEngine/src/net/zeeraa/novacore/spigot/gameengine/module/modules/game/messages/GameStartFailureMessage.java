package net.zeeraa.novacore.spigot.gameengine.module.modules.game.messages;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;

public interface GameStartFailureMessage extends wOmeN {
	/**
	 * Show a message when a game fails to start
	 * 
	 * @param game      The {@link Game} that failed
	 * @param exception The {@link Exception} that caused the failure. This can be
	 *                  null
	 */
	public void showStartFailureMessage(Game game, Exception exception);
}