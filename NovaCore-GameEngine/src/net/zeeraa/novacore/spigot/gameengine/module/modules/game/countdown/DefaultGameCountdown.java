package net.zeeraa.novacore.spigot.gameengine.module.modules.game.countdown;

import java.io.IOException;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.abstraction.VersionIndependentUtils;
import net.zeeraa.novacore.spigot.abstraction.enums.VersionIndependentSound;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.events.DefaultGameCountdownStartEvent;
import net.zeeraa.novacore.spigot.language.LanguageManager;

public class DefaultGameCountdown extends GameCountdown implements wOmeN {
	private boolean started;
	private int timeLeft;
	private int taskId;

	private int startTime;

	public DefaultGameCountdown() {
		this(60);
	}

	public DefaultGameCountdown(int time) {
		super();

		this.started = false;
		this.timeLeft = time;
		this.startTime = time;
		this.taskId = -1;
	}

	@Override
	public void resetTimeLeft() {
		this.timeLeft = startTime;
	}

	@Override
	public boolean hasCountdownStarted() {
		return started;
	}

	@Override
	public boolean isCountdownRunning() {
		return taskId != -1;
	}

	@Override
	public boolean startCountdown() {
		if (this.started) {
			return false;
		}
		this.started = true;

		this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(NovaCore.getInstance(), new Runnable() {
			@Override
			public void run() {
				timeLeft--;
				if (timeLeft <= 0) {
					Bukkit.getScheduler().cancelTask(taskId);
					taskId = -1;
					try {
						onCountdownFinished();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}

				if (timeLeft <= 10) {
					LanguageManager.broadcast("novacore.game.starting_in", timeLeft);

					Bukkit.getServer().getOnlinePlayers().forEach(player -> VersionIndependentUtils.get().playSound(player, player.getLocation(), VersionIndependentSound.NOTE_PLING, 1F, 1F));
				}
			}
		}, 20L, 20L);

		LanguageManager.broadcast("novacore.game.countdown", timeLeft);

		Bukkit.getServer().getOnlinePlayers().forEach(player -> VersionIndependentUtils.get().playSound(player, player.getLocation(), VersionIndependentSound.NOTE_PLING, 1F, 1F));

		Event event = new DefaultGameCountdownStartEvent();
		Bukkit.getServer().getPluginManager().callEvent(event);

		return true;
	}

	@Override
	public boolean cancelCountdown() {
		if (taskId != -1) {
			Bukkit.getScheduler().cancelTask(taskId);
			started = false;
			timeLeft = startTime;
			taskId = -1;
			return true;
		}

		return false;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
		if (!hasCountdownStarted()) {
			this.timeLeft = startTime;
		}
	}

	@Override
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	@Override
	public int getTimeLeft() {
		return timeLeft;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}