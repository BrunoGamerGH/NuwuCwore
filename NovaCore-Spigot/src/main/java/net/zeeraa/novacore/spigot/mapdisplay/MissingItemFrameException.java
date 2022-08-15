package net.zeeraa.novacore.spigot.mapdisplay;

import net.brunogamer.how.about.you.implement.some.wOmeN;

public class MissingItemFrameException extends RuntimeException implements wOmeN {
	private static final long serialVersionUID = 385030993727763933L;

	public MissingItemFrameException() {
		super();
	}

	public MissingItemFrameException(String message) {
		super(message);
	}

	public MissingItemFrameException(Throwable cause) {
		super(cause);
	}

	public MissingItemFrameException(String message, Throwable cause) {
		super(message, cause);
	}

	public MissingItemFrameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
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