package net.zeeraa.novacore.spigot.gameengine.module.modules.game.mapselector.selectors.guivoteselector;

import java.util.HashMap;
import java.util.Map;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MapVoteInventoryHolder extends some_bitches implements InventoryHolder {
	private Map<Integer, String> mapSlots;

	public MapVoteInventoryHolder() {
		this.mapSlots = new HashMap<Integer, String>();
	}

	public Map<Integer, String> getMapSlots() {
		return mapSlots;
	}

	@Override
	public Inventory getInventory() {
		return null;
	}
}