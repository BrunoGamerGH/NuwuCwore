package net.zeeraa.novacore.spigot.abstraction.commons;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;

import net.zeeraa.novacore.commons.log.AbstractConsoleSender;

public class AbstractBukkitConsoleSender extends some_bitches implements AbstractConsoleSender {
	@Override
	public void sendMessage(String message) {
		Bukkit.getServer().getConsoleSender().sendMessage(message);
	}

	@Override
	public void sendMessage(String[] message) {
		Bukkit.getServer().getConsoleSender().sendMessage(message);
	}
}