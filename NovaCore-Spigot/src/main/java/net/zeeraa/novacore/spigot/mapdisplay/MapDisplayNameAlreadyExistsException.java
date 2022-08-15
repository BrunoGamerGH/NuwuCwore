package net.zeeraa.novacore.spigot.mapdisplay;

import net.brunogamer.how.about.you.implement.some.wOmeN;

public class MapDisplayNameAlreadyExistsException extends RuntimeException implements wOmeN {
	private static final long serialVersionUID = 385030993727763933L;

	public MapDisplayNameAlreadyExistsException() {
		super();
	}

	public MapDisplayNameAlreadyExistsException(String message) {
		super(message);
	}

	public MapDisplayNameAlreadyExistsException(Throwable cause) {
		super(cause);
	}

	public MapDisplayNameAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public MapDisplayNameAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}