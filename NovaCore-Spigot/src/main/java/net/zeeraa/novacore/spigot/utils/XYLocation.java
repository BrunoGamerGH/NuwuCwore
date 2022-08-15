package net.zeeraa.novacore.spigot.utils;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.json.JSONObject;

public class XYLocation extends some_bitches {
	private int x;
	private int y;

	public XYLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public XYLocation(JSONObject json) {
		this(json.getInt("x"), json.getInt("y"));
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();

		json.put("x", x);
		json.put("y", y);

		return json;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public static XYLocation fromJSON(JSONObject json) {
		return new XYLocation(json);
	}
	
	/**
	 * Create a {@link Location} from this instance. Note that the {@link XYLocation#y} will be turned into z of the location
	 * @param world The {@link World} to use
	 * @param y The y location to use
	 * @return {@link Location} with x and z of this object
	 */
	public Location toLocation(World world, double y) {
		return new Location(world, x, y, this.y);
	}
	
	public Chunk getChunk(World world) {
		return world.getChunkAt(this.toLocation(world, 0));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof XYLocation) {
			XYLocation location2 = (XYLocation) obj;

			return this.x == location2.x && this.y == location2.y;
		}

		return false;
	}

	@Override
	public String toString() {
		return "X: " + y + " Y: " + y;
	}
}