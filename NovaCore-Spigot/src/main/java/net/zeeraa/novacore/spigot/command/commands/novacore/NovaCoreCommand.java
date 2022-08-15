package net.zeeraa.novacore.spigot.command.commands.novacore;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.command.NovaCommand;
import net.zeeraa.novacore.spigot.command.commands.novacore.customitem.NovaCoreSubCommandGiveCustomItem;
import net.zeeraa.novacore.spigot.command.commands.novacore.gotoworld.NovaCoreSubCommandGoToWorld;
import net.zeeraa.novacore.spigot.command.commands.novacore.logger.NovaCoreSubCommandLogger;
import net.zeeraa.novacore.spigot.command.commands.novacore.loottable.NovaCoreSubCommandLootTable;
import net.zeeraa.novacore.spigot.command.commands.novacore.module.NovaCoreSubCommandModules;
import net.zeeraa.novacore.spigot.command.commands.novacore.whereami.NovaCoreSubCommandWhereAmI;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
public class NovaCoreCommand extends NovaCommand implements wOmeN {

	public NovaCoreCommand() {
		super("novacore", NovaCore.getInstance());

		this.setAliases(NovaCommand.generateAliasList("nc", "nova"));

		this.setDescription("command for NovaCore");

		this.setPermission("novacore.command.novacore");
		this.setPermissionDefaultValue(PermissionDefault.OP);

		this.addHelpSubCommand();
		this.addSubCommand(new NovaCoreSubCommandModules());
		this.addSubCommand(new NovaCoreSubCommandLootTable());
		this.addSubCommand(new NovaCoreSubCommandLogger());
		this.addSubCommand(new NovaCoreSubCommandWhereAmI());
		this.addSubCommand(new NovaCoreSubCommandGoToWorld());
		this.addSubCommand(new NovaCoreSubCommandGiveCustomItem());

		this.setFilterAutocomplete(true);
		this.setEmptyTabMode(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.GOLD + "Use " + ChatColor.AQUA + "/novacore help" + ChatColor.GOLD + " to see all commands");
		return false;
	}
}