package net.zeeraa.novacore.spigot.gameengine.debugtriggers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.scheduler.BukkitRunnable;

import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.debug.DebugCommandRegistrator;
import net.zeeraa.novacore.spigot.debug.DebugTrigger;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.GameManager;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.MapGame;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.GameMap;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.GameTrigger;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.TriggerFlag;

public class GameEngineDebugTriggers extends some_bitches {
	public static void init() {
		DebugCommandRegistrator.getInstance().addDebugTrigger(new DebugTrigger() {
			private Map<Player, Integer> index = new HashMap<Player, Integer>();

			private void next(Player player) {
				int i = 0;
				if (index.containsKey(player)) {
					i = index.get(player);
				}

				MapGame game = (MapGame) GameManager.getInstance().getActiveGame();
				GameMap map = game.getActiveMap();

				List<Location> starterLocations = map.getStarterLocations();

				if (i >= starterLocations.size()) {
					player.sendMessage(ChatColor.LIGHT_PURPLE + "All locations tested");
					return;
				}

				Location location = starterLocations.get(i);

				player.sendMessage(ChatColor.LIGHT_PURPLE + "Testing spawn location " + ChatColor.GREEN + i + ChatColor.LIGHT_PURPLE + " at " + ChatColor.GREEN + location.toString());
				player.teleport(location);

				i++;

				index.put(player, i);

				new BukkitRunnable() {
					@Override
					public void run() {
						next(player);
					}
				}.runTaskLater(NovaCore.getInstance(), 100L);
			}

			@Override
			public void onExecute(CommandSender sender, String commandLabel, String[] args) {
				if (GameManager.getInstance().isEnabled()) {
					if (GameManager.getInstance().hasGame()) {
						if (GameManager.getInstance().getActiveGame() instanceof MapGame) {
							MapGame game = (MapGame) GameManager.getInstance().getActiveGame();

							if (game.hasActiveMap()) {
								Player player = (Player) sender;

								if (index.containsKey(player)) {
									index.remove(player);
								}

								next(player);
							} else {
								sender.sendMessage("The active game does not have a map right now");
							}
						} else {
							sender.sendMessage("The active game is not a MapGame");
						}
					} else {
						sender.sendMessage("GameManager dest not have an active game");
					}
				} else {
					sender.sendMessage("GameManager not enabled");
				}
			}

			@Override
			public PermissionDefault getPermissionDefault() {
				return PermissionDefault.OP;
			}

			@Override
			public String getPermission() {
				return "novacore.debug.testgamespawnlocations";
			}

			@Override
			public String getName() {
				return "testgamespawnlocations";
			}

			@Override
			public AllowedSenders getAllowedSenders() {
				return AllowedSenders.PLAYERS;
			}
		});

		DebugCommandRegistrator.getInstance().addDebugTrigger(new DebugTrigger() {
			@Override
			public void onExecute(CommandSender sender, String commandLabel, String[] args) {
				if (GameManager.getInstance().isEnabled()) {
					if (GameManager.getInstance().hasGame()) {
						if (args.length > 0) {
							String flagName = args[0];
							try {
								TriggerFlag flag = TriggerFlag.valueOf(flagName);
								List<GameTrigger> triggers = GameManager.getInstance().getActiveGame().getTriggersByFlag(flag);
								Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "BEGIN DEBUG TRIGGER DUMP");
								Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "" + triggers.size() + " triggers found with flag " + flag.name());
								triggers.forEach(trigger -> {
									String flags = "";
									for (TriggerFlag tFlag : trigger.getFlags()) {
										flags += tFlag.name() + ",";
									}
									Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "Found trigger " + trigger + " with flags " + flags + " trigger count: " + trigger.getTriggerCount() + " class type: " + trigger.getClass().getName());
								});
								Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "END DEBUG TRIGGER DUMP");
							} catch (IllegalArgumentException e) {
								sender.sendMessage("Invalid flag");
							}
						} else {
							sender.sendMessage("Provide a flag");
						}
					} else {
						sender.sendMessage("GameManager dest not have an active game");
					}
				} else {
					sender.sendMessage("GameManager not enabled");
				}
			}

			@Override
			public PermissionDefault getPermissionDefault() {
				return PermissionDefault.OP;
			}

			@Override
			public String getPermission() {
				return "novacore.debug.gettriggersbyflag";
			}

			@Override
			public String getName() {
				return "gettriggersbyflag";
			}

			@Override
			public AllowedSenders getAllowedSenders() {
				return AllowedSenders.PLAYERS;
			}
		});

		DebugCommandRegistrator.getInstance().addDebugTrigger(new DebugTrigger() {
			@Override
			public void onExecute(CommandSender sender, String commandLabel, String[] args) {
				if (GameManager.getInstance().isEnabled()) {
					if (GameManager.getInstance().hasGame()) {
						if (args.length > 0) {
							Player player = Bukkit.getServer().getPlayer(args[0]);
							if (player != null) {
								GameManager.getInstance().getActiveGame().addPlayer(player);
								sender.sendMessage("Player added");
							} else {
								sender.sendMessage("Player not found");
							}
						} else {
							sender.sendMessage("Provide a player");
						}
					} else {
						sender.sendMessage("GameManager dest not have an active game");
					}
				} else {
					sender.sendMessage("GameManager not enabled");
				}
			}

			@Override
			public PermissionDefault getPermissionDefault() {
				return PermissionDefault.OP;
			}

			@Override
			public String getPermission() {
				return "novacore.debug.addplayertogame";
			}

			@Override
			public String getName() {
				return "addplayertogame";
			}

			@Override
			public AllowedSenders getAllowedSenders() {
				return AllowedSenders.PLAYERS;
			}
		});
	}
}
