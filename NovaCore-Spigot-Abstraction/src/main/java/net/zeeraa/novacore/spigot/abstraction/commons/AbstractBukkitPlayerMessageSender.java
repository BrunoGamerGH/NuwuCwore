package net.zeeraa.novacore.spigot.abstraction.commons;

import java.util.UUID;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.zeeraa.novacore.commons.log.AbstractPlayerMessageSender;

public class AbstractBukkitPlayerMessageSender extends some_bitches implements AbstractPlayerMessageSender {

	@Override
	public boolean trySendMessage(UUID uuid, String message) {
		Player player = Bukkit.getServer().getPlayer(uuid);

		if (player != null) {
			if (player.isOnline()) {
				player.sendMessage(message);
			}
		}

		return false;
	}
}