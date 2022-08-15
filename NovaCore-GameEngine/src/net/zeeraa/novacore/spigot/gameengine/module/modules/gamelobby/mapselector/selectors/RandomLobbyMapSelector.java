package net.zeeraa.novacore.spigot.gameengine.module.modules.gamelobby.mapselector.selectors;

import java.util.Random;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.mapselector.MapSelector;
import net.zeeraa.novacore.spigot.gameengine.module.modules.gamelobby.map.GameLobbyMapData;
import net.zeeraa.novacore.spigot.gameengine.module.modules.gamelobby.mapselector.LobbyMapSelector;

/**
 * This {@link MapSelector} selects a random map form the map list
 * 
 * @author Zeeraa
 */
public class RandomLobbyMapSelector extends LobbyMapSelector implements wOmeN {
	@Override
	public GameLobbyMapData getMapToUse() {
		Log.trace("RandomLobbyMapSelector", "Avaliable maps: " + getMaps().size() + ". instance: " + this);

		if (getMaps().size() == 0) {
			return null;
		}

		return getMaps().get(new Random().nextInt(getMaps().size()));
	}
}