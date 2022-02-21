package net.zeeraa.novacore.spigot.command.commands.novacore.module;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.md_5.bungee.api.ChatColor;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import net.zeeraa.novacore.spigot.module.ModuleEnableFailureReason;
import net.zeeraa.novacore.spigot.module.ModuleManager;
import net.zeeraa.novacore.spigot.module.NovaModule;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
public class NovaCoreSubCommandModulesEnable extends NovaSubCommand {
	public NovaCoreSubCommandModulesEnable() {
		super("enable");
		setPermission("novacore.command.novacore.modules.enable");
		this.setPermissionDefaultValue(PermissionDefault.OP);

		setDescription("Enable a module");

		this.setFilterAutocomplete(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Please specify a module name");
			return false;
		}

		NovaModule module = null;

		for (String key : ModuleManager.getModules().keySet()) {
			NovaModule m = ModuleManager.getModule(key);
			if (m.getName().equalsIgnoreCase(args[0])) {
				module = m;
				break;
			}
		}

		if (module == null) {
			sender.sendMessage(ChatColor.RED + "Could not find a module with that name");
			return false;
		}

		if (ModuleManager.isEssential(module)) {
			sender.sendMessage(ChatColor.RED + "This module cant be enabled using this command");
			return false;
		}

		if (module.isEnabled()) {
			sender.sendMessage(ChatColor.RED + "That module is already enabled");
			return false;
		}

		if (module.enable()) {
			sender.sendMessage(ChatColor.GREEN + "Module " + module.getName() + " was enabled successfully");
			return true;
		} else {
			ModuleEnableFailureReason reason = module.getEnableFailureReason();

			if (reason == ModuleEnableFailureReason.MISSING_PLUGIN_DEPENDENCY) {
				sender.sendMessage(ChatColor.RED + "This module cant be enabled sinse it depends on the plugin " + module.getMissingPluginName() + " that is not installed. Please install the plugin before trying to enable this module");
			} else {
				sender.sendMessage(ChatColor.RED + "An error occured while trying to enable module " + module.getName() + ".\nMore info might be avaliable in the console." + ChatColor.RED + reason.name());
			}
			return false;
		}
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		ArrayList<String> modules = new ArrayList<String>();

		if (args.length == 1) {
			ModuleManager.getModules().keySet().forEach(key -> {
				if (ModuleManager.isDisabled(key)) {
					if (!ModuleManager.isEssential(ModuleManager.getModules().get(key))) {
						modules.add(ModuleManager.getModule(key).getName());
					}
				}
			});
		}

		return modules;
	}
}