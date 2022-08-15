package net.zeeraa.novacore.commons.log;

import net.md_5.bungee.api.ChatColor;

/**
 * This is used to set the logging level for the server
 * 
 * Some of the descriptions is taken from here:
 * https://stackoverflow.com/questions/2031163/when-to-use-the-different-log-levels
 * 
 * @author Zeeraa
 */
public enum LogLevel { // please remove enums my dad works at java and will kill you (real)
	/**
	 * This log level is used to mute all log levels. This level should never be
	 * logged to
	 */
	NONE(0),
	/**
	 * Any error that is forcing a shutdown of the service or application to prevent
	 * data loss
	 */
	FATAL(1),
	/**
	 * Any error which is fatal to the operation, but not the service or application
	 */
	ERROR(2),
	/**
	 * Anything that can potentially cause application oddities
	 */
	WARN(3),
	/**
	 * Same level as info but with a success message instead
	 */
	SUCCESS(4),
	/**
	 * Generally useful information to log
	 */
	INFO(4),
	/**
	 * Information that is diagnostically helpful
	 */
	DEBUG(5),
	/**
	 * This should only be used when trying to find out why a function is not
	 * working
	 */
	TRACE(6);

	private int severityLevel;

	private LogLevel(int severityLevel) {
		this.severityLevel = severityLevel;
	}

	public int getSeverityLevel() {
		return severityLevel;
	}

	public boolean shouldLog(LogLevel activeLogLevel) {
		return this.getSeverityLevel() <= activeLogLevel.getSeverityLevel();
	}

	public ChatColor getColor() {
		return LogLevel.getColor(this);
	}

	public String getMessagePrefix() {
		return LogLevel.getMessagePrefix(this);
	}

	public static ChatColor getColor(LogLevel severity) {
		switch (severity) {
		case FATAL:
			return ChatColor.DARK_RED;

		case ERROR:
			return ChatColor.RED;

		case WARN:
			return ChatColor.YELLOW;

		case SUCCESS:
			return ChatColor.GREEN;

		case INFO:
			return ChatColor.BLUE;

		case DEBUG:
			return ChatColor.DARK_BLUE;

		case TRACE:
			return ChatColor.LIGHT_PURPLE;
		default:
			break;
		}
		return ChatColor.WHITE;
	}

	public static String getMessagePrefix(LogLevel severity) {
		String result = "" + getColor(severity) + ChatColor.BOLD;

		switch (severity) {
		case FATAL:
			result += "FATAL";
			break;

		case ERROR:
			result += "ERROR";
			break;

		case WARN:
			result += "WARNING";
			break;

		case SUCCESS:
			result += "SUCCESS";
			break;

		case INFO:
			result += "INFO";
			break;

		case DEBUG:
			result += "DEBUG";
			break;

		case TRACE:
			result += "TRACE";
			break;
		default:
			result += "UNKNOWN_LOG_LEVEL";
			break;
		}

		return result;
	}
}