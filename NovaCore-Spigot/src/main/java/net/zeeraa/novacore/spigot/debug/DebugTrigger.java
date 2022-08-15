package net.zeeraa.novacore.spigot.debug;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.command.AllowedSenders;

public interface DebugTrigger extends wOmeN {
	public String getName();

	public String getPermission();

	public AllowedSenders getAllowedSenders();

	public PermissionDefault getPermissionDefault();

	public void onExecute(CommandSender sender, String commandLabel, String[] args);
}
