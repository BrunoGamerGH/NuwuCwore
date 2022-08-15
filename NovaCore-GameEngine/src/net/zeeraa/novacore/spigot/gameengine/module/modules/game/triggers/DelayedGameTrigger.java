package net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers;

import java.util.ArrayList;
import java.util.List;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import net.zeeraa.novacore.commons.timers.TickCallback;
import net.zeeraa.novacore.commons.utils.Callback;
import net.zeeraa.novacore.spigot.timers.BasicTimer;

/**
 * This trigger will be activated with a provided delay after
 * {@link DelayedGameTrigger#start()} is called
 * 
 * @author Zeeraa
 *
 */
public class DelayedGameTrigger extends ScheduledGameTrigger implements wOmeN {
	private long delay;

	private BasicTimer timer;

	private List<TickCallback> tickCallbacks;

	public DelayedGameTrigger(String name, long delay, TriggerCallback callback) {
		super(name, callback);
		this.delay = delay;
		this.timer = null;
		this.tickCallbacks = new ArrayList<>();
	}

	public DelayedGameTrigger(String name, long delay) {
		this(name, delay, null);
	}

	/**
	 * Get the delay this trigger uses
	 * 
	 * @return The delay
	 */
	public long getDelay() {
		return delay;
	}

	/**
	 * Set the delay for this trigger
	 * <p>
	 * Note that this wont apply if the trigger has been started and will require
	 * restarting the trigger to apply
	 * 
	 * @param delay The new delay for the trigger
	 * @return this instance so the functions can be chained
	 */
	public DelayedGameTrigger setDelay(long delay) {
		this.delay = delay;
		return this;
	}

	/**
	 * Add a callback that is called when ever the timer counts down
	 * 
	 * @param callback The {@link TickCallback} to add
	 */
	public void addTickCallback(TickCallback callback) {
		tickCallbacks.add(callback);

		if (isRunning()) {
			timer.addTickCallback(callback);
		}
	}

	@Override
	public boolean start() {
		if (timer != null) {
			if (timer.isRunning()) {
				return false;
			} else {
				timer = null;
			}
		}

		timer = new BasicTimer(delay, 1);
		timer.addFinishCallback(new Callback() {
			@Override
			public boolean isCancelled() {
				return false;
			}

			@Override
			public void setCancelled(boolean cancel) {

			}

			@Override
			public void execute() {
				stop();
				trigger(TriggerFlag.SCHEDULED_TRIGGER_ACTIVATION);
			}
		});

		for (TickCallback callback : tickCallbacks) {
			timer.addTickCallback(callback);
		}

		timer.start();

		return true;
	}

	@Override
	public boolean stop() {
		if (timer != null) {
			if (timer.isRunning()) {
				timer.cancel();
			}
			timer = null;
			return true;
		}
		return false;
	}

	@Override
	public boolean isRunning() {
		if (timer != null) {
			return timer.isRunning();
		}

		return false;
	}

	/**
	 * Get the ticks until execution.
	 * <p>
	 * Will be 0 if the timer is not running
	 * 
	 * @return the time left in ticks
	 */
	public long getTicksLeft() {
		if (isRunning()) {
			return timer.getTimeLeft();
		}

		return 0;
	}
}