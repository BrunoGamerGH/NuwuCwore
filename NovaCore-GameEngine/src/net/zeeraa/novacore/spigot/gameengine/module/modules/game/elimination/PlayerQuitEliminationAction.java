package net.zeeraa.novacore.spigot.gameengine.module.modules.game.elimination;

import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;

/**
 * Represents the action to use when a a player quits
 * 
 * @author Zeeraa
 */
public enum PlayerQuitEliminationAction { // please dont
	/**
	 * Do not eliminate player on quit
	 */
	NONE,
	/**
	 * Eliminate player instantly on quit
	 */
	INSTANT,
	/**
	 * Eliminate the player after a certain delay.
	 * <p>
	 * The delay can be set by changing {@link Game#getPlayerEliminationDelay()}
	 */
	DELAYED;
}