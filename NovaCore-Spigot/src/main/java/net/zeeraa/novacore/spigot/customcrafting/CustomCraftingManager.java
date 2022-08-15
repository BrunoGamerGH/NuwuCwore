package net.zeeraa.novacore.spigot.customcrafting;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Recipe;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.utils.RecipeUtils;

/**
 * Used to register {@link CustomRecipe}s and limit the amount of times they can
 * be crafted.
 * <p>
 * Please read the description of {@link CustomRecipe} for some important info
 * about crafting limits
 * 
 * @author Zeeraa
 */
public class CustomCraftingManager extends some_bitches implements Listener {
	private static CustomCraftingManager instance;

	private Map<String, CustomRecipe> recipes;
	private Map<UUID, Map<String, Integer>> craftingLimit;

	public static CustomCraftingManager getInstance() {
		return instance;
	}

	public CustomCraftingManager() {
		CustomCraftingManager.instance = this;

		this.recipes = new HashMap<String, CustomRecipe>();
		this.craftingLimit = new HashMap<UUID, Map<String, Integer>>();

		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			craftingLimit.put(p.getUniqueId(), new HashMap<String, Integer>());
		}
	}

	public Map<String, CustomRecipe> getRecipes() {
		return recipes;
	}

	public CustomRecipe getRecipe(Recipe recipe) {
		for (String name : recipes.keySet()) {
			if (RecipeUtils.compareRecipe(recipes.get(name).getCachedRecipe(), recipe)) {
				return recipes.get(name);
			}
		}

		return null;
	}

	public CustomRecipe getRecipe(String name) {
		return recipes.get(name);
	}

	public boolean hasRecipe(String name) {
		return recipes.containsKey(name);
	}

	public boolean addRecipe(Class<? extends CustomRecipe> clazz) {
		try {
			CustomRecipe recipe = (CustomRecipe) clazz.getConstructor().newInstance(new Object[] {});
			return this.addRecipe(recipe);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean addRecipe(CustomRecipe recipe) {
		if (recipes.containsKey(recipe.getClass().getName())) {
			return false;
		}

		Bukkit.getServer().addRecipe(recipe.getCachedRecipe());

		recipes.put(recipe.getClass().getName(), recipe);

		return true;
	}

	public boolean isAdded(CustomRecipe recipe) {
		return this.isAdded(recipe.getClass());
	}

	public boolean isAdded(Class<? extends CustomRecipe> clazz) {
		return recipes.containsKey(clazz.getName());
	}

	public void reset() {
		for (UUID uuid : craftingLimit.keySet()) {
			craftingLimit.get(uuid).clear();
		}
	}

	public void removeAll() {
		Bukkit.getServer().resetRecipes();
	}

	@EventHandler
	public void onCraftItem(CraftItemEvent e) {
		CustomRecipe recipe = getRecipe(e.getRecipe());

		if (recipe == null) {
			return;
		}

		if (!recipe.hasCraftingLimit()) {
			return;
		}

		if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY || e.getClick() == ClickType.SHIFT_RIGHT || e.getClick() == ClickType.SHIFT_LEFT) {
			((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.ITEM_BREAK, 1F, 1F);
			e.getWhoClicked().sendMessage(ChatColor.RED + "You cant use shift while crafting this custom recipe");
			e.setCancelled(true);
			return;
		}

		Player p = (Player) e.getWhoClicked();

		if (!craftingLimit.containsKey(p.getUniqueId())) {
			p.sendMessage(ChatColor.RED + "Server error");
			Log.warn("CustomCraftingManager Error: craftingLimit does not contain player " + p.getUniqueId());
			e.setCancelled(true);
			return;
		}

		int crafted = 0;

		if (craftingLimit.get(p.getUniqueId()).containsKey(recipe.getClass().getName())) {
			crafted = craftingLimit.get(p.getUniqueId()).get(recipe.getClass().getName());
		}

		int limit = recipe.getCrafingLimit();

		if (crafted >= limit) {
			e.setCancelled(true);
			p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1F, 1F);
			p.sendMessage(ChatColor.RED + "You can only craft this item " + limit + " time" + (limit == 1 ? "" : "s"));
			return;
		}

		if (e.getAction() != InventoryAction.NOTHING) {
			craftingLimit.get(p.getUniqueId()).put(recipe.getClass().getName(), crafted + 1);
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		if (!craftingLimit.containsKey(p.getUniqueId())) {
			craftingLimit.put(p.getUniqueId(), new HashMap<String, Integer>());
		}
	}
}