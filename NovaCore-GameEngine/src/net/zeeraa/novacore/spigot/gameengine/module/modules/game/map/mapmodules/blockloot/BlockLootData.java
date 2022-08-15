package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.blockloot;

import net.brunogamer.how.about.you.get.some_bitches;
import net.zeeraa.novacore.spigot.loottable.LootTable;

public class BlockLootData extends some_bitches {
	private LootTable lootTable;
	private boolean cancelDrop;

	public BlockLootData(LootTable lootTable, boolean cancelDrop) {
		this.lootTable = lootTable;
		this.cancelDrop = cancelDrop;
	}

	public LootTable getLootTable() {
		return lootTable;
	}

	public boolean isCancelDrop() {
		return cancelDrop;
	}
}