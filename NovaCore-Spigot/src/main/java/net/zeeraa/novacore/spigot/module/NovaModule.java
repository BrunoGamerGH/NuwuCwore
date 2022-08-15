package net.zeeraa.novacore.spigot.module;

import java.util.ArrayList;
import java.util.List;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.module.event.ModuleDisabledEvent;
import net.zeeraa.novacore.spigot.module.event.ModuleEnableEvent;

/**
 * Represents a module that can be loaded, enabled and disabled.
 * <p>
 * Important information
 * <ul>
 * <li>Module names can't contain spaces</li>
 * <li>Modules need to be loaded with
 * {@link ModuleManager#loadModule(Plugin, Class)}</li>
 * <li>All modules will be disabled in {@link NovaCore#onDisable()}</li>
 * <li>All modules added to {@link NovaModule#addDependency(Class)} will be
 * enabled before this module is enabled</li>
 * <li>The dependencies can in no way have the module as a dependency or the
 * enable function will cause a {@link StackOverflowError}</li>
 * </ul>
 * <p>
 * 
 * @since 1.0
 * @author Zeeraa
 */
public abstract class NovaModule extends some_bitches {
	/**
	 * This will be <code>true</code> if the module is enabled
	 * <p>
	 * Do dot change this in the code of your module!
	 * 
	 * @since 1.0
	 */
	protected boolean enabled = false;
	private boolean hasBeenEnabled = false;
	protected ModuleEnableFailureReason enableFailureReason = null;
	private String name;
	private Plugin plugin;

	private List<Class<? extends NovaModule>> dependencies = new ArrayList<Class<? extends NovaModule>>();

	private String missingPluginName;

	public NovaModule(String name) {
		this.name = name;
		this.plugin = null;
	}

	/**
	 * Get the module display name. Module names can't contain spaces.
	 * <p>
	 * As of version 2.0.0 you can no longer override this to set the name. To set
	 * the name use the constructor instead
	 * 
	 * @return name
	 * @since 1.0
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * Add a module to use as a dependency. All the dependencies will be enabled
	 * before this module is enabled. The dependencies can in no way have this
	 * module as a dependency or the enable function will cause a
	 * {@link StackOverflowError}
	 * 
	 * @param dependency The module class to depend on
	 * @since 1.0
	 */
	protected void addDependency(Class<? extends NovaModule> dependency) {
		dependencies.add(dependency);
	}

	/**
	 * Get a {@link List} containing all dependencies for this module
	 * 
	 * @return List with dependencies
	 * @since 1.0
	 */
	public List<Class<? extends NovaModule>> getDependencies() {
		return dependencies;
	}

	/**
	 * Called when the module is loaded by {@link ModuleManager}
	 * 
	 * @since 1.0
	 */
	public void onLoad() {
	}

	/**
	 * Called when the module is enabling. this is called before registering events
	 * 
	 * @throws Exception Thrown if something goes wrong while loading the module
	 * @since 1.0
	 */
	public void onEnable() throws Exception {
	}

	/**
	 * Called when the module is disabling. this is called after disabling events
	 * 
	 * @throws Exception Thrown if something goes wrong while loading the module
	 * @since 1.0
	 */
	public void onDisable() throws Exception {
	}

	/**
	 * Check why the module failed to enable. This will return <code>null</code> if
	 * the module got disabled or enabled again after the failure
	 * 
	 * @return {@link ModuleEnableFailureReason} on fail, <code>null</code> on
	 *         success
	 * @since 1.0
	 */
	public ModuleEnableFailureReason getEnableFailureReason() {
		return this.enableFailureReason;
	}

