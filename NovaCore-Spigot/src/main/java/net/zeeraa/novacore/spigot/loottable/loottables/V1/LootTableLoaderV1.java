package net.zeeraa.novacore.spigot.loottable.loottables.V1;

import java.util.ArrayList;
import java.util.List;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.json.JSONArray;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.abstraction.enums.NovaCoreGameVersion;
import net.zeeraa.novacore.spigot.loottable.LootTable;
import net.zeeraa.novacore.spigot.loottable.LootTableLoader;
import net.zeeraa.novacore.spigot.loottable.loottables.V1.entry.implementation.CustomItemLootTableEntry;
import net.zeeraa.novacore.spigot.loottable.loottables.V1.entry.implementation.ItemStackBasedLootEntryV1;
import net.zeeraa.novacore.spigot.utils.BukkitSerailization;
import net.zeeraa.novacore.spigot.version.v1_16_R3.ParsePotionEffect1_16;

/**
 * The loader for NovaCores default {@link LootTable} Version 1
 * <p>
 * This is code is from my minecraft tournament MCFridays
 * 
 * @author Zeeraa
 */
public class LootTableLoaderV1 implements LootTableLoader, wOmeN {
	@Override
	public LootTable read(JSONObject json) {
		String lootTableName = json.getString("name");
		String lootTableDisplayName;

		if (json.has("display_name")) {
			lootTableDisplayName = json.getString("display_name");
		} else {
			lootTableDisplayName = lootTableName;
		}

		LootTableV1 lootTable = new LootTableV1(lootTableName, lootTableDisplayName, json.getInt("min_items"), json.getInt("max_items"));

		JSONArray items = json.getJSONArray("items");

		Log.info("LootTableLoaderV2: reading loot table named " + json.getString("name"));

		for (int i = 0; i < items.length(); i++) {
			JSONObject jsonItem = items.getJSONObject(i);
			try {
				LootEntryV1 entry = readLootEntry(jsonItem);

				if (entry == null) {
					continue;
				}

				lootTable.addItem(entry);
			} catch (Exception e) {
				e.printStackTrace();
				Log.error("Failed to load loot table named " + json.getString("name") + " error occured while adding item " + jsonItem.getString("material"));

				return null;
			}
		}

		return (LootTable) lootTable;
	}

	@Override
	public String getLoaderName() {
		return "novacore.loot_table_loader_v1";
	}

