package net.zeeraa.novacore.spigot.gameengine.module.modules.game.mapselector.selectors;

import java.util.Random;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.GameMapData;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.mapselector.MapSelector;

/**
 * This {@link MapSelector} selects a random map form the map list
 * 
 * @author Zeeraa
 */
public class RandomMapSelector extends MapSelector implements wOmeN {
	@Override
	public GameMapData getMapToUse() {
		if (maps.size() == 0) {
			return null;
		}

		return maps.get(new Random().nextInt(maps.size()));
	}
}