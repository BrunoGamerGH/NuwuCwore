package net.zeeraa.novacore.spigot.module.modules.gui.holders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import net.zeeraa.novacore.spigot.module.modules.gui.GUIManager;
import net.zeeraa.novacore.spigot.module.modules.gui.callbacks.GUIClickCallback;
import net.zeeraa.novacore.spigot.module.modules.gui.callbacks.GUIClickCallbackWithEvent;
import net.zeeraa.novacore.spigot.module.modules.gui.callbacks.GUICloseCallback;

/**
 * Add this holder to and {@link Inventory} to listen for click and close
 * events. This only works if the module {@link GUIManager} is enabled
 * 
 * @author Zeeraa
 */
public class GUIHolder extends some_bitches implements InventoryHolder {
	private List<GUICloseCallback> closeCallbacks = new ArrayList<>();

	private List<GUIClickCallback> clickCallbacks = new ArrayList<>();
	private Map<Integer, List<GUIClickCallback>> slotClickCallbacks = new HashMap<Integer, List<GUIClickCallback>>();

	private List<GUIClickCallbackWithEvent> clickEventCallbacks = new ArrayList<>();
	private Map<Integer, ArrayList<GUIClickCallbackWithEvent>> slotClickEventCallbacks = new HashMap<Integer, ArrayList<GUIClickCallbackWithEvent>>();

	/**
	 * Get close callback list
	 * 
	 * @return {@link List} with {@link GUICloseCallback}
	 */
	public List<GUICloseCallback> getCloseCallbacks() {
		return closeCallbacks;
	}

	/**
	 * Get click callback list
	 * 
	 * @return {@link List} with {@link GUIClickCallback}
	 */
	public List<GUIClickCallback> getClickCallbacks() {
		return clickCallbacks;
	}

	/**
	 * Get click callback list
	 * 
	 * @return {@link List} with {@link GUIClickCallbackWithEvent}
	 */
	public List<GUIClickCallbackWithEvent> getClickEventCallbacks() {
		return clickEventCallbacks;
	}

	/**
	 * Get a {@link Map} with slot specific {@link GUIClickCallback}s
	 * 
	 * @return {@link Map} with slot specific {@link GUIClickCallback}s
	 */
	public Map<Integer, List<GUIClickCallback>> getSlotClickCallbacks() {
		return slotClickCallbacks;
	}

	/**
	 * Get a {@link Map} with slot specific {@link GUIClickCallbackWithEvent}s
	 * 
	 * @return {@link Map} with slot specific {@link GUIClickCallbackWithEvent}s
	 */
	public Map<Integer, ArrayList<GUIClickCallbackWithEvent>> getSlotClickEventCallbacks() {
		return slotClickEventCallbacks;
	}

	/**
	 * Add a {@link GUICloseCallback} to be executed when the inventory is closed
	 * 
	 * @param guiCloseCallback {@link GUICloseCallback} to add
	 */
	public void addCloseCallback(GUICloseCallback guiCloseCallback) {
		closeCallbacks.add(guiCloseCallback);
	}

	/**
	 * Add a {@link GUIClickCallback} to be executed when the inventory is clicked
	 * 
	 * @param guiClickCallback {@link GUIClickCallback} to add
	 */
	public void addClickCallback(GUIClickCallback guiClickCallback) {
		clickCallbacks.add(guiClickCallback);
	}

	/**
	 * Add a {@link GUIClickCallbackWithEvent} to be executed when the inventory is
	 * clicked
	 * 
	 * @param guiClickCallback {@link GUIClickCallbackWithEvent} to add
	 */
	public void addClickCallback(GUIClickCallbackWithEvent guiClickCallback) {
		clickEventCallbacks.add(guiClickCallback);
	}

	/**
	 * Add a {@link GUIClickCallback} to be executed when a specific slot in the
	 * inventory is clicked
	 * 
	 * @param slot             The slot number to bind the callback to
	 * @param guiClickCallback {@link GUIClickCallback} to add
	 */
	public void addClickCallback(int slot, GUIClickCallback guiClickCallback) {
		if (!slotClickCallbacks.containsKey(slot)) {
			slotClickCallbacks.put(slot, new ArrayList<GUIClickCallback>());
		}

		slotClickCallbacks.get(slot).add(guiClickCallback);
	}

	/**
	 * Add a {@link GUIClickCallbackWithEvent} to be executed when a specific slot
	 * in the inventory is clicked
	 * 
	 * @param slot             The slot number to bind the callback to
	 * @param guiClickCallback {@link GUIClickCallbackWithEvent} to add
	 */
	public void addClickCallback(int slot, GUIClickCallbackWithEvent guiClickCallback) {
		if (!slotClickEventCallbacks.containsKey(slot)) {
			slotClickEventCallbacks.put(slot, new ArrayList<GUIClickCallbackWithEvent>());
		}

		slotClickEventCallbacks.get(slot).add(guiClickCallback);
	}

	/**
	 * Unused
	 */
	@Override
	@Deprecated
	public Inventory getInventory() {
		return null;
	}
};