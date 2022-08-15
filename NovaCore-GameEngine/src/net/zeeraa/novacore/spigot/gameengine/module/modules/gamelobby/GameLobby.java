package net.zeeraa.novacore.spigot.gameengine.module.modules.gamelobby;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.GameManager;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.events.GameStartFailureEvent;
import net.zeeraa.novacore.spigot.gameengine.module.modules.gamelobby.events.PlayerJoinGameLobbyEvent;
import net.zeeraa.novacore.spigot.gameengine.module.modules.gamelobby.map.GameLobbyMap;
import net.zeeraa.novacore.spigot.gameengine.module.modules.gamelobby.map.GameLobbyMapData;
import net.zeeraa.novacore.spigot.gameengine.module.modules.gamelobby.map.GameLobbyReader;
import net.zeeraa.novacore.spigot.gameengine.module.modules.gamelobby.map.readers.DefaultLobbyMapReader;
import net.zeeraa.novacore.spigot.gameengine.module.modules.gamelobby.mapselector.LobbyMapSelector;
import net.zeeraa.novacore.spigot.gameengine.module.modules.gamelobby.mapselector.selectors.RandomLobbyMapSelector;
import net.zeeraa.novacore.spigot.language.LanguageManager;
import net.zeeraa.novacore.spigot.module.NovaModule;
import net.zeeraa.novacore.spigot.module.modules.multiverse.MultiverseManager;
import net.zeeraa.novacore.spigot.teams.Team;
import net.zeeraa.novacore.spigot.utils.PlayerUtils;

/**
 * This module creates a simple lobby where players wait for more players before
 * starting a game. This requires the {@link GameManager} and
 * {@link MultiverseManager} module and a game needs to be added
 * 
 * @author Zeeraa
 */
public class GameLobby extends NovaModule implements Listener, wOmeN {
	private static GameLobby instance;

	private GameLobbyMap activeMap;

	private GameLobbyReader mapReader;
	private LobbyMapSelector mapSelector;

	private int taskId;

	private List<UUID> waitingPlayers;

	private List<GameLobbyMapData> maps;

	public static GameLobby getInstance() {
		return instance;
	}

	public List<GameLobbyMapData> getMaps() {
		return maps;
	}

	private boolean ignoreNoTeam;

	/**
	 * Set if players without teams should be added to the game or not. Default is
	 * <code>false</code>
	 * 
	 * @param ignoreNoTeam <code>true</code> to enable players without team to get
	 *                     added to the game
	 */
	public void setIgnoreNoTeam(boolean ignoreNoTeam) {
		this.ignoreNoTeam = ignoreNoTeam;
	}

	/**
	 * Check if players without teams should be added to the game or not. Default is
	 * <code>false</code>
	 * 
	 * @return <code>true</code> if players without team to get added to the game
	 */
	public boolean isIgnoreNoTeam() {
		return ignoreNoTeam;
	}

	@Override
	public void onLoad() {
		GameLobby.instance = this;

		this.activeMap = null;

		this.ignoreNoTeam = false;

		this.mapReader = new DefaultLobbyMapReader();
		this.mapSelector = new RandomLobbyMapSelector();

		this.waitingPlayers = new ArrayList<UUID>();

		this.maps = new ArrayList<GameLobbyMapData>();

		this.taskId = -1;
	}

	public GameLobby() {
		super("NovaCore.GameEngine.GameLobby");

		this.addDependency(MultiverseManager.class);
		this.addDependency(GameManager.class);

		this.activeMap = null;
	}

	@Override
	public void onEnable() {
		GameLobbyMapData map = mapSelector.getMapToUse();

		if (map == null) {
			Log.fatal("GameLobby", "The lobby map selector returned no map");
			this.disable();
			return;
		}

		try {
			this.activeMap = (GameLobbyMap) map.load();
		} catch (IOException e) {
			e.printStackTrace();
			Log.fatal("GameLobby", "Failed to load the map to use for game lobby");
			this.disable();
			return;
		}

		this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(NovaCore.getInstance(), new Runnable() {
			@Override
			public void run() {
				if (hasActiveMap()) {
					getActiveMap().getWorld().getPlayers().forEach(player -> {
						if (GameManager.getInstance().hasGame()) {
							if (GameManager.getInstance().getActiveGame().hasStarted()) {
								GameManager.getInstance().getActiveGame().tpToSpectator(player);
								return;
							}
						}

						player.setFoodLevel(20);

						if (player.getLocation().getY() < 0) {
							player.setFallDistance(0);
							player.teleport(activeMap.getSpawnLocation());
						}
					});
				}
			}
		}, 5L, 5L);
	}

	@Override
	public void onDisable() {
		if (taskId != -1) {
			Bukkit.getScheduler().cancelTask(taskId);
			taskId = -1;
		}
	}

	public List<UUID> getWaitingPlayers() {
		return waitingPlayers;
	}

	public GameLobbyReader getMapReader() {
		return mapReader;
	}

	public void setMapReader(GameLobbyReader mapReader) {
		this.mapReader = mapReader;
	}

	public GameLobbyMap getActiveMap() {
		return activeMap;
	}

	public boolean hasActiveMap() {
		return activeMap != null;
	}

