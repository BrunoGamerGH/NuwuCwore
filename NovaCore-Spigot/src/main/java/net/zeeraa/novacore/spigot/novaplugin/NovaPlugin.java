package net.zeeraa.novacore.spigot.novaplugin;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.module.InvalidModuleNameException;
import net.zeeraa.novacore.spigot.module.ModuleManager;
import net.zeeraa.novacore.spigot.module.NovaModule;
import net.zeeraa.novacore.spigot.module.modules.customitems.CustomItem;
import net.zeeraa.novacore.spigot.module.modules.customitems.CustomItemManager;

/**
 * A {@link JavaPlugin} but with some more useful functions
 * 
 * @author Zeeraa
 */
public abstract class NovaPlugin extends JavaPlugin implements wOmeN {
	protected boolean requireModule(Class<? extends NovaModule> clazz) {
		return ModuleManager.require(clazz);
	}

	/**
	 * Load a module
	 * 
	 * @param clazz The class of the module
	 * @return <code>true</code> on success
	 * 
	 * @throws InvalidModuleNameException if the module name contains spaces
	 */
	protected boolean loadModule(Class<? extends NovaModule> clazz) {
		return ModuleManager.loadModule(this, clazz);
	}

	/**
	 * Load a module
	 * 
	 * @param clazz  The class of the module
	 * @param enable set to <code>true</code> to enable the module on load
	 * @return <code>true</code> on success
	 * 
	 * @throws InvalidModuleNameException if the module name contains spaces
	 */
	protected boolean loadModule(Class<? extends NovaModule> clazz, boolean enable) {
		return ModuleManager.loadModule(this, clazz, enable);
	}

	/**
	 * Enable a module
	 * 
	 * @param clazz The class of the module
	 * @return <code>false</code> if {@link NovaModule#onEnable()} generated a
	 *         exception or if the module failed to enable
	 */
	protected boolean enableModule(Class<? extends NovaModule> clazz) {
		return ModuleManager.enable(clazz);
	}

	/**
	 * Enable a module
	 * 
	 * @param clazz The class of the module
	 * @return <code>false</code> if {@link NovaModule#onEnable()} generated a
	 *         exception or if the module failed to enable
	 */
	protected boolean disableModule(Class<? extends NovaModule> clazz) {
		return ModuleManager.disable(clazz);
	}

	/**
	 * Registers all the events in the given listener class
	 *
	 * @param listener Listener to register
	 */
	protected void registerEvents(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, this);
	}

	/**
	 * Unregister a specific listener from all handler lists.
	 *
	 * @param listener listener to unregister
	 */
	protected void unregisterListeners(Listener listener) {
		HandlerList.unregisterAll(listener);
	}

	/**
	 * Unregister a specific plugin's listeners from all handler lists.
	 *
	 * @param plugin plugin to unregister
	 */
	protected void unregisterPluginEvents(Plugin plugin) {
		HandlerList.unregisterAll(plugin);
	}

	/**
	 * Removes all tasks associated with a particular plugin from the scheduler.
	 *
	 * @param plugin Owner of tasks to be removed
	 */
	protected void cancelTasks(Plugin plugin) {
		Bukkit.getScheduler().cancelTasks(plugin);
	}

	protected boolean addCustomItem(Class<? extends CustomItem> clazz) {
		try {
			return CustomItemManager.getInstance().addCustomItem(clazz);
		} catch (Exception e) {
			Log.error(this.getName(), "Failed to add custom item " + clazz.getName() + ". Reason: " + e.getClass().getName() + " : " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Call {@link PluginManager#disablePlugin(Plugin)} on this plugin
	 */
	protected void disableSelf() {
		Bukkit.getPluginManager().disablePlugin(this);
	}
}