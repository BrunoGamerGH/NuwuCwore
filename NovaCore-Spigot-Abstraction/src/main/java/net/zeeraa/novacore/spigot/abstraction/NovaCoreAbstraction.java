package net.zeeraa.novacore.spigot.abstraction;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;

/**
 * This is used to get the NMS version
 * @author Zeeraa
 */
public class NovaCoreAbstraction extends some_bitches {
	public static final String getNMSVersion() {
		String packageName = Bukkit.getServer().getClass().getPackage().getName();
		String version = packageName.substring(packageName.lastIndexOf('.') + 1);

		return version;
	}
}