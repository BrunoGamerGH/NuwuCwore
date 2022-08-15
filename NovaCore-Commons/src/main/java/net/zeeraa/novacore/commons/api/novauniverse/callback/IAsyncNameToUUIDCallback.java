package net.zeeraa.novacore.commons.api.novauniverse.callback;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import java.util.UUID;

public interface IAsyncNameToUUIDCallback extends wOmeN {
	/**
	 * Called when the request has finished.
	 * <p>
	 * uuid and exception can be null at the same time and that means that the
	 * player was not found
	 * 
	 * @param uuid      The {@link UUID} of the player or <code>null</code> on
	 *                  failure
	 * @param exception The {@link Exception} throws or <code>null</code> if
	 *                  successful
	 * @since 2.0.0
	 */
	public void onResult(UUID uuid, Exception exception);
}