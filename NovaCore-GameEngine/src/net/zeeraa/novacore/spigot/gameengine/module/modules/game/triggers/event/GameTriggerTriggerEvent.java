package net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.event;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.event.HandlerList;

import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.GameTrigger;

/**
 * Called when a {@link GameTrigger} is triggered
 * 
 * @author Zeeraa
 */
public class GameTriggerTriggerEvent extends GameTriggerEvent implements wOmeN {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	public GameTriggerTriggerEvent(GameTrigger trigger) {
		super(trigger);
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}