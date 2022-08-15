package net.zeeraa.novacore.spigot.mapdisplay.command.subcommand;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.BlockIterator;

import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import net.zeeraa.novacore.spigot.mapdisplay.MapDisplay;
import net.zeeraa.novacore.spigot.mapdisplay.MapDisplayManager;
import net.zeeraa.novacore.spigot.mapdisplay.MapDisplayNameAlreadyExistsException;

public class MDCreateSubCommand extends NovaSubCommand implements wOmeN {

	public MDCreateSubCommand() {
		super("create");

		setPermission("novacore.command.mapdisplay.create");
		setPermissionDefaultValue(PermissionDefault.OP);
		setDescription("Create a map display");
		setEmptyTabMode(true);
		setAllowedSenders(AllowedSenders.PLAYERS);
		setUsage("/mapdisplay create <name>");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(!MapDisplayManager.getInstance().isEnabled()) {
			sender.sendMessage(ChatColor.DARK_RED + "MapDisplayManager is not enabled");
			return false;
		}
		
		Player player = (Player) sender;

		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Please provide a name");	
			return false;
		}

		Location location = null;
		BlockIterator iter = new BlockIterator(player, 10);

		Block lastBlock = iter.next();
		while (iter.hasNext()) {
			Block next = iter.next();

			if (next.getType() == Material.AIR) {
				lastBlock = next;
				continue;
			}

			location = lastBlock.getLocation();

			break;
		}

		if (location != null) {
			ItemFrame frame = MapDisplayManager.getInstance().getItemFrameAtLocation(location);

			if (frame != null) {
				try {
					MapDisplay display = MapDisplayManager.getInstance().createMapDisplay(frame, true, args[0]);
					try {
						display.setImage(null);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (MapDisplayNameAlreadyExistsException e) {
					sender.sendMessage(ChatColor.RED + "A display with that name already exists");
				}
				return true;
			}
		}
		player.sendMessage(ChatColor.RED + "Please look at an item frame before running this");

		return false;
	}
}