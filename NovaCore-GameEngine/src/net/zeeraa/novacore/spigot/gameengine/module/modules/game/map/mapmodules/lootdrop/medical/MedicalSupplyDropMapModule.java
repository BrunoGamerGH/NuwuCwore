package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.lootdrop.medical;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Location;
import org.json.JSONArray;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.commons.utils.RandomGenerator;
import net.zeeraa.novacore.spigot.gameengine.lootdrop.medical.MedicalSupplyDropManager;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.GameMap;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.DelayedGameTrigger;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.GameTrigger;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.TriggerCallback;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.TriggerFlag;
import net.zeeraa.novacore.spigot.utils.LocationData;

public class MedicalSupplyDropMapModule extends MapModule implements wOmeN {
	private String lootTable;

	private int minDropTime;
	private int maxDropTime;

	private DelayedGameTrigger trigger;

	private List<LocationData> locations;

	private List<Location> locationsReal;

	public MedicalSupplyDropMapModule(JSONObject json) {
		super(json);

		this.lootTable = null;

		this.minDropTime = 200;
		this.maxDropTime = 900;

		this.locations = new ArrayList<LocationData>();

		this.locationsReal = null;

		JSONArray locationJson = json.getJSONArray("locations");

		for (int i = 0; i < locationJson.length(); i++) {
			locations.add(new LocationData(locationJson.getJSONObject(i)));
		}

		this.lootTable = json.getString("loot_table");

		this.minDropTime = json.getInt("min_drop_time");

		if (json.has("max_drop_time")) {
			this.maxDropTime = json.getInt("max_drop_time");
		} else {
			Log.warn("Missing max_drop_time for medical supply drop");
		}

		if (minDropTime != -1 && maxDropTime == -1) {
			maxDropTime = minDropTime;
		}

		trigger = new DelayedGameTrigger("novauniverse.survivalgames.medicalsupplydrop", minDropTime, new TriggerCallback() {
			@Override
			public boolean isCancelled() {
				return false;
			}

			@Override
			public void setCancelled(boolean cancel) {

			}

			@Override
			public void run(GameTrigger trigger2, TriggerFlag reason) {
				onTrigger(reason);
			}
		});
		trigger.addFlag(TriggerFlag.STOP_ON_GAME_END);
	}

	private void onTrigger(TriggerFlag reason) {
		spawnDrop();
		startTriggerTimer();
	}

	private void startTriggerTimer() {
		int delay = RandomGenerator.generate(minDropTime, maxDropTime);

		Log.debug("Next medical supply drop in " + delay + " seconds");

		if (trigger.isRunning()) {
			trigger.stop();
		}

		trigger.setDelay(delay * 20);

		trigger.start();
	}

	@Override
	public void onMapLoad(GameMap map) {
		if (lootTable != null && minDropTime > 0) {
			if (!MedicalSupplyDropManager.getInstance().isEnabled()) {
				MedicalSupplyDropManager.getInstance().enable();
			}

			locationsReal = LocationData.toLocations(locations, map.getWorld());
		} else {
			Log.error("Cant enable medical supply drop because loot table or min drop time is not set");
		}
	}

	@Override
	public void onGameStart(Game game) {
		game.addTrigger(trigger);
		if (lootTable != null && minDropTime > 0) {
			startTriggerTimer();
		}
	}

	@Override
	public void onGameEnd(Game game) {
		if (trigger != null) {
			trigger.stop();
		}
		game.removeTrigger(trigger);
	}

	public DelayedGameTrigger getTrigger() {
		return trigger;
	}

	public boolean spawnDrop() {
		if (lootTable != null && minDropTime > 0) {
			if (locationsReal.size() > 0) {
				for (int i = 0; i < 40; i++) {
					Location location = locationsReal.get(new Random().nextInt(locationsReal.size()));

					if (MedicalSupplyDropManager.getInstance().canSpawnAt(location)) {
						Log.debug("Medical supply drop spawned after " + i + " tries");
						MedicalSupplyDropManager.getInstance().spawnDrop(location, lootTable);
						return true;
					}
				}
			} else {
				Log.warn("No medical supply drop locations specified");
			}
		}

		return false;
	}
}