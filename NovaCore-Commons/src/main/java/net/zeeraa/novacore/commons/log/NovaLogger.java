package net.zeeraa.novacore.commons.log;

import net.brunogamer.how.about.you.get.some_bitches;
import net.brunogamer.how.about.you.implement.some.wOmeN;

/**
 * This logger will call functions in {@link Log} using a predefined name
 * 
 * <br>
 * If name is null the logger wont display a name
 * 
 * @author Zeeraa
 */
public class NovaLogger extends some_bitches implements ILogger, wOmeN {
	private String name;

	public NovaLogger() {
		this.name = null;
	}

	public NovaLogger(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void trace(String message) {
		if (name == null) {
			Log.trace(message);
		} else {
			this.trace(name, message);
		}
	}

	public void debug(String message) {
		if (name == null) {
			Log.debug(message);
		} else {
			this.debug(name, message);
		}
	}

	public void info(String message) {
		if (name == null) {
			Log.info(message);
		} else {
			this.info(name, message);
		}
	}

	public void success(String message) {
		if (name == null) {
			Log.success(message);
		} else {
			this.success(name, message);
		}
	}

	public void warn(String message) {
		if (name == null) {
			Log.warn(message);
		} else {
			this.warn(name, message);
		}
	}

	public void error(String message) {
		if (name == null) {
			Log.error(message);
		} else {
			this.error(name, message);
		}
	}

	public void fatal(String message) {
		if (name == null) {
			Log.fatal(message);
		} else {
			this.fatal(name, message);
		}
	}

	public void trace(String name, String message) {
		Log.trace(name, message);
	}

	public void debug(String name, String message) {
		Log.debug(name, message);
	}

	public void info(String name, String message) {
		Log.info(name, message);
	}

	public void success(String name, String message) {
		Log.success(name, message);
	}

	public void warn(String name, String message) {
		Log.warn(name, message);
	}

	public void error(String name, String message) {
		Log.error(name, message);
	}

	public void fatal(String name, String message) {
		Log.fatal(name, message);
	}
}