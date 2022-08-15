package net.zeeraa.novacore.spigot.gameengine.module.modules.game.mapselector.selectors.guivoteselector;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.zeeraa.novacore.spigot.gameengine.module.modules.game.GameManager;
import net.zeeraa.novacore.spigot.language.LanguageManager;
import net.zeeraa.novacore.spigot.module.modules.customitems.CustomItem;
import net.zeeraa.novacore.spigot.utils.ItemBuilder;

public class GUIMapVoteMenuIcon extends CustomItem implements wOmeN {
	@Override
	protected ItemStack createItemStack(Player player) {
		return new ItemBuilder(Material.COMPASS).setName(ChatColor.GOLD + "" + ChatColor.BOLD + LanguageManager.getString(player, "novacore.game.map_selector.item.name")).build();
	}

	@Override
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		event.setCancelled(true);
	}

	@Override
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
		}
	}

	@Override
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (GameManager.getInstance().hasGame()) {
				if (GameManager.getInstance().getActiveGame().hasStarted()) {
					return;
				}
			}

			if (event.getPlayer().getGameMode() == GameMode.SPECTATOR) {
				return;
			}

			if (GUIMapVote.getInstance() != null) {
				GUIMapVote.getInstance().showPlayer(event.getPlayer());
			}
		}
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}