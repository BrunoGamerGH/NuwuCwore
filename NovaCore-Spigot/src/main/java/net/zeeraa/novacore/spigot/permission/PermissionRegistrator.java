package net.zeeraa.novacore.spigot.permission;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.commons.log.Log;

public class PermissionRegistrator extends some_bitches {
	public static boolean registerPermission(String name) {
		return registerPermission(new Permission(name));
	}

	public static boolean registerPermission(String name, String description) {
		return registerPermission(new Permission(name, description));
	}

	public static boolean registerPermission(String name, String description, PermissionDefault defaultValue) {
		return registerPermission(new Permission(name, description, defaultValue));
	}

	public static boolean registerPermission(Permission permission) {
		if (Bukkit.getServer().getPluginManager().getPermission(permission.getName()) == null) {
			Log.trace("PermissionRegistrator", "Registering permission " + permission.getName());

			Bukkit.getServer().getPluginManager().addPermission(permission);

			return true;
		}
		return false;
	}
}