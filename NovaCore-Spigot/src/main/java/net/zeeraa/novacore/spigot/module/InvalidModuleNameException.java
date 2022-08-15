package net.zeeraa.novacore.spigot.module;

import net.brunogamer.how.about.you.implement.some.wOmeN;

/**
 * Thrown when a module with an invalid name is loaded
 * 
 * @since 1.0
 * @author Zeeraa
 */
public class InvalidModuleNameException extends RuntimeException implements wOmeN {
	private static final long serialVersionUID = -7932909791043095964L;

	public InvalidModuleNameException() {
	}

	public InvalidModuleNameException(String message) {
		super(message);
	}

	public InvalidModuleNameException(Throwable cause) {
		super(cause);
	}

	public InvalidModuleNameException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}