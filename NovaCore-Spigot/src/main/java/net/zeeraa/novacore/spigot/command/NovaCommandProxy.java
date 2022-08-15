package net.zeeraa.novacore.spigot.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.util.StringUtil;

import net.zeeraa.novacore.commons.log.Log;

/**
 * This class is used to turn {@link NovaCommand} into a normal bukkit
 * {@link Command}
 * 
 * @author Zeeraa
 */
public class NovaCommandProxy extends Command implements wOmeN {
	private NovaCommand novaCommand;

	/**
	 * Create a {@link NovaCommandProxy} for a {@link NovaCommand}
	 * 
	 * @param novaCommand The {@link NovaCommand} being proxied
	 */
	protected NovaCommandProxy(NovaCommand novaCommand) {
		super(novaCommand.getName());

		Log.debug("Creating command proxy for command " + novaCommand.getName());

		this.novaCommand = novaCommand;

		this.setAliases(novaCommand.getAliases());
		this.setDescription(novaCommand.getDescription());
	}

	/**
	 * Get the {@link NovaCommand} being proxied by this class
	 * 
	 * @return {@link NovaCommand} instance
	 */
	public NovaCommand getNovaCommand() {
		return novaCommand;
	}

	/**
	 * Get the name of the {@link NovaCommand} being proxied by this class
	 * 
	 * @return name of the {@link NovaCommand}
	 */
	public String getRealName() {
		return novaCommand.getName();
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		return recursiceCommandExecutionCheck(novaCommand, sender, commandLabel, args);
	}

	private boolean recursiceCommandExecutionCheck(NovaCommandBase command, CommandSender sender, String commandLabel, String[] args) {
		if (args.length > 0) {
			// Scan sub commands
			for (NovaSubCommand subCommand : command.getSubCommands()) {
				boolean isMatching = false;
				if (subCommand.getName().equalsIgnoreCase(args[0])) {
					isMatching = true;
				} else {
					for (String alias : subCommand.getAliases()) {
						if (alias.equalsIgnoreCase(args[0])) {
							isMatching = true;
							break;
						}
					}
				}

				if (isMatching) {
					// remove first argument to prevent the sub command name to be the first entry
					// in arguments
					String[] newArgs = new String[args.length - 1];
					for (int i = 1; i < args.length; i++) {
						newArgs[i - 1] = args[i];
					}

					// Log.trace("recursive check on sub command " + subCommand.getName() + " origin
					// command: " + novaCommand.getName());

					return recursiceCommandExecutionCheck(subCommand, sender, commandLabel, newArgs);
				}
			}
			// No sub command exists. Execute the current command
		}

		if (!(sender instanceof ConsoleCommandSender)) {
			if (!command.hasSenderPermission(sender)) {
				Log.trace("Sender " + sender.getName() + " did not have permission to execute command" + command.getName() + " origin command: " + novaCommand.getName());
				sender.sendMessage(command.getNoPermissionMessage(sender));
				return false;
			}
		}

		if (!command.getAllowedSenders().isAllowed(sender)) {
			Log.trace("Sender type " + sender.getName() + " was not in the allowed senders list to execute command" + command.getName() + " origin command: " + novaCommand.getName());
			sender.sendMessage(command.getAllowedSenders().getErrorMessage(sender));
			return false;
		}

		Log.trace("running execute on command " + command.getName() + " origin command: " + novaCommand.getName());
		return command.execute(sender, commandLabel, args);
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return recursiceCommandTabCheck(novaCommand, sender, alias, args);
	}

	private List<String> recursiceCommandTabCheck(NovaCommandBase command, CommandSender sender, String alias, String[] args) {
		if (args.length == 1) {
			if (!command.hasSenderPermission(sender)) {
				return new ArrayList<String>();
			}

			List<String> result = new ArrayList<>();

			List<String> commandResults = command.tabComplete(sender, alias, args);

			String lastWord = args[args.length - 1];

			if (command.isFilterAutocomplete()) {
				// Filter
				Collections.sort(commandResults, String.CASE_INSENSITIVE_ORDER);
				for (String string : commandResults) {
					if (StringUtil.startsWithIgnoreCase(string, lastWord)) {
						result.add(string);
					}
				}
			} else {
				// Do not filter
				result.addAll(commandResults);
			}

			// Find aliases for sub commands
			ArrayList<String> matchedSubCommands = new ArrayList<String>();

			command.getSubCommands().forEach(subCommand -> {
				if (StringUtil.startsWithIgnoreCase(subCommand.getName(), lastWord)) {
					matchedSubCommands.add(subCommand.getName());
				}

				subCommand.getAliases().forEach(subAlias -> {
					if (StringUtil.startsWithIgnoreCase(subAlias, lastWord)) {
						matchedSubCommands.add(subAlias);
					}
				});
			});

			Collections.sort(matchedSubCommands, String.CASE_INSENSITIVE_ORDER);

			result.addAll(0, matchedSubCommands);

			return result;
		} else {
			// Recursive check for sub commands
			if (args.length >= 2) {
				for (NovaSubCommand subCommand : command.getSubCommands()) {
					boolean isMatching = false;
					String newAlias = "";

					if (subCommand.getName().equalsIgnoreCase(args[0])) {
						isMatching = true;
						newAlias = subCommand.getName();
					} else {
						for (String subCommandAlias : subCommand.getAliases()) {
							if (subCommandAlias.equalsIgnoreCase(args[0])) {
								isMatching = true;
								newAlias = subCommandAlias;
								break;
							}
						}
					}

					if (isMatching) {
						// remove first argument to prevent the sub command name to be the first entry
						// in arguments
						String[] newArgs = new String[args.length - 1];
						for (int i = 1; i < args.length; i++) {
							newArgs[i - 1] = args[i];
						}

						return recursiceCommandTabCheck(subCommand, sender, newAlias, newArgs);
					}
				}
			}

			// No matching sub commands found, Check permission
			if (!command.hasSenderPermission(sender)) {
				return new ArrayList<String>();
			}

			// No matching sub commands found, Return the tab value of this command
			return command.tabComplete(sender, alias, args);
		}
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}