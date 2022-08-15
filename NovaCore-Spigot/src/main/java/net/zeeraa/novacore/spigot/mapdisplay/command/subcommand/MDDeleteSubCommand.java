package net.zeeraa.novacore.spigot.mapdisplay.command.subcommand;

import java.util.ArrayList;
import java.util.List;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import net.zeeraa.novacore.spigot.mapdisplay.MapDisplay;
import net.zeeraa.novacore.spigot.mapdisplay.MapDisplayManager;

public class MDDeleteSubCommand extends NovaSubCommand implements wOmeN {

	public MDDeleteSubCommand() {
		super("delete");

		setPermission("novacore.command.mapdisplay.delete");
		setPermissionDefaultValue(PermissionDefault.OP);
		setDescription("Delete a map display");
		setFilterAutocomplete(true);
		setAllowedSenders(AllowedSenders.ALL);
		setUsage("/mapdisplay delete <name>");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(!MapDisplayManager.getInstance().isEnabled()) {
			sender.sendMessage(ChatColor.DARK_RED + "MapDisplayManager is not enabled");
			return false;
		}
		
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Please provide a name");
			return false;
		}

		for (MapDisplay display : MapDisplayManager.getInstance().getMapDisplays()) {
			if (display.getName().equalsIgnoreCase(args[0])) {
				display.delete();
				sender.sendMessage(ChatColor.GREEN + "Display removed");
				return true;
			}
		}

		sender.sendMessage(ChatColor.RED + "Could not find map display named " + args[0]);

		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		List<String> result = new ArrayList<String>();

		MapDisplayManager.getInstance().getMapDisplays().forEach(display -> result.add(display.getName()));

		return result;
	}
}