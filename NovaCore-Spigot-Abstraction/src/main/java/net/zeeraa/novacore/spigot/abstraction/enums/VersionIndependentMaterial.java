package net.zeeraa.novacore.spigot.abstraction.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.zeeraa.novacore.spigot.abstraction.VersionIndependentUtils;

/**
 * Represents a bukkit {@link Material} that changed over multiple version of
 * the game
 * 
 * @author Zeeraa
 */
public enum VersionIndependentMaterial { // ew enum
	/**
	 * Map that supports graphics
	 */
	FILLED_MAP,
	/**
	 * Version independent End stone
	 */
	END_STONE,
	/**
	 * Version independent Crafting table
	 */
	WORKBENCH,
	/**
	 * Version independent Normal or oak boat
	 */
	OAK_BOAT,
	/**
	 * Version independent Snowball
	 */
	SNOWBALL,
	/**
	 * Version independent Diamond shover
	 */
	DIAMOND_SHOVEL,
	/**
	 * Version independent Farmland or soil
	 */
	FARMLAND,
	/**
	 * Version independent Wooden axe
	 */
	WOODEN_AXE,
	/**
	 * Version independent Wooden hoe
	 */
	WOODEN_HOE,
	/**
	 * Version independent Wooden pickaxe
	 */
	WOODEN_PICKAXE,
	/**
	 * Version independent Wooden shovel
	 */
	WOODEN_SHOVEL,
	/**
	 * Version independent Wooden sword
	 */
	WOODEN_SWORD,
	/**
	 * Version independent Golden axe
	 */
	GOLDEN_AXE,
	/**
	 * Version independent Golden hoe
	 */
	GOLDEN_HOE,
	/**
	 * Version independent Golden pickaxe
	 */
	GOLDEN_PICKAXE,
	/**
	 * Version independent Golden shovel
	 */
	GOLDEN_SHOVEL,
	/**
	 * Version independent Golden sword
	 */
	GOLDEN_SWORD,
	/**
	 * WATCH, CLOCK
	 */
	WATCH;

	/**
	 * Get the bukkit {@link Material} for the currently used version of the game
	 * 
	 * @return Bukkit {@link Material}
	 */
	public Material toBukkitVersion() {
		return VersionIndependentUtils.get().getMaterial(this);
	}

	/**
	 * Create an {@link ItemStack} with the material
	 * 
	 * @return {@link ItemStack}
	 * @since 2.0.0
	 */
	public ItemStack toItemStack() {
		ItemStack stack = new ItemStack(this.toBukkitVersion());
		stack.setAmount(1);
		return stack;
	}
}