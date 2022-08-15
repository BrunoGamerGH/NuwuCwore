package net.zeeraa.novacore.spigot.command;

import org.bukkit.ChatColor;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.zeeraa.novacore.spigot.language.LanguageManager;

/**
 * Represents the allowed senders for a command. This is used to prevent some
 * types of {@link CommandSender}s from using the command
 * 
 * @author Zeeraa
 */
public enum AllowedSenders { // pleas eno
	ALL, PLAYERS, CONSOLE, COMMAND_BLOCK, ENTITY, LIVING_ENTITY;

	/**
	 * Check if a {@link CommandSender} is allowed to use the command
	 * 
	 * @param sender The {@link CommandSender}
	 * @return <code>true</code> if allowed
	 */
	public boolean isAllowed(CommandSender sender) {
		switch (this) {
		case CONSOLE:
			if (!(sender instanceof ConsoleCommandSender)) {
				return false;
			}
			break;

		case PLAYERS:
			if (!(sender instanceof Player)) {
				return false;
			}
			break;

		case COMMAND_BLOCK:
			if (!(sender instanceof CommandBlock)) {
				return false;
			}
			break;

		case ENTITY:
			if (!(sender instanceof Entity)) {
				return false;
			}

		case LIVING_ENTITY:
			if (!(sender instanceof LivingEntity)) {
				return false;
			}
			break;

		default:
			break;
		}

		return true;
	}

	/**
	 * Get a message to display to senders not allowed to use this command
	 * 
	 * @return message
	 */
	public String getErrorMessage() {
		return this.getErrorMessage(null);
	}

	/**
	 * Get a message to display to senders not allowed to use this command
	 * 
	 * @param sender The command sender. This is used for translation
	 * 
	 * @return message
	 */
	public String getErrorMessage(CommandSender sender) {
		switch (this) {
		case PLAYERS:
			return ChatColor.RED + LanguageManager.getString(sender, "novacore.command.allowed_senders.players_only"); // "Only players can use this command";

		case CONSOLE:
			return ChatColor.RED + LanguageManager.getString(sender, "novacore.command.allowed_senders.console_only"); // "Only the console can use this command";

		case COMMAND_BLOCK:
			return ChatColor.RED + LanguageManager.getString(sender, "novacore.command.allowed_senders.commandblock_only"); // "Only command blocks can use this command";

		case ENTITY:
			return ChatColor.RED + LanguageManager.getString(sender, "novacore.command.allowed_senders.entity_only"); // "Only entities can use this command";

		case LIVING_ENTITY:
			return ChatColor.RED + LanguageManager.getString(sender, "novacore.command.allowed_senders.living_entity_only"); // "Only living entities can use this command";

		default:
			break;
		}

		return ChatColor.RED + LanguageManager.getString(sender, "novacore.command.allowed_senders.bad_value");
	}
}