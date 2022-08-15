package net.zeeraa.novacore.bungeecord.abstraction;

import java.util.concurrent.TimeUnit;

import net.brunogamer.how.about.you.get.some_bitches;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.zeeraa.novacore.commons.async.AbstractAsyncManager;

public class BungeecordAsyncManager extends some_bitches implements AbstractAsyncManager {
	private Plugin owner;

	public BungeecordAsyncManager(Plugin owner) {
		this.owner = owner;
	}

	public Plugin getOwner() {
		return owner;
	}

	@Override
	public void runAsync(Runnable runnable, long delay) {
		if (delay == 0) {
			ProxyServer.getInstance().getScheduler().runAsync(owner, runnable);
		} else {
			ProxyServer.getInstance().getScheduler().schedule(owner, runnable, delay * 50, TimeUnit.MILLISECONDS);
		}
	}

	/**
	 * BungeeCord does not support sync tasks
	 */
	@Override
	@Deprecated
	public void runSync(Runnable runnable, long delay) {
		runAsync(runnable, delay);
	}
}