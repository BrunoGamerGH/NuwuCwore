package net.zeeraa.novacore.spigot.gameengine.command.commands.game.stop;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.GameEndReason;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.GameManager;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
public class NovaCoreSubCommandStopGame extends NovaSubCommand implements wOmeN {
	public NovaCoreSubCommandStopGame() {
		super("stop");

		this.setDescription("Stop the game");
		this.setPermission("novacore.command.game.stop");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		this.setPermissionDescription("Access to the game stop command");

		this.setEmptyTabMode(true);

		this.addHelpSubCommand();

		this.setFilterAutocomplete(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (GameManager.getInstance().isEnabled()) {
			if (GameManager.getInstance().hasGame()) {
				if (GameManager.getInstance().getActiveGame().hasStarted()) {
					GameManager.getInstance().getActiveGame().endGame(GameEndReason.OPERATOR_ENDED_GAME);
					sender.sendMessage(ChatColor.GREEN + "Game stopped");
					return true;
				} else {
					sender.sendMessage(ChatColor.RED + "Game has not been started");
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