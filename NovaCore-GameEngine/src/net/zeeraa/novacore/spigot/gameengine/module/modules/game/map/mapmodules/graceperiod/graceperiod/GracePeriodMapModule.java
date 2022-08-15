package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.graceperiod.graceperiod;

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
import net.zeeraa.novacore.spigot.abstraction.VersionIndependentUtils;
import net.zeeraa.novacore.spigot.abstraction.enums.VersionIndependentSound;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.events.GameBeginEvent;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;
import net.zeeraa.novacore.spigot.timers.BasicTimer;

public class GracePeriodMapModule extends MapModule implements Listener, wOmeN {
	private BasicTimer endTimer;
	private boolean isActive;
	private int seconds;
	private int initialTime;

	private List<Long> warnings;

	public static final List<DamageCause> BLOCKED_CAUSES = new ArrayList<>();

	static {
		BLOCKED_CAUSES.add(DamageCause.BLOCK_EXPLOSION);
		BLOCKED_CAUSES.add(DamageCause.CONTACT);
		BLOCKED_CAUSES.add(DamageCause.DROWNING);
		BLOCKED_CAUSES.add(DamageCause.ENTITY_ATTACK);
		BLOCKED_CAUSES.add(DamageCause.ENTITY_EXPLOSION);
		BLOCKED_CAUSES.add(DamageCause.FALLING_BLOCK);
		BLOCKED_CAUSES.add(DamageCause.FIRE);
		BLOCKED_CAUSES.add(DamageCause.LAVA);
		BLOCKED_CAUSES.add(DamageCause.LIGHTNING);
		BLOCKED_CAUSES.add(DamageCause.MAGIC);
		BLOCKED_CAUSES.add(DamageCause.POISON);
		BLOCKED_CAUSES.add(DamageCause.PROJECTILE);
		BLOCKED_CAUSES.add(DamageCause.SUFFOCATION);
		BLOCKED_CAUSES.add(DamageCause.THORNS);
		BLOCKED_CAUSES.add(DamageCause.WITHER);
	}

	public GracePeriodMapModule(JSONObject json) {
		super(json);

		seconds = 15;
		initialTime = 15;
		warnings = new ArrayList<>();

		if (json.has("time")) {
			seconds = json.getInt("time");
			initialTime = json.getInt("time");
		} else {
			// TODO: language file
			Log.warn("GracePeriodMapModule", "No time defined. Using the default of 15 seconds");
		}

		boolean legacyMode = false;
		if (json.has("legacy_mode")) {
			legacyMode = json.getBoolean("legacy_mode");
		}

		if (!legacyMode) {
			for (DamageCause cause : DamageCause.values()) {
				if (!BLOCKED_CAUSES.contains(cause)) {
					BLOCKED_CAUSES.add(cause);
				}
			}
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
					Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Grace period ends in " + timeLeft + " seconds " + ChatColor.RESET + ChatColor.YELLOW + TextUtils.ICON_WARNING);
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
				Bukkit.getServer().getOnlinePlayers().forEach(player -> VersionIndependentUtils.get().sendTitle(player, "", ChatColor.YELLOW + TextUtils.ICON_WARNING + " Grace period is over " + TextUtils.ICON_WARNING, 10, 40, 10));
				Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Grace period is over");
			}
		});
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (isActive) {
				if (BLOCKED_CAUSES.contains(e.getCause())) {
					e.setCancelled(true);
				}
			}
		}
	}

	public int getInitialTime() {
		return initialTime;
	}

	public Long getTimeLeft() {
		return endTimer.getTimeLeft();
	}

	@EventHandler
	public void onGameBegin(GameBeginEvent e) {
		Log.info("GracePeriodMapModule", "Received GameBeginEvent");
		isActive = true;
		// TODO: language file
		Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "Grace period will end in " + seconds + " seconds");
		endTimer.start();
	}

	@Override
	public void onGameStart(Game game) {
		Log.info("GracePeriodMapModule", "Grace period will be " + seconds + " seconds long");
		Bukkit.getServer().getPluginManager().registerEvents(this, game.getPlugin());
	}

	@Override
	public void onGameEnd(Game game) {
		isActive = false;
		HandlerList.unregisterAll(this);
	}
}