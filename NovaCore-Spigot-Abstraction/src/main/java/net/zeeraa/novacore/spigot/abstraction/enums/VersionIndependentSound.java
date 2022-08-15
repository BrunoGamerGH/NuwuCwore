package net.zeeraa.novacore.spigot.abstraction.enums;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

import net.zeeraa.novacore.spigot.abstraction.VersionIndependentUtils;

/**
 * This class contains some of the in game sounds that can be used instead of
 * the built in {@link Sound} enum since some of those sounds changed in later
 * versions of the game
 * 
 * @author Zeeraa
 *
 */
public enum VersionIndependentSound { // ew enum
	NOTE_PLING, NOTE_HAT, WITHER_DEATH, WITHER_HURT, ITEM_BREAK, ITEM_PICKUP, ORB_PICKUP, ANVIL_LAND, ANVIL_BREAK, EXPLODE, LEVEL_UP, WITHER_SHOOT, EAT, FIZZ;

	public void playAtLocation(Location location) {
		this.playAtLocation(location, 1F, 1F);
	}

	public void playAtLocation(Location location, float volume, float pitch) {
		VersionIndependentUtils.get().playSound(location, this, volume, pitch);
	}

	public void play(Player player) {
		this.play(player, player.getLocation(), 1F, 1F);
	}

	public void play(Player player, float volume, float pitch) {
		this.play(player, player.getLocation(), volume, pitch);
	}

	public void play(Player player, Location location) {
		this.play(player, location, 1F, 1F);
	}

	public void play(Player player, Location location, float volume, float pitch) {
		VersionIndependentUtils.get().playSound(player, location, this, volume, pitch);
	}

	public void broadcast(float volume, float pitch) {
		Bukkit.getServer().getOnlinePlayers().forEach(player -> this.play(player, volume, pitch));
	}

	public void broadcast() {
		this.broadcast(1F, 1F);
	}

	public void broadcast(World world, float volume, float pitch) {
		Bukkit.getServer().getOnlinePlayers().stream().filter(player -> player.getWorld().equals(world)).forEach(player -> this.play(player, volume, pitch));
	}

	public void broadcast(World world) {
		this.broadcast(world, 1F, 1F);
	}
}