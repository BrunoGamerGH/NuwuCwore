package net.zeeraa.novacore.spigot.utils;

import java.util.ArrayList;
import java.util.List;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.abstraction.VersionIndependentUtils;
import net.zeeraa.novacore.spigot.abstraction.enums.VersionIndependentMaterial;

/**
 * Useful tool to create item stacks with custom names and data using a single
 * line of code. This also provides ways to do some certain things that normally
 * would make your plugin only work on a single version of minecraft
 * 
 * @author Zeeraa
 */
public class ItemBuilder extends some_bitches {
	/**
	 * An {@link ItemStack} of air
	 */
	public static final ItemStack AIR = new ItemBuilder(Material.AIR).build();

	/**
	 * Item being built
	 */
	protected ItemStack item;

	/**
	 * Meta of the item being built
	 */
	protected ItemMeta meta;

	/**
	 * Init by material
	 * 
	 * @param material The material
	 */
	public ItemBuilder(Material material) {
		this(material, 1);
	}

	/**
	 * Initiate by material and amount
	 * 
	 * @param material The material
	 * @param ammount  The amount
	 */
	public ItemBuilder(Material material, int ammount) {
		this(new ItemStack(material, ammount), false);
	}

	/**
	 * Init by material
	 * 
	 * @param material The material
	 */
	public ItemBuilder(VersionIndependentMaterial material) {
		this(material, 1);
	}

	/**
	 * Initiate by material and amount
	 * 
	 * @param material The material
	 * @param ammount  The amount
	 */
	public ItemBuilder(VersionIndependentMaterial material, int ammount) {
		this(new ItemStack(material.toBukkitVersion(), ammount), false);
	}

	/**
	 * Initiate from an existing item stack
	 * 
	 * @param itemStack The item stack
	 */
	public ItemBuilder(ItemStack itemStack) {
		this(itemStack, false);
	}

	/**
	 * Initiate from an item stack
	 * 
	 * @param itemStack The item stack
	 * @param clone     True if the stack should be cloned
	 */
	public ItemBuilder(ItemStack itemStack, boolean clone) {
		if (clone) {
			this.item = itemStack.clone();
		} else {
			this.item = itemStack;
		}
		this.meta = this.item.getItemMeta();
	}

	/**
	 * Set the display name of the item
	 * 
	 * @param name The name of the item
	 * @return The item builder instance
	 */
	public ItemBuilder setName(String name) {
		meta.setDisplayName(name);
		return this;
	}

	/**
	 * Add an enchantment to the item
	 * 
	 * @param ench  The {@link Enchantment} to add
	 * @param level The level
	 * @return The item builder instance
	 */
	public ItemBuilder addEnchant(Enchantment ench, int level) {
		return this.addEnchant(ench, level, false);
	}

	/**
	 * Add an enchantment to the item
	 * 
	 * @param ench                   The {@link Enchantment} to add
	 * @param level                  The level
	 * @param ignoreLevelRestriction <code>true</code> to ignore level restriction
	 * @return The item builder instance
	 */
	public ItemBuilder addEnchant(Enchantment ench, int level, boolean ignoreLevelRestriction) {
		meta.addEnchant(ench, level, ignoreLevelRestriction);
		return this;
	}

	/**
	 * Remove an enchantment
	 * 
	 * @param ench The {@link Enchantment} to remove
	 * @return The item builder instance
	 */
	public ItemBuilder removeEnchant(Enchantment ench) {
		meta.removeEnchant(ench);
		return this;
	}

	/**
	 * Add an item flag
	 * 
	 * @param itemFlag The {@link ItemFlag} to add
	 * @return The item builder instance
	 */
	public ItemBuilder addItemFlags(ItemFlag itemFlag) {
		meta.addItemFlags(itemFlag);
		return this;
	}

	/**
	 * Remove an item flag
	 * 
	 * @param itemFlag The {@link ItemFlag} to remove
	 * @return The item builder instance
	 */
	public ItemBuilder removeItemFlags(ItemFlag itemFlag) {
		meta.removeItemFlags(itemFlag);
		return this;
	}

	/**
	 * Set the amount of items in the stack
	 * 
	 * @param amount The amount
	 * @return The item builder instance
	 */
	public ItemBuilder setAmount(int amount) {
		item.setAmount(amount);
		return this;
	}

