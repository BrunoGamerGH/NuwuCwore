package net.zeeraa.novacore.spigot.gameengine.command.commands.game.addplayer;

import java.util.ArrayList;
import java.util.List;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.GameManager;

public class NovaCoreSubCommandGameAddPlayer extends NovaSubCommand implements wOmeN {
	public NovaCoreSubCommandGameAddPlayer() {
		super("addplayer");

		this.setDescription("Add a player to the game");
		this.setPermission("novacore.command.game.addplayer");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		this.setPermissionDescription("Access to the game addplayer command");

		this.addHelpSubCommand();

		this.setFilterAutocomplete(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (GameManager.getInstance().hasGame()) {
			if (GameManager.getInstance().getActiveGame().isRunning()) {
				if (args.length > 0) {
					Player player = Bukkit.getServer().getPlayer(args[0]);
					if (player != null) {
						if (player.isOnline()) {
							if (!GameManager.getInstance().getActiveGame().getPlayers().contains(player.getUniqueId())) {
								GameManager.getInstance().getActiveGame().addPlayer(player);
								sender.sendMessage(ChatColor.GREEN + "Player added");
							} else {
								sender.sendMessage(ChatColor.RED + "That player is already in the game");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "That player could not be found");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "That player could not be found");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Please provide a player");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "No game is running");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "No game has been loaded");
		}

		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		List<String> result = new ArrayList<String>();
		if (GameManager.getInstance().hasGame()) {
			if (GameManager.getInstance().getActiveGame().isRunning()) {
				for (Player player : Bukkit.getServer().getOnlinePlayers()) {
					if (!GameManager.getInstance().getActiveGame().getPlayers().contains(player.getUniqueId())) {
						result.add(player.getName());
					}
				}
			}
		}

		return result;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}