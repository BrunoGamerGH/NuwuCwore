package net.zeeraa.novacore.spigot.command.commands.novacore.logger;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import net.md_5.bungee.api.ChatColor;
import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
public class NovaCoreSubCommandLogger extends NovaSubCommand implements wOmeN {
	public NovaCoreSubCommandLogger() {
		super("logger");
		
		this.setAliases(NovaSubCommand.generateAliasList("log"));
		this.setPermission("novacore.command.novacore.logger");
		this.setPermissionDefaultValue(PermissionDefault.OP);

		this.addHelpSubCommand();
		this.addSubCommand(new NovaCoreSubCommandLoggerSet());
		this.addSubCommand(new NovaCoreSubCommandLoggerUnsubscribe());
		
		this.setDescription("Set log level for player and console");
		
		this.setFilterAutocomplete(true);
		this.setEmptyTabMode(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player)sender;
			if(Log.getSubscribedPlayers().containsKey(p.getUniqueId())) {
				p.sendMessage(ChatColor.GOLD + "Your log level is: " + Log.getSubscribedPlayers().get(p.getUniqueId()).name());
			} else {
				p.sendMessage(ChatColor.GOLD + "You dont have any log level set");
			}
		}
		
		sender.sendMessage(ChatColor.GOLD + "Use "+ChatColor.AQUA+"/novacore logger help" + ChatColor.GOLD + " for help");
		
		return true;
	}
}