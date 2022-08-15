package net.zeeraa.novacore.spigot.gameengine.module.modules.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.Plugin;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.commons.tasks.Task;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.elimination.PlayerEliminationReason;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.elimination.PlayerQuitEliminationAction;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.events.GameBeginEvent;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.events.GameEndEvent;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.events.GameStartEvent;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.events.PlayerEliminatedEvent;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.events.PlayerWinEvent;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.events.TeamEliminatedEvent;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.events.TeamWinEvent;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.GameTrigger;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.ScheduledGameTrigger;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.TriggerFlag;
import net.zeeraa.novacore.spigot.tasks.SimpleTask;
import net.zeeraa.novacore.spigot.teams.Team;
import net.zeeraa.novacore.spigot.utils.PlayerUtils;

/**
 * This class represents a game that {@link GameManager} can use
 * <p>
 * If this extends {@link Listener} {@link GameManager#loadGame(Game)} will
 * register the listener when called
 * <p>
 * When creating games remember to call {@link Game#sendBeginEvent()} as soon as
 * the game begins and the count downs have finished
 * 
 * @author Zeeraa
 */
public abstract class Game extends some_bitches {
	/**
	 * This is the task that run the winner check.
	 * <p>
	 * This should never be changed by the game code unless you know what you are
	 * doing
	 */
	protected Task winCheckTask;

	/**
	 * This is used the prevent {@link Game#endGame(GameEndReason)} from being
	 * called twice
	 * <p>
	 * This should never be changed by the game code unless you know what you are
	 * doing
	 */
	protected boolean endCalled;

	/**
	 * This is used the prevent {@link Game#startGame()} from being called twice
	 * <p>
	 * This should never be changed by the game code unless you know what you are
	 * doing
	 */
	protected boolean startCalled;

	/**
	 * This is false until a winner is found.
	 * <p>
	 * This should never be changed by the game code unless you know what you are
	 * doing
	 */
	protected boolean autoWinnerCheckCompleted;

	/**
	 * List of all players participating in the game
	 */
	protected List<UUID> players;

	/**
	 * List of all game triggers
	 */
	protected List<GameTrigger> triggers;

	/**
	 * The {@link World} that the game takes place in.
	 * <p>
	 * This can be null before the game has started.
	 * <p>
	 * You can check if this has a value by using {@link Game#hasWorld()}
	 */
	protected World world;

	/**
	 * should we drop the players inventory if they combat log? Default value is:
	 * <code>false</code>
	 * 
	 * @since 2.0.0
	 */
	private boolean dropItemsOnCombatLog;

	/**
	 * A {@link Random} instance that can be used in the game
	 */
	protected Random random;

	private boolean beginEventCalled;

	private Plugin plugin;

	public Game(Plugin plugin) {
		this.players = new ArrayList<UUID>();
		this.triggers = new ArrayList<GameTrigger>();
		this.world = null;
		this.endCalled = false;
		this.startCalled = false;
		this.autoWinnerCheckCompleted = false;
		this.random = new Random();
		this.beginEventCalled = false;
		this.plugin = plugin;
		this.dropItemsOnCombatLog = false;
		this.winCheckTask = new SimpleTask(NovaCore.getInstance(), new Runnable() {
			@Override
			public void run() {
				if (!autoWinnerCheckCompleted) {
					if (autoEndGame()) {
						checkWinner();
					}
				}
			}
		}, 5L);
	}

	/**
	 * Check if a player is in the game
	 * 
	 * @param uuid The {@link UUID} of the player to check
	 * @return <code>true</code> if the player is in the list of active players
	 * @since 2.0.0
	 */
	public boolean isPlayerInGame(UUID uuid) {
		return this.players.contains(uuid);
	}

	/**
	 * Check if a player is in the game
	 * 
	 * @param player The {@link OfflinePlayer} of the player to check
	 * @return <code>true</code> if the player is in the list of active players
	 * @since 2.0.0
	 */
	public boolean isPlayerInGame(OfflinePlayer player) {
		return this.isPlayerInGame(player.getUniqueId());
	}

	/**
	 * Get the {@link Plugin} that owns this game
	 * 
	 * @return {@link Plugin}
	 */
	public Plugin getPlugin() {
		return plugin;
	}

	/**
	 * This will call the {@link GameBeginEvent}
	 * <p>
	 * This should be implemented when the game has started and any countdowns have
	 * finished
	 */
	protected void sendBeginEvent() {
		if (beginEventCalled) {
			Log.warn("Game", "Tried to call Game#sendBeginEvent() twice");
			return;
		}
		beginEventCalled = true;
		GameBeginEvent event = new GameBeginEvent(this);
		Bukkit.getServer().getPluginManager().callEvent(event);
	}

