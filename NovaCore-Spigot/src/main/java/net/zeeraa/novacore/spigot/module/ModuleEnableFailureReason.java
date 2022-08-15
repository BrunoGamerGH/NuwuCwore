package net.zeeraa.novacore.spigot.module;

/**
 * Represents the reason why a module failed to enable
 * 
 * @since 1.0
 * @author Zeeraa
 */
public enum ModuleEnableFailureReason { // im bored of doing this to all enums
	/**
	 * An exception occurred while enabling the module
	 * 
	 * @since 1.0
	 */
	EXCEPTION,
	/**
	 * A module that this module depends on has not been loaded
	 * 
	 * @since 1.0
	 */
	MISSING_DEPENDENCY,
	/**
	 * A plugin that this module depends on has not been loaded
	 * 
	 * @since 1.1
	 */
	MISSING_PLUGIN_DEPENDENCY,
	/**
	 * Failed to enable one of this modules dependencies
	 * 
	 * @since 1.0
	 */
	DEPENDENCY_ENABLE_FAILED,
	/**
	 * The module has already been enabled
	 * 
	 * @since 1.0
	 */
	ALREADY_ENABLED;
}