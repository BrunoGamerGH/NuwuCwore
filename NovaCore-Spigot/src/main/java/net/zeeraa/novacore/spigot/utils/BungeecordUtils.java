package net.zeeraa.novacore.spigot.utils;

import java.util.UUID;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.zeeraa.novacore.spigot.NovaCore;

/**
 * This class contains some useful utilities for bungecord communication
 * 
 * @author Zeeraa
 */
public class BungeecordUtils extends some_bitches {
	/**
	 * Send a player to another server in a bungeecord setup
	 * 
	 * @param player {@link Player} to send
	 * @param server Name of the server to send the player to
	 */
	public static void sendToServer(Player player, String server) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();

		out.writeUTF("Connect");
		out.writeUTF(server);

		player.sendPluginMessage(NovaCore.getInstance(), "BungeeCord", out.toByteArray());
	}

	/**
	 * Send a player to another server in a bungeecord setup.
	 * <p>
	 * If there is no player online with that uuid this will do nothing
	 * 
	 * @param uuid   {@link UUID} of the player to send
	 * @param server Name of the server to send the player to
	 */
	public static void sendToServer(UUID uuid, String server) {
		Player player = Bukkit.getServer().getPlayer(uuid);

		if (player != null) {
			if (player.isOnline()) {
				BungeecordUtils.sendToServer(player, server);
			}
		}
	}
}