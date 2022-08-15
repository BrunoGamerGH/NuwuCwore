package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.graceperiod.falldamagegraceperiod;

import java.util.ArrayList;
import java.util.List;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.json.JSONArray;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.commons.timers.TickCallback;
import net.zeeraa.novacore.commons.utils.Callback;
import net.zeeraa.novacore.commons.utils.TextUtils;
import net.zeeraa.novacore.spigot.abstraction.enums.VersionIndependentSound;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.events.GameBeginEvent;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;
import net.zeeraa.novacore.spigot.timers.BasicTimer;

public class FallDamageGracePeriodMapModule extends MapModule implements Listener, wOmeN {
	private BasicTimer endTimer;
	private boolean isActive;
	private int seconds;

	private List<Long> warnings;

	public FallDamageGracePeriodMapModule(JSONObject json) {
		super(json);

		seconds = 15;
		warnings = new ArrayList<>();

		if (json.has("time")) {
			seconds = json.getInt("time");
		} else {
			// TODO: language file
			Log.warn("FallDamageGracePeriodMapModule", "No time defined. Using the default of 30 seconds");
		}

		if (json.has("warnings")) {
			JSONArray warningsJSON = json.getJSONArray("warnings");
			for (int i = 0; i < warningsJSON.length(); i++) {
				warnings.add(warningsJSON.getLong(i));
			}
		}

		isActive = false;

		endTimer = new BasicTimer(seconds, 20L);

		endTimer.addTickCallback(new TickCallback() {
			@Override
			public boolean isCancelled() {
				return false;
			}

			@Override
			public void setCancelled(boolean cancel) {

			}

			@Override
			public void execute(long timeLeft) {
				if (warnings.contains(timeLeft)) {
					Bukkit.getServer().getOnlinePlayers().forEach(VersionIndependentSound.NOTE_PLING::play);
					Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Fall damage will be enabled in " + timeLeft + " seconds " + ChatColor.RESET + ChatColor.YELLOW + TextUtils.ICON_WARNING);
				}
			}
		});

		endTimer.addFinishCallback(new Callback() {
			@Override
			public boolean isCancelled() {
				return false;
			}

			@Override
			public void setCancelled(boolean cancel) {

			}

			@Override
			public void execute() {
				isActive = false;
				Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Fall damage is now enabled");
			}
		});
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (e.getCause() == DamageCause.FALL) {
				if (isActive) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onGameBegin(GameBeginEvent e) {
		Log.info("FallDamageGracePeriodMapModule", "Received GameBeginEvent");
		isActive = true;
		// TODO: language file
		Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "Fall damage will be enabled in " + seconds + " seconds");
		endTimer.start();
	}

	@Override
	public void onGameStart(Game game) {
		Log.info("FallDamageGracePeriodMapModule", "Fall damage grace period will be " + seconds + " seconds long");
		Bukkit.getServer().getPluginManager().registerEvents(this, game.getPlugin());
	}

	@Override
	public void onGameEnd(Game game) {
		isActive = false;
		HandlerList.unregisterAll(this);
	}
}