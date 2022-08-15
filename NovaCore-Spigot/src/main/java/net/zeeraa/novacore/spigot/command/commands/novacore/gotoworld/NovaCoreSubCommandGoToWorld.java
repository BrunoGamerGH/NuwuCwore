package net.zeeraa.novacore.spigot.command.commands.novacore.gotoworld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.StringUtil;

import com.google.common.collect.ImmutableList;

import net.md_5.bungee.api.ChatColor;
import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
public class NovaCoreSubCommandGoToWorld extends NovaSubCommand implements wOmeN {
	public NovaCoreSubCommandGoToWorld() {
		super("gotoworld");

		this.setPermission("novacore.command.novacore.gotoworld");
		this.setPermissionDefaultValue(PermissionDefault.OP);

		this.setAllowedSenders(AllowedSenders.ENTITY);

		this.addHelpSubCommand();

		this.setDescription("Teleport to the spawn of a world");

		this.setFilterAutocomplete(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (sender instanceof Entity) {
			Entity entity = (Entity) sender;

			if (args.length > 0) {
				for (World world : Bukkit.getServer().getWorlds()) {
					if (world.getName().equalsIgnoreCase(args[0])) {
						entity.teleport(world.getSpawnLocation());
						entity.sendMessage(ChatColor.GREEN + "Teleported to the spawnpoint of world " + ChatColor.AQUA + world.getName());
						return true;
					}
				}

				entity.sendMessage(ChatColor.RED + "Could not find a world with that name");
			} else {
				entity.sendMessage(ChatColor.RED + "Missing argument: world name");
			}

		}
		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		if (args.length == 0) {
			return ImmutableList.of();
		}

		String lastWord = args[args.length - 1];

		ArrayList<String> matchedWorlds = new ArrayList<String>();
		Bukkit.getServer().getWorlds().forEach(world -> {
			String name = world.getName();
			if (StringUtil.startsWithIgnoreCase(name, lastWord)) {
				matchedWorlds.add(name);
			}
		});

		Collections.sort(matchedWorlds, String.CASE_INSENSITIVE_ORDER);
		return matchedWorlds;
	}
}