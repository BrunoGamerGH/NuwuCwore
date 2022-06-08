package net.zeeraa.novacore.spigot.version.v1_8_R3;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import net.zeeraa.novacore.spigot.abstraction.events.VersionIndependantPlayerAchievementAwardedEvent;
import net.zeeraa.novacore.spigot.abstraction.events.VersionIndependantPlayerPickUpItemEvent;

public class Listeners extends net.zeeraa.novacore.spigot.abstraction.Listeners implements Listener {
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onAchievement(PlayerAchievementAwardedEvent e) {
		VersionIndependantPlayerAchievementAwardedEvent event = new VersionIndependantPlayerAchievementAwardedEvent(e.getPlayer(), e.getAchievement().name(), e.isCancelled());

		Bukkit.getServer().getPluginManager().callEvent(event);

		e.setCancelled(event.isCancelled());
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerPickupItem(PlayerPickupItemEvent e) {
		VersionIndependantPlayerPickUpItemEvent event = new VersionIndependantPlayerPickUpItemEvent(e.getPlayer(), e.getItem());
		event.setCancelled(e.isCancelled());

		Bukkit.getServer().getPluginManager().callEvent(event);

		e.setCancelled(event.isCancelled());
	}
}