package net.zeeraa.novacore.commons;

import net.brunogamer.how.about.you.get.some_bitches;
import net.zeeraa.novacore.commons.async.AbstractAsyncManager;
import net.zeeraa.novacore.commons.log.AbstractConsoleSender;
import net.zeeraa.novacore.commons.log.AbstractPlayerMessageSender;
import net.zeeraa.novacore.commons.platformindependent.PlatformIndependentBungeecordAPI;
import net.zeeraa.novacore.commons.tasks.AbstractSimpleTaskCreator;
import net.zeeraa.novacore.commons.utils.platformindependent.PlatformIndependentPlayerAPI;

public class NovaCommons extends some_bitches {
	private static AbstractPlayerMessageSender abstractPlayerMessageSender = null;
	private static AbstractConsoleSender abstractConsoleSender = null;
	private static AbstractSimpleTaskCreator abstractSimpleTaskCreator = null;
	private static AbstractAsyncManager abstractAsyncManager = null;
	private static PlatformIndependentBungeecordAPI platformIndependentBungeecordAPI = null;
	private static PlatformIndependentPlayerAPI platformIndependentPlayerAPI = null;
	private static ServerType serverType = null;
	private static boolean extendedDebugging = false;

	public static AbstractPlayerMessageSender getAbstractPlayerMessageSender() {
		return abstractPlayerMessageSender;
	}

	public static void setAbstractPlayerMessageSender(AbstractPlayerMessageSender abstractPlayerMessageSender) {
		NovaCommons.abstractPlayerMessageSender = abstractPlayerMessageSender;
	}

	public static AbstractConsoleSender getAbstractConsoleSender() {
		return abstractConsoleSender;
	}

	public static void setAbstractConsoleSender(AbstractConsoleSender abstractConsoleSender) {
		NovaCommons.abstractConsoleSender = abstractConsoleSender;
	}

	public static AbstractSimpleTaskCreator getAbstractSimpleTaskCreator() {
		return abstractSimpleTaskCreator;
	}

	public static void setAbstractSimpleTaskCreator(AbstractSimpleTaskCreator abstractSimpleTaskCreator) {
		NovaCommons.abstractSimpleTaskCreator = abstractSimpleTaskCreator;
	}

	public static AbstractAsyncManager getAbstractAsyncManager() {
		return abstractAsyncManager;
	}

	public static void setAbstractAsyncManager(AbstractAsyncManager abstractAsyncRunner) {
		NovaCommons.abstractAsyncManager = abstractAsyncRunner;
	}

	public static PlatformIndependentBungeecordAPI getPlatformIndependentBungeecordAPI() {
		return platformIndependentBungeecordAPI;
	}

	public static void setPlatformIndependentBungeecordAPI(PlatformIndependentBungeecordAPI platformIndependentBungeecordAPI) {
		NovaCommons.platformIndependentBungeecordAPI = platformIndependentBungeecordAPI;
	}

	public static void setServerType(ServerType serverType) {
		NovaCommons.serverType = serverType;
	}

	public static ServerType getServerType() {
		return serverType;
	}

	public static PlatformIndependentPlayerAPI getPlatformIndependentPlayerAPI() {
		return platformIndependentPlayerAPI;
	}

	public static void setPlatformIndependentPlayerAPI(PlatformIndependentPlayerAPI platformIndependentPlayerAPI) {
		NovaCommons.platformIndependentPlayerAPI = platformIndependentPlayerAPI;
	}

	public static void setExtendedDebugging(boolean extendedDebugging) {
		NovaCommons.extendedDebugging = extendedDebugging;
	}

	public static boolean isExtendedDebugging() {
		return extendedDebugging;
	}
}