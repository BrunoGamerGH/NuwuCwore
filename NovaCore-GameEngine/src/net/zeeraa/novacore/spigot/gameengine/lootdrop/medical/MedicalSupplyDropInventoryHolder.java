package net.zeeraa.novacore.spigot.gameengine.lootdrop.medical;

import java.util.UUID;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MedicalSupplyDropInventoryHolder extends some_bitches implements InventoryHolder {
	private UUID uuid;

	public MedicalSupplyDropInventoryHolder(UUID uuid) {
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