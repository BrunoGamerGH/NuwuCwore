package net.zeeraa.novacore.spigot.gameengine.lootdrop.medical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.commons.tasks.Task;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.abstraction.VersionIndependentUtils;
import net.zeeraa.novacore.spigot.abstraction.enums.NovaCoreGameVersion;
import net.zeeraa.novacore.spigot.module.NovaModule;
import net.zeeraa.novacore.spigot.module.modules.lootdrop.particles.LootdropParticleEffect;
import net.zeeraa.novacore.spigot.tasks.SimpleTask;
import net.zeeraa.novacore.spigot.utils.LocationUtils;
import xyz.xenondevs.particle.ParticleEffect;

public class MedicalSupplyDropManager extends NovaModule implements Listener, wOmeN {
	private static MedicalSupplyDropManager instance;

	private List<MedicalSupplyDrop> chests;
	private List<MedicalSupplyDropEffect> dropEffects;

	private Map<UUID, LootdropParticleEffect> particleEffects;

	private Task particleTask;

	private int taskId;

	public static MedicalSupplyDropManager getInstance() {
		return instance;
	}

	public MedicalSupplyDropManager() {
		super("NovaUniverse.MedicalSupplyDropManager");
	}

	@Override
	public void onLoad() {
		MedicalSupplyDropManager.instance = this;
		chests = new ArrayList<MedicalSupplyDrop>();
		dropEffects = new ArrayList<MedicalSupplyDropEffect>();
		particleEffects = new HashMap<UUID, LootdropParticleEffect>();
		taskId = -1;

		this.particleTask = new SimpleTask(NovaCore.getInstance(), new Runnable() {
			@Override
			public void run() {
				particleEffects.values().forEach(e -> e.update());
			}
		}, 2L);
	}

	@Override
	public void onEnable() {
		taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(NovaCore.getInstance(), new Runnable() {
			@Override
			public void run() {
				for (int i = dropEffects.size(); i > 0; i--) {
					if (dropEffects.get(i - 1).isCompleted()) {
						dropEffects.remove(i - 1);
					}
				}
			}
		}, 20L, 20L);

		particleTask.start();
	}

	@Override
	public void onDisable() {
		Task.tryStopTask(particleTask);
		this.destroy();
	}

	public void destroy() {
		if (taskId != -1) {
			Bukkit.getScheduler().cancelTask(taskId);
		}

		dropEffects.forEach(effect -> effect.undoBlocks());

		particleEffects.clear();

		for (int i = chests.size(); i > 0; i--) {
			removeChest(chests.get(i - 1));
		}
	}

	public void removeFromWorld(World world) {
		for (MedicalSupplyDropEffect effect : dropEffects) {
			if (effect.getWorld().equals(world)) {
				effect.undoBlocks();
			}
		}

		List<UUID> removeParticles = new ArrayList<UUID>();

		particleEffects.keySet().forEach(uuid -> {
			if (particleEffects.get(uuid).getLocation().getWorld().equals(world)) {
				removeParticles.add(uuid);
			}
		});

		removeParticles.forEach(uuid -> particleEffects.remove(uuid));

		for (int i = chests.size(); i > 0; i--) {
			if (chests.get(i).getWorld().equals(world)) {
				this.removeChest(chests.get(i - 1));
			}
		}
	}

	public boolean spawnDrop(Location location, String lootTable) {
		return spawnDrop(location, lootTable, true);
	}

