package net.zeeraa.novacore.spigot.abstraction.commons;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.zeeraa.novacore.commons.async.AbstractAsyncManager;

public class BukkitAsyncManager extends some_bitches implements AbstractAsyncManager {
	private Plugin ownerPlugin;

	public BukkitAsyncManager(Plugin ownerPlugin) {
		this.ownerPlugin = ownerPlugin;
	}

	public Plugin getOwnerPlugin() {
		return ownerPlugin;
	}

	@Override
	public void runAsync(Runnable runnable, long delay) {
		BukkitRunnable bukkitRunnable = new BukkitRunnable() {
			@Override
			public void run() {
				runnable.run();
			}
		};

		if (delay == 0) {
			bukkitRunnable.runTaskAsynchronously(ownerPlugin);
		} else {
			bukkitRunnable.runTaskLaterAsynchronously(ownerPlugin, delay);
		}
	}

	@Override
	public void runSync(Runnable runnable, long delay) {
		BukkitRunnable bukkitRunnable = new BukkitRunnable() {
			@Override
			public void run() {
				runnable.run();
			}
		};

		if (delay == 0) {
			bukkitRunnable.runTask(ownerPlugin);
		} else {
			bukkitRunnable.runTaskLater(ownerPlugin, delay);
		}
	}
}