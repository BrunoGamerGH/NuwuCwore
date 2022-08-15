package net.zeeraa.novacore.spigot.module.modules.gui;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.module.NovaModule;
import net.zeeraa.novacore.spigot.module.modules.gui.callbacks.GUIClickCallback;
import net.zeeraa.novacore.spigot.module.modules.gui.callbacks.GUIClickCallbackWithEvent;
import net.zeeraa.novacore.spigot.module.modules.gui.holders.GUIHolder;
import net.zeeraa.novacore.spigot.module.modules.gui.holders.GUIReadOnlyHolder;

/**
 * This module is used to make inventory GUI menus
 * 
 * @author Zeeraa
 */
public class GUIManager extends NovaModule implements Listener, wOmeN {
	private static GUIManager instance;

	/**
	 * Get instance of {@link GUIManager}
	 * 
	 * @return Instance
	 */
	public static GUIManager getInstance() {
		return instance;
	}

	@Override
	public void onLoad() {
		instance = this;
	}

	public GUIManager() {
		super("NovaCore.GUIManager");
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getClickedInventory() != null) {
			if (e.getClickedInventory().getHolder() != null) {
				if (e.getClickedInventory().getHolder() instanceof GUIHolder) {
					try {
						GUIHolder holder = (GUIHolder) e.getClickedInventory().getHolder();

						GUIAction action = GUIAction.CANCEL_INTERACTION;

						for (GUIClickCallback gcc : holder.getClickCallbacks()) {
							GUIAction newAction = gcc.onClick(e.getClickedInventory(), e.getInventory(), e.getWhoClicked(), e.getSlot(), e.getSlotType(), e.getAction());
							if (!(newAction == null || newAction == GUIAction.NONE)) {
								action = newAction;
							}
						}
						
						for (GUIClickCallbackWithEvent gcc : holder.getClickEventCallbacks()) {
							GUIAction newAction = gcc.onClick(e);
							if (!(newAction == null || newAction == GUIAction.NONE)) {
								action = newAction;
							}
						}

						if (holder.getSlotClickCallbacks().containsKey(e.getSlot())) {
							for (GUIClickCallback gcc : holder.getSlotClickCallbacks().get(e.getSlot())) {
								GUIAction newAction = gcc.onClick(e.getClickedInventory(), e.getInventory(), e.getWhoClicked(), e.getSlot(), e.getSlotType(), e.getAction());
								if (!(newAction == null || newAction == GUIAction.NONE)) {
									action = newAction;
								}
							}
						}
						
						if (holder.getSlotClickEventCallbacks().containsKey(e.getSlot())) {
							for (GUIClickCallbackWithEvent gcc : holder.getSlotClickEventCallbacks().get(e.getSlot())) {
								GUIAction newAction = gcc.onClick(e);
								if (!(newAction == null || newAction == GUIAction.NONE)) {
									action = newAction;
								}
							}
						}

						if (holder instanceof GUIReadOnlyHolder) {
							e.setCancelled(true);
							return;
						}

						if (action == GUIAction.CANCEL_INTERACTION) {
							e.setCancelled(true);
						}
					} catch (Exception ex) {
						e.setCancelled(true);
						ex.printStackTrace();
						Log.error("GUIManager", "An exception occured while processing InventoryClickEvent. Canceling interaction to be on the safe side");
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onInventoryClose(InventoryCloseEvent e) {
		if (e.getInventory().getHolder() instanceof GUIHolder) {
			GUIHolder holder = (GUIHolder) e.getInventory().getHolder();

			holder.getCloseCallbacks().forEach(gcc -> gcc.onClose(e));
		}
	}
}