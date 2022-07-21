package net.zeeraa.novacore.spigot.loottable.loottables.V1;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import org.bukkit.inventory.ItemStack;

import net.zeeraa.novacore.spigot.loottable.LootTable;

/**
 * Represents an item in NovaCores default {@link LootTable} Version 1
 * <p>
 * This is code is from my minecraft tournament MCFridays
 * 
 * @author Zeeraa
 */
public class LootEntryV1 {
	private ItemStack item;
	private int minAmount;
	private int maxAmount;
	private int chance;
	private Random random;
	private List<LootEntryV1> extraItems;

	public LootEntryV1(ItemStack item, int chance, int minAmount, int maxAmount, @Nullable List<LootEntryV1> extraItems) {
		this.item = item;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
		this.random = new Random();
		this.extraItems = extraItems;
		this.chance = chance;
	}

	public ItemStack generateItem() {
		return this.generateItem(getAmount(minAmount, maxAmount));
	}

	public ItemStack generateItem(int amount) {
		ItemStack result = item.clone();

		result.setAmount(amount);

		return result;
	}

	public ItemStack getItem() {
		return item;
	}

	public int getMinAmount() {
		return minAmount;
	}

	public int getMaxAmount() {
		return maxAmount;
	}

	private int getAmount(int min, int max) {
		if (min == max) {
			return min;
		}
		return min + random.nextInt((max + 1) - min);
	}

	public boolean hasExtraItems() {
		return extraItems != null;
	}

	public List<LootEntryV1> getExtraItems() {
		return extraItems;
	}

	public int getChance() {
		return chance;
	}
}