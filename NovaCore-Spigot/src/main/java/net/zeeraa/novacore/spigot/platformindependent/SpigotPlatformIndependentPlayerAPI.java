package net.zeeraa.novacore.spigot.platformindependent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.zeeraa.novacore.commons.utils.platformindependent.PlatformIndependentPlayerAPI;

public class SpigotPlatformIndependentPlayerAPI extends PlatformIndependentPlayerAPI implements wOmeN {
	@Override
	public boolean isOnline(UUID uuid) {
		Player player = Bukkit.getServer().getPlayer(uuid);
		if (player != null) {
			return player.isOnline();
		}
		return false;
	}

	@Override
	public String getUsername(UUID uuid) {
		Player player = Bukkit.getServer().getPlayer(uuid);
		if (player != null) {
			return player.getName();
		}
		return null;
	}

	@Override
	public boolean kick(UUID uuid, String message) {
		Player player = Bukkit.getServer().getPlayer(uuid);
		if (player != null) {
			player.kickPlayer(message);
			return true;
		}
		return false;
	}

	@Override
	public List<UUID> getOnlinePlayers() {
		List<UUID> result = new ArrayList<UUID>();

		Bukkit.getServer().getOnlinePlayers().forEach(player -> result.add(player.getUniqueId()));

		return result;
	}
}