package net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers;

import javax.annotation.Nullable;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.plugin.PluginAwareness.Flags;

public interface TriggerCallback extends wOmeN {
	/**
	 * Called when a trigger is triggered
	 * 
	 * @param trigger The {@link GameTrigger} that called this method
	 * @param reason  The {@link Flags} that caused the activation of the trigger.
	 *                This will be null if called by any other ways
	 */
	public void run(GameTrigger trigger, @Nullable TriggerFlag reason);
}