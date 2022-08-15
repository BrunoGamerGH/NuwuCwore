package net.zeeraa.novacore.spigot.module.modules.jumppad;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.json.JSONObject;

import net.zeeraa.novacore.spigot.utils.LocationUtils;
import net.zeeraa.novacore.spigot.utils.VectorUtils;

public class JumpPad extends some_bitches {
	private Location location;
	private Vector velocity;
	private double radius;
	private Plugin owner;
	
	public JumpPad(Location location, Vector velocity, double radius, Plugin owner) {
		this.location = location;
		this.velocity = velocity;
		this.radius = radius;
		this.owner = owner;
	}

	public Location getLocation() {
		return location;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public double getRadius() {
		return radius;
	}
	
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	public Plugin getOwner() {
		return owner;
	}
	
	public boolean isInRange(LivingEntity entity) {
		if (location.getWorld().equals(entity.getWorld())) {
			if (entity.getLocation().getBlockY() == location.getBlockY()) {
				double x = location.getBlockX() - entity.getLocation().getX();
				double z = location.getBlockZ() - entity.getLocation().getZ();

				double entityDistance = Math.hypot(x, z);

				if (entityDistance <= radius) {
					return true;
				}
			}
		}

		return false;
	}

	public JSONObject toJson() {
		JSONObject json = new JSONObject();

		json.put("location", LocationUtils.toJSONObject(location));
		json.put("velocity", VectorUtils.toJSONObject(velocity));
		json.put("radius", radius);

		return json;
	}

	public static JumpPad fromJson(JSONObject json, Plugin owner) {
		Location location = LocationUtils.fromJSONObject(json.getJSONObject("location"));
		Vector velocity = VectorUtils.fromJSONObject(json.getJSONObject("velocity"));
		double radius = json.getDouble("radius");

		return new JumpPad(location, velocity, radius, owner);
	}
}