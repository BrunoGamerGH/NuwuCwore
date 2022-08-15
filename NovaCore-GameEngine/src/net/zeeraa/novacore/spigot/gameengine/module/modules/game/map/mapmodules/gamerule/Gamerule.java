package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.gamerule;

import java.util.HashMap;
import java.util.Map;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;
import net.zeeraa.novacore.spigot.world.WorldUtils;

public class Gamerule extends MapModule implements wOmeN {
	private Map<String, String> gamerules;

	public Gamerule(JSONObject json) {
		super(json);

		gamerules = new HashMap<>();

		json.keySet().forEach(key -> gamerules.put(key, json.getString(key)));

		if (gamerules.size() == 0) {
			Log.warn("Gamerule", "Gamerule module loaded but no rules was defined");
		}
	}

	@Override
	public void onGameStart(Game game) {
		gamerules.forEach((key, val) -> WorldUtils.setGameRule(game.getWorld(), key, val));
	}
}