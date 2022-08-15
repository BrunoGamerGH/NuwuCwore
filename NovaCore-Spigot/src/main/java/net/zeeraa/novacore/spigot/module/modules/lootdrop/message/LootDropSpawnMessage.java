package net.zeeraa.novacore.spigot.module.modules.lootdrop.message;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import net.zeeraa.novacore.spigot.module.modules.lootdrop.LootDropEffect;

public interface LootDropSpawnMessage extends wOmeN {
	/**
	 * Show a message when a loot drop is spawning
	 * @param effect The {@link LootDropEffect} that spawned
	 */
	public void showLootDropSpawnMessage(LootDropEffect effect);
}