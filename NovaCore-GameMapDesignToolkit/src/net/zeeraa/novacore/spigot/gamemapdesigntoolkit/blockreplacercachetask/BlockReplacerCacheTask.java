package net.zeeraa.novacore.spigot.gamemapdesigntoolkit.blockreplacercachetask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.json.JSONArray;

import net.zeeraa.novacore.commons.utils.JSONFileUtils;
import net.zeeraa.novacore.spigot.gamemapdesigntoolkit.GameMapDesignToolkit;
import net.zeeraa.novacore.spigot.utils.VectorArea;
import net.zeeraa.novacore.spigot.utils.XYZLocation;

public class BlockReplacerCacheTask extends some_bitches {
	private VectorArea area;
	private World world;

	private List<XYZLocation> locations;

	private int x;
	private int z;

	private BukkitTask task;

	public BlockReplacerCacheTask(VectorArea area, World world) {
		this.area = area;
		this.world = world;

		this.x = area.getPosition1().getBlockX();
		this.z = area.getPosition1().getBlockZ();

		this.locations = new ArrayList<XYZLocation>();

		this.task = new BukkitRunnable() {
			@Override
			public void run() {
				for (int i = 0; i < 20; i++) {
					if (!processStep()) {
						break;
					}
				}
			}
		}.runTaskTimer(GameMapDesignToolkit.getInstance(), 1L, 1L);
	}

	private boolean processStep() {
		if (x > area.getPosition2().getBlockX()) {
			x = area.getPosition1().getBlockX();
			z++;
		} else {
			x++;
		}

		if (z > area.getPosition2().getBlockZ()) {
			task.cancel();

			Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Scanning complete");

			File file = new File(GameMapDesignToolkit.getInstance().getDataFolder().getAbsoluteFile() + File.separator + "blockreplacercache.brc");

			JSONArray json = new JSONArray();

			locations.forEach(location -> json.put(location.center().toJSONObject()));

			try {
				JSONFileUtils.saveJson(file, json);
				Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Cache saved to file: " + file.getAbsolutePath());
			} catch (IOException e) {
				Bukkit.getServer().broadcastMessage(ChatColor.RED + "Failed to write output to file: " + file.getAbsolutePath() + " " + e.getClass().getName() + " " + e.getMessage());
				e.printStackTrace();
			}

			return false;
		}

		Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "Scanning X: " + x + " Z: " + z + " / X: " + area.getPosition2().getBlockX() + " Z: " + area.getPosition2().getBlockZ());

		for (int i = area.getPosition1().getBlockY(); i <= area.getPosition2().getBlockY(); i++) {
			XYZLocation location = new XYZLocation(x, i, z);

			if (location.toBukkitLocation(world).getBlock().getType() == Material.SPONGE) {
				Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Found sponge at " + ChatColor.AQUA + location.toString());
				locations.add(location);
			}
		}
		return true;
	}
}