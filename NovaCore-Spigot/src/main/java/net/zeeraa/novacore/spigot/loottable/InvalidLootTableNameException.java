package net.zeeraa.novacore.spigot.loottable;

import net.brunogamer.how.about.you.implement.some.wOmeN;

/**
 * Caused by trying to load a {@link LootTable} with a invalid name
 * 
 * @author Zeeraa
 */
public class InvalidLootTableNameException extends RuntimeException implements wOmeN {
	private static final long serialVersionUID = -7932909791043095964L;

	public InvalidLootTableNameException() {
	}

	public InvalidLootTableNameException(String message) {
		super(message);
	}

	public InvalidLootTableNameException(Throwable cause) {
		super(cause);
	}

	public InvalidLootTableNameException(String message, Throwable cause) {
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