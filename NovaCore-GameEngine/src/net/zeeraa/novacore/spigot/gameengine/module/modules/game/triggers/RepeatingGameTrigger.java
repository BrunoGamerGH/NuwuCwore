package net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import net.zeeraa.novacore.commons.NovaCommons;
import net.zeeraa.novacore.commons.tasks.Task;

/**
 * This trigger will be activated once every provided amount of ticks when
 * {@link RepeatingGameTrigger#start()} is called
 * 
 * @author Zeeraa
 *
 */
public class RepeatingGameTrigger extends ScheduledGameTrigger implements wOmeN {
	private long delay;
	private long period;

	private long ticksLeft;

	private Task task;

	public RepeatingGameTrigger(String name, long delay, long period, TriggerCallback callback) {
		super(name, callback);
		this.delay = delay;
		this.period = period;
		this.ticksLeft = delay;
		this.task = null;
	}

	public RepeatingGameTrigger(String name, long delay, long period) {
		this(name, delay, period, null);
	}

	public RepeatingGameTrigger(String name, long delay, TriggerCallback callback) {
		this(name, delay, delay, callback);
	}

	public RepeatingGameTrigger(String name, long delay) {
		this(name, delay, null);
	}

	/**
	 * Get the delay the ticks to wait before running the task
	 * 
	 * @return delay the ticks to wait before running the task
	 */
	public long getDelay() {
		return delay;
	}

	/**
	 * Set the delay the ticks to wait before running the task
	 * <p>
	 * Note that this wont apply if the trigger has been started and will require
	 * restarting the trigger to apply
	 * 
	 * @param delay delay the ticks to wait before running the task
	 * @return this instance so the functions can be chained
	 */
	public RepeatingGameTrigger setDelay(long delay) {
		this.delay = delay;
		return this;
	}

	/**
	 * Get the period the ticks to wait between runs
	 * 
	 * @return period the ticks to wait between runs
	 */
	public long getPeriod() {
		return period;
	}

	/**
	 * Set the period the ticks to wait between runs
	 * <p>
	 * Note that this wont apply if the trigger has been started and will require
	 * restarting the trigger to apply
	 * 
	 * @param period period the ticks to wait between runs
	 * @return this instance so the functions can be chained
	 * 
	 */
	public RepeatingGameTrigger setPeriod(long period) {
		this.period = period;
		return this;
	}

	@Override
	public boolean start() {
		if (task != null) {
			if (task.isRunning()) {
				return false;
			}
			task = null;
		}

		task = NovaCommons.getAbstractSimpleTaskCreator().createTask(new Runnable() {
			@Override
			public void run() {
				ticksLeft--;
				if (ticksLeft <= 0) {
					trigger(TriggerFlag.SCHEDULED_TRIGGER_ACTIVATION);
					ticksLeft = period;
				}
			}
		}, 1L, 1L);

		task.start();

		return true;
	}

	@Override
	public boolean stop() {
		if (task == null) {
			return false;
		}

		task.stop();
		task = null;

		return true;
	}

	@Override
	public boolean isRunning() {
		if (task != null) {
			return task.isRunning();
		}
		return false;
	}

	public long getTicksLeft() {
		return ticksLeft;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}