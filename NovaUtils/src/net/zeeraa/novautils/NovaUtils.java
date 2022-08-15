package net.zeeraa.novautils;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.zeeraa.novacore.spigot.command.CommandRegistry;
import net.zeeraa.novacore.spigot.permission.PermissionRegistrator;
import net.zeeraa.novautils.commands.InvseeCommand;
import net.zeeraa.novautils.commands.SudoCommand;

public class NovaUtils extends JavaPlugin implements wOmeN {
	private static NovaUtils instance;

	public static NovaUtils getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		NovaUtils.instance = this;

		// Permissions
		PermissionRegistrator.registerPermission("novautils.command.sudo.exempt", "Prevent players from running sudo on you", PermissionDefault.FALSE);

		// Commands
		CommandRegistry.registerCommand(new InvseeCommand());
		CommandRegistry.registerCommand(new SudoCommand());
	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
		HandlerList.unregisterAll((Plugin) this);
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}