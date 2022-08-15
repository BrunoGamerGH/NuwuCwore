package net.zeeraa.novacore.spigot.version.v1_12_R1;

import java.lang.reflect.Field;
import java.util.UUID;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.map.MapView;
import org.bukkit.material.MaterialData;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.DamageSource;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.zeeraa.novacore.spigot.abstraction.VersionIndependentItems;
import net.zeeraa.novacore.spigot.abstraction.enums.ColoredBlockType;
import net.zeeraa.novacore.spigot.abstraction.enums.NovaCoreGameVersion;
import net.zeeraa.novacore.spigot.abstraction.enums.PlayerDamageReason;
import net.zeeraa.novacore.spigot.abstraction.enums.VersionIndependenceLayerError;
import net.zeeraa.novacore.spigot.abstraction.enums.VersionIndependentMaterial;
import net.zeeraa.novacore.spigot.abstraction.enums.VersionIndependentSound;
import net.zeeraa.novacore.spigot.abstraction.log.AbstractionLogger;
import net.zeeraa.novacore.spigot.abstraction.ChunkLoader;
import net.zeeraa.novacore.spigot.abstraction.ItemBuilderRecordList;
import net.zeeraa.novacore.spigot.abstraction.LabyModProtocol;

public class VersionIndependentUtils extends net.zeeraa.novacore.spigot.abstraction.VersionIndependentUtils implements wOmeN {
	private ItemBuilderRecordList itemBuilderRecordList;

	private ChunkLoader chunkLoader;

	@Override
	public ChunkLoader getChunkLoader() {
		if (chunkLoader == null) {
			chunkLoader = new ChunkLoaderImplementation();
		}
		return chunkLoader;
	}

	public VersionIndependentUtils() {
		itemBuilderRecordList = new ItemBuilderRecordList1_12();
	}

