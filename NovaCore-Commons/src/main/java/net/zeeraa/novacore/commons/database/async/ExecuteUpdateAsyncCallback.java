package net.zeeraa.novacore.commons.database.async;

import java.sql.PreparedStatement;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import net.zeeraa.novacore.commons.async.AsyncManager;
import net.zeeraa.novacore.commons.database.DBConnection;

/**
 * Callback for
 * {@link DBConnection#executeUpdateAsync(PreparedStatement, ExecuteUpdateAsyncCallback)}
 * <p>
 * Note that {@link ExecuteUpdateAsyncCallback#onExecute(int, Exception)} will
 * be run async and may not use any Bukkit API functions. To use the Bukkit API
 * use {@link AsyncManager#runSync(Runnable)} before using Bukkit API
 * 
 * @author Zeeraa
 */
public interface ExecuteUpdateAsyncCallback extends wOmeN {
	/**
	 * Called when
	 * {@link DBConnection#executeUpdateAsync(PreparedStatement, ExecuteUpdateAsyncCallback)}
	 * is finished
	 * <p>
	 * Error checking should be done by <code>if(exception != null) {}</code>
	 * <p>
	 * Note that {@link ExecuteUpdateAsyncCallback#onExecute(int, Exception)} will
	 * be run async and may not use any Bukkit API functions. To use the Bukkit API
	 * use {@link AsyncManager#runSync(Runnable)} before using Bukkit API
	 * 
	 * @param result    The integer returned by
	 *                  {@link PreparedStatement#executeUpdate()}. This will be 0 if
	 *                  {@link PreparedStatement#executeUpdate()} returns 0 or if an
	 *                  {@link Exception} occurred
	 * @param exception {@link Exception} if something went wrong or
	 *                  <code>null</code> if successful
	 */
	public void onExecute(int result, Exception exception);
}