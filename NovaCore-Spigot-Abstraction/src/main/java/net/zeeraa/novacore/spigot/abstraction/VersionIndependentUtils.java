package net.zeeraa.novacore.spigot.abstraction;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.map.MapView;

import net.zeeraa.novacore.spigot.abstraction.enums.ColoredBlockType;
import net.zeeraa.novacore.spigot.abstraction.enums.NovaCoreGameVersion;
import net.zeeraa.novacore.spigot.abstraction.enums.PlayerDamageReason;
import net.zeeraa.novacore.spigot.abstraction.enums.VersionIndependenceLayerError;
import net.zeeraa.novacore.spigot.abstraction.enums.VersionIndependentMaterial;
import net.zeeraa.novacore.spigot.abstraction.enums.VersionIndependentSound;
import net.zeeraa.novacore.spigot.abstraction.log.AbstractionLogger;

/**
 * A utility to make your plugins support multiple versions of the game without
 * using reflection. This class contains a lot of functions that changes between
 * versions
 * 
 * @author zeeraa
 */
public abstract class VersionIndependentUtils extends some_bitches {
	private static VersionIndependentUtils instance;
	protected static List<String> BED_MATERIALS = new ArrayList<>();

	static {
		BED_MATERIALS.add("BED");
		BED_MATERIALS.add("BLACK_BED");
		BED_MATERIALS.add("BLUE_BED");
		BED_MATERIALS.add("BROWN_BED");
		BED_MATERIALS.add("CYAN_BED");
		BED_MATERIALS.add("GRAY_BED");
		BED_MATERIALS.add("GREEN_BED");
		BED_MATERIALS.add("LIGHT_BLUE_BED");
		BED_MATERIALS.add("LIGHT_GRAY_BED");
		BED_MATERIALS.add("LIME_BED");
		BED_MATERIALS.add("MAGENTA_BED");
		BED_MATERIALS.add("ORANGE_BED");
		BED_MATERIALS.add("PINK_BED");
		BED_MATERIALS.add("PURPLE_BED");
		BED_MATERIALS.add("RED_BED");
		BED_MATERIALS.add("WHITE_BED");
		BED_MATERIALS.add("YELLOW_BED");
	}

	private VersionIndependenceLayerError lastError = VersionIndependenceLayerError.NONE;

	/**
	 * Set the last error cause used for debugging purposes
	 * 
	 * @param error The last {@link VersionIndependenceLayerError} raised
	 */
	protected final void setLastError(VersionIndependenceLayerError error) {
		this.lastError = error;
	}

	/**
	 * GEt the last error type caused in the version independent layer. This will
	 * return null if no errors was encountered or
	 * {@link VersionIndependentUtils#resetLastError()} was called
	 * 
	 * @return The {@link VersionIndependenceLayerError} of <code>null</code> if no
	 *         errors occurred
	 */
	public final VersionIndependenceLayerError getLastError() {
		return lastError;
	}

	/**
	 * Resets the last error variable
	 */
	public final void resetLastError() {
		this.lastError = VersionIndependenceLayerError.NONE;
	}

	/**
	 * Get the {@link ChunkLoader} implementation
	 * 
	 * @return {@link ChunkLoader} to use
	 */
	public abstract ChunkLoader getChunkLoader();

	/**
	 * Get the {@link VersionIndependentUtils} instance that this server uses. Same
	 * as {@link VersionIndependentUtils#getInstance()}
	 * 
	 * @return {@link VersionIndependentUtils} instance
	 */
	public static VersionIndependentUtils get() {
		return instance;
	}

	/**
	 * Get the {@link VersionIndependentUtils} instance that this server uses. Same
	 * as {@link VersionIndependentUtils#get()}
	 * 
	 * @return {@link VersionIndependentUtils} instance
	 */
	public static VersionIndependentUtils getInstance() {
		return instance;
	}

	public static void setInstance(VersionIndependentUtils instance) {
		VersionIndependentUtils.instance = instance;
	}

