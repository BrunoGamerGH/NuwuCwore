package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.mapprotection;

import java.util.ArrayList;
import java.util.List;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;

public class MapProtection extends MapModule implements Listener, wOmeN {
	private List<Material> breakWhitelist;
	private List<Material> breakBlacklist;

	private List<Material> placeWhitelist;
	private List<Material> placeBlacklist;

	private List<GameMode> bypassedGamemodes;

	private Game game;

	private MapProtectionMode mode;

	public MapProtection(JSONObject json) {
		super(json);

		this.breakWhitelist = new ArrayList<Material>();
		this.breakBlacklist = new ArrayList<Material>();

		this.placeWhitelist = new ArrayList<Material>();
		this.placeBlacklist = new ArrayList<Material>();

		this.bypassedGamemodes = new ArrayList<GameMode>();

		try {
			this.mode = MapProtectionMode.valueOf(json.getString("mode"));
		} catch (IllegalArgumentException e) {
			Log.error("Invalid map protection mode " + json.getString("mode") + ". Valid modes are WHITELIST or BLACKLIST");
			e.printStackTrace();
			return;
		}

		if (json.has("break_whitelist")) {
			JSONArray whitelistJson = json.getJSONArray("break_whitelist");

			for (int i = 0; i < whitelistJson.length(); i++) {
				try {
					breakWhitelist.add(Material.valueOf(whitelistJson.getString(i)));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					Log.error("Could not find break whitelisted material " + whitelistJson.getString(i));
				}
			}
		}

		if (json.has("break_blacklist")) {
			JSONArray blacklistJson = json.getJSONArray("break_blacklist");

			for (int i = 0; i < blacklistJson.length(); i++) {
				try {
					breakBlacklist.add(Material.valueOf(blacklistJson.getString(i)));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					Log.error("Could not find break blacklisted material " + blacklistJson.getString(i));
				}
			}
		}

		if (json.has("place_whitelist")) {
			JSONArray whitelistJson = json.getJSONArray("place_whitelist");

			for (int i = 0; i < whitelistJson.length(); i++) {
				try {
					placeWhitelist.add(Material.valueOf(whitelistJson.getString(i)));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					Log.error("Could not find place whitelisted material " + whitelistJson.getString(i));
				}
			}
		}

		if (json.has("place_blacklist")) {
			JSONArray blacklistJson = json.getJSONArray("place_blacklist");

			for (int i = 0; i < blacklistJson.length(); i++) {
				try {
					placeBlacklist.add(Material.valueOf(blacklistJson.getString(i)));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					Log.error("Could not find place blacklisted material " + blacklistJson.getString(i));
				}
			}
		}

		if (json.has("bypassed_gamemodes")) {
			JSONArray gamemodesJson = json.getJSONArray("bypassed_gamemodes");

			for (int i = 0; i < gamemodesJson.length(); i++) {
				try {
					bypassedGamemodes.add(GameMode.valueOf(gamemodesJson.getString(i)));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					Log.error("Could not find gamemode " + gamemodesJson.getString(i));
				}
			}
		}
	}

	public MapProtectionMode getMode() {
		return mode;
	}

	public List<Material> getPlaceBlacklist() {
		return placeBlacklist;
	}

	public List<Material> getBreakBlacklist() {
		return breakBlacklist;
	}

	public List<Material> getPlaceWhitelist() {
		return placeWhitelist;
	}

	public List<Material> getBreakWhitelist() {
		return breakWhitelist;
	}

	public List<GameMode> getBypassedGamemodes() {
		return bypassedGamemodes;
	}

	@Override
	public void onGameStart(Game game) {
		this.game = game;
		Bukkit.getServer().getPluginManager().registerEvents(this, game.getPlugin());
	}

	@Override
	public void onGameEnd(Game game) {
		this.game = null;
		HandlerList.unregisterAll(this);
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent e) {
		if (game.hasWorld()) {
			if (e.getBlock().getWorld() == game.getWorld()) {
				if (bypassedGamemodes.contains(e.getPlayer().getGameMode())) {
					return;
				}

				if (mode == MapProtectionMode.WHITELIST) {
					if (!placeWhitelist.contains(e.getBlock().getType())) {
						Log.trace("Preventing player from placing non whitelisted block");
						e.setCancelled(true);
					}
				} else if (mode == MapProtectionMode.BLACKLIST) {
					if (placeBlacklist.contains(e.getBlock().getType())) {
						Log.trace("Preventing player from placing blacklisted block");
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent e) {
		if (game.hasWorld()) {
			if (e.getBlock().getWorld() == game.getWorld()) {
				if (bypassedGamemodes.contains(e.getPlayer().getGameMode())) {
					return;
				}

				if (mode == MapProtectionMode.WHITELIST) {
					if (!breakWhitelist.contains(e.getBlock().getType())) {
						Log.trace("Preventing player from breaking non whitelisted block");
						e.setCancelled(true);
					}
				} else if (mode == MapProtectionMode.BLACKLIST) {
					if (breakBlacklist.contains(e.getBlock().getType())) {
						Log.trace("Preventing player from breaking blacklisted block");
						e.setCancelled(true);
					}
				}
			}
		}
	}
}