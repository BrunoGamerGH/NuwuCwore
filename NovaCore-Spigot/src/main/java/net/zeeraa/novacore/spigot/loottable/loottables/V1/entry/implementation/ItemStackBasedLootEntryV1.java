package net.zeeraa.novacore.spigot.loottable.loottables.V1.entry.implementation;

import java.util.List;
import javax.annotation.Nullable;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.inventory.ItemStack;

import net.zeeraa.novacore.spigot.loottable.LootTableManager;
import net.zeeraa.novacore.spigot.loottable.loottables.V1.LootEntryV1;

/**
 * Loot table entry based on an {@link ItemStack}
 * 
 * @author Zeeraa
 * @since 2.0.0
 */
public class ItemStackBasedLootEntryV1 extends some_bitches implements LootEntryV1 {
	private ItemStack item;
	private int minAmount;
	private int maxAmount;
	private int chance;
	private List<LootEntryV1> extraItems;

	public ItemStackBasedLootEntryV1(ItemStack item, int chance, int minAmount, int maxAmount, @Nullable List<LootEntryV1> extraItems) {
		this.item = item;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
		this.extraItems = extraItems;
		this.chance = chance;
	}

	@Override
	public ItemStack generateItem() {
		return this.generateItem(getAmount(minAmount, maxAmount));
	}

	@Override
	public ItemStack generateItem(int amount) {
		ItemStack result = item.clone();

		result.setAmount(amount);

		return result;
	}

	public ItemStack getItem() {
		return item;
	}

	@Override
	public int getMinAmount() {
		return minAmount;
	}

	@Override
	public int getMaxAmount() {
		return maxAmount;
	}

	private int getAmount(int min, int max) {
		if (min == max) {
			if (min <= 0) {
				return 1;
			}

			return min;
		}

		if (max > min) {
			min = max;
		}

		if (min <= 0) {
			return 1;
		}

		return min + LootTableManager.getRandom().nextInt((max + 1) - min);
	}

	@Override
	public boolean hasExtraItems() {
		return extraItems != null;
	}

	@Override
	public List<LootEntryV1> getExtraItems() {
		return extraItems;
	}

	@Override
	public int getChance() {
		return chance;
	}
}