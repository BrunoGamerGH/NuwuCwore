package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.settime;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.json.JSONObject;

import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;

public class SetTime extends MapModule implements wOmeN {
	private int time;

	public SetTime(JSONObject json) {
		super(json);

		this.time = 6000;

		if (json.has("time")) {
			this.time = json.getInt("time");
		}
	}

	@Override
	public void onGameStart(Game game) {
		if (game.hasWorld()) {
			game.getWorld().setTime(time);
		}
	}
}