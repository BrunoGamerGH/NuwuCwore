package net.zeeraa.novacore.spigot.gameengine.module.modules.game.messages;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import net.zeeraa.novacore.spigot.teams.Team;

/**
 * Used to create custom team elimination messages
 * 
 * @author Zeeraa
 */
public interface TeamEliminationMessage extends wOmeN {
	/**
	 * Show the team elimination message
	 * 
	 * @param team      The {@link Team} that was eliminated
	 * @param placement The placement of the player
	 */
	public abstract void showTeamEliminatedMessage(Team team, int placement);
}