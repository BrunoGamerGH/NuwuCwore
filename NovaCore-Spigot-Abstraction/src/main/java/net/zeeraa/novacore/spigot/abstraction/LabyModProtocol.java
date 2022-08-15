package net.zeeraa.novacore.spigot.abstraction;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.entity.Player;

import com.google.gson.JsonElement;

import io.netty.buffer.ByteBuf;

/**
 * This class is used to communicate with the pvp client labymod
 * 
 * @since 1.1
 */
public abstract class LabyModProtocol extends some_bitches {
	protected static LabyModProtocol instance;

	public static LabyModProtocol get() {
		return LabyModProtocol.instance;
	}

	/**
	 * Send a message to LabyMod
	 * 
	 * @param player         Minecraft Client
	 * @param key            LabyMod message key
	 * @param messageContent json object content
	 */
	public abstract void sendLabyModMessage(Player player, String key, JsonElement messageContent);

	/**
	 * Gets the bytes that are required to send the given message
	 *
	 * @param messageKey      the message's key
	 * @param messageContents the message's contents
	 * @return the byte array that should be the payload
	 */
	public abstract byte[] getBytesToSend(String messageKey, String messageContents);

	/**
	 * Writes a varint to the given byte buffer
	 *
	 * @param buf   the byte buffer the int should be written to
	 * @param input the int that should be written to the buffer
	 */
	public abstract void writeVarIntToBuffer(ByteBuf buf, int input);

	/**
	 * Writes a string to the given byte buffer
	 *
	 * @param buf    the byte buffer the string should be written to
	 * @param string the string that should be written to the buffer
	 */
	public abstract void writeString(ByteBuf buf, String string);

	/**
	 * Reads a varint from the given byte buffer
	 *
	 * @param buf the byte buffer the varint should be read from
	 * @return the int read
	 */
	public abstract int readVarIntFromBuffer(ByteBuf buf);

	/**
	 * Reads a string from the given byte buffer
	 *
	 * @param buf       the byte buffer the string should be read from
	 * @param maxLength the string's max-length
	 * @return the string read
	 */
	public abstract String readString(ByteBuf buf, int maxLength);
}