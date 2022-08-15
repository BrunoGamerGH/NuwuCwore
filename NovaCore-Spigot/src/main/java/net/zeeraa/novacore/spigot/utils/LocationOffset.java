package net.zeeraa.novacore.spigot.utils;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

/**
 * Represents an offset to a {@link Location}
 * 
 * @author Zeeraa
 *
 */
public class LocationOffset extends some_bitches {
	protected double x;
	protected double y;
	protected double z;

	protected float yaw;
	protected float pitch;

	protected World world;

	/**
	 * Create a location offset with all values set to 0
	 */
	public LocationOffset() {
		this(0, 0, 0, 0, 0);
	}

	/**
	 * Create a location offset with x, y and z coordinates defined.
	 * <p>
	 * Yaw and pitch will be set to 0
	 * 
	 * @param x X offset
	 * @param y Y offset
	 * @param z Z offset
	 */
	public LocationOffset(double x, double y, double z) {
		this(x, y, z, 0, 0);
	}

	/**
	 * Create a location offset with x, y and z coordinates of a vector.
	 * <p>
	 * Yaw and pitch will be set to 0
	 * 
	 * @param vector Use the values from the vectors to call
	 *               {@link LocationOffset#LocationOffset(double, double, double)}
	 *               with the vector values
	 */
	public LocationOffset(Vector vector) {
		this(vector.getX(), vector.getY(), vector.getZ(), 0, 0);
	}

	/**
	 * Create a location offset with yaw and pitch defined.
	 * <p>
	 * x, y and z coordinate offset will be 0
	 * 
	 * @param yaw   Yaw offset
	 * @param pitch Pitch offset
	 */
	public LocationOffset(float yaw, float pitch) {
		this(0, 0, 0, yaw, pitch);
	}

