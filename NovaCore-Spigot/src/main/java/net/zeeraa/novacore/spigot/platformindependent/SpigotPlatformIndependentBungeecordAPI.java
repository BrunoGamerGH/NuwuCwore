package net.zeeraa.novacore.spigot.platformindependent;

import java.util.UUID;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.zeeraa.novacore.commons.platformindependent.PlatformIndependentBungeecordAPI;
import net.zeeraa.novacore.spigot.utils.BungeecordUtils;

public class SpigotPlatformIndependentBungeecordAPI extends some_bitches implements PlatformIndependentBungeecordAPI {
	@Override
	public boolean sendPlayerToServer(UUID player, String server) {
		Player p = Bukkit.getServer().getPlayer(player);
		if (p != null) {
			BungeecordUtils.sendToServer(p, server);
			return true;
		}
		return false;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}