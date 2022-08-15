package net.zeeraa.novacore.spigot.command.commands.novacore.customitem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import net.zeeraa.novacore.spigot.module.modules.customitems.CustomItemManager;

public class NovaCoreSubCommandGiveCustomItem extends NovaSubCommand implements wOmeN {
	public NovaCoreSubCommandGiveCustomItem() {
		super("givecustomitem");

		setAllowedSenders(AllowedSenders.PLAYERS);
		setPermission("novacore.command.novacore.givecustomitem");
		setPermissionDefaultValue(PermissionDefault.OP);

		setFilterAutocomplete(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		Player player = (Player) sender;
		if (!CustomItemManager.getInstance().isEnabled()) {
			player.sendMessage(ChatColor.RED + "Custom item manger is not enabled");
			return false;
		}

		if (args.length == 0) {
			player.sendMessage(ChatColor.RED + "Please provide the name of the item to generate");
			return false;
		}

		String className = CustomItemManager.getInstance().getCustomItems().keySet().stream().filter(name -> name.startsWith(args[0])).findFirst().orElse(null);
		if (className == null) {
			player.sendMessage(ChatColor.RED + "No items found with name matching " + args[0]);
			return false;
		}

		ItemStack item = CustomItemManager.getInstance().getCustomItemStack(className, player);

		if (item == null) {
			player.sendMessage(ChatColor.RED + "Failed to generate item stack");
			return false;
		}

		if (player.getInventory().addItem(item).size() == 0) {
			player.sendMessage(ChatColor.GREEN + "Item added to your inventory");
			return true;
		}

		player.sendMessage(ChatColor.GREEN + "You dont have space in your inventory for this item");
		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		if (args.length == 0 || args.length == 1) {
			if (CustomItemManager.getInstance().isEnabled()) {
				List<String> result = new ArrayList<>();

				CustomItemManager.getInstance().getCustomItems().keySet().forEach(name -> result.add(name));

				return result;
			}
		}

		return Collections.emptyList();
	}
}