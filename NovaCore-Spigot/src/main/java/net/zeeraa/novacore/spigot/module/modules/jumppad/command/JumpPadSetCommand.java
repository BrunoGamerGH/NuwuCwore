package net.zeeraa.novacore.spigot.module.modules.jumppad.command;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.Vector;

import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import net.zeeraa.novacore.spigot.module.modules.jumppad.JumpPad;
import net.zeeraa.novacore.spigot.module.modules.jumppad.JumpPadManager;

public class JumpPadSetCommand extends NovaSubCommand implements wOmeN {

	public JumpPadSetCommand() {
		super("set");

		setDescription("Create or alter a jump pad");
		setAllowedSenders(AllowedSenders.PLAYERS);
		setPermission("novauniverse.lobby.command.jumppad.set");
		setPermissionDefaultValue(PermissionDefault.OP);

		addHelpSubCommand();
		setEmptyTabMode(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (args.length < 4) {
			sender.sendMessage(ChatColor.RED + "Useage: " + ChatColor.AQUA + "/jumppad set <X Velocity> <Y Velocity> <Z Velocity> <Radius>");
			return true;
		}

		double x;
		double y;
		double z;

		double radius;

		try {
			x = Double.parseDouble(args[0]);
		} catch (Exception e) {
			sender.sendMessage("Invalid number for X Velocity parameter");
			return true;
		}

		try {
			y = Double.parseDouble(args[1]);
		} catch (Exception e) {
			sender.sendMessage("Invalid number for Y Velocity parameter");
			return true;
		}

		try {
			z = Double.parseDouble(args[2]);
		} catch (Exception e) {
			sender.sendMessage("Invalid number for Z Velocity parameter");
			return true;
		}

		try {
			radius = Double.parseDouble(args[3]);
		} catch (Exception e) {
			sender.sendMessage("Invalid number for Radius parameter");
			return true;
		}

		if (radius < 1) {
			sender.sendMessage(ChatColor.RED + "Radius can't be less than 1");
		}

		for (JumpPad pad : JumpPadManager.getInstance().getJumpPads()) {
			if (pad.isInRange((Player) sender)) {
				pad.setRadius(radius);

				pad.getVelocity().setX(x);
				pad.getVelocity().setY(y);
				pad.getVelocity().setZ(z);

				sender.sendMessage(ChatColor.GOLD + "Changed jump pad at" + ChatColor.AQUA + " X: " + pad.getLocation().getBlockX() + " Y: " + pad.getLocation().getBlockY() + " Z: " + pad.getLocation().getBlockZ());

				return true;
			}
		}

		Location location = ((Player) sender).getLocation().clone();

		location.setX(location.getBlockX());
		location.setY(location.getBlockY());
		location.setZ(location.getBlockZ());

		location.setYaw(0);
		location.setPitch(0);

		JumpPad pad = new JumpPad(location, new Vector(x, y, z), radius, NovaCore.getInstance());

		JumpPadManager.getInstance().getJumpPads().add(pad);

		sender.sendMessage(ChatColor.GOLD + "Created a new jump pad at" + ChatColor.AQUA + " X: " + pad.getLocation().getBlockX() + " Y: " + pad.getLocation().getBlockY() + " Z: " + pad.getLocation().getBlockZ());

		return true;
	}
}