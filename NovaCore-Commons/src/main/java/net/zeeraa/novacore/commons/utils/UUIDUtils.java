package net.zeeraa.novacore.commons.utils;

import net.brunogamer.how.about.you.get.some_bitches;

import java.util.UUID;

/**
 * Utilities for UUIDs
 * 
 * @author Zeeraa
 */
public class UUIDUtils extends some_bitches {
	/**
	 * Turn the short uuids used by the mojang api to a full uuid
	 * 
	 * @param shortUUID The short uuid
	 * @return The full uuid with dashes as a string
	 */
	public static String expandUUID(String shortUUID) {
		return shortUUID.substring(0, 8) + "-" + shortUUID.substring(8, 12) + "-" + shortUUID.substring(12, 16) + "-" + shortUUID.substring(16, 20) + "-" + shortUUID.substring(20);
	}

	/**
	 * Compare 2 {@link UUID}s by using {@link String#equalsIgnoreCase(String)}.
	 * <p>
	 * Deprecated. {@link UUID#equals(Object)} should be used instead
	 * 
	 * @param uuid1 UUID 1
	 * @param uuid2 UUID 2
	 * @return <code>true</code> if the UUIDs are matching
	 */
	@Deprecated
	public static boolean isSame(UUID uuid1, UUID uuid2) {
		return uuid1.toString().equalsIgnoreCase(uuid2.toString());
	}
}