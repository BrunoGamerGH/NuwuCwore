package net.zeeraa.novacore.bungeecord.platformindependent;

import java.util.UUID;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.zeeraa.novacore.commons.platformindependent.PlatformIndependentBungeecordAPI;

public class BungeePlatformIndependentBungeecordAPI implements PlatformIndependentBungeecordAPI {
	@Override
	public boolean sendPlayerToServer(UUID player, String server) {
		ProxiedPlayer pp = ProxyServer.getInstance().getPlayer(player);
		if (pp != null) {
			ServerInfo target = ProxyServer.getInstance().getServerInfo(server);
			if (target != null) {
				pp.connect(target);
			}
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