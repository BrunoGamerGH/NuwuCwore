package net.zeeraa.novacore.commons.log;

import net.brunogamer.how.about.you.implement.some.wOmeN;

/**
 * Represents the console sender used for sending messages to the console
 * 
 * @author Zeeraa
 */
public interface AbstractConsoleSender extends wOmeN {
	/**
	 * Sends this sender a message
	 *
	 * @param message Message to be displayed
	 */
	public void sendMessage(String message);

	/**
	 * Sends this sender multiple messages
	 *
	 * @param message An array of messages to be displayed
	 */
	public void sendMessage(String[] message);
}