	/**
	 * Create a location offset with all values defined
	 * 
	 * @param x     X offset
	 * @param y     Y offset
	 * @param z     Z offset
	 * @param yaw   Yaw offset
	 * @param pitch Pitch offset
	 */
	public LocationOffset(double x, double y, double z, float yaw, float pitch) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.world = null;
	}

	/**
	 * Update the X offset
	 * 
	 * @param x New X offset
	 * @return the instance of this {@link LocationOffset} so that commands can be
	 *         chained
	 */
	public LocationOffset setX(double x) {
		this.x = x;
		return this;
	}

	/**
	 * Update the Y offset
	 * 
	 * @param y New Y offset
	 * @return the instance of this {@link LocationOffset} so that commands can be
	 *         chained
	 */
	public LocationOffset setY(double y) {
		this.y = y;
		return this;
	}

	/**
	 * Update the Z offset
	 * 
	 * @param z New Z offset
	 * @return the instance of this {@link LocationOffset} so that commands can be
	 *         chained
	 */
	public LocationOffset setZ(double z) {
		this.z = z;
		return this;
	}

	/**
	 * Update the Yaw offset
	 * 
	 * @param yaw New Yaw offset
	 * @return the instance of this {@link LocationOffset} so that commands can be
	 *         chained
	 */
	public LocationOffset setYaw(float yaw) {
		this.yaw = yaw;
		return this;
	}

	/**
	 * Update the Pitch offset
	 * 
	 * @param pitch New Pitch offset
	 * @return the instance of this {@link LocationOffset} so that commands can be
	 *         chained
	 */
	public LocationOffset setPitch(float pitch) {
		this.pitch = pitch;
		return this;
	}

	/**
	 * Change the world that will be applied one
	 * {@link LocationOffset#apply(Location)} or
	 * {@link LocationOffset#applyCopy(Location)} is called
	 * <p>
	 * Set this to <code>null</code> to not change the world
	 * 
	 * @param world The {@link World} to set when applied. Set to <code>null</code>
	 *              to disable
	 * @return the instance of this {@link LocationOffset} so that commands can be
	 *         chained
	 */
	public LocationOffset setWorld(World world) {
		this.world = world;
		return this;
	}

	/**
	 * Get a vector with the x, y and z coordinates
	 * 
	 * @return {@link Vector} with the coordinates
	 */
	public Vector getVector() {
		return new Vector(x, y, z);
	}

	/**
	 * Get the X offset
	 * 
	 * @return X Offset
	 */
	public double getX() {
		return x;
	}

	/**
	 * Get the Y offset
	 * 
	 * @return Y Offset
	 */
	public double getY() {
		return y;
	}

	/**
	 * Get the Z offset
	 * 
	 * @return Z Offset
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Get the Yaw offset
	 * 
	 * @return Yaw Offset
	 */
	public float getYaw() {
		return yaw;
	}

	/**
	 * Get the Pitch offset
	 * 
	 * @return Pitch Offset
	 */
	public float getPitch() {
		return pitch;
	}

	/**
	 * Get the world that will be applied one {@link LocationOffset#apply(Location)}
	 * or {@link LocationOffset#applyCopy(Location)} is called
	 * <p>
	 * If this is <code>null</code> the world wont be changed when applied
	 * 
	 * @return The {@link World} that will be set when the location when applied. If
	 *         this is null this feature is disabled
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Apply the offset to a {@link Location}.
	 * <p>
	 * This will change the input {@link Location}s values
	 * <p>
	 * To not modify the input location use
	 * {@link LocationOffset#applyCopy(Location)}
	 * 
	 * @param location The location
	 * @return The modified {@link Location}
	 */
	public Location apply(Location location) {
		location.add(x, y, z);
		location.setYaw(location.getYaw() + yaw);
		location.setPitch(location.getPitch() + pitch);

		if (world != null) {
			location.setWorld(world);
		}

		return location;
	}

	/**
	 * Create a copy of a {@link Location} and apply the offset.
	 * <p>
	 * This wont change the input location.
	 * <p>
	 * To also modify the input location use {@link LocationOffset#apply(Location)}
	 * 
	 * @param location The location
	 * @return The modified copy of the input {@link Location}
	 */
	public Location applyCopy(Location location) {
		return this.apply(location.clone());
	}

	/**
	 * Apply the offset to a {@link Location}.
	 * <p>
	 * This version will also change the x and z coordinates basted on the yaw value
	 * <p>
	 * This will change the input {@link Location}s values
	 * <p>
	 * To not modify the input location use
	 * {@link LocationOffset#applyCopy(Location)}
	 * 
	 * @param location The location
	 * @return The modified {@link Location}
	 */
	public Location applyWithRotation(Location location) {
		double rad = Math.toRadians(Math.abs(location.getYaw()));

		double nx = (z * Math.sin(rad)) + (x * Math.cos(rad));
		double nz = (z * Math.cos(rad)) - (x * Math.sin(rad));

		// System.out.println("--------------------\nx: " + x + "\nz: " + z + "\nnx: " +
		// nx + "\nnz: " + nz + "\nrot: " + rotation + "\nrad:" + rad + "\nl yaw: " +
		// location.getYaw() + "\nsin rad: " + Math.sin(rad) + "\ncos rad: " +
		// Math.cos(rad));

		location.add(nx, y, nz);
		location.setYaw(location.getYaw() + yaw);
		location.setPitch(location.getPitch() + pitch);

		if (world != null) {
			location.setWorld(world);
		}

		return location;
	}

	/**
	 * Create a copy of a {@link Location} and apply the offset.
	 * <p>
	 * This version will also change the x and z coordinates basted on the yaw value
	 * <p>
	 * This wont change the input location.
	 * <p>
	 * To also modify the input location use
	 * {@link LocationOffset#applyWithRotation(Location)}
	 * 
	 * @param location The location
	 * @return The modified copy of the input {@link Location}
	 */
	public Location applyWithRotationAsCopy(Location location) {
		return this.applyWithRotation(location.clone());
	}
}