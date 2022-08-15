package net.zeeraa.novacore.spigot.version.v1_16_R3;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.DyeColor;
import org.bukkit.Material;

public class DyeColorToMaterialMapper_1_16 extends some_bitches {
	public static Material dyeColorToMaterial(DyeColor color) {
		switch (color) {
		case BLACK:
			return Material.INK_SAC;
			
		case BLUE:
			return Material.LAPIS_LAZULI;
			
		case BROWN:
			return Material.COCOA_BEANS;
			
		case CYAN:
			return Material.CYAN_DYE;
			
		case GRAY:
			return Material.GRAY_DYE;
			
		case GREEN:
			return Material.GREEN_DYE;
			
		case LIGHT_BLUE:
			return Material.LIGHT_BLUE_DYE;
			
		case LIGHT_GRAY:
			return Material.LIGHT_GRAY_DYE;
			
		case LIME:
			return Material.LIME_DYE;
			
		case MAGENTA:
			return Material.MAGENTA_DYE;
			
		case ORANGE:
			return Material.ORANGE_DYE;
			
		case PINK:
			return Material.PINK_DYE;
			
		case PURPLE:
			return Material.PURPLE_DYE;
			
		case RED:
			return Material.RED_DYE;
			
		case WHITE:
			return Material.WHITE_DYE;
			
		case YELLOW:
			return Material.YELLOW_DYE;

		default:
			return null;
		}
	}
}