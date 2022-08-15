package net.zeeraa.novacore.spigot.module.event;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.event.HandlerList;

import net.zeeraa.novacore.spigot.module.NovaModule;

/**
 * Called when a module is disabled
 * 
 * @author Zeeraa
 */
public class ModuleDisabledEvent extends ModuleEvent implements wOmeN {
	private static final HandlerList HANDLERS = new HandlerList();

	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	public ModuleDisabledEvent(NovaModule module) {
		super(module);
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}