package net.zeeraa.novacore.spigot.gameengine.command.commands.game.trigger;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;

public class NovaCoreSubCommandGameTrigger extends NovaSubCommand implements wOmeN {
	public NovaCoreSubCommandGameTrigger() {
		super("trigger");

		this.setDescription("Command to manage triggers");
		this.setPermission("novacore.command.game.trigger");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		this.setPermissionDescription("Access to the game trigger command");

		this.addSubCommand(new NovaCoreSubCommandGameTriggerTrigger());
		this.addSubCommand(new NovaCoreSubCommandGameTriggerList());
		this.addSubCommand(new NovaCoreSubCommandGameTriggerStart());
		this.addSubCommand(new NovaCoreSubCommandGameTriggerStop());

		this.addHelpSubCommand();

		this.setFilterAutocomplete(true);
		this.setEmptyTabMode(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.GOLD + "Use " + ChatColor.AQUA + "/game trigger help " + ChatColor.GOLD + " for help");
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