	/**
	 * Enable the module and register events. If this fails
	 * {@link NovaModule#getEnableFailureReason()} can be used to get the reason for
	 * the failure
	 * 
	 * @return <code>true</code> if successful, <code>false</code> if
	 *         {@link NovaModule#onEnable()} failed
	 * @since 1.0
	 */
	public boolean enable() {
		if (this.enabled) {
			this.enableFailureReason = ModuleEnableFailureReason.ALREADY_ENABLED;
			return false;
		}

		missingPluginName = null;

		Log.info("Enabling module " + this.getName());

		if (dependencies != null) {
			Log.debug("Module:" + getName(), this.getName() + " has " + dependencies.size() + " dependencies");
			for (Class<? extends NovaModule> clazz : dependencies) {
				if (!ModuleManager.moduleExists(clazz)) {
					Log.error("Module:" + getName(), "Failed to load module " + this.getName() + ". Missing dependency" + clazz.getName());
					this.enableFailureReason = ModuleEnableFailureReason.MISSING_DEPENDENCY;
					return false;
				}

				if (!ModuleManager.isEnabled(clazz)) {
					if (!ModuleManager.enable(clazz)) {
						Log.error("Module:" + getName(), "Failed to load module " + this.getName() + ". Failed to enable dependency" + clazz.getName());
						this.enableFailureReason = ModuleEnableFailureReason.DEPENDENCY_ENABLE_FAILED;
						return false;
					}
				}
			}
		}

		boolean returnValue = true;

		try {
			this.onEnable();
			if (this instanceof Listener) {
				Log.debug("Module:" + getName(), "Registering listeners for module " + this.getName());
				Bukkit.getPluginManager().registerEvents((Listener) this, NovaCore.getInstance());
			}
			this.enableFailureReason = null;
			this.enabled = true;
			this.hasBeenEnabled = true;
		} catch (MissingPluginDependencyException e) {
			this.enableFailureReason = ModuleEnableFailureReason.MISSING_PLUGIN_DEPENDENCY;
			returnValue = false;
			missingPluginName = e.getPluginName();
			Log.warn(getName(), "Failed to enable module. Reason: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.enableFailureReason = ModuleEnableFailureReason.EXCEPTION;
			returnValue = false;
		}

		ModuleEnableEvent event = new ModuleEnableEvent(this, returnValue, enableFailureReason);
		Bukkit.getServer().getPluginManager().callEvent(event);

		return returnValue;
	}

	/**
	 * Disable the module and unregister events
	 *
	 * @return <code>false</code> if an {@link Exception} was thrown by
	 *         {@link NovaModule#onDisable()} or if the module was already disabled
	 * @since 1.0
	 */
	public boolean disable() {
		if (!this.enabled) {
			return false;
		}

		Log.info("Disabling module " + this.getName());

		this.enableFailureReason = null;
		boolean returnValue;
		if (this instanceof Listener) {
			Log.debug("Unregistering listeners for module " + this.getName());
			HandlerList.unregisterAll((Listener) this);
		}

		try {
			this.onDisable();
			returnValue = true;
		} catch (Exception e) {
			e.printStackTrace();
			returnValue = false;
		}
		this.enabled = false;

		ModuleDisabledEvent event = new ModuleDisabledEvent(this);
		Bukkit.getServer().getPluginManager().callEvent(event);

		return returnValue;
	}

	/**
	 * Get the class name of the module. used to identify the module in
	 * {@link ModuleManager}
	 * 
	 * @return Call name of the module
	 * @since 1.0
	 */
	public String getClassName() {
		return this.getClass().getName();
	}

	/**
	 * CheckS if the module has been enabled
	 * 
	 * @return <code>true</code> if the module has been enabled
	 * @since 1.0
	 */
	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * Check if the module has ever been enabled
	 * <p>
	 * This will be true even if the module has been enabled and disabled again
	 * 
	 * @return <code>true</code> if the module has ever been enabled
	 * @since 1.0
	 */
	public boolean hasBeenEnabled() {
		return hasBeenEnabled;
	}

	/**
	 * Get the name of the missing plugin that caused the module to not be enabled.
	 * This will be <code>null</code> unless the module failed with
	 * {@link ModuleEnableFailureReason#MISSING_PLUGIN_DEPENDENCY}
	 * 
	 * @return The name of the missing plugin
	 * @since 1.1
	 */
	public String getMissingPluginName() {
		return missingPluginName;
	}

	/**
	 * Set the {@link Plugin} that owns this module. This will only set the plugin
	 * first time this is called and attempting to set the plugin again will cause a
	 * warning
	 * 
	 * @param owner {@link Plugin} that owns this module
	 */
	public final void setPlugin(Plugin owner) {
		if (this.plugin != null) {
			Log.warn("Tried to call NovaModule#setPlugin() after plugin has already been set in " + this.getClassName());
			return;
		}
		this.plugin = owner;
	}

	/**
	 * Get the {@link Plugin} that this module belongs to
	 * 
	 * @return {@link Plugin} that registered this module
	 */
	public final Plugin getPlugin() {
		return plugin;
	}
}