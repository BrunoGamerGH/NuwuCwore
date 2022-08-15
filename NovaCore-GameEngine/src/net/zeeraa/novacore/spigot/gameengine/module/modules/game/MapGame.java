package net.zeeraa.novacore.spigot.gameengine.module.modules.game;

import java.io.IOException;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.events.GameStartEvent;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.GameMap;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.GameMapData;

/**
 * This is the same as {@link Game} but this type of game uses pre made maps for
 * games
 * <p>
 * If this extends {@link Listener} {@link GameManager#loadGame(Game)} will
 * register the listener when called
 * <p>
 * When creating games remember to call {@link Game#sendBeginEvent()} as soon as
 * the game begins and the count downs have finished
 * 
 * @author Zeeraa
 */
public abstract class MapGame extends Game implements wOmeN {
	private GameMap activeMap;

	public MapGame(Plugin plugin) {
		super(plugin);

		this.activeMap = null;
	}

	/**
	 * Get the loaded map.
	 * <p>
	 * You can check if a map has been loaded using {@link MapGame#hasActiveMap()}
	 * 
	 * @return The loaded {@link GameMap} or null if no map has been loaded
	 */
	public GameMap getActiveMap() {
		return activeMap;
	}

	/**
	 * Check if a map has been loaded
	 * 
	 * @return <code>true</code> if a map has been loaded
	 */
	public boolean hasActiveMap() {
		return activeMap != null;
	}

	/**
	 * Load a map
	 * 
	 * @param mapData The {@link GameMapData} to load
	 * @return <code>true</code> on success. <code>false</code> the a map has
	 *         already been loaded
	 * @throws IOException if and {@link IOException} occurs while loading the world
	 */
	public boolean loadMap(GameMapData mapData) throws IOException {
		if (hasActiveMap()) {
			return false;
		}

		GameMap map = mapData.load();

		this.activeMap = map;
		this.world = map.getWorld();

		map.getMapData().getMapModules().forEach(module -> module.onMapLoad(map));

		return true;
	}

	/**
	 * Start the game
	 * 
	 * @return <code>false</code> if this has already been called
	 */
	@Override
	public boolean startGame() {
		if (startCalled) {
			return false;
		}
		startCalled = true;

		Bukkit.getServer().getPluginManager().callEvent(new GameStartEvent(this));

		activeMap.getMapData().getMapModules().forEach(module -> {
			Log.trace("MapGame", "Calling onGameStart() for map module " + module.getName());
			module.onGameStart(this);
		});

		if (autoEndGame()) {
			winCheckTask.start();
		}

		this.onStart();

		return true;
	}
}