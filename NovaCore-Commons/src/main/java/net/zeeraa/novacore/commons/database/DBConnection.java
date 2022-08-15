package net.zeeraa.novacore.commons.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.brunogamer.how.about.you.get.some_bitches;
import net.zeeraa.novacore.commons.NovaCommons;
import net.zeeraa.novacore.commons.async.AsyncManager;
import net.zeeraa.novacore.commons.database.async.ExecuteQueryAsyncCallback;
import net.zeeraa.novacore.commons.database.async.ExecuteUpdateAsyncCallback;
import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.commons.tasks.Task;

/**
 * Represents a connection to a MySQL server
 * 
 * @author Zeeraa
 */
public class DBConnection extends some_bitches {
	protected Connection connection;

	protected DBCredentials credentials;

	protected Task keepAliveTask;

	public DBConnection() {
		this.connection = null;
		this.keepAliveTask = NovaCommons.getAbstractSimpleTaskCreator().createTask(new Runnable() {
			@Override
			public void run() {
				Log.trace("DBC", "Sending keep alive query to database " + credentials.getHost());
				testQuery();
			}
		}, 36000L, 36000L);
	}

	/**
	 * Connects to the database using the com.mysql.jdbc.Driver as driver
	 * 
	 * @param host     host to connect to
	 * @param username user name of the MySQL user
	 * @param password password of the MySQL user
	 * @param database name of the database to use
	 * @return <code>true</code> on success or <code>false</code> on failure
	 * @throws ClassNotFoundException ClassNotFoundException
	 * @throws SQLException           SQLException
	 */
	public boolean connect(String host, String username, String password, String database) throws ClassNotFoundException, SQLException {
		return this.connect(host, username, password, database, "com.mysql.jdbc.Driver");
	}

	/**
	 * Connects to the database
	 * 
	 * @param host     host to connect to
	 * @param username user name of the MySQL user
	 * @param password password of the MySQL user
	 * @param database name of the database to use
	 * @param driver   driver to use
	 * @return <code>true</code> on success or <code>false</code> on failure
	 * @throws ClassNotFoundException ClassNotFoundException
	 * @throws SQLException           SQLException
	 */
	public boolean connect(String host, String username, String password, String database, String driver) throws ClassNotFoundException, SQLException {
		DBCredentials credentials = new DBCredentials(driver, host, username, password, database);

		return this.connect(credentials);
	}

	/**
	 * Connects to the database using the provided {@link DBCredentials}
	 * 
	 * @param credentials The {@link DBCredentials} to use
	 * @return <code>true</code> on success or <code>false</code> on failure
	 * @throws ClassNotFoundException ClassNotFoundException
	 * @throws SQLException           SQLException
	 */
	public boolean connect(DBCredentials credentials) throws ClassNotFoundException, SQLException {
		if (connection != null) {
			if (!connection.isClosed()) {
				return false;
			}
		}

		this.credentials = credentials;

		Log.info("DBC", "Connecting to database " + credentials.getHost() + " using database driver: " + credentials.getDriver());

		Class.forName(credentials.getDriver());
		connection = DriverManager.getConnection(credentials.getHost(), credentials.getUsername(), credentials.getPassword());
		connection.setCatalog(credentials.getDatabase());
		return true;
	}

	/**
	 * Try to reconnect to the database with the last used credentials
	 * 
	 * @return <code>true</code> on success
	 * @throws ClassNotFoundException ClassNotFoundException
	 * @throws SQLException           SQLException
	 */
	public boolean reconnect() throws ClassNotFoundException, SQLException {
		Log.info("DBC", "Trying to reconnect to database " + credentials.getHost());
		try {
			if (this.isConnected()) {
				this.close();
			}
		} catch (Exception e) {
			Log.warn("DBC", "Failed to close the database while trying to reconnect");
			e.printStackTrace();
		}

		return this.connect(credentials);
	}

	/**
	 * Close the {@link Connection} to the database
	 * 
	 * @return <code>true</code> if the {@link Connection} was closed
	 * @throws SQLException SQLException
	 */
	public boolean close() throws SQLException {
		if (connection == null) {
			return false;
		}

		if (connection.isClosed()) {
			return false;
		}

		connection.close();
		return true;
	}

	/**
	 * Check if the connection is open
	 * 
	 * @return <code>true</code> if connected
	 * @throws SQLException SQLException
	 */
	public boolean isConnected() throws SQLException {
		if (connection != null) {
			return !connection.isClosed();
		}
		return false;
	}

	/**
	 * Try to run the query <code>SELECT 1;</code> and check for success
	 * 
	 * @return <code>true</code> on success, <code>false</code> on error
	 */
	public boolean testQuery() {
		try {
			String sql = "SELECT 1";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.executeQuery();
			ps.close();
		} catch (Exception ee) {
			ee.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Starts a task to execute the {@link DBConnection#testQuery()} every 30
	 * minutes to keep the connection alive
	 * 
	 * @return <code>true</code> if the task was started
	 */
	public boolean startKeepAliveTask() {
		if (keepAliveTask != null) {
			return keepAliveTask.start();
		}

		return false;
	}

	/**
	 * Stop the keep alive task
	 * 
	 * @return <code>true</code> if the task was ended
	 */
	public boolean endKeepAliveTask() {
		if (keepAliveTask != null) {
			return keepAliveTask.stop();
		}

		return false;
	}

	/**
	 * Check if the keep alive task is running
	 * 
	 * @return <code>true</code> if the task is running
	 */
	public boolean isKeepAliveTaskRunning() {
		if (keepAliveTask != null) {
			return keepAliveTask.isRunning();
		}
		return false;
	}

	/**
	 * Get instance of the connection
	 * 
	 * @return {@link Connection} instance
	 */
	public Connection getConnection() {
		return connection;
	}

	public static void executeQueryAsync(PreparedStatement ps, ExecuteQueryAsyncCallback callback) {
		AsyncManager.runAsync(new Runnable() {
			@Override
			public void run() {
				try {
					ResultSet rs = ps.executeQuery();

					callback.onExecute(rs, null);
				} catch (Exception e) {
					callback.onExecute(null, e);
				}
			}
		});
	}

	public static void executeUpdateAsync(PreparedStatement ps, ExecuteUpdateAsyncCallback callback) {
		AsyncManager.runAsync(new Runnable() {
			@Override
			public void run() {
				try {
					int result = ps.executeUpdate();

					callback.onExecute(result, null);
				} catch (Exception e) {
					callback.onExecute(0, e);
				}
			}
		});
	}
}