package net.zeeraa.novacore.spigot.gameengine.lootdrop.medical;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.loottable.LootTable;

public class MedicalSupplyDrop extends some_bitches {
	private Location location;
	private Inventory inventory;
	private UUID uuid;
	private String lootTable;
	private boolean removed;

	public MedicalSupplyDrop(Location location, String lootTable) {
		this.removed = false;
		this.uuid = UUID.randomUUID();
		this.location = location;
		this.inventory = Bukkit.createInventory(new MedicalSupplyDropInventoryHolder(uuid), 27, "Loot drop");

		this.lootTable = lootTable;
		
		this.fill();

		NovaCore.getInstance().getVersionIndependentUtils().setBlockAsPlayerSkull(this.location.getBlock());
		
		NBTEditor.setSkullTexture(this.location.getBlock(), "http://textures.minecraft.net/texture/f7c7df52b5e50badb61fed7212d979e63fe94f1bde02b2968c6b156a770126c");
	}

	public void fill() {
		LootTable lt = NovaCore.getInstance().getLootTableManager().getLootTable(lootTable);

		if (lt == null) {
			Log.warn("LootDrop", "No loot table named " + lootTable + " was found");
			return;
		}

		List<ItemStack> loot = lt.generateLoot();

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
	}

	public boolean isRemoved() {
		return removed;
	}

	public void scheduleRemove() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(NovaCore.getInstance(), new Runnable() {
			@Override
			public void run() {
				remove(true);
			}
		}, 80L);
	}

	public void remove() {
		this.remove(false);
	}

	public void remove(boolean dropItems) {
		removed = true;

		this.location.getBlock().setType(Material.AIR);

		for (Player p : this.location.getWorld().getPlayers()) {
			if (p.getOpenInventory() != null) {
				if (p.getOpenInventory().getTopInventory() != null) {
					if (p.getOpenInventory().getTopInventory().getHolder() instanceof MedicalSupplyDropInventoryHolder) {
						if (((MedicalSupplyDropInventoryHolder) p.getOpenInventory().getTopInventory().getHolder()).getUuid() == this.uuid) {
							p.closeInventory();
						}
					}
				}
			}
		}

		for (ItemStack i : this.inventory.getContents()) {
			if (i == null) {
				continue;
			}

			if (i.getType() == Material.AIR) {
				continue;
			}

			this.location.getWorld().dropItem(this.location, i);
		}

		this.inventory.clear();
	}

	public UUID getUuid() {
		return uuid;
	}

	public Location getLocation() {
		return location;
	}

	public World getWorld() {
		return this.location.getWorld();
	}

	public Inventory getInventory() {
		return inventory;
	}
}