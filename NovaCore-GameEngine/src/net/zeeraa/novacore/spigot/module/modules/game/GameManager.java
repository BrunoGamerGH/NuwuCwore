package net.zeeraa.novacore.spigot.module.modules.game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.commons.tasks.Task;
import net.zeeraa.novacore.commons.utils.Callback;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.command.CommandRegistry;
import net.zeeraa.novacore.spigot.gameengine.command.commands.game.NovaCoreCommandGame;
import net.zeeraa.novacore.spigot.language.LanguageManager;
import net.zeeraa.novacore.spigot.module.NovaModule;
import net.zeeraa.novacore.spigot.module.modules.game.countdown.DefaultGameCountdown;
import net.zeeraa.novacore.spigot.module.modules.game.countdown.GameCountdown;
import net.zeeraa.novacore.spigot.module.modules.game.elimination.EliminationTask;
import net.zeeraa.novacore.spigot.module.modules.game.elimination.PlayerEliminationReason;
import net.zeeraa.novacore.spigot.module.modules.game.elimination.PlayerQuitEliminationAction;
import net.zeeraa.novacore.spigot.module.modules.game.events.GameEndEvent;
import net.zeeraa.novacore.spigot.module.modules.game.events.GameLoadedEvent;
import net.zeeraa.novacore.spigot.module.modules.game.events.GameStartEvent;
import net.zeeraa.novacore.spigot.module.modules.game.events.GameStartFailureEvent;
import net.zeeraa.novacore.spigot.module.modules.game.map.GameMapData;
import net.zeeraa.novacore.spigot.module.modules.game.map.MapReader;
import net.zeeraa.novacore.spigot.module.modules.game.map.readers.DefaultMapReader;
import net.zeeraa.novacore.spigot.module.modules.game.mapselector.MapSelector;
import net.zeeraa.novacore.spigot.module.modules.game.mapselector.selectors.RandomMapSelector;
import net.zeeraa.novacore.spigot.module.modules.game.messages.GameStartFailureMessage;
import net.zeeraa.novacore.spigot.module.modules.game.messages.PlayerEliminationMessage;
import net.zeeraa.novacore.spigot.module.modules.game.messages.TeamEliminationMessage;
import net.zeeraa.novacore.spigot.module.modules.game.messages.defaultmessage.DefaultGameStartFailureMessage;
import net.zeeraa.novacore.spigot.module.modules.game.messages.defaultmessage.DefaultPlayerEliminationMessage;
import net.zeeraa.novacore.spigot.module.modules.game.triggers.GameTrigger;
import net.zeeraa.novacore.spigot.module.modules.game.triggers.TriggerFlag;
import net.zeeraa.novacore.spigot.module.modules.multiverse.MultiverseManager;
import net.zeeraa.novacore.spigot.tasks.SimpleTask;
import net.zeeraa.novacore.spigot.utils.PlayerUtils;

/**
 * Module used to manage games and maps
 * 
 * @author Zeeraa
 */
public class GameManager extends NovaModule implements Listener {
	private static GameManager instance;
	private Game activeGame;

	private HashMap<UUID, EliminationTask> eliminationTasks;

	private MapSelector mapSelector;
	private MapReader mapReader;

	private boolean useTeams;

	private GameCountdown countdown;

	private boolean commandAdded;

	private List<UUID> callOnRespawn;

	private PlayerEliminationMessage playerEliminationMessage;
	private TeamEliminationMessage teamEliminationMessage;
	private GameStartFailureMessage startFailureMessage;

	private HashMap<UUID, Integer> combatTaggedPlayers;

	private Task combatTagCountdownTask;

	private boolean showDeathMessaage;

	private boolean useCombatTagging;
	private int combatTaggingTime;

	private boolean autoRespawn;

	private boolean dropItemsOnDeath;

	private List<CombatTagMessage> combatTagMessages;

	/**
	 * Get instance of {@link GameManager}
	 * 
	 * @return Instance
	 */
	public static GameManager getInstance() {
		return instance;
	}