	/**
	 * Set the item meta of the builder
	 * 
	 * @param meta The new item meta to use
	 * @return The item builder instance
	 */
	public ItemBuilder setItemMeta(ItemMeta meta) {
		item.setItemMeta(meta);
		this.meta = meta;
		return this;
	}

	/**
	 * Set the item as unbreakable
	 * 
	 * @param unbreakable <code>true</code> if the item should be unbreakable
	 * @return The item builder instance
	 */
	public ItemBuilder setUnbreakable(boolean unbreakable) {
		VersionIndependentUtils.get().setUnbreakable(meta, unbreakable);
		return this;
	}

	/**
	 * Add a line to the lore of the item
	 * 
	 * @param string The text that should be added
	 * @return The item builder instance
	 */
	public ItemBuilder addLore(String string) {
		List<String> lore;
		if (meta.hasLore()) {
			lore = meta.getLore();
		} else {
			lore = new ArrayList<String>();
		}

		lore.add(string);
		meta.setLore(lore);
		return this;
	}

	/**
	 * Add multiple lines of lore to the item
	 * 
	 * @param lines The lines to add
	 * @return The item builder instance
	 * @since 2.0.0
	 */
	public ItemBuilder addLore(List<String> lines) {
		lines.forEach(string -> this.addLore(string));
		return this;
	}

	/**
	 * Set the durability of the item
	 * 
	 * @param durability New durability
	 * @return The item builder instance
	 */
	public ItemBuilder setDurability(short durability) {
		item.setDurability(durability);
		return this;
	}

	/**
	 * Set the color of leather armor. Do not call this if the item is not a piece
	 * of leather armor.
	 * 
	 * @param color The color to set
	 * @return The item builder instance
	 */
	public ItemBuilder setLeatherArmorColor(Color color) {
		((LeatherArmorMeta) meta).setColor(color);
		return this;
	}

	/**
	 * Create an item stack from the builder
	 * 
	 * @return The item stack
	 */
	public ItemStack build() {
		this.item.setItemMeta(meta);
		return this.item;
	}

	/**
	 * Add a stored enchantment to the item. This is used in enchanted books
	 * 
	 * @param enchantment The {@link Enchantment} to add
	 * @param level       The level
	 * @return The item builder instance
	 */
	public ItemBuilder addStoredEnchant(Enchantment enchantment, int level) {
		return this.addStoredEnchant(enchantment, level, false);
	}

	/**
	 * Add a stored enchantment to the item. This is used in enchanted books
	 * 
	 * @param enchantment            The {@link Enchantment} to add
	 * @param level                  The level
	 * @param ignoreLevelRestriction <code>true</code> to ignore level restriction
	 * @return The item builder instance
	 */
	public ItemBuilder addStoredEnchant(Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
		if (meta instanceof EnchantmentStorageMeta) {
			((EnchantmentStorageMeta) meta).addStoredEnchant(enchantment, level, ignoreLevelRestriction);
		} else {
			Log.warn("ItemBuilder", "Could not add stored enchant to item of type " + item.getType().name() + " since it does not have a meta of type EnchantmentStorageMeta");
		}

		return this;
	}

	public ItemBuilder setCustomModelData(int data) {
		VersionIndependentUtils.get().setCustomModelData(meta, data);
		return this;
	}

	/**
	 * Get a {@link List} containing all lines from a array of strings
	 * 
	 * @param lines The lines to add to the {@link List}
	 * @return {@link List} containing all lines
	 */
	public static List<String> generateLoreList(String... lines) {
		List<String> lore = new ArrayList<String>();

		for (String line : lines) {
			lore.add(line);
		}

		return lore;

	}

	/**
	 * Get an empty {@link List}
	 * 
	 * @return An empty {@link List}
	 */
	public static List<String> generateLoreList() {
		return new ArrayList<String>();
	}

	/**
	 * Create an item stack of the provided {@link Material} with 1 item
	 * 
	 * @param material The {@link Material} of the item stack
	 * @return {@link ItemStack} of the provided material
	 */
	public static ItemStack materialToItemStack(Material material) {
		return ItemBuilder.materialToItemStack(material, 1);
	}

	/**
	 * Create an item stack of the provided {@link Material} and size
	 * 
	 * @param material The {@link Material} of the item stack
	 * @param size     The size of the item stack
	 * @return {@link ItemStack} of the provided material and size
	 */
	public static ItemStack materialToItemStack(Material material, int size) {
		return new ItemBuilder(material).setAmount(size).build();
	}