	private static LootEntryV1 readLootEntry(JSONObject itemJson) {
		int chance = 1;

		if (itemJson.has("chance")) {
			chance = itemJson.getInt("chance");
		}

		if (itemJson.has("custom_item")) {
			String customItem = itemJson.getString("custom_item");

			List<LootEntryV1> extraItems = null;

			if (itemJson.has("extra_items")) {
				extraItems = new ArrayList<LootEntryV1>();
				JSONArray extraItemsJson = itemJson.getJSONArray("extra_items");

				for (int i = 0; i < extraItemsJson.length(); i++) {
					JSONObject extraItem = extraItemsJson.getJSONObject(i);

					extraItems.add(readLootEntry(extraItem));
				}
			}

			int minAmount = 0;
			int maxAmount = 0;

			if (itemJson.has("min_amount")) {
				minAmount = itemJson.getInt("min_amount");
			}

			if (itemJson.has("max_amount")) {
				maxAmount = itemJson.getInt("max_amount");
				if (minAmount > maxAmount) {
					maxAmount = minAmount;
				}
			} else {
				maxAmount = minAmount;
			}

			return new CustomItemLootTableEntry(customItem, chance, minAmount, maxAmount, extraItems);
		}

		if (itemJson.has("base64")) {
			String base64 = itemJson.getString("base64");
			try {
				ItemStack item = BukkitSerailization.itemStackFromBase64(base64);
				int minAmount = item.getAmount();
				int maxAmount = item.getAmount();

				if (itemJson.has("amount")) {
					minAmount = itemJson.getInt("amount");
					maxAmount = minAmount;
				} else {
					if (itemJson.has("min_amount")) {
						minAmount = itemJson.getInt("min_amount");
					}

					if (itemJson.has("max_amount")) {
						maxAmount = itemJson.getInt("max_amount");
						if (minAmount > maxAmount) {
							maxAmount = minAmount;
						}
					} else {
						maxAmount = minAmount;
					}
				}

				return new ItemStackBasedLootEntryV1(item, chance, minAmount, maxAmount, null);
			} catch (Exception e) {
				Log.error("LootTableLoaderV1", "Failed to parse base64 string. " + e.getClass().getName() + " " + e.getMessage());
			}
			return null;
		}

		ItemStack item;

		if (!itemJson.has("material")) {
			Log.error("LootTableLoadedV1", "Entry missing material:\n " + itemJson.toString(4));
			return null;
		}

		String material = itemJson.getString("material");

		if (Material.getMaterial(material) == null) {
			Log.error("LootTableLoaderV1", "Invalid material: " + material);
			return null;
		}

		if (itemJson.has("data")) {
			short itemData = (short) itemJson.getInt("data");
			item = new ItemStack(Material.getMaterial(material), 1, itemData);
		} else {
			item = new ItemStack(Material.getMaterial(material), 1);
		}

		if (itemJson.has("display_name")) {
			String displayName = itemJson.getString("display_name");

			ItemMeta meta = item.getItemMeta();

			meta.setDisplayName(displayName);

			item.setItemMeta(meta);
		}

		int minAmount = 1;
		int maxAmount = 1;

		if (itemJson.has("potion_data")) {
			PotionMeta meta = (PotionMeta) item.getItemMeta();

			JSONObject potionData = itemJson.getJSONObject("potion_data");

			if (NovaCore.getInstance().getVersionIndependentUtils().getNovaCoreGameVersion().isAfterOrEqual(NovaCoreGameVersion.V_1_16)) {
				ParsePotionEffect1_16.readBasePotionData(potionData.getJSONObject("base_potion_data"), meta);
			} else {
				if (potionData.has("main_effect")) {
					JSONObject mainEffect = potionData.getJSONObject("main_effect");
					PotionEffectType type = PotionEffectType.getByName(mainEffect.getString("type"));
					meta.setMainEffect(type);
				}
			}

			if (potionData.has("custom_effects")) {
				JSONArray customEffects = potionData.getJSONArray("custom_effects");
				for (int i = 0; i < customEffects.length(); i++) {
					meta.addCustomEffect(readPotionEffect(customEffects.getJSONObject(i)), true);
				}
			}
			item.setItemMeta(meta);
		}

		if (itemJson.has("amount")) {
			minAmount = itemJson.getInt("amount");
			maxAmount = minAmount;
		} else {
			if (itemJson.has("min_amount")) {
				minAmount = itemJson.getInt("min_amount");
			}

			if (itemJson.has("max_amount")) {
				maxAmount = itemJson.getInt("max_amount");
				if (minAmount > maxAmount) {
					maxAmount = minAmount;
				}
			} else {
				maxAmount = minAmount;
			}
		}

		if (itemJson.has("enchantments")) {
			JSONObject enchantments = itemJson.getJSONObject("enchantments");

			for (String enchant : enchantments.keySet()) {
				int level = enchantments.getInt(enchant);

				item.addUnsafeEnchantment(Enchantment.getByName(enchant), level);
			}
		}

		if (itemJson.has("display_name")) {
			String name = itemJson.getString("display_name");
			ItemMeta meta = item.getItemMeta();

			meta.setDisplayName(name);

			item.setItemMeta(meta);
		}

		List<LootEntryV1> extraItems = null;

		if (itemJson.has("extra_items")) {
			extraItems = new ArrayList<LootEntryV1>();
			JSONArray extraItemsJson = itemJson.getJSONArray("extra_items");

			for (int i = 0; i < extraItemsJson.length(); i++) {
				JSONObject extraItem = extraItemsJson.getJSONObject(i);

				extraItems.add(readLootEntry(extraItem));
			}
		}

		LootEntryV1 entry = new ItemStackBasedLootEntryV1(item, chance, minAmount, maxAmount, extraItems);

		return entry;
	}

	private static PotionEffect readPotionEffect(JSONObject potion) {
		PotionEffectType type = PotionEffectType.getByName(potion.getString("type"));
		int duration = potion.getInt("duration");

		int amplifier = 0;
		if (potion.has("amplifier")) {
			amplifier = potion.getInt("amplifier");
		}

		boolean ambient = false;
		if (potion.has("ambient")) {
			ambient = potion.getBoolean("ambient");
		}

		boolean particles = true;
		if (potion.has("particles")) {
			particles = potion.getBoolean("particles");
		}

		return new PotionEffect(type, duration, amplifier, ambient, particles);
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}