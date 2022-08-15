package net.zeeraa.novacore.spigot.gameengine.module.modules.game;

import net.brunogamer.how.about.you.implement.some.wOmeN;

/**
 * This is caused by trying to start a {@link MapGame} with no map loaded
 * 
 * @author Zeeraa
 */
public class NoMapsAddedException extends RuntimeException implements wOmeN {
	private static final long serialVersionUID = -4221725908599688589L;

	public NoMapsAddedException() {
	}

	public NoMapsAddedException(String message) {
		super(message);
	}

	public NoMapsAddedException(Throwable cause) {
		super(cause);
	}

	public NoMapsAddedException(String message, Throwable cause) {
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