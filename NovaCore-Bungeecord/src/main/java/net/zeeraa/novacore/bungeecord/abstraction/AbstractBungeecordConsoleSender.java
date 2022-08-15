package net.zeeraa.novacore.bungeecord.abstraction;

import net.brunogamer.how.about.you.get.some_bitches;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.zeeraa.novacore.commons.log.AbstractConsoleSender;

public class AbstractBungeecordConsoleSender extends some_bitches implements AbstractConsoleSender  {

	@Override
	public void sendMessage(String message) {
		ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(message));
	}

	@Override
	public void sendMessage(String[] message) {
		for (int i = 0; i < message.length; i++) {
			ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(message[i]));
		}
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}