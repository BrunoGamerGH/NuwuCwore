package net.zeeraa.novacore.spigot.command.commands.novacore.logger;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
public class NovaCoreSubCommandLoggerUnsubscribe extends NovaSubCommand implements wOmeN {
	public NovaCoreSubCommandLoggerUnsubscribe() {
		super("unsubscribe");
		this.setPermission("novacore.command.novacore.logger.unsubscribe");
		this.setPermissionDefaultValue(PermissionDefault.OP);

		this.setAllowedSenders(AllowedSenders.PLAYERS);

		this.setDescription("Disable all log messages");
		
		this.setFilterAutocomplete(true);
		this.setEmptyTabMode(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		Player player = (Player) sender;

		if (Log.subscribedPlayers.containsKey(player.getUniqueId())) {
			Log.subscribedPlayers.remove(player.getUniqueId());
			player.sendMessage(ChatColor.GREEN + "You will no longer receive logger messages");
		} else {
			player.sendMessage(ChatColor.GREEN + "You already have logger messages disabled");
		}

		return true;
	}
}