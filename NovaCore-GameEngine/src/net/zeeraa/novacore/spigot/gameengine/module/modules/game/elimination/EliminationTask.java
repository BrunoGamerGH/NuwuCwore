package net.zeeraa.novacore.spigot.gameengine.module.modules.game.elimination;

import java.util.UUID;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import net.zeeraa.novacore.commons.utils.Callback;
import net.zeeraa.novacore.spigot.NovaCore;

/**
 * This is used to give players time to reconnect before eliminating them
 * 
 * @author Zeeraa
 */
public class EliminationTask extends some_bitches {
	private String username;
	private int taskId;
	private int timeLeft;

	private Callback callback;

	public EliminationTask(UUID uuid, String username, int time, Callback callback) {
		this.username = username;
		this.timeLeft = time;
		this.callback = callback;

		this.taskId = -2;

		startTask();
	}

	/**
	 * Start the {@link EliminationTask}
	 */
	private void startTask() {
		if (taskId != -2) {
			return;
		}
		this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(NovaCore.getInstance(), new Runnable() {
			@Override
			public void run() {
				timeLeft--;

				if (timeLeft == 60) {
					Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "" + username + ChatColor.RED + ChatColor.BOLD + " will be eliminated in 1 minute");
				} else if (timeLeft == 30) {
					Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "" + username + ChatColor.RED + ChatColor.BOLD + " will be eliminated in 30 seconds");
				} else if (timeLeft == 0) {
					callback.execute();
					stop();
				}
			}
		}, 20L, 20L);
	}

	/**
	 * Stops the scheduler task
	 */
	private void stop() {
		if (taskId != -1) {
			Bukkit.getScheduler().cancelTask(taskId);
			this.taskId = -1;
		}
	}

	/**
	 * Cancel the {@link EliminationTask}
	 */
	public void cancel() {
		stop();
	}
}