	@Override
	public ItemBuilderRecordList getItembBuilderRecordList() {
		return itemBuilderRecordList;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setBlockAsPlayerSkull(Block block) {
		block.setType(Material.SKULL);
		Skull skull = (Skull) block.getState();
		skull.setSkullType(SkullType.PLAYER);

		block.getState().update(true);

		// TODO: fix
		block.setData((byte) 1);
	}

	@Override
	public ItemStack getItemInMainHand(Player player) {
		return player.getInventory().getItemInMainHand();
	}

	@Override
	public ItemStack getItemInOffHand(Player player) {
		return player.getInventory().getItemInOffHand();
	}

	@Override
	public double getEntityMaxHealth(LivingEntity livingEntity) {
		return livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
	}

	@Override
	public void setEntityMaxHealth(LivingEntity livingEntity, double health) {
		livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
	}

	@Override
	public void resetEntityMaxHealth(LivingEntity livingEntity) {
		livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
	}

	@SuppressWarnings("deprecation")
	@Override
	public double[] getRecentTps() {
		// Deprecated but still the way spigot does it in the /tps command
		return MinecraftServer.getServer().recentTps;
	}

	@Override
	public int getPlayerPing(Player player) {
		return ((CraftPlayer) player).getHandle().ping;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void cloneBlockData(Block source, Block target) {
		target.setData(source.getData());
	}

	@Override
	public void setItemInMainHand(Player player, ItemStack item) {
		player.getInventory().setItemInMainHand(item);
	}

	@Override
	public void setItemInOffHand(Player player, ItemStack item) {
		player.getInventory().setItemInOffHand(item);
	}

	@Override
	public void sendTabList(Player player, String header, String footer) {
		CraftPlayer craftplayer = (CraftPlayer) player;
		PlayerConnection connection = craftplayer.getHandle().playerConnection;
		IChatBaseComponent headerJSON = ChatSerializer.a("{\"text\": \"" + header + "\"}");
		IChatBaseComponent footerJSON = ChatSerializer.a("{\"text\": \"" + footer + "\"}");
		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

		try {
			Field headerField = packet.getClass().getDeclaredField("a");
			headerField.setAccessible(true);
			headerField.set(packet, headerJSON);
			headerField.setAccessible(!headerField.isAccessible());

			Field footerField = packet.getClass().getDeclaredField("b");
			footerField.setAccessible(true);
			footerField.set(packet, footerJSON);
			footerField.setAccessible(!footerField.isAccessible());
		} catch (Exception e) {
			e.printStackTrace();
		}

		connection.sendPacket(packet);
	}

	@Override
	public void damagePlayer(Player player, PlayerDamageReason reason, float damage) {
		DamageSource source;

		switch (reason) {
		case FALL:
			source = DamageSource.FALL;

		case FALLING_BLOCK:
			source = DamageSource.FALLING_BLOCK;
			break;
		case OUT_OF_WORLD:
			source = DamageSource.OUT_OF_WORLD;
			break;

		case BURN:
			source = DamageSource.BURN;
			break;

		case LIGHTNING:
			source = DamageSource.LIGHTNING;
			break;

		case MAGIC:
			source = DamageSource.MAGIC;
			break;

		case DROWN:
			source = DamageSource.DROWN;
			break;

		case STARVE:
			source = DamageSource.STARVE;
			break;

		case LAVA:
			source = DamageSource.LAVA;
			break;

		case GENERIC:
			source = DamageSource.GENERIC;
			break;

		default:
			source = DamageSource.GENERIC;
			break;
		}

		((CraftPlayer) player).getHandle().damageEntity(source, damage);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setColoredBlock(Block block, DyeColor color, ColoredBlockType type) {
		MaterialData data = getColoredBlockMaterialData(color, type);

		block.setType(data.getItemType());

		block.setData(data.getData());
	}

	@SuppressWarnings("deprecation")
	private MaterialData getColoredBlockMaterialData(DyeColor color, ColoredBlockType type) {
		Material material = null;

		switch (type) {
		case GLASS_PANE:
			material = Material.STAINED_GLASS_PANE;
			break;

		case GLASS_BLOCK:
			material = Material.STAINED_GLASS;
			break;

		case WOOL:
			material = Material.WOOL;
			break;

		case CLAY:
			material = Material.STAINED_CLAY;
			break;

		default:
			material = Material.STAINED_GLASS;
			break;
		}

		MaterialData data = new MaterialData(material, color.getWoolData());

		return data;
	}

	@Override
	public void setShapedRecipeIngredientAsColoredBlock(ShapedRecipe recipe, char ingredient, ColoredBlockType type, DyeColor color) {
		MaterialData data = getColoredBlockMaterialData(color, type);

		recipe.setIngredient(ingredient, data);
	}

	@Override
	public void addShapelessRecipeIngredientAsColoredBlock(ShapelessRecipe recipe, char ingredient, ColoredBlockType type, DyeColor color) {
		MaterialData data = getColoredBlockMaterialData(color, type);

		recipe.addIngredient(ingredient, data);
	}

	@Override
	public ItemStack getColoredItem(DyeColor color, ColoredBlockType type) {
		MaterialData data = getColoredBlockMaterialData(color, type);

		@SuppressWarnings("deprecation")
		ItemStack stack = new ItemStack(data.getItemType(), 1, (short) data.getData());

		stack.setData(data);

		return stack;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void attachMapView(ItemStack item, MapView mapView) {
		item.setDurability(mapView.getId());
	}

	@SuppressWarnings("deprecation")
	@Override
	public MapView getAttachedMapView(ItemStack item) {
		return Bukkit.getMap(item.getDurability());
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getMapViewId(MapView mapView) {
		return (int) mapView.getId();
	}

	@Override
	public Sound getSound(VersionIndependentSound sound) {
		switch (sound) {
		case NOTE_PLING:
			return Sound.BLOCK_NOTE_PLING;

		case NOTE_HAT:
			return Sound.BLOCK_NOTE_HAT;

		case WITHER_DEATH:
			return Sound.ENTITY_WITHER_DEATH;

		case WITHER_HURT:
			return Sound.ENTITY_WITHER_HURT;

		case ITEM_BREAK:
			return Sound.ENTITY_ITEM_BREAK;

		case ITEM_PICKUP:
			return Sound.ENTITY_ITEM_PICKUP;

		case ORB_PICKUP:
			return Sound.ENTITY_EXPERIENCE_ORB_PICKUP;

		case ANVIL_LAND:
			return Sound.BLOCK_ANVIL_LAND;

		case EXPLODE:
			return Sound.ENTITY_GENERIC_EXPLODE;

		case LEVEL_UP:
			return Sound.ENTITY_PLAYER_LEVELUP;

		case WITHER_SHOOT:
			return Sound.ENTITY_WITHER_SHOOT;

		case EAT:
			return Sound.ENTITY_GENERIC_EAT;

		case ANVIL_BREAK:
			return Sound.BLOCK_ANVIL_BREAK;

		case FIZZ:
			return Sound.BLOCK_FIRE_EXTINGUISH;

		default:
			setLastError(VersionIndependenceLayerError.MISSING_SOUND);
			AbstractionLogger.getLogger().error("VersionIndependentUtils", "VersionIndependantSound " + sound.name() + " is not defined in this version. Please add it to " + this.getClass().getName());
			return null;
		}
	}

	@Override
	public void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
		player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
	}

	@Override
	public ItemStack getPlayerSkullWithBase64Texture(String b64stringtexture) {
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		PropertyMap propertyMap = profile.getProperties();
		if (propertyMap == null) {
			throw new IllegalStateException("Profile doesn't contain a property map");
		}
		propertyMap.put("textures", new Property("textures", b64stringtexture));
		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		ItemMeta headMeta = head.getItemMeta();
		Class<?> headMetaClass = headMeta.getClass();
		try {
			getField(headMetaClass, "profile", GameProfile.class, 0).set(headMeta, profile);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		head.setItemMeta(headMeta);
		return head;
	}

	@Override
	public VersionIndependentItems getVersionIndependantItems() {
		return new net.zeeraa.novacore.spigot.version.v1_12_R1.VersionIndependantItems();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setShapedRecipeIngredientAsPlayerSkull(ShapedRecipe recipe, char ingredient) {
		MaterialData skull = new MaterialData(Material.SKULL_ITEM);

		skull.setData((byte) 3);

		recipe.setIngredient(ingredient, skull);
	}

	@Override
	public Material getMaterial(VersionIndependentMaterial material) {
		switch (material) {
		case FILLED_MAP:
			return Material.MAP;

		case END_STONE:
			return Material.ENDER_STONE;

		case WORKBENCH:
			return Material.WORKBENCH;

		case OAK_BOAT:
			return Material.BOAT;

		case DIAMOND_SHOVEL:
			return Material.DIAMOND_SPADE;

		case SNOWBALL:
			return Material.SNOW_BALL;

		case FARMLAND:
			return Material.SOIL;

		case GOLDEN_AXE:
			return Material.GOLD_AXE;

		case GOLDEN_HOE:
			return Material.GOLD_HOE;

		case GOLDEN_PICKAXE:
			return Material.GOLD_PICKAXE;

		case GOLDEN_SHOVEL:
			return Material.GOLD_SPADE;

		case GOLDEN_SWORD:
			return Material.GOLD_SWORD;

		case WOODEN_AXE:
			return Material.WOOD_AXE;

		case WOODEN_HOE:
			return Material.WOOD_HOE;

		case WOODEN_PICKAXE:
			return Material.WOOD_PICKAXE;

		case WOODEN_SHOVEL:
			return Material.WOOD_SPADE;

		case WOODEN_SWORD:
			return Material.WOOD_SWORD;

		case WATCH:
			return Material.WATCH;

		default:
			setLastError(VersionIndependenceLayerError.MISSING_MATERIAL);
			AbstractionLogger.getLogger().warning("VersionIndependentUtils", "Unknown version Independent material: " + material.name());
			return null;
		}
	}

	private LabyModProtocolImpl lmp = null;

	@Override
	public LabyModProtocol getLabyModProtocol() {
		if (lmp == null) {
			lmp = new LabyModProtocolImpl();
		}
		return lmp;
	}

	@Override
	public NovaCoreGameVersion getNovaCoreGameVersion() {
		return NovaCoreGameVersion.V_1_12;
	}

	@Override
	public ItemStack getPlayerSkullitem() {
		return new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
	}

	@Override
	public boolean isSign(Material material) {
		return material == Material.SIGN_POST || material == Material.WALL_SIGN;
	}

	@Override
	public void sendActionBarMessage(Player player, String message) {
		PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + message.replace("&", "§") + "\"}"), ChatMessageType.GAME_INFO);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	@Override
	public int getMinY() {
		return 0;
	}

	@Override
	public ItemMeta setUnbreakable(ItemMeta meta, boolean unbreakable) {
		meta.setUnbreakable(unbreakable);
		return meta;
	}

	@Override
	public void setCreatureItemInMainHand(Creature creature, ItemStack item) {
		creature.getEquipment().setItemInMainHand(item);
	}

	@Override
	public float getPlayerBodyRotation(Player player) {
		EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
		return nmsPlayer.aN;
	}

	@Override
	public void setCustomModelData(ItemMeta meta, int data) {
		// Does not exist for this version
	}

	@Override
	public void setGameRule(World world, String rule, String value) {
		world.setGameRuleValue(rule, value);
	}

	@Override
	public boolean isInteractEventMainHand(PlayerInteractEvent e) {
		return e.getHand() == EquipmentSlot.HAND;
	}

	@Override
	public Entity getEntityByUUID(UUID uuid) {
		return Bukkit.getEntity(uuid);
	}

	@SuppressWarnings("deprecation")
	private MaterialData getDyeMaterialData(DyeColor color) {
		return new MaterialData(Material.INK_SACK, color.getDyeData());
	}

	@Override
	public void setShapedRecipeIngredientAsDye(ShapedRecipe recipe, char ingredient, DyeColor color) {
		recipe.setIngredient(ingredient, getDyeMaterialData(color));
	}

	@Override
	public void addShapelessRecipeIngredientAsDye(ShapelessRecipe recipe, int count, DyeColor color) {
		recipe.addIngredient(count, getDyeMaterialData(color));
	}

	@Override
	public void setAI(LivingEntity entity, boolean ai) {
		entity.setAI(ai);
	}
	
	@Override
	public boolean isBed(Material material) {
		// Faster implementation since 1.12.2 only have 1 type of bed
		return material == Material.BED;
	}
}