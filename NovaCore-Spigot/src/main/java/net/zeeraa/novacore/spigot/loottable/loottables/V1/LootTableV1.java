package net.zeeraa.novacore.spigot.loottable.loottables.V1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.inventory.ItemStack;

import net.zeeraa.novacore.spigot.loottable.LootTable;
import net.zeeraa.novacore.spigot.loottable.loottables.V1.entry.implementation.ItemStackBasedLootEntryV1;

/**
 * NovaCores default {@link LootTable} Version 1
 * <p>
 * This is code is from my minecraft tournament MCFridays
 * 
 * @author Zeeraa
 */
public class LootTableV1 extends LootTable implements wOmeN {
	private Map<UUID, LootEntryV1> items = new HashMap<UUID, LootEntryV1>();
	private List<UUID> lootChance = new ArrayList<UUID>();

	public void addItem(LootEntryV1 lootEntry) {
		UUID uuid = UUID.randomUUID();
		items.put(uuid, lootEntry);
		for (int i = 0; i < lootEntry.getChance(); i++) {
			lootChance.add(uuid);
		}
	}

	@Deprecated
	public void addItem(ItemStack itemStack, int chance, int minAmount, int maxAmount, ArrayList<LootEntryV1> extraItems) {
		if (chance <= 0) {
			return;
		}

		UUID uuid = UUID.randomUUID();

		LootEntryV1 lootEntry = new ItemStackBasedLootEntryV1(itemStack, chance, minAmount, maxAmount, extraItems);

		items.put(uuid, lootEntry);
		for (int i = 0; i < chance; i++) {
			lootChance.add(uuid);
		}
	}

	public LootTableV1(String name, int minItems, int maxItems) {
		super(name, minItems, maxItems);
	}

	public LootTableV1(String name, String displayName, int minItems, int maxItems) {
		super(name, displayName, minItems, maxItems);
	}

	@Override
	public ArrayList<ItemStack> generateLoot(Random random, int count) {
		ArrayList<ItemStack> result = new ArrayList<ItemStack>();

		for (int i = 0; i < count; i++) {
			int r = random.nextInt(lootChance.size());
			UUID lootUuid = lootChance.get(r);

			LootEntryV1 entry = items.get(lootUuid);

			ItemStack item = entry.generateItem();

			if (item != null) {
				result.add(item);
			}

			if (entry.hasExtraItems()) {
				result.addAll(getExtraItems(entry));
			}
		}

		return result;
	}

	private ArrayList<ItemStack> getExtraItems(LootEntryV1 lootEntry) {
		ArrayList<ItemStack> result = new ArrayList<ItemStack>();
		if (lootEntry.hasExtraItems()) {
			for (LootEntryV1 lootEntry2 : lootEntry.getExtraItems()) {
				if (lootEntry2.hasExtraItems()) {
					ArrayList<ItemStack> extra = this.getExtraItems(lootEntry2);
					result.addAll(extra);
				}
				result.add(lootEntry2.generateItem());
			}
		}

		return result;
	}
}