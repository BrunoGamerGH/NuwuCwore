package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.blockloot;

import java.util.HashMap;
import java.util.Map;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;
import net.zeeraa.novacore.spigot.loottable.LootTable;
import net.zeeraa.novacore.spigot.utils.LocationUtils;

public class BlockLoot extends MapModule implements Listener, wOmeN {
	private Map<Material, BlockLootData> materialData;
	private Game game;

	public BlockLoot(JSONObject json) {
		super(json);

		materialData = new HashMap<Material, BlockLootData>();

		json.keySet().forEach(key -> {
			try {
				Material material = Material.valueOf(key);

				JSONObject data = json.getJSONObject(key);

				boolean cancelDrop = false;

				if (data.has("cancel_drop")) {
					cancelDrop = data.getBoolean("cancel_drop");
				}

				if (!data.has("loot_table")) {
					Log.error("BlockLoot", "Missing key: loot_table for material: " + key);
					return;
				}

				String lootTableName = data.getString("loot_table");

				LootTable lootTable = NovaCore.getInstance().getLootTableManager().getLootTable(lootTableName);
				if (lootTable == null) {
					Log.error("BlockLoot", "Missing loot table: " + lootTableName + " for material: " + key);
					return;
				}

				BlockLootData blockLootData = new BlockLootData(lootTable, cancelDrop);

				materialData.put(material, blockLootData);
			} catch (IllegalArgumentException e) {
				Log.error("BlockLoot", "Unknown material: " + key);
			}
		});

		Log.info("BlockLoot", "Loaded loot tables for " + materialData.size() + " materials");
	}

	@Override
	public void onGameStart(Game game) {
		this.game = game;
		Bukkit.getServer().getPluginManager().registerEvents(this, game.getPlugin());
	}

	@Override
	public void onGameEnd(Game game) {
		HandlerList.unregisterAll(this);
		this.game = null;
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent e) {
		if (materialData.containsKey(e.getBlock().getType())) {
			BlockLootData data = materialData.get(e.getBlock().getType());

			if (data.isCancelDrop()) {
				e.setCancelled(true);
				e.getBlock().setType(Material.AIR);
			}

			// Log.trace("BlockLoot", "droping loot from loot table " +
			// data.getLootTable().getName() + ". Cancel drop: " + data.isCancelDrop());

			Location dropLocation = LocationUtils.centerLocation(e.getBlock().getLocation().clone());

			data.getLootTable().generateLoot(game.getRandom()).forEach(item -> dropLocation.getWorld().dropItem(dropLocation, item));
		}
	}
}