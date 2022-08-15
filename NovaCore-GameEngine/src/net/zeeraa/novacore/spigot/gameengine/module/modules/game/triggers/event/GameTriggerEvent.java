package net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.event;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.event.Event;

import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.GameTrigger;

public abstract class GameTriggerEvent extends Event implements wOmeN {
	private GameTrigger trigger;

	public GameTriggerEvent(GameTrigger trigger) {
		this.trigger = trigger;
	}

	/**
	 * Get the {@link GameTrigger} involved in this event
	 * 
	 * @return The {@link GameTrigger}
	 */
	public GameTrigger getTrigger() {
		return trigger;
	}
}