	/**
	 * Get the name of the game used by plugins
	 * 
	 * @return Name of the game
	 */
	public abstract String getName();

	/**
	 * Get the display name of the game.<br>
	 * If you need to get the display name to use in messages use
	 * {@link Game#getDisplayNameFromGameManager()} instead
	 * 
	 * @return Display name of the game
	 */
	public abstract String getDisplayName();

	/**
	 * Get the display name of the game using {@link GameManager#getDisplayName()}
	 * 
	 * @return Display name of the game for use in score boards or in the game
	 */
	public final String getDisplayNameFromGameManager() {
		return GameManager.getInstance().getDisplayName();
	}

	/**
	 * Get the {@link World} that the game takes place in.
	 * <p>
	 * This can be null before the game has started.
	 * <p>
	 * You can check if this has a value by using {@link Game#hasWorld()}
	 * 
	 * @return The {@link World} for the game or <code>null</code>
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Check if the world has been set
	 * 
	 * @return <code>true</code> if the world is not null
	 */
	public boolean hasWorld() {
		return world != null;
	}

	/**
	 * Called when {@link GameManager} loads the game.
	 * <p>
	 * This should not be called outside of {@link GameManager}
	 */
	public void load() {
		this.onLoad();
	}

	/**
	 * Called when {@link GameManager} unloads the game.
	 * <p>
	 * This should not be called outside of {@link GameManager}
	 */
	public void unload() {
		winCheckTask.stop();

		this.onUnload();
	}

	/**
	 * Called when the game is added to {@link GameManager}.
	 * <p>
	 * Called before registering events.
	 * <p>
	 * Should only be called using {@link Game#load()}
	 */
	public void onLoad() {
	}

	/**
	 * Called when the game is disabled by {@link GameManager}.
	 * <p>
	 * Called after events have been unregistered.
	 * <p>
	 * Should only be called using {@link Game#unload()}
	 */
	public void onUnload() {
	}

	/**
	 * Get a list of all players participating in the game
	 * 
	 * @return {@link List} with the {@link UUID} of all participants
	 */
	public final List<UUID> getPlayers() {
		return players;
	}

	/**
	 * Get a list with all game triggers
	 * 
	 * @return {@link List} with game triggers
	 */
	public final List<GameTrigger> getTriggers() {
		return triggers;
	}

	/**
	 * Try to add a {@link GameTrigger}
	 * <p>
	 * This will fail if the trigger has spaces in the name or if a trigger with
	 * that name already exists
	 * 
	 * @param trigger The {@link GameTrigger} to add
	 * @return <code>true</code> on success
	 */
	public boolean addTrigger(GameTrigger trigger) {
		if (!triggerExist(trigger)) {
			if (trigger.hasValidName()) {
				triggers.add(trigger);
				return true;
			}
		}

		return false;
	}

	/**
	 * Try to remove a {@link GameTrigger}
	 * <p>
	 * This will fail if a trigger with that name does not exists
	 * <p>
	 * If the trigger is of type {@link ScheduledGameTrigger} then
	 * {@link ScheduledGameTrigger#stop()} will be called on successful removal
	 * 
	 * @param trigger The {@link GameTrigger} to remove
	 * @return <code>true</code> on success
	 */
	public boolean removeTrigger(GameTrigger trigger) {
		for (int i = 0; i < triggers.size(); i++) {
			if (triggers.get(i).getName().equalsIgnoreCase(trigger.getName())) {
				triggers.remove(i);

				if (trigger instanceof ScheduledGameTrigger) {
					((ScheduledGameTrigger) trigger).stop();
				}

				return true;
			}
		}

		return false;
	}

	/**
	 * Get a list with triggers that has the provided flag
	 * 
	 * @param flag The flag to check for
	 * @return List with triggers
	 */
	public final List<GameTrigger> getTriggersByFlag(TriggerFlag flag) {
		return this.getTriggersByFlags(flag);
	}

	/**
	 * Get a list with triggers that contains 1 or more of the provided flags
	 * 
	 * @param flags The flags to check for
	 * @return List with triggers
	 */
	public final List<GameTrigger> getTriggersByFlags(TriggerFlag... flags) {
		List<GameTrigger> result = new ArrayList<GameTrigger>();

		triggers.forEach(trigger -> {
			for (TriggerFlag flag : flags) {
				if (trigger.hasFlag(flag)) {
					if (!result.contains(trigger)) {
						result.add(trigger);
					}
				}
			}
		});

		return result;
	}

