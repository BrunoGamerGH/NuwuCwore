package net.zeeraa.novacore.spigot.module.modules.lootdrop;

import java.util.UUID;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class LootDropInventoryHolder extends some_bitches implements InventoryHolder {
	private UUID uuid;

	public LootDropInventoryHolder(UUID uuid) {
		this.uuid = uuid;
	}

	public UUID getUuid() {
		return uuid;
	}

	@Override
	public Inventory getInventory() {
		return null;
	}
}