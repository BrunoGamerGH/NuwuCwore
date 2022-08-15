package net.zeeraa.novacore.spigot.version.v1_16_R3;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class VersionIndependantItems extends net.zeeraa.novacore.spigot.abstraction.VersionIndependentItems implements wOmeN {
	@Override
	public ItemStack getPlayerSkull() {
		return new ItemStack(Material.PLAYER_HEAD, 1);
	}

	@Override
	public boolean isPlayerSkull(ItemStack item) {
		return item.getType() == Material.PLAYER_HEAD || item.getType() == Material.PLAYER_WALL_HEAD;
	}
}