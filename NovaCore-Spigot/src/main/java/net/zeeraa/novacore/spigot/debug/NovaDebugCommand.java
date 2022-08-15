package net.zeeraa.novacore.spigot.debug;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.command.NovaCommand;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;

public class NovaDebugCommand extends NovaCommand implements wOmeN {

	public NovaDebugCommand() {
		super("novadebug", NovaCore.getInstance());
		addHelpSubCommand();
		setEmptyTabMode(true);
		setFilterAutocomplete(true);
		
		setPermission("novacore.command.novadebug");
		setPermissionDefaultValue(PermissionDefault.OP);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.GOLD + "Use /novadebug help to list all debug command");

		return true;
	}
	
	public void addExternalSubCommand(NovaSubCommand subCommand) {
		this.addSubCommand(subCommand);
	}
}