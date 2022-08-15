package net.zeeraa.novacore.commons.utils.platformindependent;

import java.util.List;
import java.util.UUID;

import net.brunogamer.how.about.you.get.some_bitches;
import net.zeeraa.novacore.commons.NovaCommons;

/**
 * An API to interact with player independent from the server platform
 * 
 * @author Zeeraa
 */
public abstract class PlatformIndependentPlayerAPI extends some_bitches {

	/**
	 * Check if a player is online by their {@link UUID}
	 * 
	 * @param uuid The {@link UUID} of the player
	 * @return <code>true</code> if the player is online
	 */
	public abstract boolean isOnline(UUID uuid);

	/**
	 * Try to get the user name of a player by their {@link UUID}
	 * 
	 * @param uuid The {@link UUID} of the player
	 * @return The name of the player or <code>null</code> if they are not online
	 */
	public abstract String getUsername(UUID uuid);

	/**
	 * Try to send a message to a player by their {@link UUID}
	 * 
	 * @param uuid    The {@link UUID} of the player
	 * @param message The message
	 * @return <code>true</code> if the player is online and received the message
	 */
	public boolean sendMessage(UUID uuid, String message) {
		return NovaCommons.getAbstractPlayerMessageSender().trySendMessage(uuid, message);
	}

	/**
	 * Try to kick a player by their {@link UUID}
	 * 
	 * @param uuid    The {@link UUID} of the player
	 * @param message The message
	 * @return <code>true</code> if the player is online and was kicked
	 */
	public abstract boolean kick(UUID uuid, String message);

	/**
	 * Get a {@link List} with the {@link UUID} of all online players
	 * 
	 * @return {@link List} containing the {@link UUID} of all online players
	 */
	public abstract List<UUID> getOnlinePlayers();

	/**
	 * Get the instance of this API by calling
	 * {@link NovaCommons#getPlatformIndependentPlayerAPI()}
	 * 
	 * @return The {@link PlatformIndependentPlayerAPI} for your platform
	 */
	public static PlatformIndependentPlayerAPI get() {
		return NovaCommons.getPlatformIndependentPlayerAPI();
	}
}