package net.zeeraa.novacore.spigot.module.modules.scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.annotation.Nullable;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Team;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.module.MissingPluginDependencyException;
import net.zeeraa.novacore.spigot.module.NovaModule;
import net.zeeraa.novacore.spigot.module.modules.scoreboard.event.PlayerNetherboardScoreboardInitEvent;

/**
 * This module creates a scoreboard for all players using the Netherboard
 * plugin. This can also be used to change the name color of players
 * <p>
 * The Netherboard plugin is required for this module to work
 * 
 * @author Zeeraa
 */
public class NetherBoardScoreboard extends NovaModule implements Listener, wOmeN {
	private static NetherBoardScoreboard instance;

	private int lineCount;

	private String defaultTitle;

	private HashMap<UUID, BPlayerBoard> boards;

	private HashMap<Integer, String> globalLines;
	private HashMap<UUID, HashMap<Integer, String>> playerLines;

	private HashMap<UUID, ChatColor> playerNameColor;

	private int taskId;

	public static NetherBoardScoreboard getInstance() {
		return instance;
	}

	public NetherBoardScoreboard() {
		super("NovaCore.NetherBoardScoreboard");
	}

	@Override
	public void onLoad() {
		NetherBoardScoreboard.instance = this;
		this.lineCount = 15;
		this.defaultTitle = "";
		this.boards = new HashMap<UUID, BPlayerBoard>();
		this.globalLines = new HashMap<Integer, String>();
		this.playerLines = new HashMap<UUID, HashMap<Integer, String>>();
		this.playerNameColor = new HashMap<UUID, ChatColor>();
		this.taskId = -1;
	}

	@Override
	public void onEnable() throws Exception {
		if (Bukkit.getServer().getPluginManager().getPlugin("Netherboard") == null) {
			throw new MissingPluginDependencyException("Netherboard");
		}

		for (int i = 0; i < lineCount; i++) {
			globalLines.put(i, "");
		}

		Bukkit.getServer().getOnlinePlayers().forEach(player -> createPlayerScoreboard(player));

		if (taskId == -1) {
			taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(NovaCore.getInstance(), new Runnable() {
				@Override
				public void run() {
					boards.keySet().forEach(uuid -> {
						Player player = Bukkit.getServer().getPlayer(uuid);

						if (player != null) {
							update(player);
						}
					});
				}
			}, 5L, 5L);
		}
	}

	@Override
	public void onDisable() {
		while (boards.size() > 0) {
			UUID uuid = boards.keySet().iterator().next();

			BPlayerBoard board = boards.get(uuid);
			board.delete();

			boards.remove(uuid);
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		createPlayerScoreboard(p);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();

		removeScoreboard(p);
	}

	private boolean createPlayerScoreboard(Player player) {
		if (!boards.containsKey(player.getUniqueId())) {
			PlayerNetherboardScoreboardInitEvent event = new PlayerNetherboardScoreboardInitEvent(player, defaultTitle);

			Bukkit.getServer().getPluginManager().callEvent(event);

			BPlayerBoard board = Netherboard.instance().createBoard(player, event.getTitle());

			HashMap<Integer, String> pLines = new HashMap<Integer, String>();

			for (int i = 0; i < lineCount; i++) {
				pLines.put(i, "");
				board.set("", i);
			}

			boards.put(player.getUniqueId(), board);
			playerLines.put(player.getUniqueId(), pLines);

			for (ChatColor chatColor : ChatColor.values()) {
				if (board.getScoreboard().getTeam("C_" + chatColor.name()) == null) {
					Team team = board.getScoreboard().registerNewTeam("C_" + chatColor.name());

					team.setPrefix(chatColor + "");
					team.setNameTagVisibility(NameTagVisibility.ALWAYS);
				}
			}

			playerNameColor.keySet().forEach(uuid -> {
				Team team = board.getScoreboard().getTeam("C_" + playerNameColor.get(uuid).name());
				if (team != null) {
					OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(uuid);
					if (p != null) {
						if (p.getName() != null) {
							team.addEntry(p.getName());
						} else {
							Log.debug("Failed to get name of offline player " + p.getUniqueId());
						}
					}
				} else {
					Log.warn("Missing team color: C_" + playerNameColor.get(uuid).name());
				}
			});

			update(player);

			return true;
		}
		return false;
	}

	private boolean removeScoreboard(Player player) {
		if (boards.containsKey(player.getUniqueId())) {
			boards.get(player.getUniqueId()).delete();
			boards.remove(player.getUniqueId());
			return true;
		}

		return false;
	}

	/**
	 * Force an update of the scoreboard for a player
	 * 
	 * @param player The player to force an update for
	 */
	public void update(Player player) {
		if (boards.containsKey(player.getUniqueId())) {
			BPlayerBoard board = boards.get(player.getUniqueId());

			HashMap<Integer, String> pLines = playerLines.get(player.getUniqueId());

			ArrayList<String> existingLines = new ArrayList<String>();

			int lineNumber = lineCount;
			for (int i = 0; i < lineCount; i++) {
				String line = "";

				if (pLines.containsKey(i)) {
					String content = pLines.get(i);
					if (content.length() > 0) {
						line = content;
					}
				}

				if (line.length() == 0) {
					if (globalLines.containsKey(i)) {
						line = globalLines.get(i);
					}
				}

				if (line.length() == 0) {
					line = "";
				}

				while (existingLines.contains(line)) {
					line += ChatColor.RESET;
				}

				existingLines.add(line);

				board.set(line, lineNumber);

				lineNumber--;
			}
		}
	}

	/**
	 * Get a {@link HashMap} with the {@link BPlayerBoard} for all players that has
	 * a scoreboard
	 * 
	 * @return {@link HashMap} with {@link BPlayerBoard}
	 */
	public HashMap<UUID, BPlayerBoard> getBoards() {
		return boards;
	}

	/**
	 * Get the line count of the scoreboards
	 * 
	 * @return line count
	 */
	public int getLineCount() {
		return lineCount;
	}

	/**
	 * Set the line count for the scoreboards
	 * 
	 * @param lineCount The amount of lines to display
	 */
	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}

