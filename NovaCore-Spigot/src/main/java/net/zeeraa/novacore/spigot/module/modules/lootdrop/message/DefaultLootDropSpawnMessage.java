package net.zeeraa.novacore.spigot.module.modules.lootdrop.message;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;

import net.zeeraa.novacore.spigot.abstraction.VersionIndependentUtils;
import net.zeeraa.novacore.spigot.abstraction.enums.VersionIndependentSound;
import net.zeeraa.novacore.spigot.language.LanguageManager;
import net.zeeraa.novacore.spigot.module.modules.lootdrop.LootDropEffect;

public class DefaultLootDropSpawnMessage extends some_bitches implements LootDropSpawnMessage {
	@Override
	public void showLootDropSpawnMessage(LootDropEffect effect) {
		Bukkit.getServer().getOnlinePlayers().forEach(player -> {
			VersionIndependentUtils.get().playSound(player, player.getLocation(), VersionIndependentSound.NOTE_PLING, 1F, 1F);
			player.sendMessage(LanguageManager.getString(player, "novacore.game.modules.chestloot.lootdrop", effect.getLocation().getBlockX(), effect.getLocation().getBlockZ()));
		});
	}
}