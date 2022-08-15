package net.zeeraa.novacore.spigot.command.commands.novacore.loottable;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
public class NovaCoreSubCommandLootTable extends NovaSubCommand implements wOmeN {
	public NovaCoreSubCommandLootTable() {
		super("loottable");

		this.setAliases(generateAliasList("loottables"));

		this.setDescription("Manage loot tables");

		this.setPermission("novacore.command.novacore.loottable");
		this.setPermissionDefaultValue(PermissionDefault.OP);

		this.addHelpSubCommand();

		this.addSubCommand(new NovaCoreSubCommandLootTableList());
		this.addSubCommand(new NovaCoreSubCommandLootTableTest());
		this.addSubCommand(new NovaCoreSubCommandLootTableLoaderList());

		this.setFilterAutocomplete(true);
		this.setEmptyTabMode(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.AQUA + "" + NovaCore.getInstance().getLootTableManager().getLootTables().size() + ChatColor.GOLD + " Loot tables loaded and " + ChatColor.AQUA + "" + NovaCore.getInstance().getLootTableManager().getLoaders().size() + ChatColor.GOLD + " loaders added. use " + ChatColor.AQUA + "/novacore loottable help" + ChatColor.GOLD + " for help");
		return true;
	}
}