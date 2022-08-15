package net.zeeraa.novacore.spigot.gameengine.command.commands.game.eliminateplayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.GameManager;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.elimination.PlayerEliminationReason;

public class NovaCoreSubCommandGameEliminatePlayer extends NovaSubCommand implements wOmeN {
	public NovaCoreSubCommandGameEliminatePlayer() {
		super("eliminateplayer");

		this.setDescription("Eliminate a player");
		this.setPermission("novacore.command.game.eliminateplayer");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		this.setPermissionDescription("Access to the game eliminateplayer command");

		this.addHelpSubCommand();

		this.setFilterAutocomplete(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (GameManager.getInstance().hasGame()) {
			if (GameManager.getInstance().getActiveGame().isRunning()) {
				if (args.length > 0) {
					UUID foundUuid = null;

					for (UUID uuid : GameManager.getInstance().getActiveGame().getPlayers()) {
						if (uuid.toString().equalsIgnoreCase(args[0])) {
							foundUuid = uuid;
							break;
						}
					}

					if (foundUuid != null) {
						GameManager.getInstance().getActiveGame().eliminatePlayer(Bukkit.getServer().getOfflinePlayer(foundUuid), null, PlayerEliminationReason.COMMAND);
						sender.sendMessage(ChatColor.GREEN + "Player with uuid " + ChatColor.AQUA + foundUuid.toString() + ChatColor.GREEN + " was eliminated");
					} else {
						Player player = Bukkit.getServer().getPlayer(args[0]);
						if (player != null) {
							if (GameManager.getInstance().getActiveGame().getPlayers().contains(player.getUniqueId())) {
								GameManager.getInstance().getActiveGame().eliminatePlayer(player, null, PlayerEliminationReason.COMMAND);
								sender.sendMessage(ChatColor.AQUA + player.getName() + ChatColor.GREEN + " was eliminated");
							} else {
								sender.sendMessage(ChatColor.RED + "That player is not in the game");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "That player could not be found. If they are offline you can try" + ChatColor.AQUA + " /game eliminateofflineplayer <Name>");
						}
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Please provide a player");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "No game running");
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
				for (UUID uuid : GameManager.getInstance().getActiveGame().getPlayers()) {
					result.add(uuid.toString());

					Player player = Bukkit.getServer().getPlayer(uuid);
					if (player != null) {
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