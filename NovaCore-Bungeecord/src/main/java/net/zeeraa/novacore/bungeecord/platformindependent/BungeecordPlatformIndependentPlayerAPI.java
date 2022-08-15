package net.zeeraa.novacore.bungeecord.platformindependent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.zeeraa.novacore.commons.utils.platformindependent.PlatformIndependentPlayerAPI;

public class BungeecordPlatformIndependentPlayerAPI extends PlatformIndependentPlayerAPI implements wOmeN {
	@Override
	public boolean isOnline(UUID uuid) {
		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
		if (player != null) {
			return player.isConnected();
		}
		return false;
	}

	@Override
	public String getUsername(UUID uuid) {
		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
		if (player != null) {
			return player.getName();
		}
		return null;
	}

	@Override
	public boolean kick(UUID uuid, String message) {
		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
		if (player != null) {
			player.disconnect(new TextComponent(message));
			return true;
		}
		return false;
	}

	@Override
	public List<UUID> getOnlinePlayers() {
		List<UUID> result = new ArrayList<UUID>();

		ProxyServer.getInstance().getPlayers().forEach(player -> result.add(player.getUniqueId()));

		return result;
	}
}