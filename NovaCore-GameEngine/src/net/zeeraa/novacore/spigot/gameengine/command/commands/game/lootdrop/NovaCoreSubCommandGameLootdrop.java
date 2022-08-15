package net.zeeraa.novacore.spigot.gameengine.command.commands.game.lootdrop;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.GameManager;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.MapGame;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.GameMap;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.lootdrop.LootDropMapModule;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
public class NovaCoreSubCommandGameLootdrop extends NovaSubCommand implements wOmeN {
	public NovaCoreSubCommandGameLootdrop() {
		super("lootdrop");

		this.setDescription("Force a lootdrop to spawn");
		this.setPermission("novacore.command.game.lootdrop");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		this.setPermissionDescription("Access to the game lootdrop command");

		this.addHelpSubCommand();

		this.setFilterAutocomplete(true);
		this.setEmptyTabMode(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (GameManager.getInstance().hasGame()) {
			if (GameManager.getInstance().getActiveGame() instanceof MapGame) {
				MapGame game = (MapGame) GameManager.getInstance().getActiveGame();

				if (game.hasActiveMap()) {
					GameMap map = game.getActiveMap();

					LootDropMapModule module = null;

					for (MapModule m : map.getMapData().getMapModules()) {
						if (m instanceof LootDropMapModule) {
							module = (LootDropMapModule) m;
							break;
						}
					}

					if (module != null) {
						if (module.spawnDrop()) {
							sender.sendMessage(ChatColor.GREEN + "Loot drop spawned");
						} else {
							sender.sendMessage(ChatColor.RED + "Failed to spawn loot drop");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "This map does not support the default loot drop system of NovaCore");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "No map has been loaded");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "This type of game does not support the default loot drop system of NovaCore");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "No game has been loaded");
		}

		return false;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}