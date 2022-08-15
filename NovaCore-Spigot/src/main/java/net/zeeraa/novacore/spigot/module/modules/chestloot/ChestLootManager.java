package net.zeeraa.novacore.spigot.module.modules.chestloot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.abstraction.VersionIndependentUtils;
import net.zeeraa.novacore.spigot.abstraction.enums.VersionIndependentSound;
import net.zeeraa.novacore.spigot.language.LanguageManager;
import net.zeeraa.novacore.spigot.loottable.LootTable;
import net.zeeraa.novacore.spigot.module.NovaModule;
import net.zeeraa.novacore.spigot.module.modules.chestloot.events.ChestFillEvent;

public class ChestLootManager extends NovaModule implements Listener, wOmeN {
	private static ChestLootManager instance;

	private ArrayList<Location> chests;
	private HashMap<Location, Inventory> enderChests;

	private BlockFace chestBlockFaces[] = { BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH };

	private String chestLootTable;
	private String enderChestLootTable;

	public static ChestLootManager getInstance() {
		return instance;
	}

	public ChestLootManager() {
		super("NovaCore.ChestLootManager");
		ChestLootManager.instance = this;
		this.enderChests = new HashMap<Location, Inventory>();
		this.chests = new ArrayList<Location>();
		this.chestLootTable = null;
		this.enderChestLootTable = null;
	}

	public void refillChests() {
		refillChests(false);
	}

	public void refillChests(boolean announce) {
		enderChests.clear();
		chests.clear();
		if (announce) {
			
			Bukkit.getOnlinePlayers().forEach(player -> {
				player.sendMessage(LanguageManager.getString(player, "novacore.game.modules.chestloot.refill"));
				VersionIndependentUtils.get().playSound(player, player.getLocation(), VersionIndependentSound.NOTE_PLING, 1F, 1F);
			});
		}
	}

	public String getChestLootTable() {
		return chestLootTable;
	}

	public void setChestLootTable(String chestLootTable) {
		this.chestLootTable = chestLootTable;
	}

	public String getEnderChestLootTable() {
		return enderChestLootTable;
	}

	public void setEnderChestLootTable(String enderChestLootTable) {
		this.enderChestLootTable = enderChestLootTable;
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType() == Material.TRAPPED_CHEST) {
				fillChest(e.getClickedBlock());
			} else if (e.getClickedBlock().getType() == Material.ENDER_CHEST) {
				if (enderChestLootTable != null) {
					e.setCancelled(true);
					Player p = e.getPlayer();

					if (e.getClickedBlock() != null) {
						Log.trace("Filling ender chest at location " + e.getClickedBlock().getLocation().toString());

						if (!enderChests.containsKey(e.getClickedBlock().getLocation())) {
							Inventory inventory = Bukkit.createInventory(new EnderChestHolder(), 27, "Ender chest");

							LootTable lootTable = NovaCore.getInstance().getLootTableManager().getLootTable(enderChestLootTable);

							if (lootTable == null) {
								Log.warn("Missing loot table " + enderChestLootTable);
								return;
							}

							ChestFillEvent event = new ChestFillEvent(e.getClickedBlock(), lootTable, ChestType.ENDERCHEST);

							Bukkit.getServer().getPluginManager().callEvent(event);

							if (event.isCancelled()) {
								return;
							}

							if (event.hasLootTableChanged()) {
								lootTable = event.getLootTable();
							}

							inventory.clear();

							List<ItemStack> loot = lootTable.generateLoot();

							inventory.clear();

							while (loot.size() > inventory.getSize()) {
								loot.remove(0);
							}

							while (loot.size() > 0) {
								Random random = new Random();

								int slot = random.nextInt(inventory.getSize());

								if (inventory.getItem(slot) == null) {
									ItemStack item = loot.remove(0);
									inventory.setItem(slot, item);
								}
							}

							enderChests.put(e.getClickedBlock().getLocation(), inventory);
						}

						p.openInventory(enderChests.get(e.getClickedBlock().getLocation()));
					}
				}
			}
		}
	}

	private void fillChest(Block block) {
		if (block.getState() instanceof Chest) {
			if (chestLootTable != null) {
				if (!chests.contains(block.getLocation())) {
					Log.trace("Filling chest at location " + block.getLocation().toString());

					LootTable lootTable = NovaCore.getInstance().getLootTableManager().getLootTable(chestLootTable);

					if (lootTable == null) {
						Log.warn("Missing loot table " + chestLootTable);
						return;
					}

					chests.add(block.getLocation());

					Chest chest = (Chest) block.getState();

					Inventory inventory = chest.getBlockInventory();

					ChestFillEvent event = new ChestFillEvent(block, lootTable, ChestType.CHEST);

					Bukkit.getServer().getPluginManager().callEvent(event);

					if (event.isCancelled()) {
						return;
					}

					if (event.hasLootTableChanged()) {
						lootTable = event.getLootTable();
					}

					inventory.clear();

					List<ItemStack> loot = lootTable.generateLoot();

					inventory.clear();

					while (loot.size() > inventory.getSize()) {
						loot.remove(0);
					}

					while (loot.size() > 0) {
						Random random = new Random();

						int slot = random.nextInt(inventory.getSize());

						if (inventory.getItem(slot) == null) {
							ItemStack item = loot.remove(0);
							inventory.setItem(slot, item);
						}
					}
					
					for (BlockFace face : chestBlockFaces) {
						Block nextBlock = block.getRelative(face);

						if (nextBlock.getType() == Material.CHEST || nextBlock.getType() == Material.TRAPPED_CHEST) {
							if (!chests.contains(nextBlock.getLocation())) {
								Log.trace("Executing recursive fill to chest at location " + nextBlock.getLocation().toString());
								this.fillChest(nextBlock);
							}
						}
					}
				}
			}
		}
	}
}