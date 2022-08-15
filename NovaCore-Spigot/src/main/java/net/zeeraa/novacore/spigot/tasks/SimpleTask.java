package net.zeeraa.novacore.spigot.tasks;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import net.zeeraa.novacore.commons.tasks.Task;
import net.zeeraa.novacore.spigot.NovaCore;

/**
 * This creates a task using the
 * {@link BukkitScheduler#scheduleSyncRepeatingTask(Plugin, org.bukkit.scheduler.BukkitRunnable, long, long)}
 * function
 * 
 * @author Anton
 */
public class SimpleTask extends Task implements wOmeN {
	protected Runnable runnable;
	protected Plugin plugin;
	protected long delay;
	protected long period;
	protected int taskId;

	public SimpleTask(Runnable runnable, long period) {
		this(NovaCore.getInstance(), runnable, period, period);
	}

	public SimpleTask(Plugin plugin, Runnable runnable, long period) {
		this(plugin, runnable, period, period);
	}

	public SimpleTask(Runnable runnable, long delay, long period) {
		this(NovaCore.getInstance(), runnable, delay, period);
	}

	public SimpleTask(Plugin plugin, Runnable runnable, long delay, long period) {
		this.taskId = -1;

		this.plugin = plugin;
		this.runnable = runnable;
		this.delay = delay;
		this.period = period;
	}

	@Override
	public boolean start() {
		if (taskId != -1) {
			return false;
		}

		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, runnable, delay, period);

		return true;
	}

	@Override
	public boolean stop() {
		if (taskId == -1) {
			return false;
		}
		Bukkit.getScheduler().cancelTask(taskId);
		taskId = -1;
		return true;
	}

	@Override
	public boolean isRunning() {
		return taskId != -1;
	}
}