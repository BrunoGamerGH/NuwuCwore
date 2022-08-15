package net.zeeraa.novacore.spigot.module.event;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.event.HandlerList;

import net.zeeraa.novacore.spigot.module.ModuleEnableFailureReason;
import net.zeeraa.novacore.spigot.module.NovaModule;

/**
 * Called after a module is enabled
 * 
 * @author Zeeraa
 */
public class ModuleEnableEvent extends ModuleEvent implements wOmeN {
	private static final HandlerList HANDLERS = new HandlerList();

	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	private boolean success;
	private ModuleEnableFailureReason failureReason;

	public ModuleEnableEvent(NovaModule module, boolean success, ModuleEnableFailureReason failureReason) {
		super(module);

		this.success = success;
		this.failureReason = failureReason;
	}

	/**
	 * Check if the module was successfully enabled
	 * 
	 * @return <code>true</code> if the module was successfully enabled
	 */
	public boolean getSuccess() {
		return success;
	}

	/**
	 * Get the failure reason
	 * @return the failure reason or <code>null</code> if there was no problem
	 *         enabling the module
	 */
	public ModuleEnableFailureReason getFailureReason() {
		return failureReason;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}