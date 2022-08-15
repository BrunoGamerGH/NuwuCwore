package net.zeeraa.novacore.spigot.module.modules.compass;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Location;

/**
 * Used to indicate the target of a compass in the {@link CompassTracker} module
 * @author Zeeraa
 */
public class CompassTarget extends some_bitches {
	private Location targetLocation;
	private String trackingMessage;
	
	public CompassTarget(Location targetLocation, String trackingMessage) {
		this.targetLocation = targetLocation;
		this.trackingMessage = trackingMessage;
	}
	
	public Location getTargetLocation() {
		return targetLocation;
	}
	
	public String getTrackingMessage() {
		return trackingMessage;
	}
}