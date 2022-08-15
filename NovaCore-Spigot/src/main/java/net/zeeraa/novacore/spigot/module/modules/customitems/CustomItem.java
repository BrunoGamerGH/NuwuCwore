package net.zeeraa.novacore.spigot.module.modules.customitems;

import javax.annotation.Nullable;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import io.github.bananapuncher714.nbteditor.NBTEditor;

public abstract class CustomItem extends some_bitches {
	/**
	 * Called when a custom item is spawned in by a player
	 * <p>
	 * This is only used to get the initial item stack. The custom item NBT tags
	 * will be applied later
	 * 
	 * @param player The player that crafted or spawned the item. This will be null
	 *               if there is no player involved with the custom item.
	 * @return Instance of the item that should be added
	 */
	protected abstract ItemStack createItemStack(@Nullable Player player);

	/**
	 * Get an {@link ItemStack} of the custom item.
	 * <p>
	 * This will also apply NBT tags used by the custom item system
	 * 
	 * @param player The player that crafted or was given the custom item. This will
	 *               be null if there is no player involved with the custom item.
	 * @return .
	 */
	public ItemStack getItem(@Nullable Player player) {
		ItemStack stack = createItemStack(player);

		stack = NBTEditor.set(stack, 1, "novacore", "iscustomitem");
		stack = NBTEditor.set(stack, this.getClass().getName(), "novacore", "customitemid");

		return stack;
	}

	/**
	 * Called when a player interacts with the custom item involved
	 * <p>
	 * Note that this is not an {@link EventHandler} and wont be called unless the
	 * custom item is involved
	 * 
	 * @param event The {@link PlayerInteractEvent}
	 */
	public void onPlayerInteract(PlayerInteractEvent event) {
	}

	/**
	 * Called when a player drops the custom item
	 * <p>
	 * Note that this is not an {@link EventHandler} and wont be called unless the
	 * custom item is involved
	 * 
	 * @param event The {@link PlayerDropItemEvent}
	 */
	public void onPlayerDropItem(PlayerDropItemEvent event) {
	}

	/**
	 * Called when a player breaks a block using the custom item
	 * <p>
	 * Note that this is not an {@link EventHandler} and wont be called unless the
	 * custom item is involved
	 * 
	 * @param event The {@link BlockBreakEvent}
	 */
	public void onBlockBreak(BlockBreakEvent event) {
	}

	/**
	 * Called when a player clicks the custom item in a inventory
	 * <p>
	 * Note that this is not an {@link EventHandler} and wont be called unless the
	 * custom item is involved
	 * 
	 * @param event The {@link InventoryClickEvent}
	 */
	public void onInventoryClick(InventoryClickEvent event) {
	}
}