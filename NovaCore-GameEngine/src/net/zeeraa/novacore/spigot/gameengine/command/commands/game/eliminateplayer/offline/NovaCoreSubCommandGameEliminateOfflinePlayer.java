package net.zeeraa.novacore.spigot.gameengine.command.commands.game.eliminateplayer.offline;

import java.util.UUID;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.commons.api.novauniverse.NovaUniverseAPI;
import net.zeeraa.novacore.commons.api.novauniverse.callback.IAsyncNameToUUIDCallback;
import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.GameManager;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.elimination.PlayerEliminationReason;

public class NovaCoreSubCommandGameEliminateOfflinePlayer extends NovaSubCommand implements wOmeN {
	public NovaCoreSubCommandGameEliminateOfflinePlayer() {
		super("eliminateofflineplayer");

		this.setDescription("Eliminate a player that is not online");
		this.setPermission("novacore.command.game.eliminateplayer");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		this.setPermissionDescription("Access to the game eliminateplayer command");

		this.addHelpSubCommand();

		this.setEmptyTabMode(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (GameManager.getInstance().hasGame()) {
			if (GameManager.getInstance().getActiveGame().isRunning()) {
				if (args.length > 0) {
					sender.sendMessage(ChatColor.AQUA + "Please wait while we try to fetch the uuid of that player from the mojang api...");

					try {
						NovaUniverseAPI.nameToUUIDAsync(args[0], new IAsyncNameToUUIDCallback() {
							@Override
							public boolean isCancelled() {
								return false;
							}

							@Override
							public void setCancelled(boolean cancel) {

							}

							@Override
							public void onResult(UUID uuid, Exception exception) {
								if (uuid != null) {
									if (GameManager.getInstance().getActiveGame().getPlayers().contains(uuid)) {
										GameManager.getInstance().getActiveGame().eliminatePlayer(Bukkit.getServer().getOfflinePlayer(uuid), null, PlayerEliminationReason.COMMAND);
										sender.sendMessage(ChatColor.GREEN + "Player with uuid " + ChatColor.AQUA + uuid + ChatColor.GREEN + " was eliminated");
									} else {
										sender.sendMessage(ChatColor.RED + "That player is not in game");
									}
								} else {
									if (exception != null) {
										exception.printStackTrace();
										Log.error("EliminateOfflinePlayerCommand", "An exception occured while trying to fetch the player id from the novauniverse api. " + exception.getClass().getName() + " " + exception.getMessage());
										sender.sendMessage(ChatColor.DARK_RED + "An exception occured while trying to fetch the player id from the novauniverse api. " + exception.getClass().getName() + " " + exception.getMessage());
									} else {
										sender.sendMessage(ChatColor.RED + "Could not find a player named " + args[0]);
									}
								}
							}
						});
					} catch (IllegalArgumentException e) {
						sender.sendMessage(ChatColor.RED + "Please provide a valid username");
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
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}