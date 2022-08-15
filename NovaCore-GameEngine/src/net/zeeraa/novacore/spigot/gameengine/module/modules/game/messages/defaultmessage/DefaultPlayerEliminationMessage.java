package net.zeeraa.novacore.spigot.gameengine.module.modules.game.messages.defaultmessage;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;

import net.md_5.bungee.api.ChatColor;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.elimination.PlayerEliminationReason;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.messages.PlayerEliminationMessage;
import net.zeeraa.novacore.spigot.language.LanguageManager;
import net.zeeraa.novacore.spigot.teams.Team;

/**
 * This is the default player elimination message for NovaCore
 * 
 * @author Zeeraa
 */
public class DefaultPlayerEliminationMessage implements PlayerEliminationMessage {
	@Override
	public void showPlayerEliminatedMessage(OfflinePlayer player, Entity killer, PlayerEliminationReason reason, int placement) {
		ChatColor playerColor = ChatColor.AQUA;

		if (NovaCore.getInstance().getTeamManager() != null) {
			Team playerTeam = NovaCore.getInstance().getTeamManager().getPlayerTeam(player);
			if (playerTeam != null) {
				playerColor = playerTeam.getTeamColor();
			}
		}

		switch (reason) {
		case DEATH:
			LanguageManager.broadcast("novacore.game.elimination.player.died", playerColor.toString(), player.getName());
			break;

		case COMBAT_LOGGING:
			LanguageManager.broadcast("novacore.game.elimination.player.combat_logging", playerColor.toString(), player.getName());
			break;

		case DID_NOT_RECONNECT:
			LanguageManager.broadcast("novacore.game.elimination.player.did_not_reconnect", playerColor.toString(), player.getName());
			break;

		case COMMAND:
			LanguageManager.broadcast("novacore.game.elimination.player.command", playerColor.toString(), player.getName());
			break;

		case KILLED:
			String killerName = "";
			if (killer != null) {
				if (killer instanceof Projectile) {
					Entity theBoiWhoFirered = (Entity) ((Projectile) killer).getShooter();

					if (theBoiWhoFirered != null) {
						killerName = theBoiWhoFirered.getName();
					} else {
						killerName = killer.getName();
					}
				} else {
					killerName = killer.getName();
				}
			}
			LanguageManager.broadcast("novacore.game.elimination.player.killed", playerColor.toString(), player.getName(), killerName);
			break;

		case QUIT:
			LanguageManager.broadcast("novacore.game.elimination.player.quit", playerColor.toString(), player.getName());
			break;

		default:
			LanguageManager.broadcast("novacore.game.elimination.player.unknown", playerColor.toString(), player.getName());
			break;
		}
		// Bukkit.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD +
		// "Player Eliminated> " + playerColor + ChatColor.BOLD + player.getName() + " "
		// + ChatColor.GOLD + ChatColor.BOLD + extra);
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}