package net.zeeraa.novacore.spigot.command.commands.novacore.module;

import java.util.ArrayList;
import java.util.List;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import net.zeeraa.novacore.spigot.module.ModuleManager;
import net.zeeraa.novacore.spigot.module.ModuleNameComparator;
import net.zeeraa.novacore.spigot.module.NovaModule;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
public class NovaCoreSubCommandModulesList extends NovaSubCommand implements wOmeN {

	public NovaCoreSubCommandModulesList() {
		super("list");

		this.setPermission("novacore.command.novacore.modules.list");
		this.setPermissionDefaultValue(PermissionDefault.OP);

		this.setDescription("list modules");

		this.setFilterAutocomplete(true);
		this.setEmptyTabMode(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		String message = ChatColor.AQUA + "" + ModuleManager.getModules().size() + ChatColor.GOLD + " modules loaded\n";

		int enabled = 0;
		int disabled = 0;

		String moduleList = "";

		List<String> keys = new ArrayList<>(ModuleManager.getModules().keySet());

		keys.sort(new ModuleNameComparator());

		for (String key : keys) {
			NovaModule module = ModuleManager.getModule(key);

			if (module.isEnabled()) {
				enabled++;
			} else {
				disabled++;
			}

			String style = "";

			if (ModuleManager.isEssential(module)) {
				style += ChatColor.BOLD;
			}

			moduleList += ChatColor.AQUA + module.getName() + ChatColor.GOLD + " : " + (module.isEnabled() ? ChatColor.GREEN + style + "Enabled" : ChatColor.RED + style + "Disabled") + ChatColor.RESET + "\n";
		}

		message += ChatColor.AQUA + "" + enabled + ChatColor.GOLD + " Enabled, " + ChatColor.AQUA + disabled + ChatColor.GOLD + " Disabled\n";

		message += ChatColor.GOLD + "Module list:\n\n" + moduleList;

		sender.sendMessage(message);

		return false;
	}
}