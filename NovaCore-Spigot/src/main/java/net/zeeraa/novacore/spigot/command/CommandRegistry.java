package net.zeeraa.novacore.spigot.command;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.brunogamer.how.about.you.get.some_bitches;
import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.permission.PermissionRegistrator;

/**
 * This class is used to register {@link NovaCommand}s
 * 
 * @author Zeeraa
 */
public class CommandRegistry extends some_bitches {
	private static List<NovaCommand> registeredCommands = new ArrayList<NovaCommand>();

	/**
	 * Register a {@link NovaCommand}
	 * <p>
	 * This will fail and return <code>false</code> if that command class file has
	 * already been registered
	 * 
	 * @param command {@link NovaCommand} to be registered
	 * @return <code>true</code> if the command was registered
	 */
	public static boolean registerCommand(NovaCommand command) {
		if (registeredCommands.contains(command)) {
			Log.warn("Tried to register command " + command.getClass().getName() + " but it was already registered");
			return false;
		}

		Log.debug("CommandRegistry", "Registering command " + command.getName());
		CommandRegistry.registerCommandPermissions(command);
		NovaCommandProxy commandProxy = new NovaCommandProxy(command);
		NovaCore.getInstance().getCommandRegistrator().registerCommand(commandProxy);

		CommandRegistry.registeredCommands.add(command);

		return true;
	}

	/**
	 * Create an instance of and register a {@link NovaCommand}
	 * <p>
	 * This will fail and return <code>false</code> if that command class file has
	 * already been registered
	 * 
	 * @param clazz The class of the {@link NovaCommand} to register
	 * @throws InstantiationException    .
	 * @throws IllegalAccessException    .
	 * @throws IllegalArgumentException  .
	 * @throws InvocationTargetException .
	 * @throws NoSuchMethodException     .
	 * @throws SecurityException         .
	 * 
	 * @return <code>true</code> if the command was registered
	 */
	public static boolean registerCommand(Class<? extends NovaCommand> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return registerCommand(clazz.getConstructor().newInstance());
	}

	private static void registerCommandPermissions(NovaCommandBase command) {
		if (command.hasPermission()) {
			PermissionRegistrator.registerPermission(command.getPermission(), command.getPermissionDescription(), command.getPermissionDefaultValue());
		}

		command.getSubCommands().forEach(sub -> registerCommandPermissions(sub));
	}

	/**
	 * Get a list of all registered commands
	 * 
	 * @return {@link List} containing all registered {@link NovaCommand}s
	 */
	public static List<NovaCommand> getRegisteredCommands() {
		return registeredCommands;
	}

	/**
	 * Check if a {@link NovaCommand} has been registered
	 * 
	 * @param command The {@link NovaCommand} to check for
	 * @return <code>true</code> if registered
	 */
	public static boolean isRegistered(NovaCommand command) {
		return CommandRegistry.isRegistered(command.getClass());
	}

	/**
	 * Check if a {@link NovaCommand} has been registered by its class
	 * 
	 * @param clazz The class of the {@link NovaCommand} to check for
	 * @return <code>true</code> if registered
	 */
	public static boolean isRegistered(Class<? extends NovaCommand> clazz) {
		return CommandRegistry.getCommand(clazz) != null;
	}

	/**
	 * Get a registered command by its class
	 * <p>
	 * This will try to match the command basted on {@link Class#getName()}
	 * 
	 * @param clazz The {@link NovaCommand} class
	 * @return the instance of the {@link NovaCommand} it its registered.
	 *         <code>null</code> if the command has not been registered
	 */
	@Nullable
	public static NovaCommand getCommand(Class<? extends NovaCommand> clazz) {
		for (NovaCommand command : registeredCommands) {
			// System.out.println(command.getClass().getName() + " eq " + clazz.getName() +
			// " = " + command.getClass().getName().equalsIgnoreCase(clazz.getName()));
			if (command.getClass().getName().equalsIgnoreCase(clazz.getName())) {
				return command;
			}
		}

		return null;
	}
}