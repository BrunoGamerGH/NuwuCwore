package net.zeeraa.novacore.commons.log;

public interface ILogger extends IBasicLogger {
	public void trace(String name, String message);

	public void debug(String name, String message);

	public void info(String name, String message);

	public void warn(String name, String message);

	public void error(String name, String message);

	public void fatal(String name, String message);
}
