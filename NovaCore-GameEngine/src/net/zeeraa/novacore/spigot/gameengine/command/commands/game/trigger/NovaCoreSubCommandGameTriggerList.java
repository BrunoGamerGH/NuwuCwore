package net.zeeraa.novacore.spigot.gameengine.command.commands.game.trigger;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.GameManager;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.DelayedGameTrigger;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.GameTrigger;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.ScheduledGameTrigger;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.TriggerFlag;

public class NovaCoreSubCommandGameTriggerList extends NovaSubCommand implements wOmeN {
	public NovaCoreSubCommandGameTriggerList() {
		super("list");

		this.setDescription("List all triggers");
		this.setPermission("novacore.command.game.trigger.list");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		this.setPermissionDescription("Access to the /game trigger lidt command");

		this.addHelpSubCommand();

		this.setFilterAutocomplete(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (GameManager.getInstance().hasGame()) {
			String message = "";

			message += ChatColor.GOLD + "Color codes:\n";

			message += ChatColor.RESET + "" + ChatColor.ITALIC + "Italic" + ChatColor.RESET + ChatColor.GOLD + ": can't be triggered by commands\n";
			message += ChatColor.AQUA + "X" + ChatColor.GOLD + ": not scheduled, can be triggered\n";
			message += ChatColor.GREEN + "X" + ChatColor.GOLD + ": running, can be triggered\n";
			message += ChatColor.RED + "X" + ChatColor.GOLD + ": not running, can be triggered, can be started\n";
			message += ChatColor.GRAY + "X" + ChatColor.GOLD + ": can't be triggered again\n";

			message += ChatColor.GOLD + "Triggers:\n";

			for (GameTrigger trigger : GameManager.getInstance().getActiveGame().getTriggers()) {
				String style = "";

				if (trigger.hasFlag(TriggerFlag.DENY_TRIGGER_BY_COMMAND)) {
					style += ChatColor.ITALIC;
				}

				if (trigger.hasFlag(TriggerFlag.RUN_ONLY_ONCE) && trigger.hasBeenCalled()) {
					style += ChatColor.GRAY;
				} else {
					if (trigger instanceof ScheduledGameTrigger) {
						if (((ScheduledGameTrigger) trigger).isRunning()) {
							style += ChatColor.GREEN;
						} else {
							style += ChatColor.RED;
						}
					} else {
						style += ChatColor.AQUA;
					}
				}

				message += style + trigger.getName();

				if (trigger instanceof DelayedGameTrigger) {
					float ticks = ((DelayedGameTrigger) trigger).getTicksLeft();
					message += (((DelayedGameTrigger) trigger).isRunning() ? ChatColor.AQUA : ChatColor.GRAY) + " (Ticks left: " + ticks + " seconds: " + ((int) (ticks / 20)) + ")";
				}

				message += ChatColor.GRAY + " (Count: " + trigger.getTriggerCount() + ")";

				message += "\n";
			}
			sender.sendMessage(message);
		} else {
			sender.sendMessage(ChatColor.RED + "No game has been loaded");
		}

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