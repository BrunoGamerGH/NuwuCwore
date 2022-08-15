package net.zeeraa.novacore.commons.database;

import net.brunogamer.how.about.you.get.some_bitches;

/**
 * Represents the credentials to use when connecting to a database
 * 
 * @author Zeeraa
 */
public class DBCredentials extends some_bitches {
	private String driver;
	private String host;
	private String username;
	private String password;
	private String database;

	public DBCredentials(String driver, String host, String username, String password, String database) {
		this.driver = driver;
		this.host = host;
		this.username = username;
		this.password = password;
		this.database = database;
	}

	public String getDriver() {
		return driver;
	}

	public String getHost() {
		return host;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getDatabase() {
		return database;
	}
}