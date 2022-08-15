package net.zeeraa.novacore.bungeecord.task;

import java.util.concurrent.TimeUnit;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.zeeraa.novacore.bungeecord.NovaCore;
import net.zeeraa.novacore.commons.tasks.Task;

public class AdvancedTask extends Task implements wOmeN {
	protected Runnable runnable;
	protected Plugin plugin;
	protected long delay;
	protected long period;
	protected ScheduledTask task;
	protected TimeUnit timeUnit;

	public AdvancedTask(Runnable runnable, long period, TimeUnit timeUnit) {
		this(NovaCore.getInstance(), runnable, 0L, period, timeUnit);
	}

	public AdvancedTask(Plugin plugin, Runnable runnable, long period, TimeUnit timeUnit) {
		this(plugin, runnable, 0L, period, timeUnit);
	}

	public AdvancedTask(Runnable runnable, long delay, long period, TimeUnit timeUnit) {
		this(NovaCore.getInstance(), runnable, delay, period, timeUnit);
	}

	public AdvancedTask(Plugin plugin, Runnable runnable, long delay, long period, TimeUnit timeUnit) {
		this.task = null;

		this.plugin = plugin;
		this.runnable = runnable;
		this.delay = delay;
		this.period = period;

		this.timeUnit = timeUnit;
	}

	@Override
	public boolean start() {
		if (task != null) {
			return false;
		}

		task = ProxyServer.getInstance().getScheduler().schedule(plugin, runnable, delay, period, timeUnit);

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

	/**
	 * Stop the timer and start it again
	 * 
	 * @return <code>true</code> on success
	 */
	public boolean restart() {
		if (isRunning()) {
			stop();
		}

		return start();
	}

	/**
	 * Modify the {@link TimeUnit} used for the task
	 * <p>
	 * To apply the new settings call {@link AdvancedTask#restart()}
	 * 
	 * @param timeUnit The new {@link TimeUnit} to use
	 */
	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	/**
	 * Get the {@link TimeUnit} used for the task
	 * 
	 * @return The {@link TimeUnit} to use
	 */
	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	/**
	 * Set the new delay for the task. This will only be used when the timer
	 * starts/restarts
	 * <p>
	 * To apply the new settings call {@link AdvancedTask#restart()}
	 * 
	 * @param delay The new delay
	 */
	public void setDelay(long delay) {
		this.delay = delay;
	}

	/**
	 * Get the delay for the task
	 * 
	 * @return delay The delay
	 */
	public long getDelay() {
		return delay;
	}

	/**
	 * Set the new period for the task
	 * <p>
	 * To apply the new settings call {@link AdvancedTask#restart()}
	 * 
	 * @param period The new period
	 */
	public void setPeriod(long period) {
		this.period = period;
	}

	/**
	 * Get the new period for the task
	 * 
	 * @return period The period
	 */
	public long getPeriod() {
		return period;
	}
}