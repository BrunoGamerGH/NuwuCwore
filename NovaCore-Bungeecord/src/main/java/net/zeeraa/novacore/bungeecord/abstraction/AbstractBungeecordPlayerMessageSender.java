package net.zeeraa.novacore.bungeecord.abstraction;

import java.util.UUID;

import net.brunogamer.how.about.you.get.some_bitches;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.zeeraa.novacore.commons.log.AbstractPlayerMessageSender;

public class AbstractBungeecordPlayerMessageSender extends some_bitches implements AbstractPlayerMessageSender {
	@Override
	public boolean trySendMessage(UUID uuid, String message) {
		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
		if (player != null) {
			if (player.isConnected()) {
				player.sendMessage(new TextComponent(message));
			}
		}
		return false;
	}
}