	public GameManager() {
		GameManager.instance = this;

		this.useTeams = false;

		this.mapSelector = new RandomMapSelector();
		this.mapReader = new DefaultMapReader();

		this.addDependency(MultiverseManager.class);

		this.activeGame = null;
		this.eliminationTasks = new HashMap<UUID, EliminationTask>();

		this.countdown = new DefaultGameCountdown();

		this.startFailureMessage = new DefaultGameStartFailureMessage();
		this.playerEliminationMessage = new DefaultPlayerEliminationMessage();
		this.teamEliminationMessage = null;

		this.callOnRespawn = new ArrayList<UUID>();

		this.commandAdded = false;

		this.combatTaggedPlayers = new HashMap<UUID, Integer>();

		this.useCombatTagging = false;
		this.combatTaggingTime = 5;

		this.combatTagMessages = new ArrayList<CombatTagMessage>();

		this.combatTagCountdownTask = new SimpleTask(NovaCore.getInstance(), new Runnable() {
			@Override
			public void run() {
				for (UUID uuid : combatTaggedPlayers.keySet()) {
					combatTaggedPlayers.put(uuid, combatTaggedPlayers.get(uuid) - 1);
				}

				combatTaggedPlayers.entrySet().removeIf(i -> i.getValue() <= 0);
			}
		}, 20L, 20L);

		this.showDeathMessaage = false;
		this.dropItemsOnDeath = false;
	}

	/**
	 * Check if the {@link GameManager} should drop player items on death instead
	 * (disabled by default).
	 * <p>
	 * This might cause items to be duped
	 * 
	 * @return <code>true</code> if enabled
	 */
	public boolean isDropItemsOnDeath() {
		return dropItemsOnDeath;
	}

	/**
	 * Set if the {@link GameManager} should drop player items on death instead
	 * (disabled by default).
	 * <p>
	 * This might cause items to be duped
	 * 
	 * @param dropItemsOnDeath <code>true</code> to enable
	 */
	public void setDropItemsOnDeath(boolean dropItemsOnDeath) {
		this.dropItemsOnDeath = dropItemsOnDeath;
	}

	/**
	 * Get a {@link List} with all {@link CombatTagMessage}s the game is using.
	 * <p>
	 * You can add and remove objects from this list and they will also get added or
	 * removed from the {@link GameManager}
	 * 
	 * @return {@link List} with {@link CombatTagMessage}
	 */
	public List<CombatTagMessage> getCombatTagMessages() {
		return combatTagMessages;
	}

	/**
	 * Add a {@link CombatTagMessage} to the list of combat tag messages used by
	 * {@link GameManager}
	 * 
	 * @param message The {@link CombatTagMessage} to add
	 */
	public void addCombatTagMessage(CombatTagMessage message) {
		combatTagMessages.add(message);
	}