	/**
	 * Get the default title for scoreboards
	 * 
	 * @return Default scoreboard title
	 */
	public String getDefaultTitle() {
		return defaultTitle;
	}

	/**
	 * Set the default title for scoreboards
	 * 
	 * @param defaultTitle The new default title for scoreboards
	 */
	public void setDefaultTitle(String defaultTitle) {
		this.defaultTitle = defaultTitle;
	}

	/**
	 * Set a line to display for all players
	 * <p>
	 * If a player line (see
	 * {@link NetherBoardScoreboard#setPlayerLine(int, Player, String)}) exists with
	 * the same line number the player line will be used instead
	 * 
	 * @param line    The line number. Note that this starts from 0
	 * @param content The text to set
	 */
	public void setGlobalLine(int line, String content) {
		globalLines.put(line, content);
	}

	/**
	 * Get the content of a global line
	 * 
	 * @param line The line to get
	 * @return The content of that line
	 */
	public String getGlobalLine(int line) {
		return globalLines.get(line);
	}

	/**
	 * Remove a global line from the scoreboard
	 * 
	 * @param line The line number. Note that this starts from 0
	 */
	public void clearGlobalLine(int line) {
		this.setGlobalLine(line, " ");
	}

	/**
	 * Set a line to display for a single player
	 * <p>
	 * This will override global lines with the same line number
	 * 
	 * @param line    The line number. Note that this starts from 0
	 * @param player  The player to display the line to
	 * @param content The text to set
	 */
	public void setPlayerLine(int line, Player player, String content) {
		if (playerLines.containsKey(player.getUniqueId())) {
			playerLines.get(player.getUniqueId()).put(line, content);
		}
	}

	/**
	 * Remove a player line from the scoreboard
	 * 
	 * @param line   The line number. Note that this starts from 0
	 * @param player The player to remove the line from
	 */
	public void clearPlayerLine(int line, Player player) {
		this.setPlayerLine(line, player, " ");
	}

	/**
	 * Get the line for a specified player.
	 * <p>
	 * This will return <code>null</code> if global lines are used instead
	 * 
	 * @param line   The line number to get
	 * @param player The player to get
	 * @return The content of that line or <code>null</code> if not found
	 */
	@Nullable
	public String getPlayerLine(int line, Player player) {
		if (playerLines.containsKey(player.getUniqueId())) {
			return playerLines.get(player.getUniqueId()).get(line);
		}

		return null;
	}

	/**
	 * Get a {@link HashMap} with all global lines
	 * 
	 * @return{@link HashMap} with global lines
	 */
	public HashMap<Integer, String> getGlobalLines() {
		return globalLines;
	}

	/**
	 * Get a {@link HashMap} with all player lines
	 * 
	 * @return {@link HashMap} with player lines
	 */
	public HashMap<UUID, HashMap<Integer, String>> getPlayerLines() {
		return playerLines;
	}

	/**
	 * Reset the name color of a player
	 * <p>
	 * Name colors use scoreboard teams to chance the name color above the players
	 * head
	 * 
	 * @param player _The player to reset the name color for
	 */
	public void resetPlayerNameColor(OfflinePlayer player) {
		setPlayerNameColor(player, null);
	}

	/**
	 * Set the name color of a player using the bungeecord version of chat color.
	 * Warning: using this with non enum value chat colors will probably cause this
	 * to crash
	 * <p>
	 * Name colors use scoreboard teams to chance the name color above the players
	 * head
	 * 
	 * @param player   The player to set the color of
	 * @param newColor The {@link net.md_5.bungee.api.ChatColor} to use
	 * @since 2.0.0
	 */
	public void setPlayerNameColorBungee(OfflinePlayer player, net.md_5.bungee.api.ChatColor newColor) {
		ChatColor realColor = ChatColor.valueOf(newColor.name());
		this.setPlayerNameColor(player, realColor);
	}

	/**
	 * Set the name color of a player
	 * <p>
	 * Name colors use scoreboard teams to chance the name color above the players
	 * head
	 * 
	 * @param player   The player to set the color of
	 * @param newColor The {@link ChatColor} to use
	 */
	public void setPlayerNameColor(OfflinePlayer player, ChatColor newColor) {
		ChatColor oldColor = null;
		if (playerNameColor.containsKey(player.getUniqueId())) {
			oldColor = playerNameColor.get(player.getUniqueId());
		}

		if (oldColor != null) {
			for (UUID uuid : boards.keySet()) {
				BPlayerBoard board = boards.get(uuid);

				Team team = board.getScoreboard().getTeam("C_" + oldColor.name());

				if (team != null) {
					if (player.getName() != null) {
						if (team.getEntries().contains(player.getName())) {
							team.removeEntry(player.getName());
						}
					}
				}
			}
		}

		if (newColor != null) {
			playerNameColor.put(player.getUniqueId(), newColor);
			boards.keySet().forEach(uuid -> {
				BPlayerBoard board = boards.get(uuid);

				Team team = board.getScoreboard().getTeam("C_" + newColor.name());

				if (team != null) {
					if (player.getName() != null) {
						if (!team.getEntries().contains(player.getName())) {
							team.addEntry(player.getName());
						}
					}
				}
			});
		}
	}
}