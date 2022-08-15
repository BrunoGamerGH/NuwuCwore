package net.zeeraa.novacore.spigot.command.commands.novacore.whereami;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
public class NovaCoreSubCommandWhereAmI extends NovaSubCommand implements wOmeN {
	public NovaCoreSubCommandWhereAmI() {
		super("whereami");

		this.setAliases(NovaSubCommand.generateAliasList("wai"));
		this.setPermission("novacore.command.novacore.whereami");
		this.setPermissionDefaultValue(PermissionDefault.OP);

		this.setAllowedSenders(AllowedSenders.PLAYERS);

		this.setDescription("Show your location");

		this.setFilterAutocomplete(true);
		this.setEmptyTabMode(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			p.sendMessage(ChatColor.GOLD + "-=-= Your location is =-=-");
			p.sendMessage(ChatColor.GOLD + "World: " + ChatColor.AQUA + p.getLocation().getWorld().getName());
			p.sendMessage(ChatColor.GOLD + "X: " + ChatColor.AQUA + p.getLocation().getX());
			p.sendMessage(ChatColor.GOLD + "Y: " + ChatColor.AQUA + p.getLocation().getY());
			p.sendMessage(ChatColor.GOLD + "Z: " + ChatColor.AQUA + p.getLocation().getZ());
			p.sendMessage(ChatColor.GOLD + "Yaw: " + ChatColor.AQUA + p.getLocation().getYaw());
			p.sendMessage(ChatColor.GOLD + "Pitch: " + ChatColor.AQUA + p.getLocation().getPitch());
		}

		return true;
	}
}