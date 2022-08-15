package net.zeeraa.novacore.spigot.teams;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import net.zeeraa.novacore.spigot.NovaCore;

public abstract class TeamManager extends some_bitches {
	protected List<Team> teams;

	public TeamManager() {
		this.teams = new ArrayList<Team>();
	}

	/**
	 * Get a list of all {@link Team}s
	 * 
	 * @return List of teams
	 */
	public List<Team> getTeams() {
		return teams;
	}

	public Team getPlayerTeam(OfflinePlayer player) {
		return this.getPlayerTeam(player.getUniqueId());
	}

	public Team getPlayerTeam(UUID uuid) {
		return teams.stream().filter(team -> team.isMember(uuid)).findFirst().orElse(null);
	}

	public boolean hasTeam(OfflinePlayer player) {
		return getPlayerTeam(player.getUniqueId()) != null;
	}

	public boolean hasTeam(UUID uuid) {
		return getPlayerTeam(uuid) != null;
	}

	public Team getTeamByTeamUUID(UUID uuid) {
		for (Team team : teams) {
			if (team.getTeamUuid().equals(uuid)) {
				return team;
			}
		}

		return null;
	}

	public boolean isInSameTeam(OfflinePlayer player1, OfflinePlayer player2) {
		return this.isInSameTeam(player1.getUniqueId(), player2.getUniqueId());
	}

	public boolean isInSameTeam(UUID uuid1, UUID uuid2) {
		Team team1 = this.getPlayerTeam(uuid1);

		if (team1 != null) {
			return team1.isMember(uuid2);
		}

		return false;
	}

	public abstract boolean requireTeamToJoin(Player player);

	/**
	 * Check if {@link NovaCore} has a team manager
	 * 
	 * @return The result of {@link NovaCore#hasTeamManager()}
	 */
	public static boolean hasTeamManager() {
		return NovaCore.getInstance().hasTeamManager();
	}

	/**
	 * Get the {@link TeamManager} from {@link NovaCore}
	 * 
	 * @return The result of {@link NovaCore#getTeamManager()}
	 */
	public static TeamManager getTeamManager() {
		return NovaCore.getInstance().getTeamManager();
	}
}