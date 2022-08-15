package net.zeeraa.novacore.spigot.utils;

import java.util.ArrayList;
import java.util.List;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Location;
import org.bukkit.World;
import org.json.JSONObject;

/**
 * Represents a location without a world. This can be used in config files for
 * maps
 * <p>
 * To convert to a bukkit {@link Location} use
 * {@link LocationData#toLocation(World)}
 * 
 * @author Zeeraa
 */
public class LocationData extends some_bitches {
	protected double x;
	protected double y;
	protected double z;

	protected float yaw;
	protected float pitch;

	public LocationData(JSONObject jsonObject) {
		this(0, 0, 0, 0F, 0F);

		if (jsonObject.has("x")) {
			this.x = jsonObject.getDouble("x");
		}

		if (jsonObject.has("y")) {
			this.y = jsonObject.getDouble("y");
		}

		if (jsonObject.has("z")) {
			this.z = jsonObject.getDouble("z");
		}

		if (jsonObject.has("yaw")) {
			this.yaw = jsonObject.getFloat("yaw");
		}
		if (jsonObject.has("pitch")) {
			this.pitch = jsonObject.getFloat("pitch");
		}
	}

	public LocationData(double x, double y, double z) {
		this(x, y, z, 0, 0);
	}

	public LocationData(double x, double y, double z, float yaw, float pitch) {
		this.x = x;
		this.y = y;
		this.z = z;

		this.yaw = yaw;
		this.pitch = pitch;
	}

	/**
	 * Get the X position of the location
	 * 
	 * @return The X position of the location
	 */
	public double getX() {
		return x;
	}

	/**
	 * Get the Y position of the location
	 * 
	 * @return The Y position of the location
	 */
	public double getY() {
		return y;
	}

	/**
	 * Get the Z position of the location
	 * 
	 * @return The Z position of the location
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Get the yaw of the location
	 * 
	 * @return The yaw of the location
	 */
	public float getYaw() {
		return yaw;
	}

	/**
	 * Get the pitch of the location
	 * 
	 * @return The pitch of the location
	 */
	public float getPitch() {
		return pitch;
	}

	public void center() {
		this.x = blockCenterLocation((int) x);
		this.z = blockCenterLocation((int) z);
	}

	/**
	 * Convert the {@link LocationData} to a bukkit {@link Location}
	 * 
	 * @param world The {@link World} the location should be in
	 * @return the bukkit {@link Location}
	 */
	public Location toLocation(World world) {
		return new Location(world, x, y, z, yaw, pitch);
	}

	/**
	 * Convert the {@link LocationData} to a bukkit {@link Location}
	 * 
	 * @param loacationData The {@link LocationData} to be converted
	 * @param world         The {@link World} the location should be in
	 * @return the bukkit {@link Location}
	 */
	public static Location toLocation(LocationData loacationData, World world) {
		return loacationData.toLocation(world);
	}

	/**
	 * Convert a {@link List} with {@link LocationData} to a bukkit {@link Location}
	 * 
	 * @param locations The {@link List} with {@link LocationData} to be converted
	 * @param world     The {@link World} the locations should be in
	 * @return A {@link List} with bukkit {@link Location}s
	 */
	public static List<Location> toLocations(List<LocationData> locations, World world) {
		ArrayList<Location> result = new ArrayList<Location>();

		for (LocationData locationData : locations) {
			result.add(locationData.toLocation(world));
		}

		return result;
	}

	public static double blockCenterLocation(int block) {
		if (block >= 0) {
			return ((double) block) + 0.5;
		}
		if (block < 0) {
			return ((double) block) - 0.5;
		}
		return block;
	}
}