	public boolean spawnDrop(Location location, String lootTable, boolean announce) {
		if (canSpawnAt(location)) {
			MedicalSupplyDropEffect effect = new MedicalSupplyDropEffect(location, lootTable);
			dropEffects.add(effect);
			if (announce) {
				Bukkit.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "A medical supply drop is spawning at X: " + ChatColor.AQUA + "" + ChatColor.BOLD + location.getBlockX() + ChatColor.RED + "" + ChatColor.BOLD + " Z: " + ChatColor.AQUA + "" + ChatColor.BOLD + location.getBlockZ());
			}
			return true;
		}
		return false;
	}

	public boolean canSpawnAt(Location location) {
		for (MedicalSupplyDropEffect effect : dropEffects) {
			if (effect.getLocation().getBlockX() == location.getBlockX()) {
				if (effect.getLocation().getBlockZ() == location.getBlockZ()) {
					return false;
				}
			}
		}

		if (this.isPotentialyADrop(location.getBlock().getType())) {
			return false;
		}

		if (LocationUtils.isOutsideOfBorder(location)) {
			return false;
		}

		return true;
	}

	public void spawnChest(Location location, String lootTable) {
		MedicalSupplyDrop drop = new MedicalSupplyDrop(location, lootTable);
		chests.add(drop);

		Location particleLocation = new Location(location.getWorld(), LocationUtils.blockCenter(location.getBlockX()), location.getY() + 0.8, LocationUtils.blockCenter(location.getBlockZ()));

		particleEffects.put(drop.getUuid(), new LootdropParticleEffect(particleLocation, ParticleEffect.HEART, 0, 0, 0, false));
	}

	public MedicalSupplyDrop getChestAtLocation(Location location) {
		for (MedicalSupplyDrop chest : chests) {
			if (chest.getLocation().getWorld() == location.getWorld()) {
				if (chest.getLocation().getBlockX() == location.getBlockX()) {
					if (chest.getLocation().getBlockY() == location.getBlockY()) {
						if (chest.getLocation().getBlockZ() == location.getBlockZ()) {
							return chest;
						}
					}
				}
			}
		}

		return null;
	}

	public void removeChest(MedicalSupplyDrop chest) {
		chests.remove(chest);
		chest.remove();

		if (particleEffects.containsKey(chest.getUuid())) {
			particleEffects.remove(chest.getUuid());
		}
	}

	public MedicalSupplyDrop getChestByUUID(UUID uuid) {
		for (MedicalSupplyDrop chest : chests) {
			if (chest.getUuid() == uuid) {
				return chest;
			}
		}

		return null;
	}

	private boolean isInventoryEmpty(Inventory inventory) {
		for (ItemStack i : inventory.getContents()) {
			if (i == null) {
				continue;
			}

			if (i.getType() != Material.AIR) {
				return false;
			}
		}

		return true;
	}

	public boolean isDropActiveAt(World world, int x, int z) {
		for (MedicalSupplyDropEffect e : dropEffects) {
			if (e.getWorld().getName().equalsIgnoreCase(world.getName())) {
				if (e.getLocation().getBlockX() == x) {
					if (e.getLocation().getBlockZ() == z) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean isPotentialyADrop(Material material) {
		return material.name().equals("SKULL") || material.name().equals("PLAYER_HEAD");
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onEntityDamage(EntityDamageEvent e) {
		if (e.getCause() == DamageCause.ENTITY_EXPLOSION) {
			if (VersionIndependentUtils.get().getNovaCoreGameVersion() == NovaCoreGameVersion.V_1_8) {
				return;
			}

			this.dropEffects.forEach(effect -> {
				if (effect.getWorld() == e.getEntity().getWorld()) {
					if (e.getEntity().getLocation().distance(effect.getFireworkLocation()) < 7) {
						e.setCancelled(true);
					}
				}
			});
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onInventoryClose(InventoryCloseEvent e) {
		if (e.getInventory().getHolder() instanceof MedicalSupplyDropInventoryHolder) {
			if (isInventoryEmpty(e.getInventory())) {
				UUID uuid = ((MedicalSupplyDropInventoryHolder) e.getInventory().getHolder()).getUuid();

				MedicalSupplyDrop chest = this.getChestByUUID(uuid);

				if (chest != null) {
					if (chest.isRemoved()) {
						return;
					}

					removeChest(chest);
					chest.getWorld().playSound(chest.getLocation(), Sound.WITHER_HURT, 1F, 1F);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (this.isPotentialyADrop(e.getClickedBlock().getType())) {
				MedicalSupplyDrop chest = this.getChestAtLocation(e.getClickedBlock().getLocation());

				if (chest != null) {
					e.getPlayer().openInventory(chest.getInventory());
					e.setCancelled(true);
				}
			} else if (e.getClickedBlock().getType() == Material.BEACON) {
				for (MedicalSupplyDropEffect effect : dropEffects) {
					for (Location location : effect.getRemovedBlocks().keySet()) {
						if (location.equals(e.getClickedBlock().getLocation())) {
							Log.trace("Preventing player from interacting with MedicalSupplyDrop beacon");
							e.setCancelled(true);
							return;
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent e) {
		for (MedicalSupplyDropEffect effect : dropEffects) {
			for (Location location : effect.getRemovedBlocks().keySet()) {
				if (location.equals(e.getBlock().getLocation())) {
					Log.trace("Preventing player from breaking MedicalSupplyDrop");
					e.setCancelled(true);
					return;
				}
			}
		}

		if (this.isPotentialyADrop(e.getBlock().getType())) {
			MedicalSupplyDrop chest = this.getChestAtLocation(e.getBlock().getLocation());

			if (chest != null) {
				e.setCancelled(true);
				return;
			}
		}
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}