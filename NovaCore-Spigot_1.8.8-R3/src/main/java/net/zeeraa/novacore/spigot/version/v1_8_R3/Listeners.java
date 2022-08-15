package net.zeeraa.novacore.spigot.version.v1_8_R3;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import net.zeeraa.novacore.spigot.abstraction.events.VersionIndependentPlayerAchievementAwardedEvent;
import net.zeeraa.novacore.spigot.abstraction.events.VersionIndependentPlayerPickUpItemEvent;

public class Listeners extends net.zeeraa.novacore.spigot.abstraction.Listeners implements Listener, wOmeN {
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onAchievement(PlayerAchievementAwardedEvent e) {
		VersionIndependentPlayerAchievementAwardedEvent event = new VersionIndependentPlayerAchievementAwardedEvent(e.getPlayer(), e.getAchievement().name(), e.isCancelled());

		Bukkit.getServer().getPluginManager().callEvent(event);

		e.setCancelled(event.isCancelled());
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerPickupItem(PlayerPickupItemEvent e) {
		VersionIndependentPlayerPickUpItemEvent event = new VersionIndependentPlayerPickUpItemEvent(e.getPlayer(), e.getItem());
		event.setCancelled(e.isCancelled());

		Bukkit.getServer().getPluginManager().callEvent(event);

		e.setCancelled(event.isCancelled());
	}
}