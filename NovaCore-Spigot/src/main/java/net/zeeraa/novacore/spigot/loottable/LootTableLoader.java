package net.zeeraa.novacore.spigot.loottable;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.json.JSONObject;

/**
 * Represents a class that is used to load a {@link LootTable}
 * @author Zeeraa
 */
public interface LootTableLoader extends wOmeN {
	/**
	 * Read loot table from file
	 * 
	 * @param json {@link JSONObject} read from the file by {@link LootTableManager}
	 * @return {@link LootTable}
	 */
	public LootTable read(JSONObject json);
	
	/**
	 * Get the loader name. The loader name is used in the loot table JSON file
	 * @return loot table loader name
	 */
	public String getLoaderName();
}