	/**
	 * Check if a {@link GameTrigger} exists by name
	 * 
	 * @param name The name of the {@link GameTrigger}
	 * @return <code>true</code> if a trigger with that name exists
	 */
	public final boolean triggerExist(String name) {
		return this.getTrigger(name) != null;
	}

	/**
	 * Check if a {@link GameTrigger} has been added
	 * <p>
	 * Not that triggers are checked for by name and not their value
	 * 
	 * @param trigger The {@link GameTrigger} to check for
	 * @return <code>true</code> if that trigger has been added
	 */
	public final boolean triggerExist(GameTrigger trigger) {
		return this.triggerExist(trigger.getName());
	}

	/**
	 * Try to get a {@link GameTrigger} by its name
	 * 
	 * @param name The name of the {@link GameTrigger}
	 * @return The {@link GameTrigger} or <code>null</code> if not found
	 */
	public final GameTrigger getTrigger(String name) {
		return triggers.stream().filter(trigger -> trigger.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}

	/**
	 * Get the {@link PlayerQuitEliminationAction} to use when a player quits
	 * 
	 * @return {@link PlayerQuitEliminationAction} to use
	 */
	public abstract PlayerQuitEliminationAction getPlayerQuitEliminationAction();

	/**
	 * Get delay in seconds before eliminating player. The default value is 180
	 * seconds (3 minutes)
	 * <p>
	 * This only works if {@link PlayerQuitEliminationAction} is set to
	 * {@link PlayerQuitEliminationAction#DELAYED}
	 * 
	 * @return delay in seconds before eliminating player
	 */
	public int getPlayerEliminationDelay() {
		return 180;
	}

	/**
	 * Check if a player should be eliminated on death
	 * 
	 * @param player The {@link Player} that died
	 * 
	 * @return <code>true</code> to eliminate player
	 */
	public abstract boolean eliminatePlayerOnDeath(Player player);

	/**
	 * Check if PvP is enabled
	 * 
	 * @return <code>true</code> if players can hit each other
	 */
	public abstract boolean isPVPEnabled();

	/**
	 * Set to true to make the game auto end and call
	 * {@link Game#onPlayerWin(OfflinePlayer)} when there is only one player left or
	 * {@link Game#onTeamWin(Team)} when teams are enabled and there is only one
	 * team left.
	 * <p>
	 * If this is disabled you will have to provide your own code to call
	 * {@link Game#onPlayerWin(OfflinePlayer)}, {@link Game#onTeamWin(Team)} and
	 * fire the events in your game code
	 * 
	 * @return <code>true</code> to enable pvp
	 */
	public abstract boolean autoEndGame();

	/**
	 * By default the
	 * {@link Game#onPlayerEliminated(OfflinePlayer, Entity, PlayerEliminationReason, int)}
	 * and the {@link PlayerEliminatedEvent} and the respective team functions and
	 * events wont be called after the game has been ended by
	 * {@link Game#checkWinner()} but by changing this to <code>true</code> the
	 * events will still be called after the game is auto ended by
	 * {@link Game#checkWinner()}.
	 * <p>
	 * This might have a bad effect on other plugins that register score and might
	 * also cause players and teams to be eliminated as first place.
	 * <p>
	 * If you enable this you should also make sure that your plugin can handle a
	 * {@link PlayerEliminatedEvent} or {@link TeamEliminatedEvent} with a placement
	 * of 1 and that your elimination messages does not display a elimination
	 * message with a placement of 1 unless that is what you want.
	 * <p>
	 * This is disabled by default
	 * 
	 * @return <code>true</code> to auto end the game
	 */
	public boolean eliminateAfterAutoWin() {
		return false;
	}

	/**
	 * Set if the players inventory should be dropped if they combat log. This is
	 * disabled by default
	 * 
	 * @param dropItemsOnCombatLog <code>true</code> to enable
	 * @since 2.0.0
	 */
	public void setDropItemsOnCombatLog(boolean dropItemsOnCombatLog) {
		this.dropItemsOnCombatLog = dropItemsOnCombatLog;
	}

	/**
	 * Check if we should drop the players inventory when they combat log. This is
	 * disabled by default
	 * 
	 * @return <code>true</code> if enabled
	 */
	public boolean isDropItemsOnCombatLog() {
		return dropItemsOnCombatLog;
	}

	/**
	 * Deprecated. use {@link GameManager#setUseCombatTagging(boolean)} to enable
	 * this feature
	 * <p>
	 * Set to true to eliminate players for combat logging
	 * 
	 * @return <code>true</code> if players will be eliminated for combat logging
	 */
	@Deprecated
	public boolean eliminateIfCombatLogging() {
		return false;
	}

	/**
	 * Deprecated. Use {@link GameManager#setCombatTaggingTime(int)} to change the
	 * time
	 * <p>
	 * Get the delay in second the player is combat tagged for if hurt by another
	 * player
	 * 
	 * @return delay in seconds for combat tag to expire
	 */
	@Deprecated
	public int getCombatTagDelay() {
		return 5;
	}

	/**
	 * Check if the game can be started
	 * <p>
	 * This can be used to create games that need to generate a world before
	 * starting to prevent the game from starting before the world is ready
	 * 
	 * @return <code>true</code> if the game can start
	 */
	public boolean canStart() {
		return true;
	}

	/**
	 * Check if the game is running
	 * 
	 * @return <code>true</code> if {@link Game#hasStarted()} is <code>true</code>
	 *         and {@link Game#hasEnded()} is <code>false</code>
	 */
	public boolean isRunning() {
		return this.hasStarted() && !this.hasEnded();
	}

	/**
	 * Check if the game has started
	 * 
	 * @return <code>true</code> if the game has started
	 */
	public abstract boolean hasStarted();

	/**
	 * Check if the game has ended
	 * 
	 * @return <code>true</code> if the game has ended
	 */
	public abstract boolean hasEnded();

	/**
	 * Check friendly fire is enabled. This wont do anything unless
	 * {@link GameManager#setUseTeams(boolean)} is enabled and
	 * {@link NovaCore#setTeamManager(net.zeeraa.novacore.spigot.teams.TeamManager)}
	 * is set
	 * 
	 * @return <code>true</code> if friendly fire is enabled
	 */
	public abstract boolean isFriendlyFireAllowed();

	/**
	 * Check if 2 entities can hurt each other
	 * 
	 * @param attacker The attacking {@link LivingEntity}, If the entity is a
	 *                 projectile the entity that fired the projectile will be the
	 *                 attacker
	 * @param target   The target {@link LivingEntity}
	 * @return <code>true</code> if the attack is allowed
	 */
	public abstract boolean canAttack(LivingEntity attacker, LivingEntity target);

	/**
	 * Start the game
	 * 
	 * @return <code>false</code> if this has already been called
	 */
	public boolean startGame() {
		if (startCalled) {
			return false;
		}
		startCalled = true;

		if (this.autoEndGame()) {
			winCheckTask.start();
		}

		Bukkit.getServer().getPluginManager().callEvent(new GameStartEvent(this));
		this.onStart();

		return true;
	}

	/**
	 * Called when the game starts
	 */
	public abstract void onStart();

	/**
	 * End the game.
	 * <p>
	 * This should also send all players to the lobby and reset the server
	 * 
	 * @param reason The {@link GameEndReason} why the game ended
	 * @return <code>false</code> if this has already been called
	 */
	public boolean endGame(GameEndReason reason) {
		if (endCalled) {
			return false;
		}
		endCalled = true;

		if (!beginEventCalled) {
			Log.warn("Game", "Game#sendBeginEvent() was never called before Game#endGame() was called. If the game progressed as intended this might mean that you forgot to call Game#sendBeginEvent() in your game code");
		}

		winCheckTask.stop();

		Bukkit.getServer().getPluginManager().callEvent(new GameEndEvent(this, reason));
		this.onEnd(reason);

		return true;
	}

	/**
	 * Called when the game ends
	 * 
	 * @param reason The {@link GameEndReason} why the game ended
	 */
	public abstract void onEnd(GameEndReason reason);

	/**
	 * Eliminate a player from the game
	 * 
	 * @param player The player to eliminate
	 * @param killer killer The entity that killed the player. this can be null
	 * @param reason {@link PlayerEliminationReason} for why the player was
	 *               eliminated
	 * 
	 * @return <code>true</code> if player was eliminated. <code>false</code> if the
	 *         player was not in game or if canceled. This might also return
	 *         <code>false</code> if the game was ended by
	 *         {@link Game#checkWinner()} and {@link Game#eliminateAfterAutoWin()}
	 *         is not enabled
	 */
	public boolean eliminatePlayer(OfflinePlayer player, Entity killer, PlayerEliminationReason reason) {
		return this.eliminatePlayer(player, killer, reason, false);
	}

	/**
	 * Eliminate a player from the game
	 * 
	 * @param player The player to eliminate
	 * @param killer killer The entity that killed the player. this can be null
	 * @param reason {@link PlayerEliminationReason} for why the player was
	 *               eliminated
	 * @param silent Set to true to indicate that there should be no elimination
	 *               message
	 * 
	 * @return <code>true</code> if player was eliminated. <code>false</code> if the
	 *         player was not in game or if canceled. This might also return
	 *         <code>false</code> if the game was ended by
	 *         {@link Game#checkWinner()} and {@link Game#eliminateAfterAutoWin()}
	 *         is not enabled
	 */
	public boolean eliminatePlayer(OfflinePlayer player, Entity killer, PlayerEliminationReason reason, boolean silent) {
		if (!players.contains(player.getUniqueId())) {
			Log.trace("Canceling elimination because the player is not in game");
			return false;
		}

		if (autoWinnerCheckCompleted) {
			if (!eliminateAfterAutoWin()) {
				Log.trace("Canceling elimination because the game has ended");
				return false;
			}
		}

		int placement = players.size() - 1;

		PlayerEliminatedEvent playerEliminatedEvent = new PlayerEliminatedEvent(player, killer, reason, placement, silent, this);

		Bukkit.getServer().getPluginManager().callEvent(playerEliminatedEvent);

		if (playerEliminatedEvent.isCancelled()) {
			Log.trace("Canceling elimination because the elimination event was canceled");
			return false;
		}

		players.remove(player.getUniqueId());

		this.onPlayerEliminated(player, killer, reason, placement);
		if (!silent) {
			this.getGameManager().getPlayerEliminationMessage().showPlayerEliminatedMessage(player, killer, reason, placement);
		}

		if (NovaCore.getInstance().getTeamManager() != null) {
			if (this.getGameManager().isUseTeams()) {
				boolean teamEliminated = true;
				List<UUID> teamsLeft = new ArrayList<UUID>();

				Team team = NovaCore.getInstance().getTeamManager().getPlayerTeam(player);
				if (team != null) {
					for (UUID uuid : players) {
						Team team2 = NovaCore.getInstance().getTeamManager().getPlayerTeam(uuid);
						if (team2 != null) {
							if (team.getTeamUuid() == team2.getTeamUuid()) {
								teamEliminated = false;
							}

							if (!teamsLeft.contains(team2.getTeamUuid())) {
								teamsLeft.add(team2.getTeamUuid());
							}
						}
					}

					if (teamEliminated) {
						int teamPlacement = teamsLeft.size() + 1;
						TeamEliminatedEvent event = new TeamEliminatedEvent(team, teamPlacement, this);

						Bukkit.getServer().getPluginManager().callEvent(event);

						onTeamEliminated(team, teamPlacement);

						Log.debug("A team was eliminated. Teams left: " + teamsLeft.size());
						Log.trace("Team placement: " + teamPlacement);

						if (this.getGameManager().getTeamEliminationMessage() != null) {
							this.getGameManager().getTeamEliminationMessage().showTeamEliminatedMessage(team, teamPlacement);
						}
					}
				}
			}
		}

		if (autoEndGame()) {
			checkWinner();
		}

		return true;
	}

	/**
	 * Check for winner and if a winner is found call
	 * {@link Game#onPlayerWin(OfflinePlayer)} or {@link Game#onTeamWin(Team)}
	 * depending on if {@link GameManager#isUseTeams()} is enabled or not
	 */
	protected void checkWinner() {
		if (autoWinnerCheckCompleted) {
			return;
		}

		boolean hasWinner = false;

		if (NovaCore.getInstance().hasTeamManager() && getGameManager().isUseTeams()) {
			List<UUID> teamsLeft = new ArrayList<UUID>();

			players.forEach(uuid -> {
				Team team = NovaCore.getInstance().getTeamManager().getPlayerTeam(uuid);
				if (team != null) {
					if (!teamsLeft.contains(team.getTeamUuid())) {
						teamsLeft.add(team.getTeamUuid());
					}
				}
			});

			if (teamsLeft.size() == 1) {
				Team team = NovaCore.getInstance().getTeamManager().getTeamByTeamUUID(teamsLeft.get(0));
				if (team != null) {
					hasWinner = true;
					onTeamWin(team);
					Bukkit.getServer().getPluginManager().callEvent(new TeamWinEvent(team));
				}
			}

			if (teamsLeft.size() <= 1) {
				getGameManager().endGame(hasWinner ? GameEndReason.WIN : GameEndReason.DRAW);
				autoWinnerCheckCompleted = true;
			}
		} else {
			if (players.size() == 1) {
				OfflinePlayer player = Bukkit.getOfflinePlayer(players.get(0));

				if (player != null) {
					hasWinner = true;
					onPlayerWin(player);
					Bukkit.getServer().getPluginManager().callEvent(new PlayerWinEvent(player));
				} else {
					Log.trace("Game#checkWinner()", "OfflinePlayer was null");
				}
			}

			if (players.size() <= 1) {
				Log.debug("Game#checkWinner()", "Player size is less than or equal to 1. Ending the game");
				getGameManager().endGame(hasWinner ? GameEndReason.WIN : GameEndReason.DRAW);
				autoWinnerCheckCompleted = true;
			}
		}
	}

	/**
	 * Called when a player wins the game.
	 * <p>
	 * This gets called automatically if teams are disabled,
	 * {@link Game#autoEndGame()} is enabled and there is only one player left.
	 * <p>
	 * If called by {@link Game#checkWinner()} this will be called before the
	 * {@link PlayerWinEvent}
	 * 
	 * @param player The {@link OfflinePlayer} that won
	 */
	public void onPlayerWin(OfflinePlayer player) {
	}

	/**
	 * Called when a team wins the game.
	 * <p>
	 * This gets called automatically if teams are enabled,
	 * {@link Game#autoEndGame()} is enabled and there is only one team left.
	 * <p>
	 * If called by {@link Game#checkWinner()} this will be called before the
	 * {@link TeamWinEvent}
	 * 
	 * @param team The {@link Team} that won
	 */
	public void onTeamWin(Team team) {
	}

	/**
	 * Add a player to the game
	 * 
	 * @param player The player to be added
	 * @return <code>true</code> if the player was added
	 */
	public boolean addPlayer(Player player) {
		if (players.contains(player.getUniqueId())) {
			return false;
		}

		Log.debug("Adding player " + player.getName() + " the the game");

		players.add(player.getUniqueId());

		return true;
	}

	/**
	 * Called when a player is eliminated.
	 * <p>
	 * This is called after {@link PlayerEliminatedEvent} and wont be called if the
	 * event is canceled.
	 * <p>
	 * The player is removed from the player list before this is called
	 * 
	 * @param player    The player that was eliminated
	 * @param killer    The entity that killed the player. this will always be
	 *                  <code>null</code> unless the {@link PlayerEliminationReason}
	 *                  is {@link PlayerEliminationReason#KILLED}
	 * @param reason    The {@link PlayerEliminationReason}
	 * @param placement The placement of the player
	 */
	public void onPlayerEliminated(OfflinePlayer player, Entity killer, PlayerEliminationReason reason, int placement) {
	}

	/**
	 * Called when a team is eliminated.
	 * <p>
	 * This is called after {@link TeamEliminatedEvent}
	 * 
	 * @param team      The {@link Team} that was eliminated
	 * @param placement The placement of the team
	 */
	public void onTeamEliminated(Team team, int placement) {
	}

	/**
	 * Called when a player respawns
	 * 
	 * @param player The player that respawned
	 */
	public void onPlayerRespawn(Player player) {
	}

	/**
	 * Set a player to spectator mode. The default version of this just sets the
	 * player to spectator, clears potion effects and teleports them if the game is
	 * a map game
	 * 
	 * @param player the {@link Player} to set to spectator mode
	 */
	public void tpToSpectator(Player player) {
		PlayerUtils.clearPlayerInventory(player);
		player.setGameMode(GameMode.SPECTATOR);
		if (this instanceof MapGame) {
			MapGame game = (MapGame) this;
			if (game.hasActiveMap()) {
				player.teleport(game.getActiveMap().getSpectatorLocation(), TeleportCause.PLUGIN);
			}
		}
	}

	/**
	 * Get instance of {@link GameManager}
	 * 
	 * @return {@link GameManager} instance
	 */
	public GameManager getGameManager() {
		return GameManager.getInstance();
	}

	/**
	 * Get the random instance to the game is using
	 * 
	 * @return The {@link Random} instance the game is using
	 */
	public Random getRandom() {
		return random;
	}

	/**
	 * Set the {@link Random} instance to use for the game
	 * 
	 * @param random The {@link Random} instance to use
	 */
	public void setRandom(Random random) {
		this.random = random;
	}
}