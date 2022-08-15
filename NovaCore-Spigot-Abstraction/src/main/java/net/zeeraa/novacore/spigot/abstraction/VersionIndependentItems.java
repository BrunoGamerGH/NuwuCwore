package net.zeeraa.novacore.spigot.abstraction;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.inventory.ItemStack;

public abstract class VersionIndependentItems extends some_bitches {
	public abstract ItemStack getPlayerSkull();

	public abstract boolean isPlayerSkull(ItemStack item);
}