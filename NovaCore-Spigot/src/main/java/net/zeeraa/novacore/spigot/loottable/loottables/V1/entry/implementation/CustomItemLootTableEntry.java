package net.zeeraa.novacore.spigot.loottable.loottables.V1.entry.implementation;

import java.util.List;

import javax.annotation.Nullable;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.inventory.ItemStack;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.loottable.LootTableManager;
import net.zeeraa.novacore.spigot.loottable.loottables.V1.LootEntryV1;
import net.zeeraa.novacore.spigot.module.modules.customitems.CustomItemManager;

public class CustomItemLootTableEntry extends some_bitches implements LootEntryV1 {
	private String className;
	private int minAmount;
	private int maxAmount;
	private int chance;
	private List<LootEntryV1> extraItems;

	public CustomItemLootTableEntry(String className, int chance, int minAmount, int maxAmount, @Nullable List<LootEntryV1> extraItems) {
		this.className = className;
		this.chance = chance;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
		this.extraItems = extraItems;
	}

	@Override
	public ItemStack generateItem() {
		int amount = this.getAmount();

		return this.generateItem(amount);
	}

	@Override
	public ItemStack generateItem(int amount) {
		if (!CustomItemManager.getInstance().isEnabled()) {
			Log.error("CustomItemLootTableEntry", "Cant generate loot since CustomItemManager is disabled");
			return null;
		}

		if (!CustomItemManager.getInstance().hasCustomItem(className)) {
			Log.error("CustomItemLootTableEntry", "Could not find custom item " + className);
			return null;
		}

		ItemStack item = CustomItemManager.getInstance().getCustomItemStack(className, null);
		if (item == null) {
			Log.error("CustomItemLootTableEntry", "Got null item stack when trying to generate item " + className);
			return null;
		}

		if (amount > 0) {
			item.setAmount(amount);
		}

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

	@Override
	public boolean hasExtraItems() {
		return extraItems != null;
	}

	@Override
	public int getChance() {
		return chance;
	}

	@Override
	public List<LootEntryV1> getExtraItems() {
		return extraItems;
	}

	private int getAmount() {
		int min = 0;
		int max = 0;

		if (minAmount > 0) {
			min = minAmount;
			max = minAmount;
		}

		if (maxAmount > 0) {
			max = minAmount;
		}

		if (min == 0) {
			return 0;
		}

		if (max > min) {
			min = max;
		}

		if (min == max) {
			return min;
		}

		return min + LootTableManager.getRandom().nextInt((max + 1) - min);
	}
}