	/**
	 * Run all {@link CombatTagMessage#showTaggedMessage(Player)} for a player
	 * 
	 * @param player The player to message
	 */
	public void showCombatTaggedMessage(Player player) {
		try {
			for (CombatTagMessage message : combatTagMessages) {
				message.showTaggedMessage(player);
			}
		} catch (Exception e) {
			Log.error("Error while showing combat tagged message fro a player: " + e.getClass().getName() + " " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Run all {@link CombatTagMessage#showNoLongerTaggedMessage(Player)} for a
	 * player
	 * 
	 * @param player The player to message
	 */
	public void showNoLongerCombatTaggedMessage(Player player) {
		try {
			for (CombatTagMessage message : combatTagMessages) {
				message.showNoLongerTaggedMessage(player);
			}
		} catch (Exception e) {
			Log.error("Error while showing no longer combat tagged message fro a player: " + e.getClass().getName() + " " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Check if auto respawn is enabled. This is enabled by default
	 * 
	 * @return <code>true</code> if auto respawn is enabled
	 */
	public boolean isAutoRespawn() {
		return autoRespawn;
	}

	/**
	 * Enable or disable auto respawning
	 * 
	 * @param autoRespawn <code>true</code> to auto respawn players,
	 *                    <code>false</code> to disable auto respawn
	 */
	public void setAutoRespawn(boolean autoRespawn) {
		this.autoRespawn = autoRespawn;
	}

	/**
	 * Check if the {@link GameManager} should use teams
	 * 
	 * @return <code>true</code> if teams should be used
	 */
	public boolean isUseTeams() {
		return useTeams;
	}

	/**
	 * Set if the {@link GameManager} should use teams
	 * 
	 * @param useTeams <code>true</code> to use teams
	 */
	public void setUseTeams(boolean useTeams) {
		this.useTeams = useTeams;
	}

	/**
	 * Get the map selector. The default map selector is {@link RandomMapSelector}.
	 * <p>
	 * This is only used for games based on {@link MapGame}
	 * 
	 * @return The {@link MapSelector} to use
	 */
	public MapSelector getMapSelector() {
		return mapSelector;
	}

	/**
	 * Set the map selector to use.
	 * <p>
	 * The default map selector is {@link RandomMapSelector}.
	 * <p>
	 * This is only used for games based on {@link MapGame}
	 * 
	 * @param mapSelector The {@link MapSelector} to use
	 */
	public void setMapSelector(MapSelector mapSelector) {
		this.mapSelector = mapSelector;
	}

	/**
	 * Get the map reader. The default map reader is {@link DefaultMapReader}.
	 * <p>
	 * This is only used for games based on {@link MapGame}
	 * 
	 * @return The {@link DefaultMapReader} to use
	 */
	public MapReader getMapReader() {
		return mapReader;
	}

	/**
	 * Check if a player is in the game
	 * 
	 * @param player The player to check
	 * @return <code>true</code> if the player is in the game
	 */
	public boolean isInGame(Player player) {
		return this.isInGame(player.getUniqueId());
	}

	/**
	 * Check if a player is in the game
	 * 
	 * @param player The uuid of the player to check
	 * @return <code>true</code> if the player is in the game
	 */
	public boolean isInGame(UUID player) {
		if (hasGame()) {
			return getActiveGame().getPlayers().contains(player);
		}

		return false;
	}

	/**
	 * Try to load all JSON files from a directory as maps and add them to the
	 * active {@link MapSelector} defined by {@link GameManager#getMapSelector()}
	 * 
	 * @param directory      Directory to scan
	 * @param worldDirectory The directory containing the worlds
	 */
	public void addMaps(File directory, File worldDirectory) {
		mapReader.loadAll(directory, worldDirectory);
	}

	/**
	 * Read {@link GameMapData} from a {@link File} list and add it to the active
	 * {@link MapSelector} defined by {@link GameManager#getMapSelector()}
	 * 
	 * @param mapFile        The {@link File} to read
	 * @param worldDirectory The directory containing the worlds
	 * @return <code>true</code> on success
	 */
	public boolean addMap(File mapFile, File worldDirectory) {
		return mapReader.readMap(mapFile, worldDirectory) != null;
	}

	@Override
	public String getName() {
		return "GameManager";
	}

	/**
	 * Get the {@link Game} that has been loaded
	 * 
	 * @return Instance of the active {@link Game}
	 */
	public Game getActiveGame() {
		return activeGame;
	}

	/**
	 * Check if a game is loaded
	 * 
	 * @return <code>true</code> if a game has been loaded
	 */
	public boolean hasGame() {
		return this.activeGame != null;
	}

	/**
	 * Load a {@link Game} and register listeners
	 * 
	 * @param game {@link Game} to be loaded
	 * @return <code>true</code> on success been added
	 */
	public boolean loadGame(Game game) {
		if (this.hasGame()) {
			return false;
		}

		Log.info("Loading game " + game.getName());

		game.onLoad();
		if (game instanceof Listener) {
			Bukkit.getPluginManager().registerEvents((Listener) game, NovaCore.getInstance());
		}

		this.activeGame = game;

		Bukkit.getServer().getPluginManager().callEvent(new GameLoadedEvent(activeGame));

		return true;
	}

	@Override
	public void onEnable() {
		if (!commandAdded) {
			Log.info("Adding the game command");
			CommandRegistry.registerCommand(new NovaCoreCommandGame());
			commandAdded = true;
		}

		combatTagCountdownTask.start();
	}

	@Override
	public void onDisable() {
		for (UUID uuid : eliminationTasks.keySet()) {
			EliminationTask eliminationTask = eliminationTasks.get(uuid);
			if (eliminationTask != null) {
				eliminationTask.cancel();
			}
		}

		if (activeGame != null) {
			if (activeGame instanceof Listener) {
				HandlerList.unregisterAll((Listener) activeGame);
			}

			activeGame.onUnload();
		}

		combatTagCountdownTask.stop();

		combatTaggedPlayers.clear();

		if (callOnRespawn != null) {
			callOnRespawn.clear();
		}
	}

	/**
	 * Start the game.
	 * <p>
	 * If the game is a {@link MapGame} a map will also be selected by the
	 * {@link MapSelector} and will be loaded
	 * 
	 * @throws IOException          if the game is a {@link MapGame} and the map
	 *                              load function throws an {@link IOException}
	 * @throws NoMapsAddedException if the game is a {@link MapGame} and no maps has
	 * 
	 */
	public void start() throws IOException {
		try {
			Log.debug("GameManager is trying to start a game");
			if (activeGame instanceof MapGame) {
				if (mapSelector.getMaps().size() == 0) {
					Log.fatal("No maps has been loaded");
					throw new NoMapsAddedException("No maps has been loaded");
				}

				GameMapData map = mapSelector.getMapToUse();
				Log.debug("Selected map: " + map.getDisplayName());

				Log.info("Loading game map");
				((MapGame) activeGame).loadMap(map);
			}

			Log.debug("Calling start on " + activeGame.getClass().getName());
			activeGame.startGame();
		} catch (Exception e) {
			e.printStackTrace();
			GameStartFailureEvent event = new GameStartFailureEvent(GameManager.getInstance().getActiveGame(), e);
			Bukkit.getPluginManager().callEvent(event);
		}
	}

	/**
	 * Call the {@link Game#endGame(GameEndReason)} of the active game
	 * <p>
	 * Deprecated since {@link GameManager#endGame(GameEndReason)} should be used
	 * instead of this
	 * <p>
	 * This will use the default {@link GameEndReason} value of
	 * {@link GameEndReason#UNSPECIFIED}
	 * 
	 * @return <code>false</code> if {@link Game#endGame(GameEndReason)} returns
	 *         <code>false</code> or if not game has been loaded
	 */
	@Deprecated
	public boolean endGame() {
		return this.endGame(GameEndReason.UNSPECIFIED);
	}

	/**
	 * Call the {@link Game#endGame(GameEndReason)} of the active game
	 * 
	 * @param reason The reason why the game ended
	 * 
	 * @return <code>false</code> if {@link Game#endGame(GameEndReason)} returns
	 *         <code>false</code> or if not game has been loaded
	 */
	public boolean endGame(GameEndReason reason) {
		if (hasGame()) {
			return activeGame.endGame(reason);
		}
		return false;
	}

	/**
	 * Set to <code>true</code> to use the combat tagging system
	 * 
	 * @param useCombatTagging <code>true</code> to enable combat tagging
	 */
	public void setUseCombatTagging(boolean useCombatTagging) {
		this.useCombatTagging = useCombatTagging;
	}

	/**
	 * Check it the combat tagging system is enabled
	 * 
	 * @return <code>true</code> if the combat tagging system is used
	 */
	public boolean isUseCombatTagging() {
		return useCombatTagging;
	}

	/**
	 * Get the time in seconds to combat tag players for
	 * 
	 * @return Time in seconds to combat tag players for
	 */
	public int getCombatTaggingTime() {
		return combatTaggingTime;
	}

	/**
	 * Set the time in seconds that players will be combat tagged for
	 * 
	 * @param combatTaggingTime Time to set
	 */
	public void setCombatTaggingTime(int combatTaggingTime) {
		this.combatTaggingTime = combatTaggingTime;
	}

	/**
	 * Set the countdown time in seconds
	 * 
	 * @param countdown The count down time
	 */
	public void setCountdown(GameCountdown countdown) {
		this.countdown = countdown;
	}

	/**
	 * Get the countdown in seconds for the game
	 * 
	 * @return Count down time in seconds
	 */
	public GameCountdown getCountdown() {
		return countdown;
	}

	/**
	 * Check if the game has a countdown before starting
	 * 
	 * @return <code>true</code> if the game has a countdown
	 */
	public boolean hasCountdown() {
		return countdown != null;
	}

	/**
	 * Get the {@link PlayerEliminationMessage} to use
	 * 
	 * @return {@link PlayerEliminationMessage} or <code>null</code> if disabled
	 */
	public PlayerEliminationMessage getPlayerEliminationMessage() {
		return playerEliminationMessage;
	}

	/**
	 * Set the {@link PlayerEliminationMessage}.
	 * <p>
	 * Set to <code>null</code> to disable.
	 * 
	 * @param playerEliminationMessage {@link PlayerEliminationMessage} to use
	 */
	public void setPlayerEliminationMessage(PlayerEliminationMessage playerEliminationMessage) {
		this.playerEliminationMessage = playerEliminationMessage;
	}

	/**
	 * Get the {@link TeamEliminationMessage} to use
	 * 
	 * @return {@link TeamEliminationMessage} or <code>null</code> if disabled
	 */
	public TeamEliminationMessage getTeamEliminationMessage() {
		return teamEliminationMessage;
	}

	/**
	 * Set the {@link TeamEliminationMessage}.
	 * <p>
	 * Set to <code>null</code> to disable.
	 * 
	 * @param teamEliminationMessage {@link TeamEliminationMessage} to use
	 */
	public void setTeamEliminationMessage(TeamEliminationMessage teamEliminationMessage) {
		this.teamEliminationMessage = teamEliminationMessage;
	}

	/**
	 * Call this to combat tag a player.
	 * <p>
	 * Combat tagged players will always be eliminated even if the
	 * {@link Game#getPlayerQuitEliminationAction()} is set
	 * {@link PlayerQuitEliminationAction#NONE}
	 * 
	 * @param player The {@link Player} to combat tag
	 * @return <code>true</code> if the player was tagged
	 */
	public boolean combatTagPlayer(Player player) {
		return this.combatTagPlayer(player, true);
	}

	/**
	 * Call this to combat tag a player.
	 * <p>
	 * Combat tagged players will always be eliminated even if the
	 * {@link Game#getPlayerQuitEliminationAction()} is set
	 * {@link PlayerQuitEliminationAction#NONE}
	 * 
	 * @param player      The {@link Player} to combat tag
	 * @param showMessage Set to <code>false</code> to disable the combat tag
	 *                    message
	 * @return <code>true</code> if the player was tagged
	 */
	public boolean combatTagPlayer(Player player, boolean showMessage) {
		if (hasGame()) {
			if (getActiveGame().hasStarted()) {
				combatTaggedPlayers.put(player.getUniqueId(), combatTaggingTime);
				if (showMessage) {
					showCombatTaggedMessage(player);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Remove the combat tag from a player
	 * <p>
	 * Combat tagged players will always be eliminated even if the
	 * {@link Game#getPlayerQuitEliminationAction()} is set
	 * {@link PlayerQuitEliminationAction#NONE}
	 * 
	 * @param player The {@link Player} to remove the combat tag from
	 */
	public void removeCombatTag(Player player) {
		this.removeCombatTag(player, true);
	}

	/**
	 * Remove the combat tag from a player
	 * <p>
	 * Combat tagged players will always be eliminated even if the
	 * {@link Game#getPlayerQuitEliminationAction()} is set
	 * {@link PlayerQuitEliminationAction#NONE}
	 * 
	 * @param player      The {@link Player} to remove the combat tag from
	 * @param showMessage Set to <code>false</code> to disable the combat tag
	 */
	public void removeCombatTag(Player player, boolean showMessage) {
		removeCombatTag(player.getUniqueId());
		if (showMessage) {
			showNoLongerCombatTaggedMessage(player);
		}
	}

	/**
	 * Remove the combat tag from a player
	 * <p>
	 * Combat tagged players will always be eliminated even if the
	 * {@link Game#getPlayerQuitEliminationAction()} is set
	 * {@link PlayerQuitEliminationAction#NONE}
	 * 
	 * @param uuid The {@link UUID} of the player to remove the combat tag from
	 */
	public void removeCombatTag(UUID uuid) {
		this.removeCombatTag(uuid, true);
	}

	/**
	 * Remove the combat tag from a player
	 * <p>
	 * Combat tagged players will always be eliminated even if the
	 * {@link Game#getPlayerQuitEliminationAction()} is set
	 * {@link PlayerQuitEliminationAction#NONE}
	 * 
	 * @param uuid        The {@link UUID} of the player to remove the combat tag
	 *                    from
	 * @param showMessage Set to <code>false</code> to disable the combat tag
	 */
	public void removeCombatTag(UUID uuid, boolean showMessage) {
		combatTaggedPlayers.remove(uuid);
		if (showMessage) {
			Player player = Bukkit.getServer().getPlayer(uuid);
			if (player != null) {
				if (player.isOnline()) {
					showNoLongerCombatTaggedMessage(player);
				}
			}
		}
	}

	/**
	 * Get a {@link HashMap} containing all combat tagged players with the
	 * {@link UUID} of the player as key and seconds left as value
	 * <p>
	 * Combat tagged players will always be eliminated even if the
	 * {@link Game#getPlayerQuitEliminationAction()} is set
	 * {@link PlayerQuitEliminationAction#NONE}
	 * 
	 * @return {@link HashMap} containing all combat tagged players
	 */
	public HashMap<UUID, Integer> getCombatTaggedPlayers() {
		return combatTaggedPlayers;
	}

	/**
	 * Check if a player is combat tagged
	 * <p>
	 * Combat tagged players will always be eliminated even if the
	 * {@link Game#getPlayerQuitEliminationAction()} is set
	 * {@link PlayerQuitEliminationAction#NONE}
	 * 
	 * @param player The {@link Player} to check
	 * @return <code>true</code> if the player is combat tagged
	 */
	public boolean isCombatTagged(Player player) {
		return isCombatTagged(player.getUniqueId());
	}

	/**
	 * Check if a player is combat tagged
	 * <p>
	 * Combat tagged players will always be eliminated even if the
	 * {@link Game#getPlayerQuitEliminationAction()} is set
	 * {@link PlayerQuitEliminationAction#NONE}
	 * 
	 * @param uuid The {@link UUID} of the player to check
	 * @return <code>true</code> if the player is combat tagged
	 */
	public boolean isCombatTagged(UUID uuid) {
		return combatTaggedPlayers.containsKey(uuid);
	}

	public boolean isShowDeathMessaage() {
		return showDeathMessaage;
	}

	public void setShowDeathMessaage(boolean showDeathMessaage) {
		this.showDeathMessaage = showDeathMessaage;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();

		if (hasGame()) {
			if (getActiveGame().hasStarted()) {
				if (getActiveGame().getPlayers().contains(p.getUniqueId())) {
					if (!showDeathMessaage) {
						e.setDeathMessage(null);
					}

					if (getActiveGame().eliminatePlayerOnDeath(p)) {
						try {
							if (dropItemsOnDeath) {
								Location lootLocation = e.getEntity().getLocation().clone();
								
								try {
									ItemStack[] armor = e.getEntity().getInventory().getArmorContents();
									for (ItemStack item : armor) {
										if (item != null) {
											if (item.getType() != Material.AIR) {
												e.getEntity().getWorld().dropItem(lootLocation, item.clone());
											}
										}
									}
								} catch (Exception e2) {
									e2.printStackTrace();
								}

								for (ItemStack item : e.getEntity().getInventory().getContents()) {
									if (item != null) {
										if (item.getType() != Material.AIR) {
											e.getEntity().getWorld().dropItem(lootLocation, item.clone());
										}
									}
								}
							}
						} catch (Exception e2) {
							e2.printStackTrace();
						}

						// e.setKeepInventory(true); i dont think this should be here

						PlayerUtils.clearPlayerInventory(e.getEntity());

						LivingEntity killer = p.getKiller();

						Log.trace("Eliminating player on death");
						getActiveGame().eliminatePlayer(p, killer, (killer == null ? PlayerEliminationReason.DEATH : PlayerEliminationReason.KILLED));
					}

					if (!callOnRespawn.contains(p.getUniqueId())) {
						callOnRespawn.add(p.getUniqueId());
					}

					if (autoRespawn) {
						new BukkitRunnable() {

							@Override
							public void run() {
								p.spigot().respawn();
							}
						}.runTaskLater(NovaCore.getInstance(), 5L);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();

		if (callOnRespawn.contains(p.getUniqueId())) {
			if (hasGame()) {
				getActiveGame().onPlayerRespawn(p);
				callOnRespawn.remove(p.getUniqueId());
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (hasGame()) {
			if (activeGame.hasStarted()) {
				if (e.getEntity() instanceof Player) {
					UUID damagerUuid = null;

					if (e.getDamager() instanceof Player) {
						damagerUuid = e.getDamager().getUniqueId();
					} else if (e.getDamager() instanceof Projectile) {
						Projectile projectile = (Projectile) e.getDamager();
						if (projectile.getShooter() != null) {
							if (projectile.getShooter() instanceof Player) {
								damagerUuid = ((Player) ((Projectile) e.getDamager()).getShooter()).getUniqueId();
							}
						}
					} else if (e.getDamager() instanceof Tameable) {
						Tameable tameable = (Tameable) e.getDamager();

						if (tameable.getOwner() instanceof HumanEntity) {
							damagerUuid = ((HumanEntity) tameable.getOwner()).getUniqueId();
						}
					}

					if (damagerUuid != null) {
						if (!activeGame.isPVPEnabled()) {
							e.setCancelled(true);
							return;
						}

						if (useTeams) {
							if (NovaCore.getInstance().hasTeamManager()) {
								if (NovaCore.getInstance().getTeamManager().isInSameTeam(((OfflinePlayer) e.getEntity()).getUniqueId(), damagerUuid)) {
									if (!getActiveGame().isFriendlyFireAllowed()) {
										e.setCancelled(true);
										return;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onEntityDamageByEntityMonitor(EntityDamageByEntityEvent e) {
		if (hasGame()) {
			if (activeGame.hasStarted()) {
				if (e.getEntity() instanceof Player) {
					UUID damagerUuid = null;

					if (e.getDamager() instanceof Player) {
						damagerUuid = e.getDamager().getUniqueId();
					} else if (e.getDamager() instanceof Arrow) {
						Projectile projectile = (Projectile) e.getDamager();
						if (projectile.getShooter() != null) {
							if (projectile.getShooter() instanceof Player) {
								damagerUuid = ((Player) ((Projectile) e.getDamager()).getShooter()).getUniqueId();
							}
						}
					} else if (e.getDamager() instanceof Tameable) {
						Tameable tameable = (Tameable) e.getDamager();

						if (tameable.getOwner() instanceof HumanEntity) {
							damagerUuid = ((HumanEntity) tameable.getOwner()).getUniqueId();
						}
					}

					if (damagerUuid != null) {
						if (!activeGame.isPVPEnabled()) {
							return;
						}

						if (useTeams) {
							if (NovaCore.getInstance().hasTeamManager()) {
								if (NovaCore.getInstance().getTeamManager().isInSameTeam(((OfflinePlayer) e.getEntity()).getUniqueId(), damagerUuid)) {
									return;
								}
							}

							if (useCombatTagging) {
								combatTagPlayer((Player) e.getEntity(), !isCombatTagged((Player) e.getEntity()));
							}
						} else {
							if (useCombatTagging) {
								combatTagPlayer((Player) e.getEntity(), !isCombatTagged((Player) e.getEntity()));
							}
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (eliminationTasks.containsKey(e.getPlayer().getUniqueId())) {
			eliminationTasks.get(p.getUniqueId()).cancel();
			eliminationTasks.remove(p.getUniqueId());

			// Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "" + ChatColor.BOLD + ""
			// + p.getName() + ChatColor.GREEN + ChatColor.BOLD + " reconnected in time");
			LanguageManager.broadcast("novacore.game.elimination.player.reconnected", p.getName());
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (callOnRespawn.contains(p.getUniqueId())) {
			callOnRespawn.remove(p.getUniqueId());
		}

		if (hasGame()) {
			if (activeGame.hasEnded()) {
				return;
			}
			if (activeGame.hasStarted()) {
				if (activeGame.getPlayers().contains(p.getUniqueId())) {
					if (isCombatTagged(p)) {
						activeGame.eliminatePlayer(p, null, PlayerEliminationReason.COMBAT_LOGGING);
					} else {

						switch (activeGame.getPlayerQuitEliminationAction()) {
						case NONE:
							break;

						case INSTANT:
							activeGame.eliminatePlayer(p, null, PlayerEliminationReason.QUIT);
							break;

						case DELAYED:
							EliminationTask eliminationTask = new EliminationTask(p.getUniqueId(), p.getName(), activeGame.getPlayerEliminationDelay(), new Callback() {
								@Override
								public void execute() {
									getActiveGame().eliminatePlayer(p, null, PlayerEliminationReason.DID_NOT_RECONNECT);
								}
							});
							eliminationTasks.put(e.getPlayer().getUniqueId(), eliminationTask);
							int minutes = (activeGame.getPlayerEliminationDelay() / 60);
							int seconds = activeGame.getPlayerEliminationDelay() % 60;

							Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "" + p.getName() + ChatColor.RED + ChatColor.BOLD + " disconnected. They have " + minutes + " minute" + (minutes == 1 ? "" : "s") + (seconds == 0 ? "" : " and " + seconds + " seconds") + " to reconnect");

							break;

						default:
							break;
						}
					}
				}
			}
		}

		removeCombatTag(p);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onGameStartFailure(GameStartFailureEvent e) {
		if (startFailureMessage != null) {
			startFailureMessage.showStartFailureMessage(e.getGame(), e.getException());
		}
		GameTrigger.triggerMany(e.getGame().getTriggersByFlag(TriggerFlag.TRIGGER_ON_GAME_START_FAILURE), TriggerFlag.TRIGGER_ON_GAME_START_FAILURE);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onGameStart(GameStartEvent e) {
		GameTrigger.triggerMany(e.getGame().getTriggersByFlag(TriggerFlag.TRIGGER_ON_GAME_START), TriggerFlag.TRIGGER_ON_GAME_START);
		GameTrigger.startMany(e.getGame().getTriggersByFlag(TriggerFlag.START_ON_GAME_START));
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onGameEnd(GameEndEvent e) {
		GameTrigger.triggerMany(e.getGame().getTriggersByFlag(TriggerFlag.TRIGGER_ON_GAME_END), TriggerFlag.TRIGGER_ON_GAME_END);
		GameTrigger.stopMany(e.getGame().getTriggersByFlag(TriggerFlag.STOP_ON_GAME_END));
	}
}