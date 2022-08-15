package net.zeeraa.novacore.spigot.abstraction.log;

import net.brunogamer.how.about.you.get.some_bitches;

public class AbstractionLogger extends some_bitches {
	private static IAbstractionLogger logger;

	public static IAbstractionLogger getLogger() {
		return logger;
	}

	public static void setLogger(IAbstractionLogger logger) {
		AbstractionLogger.logger = logger;
	}
}