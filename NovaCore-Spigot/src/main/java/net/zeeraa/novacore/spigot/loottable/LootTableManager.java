package net.zeeraa.novacore.spigot.loottable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.brunogamer.how.about.you.get.some_bitches;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;

/**
 * This class is used to load {@link LootTable}s
 * 
 * @author Zeeraa
 */
public class LootTableManager extends some_bitches {
	private Map<String, LootTableLoader> loaders;
	private Map<String, LootTable> lootTables;

	private static Random random = new Random();
	
	public static Random getRandom() {
		return random;
	}
	
	public static void setRandom(Random random) {
		LootTableManager.random = random;
	}
	
	public LootTableManager() {
		this.loaders = new HashMap<String, LootTableLoader>();
		this.lootTables = new HashMap<String, LootTable>();
	}

	/**
	 * Add a {@link LootTableLoader} to the loader list
	 * 
	 * @param loader The {@link LootTableLoader} to add
	 * @return <code>false</code> if the loader is already added to the list
	 */
	public boolean registerLoader(LootTableLoader loader) {
		if (loaders.containsKey(loader.getLoaderName().toLowerCase())) {
			return false;
		}

		loaders.put(loader.getLoaderName().toLowerCase(), loader);
		return true;
	}

	/**
	 * Check if a {@link LootTableLoader} has been added
	 * 
	 * @param name Name of the loader
	 * @return <code>true</code> if the loader has been added
	 */
	public boolean hasLoader(String name) {
		return loaders.containsKey(name.toLowerCase());
	}

	/**
	 * Add a {@link LootTableLoader} to be used when reading loot table JSON files
	 * 
	 * @param loader The {@link LootTableLoader} to add
	 * @return <code>true</code> on success, <code>false</code> if the loot table
	 *         loader with that name is already added
	 */
	public boolean addLoader(LootTableLoader loader) {
		if (hasLoader(loader.getLoaderName())) {
			return false;
		}

		Log.info("Adding loot table loader " + loader.getLoaderName());

		loaders.put(loader.getLoaderName().toLowerCase(), loader);

		return true;
	}

	/**
	 * Get a {@link LootTableLoader} by name
	 * 
	 * @param name Name of the loader
	 * @return instance of the {@link LootTableLoader} or <code>null</code> if not
	 *         found
	 */
	public LootTableLoader getLoader(String name) {
		return loaders.get(name.toLowerCase());
	}

	/**
	 * Check if a {@link LootTable} has been added
	 * 
	 * @param name Name of the loot table
	 * @return <code>true</code> if the loot table has been added
	 */
	public boolean hasLootTable(String name) {
		return lootTables.containsKey(name.toLowerCase());
	}

	/**
	 * Get a {@link LootTable} by name
	 * 
	 * @param name Name of the loot table
	 * @return instance of the {@link LootTable} or <code>null</code> if not found
	 */
	public LootTable getLootTable(String name) {
		return lootTables.get(name.toLowerCase());
	}

	/**
	 * Get a {@link Map} with all loaders with the name as key and the
	 * {@link LootTableLoader} as value
	 * 
	 * @return {@link Map} with all loot loaders
	 */
	public Map<String, LootTableLoader> getLoaders() {
		return loaders;
	}

	/**
	 * Get a {@link Map} with all loot tables with the name is key and
	 * {@link LootTable} as value
	 * 
	 * @return {@link Map} with all loot tables
	 */
	public Map<String, LootTable> getLootTables() {
		return lootTables;
	}

	/**
	 * Try to load all JSON files from a directory as a loot table
	 * 
	 * @param directory The direcorty to read loot tables from
	 */
	public void loadAll(File directory) {
		for (File file : directory.listFiles()) {
			if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("json")) {
				if (!file.isDirectory()) {
					this.loadLootTable(file);
				}
			}
		}
	}

	/**
	 * Create a {@link LootTable} from a {@link File} and add it to the loot table
	 * list
	 * 
	 * @param file The {@link File} to read
	 * @return Instance of the {@link LootTable} or <code>null</code> on failure
	 */
	public LootTable loadLootTable(File file) {
		try {
			Log.info("Reading loot table from file " + file.getName());

			String data = FileUtils.readFileToString(file, "UTF-8");

			LootTable lootTable = loadLootTable(new JSONObject(data));

			if (lootTable != null) {
				if (!hasLootTable(lootTable.getName())) {
					lootTables.put(lootTable.getName().toLowerCase(), lootTable);
					return lootTable;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a {@link LootTable} from a {@link JSONObject} and add it to the loot
	 * table list
	 * 
	 * @param json The {@link JSONObject} to read
	 * @return Instance of the {@link LootTable} or <code>null</code> on failure
	 */
	public LootTable loadLootTable(JSONObject json) {
		if (json.has("loader")) {
			String loader = json.getString("loader");

			if (hasLoader(loader)) {
				LootTable lootTable = getLoader(loader).read(json);

				if (lootTable != null) {
					if (lootTable.getName().contains(" ")) {
						throw new InvalidLootTableNameException("Loot table names cant contain spaces. Loot table that caused the issue: " + lootTable.getName());
					}
				}

				return lootTable;
			} else {
				Log.warn("No loot table loader named " + loader + " was found");
			}
		} else {
			Log.warn("Loot table json does not contain a loader");
		}
		return null;
	}
	
	/**
	 * Add a loot table
	 * <p>
	 * This will fail if a {@link LootTable} with that name already exists
	 * 
	 * @param lootTable The {@link LootTable} to add
	 * @return <code>true</code> on success
	 */
	public boolean addLootTable(LootTable lootTable) {
		if (lootTables.containsKey(lootTable.getName())) {
			return false;
		}
		
		if (lootTable.getName().contains(" ")) {
			throw new InvalidLootTableNameException("Loot table names cant contain spaces. Loot table that caused the issue: " + lootTable.getName());
		}

		lootTables.put(lootTable.getName(), lootTable);

		return true;
	}
}