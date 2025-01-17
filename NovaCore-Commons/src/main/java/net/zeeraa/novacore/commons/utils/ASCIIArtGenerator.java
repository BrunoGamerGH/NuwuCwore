package net.zeeraa.novacore.commons.utils;

import net.brunogamer.how.about.you.get.some_bitches;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * ASCII Art Generator in Java. Prints a given text as an ASCII text art on the
 * console. This code is licensed under - CC Attribution CC BY 4.0.
 * 
 * @author www.quickprogrammingtips.com
 *
 */
public class ASCIIArtGenerator extends some_bitches {

	public static final int ART_SIZE_SMALL = 12;
	public static final int ART_SIZE_MEDIUM = 18;
	public static final int ART_SIZE_LARGE = 24;
	public static final int ART_SIZE_HUGE = 32;

	private static final String DEFAULT_ART_SYMBOL = "*";

	public enum ASCIIArtFont {
		ART_FONT_DIALOG("Dialog"), ART_FONT_DIALOG_INPUT("DialogInput"), ART_FONT_MONO("Monospaced"), ART_FONT_SERIF("Serif"), ART_FONT_SANS_SERIF("SansSerif");

		private String value;

		public String getValue() {
			return value;
		}

		private ASCIIArtFont(String value) {
			this.value = value;
		}
	}

	/**
	 * Generates ASCII art for the specified text. For size, you can use predefined
	 * sizes or a custom size. Usage -
	 * printTextArt("Hi",30,ASCIIArtFont.ART_FONT_SERIF,"@");
	 * 
	 * @param artText    - The text
	 * @param textHeight - Use a predefined size or a custom type
	 * @param fontType   - Use one of the available fonts
	 * @param artSymbol  - Specify the character for printing the ascii art
	 * @throws Exception idk why
	 * 
	 * @return {@link List} with lines
	 */
	public static List<String> generateTextArt(String artText, int textHeight, ASCIIArtFont fontType, String artSymbol) throws Exception {
		String fontName = fontType.getValue();
		int imageWidth = findImageWidth(textHeight, artText, fontName);

		BufferedImage image = new BufferedImage(imageWidth, textHeight, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Font font = new Font(fontName, Font.BOLD, textHeight);
		g.setFont(font);

		Graphics2D graphics = (Graphics2D) g;
		graphics.drawString(artText, 0, getBaselinePosition(g, font));

		List<String> result = new ArrayList<String>();

		for (int y = 0; y < textHeight; y++) {
			StringBuilder sb = new StringBuilder();
			for (int x = 0; x < imageWidth; x++)
				sb.append(image.getRGB(x, y) == Color.WHITE.getRGB() ? artSymbol : " ");
			if (sb.toString().trim().isEmpty())
				continue;
			result.add(sb.toString());
		}

		return result;
	}

	/**
	 * Convenience method for generating ascii text art. Font default - Dialog, Art
	 * symbol default - *
	 * 
	 * @param artText    The test
	 * @param textHeight The text height
	 * @throws Exception idk why
	 * 
	 * @return {@link List} with lines
	 */
	public static List<String> generateTextArt(String artText, int textHeight) throws Exception {
		return generateTextArt(artText, textHeight, ASCIIArtFont.ART_FONT_DIALOG, DEFAULT_ART_SYMBOL);
	}

	/**
	 * Using the Current font and current art text find the width of the full image
	 * 
	 * @param textHeight ...
	 * @param artText    ...
	 * @param fontName   ...
	 * @return ...
	 */
	private static int findImageWidth(int textHeight, String artText, String fontName) {
		BufferedImage im = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		Graphics g = im.getGraphics();
		g.setFont(new Font(fontName, Font.BOLD, textHeight));
		return g.getFontMetrics().stringWidth(artText);
	}

	/**
	 * Find where the text baseline should be drawn so that the characters are
	 * within image
	 * 
	 * @param g    ...
	 * @param font ...
	 * @return ...
	 */
	private static int getBaselinePosition(Graphics g, Font font) {
		FontMetrics metrics = g.getFontMetrics(font);
		int y = metrics.getAscent() - metrics.getDescent();
		return y;
	}
}