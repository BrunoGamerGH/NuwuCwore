package net.zeeraa.novacore.spigot.module.modules.gui.callbacks;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;

import net.zeeraa.novacore.spigot.module.modules.gui.GUIAction;

/**
 * Called when a GUI is clicked
 * 
 * @author Zeeraa
 */
public interface GUIClickCallback extends wOmeN {
	/**
	 * Called when someone clicks a GUI. The return value is used to either allow or
	 * deny the interaction
	 * 
	 * @param clickedInventory The {@link Inventory} that was clicked
	 * @param inventory        The {@link Inventory}
	 * @param entity           The {@link HumanEntity} that clicked
	 * @param clickedSlot      The clicked slot number
	 * @param slotType         The clicked {@link SlotType}
	 * @param clickType        The {@link InventoryAction}
	 * @return return {@link GUIAction#NONE} or <code>null</code> to use the default
	 *         outcome and deny the interaction. use the other values to either
	 *         allow or deny the interaction
	 */
	public GUIAction onClick(Inventory clickedInventory, Inventory inventory, HumanEntity entity, int clickedSlot, SlotType slotType, InventoryAction clickType);
}