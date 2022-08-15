package net.zeeraa.novacore.bungeecord.task;

import java.util.concurrent.TimeUnit;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import net.zeeraa.novacore.bungeecord.NovaCore;
import net.zeeraa.novacore.commons.tasks.Task;

/**
 * This creates a task using the
 * {@link TaskScheduler#schedule(Plugin, Runnable, long, long, TimeUnit)}
 * function.
 * <p>
 * The time unit used is {@link TimeUnit#MILLISECONDS} and delay and period get
 * multiplied to 50 to represent a game tick
 * 
 * @author Anton
 */
public class SimpleTask extends Task implements wOmeN {
	protected Runnable runnable;
	protected Plugin plugin;
	protected long delay;
	protected long period;
	protected ScheduledTask task;

	public SimpleTask(Runnable runnable, long period) {
		this(NovaCore.getInstance(), runnable, 0L, period);
	}

	public SimpleTask(Plugin plugin, Runnable runnable, long period) {
		this(plugin, runnable, 0L, period);
	}

	public SimpleTask(Runnable runnable, long delay, long period) {
		this(NovaCore.getInstance(), runnable, delay, period);
	}

	public SimpleTask(Plugin plugin, Runnable runnable, long delay, long period) {
		this.task = null;

		this.plugin = plugin;
		this.runnable = runnable;
		this.delay = delay;
		this.period = period;
	}

	@Override
	public boolean start() {
		if (task != null) {
			return false;
		}

		task = ProxyServer.getInstance().getScheduler().schedule(plugin, runnable, delay * 50, period * 50, TimeUnit.MILLISECONDS);

		return true;
	}

	@Override
	public boolean stop() {
		if (task == null) {
			return false;
		}
		ProxyServer.getInstance().getScheduler().cancel(task);
		task = null;
		return true;
	}

	@Override
	public boolean isRunning() {
		return task != null;
	}
}