package net.zeeraa.novacore.spigot.loottable;

import java.util.List;
import java.util.Random;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.inventory.ItemStack;

/**
 * Represents a loot table that can generate random loot
 * 
 * @author Zeeraa
 */
public abstract class LootTable extends some_bitches {
	protected String name;
	protected String displayName;
	protected int minItems;
	protected int maxItems;

	public LootTable(String name, int minItems, int maxItems) {
		this(name, name, minItems, maxItems);
	}

	public LootTable(String name, String displayName, int minItems, int maxItems) {
		this.name = name;
		this.displayName = displayName;
		this.minItems = minItems;
		this.maxItems = maxItems;
	}

	/**
	 * Get name of loot table
	 * 
	 * @return name {@link String}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get display name of loot table
	 * 
	 * @return display name {@link String}
	 */
	public String getDisplayName() {
		return displayName;
	}

	protected void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Get minimum items to add
	 * 
	 * @return minimum items
	 */
	public int getMinItems() {
		return minItems;
	}

	/**
	 * Get max items to add
	 * 
	 * @return max items
	 */
	public int getMaxItems() {
		return maxItems;
	}

	/**
	 * Generate loot using loot table
	 * 
	 * @return {@link List} with {@link ItemStack}
	 */
	public List<ItemStack> generateLoot() {
		return this.generateLoot(new Random());
	}

	/**
	 * Generate loot using loot table
	 * 
	 * @param random instance of {@link Random} to use
	 * @return {@link List} with {@link ItemStack}
	 */
	public List<ItemStack> generateLoot(Random random) {
		int count = minItems + random.nextInt((maxItems - minItems) + 1);
		return this.generateLoot(random, count);
	}

	/**
	 * Generate loot using loot table
	 * 
	 * @param count amount of {@link ItemStack} to generate
	 * @return {@link List} with {@link ItemStack}
	 */
	public List<ItemStack> generateLoot(int count) {
		return this.generateLoot(new Random(), count);
	}

	/**
	 * Generate loot using loot table
	 * 
	 * @param random instance of {@link Random} to use
	 * @param count  amount of {@link ItemStack} to generate
	 * @return {@link List} with {@link ItemStack}
	 */
	public abstract List<ItemStack> generateLoot(Random random, int count);
}