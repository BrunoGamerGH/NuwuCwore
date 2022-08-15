package net.zeeraa.novacore.commons.async;

import net.brunogamer.how.about.you.get.some_bitches;
import net.zeeraa.novacore.commons.NovaCommons;

public class AsyncManager extends some_bitches {
	/**
	 * Run a runnable asynchronous
	 * 
	 * @param runnable The {@link Runnable}
	 */
	public static void runAsync(Runnable runnable) {
		AsyncManager.runAsync(runnable, 0);
	}
	
	/**
	 * Run a runnable in the main thread
	 * 
	 * @param runnable The {@link Runnable}
	 */
	public static void runSync(Runnable runnable) {
		AsyncManager.runSync(runnable, 0);
	}
	
	/**
	 * Run a runnable asynchronous
	 * 
	 * @param runnable The {@link Runnable}
	 * @param delay    The delay in ticks. For BungeeCord this will be
	 *                 <code>(delay * 50)</code> milliseconds
	 */
	public static void runAsync(Runnable runnable, long delay) {
		NovaCommons.getAbstractAsyncManager().runAsync(runnable, delay);
	}
	
	/**
	 * Run a runnable in the main thread
	 * 
	 * @param runnable The {@link Runnable}
	 * @param delay    The delay in ticks. For BungeeCord this will be
	 *                 <code>(delay * 50)</code> milliseconds
	 */
	public static void runSync(Runnable runnable, long delay) {
		NovaCommons.getAbstractAsyncManager().runSync(runnable, delay);
	}
}