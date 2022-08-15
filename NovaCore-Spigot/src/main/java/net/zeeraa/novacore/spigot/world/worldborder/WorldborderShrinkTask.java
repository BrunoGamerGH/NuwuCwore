package net.zeeraa.novacore.spigot.world.worldborder;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;
import org.bukkit.World;
import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.NovaCore;

public class WorldborderShrinkTask extends some_bitches {
	private double centerX;
	private double centerZ;

	private double startSize;


	private double damage;
	private double damageBuffer;

	private int stepTime;
	private double stepShrinkValue;
	private int totalSteps;
	private int activeStep;
	private double lastSize;

	private World world;

	private int taskId = -1;

	public WorldborderShrinkTask(World world, double centerX, double centerZ, double startSize, double endSize, double damage, double damageBuffer, int shrinkDuration, int stepTime) {
		this.centerX = centerX;
		this.centerZ = centerZ;

		this.startSize = startSize;

		this.damage = damage;
		this.damageBuffer = damageBuffer;

		this.stepTime = stepTime;

		this.world = world;

		this.totalSteps = shrinkDuration / stepTime;

		this.stepShrinkValue = (startSize - endSize) / (double) totalSteps;

		this.lastSize = startSize;

		this.taskId = -1;
		this.activeStep = 0;
	}

	public void init() {
		world.getWorldBorder().setCenter(centerX, centerZ);
		world.getWorldBorder().setSize(startSize);
		world.getWorldBorder().setDamageAmount(damage);
		world.getWorldBorder().setDamageBuffer(damageBuffer);
		Log.info("The worldborder in world " + world.getName() + " has been reset");
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

					world.getWorldBorder().setSize(lastSize - stepShrinkValue, (long) stepTime);

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