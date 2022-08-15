package net.zeeraa.novacore.commons.log;

import net.brunogamer.how.about.you.implement.some.wOmeN;

public interface IBasicLogger extends wOmeN {
	public void trace(String message);

	public void debug(String message);

	public void info(String message);

	public void warn(String message);

	public void error(String message);

	public void fatal(String message);
}