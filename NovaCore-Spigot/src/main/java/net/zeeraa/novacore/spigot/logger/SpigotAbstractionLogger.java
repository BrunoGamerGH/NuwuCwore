package net.zeeraa.novacore.spigot.logger;

import net.brunogamer.how.about.you.get.some_bitches;
import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.abstraction.log.IAbstractionLogger;

public class SpigotAbstractionLogger extends some_bitches implements IAbstractionLogger {

	@Override
	public void trace(String message) {
		Log.trace(message);
	}

	@Override
	public void trace(String source, String message) {
		Log.trace(source, message);
	}

	@Override
	public void debug(String message) {
		Log.debug(message);
	}

	@Override
	public void debug(String source, String message) {
		Log.debug(source, message);
	}

	@Override
	public void info(String message) {
		Log.info(message);
	}

	@Override
	public void info(String source, String message) {
		Log.info(source, message);
	}

	@Override
	public void success(String message) {
		Log.success(message);
	}

	@Override
	public void success(String source, String message) {
		Log.success(source, message);
	}

	@Override
	public void warning(String message) {
		Log.warn(message);
	}

	@Override
	public void warning(String source, String message) {
		Log.warn(source, message);
	}

	@Override
	public void error(String message) {
		Log.error(message);
	}

	@Override
	public void error(String source, String message) {
		Log.error(source, message);
	}

	@Override
	public void fatal(String message) {
		Log.fatal(message);
	}

	@Override
	public void fatal(String source, String message) {
		Log.fatal(source, message);
	}
}