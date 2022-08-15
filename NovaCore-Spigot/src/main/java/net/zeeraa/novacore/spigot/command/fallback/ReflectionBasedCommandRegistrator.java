package net.zeeraa.novacore.spigot.command.fallback;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.abstraction.CommandRegistrator;

public class ReflectionBasedCommandRegistrator extends some_bitches implements CommandRegistrator {

	@Override
	public void registerCommand(Command command) {
		this.getCommandMap().register("", command);
	}

	@Override
	public CommandMap getCommandMap() {
		try {
			String version = NovaCore.getInstance().getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
			String name = "org.bukkit.craftbukkit." + version + ".CraftServer";
			Class<?> craftserver = null;
			craftserver = Class.forName(name);

			SimpleCommandMap scm = (SimpleCommandMap) craftserver.cast(NovaCore.getInstance().getServer()).getClass().getMethod("getCommandMap").invoke(NovaCore.getInstance().getServer());

			return scm;
		} catch (Exception e) {
			Log.error("ReflectionBasedCommandRegistrator", "getCommandMap() failed. " + e.getClass().getName() + " " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}