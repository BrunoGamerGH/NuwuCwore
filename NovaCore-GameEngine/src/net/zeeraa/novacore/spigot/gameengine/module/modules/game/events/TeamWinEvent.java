package net.zeeraa.novacore.spigot.gameengine.module.modules.game.events;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.teams.Team;

/**
 * Called when a team wins a game.
 * <p>
 * This will not be called by NovaCore if {@link Game#autoEndGame()} is disabled
 * 
 * @author Zeeraa
 */
public class TeamWinEvent extends Event implements wOmeN {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	private Team team;

	public TeamWinEvent(Team team) {
		this.team = team;
	}

	/**
	 * Get the {@link Team} that won the game
	 * 
	 * @return The winning {@link Team}
	 */
	public Team getTeam() {
		return team;
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