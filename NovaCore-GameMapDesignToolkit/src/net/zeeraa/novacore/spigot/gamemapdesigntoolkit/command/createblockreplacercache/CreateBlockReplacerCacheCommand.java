package net.zeeraa.novacore.spigot.gamemapdesigntoolkit.command.createblockreplacercache;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaCommand;
import net.zeeraa.novacore.spigot.gamemapdesigntoolkit.blockreplacercachetask.BlockReplacerCacheTask;
import net.zeeraa.novacore.spigot.utils.VectorArea;

public class CreateBlockReplacerCacheCommand extends NovaCommand implements wOmeN {
	public CreateBlockReplacerCacheCommand(Plugin owner) {
		super("createblockreplacercache", owner);

		setAllowedSenders(AllowedSenders.PLAYERS);
		setPermission("gamemapdesigntoolkit.createblockreplacercache");
		setPermissionDefaultValue(PermissionDefault.OP);
		setUsage("/createblockreplacercache x1 z1 x2 z2");
		addHelpSubCommand();
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (args.length < 6) {
			sender.sendMessage(ChatColor.RED + "Useage: " + ChatColor.AQUA + "/createblockreplacercache x1 y1 z1 x2 y2 z2");
			return true;
		}

		int x1;
		int x2;
		
		int y1;
		int y2;
		
		int z1;
		int z2;

		try {
			x1 = Integer.parseInt(args[0]);
			y1 = Integer.parseInt(args[1]);
			z1 = Integer.parseInt(args[2]);
			x2 = Integer.parseInt(args[3]);
			y2 = Integer.parseInt(args[4]);
			z2 = Integer.parseInt(args[5]);
		} catch (Exception e) {
			sender.sendMessage(ChatColor.RED + "Please provide valid numbers. Error: " + e.getClass().getName() + " " + e.getMessage());
			return true;
		}

		VectorArea vectorArea = new VectorArea(x1, y1, z1, x2, y2, z2);
		new BlockReplacerCacheTask(vectorArea, ((Player) sender).getWorld());

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