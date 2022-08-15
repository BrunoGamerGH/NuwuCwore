package net.zeeraa.novacore.spigot.gamemapdesigntoolkit.command;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import net.zeeraa.novacore.spigot.abstraction.VersionIndependentUtils;
import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaCommand;
import net.zeeraa.novacore.spigot.utils.BukkitSerailization;

public class CopyItemBase64 extends NovaCommand implements wOmeN {
	public CopyItemBase64(Plugin owner) {
		super("copyitembase64", owner);

		setAllowedSenders(AllowedSenders.PLAYERS);
		setPermission("gamemapdesigntoolkit.copyitembase64");
		setPermissionDefaultValue(PermissionDefault.OP);
		setPermissionDescription("Allow the player to use the copy item base64 command");
		setUsage("/copyitembase64");
		setDescription("Copy the item base64 to server clipboard");
		addHelpSubCommand();
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		Player player = (Player) sender;

		String string;
		try {
			string = BukkitSerailization.itemStackToBase64(VersionIndependentUtils.get().getItemInMainHand(player));
		} catch (IOException e) {
			player.sendMessage(ChatColor.RED + "Failed to encode item to base64");
			e.printStackTrace();
			return false;
		}

		StringSelection stringSelection = new StringSelection(string);

		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);

		player.sendMessage(ChatColor.GREEN + "ok");

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