	/**
	 * Get the {@link LabyModProtocol} instance to use for this version. If the
	 * server version is not supported by labymod this will return a fake instance
	 * that does nothing
	 * 
	 * @return The {@link LabyModProtocol} instance to use
	 */
	public abstract LabyModProtocol getLabyModProtocol();

	/**
	 * Get the {@link NovaCoreGameVersion} nova core is using for the server
	 * 
	 * @return The {@link NovaCoreGameVersion} for thie server
	 */
	public abstract NovaCoreGameVersion getNovaCoreGameVersion();

	/**
	 * Set a block as a player skull
	 * 
	 * @param block The {@link Block} to change the type of
	 */
	public abstract void setBlockAsPlayerSkull(Block block);

	/**
	 * Get the item from the player main hand
	 * 
	 * @param player The player to get item from
	 * @return {@link ItemStack} from the players main hand
	 */
	public abstract ItemStack getItemInMainHand(Player player);

	/**
	 * Set the item in a players main hand
	 * 
	 * @param player The player
	 * @param item   The item so place the the players hand
	 */
	public abstract void setItemInMainHand(Player player, ItemStack item);

	/**
	 * Get the item from the player off hand
	 * 
	 * @param player The player to get item from
	 * @return {@link ItemStack} or <code>null</code> if the server version does not
	 *         have a off hand
	 */
	public abstract ItemStack getItemInOffHand(Player player);

	/**
	 * Set the item in a players off hand
	 * 
	 * @param player The player
	 * @param item   The item so place the the players hand
	 */
	public abstract void setItemInOffHand(Player player, ItemStack item);

	/**
	 * Get the ping of a player
	 * 
	 * @param player The player to get the ping of
	 * @return ping
	 */
	public abstract int getPlayerPing(Player player);

	/**
	 * Attempt to damage a player with a specified reason. If this is not correctly
	 * implemented the player will be damaged using {@link Player#damage(double)}
	 * instead
	 * 
	 * @param player The player to damage
	 * @param reason The {@link PlayerDamageReason} to use
	 * @param damage The amount of damage to cause
	 */
	public abstract void damagePlayer(Player player, PlayerDamageReason reason, float damage);

	/**
	 * Get the max heath of a {@link LivingEntity}
	 * 
	 * @param livingEntity The entity to get the max health from
	 * 
	 * @return The max health of the player
	 */
	public abstract double getEntityMaxHealth(LivingEntity livingEntity);

	/**
	 * Set the max heath of a {@link LivingEntity}
	 * 
	 * @param livingEntity The entity to reset the set health of
	 * @param health       The new health value
	 */
	public abstract void setEntityMaxHealth(LivingEntity livingEntity, double health);

	/**
	 * Reset the max heath of a {@link LivingEntity}
	 * 
	 * @param livingEntity The entity to reset the max health of
	 */
	public abstract void resetEntityMaxHealth(LivingEntity livingEntity);

	/**
	 * Get the recent server TPS
	 * 
	 * @return Array with recent TPS
	 */
	public abstract double[] getRecentTps();

	/**
	 * Clones the data of 2 blocks
	 * 
	 * @param source The block to copy the data from
	 * @param target The block to set the data of
	 */
	public abstract void cloneBlockData(Block source, Block target);

	/**
	 * Send text to display in the tab list
	 * 
	 * @param player The {@link Player} that should receive the text
	 * @param header The top text
	 * @param footer The bottom text
	 */
	public abstract void sendTabList(Player player, String header, String footer);

	/**
	 * Set a block to a {@link ColoredBlockType} with a {@link DyeColor}
	 * 
	 * @param block The {@link Block} to change
	 * @param color The {@link DyeColor} to use
	 * @param type  The {@link ColoredBlockType} to set the block as
	 */
	public abstract void setColoredBlock(Block block, DyeColor color, ColoredBlockType type);

	/**
	 * Used to add a colored block as ingredient of a {@link ShapedRecipe}
	 * 
	 * @param recipe     The {@link ShapedRecipe} to modify
	 * @param ingredient The ingredient char
	 * @param type       The {@link ColoredBlockType} to use
	 * @param color      The {@link DyeColor} to use
	 */
	public abstract void setShapedRecipeIngredientAsColoredBlock(ShapedRecipe recipe, char ingredient, ColoredBlockType type, DyeColor color);

