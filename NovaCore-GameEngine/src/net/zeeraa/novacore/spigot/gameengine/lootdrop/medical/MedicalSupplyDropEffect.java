package net.zeeraa.novacore.spigot.gameengine.lootdrop.medical;

import java.util.HashMap;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.abstraction.enums.ColoredBlockType;
import net.zeeraa.novacore.spigot.abstraction.enums.VersionIndependentSound;

public class MedicalSupplyDropEffect extends some_bitches implements Runnable {
	private Location location;
	private String lootTable;

	private int taskId;
	private boolean completed;
	private double y;

	private HashMap<Location, Material> removedBlocks;

	public Location getFireworkLocation() {
		return new Location(location.getWorld(), location.getX(), (double) y, location.getZ());
	}

	public MedicalSupplyDropEffect(Location location, String lootTable) {
		location.setX(location.getBlockX() + 0.5);
		location.setY(location.getBlockY());
		location.setZ(location.getBlockZ() + 0.5);

		this.taskId = -1;

		this.completed = false;

		this.lootTable = lootTable;

		this.removedBlocks = new HashMap<Location, Material>();
		this.location = location;

		this.y = location.getWorld().getMaxHeight();

		this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(NovaCore.getInstance(), this, 5L, 5L);

		for (int x = (int) location.getBlockX() - 1; x <= location.getBlockX() + 1; x++) {
			for (int z = location.getBlockZ() - 1; z <= location.getBlockZ() + 1; z++) {
				Location l1 = new Location(location.getWorld(), x, location.getBlockY() - 3, z);
				Location l2 = new Location(location.getWorld(), x, location.getBlockY() - 2, z);

				removedBlocks.put(l1, l1.getWorld().getBlockAt(l1).getType());
				removedBlocks.put(l2, l2.getWorld().getBlockAt(l2).getType());

				l1.getBlock().setType(Material.IRON_BLOCK);
				l2.getBlock().setType(Material.IRON_BLOCK);
			}
		}

		Location beaconLocation = new Location(location.getWorld(), location.getBlockX(), location.getBlockY() - 2, location.getBlockZ());
		Location glassLocation = new Location(location.getWorld(), location.getBlockX(), location.getBlockY() - 1, location.getBlockZ());

		removedBlocks.put(glassLocation, beaconLocation.getWorld().getBlockAt(glassLocation).getType());

		beaconLocation.getBlock().setType(Material.BEACON);
		NovaCore.getInstance().getVersionIndependentUtils().setColoredBlock(glassLocation.getBlock(), DyeColor.RED, ColoredBlockType.GLASS_BLOCK);
	}

	@Override
	public void run() {
		if (this.y <= this.location.getBlockY()) {
			cancelTask();

			Firework fw = (Firework) this.location.getWorld().spawnEntity(this.location, EntityType.FIREWORK);
			FireworkMeta fwm = fw.getFireworkMeta();

			fwm.setPower(1);
			fwm.addEffect(FireworkEffect.builder().with(Type.BALL_LARGE).withColor(Color.ORANGE).trail(true).flicker(true).build());

			fw.setFireworkMeta(fwm);

			Bukkit.getScheduler().scheduleSyncDelayedTask(NovaCore.getInstance(), new Runnable() {
				@Override
				public void run() {
					fw.detonate();
				}
			}, 2L);

			animationCompleted();
			return;
		}

		Location fireworkLocation = new Location(this.location.getWorld(), this.location.getX(), this.y, this.location.getZ());

		Firework fw = (Firework) fireworkLocation.getWorld().spawnEntity(fireworkLocation, EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();

		fwm.setPower(1);
		fwm.addEffect(FireworkEffect.builder().withColor(Color.RED).trail(true).build());

		fw.setFireworkMeta(fwm);

		Bukkit.getScheduler().scheduleSyncDelayedTask(NovaCore.getInstance(), new Runnable() {
			@Override
			public void run() {
				fw.detonate();
			}
		}, 2L);

		y -= 1;
	}

	public void cancelTask() {
		if (taskId != -1) {
			Bukkit.getScheduler().cancelTask(taskId);
			taskId = -1;
		}
	}

	public void stop() {
		Bukkit.getScheduler().cancelTask(taskId);
		undoBlocks();
		completed = true;
	}

	public void undoBlocks() {
		for (Location l : removedBlocks.keySet()) {
			l.getBlock().setType(removedBlocks.get(l));
		}
		removedBlocks.clear();

		MedicalSupplyDropManager.getInstance().spawnChest(location, lootTable);
		VersionIndependentSound.ANVIL_LAND.playAtLocation(location, 1F, 1F);
	}

	private void animationCompleted() {
		undoBlocks();
		completed = true;
	}

	public boolean isCompleted() {
		return completed;
	}

	public Location getLocation() {
		return location;
	}

	public World getWorld() {
		return location.getWorld();
	}

	public HashMap<Location, Material> getRemovedBlocks() {
		return removedBlocks;
	}
}