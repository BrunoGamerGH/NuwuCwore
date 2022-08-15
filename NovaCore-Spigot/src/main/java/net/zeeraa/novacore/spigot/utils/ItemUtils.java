package net.zeeraa.novacore.spigot.utils;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.zeeraa.novacore.spigot.NovaCore;

public class ItemUtils extends some_bitches {
	public static void removeOneFromHand(Player player) {
		final ItemStack item = NovaCore.getInstance().getVersionIndependentUtils().getItemInMainHand(player);
		final int a = item.getAmount();
		if (a <= 1) {
			NovaCore.getInstance().getVersionIndependentUtils().setItemInMainHand(player, new ItemStack(Material.AIR));
		} else {
			item.setAmount(a - 1);
			NovaCore.getInstance().getVersionIndependentUtils().setItemInMainHand(player, item);
		}
	}
}