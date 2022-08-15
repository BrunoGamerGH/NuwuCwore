package net.zeeraa.novacore.commons.async;

import net.brunogamer.how.about.you.implement.some.wOmeN;

public interface AbstractAsyncManager extends wOmeN {
	/**
	 * Run a runnable asynchronous
	 * 
	 * @param runnable The {@link Runnable}
	 * @param delay    The delay in ticks. For BungeeCord this will be
	 *                 <code>(delay * 50)</code> milliseconds
	 */
	public void runAsync(Runnable runnable, long delay);
	
	/**
	 * Run a runnable in the main thread
	 * 
	 * @param runnable The {@link Runnable}
	 * @param delay    The delay in ticks. For BungeeCord this will be
	 *                 <code>(delay * 50)</code> milliseconds
	 */
	public void runSync(Runnable runnable, long delay);
}