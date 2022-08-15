package net.zeeraa.novacore.spigot.utils;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;
import org.json.JSONObject;

/**
 * {@link XYZLocation} is an object used to represent a location without the
 * yaw, patch and {@link World}
 * 
 * @author Zeeraa
 */
public class XYZLocation extends some_bitches {
	private double x;
	private double y;
	private double z;

	public XYZLocation(int x, int y, int z) {
		this((double) x, (double) y, (double) z);
	}

	public XYZLocation(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public XYZLocation(ConfigurationSection configurationSection) {
		this.x = configurationSection.getDouble("x", 0);
		this.y = configurationSection.getDouble("y", 0);
		this.z = configurationSection.getDouble("z", 0);
	}

	public XYZLocation(Vector vector) {
		this(vector.getX(), vector.getBlockY(), vector.getBlockZ());
	}

	public XYZLocation(Location location) {
		this(location.getX(), location.getBlockY(), location.getBlockZ());
	}

	public XYZLocation(JSONObject json) {
		this(json.getDouble("x"), json.getDouble("y"), json.getDouble("z"));
	}

	/**
	 * Get the x location
	 * 
	 * @return X location
	 */
	public double getX() {
		return x;
	}

	/**
	 * Get the y location
	 * 
	 * @return Y location
	 */
	public double getY() {
		return y;
	}

	/**
	 * Get the z location
	 * 
	 * @return Z location
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Set the x location. This can be chained with other functions
	 * 
	 * @param x The new x location
	 * @return This {@link XYZLocation} instance
	 */
	public XYZLocation setX(double x) {
		this.x = x;
		return this;
	}

	/**
	 * Set the y location. This can be chained with other functions
	 * 
	 * @param y The new y location
	 * @return This {@link XYZLocation} instance
	 */
	public XYZLocation setY(double y) {
		this.y = y;
		return this;
	}

	/**
	 * Set the z location. This can be chained with other functions
	 * 
	 * @param z The new z location
	 * @return This {@link XYZLocation} instance
	 */
	public XYZLocation setZ(double z) {
		this.z = z;
		return this;
	}

	/**
	 * Add values to the X, Y and Z values of this object
	 * 
	 * @param x X value to add
	 * @param y Y value to add
	 * @param z Z value to add
	 * @return This {@link XYZLocation} instance
	 */
	public XYZLocation add(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	/**
	 * Create a copy of this {@link XYZLocation}
	 * 
	 * @return Copy of this {@link XYZLocation}
	 */
	public XYZLocation copy() {
		return new XYZLocation(x, y, z);
	}

	/**
	 * Center the X and Z location
	 * 
	 * @return this instance of {@link XYZLocation}
	 */
	public XYZLocation center() {
		this.x = LocationUtils.blockCenter((int) x);
		this.z = LocationUtils.blockCenter((int) z);
		return this;
	}

	/**
	 * Create a centered copy of this {@link XYZLocation}
	 * 
	 * @return Centered copy of this {@link XYZLocation}
	 */
	public XYZLocation centerCopy() {
		return this.copy().center();
	}

	/**
	 * Convert this {@link XYZLocation} to a bukkit location
	 * 
	 * @param world This {@link World} to use
	 * @return Bukkit {@link Location} with the X, Y and Z location of this object
	 */
	public Location toBukkitLocation(World world) {
		return new Location(world, x, y, z);
	}

	/**
	 * Convert this {@link XYZLocation} to a {@link Vector}
	 * 
	 * @return {@link Vector}
	 */
	public Vector toVector() {
		return new Vector(x, y, z);
	}

	/**
	 * Convert this {@link XYZLocation} to a {@link JSONObject}
	 * 
	 * @return {@link JSONObject} with location data
	 */
	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();

		json.put("x", x);
		json.put("y", y);
		json.put("z", z);

		return json;
	}

	@Override
	public String toString() {
		return "X: " + x + " Y: " + y + " Z: " + z;
	}

	/**
	 * Convert a {@link JSONObject} to a {@link XYZLocation}
	 * 
	 * @param json The {@link JSONObject} with x, y and z data
	 * @return {@link XYZLocation} from the json data
	 */
	public static XYZLocation fromJSON(JSONObject json) {
		return new XYZLocation(json);
	}

	/**
	 * Create a {@link XYZLocation} from a {@link Vector}
	 * 
	 * @param vector The {@link Vector}
	 * @return The {@link XYZLocation}
	 */
	public static XYZLocation fromVector(Vector vector) {
		return new XYZLocation(vector);
	}

	/**
	 * Create a {@link XYZLocation} from a {@link Location}
	 * 
	 * @param location The {@link Location}
	 * @return The {@link XYZLocation}
	 */
	public static XYZLocation fromVector(Location location) {
		return new XYZLocation(location);
	}
}