	/**
	 * Get a {@link List} with all available record names
	 * 
	 * @return List with all available record names
	 */
	public static List<String> getAvailableRecordNames() {
		return new ArrayList<>(NovaCore.getInstance().getVersionIndependentUtils().getItembBuilderRecordList().getRecordMap().keySet());
	}

	/**
	 * Check if a record with the provided name exists
	 * 
	 * @param name The name of the record
	 * @return <code>true</code> if the record exits
	 */
	public static boolean hasRecordName(String name) {
		for (String s : NovaCore.getInstance().getVersionIndependentUtils().getItembBuilderRecordList().getRecordMap().keySet()) {
			if (s.equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get an instance of a {@link ItemBuilder} containing a record with the
	 * provided name
	 * <p>
	 * This will return null if an invalid name is provided, you can check if the
	 * name is valid by using {@link ItemBuilder#hasRecordName(String)}
	 * 
	 * @param recordName The name of the record
	 * @return {@link ItemBuilder} with the provided record or <code>null</code> if
	 *         the record name could not be found
	 */
	public static ItemBuilder getRecordItemBuilder(String recordName) {
		Material material = null;

		for (String key : NovaCore.getInstance().getVersionIndependentUtils().getItembBuilderRecordList().getRecordMap().keySet()) {
			if (key.equalsIgnoreCase(recordName)) {
				material = NovaCore.getInstance().getVersionIndependentUtils().getItembBuilderRecordList().getRecordMap().get(key);
				break;
			}
		}

		if (material == null) {
			return null;
		}

		return new ItemBuilder(material);
	}

	/**
	 * Create an item builder with a player skull of the provided player name
	 * 
	 * @param playerName The name of the player the skull belongs to
	 * @return {@link ItemBuilder} instance
	 * @since 1.1
	 */
	public static ItemBuilder playerSkull(String playerName) {
		ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

		SkullMeta meta = (SkullMeta) playerHead.getItemMeta();

		meta.setOwner(playerName);

		playerHead.setItemMeta(meta);

		return new ItemBuilder(playerHead);
	}

	/**
	 * Create an item builder with a player skull of the provided player
	 * 
	 * @param player The player the skull belongs to
	 * @return {@link ItemBuilder} instance
	 * @since 1.1
	 */
	public static ItemBuilder playerSkull(OfflinePlayer player) {
		return ItemBuilder.playerSkull(player.getName());
	}

	/**
	 * Get a player skull with the provided texture
	 * 
	 * @param b64stringtexture The texture
	 * @return {@link ItemStack} with a player skull
	 */
	public static ItemStack getPlayerSkullWithBase64Texture(String b64stringtexture) {
		return VersionIndependentUtils.get().getPlayerSkullWithBase64Texture(b64stringtexture);
	}

	/**
	 * Get a player skull with the provided texture
	 * 
	 * @param b64stringtexture The texture
	 * @return {@link ItemBuilder} with a player skull
	 */
	public static ItemBuilder getPlayerSkullWithBase64TextureAsBuilder(String b64stringtexture) {
		return new ItemBuilder(VersionIndependentUtils.get().getPlayerSkullWithBase64Texture(b64stringtexture));
	}

	/**
	 * Get the display name of an item
	 * 
	 * @param stack The item to check
	 * @return The display name of the item
	 */
	public static String getItemDisplayName(ItemStack stack) {
		return stack.getItemMeta().getDisplayName();
	}

	/**
	 * Check if an item has a display name
	 * 
	 * @param stack The item to check
	 * @return <code>true</code> if the item has a display name
	 * @since 1.1
	 */
	public static boolean hasItemDisplayName(ItemStack stack) {
		return stack.getItemMeta().hasDisplayName();
	}

	/**
	 * Create an instance from a material
	 * 
	 * @param material The {@link Material} to use
	 * @return {@link ItemBuilder} instance with the provided material
	 */
	public static ItemBuilder newInstance(Material material) {
		return new ItemBuilder(material);
	}

	public static ItemStack makeItemStackUnbreakable(ItemStack item, boolean unbreakable) {
		return VersionIndependentUtils.get().setUnbreakable(item, unbreakable);
	}
}