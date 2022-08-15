package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.startmessage;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.json.JSONObject;

import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;

public class StartMessage extends MapModule implements wOmeN {
	private String message;

	public StartMessage(JSONObject json) {
		super(json);

		this.message = ChatColor.DARK_RED + "error: missing json value: message";

		if (json.has("message")) {
			this.message = json.getString("message");
		}
	}

	@Override
	public void onGameStart(Game game) {
		Bukkit.getServer().broadcastMessage(message);
	}
}