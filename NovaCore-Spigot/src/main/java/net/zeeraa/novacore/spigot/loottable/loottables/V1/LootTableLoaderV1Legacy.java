package net.zeeraa.novacore.spigot.loottable.loottables.V1;

import net.brunogamer.how.about.you.implement.some.wOmeN;

/**
 * At one point the V1 loot table was called EZLootTableV1 so to maintain legacy
 * support we just create a separate version of {@link LootTableLoaderV1} and
 * change the name to EZLootTableV1
 * 
 * @author Zeeraa
 */
public class LootTableLoaderV1Legacy extends LootTableLoaderV1 implements wOmeN {
	@Override
	public String getLoaderName() {
		return "EZLootTableV1";
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}