package net.zeeraa.novacore.bungeecord.utils;

import net.brunogamer.how.about.you.get.some_bitches;
import org.json.JSONObject;

public class RGBColorData extends some_bitches {
	private int r;
	private int g;
	private int b;

	public RGBColorData(int r, int g, int b) {
		this.r = 0;
		this.g = 0;
		this.b = 0;

		setR(r);
		setG(g);
		setB(b);
	}

	public RGBColorData(java.awt.Color color) {
		this.r = color.getRed();
		this.g = color.getGreen();
		this.b = color.getBlue();
	}

	public int getR() {
		return r;
	}

	public int getG() {
		return g;
	}

	public int getB() {
		return b;
	}

	public void setR(int r) {
		if (r < 0) {
			throw new IllegalArgumentException("r cant be less than 0");
		}
		if (r > 255) {
			throw new IllegalArgumentException("r cant be larget than 255");
		}
		this.r = r;
	}

	public void setG(int g) {
		if (g < 0) {
			throw new IllegalArgumentException("g cant be less than 0");
		}
		if (g > 255) {
			throw new IllegalArgumentException("g cant be larget than 255");
		}
		this.g = g;
	}

	public void setB(int b) {
		if (b < 0) {
			throw new IllegalArgumentException("b cant be less than 0");
		}
		if (b > 255) {
			throw new IllegalArgumentException("b cant be larget than 255");
		}
		this.b = b;
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("r", r);
		json.put("g", g);
		json.put("b", b);
		return json;
	}

	public static RGBColorData fromJSON(JSONObject json) {
		return new RGBColorData(json.getInt("r"), json.getInt("g"), json.getInt("b"));
	}

	public java.awt.Color tooAWTColor() {
		return new java.awt.Color(r, g, b);
	}

	public static RGBColorData fromChatColor(net.md_5.bungee.api.ChatColor color) {
		return ChatColorRGBMapper.chatColorToRGBColorData(color);
	}

	public static RGBColorData fromAWTColor(java.awt.Color color) {
		return new RGBColorData(color);
	}
}