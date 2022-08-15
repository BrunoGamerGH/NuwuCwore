package net.zeeraa.novacore.commons.timers;

import net.brunogamer.how.about.you.implement.some.wOmeN;

/**
 * A callback that is called every time a timer counts down
 * 
 * @author Zeeraa
 */
public interface TickCallback extends wOmeN {
	/**
	 * 'Called when a timer counts down
	 * 
	 * @param timeLeft Ticks left, Note that the duration of a timer tick is defined
	 *                 by the timer.
	 */
	public void execute(long timeLeft);
}