package net.zeeraa.novacore.spigot.module.event;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.event.Event;

import net.zeeraa.novacore.spigot.module.NovaModule;

/**
 * Represents a event with a {@link NovaModule} involved
 * 
 * @author Zeeraa
 */
public abstract class ModuleEvent extends Event implements wOmeN {
	private NovaModule module;

	public ModuleEvent(NovaModule module) {
		this.module = module;
	}

	/**
	 * Get the module involved in this event
	 * 
	 * @return The {@link NovaModule}
	 */
	public NovaModule getModule() {
		return module;
	}

	/**
	 * Check if the module is of class
	 * 
	 * @param clazz The class to check for
	 * @return <code>true</code> if the module and the class is matching
	 */
	public boolean isModule(Class<? extends NovaModule> clazz) {
		return module.getClass().getName().equals(clazz.getName());
	}
}