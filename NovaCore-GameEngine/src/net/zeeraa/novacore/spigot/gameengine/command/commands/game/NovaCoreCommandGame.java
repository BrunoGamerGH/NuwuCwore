package net.zeeraa.novacore.spigot.gameengine.command.commands.game;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.command.NovaCommand;
import net.zeeraa.novacore.spigot.gameengine.command.commands.game.addplayer.NovaCoreSubCommandGameAddPlayer;
import net.zeeraa.novacore.spigot.gameengine.command.commands.game.debug.GameDebugCommand;
import net.zeeraa.novacore.spigot.gameengine.command.commands.game.eliminateplayer.NovaCoreSubCommandGameEliminatePlayer;
import net.zeeraa.novacore.spigot.gameengine.command.commands.game.eliminateplayer.offline.NovaCoreSubCommandGameEliminateOfflinePlayer;
import net.zeeraa.novacore.spigot.gameengine.command.commands.game.listplayers.NovaCoreSubCommandGameListplayers;
import net.zeeraa.novacore.spigot.gameengine.command.commands.game.lootdrop.NovaCoreSubCommandGameLootdrop;
import net.zeeraa.novacore.spigot.gameengine.command.commands.game.refill.NovaCoreSubCommandGameRefill;
import net.zeeraa.novacore.spigot.gameengine.command.commands.game.resetcountdown.NovaCoreSubCommandResetCountdownGame;
import net.zeeraa.novacore.spigot.gameengine.command.commands.game.start.NovaCoreSubCommandForceStartGame;
import net.zeeraa.novacore.spigot.gameengine.command.commands.game.start.NovaCoreSubCommandStartGame;
import net.zeeraa.novacore.spigot.gameengine.command.commands.game.stop.NovaCoreSubCommandStopGame;
import net.zeeraa.novacore.spigot.gameengine.command.commands.game.trigger.NovaCoreSubCommandGameTrigger;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
public class NovaCoreCommandGame extends NovaCommand implements wOmeN {
	public NovaCoreCommandGame() {
		super("game", NovaCore.getInstance());

		this.setDescription("Manage minigames");
		this.setPermission("novacore.command.game");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		this.setPermissionDescription("Access to the game command");

		this.addHelpSubCommand();
		this.addSubCommand(new NovaCoreSubCommandStartGame());
		this.addSubCommand(new NovaCoreSubCommandForceStartGame());
		this.addSubCommand(new NovaCoreSubCommandStopGame());
		this.addSubCommand(new NovaCoreSubCommandGameRefill());
		this.addSubCommand(new NovaCoreSubCommandGameLootdrop());
		this.addSubCommand(new NovaCoreSubCommandGameListplayers());
		this.addSubCommand(new NovaCoreSubCommandGameAddPlayer());
		this.addSubCommand(new NovaCoreSubCommandGameEliminatePlayer());
		this.addSubCommand(new NovaCoreSubCommandGameEliminateOfflinePlayer());
		this.addSubCommand(new NovaCoreSubCommandResetCountdownGame());
		this.addSubCommand(new NovaCoreSubCommandGameTrigger());
		this.addSubCommand(new GameDebugCommand());

		this.setEmptyTabMode(true);
		this.setFilterAutocomplete(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.GOLD + "Use " + ChatColor.AQUA + "/game help" + ChatColor.GOLD + " to see all commands");
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