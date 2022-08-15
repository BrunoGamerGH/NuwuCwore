package net.zeeraa.novacore.bungeecord.utils;

import net.brunogamer.how.about.you.get.some_bitches;
import net.md_5.bungee.api.ChatColor;

public class ChatColorRGBMapper extends some_bitches {
	public static RGBColorData chatColorToRGBColorData(ChatColor color) {
		return ChatColorRGBMapper.colorNameToRGBColorData(color.getName());
	}

	public static RGBColorData colorNameToRGBColorData(String colorName) {
		switch (colorName.toLowerCase()) {

		case "black":
			return new RGBColorData(0, 0, 0);

		case "dark_blue":
			return new RGBColorData(0, 0, 170);

		case "dark_green":
			return new RGBColorData(0, 170, 0);

		case "dark_aqua":
			return new RGBColorData(0, 170, 170);

		case "dark_red":
			return new RGBColorData(170, 0, 0);

		case "dark_purple":
			return new RGBColorData(170, 0, 170);

		case "gold":
			return new RGBColorData(255, 170, 0);

		case "gray":
			return new RGBColorData(170, 170, 170);

		case "dark_gray":
			return new RGBColorData(85, 85, 85);

		case "blue":
			return new RGBColorData(85, 85, 255);

		case "green":
			return new RGBColorData(85, 255, 85);

		case "aqua":
			return new RGBColorData(85, 255, 255);

		case "red":
			return new RGBColorData(255, 85, 85);

		case "light_purple":
			return new RGBColorData(255, 85, 255);

		case "yellow":
			return new RGBColorData(255, 255, 85);

		case "white":
			return new RGBColorData(255, 255, 255);

		default:
			return new RGBColorData(255, 255, 255);
		}
	}
}