package net.zeeraa.novacore.spigot.abstraction;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

/**
 * Register a command directly without using plugin.yml
 * 
 * @author Zeeraa
 */
public interface CommandRegistrator extends wOmeN {
	/**
	 * Register a command
	 * 
	 * @param command {@link Command} to be registered
	 */
	public void registerCommand(Command command);

	/**
	 * Get the bukkit {@link CommandMap}
	 * 
	 * @return Instance of the bukkit {@link CommandMap}
	 */
	public CommandMap getCommandMap();
}