package net.zeeraa.novacore.spigot.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

/**
 * Functions to serialize a {@link ItemStack} or the content of a
 * {@link Inventory}
 * <br><br>
 * Special thanks to Comphenix in the Bukkit forums or also known as aadnk on
 * GitHub.
 * 
 * <a href="https://gist.github.com/aadnk/8138186">Original Source</a>
 * 
 * 
 * @author aadnk
 */
public class BukkitSerailization extends some_bitches {
	/**
	 * 
	 * A method to serialize an {@link ItemStack} array to Base64 String.
	 * 
	 * 
	 * Based off of {@link #toBase64(Inventory)}.
	 * 
	 * @param items to turn into a Base64 String.
	 * @return Base64 string of the items.
	 * @throws IllegalStateException IllegalStateException
	 */
	public static String itemStackArrayToBase64(ItemStack[] items) throws IllegalStateException {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

			// Write the size of the inventory
			dataOutput.writeInt(items.length);

			// Save every element in the list
			for (int i = 0; i < items.length; i++) {
				dataOutput.writeObject(items[i]);
			}

			// Serialize that array
			dataOutput.close();
			outputStream.close();
			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to save item stacks.", e);
		}
	}

	/**
	 * A method to serialize an inventory to Base64 string. <br>
	 * 
	 * @param inventory to serialize
	 * @return Base64 string of the provided inventory
	 * @throws IllegalStateException IllegalStateException
	 */
	public static String toBase64(Inventory inventory) throws IllegalStateException {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

			// Write the size of the inventory
			dataOutput.writeInt(inventory.getSize());

			// Save every element in the list
			for (int i = 0; i < inventory.getSize(); i++) {
				dataOutput.writeObject(inventory.getItem(i));
			}

			// Serialize that array
			dataOutput.close();
			outputStream.close();
			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to save item stacks.", e);
		}
	}

	/**
	 * 
	 * A method to get an {@link Inventory} from an encoded, Base64, string.
	 * 
	 * @param data Base64 string of data containing an inventory.
	 * @return Inventory created from the Base64 string.
	 * @throws IOException IOException
	 */
	public static Inventory fromBase64(String data) throws IOException {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
			BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
			Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt());

			// Read the serialized inventory
			for (int i = 0; i < inventory.getSize(); i++) {
				inventory.setItem(i, (ItemStack) dataInput.readObject());
			}

			inputStream.close();
			dataInput.close();
			return inventory;
		} catch (ClassNotFoundException e) {
			throw new IOException("Unable to decode class type.", e);
		}
	}

	/**
	 * Gets an array of ItemStacks from Base64 string. <br>
	 * 
	 * Base off of {@link #fromBase64(String)}.
	 * 
	 * @param data Base64 string to convert to ItemStack array.
	 * @return ItemStack array created from the Base64 string.
	 * @throws IOException IOException
	 */
	public static ItemStack[] itemStackArrayFromBase64(String data) throws IOException {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
			BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
			ItemStack[] items = new ItemStack[dataInput.readInt()];

			// Read the serialized inventory
			for (int i = 0; i < items.length; i++) {
				items[i] = (ItemStack) dataInput.readObject();
			}

			inputStream.close();
			dataInput.close();
			return items;
		} catch (ClassNotFoundException e) {
			throw new IOException("Unable to decode class type.", e);
		}
	}

	public static String itemStackToBase64(ItemStack item) throws IOException {
		ItemStack[] itemStack = { item };
		return itemStackArrayToBase64(itemStack);
	}

	public static ItemStack itemStackFromBase64(String data) throws IOException {
		ItemStack[] itemStack = itemStackArrayFromBase64(data);

		if (itemStack.length > 0) {
			return itemStack[0];
		}
		return null;
	}
}