	/**
	 * Used to add a colored block as ingredient of a {@link ShapelessRecipe}
	 * 
	 * @param recipe     The {@link ShapelessRecipe} to modify
	 * @param ingredient The ingredient char
	 * @param type       The {@link ColoredBlockType} to use
	 * @param color      The {@link DyeColor} to use
	 */
	public abstract void addShapelessRecipeIngredientAsColoredBlock(ShapelessRecipe recipe, char ingredient, ColoredBlockType type, DyeColor color);

	/**
	 * Used to add dyes in a {@link ShapedRecipe}
	 * 
	 * @param recipe     The {@link ShapedRecipe} to modify
	 * @param ingredient The ingredient char
	 * @param color      The {@link DyeColor} to use
	 */
	public abstract void setShapedRecipeIngredientAsDye(ShapedRecipe recipe, char ingredient, DyeColor color);

	/**
	 * Used to add dyes in a {@link ShapelessRecipe}
	 * 
	 * @param recipe The {@link ShapelessRecipe} to modify
	 * @param count  The amount required
	 * @param color  The {@link DyeColor} to use
	 */
	public abstract void addShapelessRecipeIngredientAsDye(ShapelessRecipe recipe, int count, DyeColor color);

	/**
	 * Get an {@link ItemStack} of with the type provided in the form of
	 * {@link ColoredBlockType}
	 * 
	 * @param color The {@link DyeColor}
	 * @param type  The {@link ColoredBlockType}
	 * @return An {@link ItemStack} of the {@link ColoredBlockType}
	 */
	public abstract ItemStack getColoredItem(DyeColor color, ColoredBlockType type);

	/**
	 * GEt the {@link ItemBuilderRecordList} for this verion
	 * 
	 * @return {@link ItemBuilderRecordList}
	 */
	public abstract ItemBuilderRecordList getItembBuilderRecordList();

	/**
	 * Set the {@link MapView} of a map {@link ItemStack}
	 * 
	 * @param item    The {@link ItemStack} to set the map view of
	 * @param mapView The {@link MapView} to apply
	 */
	public abstract void attachMapView(ItemStack item, MapView mapView);

	/**
	 * Get the {@link MapView} of a map {@link ItemStack}
	 * 
	 * @param item The {@link ItemStack} to get the map view of
	 * @return mapView The {@link MapView} used for that item
	 */
	public abstract MapView getAttachedMapView(ItemStack item);

	/**
	 * GEt the id number of a {@link MapView}
	 * 
	 * @param mapView The {@link MapView} to get the id of
	 * @return id number of the provided {@link MapView}
	 */
	public abstract int getMapViewId(MapView mapView);

	/**
	 * Play a sound for a player
	 * 
	 * @param player   The player to play the sound for
	 * @param location The location of the sound
	 * @param sound    The {@link VersionIndependentSound} to play
	 * @return <code>true</code> on success. <code>false</code> if the sound is not
	 *         configured
	 */
	public boolean playSound(Player player, Location location, VersionIndependentSound sound) {
		return this.playSound(player, location, sound, 1F, 1F);
	}

	/**
	 * Play a sound for a player
	 * 
	 * @param player   The player to play the sound for
	 * @param location The location of the sound
	 * @param sound    The {@link VersionIndependentSound} to play
	 * @param volume   The volume of the sound
	 * @param pitch    The pitch of the sound
	 * @return <code>true</code> on success. <code>false</code> if the sound is not
	 *         configured
	 */
	public boolean playSound(Player player, Location location, VersionIndependentSound sound, float volume, float pitch) {
		Sound realSound = this.getSound(sound);

		if (sound == null) {
			return false;
		}

		player.playSound(location, realSound, volume, pitch);
		return true;
	}

