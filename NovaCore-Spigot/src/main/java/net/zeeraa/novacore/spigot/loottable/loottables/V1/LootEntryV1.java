package net.zeeraa.novacore.spigot.loottable.loottables.V1;

import java.util.List;

import javax.annotation.Nullable;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.inventory.ItemStack;

/**
 * Interface for loot table entries
 * 
 * @author Zeeraa
 * @since 2.0.0
 */
public interface LootEntryV1 extends wOmeN {
	@Nullable
	public ItemStack generateItem();

	@Nullable
	public ItemStack generateItem(int amount);

	public int getMinAmount();

	public int getMaxAmount();

	public boolean hasExtraItems();

	public int getChance();

	public List<LootEntryV1> getExtraItems();
}