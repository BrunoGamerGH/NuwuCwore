package net.zeeraa.novacore.spigot.gamemapdesigntoolkit.command;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.json.JSONObject;

import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaCommand;
import net.zeeraa.novacore.spigot.utils.LocationUtils;

public class CopyLocationCommand extends NovaCommand implements wOmeN {
	public CopyLocationCommand(Plugin owner) {
		super("copylocation", owner);

		setAliases(generateAliasList("cl"));
		setAllowedSenders(AllowedSenders.PLAYERS);
		setPermission("gamemapdesigntoolkit.copylocation");
		setPermissionDefaultValue(PermissionDefault.OP);
		setPermissionDescription("Allow the player to use the copy location command");
		setUsage("/copylocation [-v | --verbose] [-c | --center] [-l | --location-only]");
		setDescription("Copy the player location as json to the server clipboard");
		addHelpSubCommand();
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		int indent = 4;
		boolean verbose = false;
		boolean center = false;
		boolean onlyLocation = false;

		for (int i = 0; i < args.length; i++) {
			if (args[i].equalsIgnoreCase("-v") || args[i].equalsIgnoreCase("--verbose")) {
				verbose = true;
			} else if (args[i].equalsIgnoreCase("-c") || args[i].equalsIgnoreCase("--center")) {
				center = true;
			} else if (args[i].equalsIgnoreCase("-l") || args[i].equalsIgnoreCase("--location-only")) {
				onlyLocation = true;
			}
		}

		Player player = (Player) sender;

		Location location = player.getLocation();

		JSONObject json = new JSONObject();

		double x;
		double y = location.getY();
		double z;

		if (center) {
			x = LocationUtils.blockCenter(location.getBlockX());
			z = LocationUtils.blockCenter(location.getBlockZ());
		} else {
			x = location.getX();
			z = location.getZ();
		}

		json.put("x", x);
		json.put("y", y);
		json.put("z", z);

		if (!onlyLocation) {
			json.put("yaw", location.getYaw());
			json.put("pitch", location.getPitch());
		}

		String string;

		if (indent == -1) {
			string = json.toString();
		} else {
			string = json.toString(indent);
		}

		StringSelection stringSelection = new StringSelection(string);

		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);

		player.sendMessage(ChatColor.GREEN + "ok");

		if (verbose) {
			player.sendMessage(ChatColor.AQUA + string);
		}

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