	public LobbyMapSelector getMapSelector() {
		return mapSelector;
	}

	public void setMapSelector(LobbyMapSelector mapSelector) {
		this.mapSelector = mapSelector;
	}

	public boolean startGame() {
		if (!GameManager.getInstance().getActiveGame().canStart()) {
			return false;
		}

		try {
			for (UUID uuid : waitingPlayers) {
				Player player = Bukkit.getServer().getPlayer(uuid);

				if (player != null) {
					if (player.isOnline()) {
						// Prevent players from joining if teams are enabled and the player does not
						// have a team
						if (!ignoreNoTeam) {
							if (NovaCore.getInstance().hasTeamManager()) {
								Team team = NovaCore.getInstance().getTeamManager().getPlayerTeam(player);
								if (team == null) {
									player.sendMessage(LanguageManager.getString(player, "novacore.game.lobby.spectator_no_team"));
									continue;
								}
							}
						}

						GameManager.getInstance().getActiveGame().addPlayer(player);
					}
				}
			}

			waitingPlayers.clear();

			GameManager.getInstance().start();

			return true;
		} catch (Exception e) {
			e.printStackTrace();

			GameStartFailureEvent event = new GameStartFailureEvent(GameManager.getInstance().getActiveGame(), e);
			Bukkit.getPluginManager().callEvent(event);
		}

		return false;
	}

	private void tpToLobby(Player player) {
		player.teleport(activeMap.getSpawnLocation());

		// player.getInventory().clear();

		player.setGameMode(GameMode.ADVENTURE);
		NovaCore.getInstance().getVersionIndependentUtils().setEntityMaxHealth(player, 20);
		player.setHealth(20);
		player.setFoodLevel(20);
	}

	// ----- Listeners -----

	// -- Join and quit --
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent e) {
		final Player player = e.getPlayer();
		if (hasActiveMap()) {
			if (GameManager.getInstance().hasGame()) {
				if (GameManager.getInstance().getActiveGame().hasStarted()) {
					if (!GameManager.getInstance().getActiveGame().getPlayers().contains(player.getUniqueId())) {
						GameManager.getInstance().getActiveGame().tpToSpectator(player);
					}
				} else {
					if (!waitingPlayers.contains(player.getUniqueId())) {
						waitingPlayers.add(player.getUniqueId());
					}

					if (!GameManager.getInstance().getActiveGame().hasStarted()) {
						PlayerUtils.clearPlayerInventory(player);
					}

					player.setGameMode(GameMode.SPECTATOR);
					new BukkitRunnable() {
						@Override
						public void run() {
							if (GameManager.getInstance().getActiveGame().hasStarted()) {
								return;
							}
							player.teleport(activeMap.getSpawnLocation());
						}
					}.runTaskLater(getPlugin(), 10L);

					new BukkitRunnable() {
						@Override
						public void run() {
							if (GameManager.getInstance().getActiveGame().hasStarted()) {
								return;
							}
							tpToLobby(player);
						}
					}.runTaskLater(getPlugin(), 20L);

					Bukkit.getServer().getPluginManager().callEvent(new PlayerJoinGameLobbyEvent(player));
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();

		if (waitingPlayers.contains(p.getUniqueId())) {
			Log.debug("Removing player " + e.getPlayer().getName() + " from the waiting players list");
			waitingPlayers.remove(p.getUniqueId());
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDeath(PlayerDeathEvent e) {
		if (waitingPlayers.contains(e.getEntity().getUniqueId())) {
			if (!GameManager.getInstance().getActiveGame().hasStarted()) {
				e.getEntity().spigot().respawn();
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		if (waitingPlayers.contains(e.getPlayer().getUniqueId())) {
			e.setRespawnLocation(activeMap.getSpawnLocation());

			tpToLobby(e.getPlayer());
		}
	}

	// -- World interactions --

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent e) {
		if (hasActiveMap()) {
			if (e.getBlock().getWorld() == activeMap.getWorld()) {
				if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlace(BlockPlaceEvent e) {
		if (hasActiveMap()) {
			if (e.getBlock().getWorld() == activeMap.getWorld()) {
				if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (hasActiveMap()) {
			if (e.getDamager().getWorld() == activeMap.getWorld()) {
				if (e.getDamager() instanceof Player) {
					if (((Player) e.getDamager()).getGameMode() != GameMode.CREATIVE) {
						return;
					}
				}

				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamage(EntityDamageEvent e) {
		if (hasActiveMap()) {
			if (e.getEntity() instanceof Player) {
				if (e.getEntity().getWorld() == activeMap.getWorld()) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (hasActiveMap()) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (e.getPlayer().getWorld() == activeMap.getWorld()) {
					if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerArmorStandManipulate(PlayerArmorStandManipulateEvent e) {
		if (hasActiveMap()) {
			if (e.getPlayer().getWorld() == activeMap.getWorld()) {
				if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
					e.setCancelled(true);
				}
			}
		}
	}

	// -- World --

	@EventHandler(priority = EventPriority.NORMAL)
	public void onWeatherChange(WeatherChangeEvent e) {
		if (hasActiveMap()) {
			if (e.getWorld() == activeMap.getWorld()) {
				if (e.toWeatherState()) {
					e.setCancelled(true);
				}
			}
		}
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}