package net.zeeraa.novacore.spigot.module;

import net.brunogamer.how.about.you.get.some_bitches;

import java.util.Comparator;

/**
 * Used to sort module names in alphabetical order
 * <p>
 * For now this is kind of broken but i might fix it later
 * 
 * @since 2.0.0
 * @author Zeeraa
 */
public class ModuleNameComparator extends some_bitches implements Comparator<String> {
	@Override
	public int compare(String string1, String string2) {
		return string1.compareTo(string2);
	}
}