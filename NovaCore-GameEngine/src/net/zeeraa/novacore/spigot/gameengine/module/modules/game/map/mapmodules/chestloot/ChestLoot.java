package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.chestloot;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.commons.utils.RandomGenerator;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.GameMap;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.DelayedGameTrigger;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.GameTrigger;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.TriggerCallback;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.TriggerFlag;
import net.zeeraa.novacore.spigot.module.ModuleManager;
import net.zeeraa.novacore.spigot.module.modules.chestloot.ChestLootManager;

public class ChestLoot extends MapModule implements wOmeN {
	private String chestLootTable;
	private String enderChestLootTable;

	private int minRefillTime;
	private int maxRefillTime;

	private boolean announceRefills;

	private DelayedGameTrigger trigger;

	public ChestLoot(JSONObject json) {
		super(json);

		this.chestLootTable = null;
		this.enderChestLootTable = null;
		this.minRefillTime = -1;
		this.maxRefillTime = -1;
		this.announceRefills = true;
		this.trigger = null;

		if (json.has("ender_chest_loot")) {
			this.enderChestLootTable = json.getString("ender_chest_loot");
		}

		if (json.has("chest_loot")) {
			this.chestLootTable = json.getString("chest_loot");
		}

		if (json.has("min_refill_time")) {
			this.minRefillTime = json.getInt("min_refill_time");
		}

		if (json.has("max_refill_time")) {
			this.maxRefillTime = json.getInt("max_refill_time");
		}

		if (json.has("announce_refills")) {
			this.announceRefills = json.getBoolean("announce_refills");
		}

		if (minRefillTime != -1 && maxRefillTime == -1) {
			maxRefillTime = minRefillTime;
		} else if (maxRefillTime != -1 && minRefillTime == -1) {
			minRefillTime = maxRefillTime;
		}

		this.trigger = new DelayedGameTrigger("novacore.chest.refill", minRefillTime * 20, new TriggerCallback() {
			@Override
			public boolean isCancelled() {
				return false;
			}

			@Override
			public void setCancelled(boolean cancel) {

			}

			@Override
			public void run(GameTrigger trigger, TriggerFlag reason) {
				ChestLootManager.getInstance().refillChests(announceRefills);
				startTask();
			}
		});
		trigger.addFlag(TriggerFlag.STOP_ON_GAME_END);
	}

	public String getEnderChestLootTable() {
		return enderChestLootTable;
	}

	public boolean hasEnderChestLootTable() {
		return enderChestLootTable != null;
	}

	public String getChestLootTable() {
		return chestLootTable;
	}

	public boolean hasChestLootTable() {
		return chestLootTable != null;
	}

	public int getMinRefillTime() {
		return minRefillTime;
	}

	public int getMaxRefillTime() {
		return maxRefillTime;
	}

	public boolean isRefillsEnabled() {
		return minRefillTime > 0;
	}

	public boolean isAnnounceRefills() {
		return announceRefills;
	}

	@Override
	public void onMapLoad(GameMap map) {
		if (hasChestLootTable() || hasEnderChestLootTable()) {
			if (ModuleManager.isDisabled(ChestLootManager.class)) {
				Log.info("Loading ChestLootManager because the game map has a chest or ender chest loot table");
				ModuleManager.enable(ChestLootManager.class);
			}

			ChestLootManager.getInstance().setChestLootTable(getChestLootTable());
			ChestLootManager.getInstance().setEnderChestLootTable(getEnderChestLootTable());
		}
	}

	@Override
	public void onGameStart(Game game) {
		if (isRefillsEnabled()) {
			game.addTrigger(trigger);
		}
	}

	@Override
	public void onGameBegin(Game game) {
		if (isRefillsEnabled()) {
			startTask();
		}
	}

	public DelayedGameTrigger getTrigger() {
		return trigger;
	}

	private void startTask() {
		int delay = RandomGenerator.generate(minRefillTime, maxRefillTime);

		Log.debug("Next chest refill in " + delay + " seconds");

		trigger.stop();
		trigger.setDelay(delay * 20);
		trigger.start();
	}
}