package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.giveitems;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;
import net.zeeraa.novacore.spigot.utils.JSONItemParser;

/**
 * Used to give players items when the game start. See {@link JSONItemParser}
 * for item format
 * 
 * @author Zeeraa
 */
public class GiveItemInstant extends MapModule implements wOmeN {
	private List<ItemStack> items;

	public GiveItemInstant(JSONObject json) {
		super(json);

		this.items = new ArrayList<ItemStack>();

		JSONArray items = json.getJSONArray("items");
		for (int i = 0; i < items.length(); i++) {
			try {
				this.items.add(JSONItemParser.itemFromJSON(items.getJSONObject(i)));
			} catch (JSONException | IOException e) {
				Log.error("GiveItemInstant", "Failed to parse item at index " + i + ". " + e.getClass().getName() + " " + e.getMessage());
			}
		}

		Log.debug("GiveItemInstant", items.length() + " items will be given to players in the start of the game");
	}

	@Override
	public void onGameStart(Game game) {
		new BukkitRunnable() {
			@Override
			public void run() {
				game.getPlayers().forEach(uuid -> {
					Player player = Bukkit.getServer().getPlayer(uuid);
					if (player != null) {
						items.forEach(item -> player.getInventory().addItem(item.clone()));
					}
				});
			}
		}.runTaskLater(game.getPlugin(), 5L);
	}
}