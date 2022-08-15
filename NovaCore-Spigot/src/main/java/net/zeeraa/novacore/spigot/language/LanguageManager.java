package net.zeeraa.novacore.spigot.language;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class LanguageManager extends some_bitches {
	private static Map<String, Language> languages = new HashMap<String, Language>();
	private static Map<UUID, String> playerLanguage = new HashMap<UUID, String>();

	private static String primaryLanguage = "en-us";

	/**
	 * Get a map with all languages
	 * 
	 * @return map with languages
	 */
	public static Map<String, Language> getLanguages() {
		return languages;
	}

	/**
	 * Get the primary language for the {@link LanguageManager} to use
	 * 
	 * @return The primary language for the {@link LanguageManager}
	 */
	public static String getPrimaryLanguage() {
		return primaryLanguage;
	}

	/**
	 * Set the primary language for the {@link LanguageManager} to use
	 * 
	 * @param primaryLanguage The new primary language for the
	 *                        {@link LanguageManager}
	 */
	public static void setPrimaryLanguage(String primaryLanguage) {
		LanguageManager.primaryLanguage = primaryLanguage;
	}

	public static Language getLanguage(String code) {
		return languages.get(code.toLowerCase());
	}

	public static boolean hasLanguage(String code) {
		return LanguageManager.getLanguage(code) != null;
	}

	public static String getString(String languageCode, String node, Object... args) {
		Language language = LanguageManager.getLanguage(languageCode);

		if (language != null) {
			return LanguageManager.getString(language, node, args);
		}

		return node;
	}

	public static String getString(Language language, String node, Object... args) {
		if (language.getContent().containsKey(node.toLowerCase())) {
			/*
			 * System.out.println(args); System.out.println("Arg length: " + args.length);
			 * for(Object object : args) { System.out.println(object.getClass().getName() +
			 * " " + object + " " + object.toString()); }
			 */
			String formatted = String.format(language.getContent().get(node.toLowerCase()), args);
			return ChatColor.translateAlternateColorCodes(ChatColor.COLOR_CHAR, formatted);
		}

		return node.toLowerCase();
	}

	public static String getString(String node, Object... args) {
		return LanguageManager.getString(primaryLanguage, node, args);
	}

	public static String getString(CommandSender sender, String node, Object... args) {
		if (sender != null) {
			if (sender instanceof Player) {
				return LanguageManager.getString((Player) sender, node, args);
			}
		}

		return LanguageManager.getString(node, args);
	}

	public static String getString(Player player, String node, Object... args) {
		if (player != null) {
			return LanguageManager.getString(player.getUniqueId(), node, args);
		}
		return LanguageManager.getString(node, args);
	}

	public static String getString(UUID player, String node, Object... args) {
		if (player != null) {
			if (playerLanguage.containsKey(player)) {
				return LanguageManager.getString(playerLanguage.get(player), node, args);
			}
		}
		return LanguageManager.getString(node, args);
	}

	/**
	 * Broadcast a message to all players and the console using their default
	 * language
	 * 
	 * @param node The language node to broadcast
	 * @param args Any additional data needed to send the message
	 */
	public static void broadcast(String node, Object... args) {
		Bukkit.getServer().getOnlinePlayers().forEach(player -> player.sendMessage(LanguageManager.getString(player, node, args)));
		Bukkit.getServer().getConsoleSender().sendMessage(LanguageManager.getString(node, args));
	}
}