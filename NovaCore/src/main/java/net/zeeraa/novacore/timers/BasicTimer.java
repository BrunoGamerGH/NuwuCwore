package net.zeeraa.novacore.timers;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import net.zeeraa.novacore.NovaCore;
import net.zeeraa.novacore.callbacks.Callback;

/**
 * This is a simple timer that can be used for countdowns and delayed tasks
 * 
 * @author Zeeraa
 */
public class BasicTimer implements Timer {
	private int taskId;
	private int timeLeft;
	private Long tickTime;

	private boolean started;
	private boolean finished;

	private ArrayList<Callback> finishCallbacks;
	private ArrayList<TickCallback> tickCallbacks;

	/**
	 * Timer that will run a callback every second and when finished
	 * 
	 * @param time the amount of seconds the timer will count down from
	 */
	public BasicTimer(int time) {
		this(time, 20L);
	}

	/**
	 * Timer that will count down form a certain value
	 * 
	 * @param time     value the timer will count down from
	 * @param tickTime how many ticks until the next step
	 */
	public BasicTimer(int time, Long tickTime) {
		this.timeLeft = time;
		this.tickTime = tickTime;

		this.taskId = -1;
		
		this.started = false;
		this.finished = false;

		this.finishCallbacks = new ArrayList<Callback>();
		this.tickCallbacks = new ArrayList<TickCallback>();
	}

	/**
	 * Add a {@link Callback} to be called when the timer is finished
	 * 
	 * @param callback {@link Callback} to be added
	 */
	public void addFinishCallback(Callback callback) {
		finishCallbacks.add(callback);
	}

	/**
	 * Add a {@link TickCallback} to be called every timer tick
	 * 
	 * @param tickCallback {@link TickCallback} to add
	 */
	public void addTickCallback(TickCallback tickCallback) {
		tickCallbacks.add(tickCallback);
	}

	/**
	 * Start the timer
	 * 
	 * @return <code>true</code> if the timer was started. <code>false</code> if it
	 *         has already been started
	 */
	@Override
	public boolean start() {
		if (this.isRunning()) {
			return false;
		}

		this.started = true;

		this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(NovaCore.getInstance(), new Runnable() {
			@Override
			public void run() {
				timeLeft--;

				for (TickCallback tc : tickCallbacks) {
					tc.execute(timeLeft);
				}

				if (timeLeft <= 0) {
					finished = true;
					for (Callback c : finishCallbacks) {
						c.execute();
					}
					stop();
				}
			}
		}, this.tickTime, this.tickTime);

		return true;
	}

	/**
	 * Stop the timer
	 * 
	 * @return <code>true</code> if the timer was stopped. <code>false</code> if
	 *         already stopped
	 */
	private boolean stop() {
		if (!this.isRunning()) {
			return false;
		}

		Bukkit.getScheduler().cancelTask(this.taskId);
		this.taskId = -1;

		return true;
	}

	/**
	 * Check if the timer has started. This does not get reset on cancel or stop
	 * 
	 * @return <code>true</code> if the timer has started
	 */
	@Override
	public boolean hasStarted() {
		return this.started;
	}

	@Override
	public boolean hasFinished() {
		return this.finished;
	}
	
	/**
	 * Check if the timer is running
	 * 
	 * @return <code>true</code> if the timer is running. <code>false</code> if the
	 *         timer is finished or has not started
	 */
	@Override
	public boolean isRunning() {
		return this.taskId != -1;
	}

	/**
	 * Cancel the timer
	 * 
	 * @return <code>true</code> if the timer is canceled. <code>false</code> if it
	 *         has already been canceled
	 */
	@Override
	public boolean cancel() {
		return this.stop();
	}

	/**
	 * Get time left
	 * 
	 * @return the time left
	 */
	@Override
	public int getTimeLeft() {
		return this.timeLeft;
	}

	/**
	 * Set the time left
	 * 
	 * @param timeLeft Value
	 */
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	/**
	 * Get mow many ticks before the timer will count down
	 * 
	 * @return ticks
	 */
	public Long getTickTime() {
		return tickTime;
	}
}