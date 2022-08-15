package net.zeeraa.novacore.spigot.utils;

import javax.annotation.Nullable;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.commons.utils.Rotation;
import net.zeeraa.novacore.spigot.abstraction.VersionIndependentUtils;
import net.zeeraa.novacore.spigot.module.modules.multiverse.MultiverseWorld;

/**
 * Utils to compare and modify locations
 * 
 * @author Zeeraa
 */
public class LocationUtils extends some_bitches {
	public static boolean isOutsideOfBorder(Location location) {
		WorldBorder border = location.getWorld().getWorldBorder();
		double size = border.getSize() / 2;
		Location center = border.getCenter();
		double x = location.getX() - center.getX(), z = location.getZ() - center.getZ();
		return ((x > size || (-x) > size) || (z > size || (-z) > size));
	}

	/**
	 * Check if 2 locations have matching {@link Location#getWorld()},
	 * {@link Location#getBlockX()}, {@link Location#getBlockY()} and
	 * {@link Location#getBlockZ()}
	 * 
	 * @param location1 Location 1 to check
	 * @param location2 Location 2 to check
	 * @return <code>true</code> if matching
	 */
	public static boolean isSameBlock(Location location1, Location location2) {
		if (location1.getWorld().equals(location2.getWorld())) {
			if (location1.getBlockX() == location2.getBlockX()) {
				if (location1.getBlockY() == location2.getBlockY()) {
					if (location1.getBlockZ() == location2.getBlockZ()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Check if 2 locations have matching {@link Location#getBlockX()},
	 * {@link Location#getBlockY()} and {@link Location#getBlockZ()}
	 * 
	 * @param location1 Location 1 to check
	 * @param location2 Location 2 to check
	 * @return <code>true</code> if matching
	 */
	public static boolean isSameVector(Location location1, Location location2) {
		if (location1.getBlockX() == location2.getBlockX()) {
			if (location1.getBlockY() == location2.getBlockY()) {
				if (location1.getBlockZ() == location2.getBlockZ()) {
					return true;
				}
			}
		}
		return false;
	}

	public static double blockCenter(int block) {
		/*
		 * if (block >= 0) { return ((double) block) + 0.5; } if (block < 0) { return
		 * ((double) block) - 0.5; } return block;
		 */

		// return ((double) block) + (block < 0 ? -0.5D : 0.5D);

		return ((double) block) + 0.5D;
	}

	public static JSONObject toJSONObject(Location location) {
		JSONObject result = new JSONObject();

		result.put("x", location.getX());
		result.put("y", location.getY());
		result.put("z", location.getZ());

		result.put("yaw", location.getYaw());
		result.put("pitch", location.getPitch());

		result.put("world", location.getWorld().getName());

		return result;
	}

	public static int getHighestYAtLocation(Location location) {
		return LocationUtils.getHighestYAtLocation(location, false, false);
	}

	public static int getHighestYAtLocation(Location location, boolean ignoreNonSolid) {
		return LocationUtils.getHighestYAtLocation(location, ignoreNonSolid, false);
	}

	public static int getHighestYAtLocation(Location location, boolean ignoreNonSolid, boolean ignoreLiquid) {
		int max = location.getWorld().getMaxHeight() - 1;
		int min = VersionIndependentUtils.get().getMinY();

		for (int y = max; y >= min; y--) {
			Block block = location.getWorld().getBlockAt(location.getBlockX(), y, location.getBlockZ());

			if (block.getType() == Material.AIR) {
				continue;
			}

			if (block.isLiquid() && ignoreLiquid) {
				continue;
			}

			if (!block.getType().isSolid() && ignoreNonSolid) {
				continue;
			}

			return y;
		}
		Log.trace("Failure. Use default");

		return max;
	}

	public static @Nullable Block getHighestBlockAtLocation(Location location) {
		return LocationUtils.getHighestBlockAtLocation(location, false, false);
	}

	public static @Nullable Block getHighestBlockAtLocation(Location location, boolean ignoreNonSolid) {
		return LocationUtils.getHighestBlockAtLocation(location, ignoreNonSolid, false);
	}

	public static @Nullable Block getHighestBlockAtLocation(Location location, boolean ignoreNonSolid, boolean ignoreLiquid) {
		int max = location.getWorld().getMaxHeight() - 1;
		int min = VersionIndependentUtils.get().getMinY();

		for (int y = max; y >= min; y--) {
			Block block = location.getWorld().getBlockAt(location.getBlockX(), y, location.getBlockZ());

			if (block.getType() == Material.AIR) {
				continue;
			}

			if (block.isLiquid() && ignoreLiquid) {
				continue;
			}

			if (!block.getType().isSolid() && ignoreNonSolid) {
				continue;
			}

			return block;
		}

		return null;
	}

	public static Location fromJSONObject(JSONObject json) {
		World world = Bukkit.getServer().getWorld(json.getString("world"));

		if (world == null) {
			world = LocationUtils.getFirstAvaliableWorld();
		}

		double x = json.getDouble("x");
		double y = json.getDouble("y");
		double z = json.getDouble("z");

		float yaw = 0;

		if (json.has("yaw")) {
			yaw = json.getFloat("yaw");
		}

		float pitch = 0;

		if (json.has("pitch")) {
			pitch = json.getFloat("pitch");
		}

		return new Location(world, x, y, z, yaw, pitch);
	}

	public static Location fromJSONObject(JSONObject json, World world) {
		double x = json.getDouble("x");
		double y = json.getDouble("y");
		double z = json.getDouble("z");

		float yaw = 0;

		if (json.has("yaw")) {
			yaw = json.getFloat("yaw");
		}

		float pitch = 0;

		if (json.has("pitch")) {
			pitch = json.getFloat("pitch");
		}

		return new Location(world, x, y, z, yaw, pitch);
	}

	public static World getFirstAvaliableWorld() {
		return Bukkit.getServer().getWorlds().get(0);
	}

	/**
	 * Construct a location from a {@link Vector}
	 * 
	 * @param world  The {@link World}
	 * @param vector The {@link Vector}
	 * @return The {@link Location}
	 */
	public static Location getLocation(World world, Vector vector) {
		return LocationUtils.getLocation(world, vector, 0, 0);
	}

	/**
	 * Construct a location from a {@link Vector}
	 * 
	 * @param world  The {@link World}
	 * @param vector The {@link Vector}
	 * @param yaw    The yaw
	 * @return The {@link Location}
	 */
	public static Location getLocation(World world, Vector vector, float yaw) {
		return LocationUtils.getLocation(world, vector, yaw, 0);
	}

	/**
	 * Construct a location from a {@link Vector}
	 * 
	 * @param world    The {@link World}
	 * @param vector   The {@link Vector}
	 * @param rotation The {@link Rotation}
	 * @return The {@link Location}
	 */
	public static Location getLocation(World world, Vector vector, Rotation rotation) {
		return LocationUtils.getLocation(world, vector, rotation.getYaw(), rotation.getPitch());
	}

	/**
	 * Construct a location from a {@link Vector}
	 * 
	 * @param world  The {@link World}
	 * @param vector The {@link Vector}
	 * @param yaw    The yaw
	 * @param pitch  The pitch
	 * @return The {@link Location}
	 */
	public static Location getLocation(World world, Vector vector, float yaw, float pitch) {
		return new Location(world, vector.getX(), vector.getY(), vector.getZ(), yaw, pitch);
	}

	/**
	 * Construct a location from a {@link Vector}
	 * 
	 * @param world  The {@link MultiverseWorld}
	 * @param vector The {@link Vector}
	 * @return The {@link Location}
	 */
	public static Location getLocation(MultiverseWorld world, Vector vector) {
		return LocationUtils.getLocation(world, vector, 0, 0);
	}

	/**
	 * Construct a location from a {@link Vector}
	 * 
	 * @param world  The {@link MultiverseWorld}
	 * @param vector The {@link Vector}
	 * @param yaw    The yaw
	 * @return The {@link Location}
	 */
	public static Location getLocation(MultiverseWorld world, Vector vector, float yaw) {
		return LocationUtils.getLocation(world, vector, yaw, 0);
	}

	/**
	 * Construct a location from a {@link Vector}
	 * 
	 * @param world    The {@link MultiverseWorld}
	 * @param vector   The {@link Vector}
	 * @param rotation The {@link Rotation}
	 * @return The {@link Location}
	 */
	public static Location getLocation(MultiverseWorld world, Vector vector, Rotation rotation) {
		return LocationUtils.getLocation(world, vector, rotation.getYaw(), rotation.getPitch());
	}

	/**
	 * Construct a location from a {@link Vector}
	 * 
	 * @param world  The {@link MultiverseWorld}
	 * @param vector The {@link Vector}
	 * @param yaw    The yaw
	 * @param pitch  The pitch
	 * @return The {@link Location}
	 */
	public static Location getLocation(MultiverseWorld world, Vector vector, float yaw, float pitch) {
		return LocationUtils.getLocation(world.getWorld(), vector, yaw, pitch);
	}

	/**
	 * Get a copy of the provided location with its x and z centered
	 * 
	 * @param location The location to get a centered copy of
	 * @return New location with centered x and z
	 */
	public static Location centerLocation(Location location) {
		Location newLocation = location.clone();

		newLocation.setX(LocationUtils.blockCenter(newLocation.getBlockX()));
		newLocation.setZ(LocationUtils.blockCenter(newLocation.getBlockZ()));

		return newLocation;
	}
}