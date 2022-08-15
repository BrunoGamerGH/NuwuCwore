package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.giveitems;

import java.util.UUID;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.commons.tasks.Task;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.GameManager;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;
import net.zeeraa.novacore.spigot.tasks.SimpleTask;
import net.zeeraa.novacore.spigot.utils.ItemBuilder;

/**
 * Used to give players Arrows, Eggs and Snowballs, can also be used for other
 * items but is not recommended. By default this gives snowballs
 * 
 * @author Zeeraa
 */
public class GiveItemSlow extends MapModule implements wOmeN {
	private Material material;
	private int maxItems;
	private int delay;
	private Task task;

	public Material getMaterial() {
		return material;
	}

	public int getMaxItems() {
		return maxItems;
	}

	public int getDelay() {
		return delay;
	}

	public Task getTask() {
		return task;
	}

	public GiveItemSlow(JSONObject json) {
		super(json);

		if (json.has("material")) {
			material = Material.valueOf(json.getString("material"));
		} else {
			material = Material.SNOW_BALL;
		}

		if (json.has("max_items")) {
			maxItems = json.getInt("max_items");
		} else {
			maxItems = 16;
		}

		if (json.has("delay")) {
			delay = json.getInt("delay");
		} else {
			delay = 5;
		}

		this.task = new SimpleTask(new Runnable() {
			@Override
			public void run() {
				for (UUID uuid : GameManager.getInstance().getActiveGame().getPlayers()) {
					Player player = Bukkit.getServer().getPlayer(uuid);

					if (player != null) {
						if (player.isOnline()) {
							Inventory inventory = player.getInventory();

							int totalItems = 0;

							for (ItemStack item : inventory.getContents()) {
								if (item != null) {
									if (item.getType() == material) {
										totalItems += item.getAmount();
									}
								}
							}

							if (totalItems >= maxItems) {
								continue;
							}

							inventory.addItem(ItemBuilder.materialToItemStack(material));
						}
					}
				}
			}
		}, delay * 20);
	}

	@Override
	public void onGameStart(Game game) {
		Log.trace("GiveProjectiles", "Starting task");
		task.start();
	}

	@Override
	public void onGameEnd(Game game) {
		Task.tryStopTask(task);
	}
}