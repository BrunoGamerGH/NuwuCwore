package net.zeeraa.novacore.spigot.module.modules.multiverse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.module.NovaModule;
import net.zeeraa.novacore.spigot.module.modules.lootdrop.LootDropManager;

public class MultiverseManager extends NovaModule implements Listener, wOmeN {
	private Map<String, MultiverseWorld> worlds;

	private static MultiverseManager instance;

	public static MultiverseManager getInstance() {
		return instance;
	}

	@Override
	public void onLoad() {
		instance = this;
		this.worlds = new HashMap<>();
	}

	@Override
	public void onDisable() {
		try {
			this.unloadAll();
		} catch (Exception e) {
			Log.error(getName(), "An error occured while disabling. " + e.getClass().getName() + " " + e.getMessage());
			e.printStackTrace();
		}
	}

	public MultiverseManager() {
		super("NovaCore.MultiverseManager");
	}

	public Map<String, MultiverseWorld> getWorlds() {
		return worlds;
	}

	public MultiverseWorld createWorld(WorldOptions options) {
		WorldCreator worldCreator = new WorldCreator(options.getName());

		if (options.hasSeed()) {
			worldCreator.seed(options.getSeed());
		}

		worldCreator.environment(worldCreator.environment());

		worldCreator.generateStructures(options.isGenerateStructures());

		if (options.getGenerator() != null) {
			worldCreator.generator(options.getGenerator());
		}

		World world = worldCreator.createWorld();

		MultiverseWorld multiverseWorld = new MultiverseWorld(options.getName(), world, options.getUnloadOption(), options.getPlayerUnloadOption(), options.isSaveOnUnload(), options.isLockWeather());

		worlds.put(options.getName(), multiverseWorld);

		return multiverseWorld;
	}

	/**
	 * Load a world from a file
	 * <p>
	 * This will call
	 * {@link MultiverseManager#createFromFile(File, String, WorldUnloadOption, boolean)}
	 * with the file name as the world name and with deleteUidFile as true
	 * 
	 * @param worldFile    The world {@link File}
	 * @param unloadOption The {@link WorldUnloadOption} to use
	 * @return The {@link MultiverseWorld} that was loaded
	 * @throws IOException           An {@link IOException} can occur due to the
	 *                               world already existing, failing to unload the
	 *                               world or failing to copy the world file
	 * @throws FileNotFoundException A {@link FileNotFoundException} if the world
	 *                               file does not exist
	 */
	public MultiverseWorld createFromFile(File worldFile, WorldUnloadOption unloadOption) throws IOException {
		String worldName = worldFile.getName();

		return this.createFromFile(worldFile, worldName, unloadOption, true);
	}

	/**
	 * Load a world from a file
	 * <p>
	 * This will call
	 * {@link MultiverseManager#createFromFile(File, String, WorldUnloadOption, boolean)}
	 * with the file name as the world name
	 * 
	 * @param worldFile     The world {@link File}
	 * @param unloadOption  The {@link WorldUnloadOption} to use
	 * @param deleteUidFile <code>true</code> to delete the <code>uid.dat</code>
	 *                      file
	 * @return The {@link MultiverseWorld} that was loaded
	 * @throws IOException           An {@link IOException} can occur due to the
	 *                               world already existing, failing to unload the
	 *                               world or failing to copy the world file
	 * @throws FileNotFoundException A {@link FileNotFoundException} if the world
	 *                               file does not exist
	 */
	public MultiverseWorld createFromFile(File worldFile, WorldUnloadOption unloadOption, boolean deleteUidFile) throws IOException {
		String worldName = worldFile.getName();

		return this.createFromFile(worldFile, worldName, unloadOption, deleteUidFile);
	}

	/**
	 * Load a world from a file
	 * <p>
	 * This will call
	 * {@link MultiverseManager#createFromFile(File, String, WorldUnloadOption, boolean)}
	 * with deleteUidFile as true
	 * 
	 * @param worldFile    The world {@link File}
	 * @param name         The name the world should be loaded as
	 * @param unloadOption The {@link WorldUnloadOption} to use
	 * @return The {@link MultiverseWorld} that was loaded
	 * @throws IOException           An {@link IOException} can occur due to the
	 *                               world already existing, failing to unload the
	 *                               world or failing to copy the world file
	 * @throws FileNotFoundException A {@link FileNotFoundException} if the world
	 *                               file does not exist
	 */
	public MultiverseWorld createFromFile(File worldFile, String name, WorldUnloadOption unloadOption) throws IOException {
		return this.createFromFile(worldFile, name, unloadOption, true);
	}

