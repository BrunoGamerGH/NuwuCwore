package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.worldborder;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.abstraction.VersionIndependentUtils;
import net.zeeraa.novacore.spigot.abstraction.enums.VersionIndependentSound;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.DelayedGameTrigger;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.GameTrigger;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.TriggerCallback;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.TriggerFlag;
import net.zeeraa.novacore.spigot.language.LanguageManager;

public class WorldborderMapModule extends MapModule implements wOmeN {
	private double centerX;
	private double centerZ;

	private double startSize;
	private double endSize;

	private double damage;
	private double damageBuffer;

	private int shrinkDuration;
	private int startDelay;

	private DelayedGameTrigger startTrigger;

	private Game game;

	private int taskId = -1;

	private int stepTime;
	private double stepShrinkValue;
	private int totalSteps;
	private int activeStep;
	private double lastSize;

	public WorldborderMapModule(JSONObject json) {
		super(json);

		this.centerX = 0.5;
		this.centerZ = 0.5;

		this.startSize = 501;
		this.endSize = 51;

		this.damage = 5;
		this.damageBuffer = 2;

		this.shrinkDuration = 900;
		this.startDelay = 600;

		this.stepTime = 30;

		this.taskId = -1;

		if (json.has("center_x")) {
			this.centerX = json.getDouble("center_x");
		} else {
			Log.warn("Missing center_x for wordborder map module");
		}

		if (json.has("center_z")) {
			this.centerZ = json.getDouble("center_z");
		} else {
			Log.warn("Missing center_z for wordborder map module");
		}

		if (json.has("start_size")) {
			this.startSize = json.getDouble("start_size");
		} else {
			Log.warn("Missing start_size for wordborder map module");
		}

		if (json.has("end_size")) {
			this.endSize = json.getDouble("end_size");
		} else {
			Log.warn("Missing end_size for wordborder map module");
		}

		if (json.has("shrink_duration")) {
			this.shrinkDuration = json.getInt("shrink_duration");
		} else {
			Log.warn("Missing shrink_duration for wordborder map module");
		}

		if (json.has("start_delay")) {
			this.startDelay = json.getInt("start_delay");
		} else {
			Log.warn("Missing start_delay for wordborder map module");
		}

		if (json.has("damage")) {
			this.damage = json.getDouble("damage");
		}

		if (json.has("damage_buffer")) {
			this.damageBuffer = json.getDouble("damage_buffer");
		}

		if (json.has("step_time")) {
			this.stepTime = json.getInt("step_time");
		}

		this.totalSteps = shrinkDuration / stepTime;

		this.stepShrinkValue = (startSize - endSize) / (double) totalSteps;

		this.lastSize = startSize;

		this.taskId = -1;
		this.activeStep = 0;

		this.startTrigger = new DelayedGameTrigger("novacore.worldborder.start", startDelay * 20, new TriggerCallback() {
			@Override
			public boolean isCancelled() {
				return false;
			}

			@Override
			public void setCancelled(boolean cancel) {

			}

			@Override
			public void run(GameTrigger trigger, TriggerFlag reason) {
				startTrigger.stop();
				// Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD +
				// "The world border is starting to shrink");
				LanguageManager.broadcast("novacore.game.wordborder.start");

				Bukkit.getServer().getOnlinePlayers().forEach(player -> VersionIndependentUtils.get().playSound(player, player.getLocation(), VersionIndependentSound.NOTE_PLING, 1F, 1F));

				start();
			}
		});
		this.startTrigger.addFlag(TriggerFlag.RUN_ONLY_ONCE);
		this.startTrigger.addFlag(TriggerFlag.STOP_ON_GAME_END);
	}

	public double getCenterX() {
		return centerX;
	}

	public double getCenterZ() {
		return centerZ;
	}

	public double getStartSize() {
		return startSize;
	}

	public double getEndSize() {
		return endSize;
	}

	public int getShrinkDuration() {
		return shrinkDuration;
	}

	public double getDamage() {
		return damage;
	}

	public double getDamageBuffer() {
		return damageBuffer;
	}

	public DelayedGameTrigger getStartTrigger() {
		return startTrigger;
	}

	@Override
	public void onGameStart(Game game) {
		this.game = game;

		game.addTrigger(startTrigger);

		if (game.hasWorld()) {
			game.getWorld().getWorldBorder().setCenter(centerX, centerZ);
			game.getWorld().getWorldBorder().setSize(startSize);
			game.getWorld().getWorldBorder().setDamageAmount(damage);
			game.getWorld().getWorldBorder().setDamageBuffer(damageBuffer);
			Log.info("The worldborder in world " + game.getWorld().getName() + " has been reset");
		} else {
			Log.fatal("Worldborder cant set initial size because the game does not have a world set");
			return;
		}
	}
	
	@Override
	public void onGameBegin(Game game) {
		startTrigger.start();
	}

	@Override
	public void onGameEnd(Game game) {
		this.game = null;
	}

	public boolean cancel() {
		if (taskId != -1) {
			Bukkit.getScheduler().cancelTask(taskId);
			taskId = -1;
			return true;
		}
		return false;
	}

	public boolean start() {
		if (taskId == -1) {
			taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(NovaCore.getInstance(), new Runnable() {
				@Override
				public void run() {
					if (activeStep >= totalSteps) {
						cancel();
						return;
					}

					if (game == null) {
						return;
					}

					if (game.getWorld() == null) {
						return;
					}

					game.getWorld().getWorldBorder().setSize(lastSize - stepShrinkValue, (long) stepTime);

					lastSize -= stepShrinkValue;

					activeStep++;
				}
			}, 0, stepTime * 20);
			return true;
		}
		return false;
	}

	public boolean isRunning() {
		return taskId != -1;
	}
}