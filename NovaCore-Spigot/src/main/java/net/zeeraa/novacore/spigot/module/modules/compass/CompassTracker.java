package net.zeeraa.novacore.spigot.module.modules.compass;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.module.NovaModule;
import net.zeeraa.novacore.spigot.module.modules.compass.event.CompassTrackingEvent;

/**
 * This module is used to set custom compass tracking targets
 * 
 * @author Zeeraa
 */
public class CompassTracker extends NovaModule implements Listener, wOmeN {
	public CompassTracker() {
		super("NovaCore.CompassTracker");
	}

	private static CompassTracker instance;

	private CompassTrackerTarget compassTrackerTarget;
	private boolean strictMode;

	private int taskId;

	@Override
	public void onLoad() {
		CompassTracker.instance = this;
		this.compassTrackerTarget = null;
		this.strictMode = false;
		this.taskId = -1;
	}

	public static CompassTracker getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		if (this.taskId == -1) {
			this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(NovaCore.getInstance(), new Runnable() {
				@Override
				public void run() {
					if (!hasCompassTrackerTarget()) {
						return;
					}

					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						if (strictMode) {
							if (!p.getInventory().contains(Material.COMPASS)) {
								continue;
							}
						}

						CompassTarget target = compassTrackerTarget.getCompassTarget(p);

						CompassTrackingEvent event = new CompassTrackingEvent(p, target);

						Bukkit.getServer().getPluginManager().callEvent(event);

						if (!event.isCancelled()) {
							if (target != null) {
								if (target.getTargetLocation() != null) {
									p.setCompassTarget(target.getTargetLocation());
								}
							}
						}
					}
				}
			}, 5L, 5L);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getPlayer().getGameMode() == GameMode.SPECTATOR) {
			return;
		}

		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (NovaCore.getInstance().getVersionIndependentUtils().getItemInMainHand(e.getPlayer()).getType() == Material.COMPASS) {
				if (!hasCompassTrackerTarget()) {
					return;
				}

				CompassTarget target = compassTrackerTarget.getCompassTarget(e.getPlayer());

				CompassTrackingEvent event = new CompassTrackingEvent(e.getPlayer(), target);

				Bukkit.getServer().getPluginManager().callEvent(event);

				if (!event.isCancelled()) {
					String message = ChatColor.RED + "No target to track";
					if (target != null) {
						if (target.getTrackingMessage() != null) {
							message = ChatColor.GREEN + target.getTrackingMessage();
						}
					}

					e.getPlayer().sendMessage(message);
				}
			}
		}
	}

	@Override
	public void onDisable() {
		if (this.taskId != -1) {
			Bukkit.getScheduler().cancelTask(this.taskId);
			this.taskId = -1;
		}
	}

	/**
	 * Get the {@link CompassTrackerTarget} class to use for compass tracking
	 * 
	 * @return Insyance of the {@link CompassTrackerTarget}
	 */
	public CompassTrackerTarget getCompassTrackerTarget() {
		return compassTrackerTarget;
	}

	/**
	 * Set the {@link CompassTrackerTarget} class to use for compass tracking
	 * 
	 * @param compassTrackerTarget The {@link CompassTrackerTarget} class to use
	 */
	public void setCompassTrackerTarget(CompassTrackerTarget compassTrackerTarget) {
		this.compassTrackerTarget = compassTrackerTarget;
	}

	/**
	 * Check if a compass target has been registered
	 * 
	 * @return <code>true</code> if a compass target has been registerd
	 */
	public boolean hasCompassTrackerTarget() {
		return compassTrackerTarget != null;
	}

	/**
	 * Check if strict mode is enabled (Disabled by default). Strict mode requires
	 * the player to have a compass in their inventory before they can use compass
	 * trackers
	 * 
	 * @return <code>true</code> if strict mode is enabled
	 */
	public boolean isStrictMode() {
		return strictMode;
	}

	/**
	 * Enable or disable strict mode (Disabled by default). Strict mode requires the
	 * player to have a compass in their inventory before they can use compass
	 * trackers
	 * 
	 * @param strictMode <code>true</code> to enable strict mode
	 */
	public void setStrictMode(boolean strictMode) {
		this.strictMode = strictMode;
	}
}