	/**
	 * Load a world from a file
	 * 
	 * @param worldFile     The world {@link File}
	 * @param name          The name the world should be loaded as
	 * @param unloadOption  The {@link WorldUnloadOption} to use
	 * @param deleteUidFile <code>true</code> to delete the <code>uid.dat</code>
	 *                      file
	 * @return The {@link MultiverseWorld} that was loaded
	 * @throws IOException           An {@link IOException} can occur due to the
	 *                               world already existing, failing to unload the
	 *                               world or failing to copy the world file
	 * @throws FileNotFoundException A {@link FileNotFoundException} if the world
	 *                               file does not exist
	 */
	public MultiverseWorld createFromFile(File worldFile, String name, WorldUnloadOption unloadOption, boolean deleteUidFile) throws IOException {
		if (!worldFile.exists()) {
			throw new FileNotFoundException("Could not find world file: " + worldFile.getPath());
		}

		File worldContainer = Bukkit.getServer().getWorldContainer();

		File targetFile = Paths.get(worldContainer.getAbsolutePath() + File.separator + name).toFile();
		if (targetFile.exists()) {
			Log.info("Multiverse", "Replacing old world " + name);
			targetFile.delete();
			FileUtils.deleteDirectory(targetFile);
		}

		Log.info("Multiverse", "Adding " + name + " to " + worldContainer.getAbsolutePath());
		FileUtils.copyDirectory(worldFile, targetFile);

		if (deleteUidFile) {
			File uidFile = new File(targetFile.getAbsolutePath() + File.separator + "uid.dat");

			if (uidFile.exists()) {
				Log.debug("Multiverse", "Deleting uid file " + uidFile.getPath());
				FileUtils.forceDelete(uidFile);
			}
		}

		World world = Bukkit.getServer().createWorld(new WorldCreator(name));

		MultiverseWorld multiverseWorld = new MultiverseWorld(name, world, unloadOption, PlayerUnloadOption.KICK, true, false);

		worlds.put(multiverseWorld.getName(), multiverseWorld);

		return multiverseWorld;
	}

	public MultiverseWorld cloneWorld(World world, String name, WorldUnloadOption unloadOption) {
		WorldCreator worldCreator = new WorldCreator(name);

		worldCreator.copy(world);

		World world2 = worldCreator.createWorld();

		MultiverseWorld multiverseWorld = new MultiverseWorld(name, world2, unloadOption, PlayerUnloadOption.KICK, true, false);

		worlds.put(name, multiverseWorld);

		return multiverseWorld;
	}

	public MultiverseWorld getWorld(String name) {
		return worlds.get(name);
	}

	public MultiverseWorld getWorld(World world) {
		return worlds.get(world.getName());
	}

	public boolean hasWorld(World world) {
		return hasWorld(world.getName());
	}

	public boolean hasWorld(String name) {
		return worlds.containsKey(name);
	}

	public boolean unload(String name) {
		if (worlds.containsKey(name)) {
			return unload(worlds.get(name));
		}

		return false;
	}

	public boolean unload(MultiverseWorld multiverseWorld) {
		String worldName = multiverseWorld.getWorld().getName();
		Log.info("Multiverse", "Unloading world " + worldName);

		if (LootDropManager.getInstance().isEnabled()) {
			LootDropManager.getInstance().removeFromWorld(multiverseWorld.getWorld());
		}

		for (Player player : multiverseWorld.getWorld().getPlayers()) {
			switch (multiverseWorld.getPlayerUnloadOptions()) {
			case KICK:
				player.kickPlayer("Unloading world");
				break;

			case SEND_TO_FIRST:
				boolean failed = true;

				for (World world : Bukkit.getServer().getWorlds()) {
					if (!world.getUID().toString().equalsIgnoreCase(multiverseWorld.getWorld().getUID().toString())) {
						player.teleport(world.getSpawnLocation());
						failed = false;
						break;
					}
				}

				if (failed) {
					player.kickPlayer("Unloading world\nCould not find a fallback world");
				}
				break;

			case DO_NOTHING:
				break;

			default:
				player.kickPlayer("Unloading world.\nA server error has occured: ERR:BAD_PLAYER_UNLOAD_OPTION");
				break;
			}
		}

		Bukkit.getServer().unloadWorld(multiverseWorld.getWorld(), multiverseWorld.isSaveOnUnload());
		worlds.remove(multiverseWorld.getName());

		if (multiverseWorld.getUnloadOption() == WorldUnloadOption.DELETE) {
			Log.info("Multiverse", "Deleting unloaded world " + worldName);
			try {
				FileUtils.deleteDirectory(new File(Bukkit.getServer().getWorldContainer().getPath(), worldName));
			} catch (IOException e) {
				Log.error("Multiverse", "IOException while trying to delete world " + worldName);
				e.printStackTrace();
			}
		}
		return true;
	}

	public void unloadAll() {
		while (worlds.size() > 0) {
			String key = worlds.keySet().iterator().next();
			this.unload(worlds.get(key));
		}
	}

	@EventHandler
	public void onWatherChage(WeatherChangeEvent e) {
		if (this.hasWorld(e.getWorld())) {
			if (this.getWorld(e.getWorld()).isLockWeather()) {
				Log.debug("Multiverse", "Prevented weather change in world " + e.getWorld().getName());
				e.setCancelled(true);
			}
		}
	}
}