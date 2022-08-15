package net.zeeraa.novacore.spigot.command.commands.novacore.loottable;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import net.zeeraa.novacore.spigot.loottable.LootTable;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
public class NovaCoreSubCommandLootTableList extends NovaSubCommand implements wOmeN {
	public NovaCoreSubCommandLootTableList() {
		super("list");

		this.setDescription("List loot tables");

		this.setPermission("novacore.command.novacore.loottable.list");
		this.setPermissionDefaultValue(PermissionDefault.OP);

		this.setFilterAutocomplete(true);

		this.setEmptyTabMode(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		String message = ChatColor.AQUA + "" + NovaCore.getInstance().getLootTableManager().getLootTables().size() + ChatColor.GOLD + " Loot tables loaded\n";

		String lootTableList = "";
		for (String key : NovaCore.getInstance().getLootTableManager().getLootTables().keySet()) {
			LootTable lootTable = NovaCore.getInstance().getLootTableManager().getLootTable(key);

			lootTableList += ChatColor.AQUA + lootTable.getName() + ChatColor.GOLD + " : " + ChatColor.AQUA + lootTable.getDisplayName() + ChatColor.RESET + "\n";
		}

		message += ChatColor.GOLD + "Loot table list:\n-- name -- | -- display name --\n\n" + lootTableList;

		sender.sendMessage(message);

		return false;
	}
}