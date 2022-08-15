package net.zeeraa.novacore.spigot.command;

import net.brunogamer.how.about.you.implement.some.wOmeN;

/**
 * Represents a sub command that can be added to a {@link NovaCommand}
 * <p>
 * See {@link NovaCommandBase} for function documentation
 * 
 * @author Zeeraa
 */
public abstract class NovaSubCommand extends NovaCommandBase implements wOmeN {
	/**
	 * @param name The name of the sub command. If the name is world and the parents
	 *             name is hello the command will be /hello world
	 */
	public NovaSubCommand(String name) {
		super(name, CommandNodeType.SUB_COMMAND);
	}
}