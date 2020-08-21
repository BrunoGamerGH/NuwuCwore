package net.zeeraa.novacore.command;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;

import net.zeeraa.novacore.NovaCore;
import net.zeeraa.novacore.log.Log;

/**
 * This class is used to register {@link NovaCommand}s
 * 
 * @author Zeeraa
 */
public class CommandRegistry {
	/**
	 * Register a {@link NovaCommand}
	 * 
	 * @param command {@link NovaCommand} to be registered
	 */
	public static void registerCommand(NovaCommand command) {
		Log.debug("CommandRegistry", "Registering command " + command.getName());
		registerCommandPermissions(command);
		NovaCommandProxy commandProxy = new NovaCommandProxy(command);
		NovaCore.getInstance().getCommandRegistrator().registerCommand(commandProxy);
	}

	private static void registerCommandPermissions(NovaCommandBase command) {
		if (command.hasPermission()) {
			Log.trace("CommandRegistry", "Registering permission " + command.getPermission());
			if (Bukkit.getServer().getPluginManager().getPermission(command.getPermission()) == null) {
				Bukkit.getServer().getPluginManager().addPermission(new Permission(command.getPermission(), command.getPermissionDescription(), command.getPermissionDefaultValue()));
			}
		}

		for (NovaCommandBase subCommand : command.getSubCommands()) {
			registerCommandPermissions(subCommand);
		}
	}
}