package net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.event.GameTriggerTriggerEvent;

/**
 * Game triggers can be used to make certain events in games that can be
 * triggered by either a timer, a command or by code
 * 
 * @author Zeeraa
 *
 */
public class GameTrigger extends some_bitches {
	private String name;
	private int triggerCount;

	private List<TriggerCallback> callbacks;
	private List<TriggerFlag> flags;

	public GameTrigger(String name) {
		this(name, null);
	}

	public GameTrigger(String name, @Nullable TriggerCallback callback) {
		this.name = name;

		this.triggerCount = 0;

		this.callbacks = new ArrayList<TriggerCallback>();
		this.flags = new ArrayList<TriggerFlag>();

		if (callback != null) {
			this.callbacks.add(callback);
		}
	}

	/**
	 * Get a list with all trigger flags
	 * 
	 * @return List with trigger flags
	 */
	public List<TriggerFlag> getFlags() {
		return flags;
	}

	/**
	 * Check if the trigger has a flag
	 * 
	 * @param flag The {@link TriggerFlag} to check for
	 * @return <code>true</code> if the trigger has the flag
	 */
	public boolean hasFlag(TriggerFlag flag) {
		return flags.contains(flag);
	}

	/**
	 * Add a flag to this trigger
	 * <p>
	 * If the flag already exists it wont be added again
	 * <p>
	 * This wont add the trigger if {@link TriggerFlag#canBeApplied()} is
	 * <code>false</code>
	 * 
	 * @param flag The flag to add
	 * @return this instance so the commands can be chained
	 */
	public GameTrigger addFlag(TriggerFlag flag) {
		if (!flag.canBeApplied()) {
			return this;
		}

		if (!hasFlag(flag)) {
			flags.add(flag);
		}
		return this;
	}

	/**
	 * Add a callback to the trigger
	 * 
	 * @param callback The {@link TriggerCallback} to add
	 * @return this instance so the commands can be chained
	 */
	public GameTrigger addCallback(TriggerCallback callback) {
		this.callbacks.add(callback);
		return this;
	}

	/**
	 * Activate the trigger
	 * <p>
	 * This will return <code>false</code> and not activate the trigger the trigger
	 * has the flag {@link TriggerFlag#RUN_ONLY_ONCE}
	 * 
	 * @return {@link TriggerResponse}
	 */
	public TriggerResponse trigger() {
		return this.trigger(null);
	}

	/**
	 * Activate the trigger
	 * <p>
	 * This will return <code>false</code> and not activate the trigger the trigger
	 * has the flag {@link TriggerFlag#RUN_ONLY_ONCE}
	 * 
	 * @param flag The {@link TriggerFlag} that caused the trigger to activate. If
	 *             the {@link TriggerFlag} is not an auto trigger this will fail
	 * 
	 * @return {@link TriggerResponse}
	 */
	public TriggerResponse trigger(TriggerFlag flag) {
		if (hasFlag(TriggerFlag.RUN_ONLY_ONCE) && this.hasBeenCalled()) {
			return new TriggerResponse(this, false);
		}

		if (flag != null) {
			if (!flag.canTrigger()) {
				Log.warn("GameTrigger", "Tried to execute trigger " + name + " with non trigger reason flag " + flag.name());
				return new TriggerResponse(this, false);
			}
		}

		this.triggerCount++;

		if (!this.hasFlag(TriggerFlag.DISABLE_LOGGING)) {
			Log.trace("GameTrigger", "Trigger: " + name + " was triggered with flag " + (flag == null ? "null" : flag.name()) + ". Times triggered: " + triggerCount);
		}

		GameTriggerTriggerEvent event = new GameTriggerTriggerEvent(this);
		Bukkit.getServer().getPluginManager().callEvent(event);

		callbacks.forEach(callback -> callback.run(this, flag));

		if (this instanceof ScheduledGameTrigger) {
			if (this.hasFlag(TriggerFlag.RUN_ONLY_ONCE)) {
				((ScheduledGameTrigger) this).stop();
			}
		}

		return new TriggerResponse(this, true);
	}

	/**
	 * Check if the name is valid
	 * <p>
	 * Valid names can not contain spaces
	 * 
	 * @return <code>true</code> if the name is valid
	 */
	public final boolean hasValidName() {
		return (!this.getName().contains(" "));
	}

	/**
	 * Get the name of the trigger
	 * 
	 * @return Name of the trigger
	 */
	public String getName() {
		return name;
	}

	/**
	 * Check if the trigger has been called
	 * 
	 * @return <code>true</code> if the trigger has been called
	 */
	public boolean hasBeenCalled() {
		return this.triggerCount > 0;
	}

	/**
	 * Get the amount of times this trigger has been called
	 * 
	 * @return amount of times the trigger has been called
	 */
	public int getTriggerCount() {
		return triggerCount;
	}

	/**
	 * Try to activate all triggers in a list
	 * 
	 * @param triggers The list of triggers
	 */
	public static void triggerMany(List<GameTrigger> triggers) {
		triggers.forEach(t -> t.trigger());
	}

	/**
	 * Try to activate all triggers in a list
	 * 
	 * @param triggers The list of triggers
	 * @param reason   The {@link TriggerFlag} that activated the trigger
	 */
	public static void triggerMany(List<GameTrigger> triggers, TriggerFlag reason) {
		triggers.forEach(t -> t.trigger(reason));
	}

	/**
	 * Try to start all triggers in a list
	 * <p>
	 * This will ignore all triggers that is not of type
	 * {@link ScheduledGameTrigger}
	 * 
	 * @param triggers The list of triggers
	 */
	public static void startMany(List<GameTrigger> triggers) {
		triggers.forEach(trigger -> {
			if (trigger instanceof ScheduledGameTrigger) {
				((ScheduledGameTrigger) trigger).start();
			}
		});
	}

	/**
	 * Try to stop all triggers in a list
	 * <p>
	 * This will ignore all triggers that is not of type
	 * {@link ScheduledGameTrigger}
	 * 
	 * @param triggers The list of triggers
	 */
	public static void stopMany(List<GameTrigger> triggers) {
		triggers.forEach(trigger -> {
			if (trigger instanceof ScheduledGameTrigger) {
				((ScheduledGameTrigger) trigger).stop();
			}
		});
	}
}