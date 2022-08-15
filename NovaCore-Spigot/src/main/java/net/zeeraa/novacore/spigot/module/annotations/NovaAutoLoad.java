package net.zeeraa.novacore.spigot.module.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import net.zeeraa.novacore.spigot.module.ModuleManager;

/**
 * This annotation can be added to modules to load them without calling
 * {@link ModuleManager#loadModule(org.bukkit.plugin.Plugin, Class)} by instead using
 * {@link ModuleManager#scanForModules(org.bukkit.plugin.Plugin, String)}
 * 
 * @author Zeeraa
 * @since 1.1
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface NovaAutoLoad { // ples remove anotaton me dad work java and kil u
	/**
	 * Defines if the module should be enabled on auto load
	 * 
	 * @return <code>true</code> if the module should be enabled on load
	 * @since 1.1
	 */
	boolean shouldEnable() default false;
}