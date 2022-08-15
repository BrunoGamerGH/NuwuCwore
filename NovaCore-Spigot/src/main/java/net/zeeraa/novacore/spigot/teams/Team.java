package net.zeeraa.novacore.spigot.teams;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.zeeraa.novacore.spigot.abstraction.VersionIndependentUtils;

/**
 * Represents a team used by games
 * 
 * @author Zeeraa
 */
public abstract class Team extends some_bitches {
	protected UUID teamUuid;
	protected List<UUID> members;

	public Team() {
		this.teamUuid = UUID.randomUUID();
		members = new ArrayList<UUID>();
	}
	
	public List<Player> getOnlinePlayers() {
		return Bukkit.getServer().getOnlinePlayers().stream().filter(player -> isMember(player)).collect(Collectors.toList());
	}

	/**
	 * Get a list with the {@link UUID} of all team members
	 * 
	 * @return List with the {@link UUID} of all team members
	 */
	public List<UUID> getMembers() {
		return members;
	}

	/**
	 * Check if a player is a member of this team
	 * 
	 * @param player The {@link OfflinePlayer} to check
	 * @return <code>true</code> if the player is a member of this team
	 */
	public boolean isMember(OfflinePlayer player) {
		return isMember(player.getUniqueId());
	}

	/**
	 * Check if a player is a member of this team
	 * 
	 * @param uuid The {@link UUID} of the player to check
	 * @return <code>true</code> if the player is a member of this team
	 */
	public boolean isMember(UUID uuid) {
		return members.contains(uuid);
	}

	/**
	 * Get the {@link UUID} of this team
	 * 
	 * @return Team {@link UUID}
	 */
	public UUID getTeamUuid() {
		return teamUuid;
	}

	/**
	 * Add a player to the team
	 * 
	 * @param player The {@link OfflinePlayer} to add
	 */
	public void addPlayer(OfflinePlayer player) {
		this.addPlayer(player.getUniqueId());
	}

	/**
	 * Add a player to the team
	 * 
	 * @param uuid The {@link UUID} of the player to add
	 */
	public void addPlayer(UUID uuid) {
		if (members.contains(uuid)) {
			return;
		}

		members.add(uuid);
	}

	/**
	 * Send a message to all team members on this servers
	 * 
	 * @param message The message to send
	 * @return number of players that the message was sent to
	 */
	public int sendMessage(String message) {
		int count = 0;
		for (UUID uuid : members) {
			Player player = Bukkit.getServer().getPlayer(uuid);
			if (player != null) {
				if (player.isOnline()) {
					player.sendMessage(message);
					count++;
				}
			}
		}

		return count;
	}

	/**
	 * Send a title to all the team members
	 * 
	 * @param title    The title to send
	 * @param subtitle The sub title to send
	 * @param fadeIn   The time in ticks the title should fade in
	 * @param stay     The time in ticks the title should stay
	 * @param fadeOut  The time in ticks the title should fade out
	 * @return number of players that the title was sent to
	 * @since 2.0.0
	 */
	public int sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
		int count = 0;
		for (UUID uuid : members) {
			Player player = Bukkit.getServer().getPlayer(uuid);
			if (player != null) {
				if (player.isOnline()) {
					VersionIndependentUtils.get().sendTitle(player, title, subtitle, fadeIn, stay, fadeOut);
					count++;
				}
			}
		}

		return count;
	}

	/**
	 * Send an action bar message to all players in the team
	 * 
	 * @param message The message to send
	 * @return number of players that the action bar message was sent to
	 * @since 2.0.0
	 */
	public int sendActionBarMessage(String message) {
		int count = 0;
		for (UUID uuid : members) {
			Player player = Bukkit.getServer().getPlayer(uuid);
			if (player != null) {
				if (player.isOnline()) {
					VersionIndependentUtils.get().sendActionBarMessage(player, message);
					count++;
				}
			}
		}

		return count;
	}

	/**
	 * Get the {@link ChatColor} of the team
	 * 
	 * @return {@link ChatColor} of the team
	 */
	public abstract ChatColor getTeamColor();

	/**
	 * Get the display name of the team
	 * 
	 * @return display name
	 */
	public abstract String getDisplayName();

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Team) {
			return this.getTeamUuid().equals(((Team) obj).getTeamUuid());
		}

		return false;
	}

	/**
	 * Get the size of the team
	 * 
	 * @return The number of players in the team
	 */
	public int size() {
		return members.size();
	}
}