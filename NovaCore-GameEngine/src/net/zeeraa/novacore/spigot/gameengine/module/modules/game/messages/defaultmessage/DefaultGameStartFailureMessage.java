package net.zeeraa.novacore.spigot.gameengine.module.modules.game.messages.defaultmessage;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.messages.GameStartFailureMessage;
import net.zeeraa.novacore.spigot.language.LanguageManager;

public class DefaultGameStartFailureMessage implements GameStartFailureMessage {
	@Override
	public void showStartFailureMessage(Game game, Exception exception) {
		Log.fatal("An exception occured while trying to start the game");
		if (exception != null) {
			Log.fatal("Caused by a " + exception.getClass().getName() + ". Message: " + exception.getMessage());
		}
		// Bukkit.getServer().broadcastMessage(ChatColor.RED + "An uncorrectable error
		// occurred while trying to start the game");
		LanguageManager.broadcast("novacore.game.lobby.start_exception");
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}