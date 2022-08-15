package net.zeeraa.novacore.spigot.gameengine.command.commands.game.debug;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.GameManager;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.MapGame;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;

public class GameDebugCommand extends NovaSubCommand implements wOmeN {
	public GameDebugCommand() {
		super("debug");
		setAllowedSenders(AllowedSenders.ALL);
		setDescription("Show debug info");
		setPermission("novacore.command.game.debug");
		setPermissionDescription("Debug command for games");
		setPermissionDefaultValue(PermissionDefault.OP);

		setEmptyTabMode(true);
		addHelpSubCommand();
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.AQUA + "-=-=-= BEGIN DEBUG =-=-=-");

		sender.sendMessage(ChatColor.GOLD + "Server version: " + ChatColor.AQUA + Bukkit.getServer().getVersion());

		if (GameManager.getInstance().isEnabled()) {
			sender.sendMessage(ChatColor.GREEN + "GameManager is enabled");

			if (GameManager.getInstance().hasGame()) {
				sender.sendMessage(ChatColor.GOLD + "Loaded game name: " + ChatColor.AQUA + GameManager.getInstance().getActiveGame().getName());
				sender.sendMessage(ChatColor.GOLD + "Display name: " + ChatColor.AQUA + GameManager.getInstance().getActiveGame().getDisplayName());
				sender.sendMessage(ChatColor.GOLD + "Class: " + ChatColor.AQUA + GameManager.getInstance().getActiveGame().getClass().getName());
				boolean started = GameManager.getInstance().getActiveGame().hasStarted();
				boolean ended = GameManager.getInstance().getActiveGame().hasEnded();
				sender.sendMessage(ChatColor.GOLD + "Started: " + (started ? ChatColor.GREEN : ChatColor.RED) + "" + started + ChatColor.GOLD + " Ended: " + (ended ? ChatColor.GREEN : ChatColor.RED) + "" + ended + ChatColor.GOLD);

				sender.sendMessage(ChatColor.GOLD + "Player count: " + ChatColor.AQUA + GameManager.getInstance().getActiveGame().getPlayers().size());

				sender.sendMessage(ChatColor.GOLD + "Auto end: " + ChatColor.AQUA + GameManager.getInstance().getActiveGame().autoEndGame());
				sender.sendMessage(ChatColor.GOLD + "Can start: " + ChatColor.AQUA + GameManager.getInstance().getActiveGame().canStart());

				if (GameManager.getInstance().getActiveGame() instanceof MapGame) {
					MapGame mapGame = (MapGame) GameManager.getInstance().getActiveGame();

					sender.sendMessage(ChatColor.GOLD + "-= MAP DATA =-");

					sender.sendMessage(ChatColor.GOLD + "Has active map: " + ChatColor.AQUA + (mapGame.hasActiveMap() ? (ChatColor.GREEN + "yes") : (ChatColor.RED + "no")));

					if (mapGame.hasActiveMap()) {
						sender.sendMessage(ChatColor.GOLD + "Map name: " + ChatColor.AQUA + mapGame.getActiveMap().getAbstractMapData().getMapName());
						sender.sendMessage(ChatColor.GOLD + "Display name: " + ChatColor.AQUA + mapGame.getActiveMap().getAbstractMapData().getDisplayName());

						String modules = "";

						for (MapModule module : mapGame.getActiveMap().getMapData().getMapModules()) {
							modules += module.getName() + " ";
						}

						sender.sendMessage(ChatColor.GOLD + "Modules (" + mapGame.getActiveMap().getMapData().getMapModules().size() + "): " + modules);
					}
				}

			} else {
				sender.sendMessage(ChatColor.RED + "No game loaded");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "GameManager is disabled");
		}

		sender.sendMessage(ChatColor.AQUA + "-=-=-= END DEBUG =-=-=-");

		return true;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}