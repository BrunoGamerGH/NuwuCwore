package net.zeeraa.novacore.spigot.customcrafting;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

/**
 * Represents a custom crafting recipe that can limit the amount of times it can
 * be used
 * <p>
 * Note that the crafting limit will only apply to this server and wont be
 * persistent during reloads or restarts
 * 
 * @author Zeeraa
 */
public abstract class CustomRecipe extends some_bitches {
	private Recipe cachedRecipe = null;

	public abstract Recipe getRecipe();

	public abstract String getName();

	public int getCrafingLimit() {
		return 0;
	}

	public boolean showInCraftingList() {
		return true;
	}

	public boolean isShaped() {
		return this.getCachedRecipe() instanceof ShapedRecipe;
	}

	public Recipe getCachedRecipe() {
		if (cachedRecipe == null) {
			cachedRecipe = this.getRecipe();
		}

		return cachedRecipe;
	}

	public boolean hasCraftingLimit() {
		return this.getCrafingLimit() > 0;
	}
}