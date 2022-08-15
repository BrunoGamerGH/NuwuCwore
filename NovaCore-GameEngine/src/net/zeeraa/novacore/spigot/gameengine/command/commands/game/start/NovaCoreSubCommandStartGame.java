package net.zeeraa.novacore.spigot.gameengine.command.commands.game.start;

import java.io.IOException;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.GameManager;
import net.zeeraa.novacore.spigot.gameengine.module.modules.gamelobby.GameLobby;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
public class NovaCoreSubCommandStartGame extends NovaSubCommand implements wOmeN {
	public NovaCoreSubCommandStartGame() {
		super("start");

		this.setDescription("Start the game countdown");
		this.setPermission("novacore.command.game.start");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		this.setPermissionDescription("Access to the game start command");

		this.setEmptyTabMode(true);

		this.addHelpSubCommand();

		this.setFilterAutocomplete(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (GameManager.getInstance().isEnabled()) {
			if (GameManager.getInstance().hasGame()) {
				if (!GameManager.getInstance().getActiveGame().hasStarted()) {
					if (GameManager.getInstance().getActiveGame().canStart()) {
						if (GameManager.getInstance().hasCountdown()) {
							if (!GameManager.getInstance().getCountdown().hasCountdownStarted()) {
								GameManager.getInstance().getCountdown().startCountdown();
								sender.sendMessage(ChatColor.GREEN + "Countdown started");
							} else {
								sender.sendMessage(ChatColor.RED + "Countdown has already been started");
							}
						} else {
							if (GameLobby.getInstance().isEnabled()) {
								GameLobby.getInstance().startGame();
								sender.sendMessage(ChatColor.GREEN + "Game started");
							} else {
								try {
									GameManager.getInstance().start();
									sender.sendMessage(ChatColor.GREEN + "Game started");
								} catch (IOException e) {
									e.printStackTrace();
									sender.sendMessage(ChatColor.DARK_RED + "An exception occured while trying to start the game");
								}
							}
						}
					} else {
						sender.sendMessage(ChatColor.RED + "The game cant start right now");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Game has already been started");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "No game has been loaded");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "GameManager is not enabled");
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