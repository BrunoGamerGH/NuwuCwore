package net.zeeraa.novacore.spigot.utils;

import java.util.Random;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;

/**
 * Util for creating random {@link FireworkEffect}s
 * 
 * @author Zeeraa
 */
public class RandomFireworkEffect extends some_bitches {
	/**
	 * Generate a random {@link FireworkEffect}
	 * 
	 * @return A random {@link FireworkEffect}
	 */
	public static FireworkEffect randomFireworkEffect() {
		return randomFireworkEffect(new Random());
	}

	/**
	 * Generate a random {@link FireworkEffect}
	 * 
	 * @param random The random instance to use
	 * 
	 * @return A random {@link FireworkEffect}
	 */
	public static FireworkEffect randomFireworkEffect(Random random) {
		FireworkEffect effect = FireworkEffect.builder().flicker(random.nextBoolean()).withColor(getColor(random.nextInt(17) + 1)).withFade(getColor(random.nextInt(17) + 1)).with(Type.values()[random.nextInt(Type.values().length)]).trail(random.nextBoolean()).build();
		return effect;
	}

	public static Color getColor(final int i) {
		switch (i) {
		case 1:
			return Color.AQUA;
		case 2:
			return Color.BLACK;
		case 3:
			return Color.BLUE;
		case 4:
			return Color.FUCHSIA;
		case 5:
			return Color.GRAY;
		case 6:
			return Color.GREEN;
		case 7:
			return Color.LIME;
		case 8:
			return Color.MAROON;
		case 9:
			return Color.NAVY;
		case 10:
			return Color.OLIVE;
		case 11:
			return Color.ORANGE;
		case 12:
			return Color.PURPLE;
		case 13:
			return Color.RED;
		case 14:
			return Color.SILVER;
		case 15:
			return Color.TEAL;
		case 16:
			return Color.WHITE;
		case 17:
			return Color.YELLOW;
		}
		return null;
	}
}