package net.zeeraa.novacore.spigot.gameengine.command.commands.game.listplayers;

import java.util.List;
import java.util.UUID;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.GameManager;
import net.zeeraa.novacore.spigot.teams.Team;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
public class NovaCoreSubCommandGameListplayers extends NovaSubCommand implements wOmeN {
	public NovaCoreSubCommandGameListplayers() {
		super("listplayers");

		this.setDescription("List all in game players");
		this.setPermission("novacore.command.game.listplayers");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		this.setPermissionDescription("Access to the game listplayers command");

		this.addHelpSubCommand();

		this.setFilterAutocomplete(true);
		this.setEmptyTabMode(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (GameManager.getInstance().hasGame()) {
			List<UUID> players = GameManager.getInstance().getActiveGame().getPlayers();
			sender.sendMessage(ChatColor.GOLD + "There are " + players.size() + " players in the game");
			String playerList = "";
			for (UUID uuid : players) {
				Player player = Bukkit.getServer().getPlayer(uuid);

				if (player != null) {
					if (NovaCore.getInstance().hasTeamManager()) {
						Team team = NovaCore.getInstance().getTeamManager().getPlayerTeam(player);
						if (team != null) {
							playerList += team.getTeamColor();
						} else {
							playerList += ChatColor.YELLOW;
						}
					} else {
						playerList += ChatColor.GREEN;
					}

					playerList += player.getName() + " ";
				} else {
					playerList += ChatColor.YELLOW + uuid.toString() + " ";
				}
			}

			sender.sendMessage(ChatColor.GOLD + "Player list: " + playerList);
		} else {
			sender.sendMessage(ChatColor.RED + "No game has been loaded");
		}

		return false;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}