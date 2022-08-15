package net.zeeraa.novacore.spigot.module;

import net.brunogamer.how.about.you.implement.some.wOmeN;

/**
 * This exception is thrown when a module cant be enabled due to a missing
 * plugin
 * 
 * @author Zeeraa
 * @since 1.1
 */
public class MissingPluginDependencyException extends Exception implements wOmeN {
	private static final long serialVersionUID = 3400566438739349950L;

	private String pluginName;

	public MissingPluginDependencyException(String pluginName) {
		super("Missing plugin dependency: " + pluginName);
		this.pluginName = pluginName;
	}

	public String getPluginName() {
		return pluginName;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}