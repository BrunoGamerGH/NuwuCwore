package net.zeeraa.novacore.spigot.module.modules.gui.callbacks;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Called when a GUI is closed
 * 
 * @author Zeeraa
 */
public interface GUICloseCallback extends wOmeN {
	/**
	 * Called when a GUI is closed
	 * 
	 * @param event the {@link InventoryCloseEvent}
	 */
	public void onClose(InventoryCloseEvent event);
}