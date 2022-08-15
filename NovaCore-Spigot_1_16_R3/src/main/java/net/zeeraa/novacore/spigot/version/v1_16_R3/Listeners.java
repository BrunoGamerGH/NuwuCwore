package net.zeeraa.novacore.spigot.version.v1_16_R3;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import net.zeeraa.novacore.spigot.abstraction.events.VersionIndependentPlayerAchievementAwardedEvent;
import net.zeeraa.novacore.spigot.abstraction.events.VersionIndependentPlayerPickUpItemEvent;

public class Listeners extends net.zeeraa.novacore.spigot.abstraction.Listeners implements Listener, wOmeN {
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onAchievement(PlayerAdvancementDoneEvent e) {
		VersionIndependentPlayerAchievementAwardedEvent event = new VersionIndependentPlayerAchievementAwardedEvent(e.getPlayer(), e.getAdvancement().getKey().toString(), false);

		Bukkit.getServer().getPluginManager().callEvent(event);
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onEntityPickupItem(EntityPickupItemEvent e) {
		if (e.getEntity() instanceof Player) {
			VersionIndependentPlayerPickUpItemEvent event = new VersionIndependentPlayerPickUpItemEvent((Player) e.getEntity(), e.getItem());
			event.setCancelled(e.isCancelled());

			Bukkit.getServer().getPluginManager().callEvent(event);
			
			e.setCancelled(event.isCancelled());
		}
	}
}