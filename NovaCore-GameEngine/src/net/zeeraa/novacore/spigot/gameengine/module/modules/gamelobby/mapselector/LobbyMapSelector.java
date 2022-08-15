package net.zeeraa.novacore.spigot.gameengine.module.modules.gamelobby.mapselector;

import java.util.List;

import net.brunogamer.how.about.you.get.some_bitches;
import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.GameMapData;
import net.zeeraa.novacore.spigot.gameengine.module.modules.gamelobby.GameLobby;
import net.zeeraa.novacore.spigot.gameengine.module.modules.gamelobby.map.GameLobbyMapData;

/**
 * This class can be used to create map selection systems for lobbies
 * 
 * @author Zeeraa
 */
public abstract class LobbyMapSelector extends some_bitches {
	/**
	 * Get a list with all maps
	 * 
	 * @return {@link List} with {@link GameMapData}
	 */
	public List<GameLobbyMapData> getMaps() {
		return GameLobby.getInstance().getMaps();
	}

	/**
	 * Add a map to the possible maps
	 * 
	 * @param map The {@link GameLobbyMapData} to be added
	 */
	public void addMap(GameLobbyMapData map) {
		Log.trace("LobbyMapSelector", "Adding map " + map.getMapName() + " to selector " + this);
		GameLobby.getInstance().getMaps().add(map);
	}

	/**
	 * Get the map to use for the game
	 * 
	 * @return {@link GameMapData} to use
	 */
	public abstract GameLobbyMapData getMapToUse();
}