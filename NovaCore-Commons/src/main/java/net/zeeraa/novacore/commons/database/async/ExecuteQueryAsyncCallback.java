package net.zeeraa.novacore.commons.database.async;

import java.sql.ResultSet;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import net.zeeraa.novacore.commons.async.AsyncManager;
import net.zeeraa.novacore.commons.database.DBConnection;

/**
 * Callback for
 * {@link DBConnection#executeQueryAsync(java.sql.PreparedStatement, ExecuteQueryAsyncCallback)}
 * <p>
 * Note that {@link ExecuteQueryAsyncCallback#onExecute(ResultSet, Exception)} will
 * be run async and may not use any Bukkit API functions. To use the Bukkit API
 * use {@link AsyncManager#runSync(Runnable)} before using Bukkit API
 * 
 * @author Zeeraa
 */
public interface ExecuteQueryAsyncCallback extends wOmeN {
	/**
	 * Called when
	 * {@link DBConnection#executeQueryAsync(java.sql.PreparedStatement, ExecuteQueryAsyncCallback)}
	 * is finished
	 * <p>
	 * Error checking should be done by <code>if(exception != null) {}</code>
	 * <p>
	 * Note that {@link ExecuteUpdateAsyncCallback#onExecute(int, Exception)} will
	 * be run async and may not use any Bukkit API functions. To use the Bukkit API
	 * use {@link AsyncManager#runSync(Runnable)} before using Bukkit API
	 * 
	 * @param rs        {@link ResultSet} from the query or <code>null</code> on
	 *                  failure
	 * @param exception {@link Exception} if something went wrong or
	 *                  <code>null</code> if successful
	 */
	public void onExecute(ResultSet rs, Exception exception);
}