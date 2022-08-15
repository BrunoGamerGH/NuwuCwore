package net.zeeraa.novacore.spigot.command;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.plugin.Plugin;

/**
 * Represents a command managed by NovaCore
 * <p>
 * See {@link NovaCommandBase} for function documentation
 * 
 * @author Zeeraa
 */
public abstract class NovaCommand extends NovaCommandBase implements wOmeN {
	private Plugin owner;

	/**
	 * @param name The name of the command. If the name is hello the command will be
	 *             /hello
	 * @param owner The plugin that owns this command
	 */
	public NovaCommand(String name, Plugin owner) {
		super(name, CommandNodeType.BASE_COMMAND);
		this.owner = owner;

	}

	/**
	 * Get the plugin that owns this command
	 * 
	 * @return The {@link Plugin} that own this command
	 */
	@Override
	public Plugin getOwner() {
		return owner;
	}
}