	/**
	 * Play a sound at a location
	 * 
	 * @param location The location of the sound
	 * @param sound    The {@link VersionIndependentSound} to play
	 * @return <code>true</code> on success. <code>false</code> if the sound is not
	 *         configured
	 */
	public boolean playSound(Location location, VersionIndependentSound sound) {
		return this.playSound(location, sound, 1F, 1F);
	}

	/**
	 * Play a sound at a location
	 * 
	 * @param location The location of the sound
	 * @param sound    The {@link VersionIndependentSound} to play
	 * @param volume   The volume of the sound
	 * @param pitch    The pitch of the sound
	 * @return <code>true</code> on success. <code>false</code> if the sound is not
	 *         configured
	 */
	public boolean playSound(Location location, VersionIndependentSound sound, float volume, float pitch) {
		Sound realSound = this.getSound(sound);

		if (sound == null) {
			return false;
		}

		location.getWorld().playSound(location, realSound, volume, pitch);
		return true;
	}

	/**
	 * Get the {@link Sound} from a {@link VersionIndependentSound}
	 * 
	 * @param sound The {@link VersionIndependentSound} to get
	 * @return resulting {@link Sound}
	 */
	public abstract Sound getSound(VersionIndependentSound sound);

	/**
	 * Send a title to a player
	 * 
	 * @param player   The {@link Player} that should receive the title
	 * @param title    The title text
	 * @param subtitle The subtitle text
	 * @param fadeIn   The amount of ticks for the fade in animation. Set to 0 to
	 *                 disable
	 * @param stay     The amount of ticks the text should stay
	 * @param fadeOut  The amount of ticks for the fade out animation. Set to 0 to
	 *                 disable
	 */
	public abstract void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut);

	/**
	 * Get an {@link ItemStack} with a skull that has its texture set to the
	 * provided base64 string
	 * 
	 * @param b64stringtexture The base64 string containing texture data
	 * @return {@link ItemStack} with a textured player skull
	 */
	public abstract ItemStack getPlayerSkullWithBase64Texture(String b64stringtexture);

	public static <T> Field getField(Class<?> target, String name, Class<T> fieldType, int index) {
		for (final Field field : target.getDeclaredFields()) {
			if ((name == null || field.getName().equals(name)) && fieldType.isAssignableFrom(field.getType()) && index-- <= 0) {
				field.setAccessible(true);
				return field;
			}
		}

		// Search in parent classes
		if (target.getSuperclass() != null)
			return getField(target.getSuperclass(), name, fieldType, index);
		throw new IllegalArgumentException("Cannot find field with type " + fieldType);
	}

	/**
	 * Get the instance of {@link VersionIndependentItems} for this version of the
	 * game
	 * 
	 * @return {@link VersionIndependentItems} instance
	 */
	public abstract VersionIndependentItems getVersionIndependantItems();

	/**
	 * Used to add a player skull as a ingredient in a {@link ShapedRecipe}
	 * 
	 * @param recipe     The {@link ShapedRecipe} to modify
	 * @param ingredient The ingredient char
	 */
	public abstract void setShapedRecipeIngredientAsPlayerSkull(ShapedRecipe recipe, char ingredient);

	/**
	 * Get the {@link Material} from {@link VersionIndependentMaterial}
	 * 
	 * @param material The {@link VersionIndependentMaterial}
	 * @return The minecraft {@link Material}
	 */
	public abstract Material getMaterial(VersionIndependentMaterial material);

	/**
	 * Get an {@link ItemStack} with the material from a
	 * {@link VersionIndependentMaterial}. Can return null if the
	 * {@link VersionIndependentUtils} instance used is outdated. In case of missing
	 * materials please contact the devs
	 * 
	 * @param material The {@link VersionIndependentMaterial} the item should be of
	 * @return {@link ItemStack} of provided type or <code>null</code> if the
	 *         material has not been implemented by the devs
	 */
	public ItemStack getItemStack(VersionIndependentMaterial material) {
		Material mcMaterial = this.getMaterial(material);
		if (mcMaterial == null) {
			AbstractionLogger.getLogger().error("VersionIndependentUtils", "Failed to get version independent material " + material.name() + " for version " + this.getNovaCoreGameVersion().name() + ". This needs to be added");
			return null;
		}

		return new ItemStack(mcMaterial);
	}

	/**
	 * Get an {@link ItemStack} of a player skull
	 * 
	 * @return Player skull {@link ItemStack}
	 */
	public abstract ItemStack getPlayerSkullitem();

	/**
	 * Check if a block is a sign
	 * 
	 * @param block The block to check
	 * @return <code>true</code> if the block is a sign
	 */
	public boolean isSign(Block block) {
		return this.isSign(block.getType());
	}

	public abstract boolean isSign(Material material);

	/**
	 * Send a message to a player in their action bar
	 * 
	 * @param player  The {@link Player} that should receive the message
	 * @param message The message to send
	 */
	public abstract void sendActionBarMessage(Player player, String message);

	/**
	 * @return The minimum y level blocks can exist at
	 */
	public abstract int getMinY();

	/**
	 * Modify the unbreakable parameter of the provided {@link ItemMeta}
	 * 
	 * @param meta        The {@link ItemMeta} to change
	 * @param unbreakable <code>true</code> to make the item unbreakable
	 * @return the {@link ItemMeta} instance
	 */
	public abstract ItemMeta setUnbreakable(ItemMeta meta, boolean unbreakable);

	public ItemStack setUnbreakable(ItemStack item, boolean unbreakable) {
		ItemMeta meta = item.getItemMeta();
		this.setUnbreakable(meta, unbreakable);
		item.setItemMeta(meta);
		return item;
	}

	/**
	 * Set the item in the main hand of a {@link Creature}
	 * 
	 * @param creature The {@link Creature} to use
	 * @param item     The {@link ItemStack} to place in the main hand
	 */
	public abstract void setCreatureItemInMainHand(Creature creature, ItemStack item);

	/**
	 * Get the body rotation of a {@link Player} using NMS
	 * 
	 * @param player The {@link Player} to get the rotation of
	 * @return Player body rotation
	 */
	public abstract float getPlayerBodyRotation(Player player);

	/**
	 * Set the custom model data int of {@link ItemMeta}. If the server does not
	 * support custom model data this will get no oped
	 * 
	 * @param meta The {@link ItemMeta} to modify
	 * @param data The custom model data
	 */
	public abstract void setCustomModelData(ItemMeta meta, int data);

	/**
	 * Secure way to modify gamerules since the 1.8 way got deprecated in newer
	 * versions of spigot
	 * 
	 * @param world The {@link World} to set the rule in
	 * @param rule  The rule to set
	 * @param value The value of the game rule
	 */
	public abstract void setGameRule(World world, String rule, String value);

	/**
	 * Check if a {@link PlayerInteractEvent} is in the main hand
	 * 
	 * @param event The {@link PlayerInteractEvent} to check
	 * @return <code>true</code> if the event was triggered by the main hand
	 */
	public abstract boolean isInteractEventMainHand(PlayerInteractEvent event);

	/**
	 * GEt an {@link Entity} by its {@link UUID}
	 * 
	 * @param uuid The {@link UUID} of the entity
	 * @return The {@link Entity} or <code>null</code> if not found
	 */
	public abstract Entity getEntityByUUID(UUID uuid);

	/**
	 * Set if the AI should be enabled or disabled on a {@link LivingEntity}
	 * 
	 * @param entity The entity
	 * @param ai     <code>false</code> to disable the ai
	 */
	public abstract void setAI(LivingEntity entity, boolean ai);

	/**
	 * Check if a block is a bed
	 * 
	 * @param block The {@link Block} to check
	 * @return <code>true</code> if its a bed
	 */
	public boolean isBed(Block block) {
		return this.isBed(block.getType());
	}

	/**
	 * Check if a material is a bed
	 * 
	 * @param material The {@link Material} to check
	 * @return <code>true</code> if its a bed
	 */
	public boolean isBed(Material material) {
		return BED_MATERIALS.contains(material.name());
	}
}