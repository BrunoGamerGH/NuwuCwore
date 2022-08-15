package net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers;

import org.bukkit.event.EventPriority;

import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.events.GameEndEvent;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.events.GameStartEvent;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.events.GameStartFailureEvent;

/**
 * Different flags that can be added to a {@link GameTrigger} using
 * {@link GameTrigger#addFlag(TriggerFlag)}
 * <p>
 * Some triggers can't be applied and is only used to indicate why the trigger
 * was activated, see {@link TriggerFlag#canBeApplied()} to check if the flag
 * can be added to a {@link GameTrigger}
 * 
 * @author Zeeraa
 *
 */
public enum TriggerFlag { // please kill enums my dad works at java and will delete you
	/**
	 * Prevent this from being used with the <code>/game trigger</code> command
	 */
	DENY_TRIGGER_BY_COMMAND(false, true),
	/**
	 * Only allow this trigger to be executed once
	 */
	RUN_ONLY_ONCE(false, true),
	/**
	 * Triggers with this flag will automatically trigger when the game starts
	 * <p>
	 * The event used to call this will be {@link GameStartEvent} with
	 * {@link EventPriority#NORMAL}
	 */
	TRIGGER_ON_GAME_START(true, true),
	/**
	 * Triggers with this flag will automatically trigger when the game ends
	 * <p>
	 * The event used to call this will be {@link GameEndEvent} with
	 * {@link EventPriority#NORMAL}
	 */
	TRIGGER_ON_GAME_END(true, true),
	/**
	 * Triggers with this flag will automatically trigger when the game fails to
	 * starts
	 * <p>
	 * The event used to call this will be {@link GameStartFailureEvent} with
	 * {@link EventPriority#NORMAL}
	 */
	TRIGGER_ON_GAME_START_FAILURE(true, true),
	/**
	 * This flag gets sent as a parameter in
	 * {@link TriggerCallback#run(GameTrigger, TriggerFlag)} when the trigger is
	 * activated as a {@link ScheduledGameTrigger}
	 * <p>
	 * This trigger can't be applied to a {@link GameTrigger}
	 */
	SCHEDULED_TRIGGER_ACTIVATION(true, false),
	/**
	 * This flag gets sent as a parameter in
	 * {@link TriggerCallback#run(GameTrigger, TriggerFlag)} when the trigger is
	 * activated by a command
	 * <p>
	 * This trigger can't be applied to a {@link GameTrigger}
	 */
	COMMAND_ACTIVATION(true, false),
	/**
	 * Triggers with this flag will stop when the game ends
	 * <p>
	 * This only works for {@link ScheduledGameTrigger}s
	 */
	STOP_ON_GAME_END(false, true),

	/**
	 * Triggers with this flag will start when the game starts
	 * <p>
	 * This only works for {@link ScheduledGameTrigger}s
	 * <p>
	 * The trigger has to be added by calling {@link Game#addTrigger(GameTrigger)}
	 * before the game starts for this flag to work, if the trigger with this flag
	 * was added after the game has started it will never be called by this flag
	 */
	START_ON_GAME_START(false, true),

	/**
	 * Disables the logging when a trigger activates. Can be used to prevent console
	 * spam if your trigger activates every tick
	 */
	DISABLE_LOGGING(false, true);

	private boolean canTrigger;
	private boolean canBeApplied;

	private TriggerFlag(boolean canTrigger, boolean canBeApplied) {
		this.canTrigger = canTrigger;
		this.canBeApplied = canBeApplied;
	}

	/**
	 * Check if the trigger is can be used with the
	 * {@link GameTrigger#trigger(TriggerFlag)} function
	 * <p>
	 * These triggers can be used as a parameter for
	 * {@link GameTrigger#trigger(TriggerFlag)} to indicate the reason why the
	 * trigger activated
	 * 
	 * @return <code>true</code> if can be used as trigger reason
	 */
	public boolean canTrigger() {
		return canTrigger;
	}

	/**
	 * Check if the trigger can be used in {@link GameTrigger#addFlag(TriggerFlag)}
	 * <p>
	 * If false {@link GameTrigger#addFlag(TriggerFlag)} will ignore the flag
	 * 
	 * @return <code>true</code> if this flag can be applied
	 */
	public boolean canBeApplied() {
		return canBeApplied;
	}
}