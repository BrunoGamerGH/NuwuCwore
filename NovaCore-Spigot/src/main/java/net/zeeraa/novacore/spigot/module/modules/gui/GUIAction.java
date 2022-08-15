package net.zeeraa.novacore.spigot.module.modules.gui;

/**
 * This is used to either allow or deny something in a GUI
 * 
 * @author Zeeraa
 */
public enum GUIAction { //
	/**
	 * Cancel the interaction
	 */
	CANCEL_INTERACTION,
	/**
	 * Allow the interaction
	 */
	ALLOW_INTERACTION,
	/**
	 * Do not change the {@link GUIAction}. If no other callback or event modifies
	 * the {@link GUIAction} it will use the default of
	 * {@link GUIAction#CANCEL_INTERACTION}
	 */
	NONE;
}