package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.simplemapdecay;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.json.JSONObject;

import net.md_5.bungee.api.ChatColor;
import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.GameManager;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.DelayedGameTrigger;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.GameTrigger;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.RepeatingGameTrigger;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.TriggerCallback;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.TriggerFlag;
import net.zeeraa.novacore.spigot.utils.VectorArea;

public class SimpleBoxDecay extends MapModule implements wOmeN {
	private VectorArea area;

	private String startMessage;

	private RepeatingGameTrigger trigger;
	private DelayedGameTrigger startTrigger;

	private int layer;

	public SimpleBoxDecay(JSONObject json) {
		super(json);

		JSONObject areaJson = json.getJSONObject("area");
		area = VectorArea.fromJSON(areaJson);

		layer = 0;

		if (json.has("message")) {
			startMessage = ChatColor.translateAlternateColorCodes(ChatColor.COLOR_CHAR, json.getString("message"));
		} else {
			startMessage = null;
		}

		int beginAfter = json.getInt("begin_after");

		trigger = new RepeatingGameTrigger("novauniverse.simpleboxdecay.simpleboxdecay", 20L, 60L, new TriggerCallback() {
			@Override
			public boolean isCancelled() {
				return false;
			}

			@Override
			public void setCancelled(boolean cancel) {

			}

			@Override
			public void run(GameTrigger trigger2, TriggerFlag reason) {
				execDecayStep();
			}
		});

		trigger.addFlag(TriggerFlag.STOP_ON_GAME_END);

		startTrigger = new DelayedGameTrigger("novauniverse.simpleboxdecay.begin_simpleboxdecay", beginAfter * 20L, new TriggerCallback() {
			@Override
			public boolean isCancelled() {
				return false;
			}

			@Override
			public void setCancelled(boolean cancel) {

			}

			@Override
			public void run(GameTrigger trigger2, TriggerFlag reason) {
				Bukkit.getServer().getOnlinePlayers().forEach(p -> {
					p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F);
				});

				Bukkit.getServer().broadcastMessage(startMessage);

				trigger.start();
			}
		});

		startTrigger.addFlag(TriggerFlag.STOP_ON_GAME_END);
		startTrigger.addFlag(TriggerFlag.RUN_ONLY_ONCE);

		Log.debug("SimpleBoxDecay", "Decay area contains " + area.getArea() + " blocks");
	}

	private void execDecayStep() {
		// position 2 is larger than position 1
		int size = area.getPosition2().getBlockY() - area.getPosition1().getBlockY();

		if (layer > size) {
			if (trigger.isRunning()) {
				trigger.stop();
			}
			return;
		}

		int x1 = area.getPosition1().getBlockX();
		int x2 = area.getPosition2().getBlockX();

		int z1 = area.getPosition1().getBlockZ();
		int z2 = area.getPosition2().getBlockZ();

		Log.trace("SimpleBoxDecay", "Decay layer: " + layer);
		Log.trace("SimpleBoxDecay", "Decay bounds: X1: " + x1 + " X2: " + x2 + " Z1: " + z1 + " Z2: " + z2);

		int y = area.getPosition2().getBlockY() - layer;

		for (int x = x1; x <= x2; x++) {
			for (int z = z1; z <= z2; z++) {
				World world = GameManager.getInstance().getActiveGame().getWorld();
				Block block = world.getBlockAt(x - 1, y, z - 1);

				if (block.getType() != Material.AIR) {
					block.setType(Material.AIR);
				}
			}
		}

		layer++;
	}

	@Override
	public void onGameStart(Game game) {
		game.addTrigger(trigger);
		game.addTrigger(startTrigger);

		startTrigger.start();
	}

	public VectorArea getArea() {
		return area;
	}

	public String getStartMessage() {
		return startMessage;
	}
}