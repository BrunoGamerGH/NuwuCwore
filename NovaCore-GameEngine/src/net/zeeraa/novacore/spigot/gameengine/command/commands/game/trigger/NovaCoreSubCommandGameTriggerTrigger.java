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
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.TriggerFlag;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.TriggerResponse;

public class NovaCoreSubCommandGameTriggerTrigger extends NovaSubCommand implements wOmeN {
	public NovaCoreSubCommandGameTriggerTrigger() {
		super("trigger");

		this.setDescription("Activate a trigger");
		this.setPermission("novacore.command.game.trigger.trigger");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		this.setPermissionDescription("Access to the /game trigger trigger command");

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
					if (!trigger.hasFlag(TriggerFlag.DENY_TRIGGER_BY_COMMAND)) {
						TriggerResponse response = trigger.trigger(TriggerFlag.COMMAND_ACTIVATION);

						if (response.isSuccess()) {
							sender.sendMessage(ChatColor.GREEN + "Trigger activated succesfully");
						} else {
							sender.sendMessage(ChatColor.RED + "Trigger did not activate");
						}
						return true;
					} else {
						sender.sendMessage(ChatColor.RED + "This trigger does not allow execution by commands");
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
				if (trigger.hasFlag(TriggerFlag.DENY_TRIGGER_BY_COMMAND)) {
					continue;
				}
				result.add(trigger.getName());
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