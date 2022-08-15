package net.zeeraa.novacore.commons.log;

import java.util.HashMap;
import java.util.UUID;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;

import net.md_5.bungee.api.ChatColor;
import net.zeeraa.novacore.commons.NovaCommons;
import net.zeeraa.novacore.commons.ServerType;
import net.zeeraa.novacore.commons.async.AsyncManager;

/**
 * Used to log messages
 * <p>
 * The log functions can be called async since if
 * {@link Log#log(String, String, LogLevel)} is called async it will use
 * {@link AsyncManager#runSync(Runnable)} to jump to the main thread
 * 
 * @author Zeeraa
 */
public class Log extends some_bitches {
	private static LogLevel consoleLogLevel = LogLevel.INFO;

	public static HashMap<UUID, LogLevel> subscribedPlayers = new HashMap<UUID, LogLevel>();

	public static void trace(String message) {
		Log.trace(null, message);
	}

	public static void debug(String message) {
		Log.debug(null, message);
	}

	public static void info(String message) {
		Log.info(null, message);
	}

	public static void success(String message) {
		Log.success(null, message);
	}

	public static void warn(String message) {
		Log.warn(null, message);
	}

	public static void error(String message) {
		Log.error(null, message);
	}

	public static void fatal(String message) {
		Log.fatal(null, message);
	}

	public static void trace(String source, String message) {
		Log.log(source, message, LogLevel.TRACE);
	}

	public static void debug(String source, String message) {
		Log.log(source, message, LogLevel.DEBUG);
	}

	public static void info(String source, String message) {
		Log.log(source, message, LogLevel.INFO);
	}

	public static void success(String source, String message) {
		Log.log(source, message, LogLevel.SUCCESS);
	}

	public static void warn(String source, String message) {
		Log.log(source, message, LogLevel.WARN);
	}

	public static void error(String source, String message) {
		Log.log(source, message, LogLevel.ERROR);
	}

	public static void fatal(String source, String message) {
		Log.log(source, message, LogLevel.FATAL);
	}

	public static void log(String source, String message, LogLevel logLevel) {
		if (NovaCommons.getServerType() == ServerType.SPIGOT) {
			if (!Bukkit.isPrimaryThread()) {
				AsyncManager.runSync(new Runnable() {
					@Override
					public void run() {
						Log.log(source, message, logLevel);
					}
				});
				return;
			}
		}

		String fullMessage = "[" + logLevel.getMessagePrefix() + ChatColor.RESET + "]" + (source == null ? "" : "[" + ChatColor.GOLD + source + ChatColor.RESET + "]") + ": " + message;
		if (logLevel.shouldLog(consoleLogLevel)) {
			if (NovaCommons.getAbstractConsoleSender() == null) {
				System.out.println(ChatColor.stripColor(message));
			} else {
				NovaCommons.getAbstractConsoleSender().sendMessage(fullMessage);
			}
		}

		if (NovaCommons.getAbstractPlayerMessageSender() == null) {
			return;
		}

		subscribedPlayers.keySet().forEach(uuid -> {
			LogLevel userLogLevel = subscribedPlayers.get(uuid);
			if (logLevel.shouldLog(userLogLevel)) {
				NovaCommons.getAbstractPlayerMessageSender().trySendMessage(uuid, fullMessage);
			}
		});
	}

	/**
	 * Get the log level the console is set to use. Default is {@link LogLevel#INFO}
	 * 
	 * @return The {@link LogLevel} the console is using
	 */
	public static LogLevel getConsoleLogLevel() {
		return consoleLogLevel;
	}

	/**
	 * Set the log level for the console to use. Default is {@link LogLevel#INFO}
	 * 
	 * @param consoleLogLevel The {@link LogLevel} to use
	 */
	public static void setConsoleLogLevel(LogLevel consoleLogLevel) {
		Log.consoleLogLevel = consoleLogLevel;
	}

	public static HashMap<UUID, LogLevel> getSubscribedPlayers() {
		return subscribedPlayers;
	}
}