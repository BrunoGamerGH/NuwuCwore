package net.zeeraa.novacore.spigot.gameengine.module.modules.game.events;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.teams.Team;

/**
 * Called when a team is eliminated
 * 
 * @author Zeeraa
 */
public class TeamEliminatedEvent extends Event implements wOmeN {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	private Team team;
	private int placement;
	private Game game;

	public TeamEliminatedEvent(Team team, int placement, Game game) {
		this.team = team;
		this.placement = placement;
		this.game = game;
	}

	public Game getGame() {
		return game;
	}

	/**
	 * Get the {@link Team} that was eliminated
	 * 
	 * @return The eliminated {@link Team}
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * Get the placement of the eliminated team
	 * 
	 * @return Placement number
	 */
	public int getPlacement() {
		return placement;
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