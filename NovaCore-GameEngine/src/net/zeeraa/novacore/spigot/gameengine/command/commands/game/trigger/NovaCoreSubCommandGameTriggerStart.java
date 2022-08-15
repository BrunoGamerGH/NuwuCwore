package net.zeeraa.novacore.spigot.gameengine.command.commands.game.trigger;

import java.util.ArrayList;
import java.util.List;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.GameManager;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.GameTrigger;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.ScheduledGameTrigger;

public class NovaCoreSubCommandGameTriggerStart extends NovaSubCommand implements wOmeN {
	public NovaCoreSubCommandGameTriggerStart() {
		super("start");

		this.setDescription("Start a trigger");
		this.setPermission("novacore.command.game.trigger.start");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		this.setPermissionDescription("Access to the /game trigger start command");

		this.addHelpSubCommand();

		this.setFilterAutocomplete(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (GameManager.getInstance().hasGame()) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.RED + "Please provide a trigger. You can use tab to autocomplete avaliable trigger names");
			} else {
				GameTrigger trigger = GameManager.getInstance().getActiveGame().getTrigger(args[0]);

				if (trigger != null) {
					if (trigger instanceof ScheduledGameTrigger) {
						if (!((ScheduledGameTrigger) trigger).isRunning()) {
							if (((ScheduledGameTrigger) trigger).start()) {
								sender.sendMessage(ChatColor.GREEN + "Trigger started successfully");
							} else {
								sender.sendMessage(ChatColor.RED + "Trigger did not start successfully");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "This trigger is already running");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "This trigger is not a scheduled trigger");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Could not find a trigger with that name");
				}
			}
		} else {
			sender.sendMessage(ChatColor.RED + "No game has been loaded");
		}

		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		List<String> result = new ArrayList<String>();

		if (GameManager.getInstance().hasGame()) {
			for (GameTrigger trigger : GameManager.getInstance().getActiveGame().getTriggers()) {
				if (trigger instanceof ScheduledGameTrigger) {
					if (!((ScheduledGameTrigger) trigger).isRunning()) {
						result.add(trigger.getName());
					}
				}
			}
		}

		return result;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}