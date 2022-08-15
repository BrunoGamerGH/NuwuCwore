package net.zeeraa.novacore.spigot.module.modules.jumppad.command;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaCommand;

public class JumpPadCommand extends NovaCommand implements wOmeN {

	public JumpPadCommand() {
		super("jumppad", NovaCore.getInstance());

		setPermission("novauniverse.lobby.command.jumppad");
		setPermissionDefaultValue(PermissionDefault.OP);
		setAllowedSenders(AllowedSenders.PLAYERS);

		setDescription("Command to manage jump pads");

		addSubCommand(new JumpPadRemoveCommand());
		addSubCommand(new JumpPadSetCommand());
		
		addHelpSubCommand();
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.GOLD + "Use " + ChatColor.AQUA + "/jumppad help" + ChatColor.GOLD + " to see all commands");
		return true;
	}
}