package net.zeeraa.novacore.spigot.mapdisplay.command;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaCommand;
import net.zeeraa.novacore.spigot.mapdisplay.command.subcommand.MDCreateSubCommand;
import net.zeeraa.novacore.spigot.mapdisplay.command.subcommand.MDDebugFrames;
import net.zeeraa.novacore.spigot.mapdisplay.command.subcommand.MDDeleteSubCommand;
import net.zeeraa.novacore.spigot.mapdisplay.command.subcommand.MDListSubCommand;
import net.zeeraa.novacore.spigot.mapdisplay.command.subcommand.MDSetImageSubCommand;

public class MapDisplayCommand extends NovaCommand implements wOmeN {

	public MapDisplayCommand() {
		super("mapdisplay", NovaCore.getInstance());

		setAllowedSenders(AllowedSenders.PLAYERS);
		setPermission("novacore.command.mapdisplay");
		setPermissionDefaultValue(PermissionDefault.OP);
		setEmptyTabMode(true);
		setFilterAutocomplete(true);

		addSubCommand(new MDListSubCommand());
		addSubCommand(new MDCreateSubCommand());
		addSubCommand(new MDDeleteSubCommand());
		addSubCommand(new MDSetImageSubCommand());
		addSubCommand(new MDDebugFrames());

		addHelpSubCommand();
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.GOLD + "Use " + ChatColor.AQUA + "/mapdisplay help" + ChatColor.GOLD + " for help");
		return true;
	}
}