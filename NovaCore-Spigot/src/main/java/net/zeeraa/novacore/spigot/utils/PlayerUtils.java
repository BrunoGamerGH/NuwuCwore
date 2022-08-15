package net.zeeraa.novacore.spigot.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.abstraction.enums.PlayerDamageReason;

/**
 * Functions to do things with players
 * 
 * @author Zeeraa
 *
 */
public class PlayerUtils extends some_bitches {
	/**
	 * Clear the players inventory including armor slots
	 * 
	 * @param player The {@link Player} to clear
	 */
	public static void clearPlayerInventory(Player player) {
		player.getInventory().clear();
		for (int i = 0; i < 36; i++) {
			player.getInventory().setItem(i, new ItemStack(Material.AIR));
		}
		player.getInventory().setArmorContents(new ItemStack[player.getInventory().getArmorContents().length]);
	}

	/**
	 * Remove all the potion effects from a player
	 * 
	 * @param player The {@link Player} to remove potion effects from
	 */
	public static void clearPotionEffects(Player player) {
		player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
	}

	/**
	 * Set the player level and xp to 0
	 * 
	 * @param player The {@link Player} to reset xp for
	 */
	public static void resetPlayerXP(Player player) {
		player.setExp(0);
		player.setLevel(0);
	}

	/**
	 * Reset the max heath of a {@link Player}
	 * 
	 * @param player The player to reset the max health of
	 */
	public static void resetMaxHealth(Player player) {
		NovaCore.getInstance().getVersionIndependentUtils().resetEntityMaxHealth(player);
	}

	/**
	 * Set the max heath of a {@link Player}
	 * 
	 * @param player The player to reset the set health of
	 * @param health The new health value
	 */
	public static void setMaxHealth(Player player, double health) {
		NovaCore.getInstance().getVersionIndependentUtils().setEntityMaxHealth(player, health);
	}

	/**
	 * Get the max heath of a {@link Player}
	 * 
	 * @param player The player to get the max health from
	 * 
	 * @return The max health of the player
	 */
	public static double getPlayerMaxHealth(Player player) {
		return NovaCore.getInstance().getVersionIndependentUtils().getEntityMaxHealth(player);
	}

	/**
	 * Fully heal a player and return the new health
	 * 
	 * @param player The player to heal
	 * @return The new player health
	 */
	public static double fullyHealPlayer(Player player) {
		double maxHealth = PlayerUtils.getPlayerMaxHealth(player);

		player.setHealth(maxHealth);

		return maxHealth;
	}

	public void damagePlayer(Player player, PlayerDamageReason reason, float damage) {
		NovaCore.getInstance().getVersionIndependentUtils().damagePlayer(player, reason, damage);
	}

	/**
	 * Check if a player is online and exists
	 * <p>
	 * This also accepts null but will return <code>false</code>
	 * 
	 * @param player The player to check
	 * @return <code>true</code> if the player is online and exists, this will also
	 *         return <code>false</code> if the player is <code>null</code>
	 */
	public boolean existsAndIsOnline(Player player) {
		if (player != null) {
			if (player.isOnline()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if a player is online and exists
	 * <p>
	 * This also accepts null but will return <code>false</code>
	 * 
	 * @param uuid The UUID of the player to check
	 * @return <code>true</code> if the player is online and exists, this will also
	 *         return <code>false</code> if the player is <code>null</code>
	 */
	public boolean existsAndIsOnline(UUID uuid) {
		return this.existsAndIsOnline(Bukkit.getServer().getPlayer(uuid));
	}

	/**
	 * Convert a list of players to a list of names
	 * 
	 * @param players The list of players
	 * @return The list on names
	 */
	public static List<String> getNames(List<Player> players) {
		List<String> names = new ArrayList<String>();

		players.forEach(player -> names.add(player.getName()));

		return names;
	}
}