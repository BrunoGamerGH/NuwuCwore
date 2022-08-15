package net.zeeraa.novacore.spigot.command.commands.novacore.module;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import net.zeeraa.novacore.spigot.module.ModuleManager;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
public class NovaCoreSubCommandModules extends NovaSubCommand implements wOmeN {
	public NovaCoreSubCommandModules() {
		super("modules");

		this.setDescription("Manage modules");

		this.setAliases(generateAliasList("module"));

		this.setPermission("novacore.command.novacore.modules");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		
		this.addHelpSubCommand();
		this.addSubCommand(new NovaCoreSubCommandModulesList());
		this.addSubCommand(new NovaCoreSubCommandModulesDisable());
		this.addSubCommand(new NovaCoreSubCommandModulesEnable());
		
		this.setFilterAutocomplete(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.AQUA + "" + ModuleManager.getModules().size() + ChatColor.GOLD + " Modules loaded. use " + ChatColor.AQUA + "/novacore modules help" + ChatColor.GOLD + " for help");
		return true;
	}
}