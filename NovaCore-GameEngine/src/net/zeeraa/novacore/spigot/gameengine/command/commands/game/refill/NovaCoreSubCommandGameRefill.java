package net.zeeraa.novacore.spigot.gameengine.command.commands.game.refill;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import net.zeeraa.novacore.spigot.module.modules.chestloot.ChestLootManager;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
public class NovaCoreSubCommandGameRefill extends NovaSubCommand implements wOmeN {
	public NovaCoreSubCommandGameRefill() {
		super("refill");

		this.setDescription("Force a chest refill");
		this.setPermission("novacore.command.game.refill");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		this.setPermissionDescription("Access to the game chest refill command");

		this.addHelpSubCommand();

		this.setFilterAutocomplete(true);
		this.setEmptyTabMode(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (ChestLootManager.getInstance().isEnabled()) {
			ChestLootManager.getInstance().refillChests();
			sender.sendMessage(ChatColor.GREEN + "OK");
		} else {
			sender.sendMessage(ChatColor.RED + "ChestLootManager is not enabled");
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