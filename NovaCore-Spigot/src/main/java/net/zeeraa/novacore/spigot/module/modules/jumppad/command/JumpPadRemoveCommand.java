package net.zeeraa.novacore.spigot.module.modules.jumppad.command;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import net.zeeraa.novacore.spigot.module.modules.jumppad.JumpPad;
import net.zeeraa.novacore.spigot.module.modules.jumppad.JumpPadManager;

public class JumpPadRemoveCommand extends NovaSubCommand implements wOmeN {

	public JumpPadRemoveCommand() {
		super("remove");

		setDescription("Remove a jump pad");
		setAllowedSenders(AllowedSenders.PLAYERS);
		setPermission("novauniverse.lobby.command.jumppad.remove");
		setPermissionDefaultValue(PermissionDefault.OP);

		addHelpSubCommand();
		setEmptyTabMode(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		for (int i = 0; i < JumpPadManager.getInstance().getJumpPads().size(); i++) {
			JumpPad pad = JumpPadManager.getInstance().getJumpPads().get(i);
			if (pad.isInRange((Player) sender)) {
				JumpPadManager.getInstance().getJumpPads().remove(i);
				sender.sendMessage(ChatColor.GOLD + "Removed jump pad at" + ChatColor.AQUA + " X: " + pad.getLocation().getBlockX() + " Y: " + pad.getLocation().getBlockY() + " Z: " + pad.getLocation().getBlockZ());
				return true;
			}
		}

		sender.sendMessage(ChatColor.RED + "You need to stand on a jump pad to remove it. You can fly in creative to not get yeeted away by the jump pad");

		return true;
	}
}