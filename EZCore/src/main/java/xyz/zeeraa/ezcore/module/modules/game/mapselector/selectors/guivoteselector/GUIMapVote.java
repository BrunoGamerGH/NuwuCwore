package xyz.zeeraa.ezcore.module.modules.game.mapselector.selectors.guivoteselector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import xyz.zeeraa.ezcore.log.EZLogger;
import xyz.zeeraa.ezcore.module.modules.game.map.GameMapData;
import xyz.zeeraa.ezcore.module.modules.game.mapselector.MapSelector;
import xyz.zeeraa.ezcore.module.modules.gamelobby.events.PlayerJoinGameLobbyEvent;
import xyz.zeeraa.ezcore.utils.ItemBuilder;

public class GUIMapVote extends MapSelector implements Listener {
	private HashMap<UUID, String> votes;
	private HashMap<UUID, Inventory> playerVoteInventory;

	private String forcedMap;

	public GUIMapVote() {
		this.votes = new HashMap<UUID, String>();
		this.playerVoteInventory = new HashMap<UUID, Inventory>();

		this.forcedMap = null;
	}

	public void showPlayer(Player player) {
		if (!playerVoteInventory.containsKey(player.getUniqueId())) {
			Inventory voteInventory = Bukkit.createInventory(new MapVoteInventoryHolder(), 9, ChatColor.GOLD + "" + ChatColor.BOLD + "Vote for map");
			playerVoteInventory.put(player.getUniqueId(), voteInventory);

			updateInventory(player);

		}

		player.openInventory(playerVoteInventory.get(player.getUniqueId()));
	}

	private void updateInventory(Player player) {
		if (playerVoteInventory.containsKey(player.getUniqueId())) {
			Inventory voteInventory = playerVoteInventory.get(player.getUniqueId());
			MapVoteInventoryHolder holder = (MapVoteInventoryHolder) voteInventory.getHolder();

			holder.getMapSlots().clear();

			int slot = 0;
			for (GameMapData map : getMaps()) {
				ItemStack voteItem = new ItemStack(map.isEnabled() ? Material.EMERALD : Material.COAL);

				ItemMeta meta = voteItem.getItemMeta();

				boolean selected = false;

				if (votes.containsKey(player.getUniqueId())) {
					if (map.getMapName().equals(votes.get(player.getUniqueId()))) {
						selected = true;
					}
				}

				ArrayList<String> lore = new ArrayList<String>();

				for (String line : map.getDescription().split("\n")) {
					lore.add(line);
				}

				if (!map.isEnabled()) {
					lore.add("");
					lore.add(ChatColor.RESET + "" + ChatColor.DARK_RED + "Disabled");
				}

				meta.setLore(lore);

				meta.setDisplayName((selected ? ChatColor.GREEN : ChatColor.GOLD) + "" + ChatColor.BOLD + map.getDisplayName());

				if (selected) {
					meta.addEnchant(Enchantment.DURABILITY, 1, false);
					meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				}

				voteItem.setItemMeta(meta);

				voteInventory.setItem(slot, voteItem);

				if (map.isEnabled()) {
					holder.getMapSlots().put(slot, map.getMapName());
				}

				slot++;

				if (voteInventory.getSize() < slot) {
					break;
				}
			}
		}
	}

	@Override
	public GameMapData getMapToUse() {
		if (forcedMap != null) {
			EZLogger.info("Using forced map " + forcedMap);
			return this.getMap(forcedMap);
		} else {
			HashMap<String, Integer> voteCount = new HashMap<String, Integer>();
			for (GameMapData map : maps) {
				int mapVotes = 0;

				for (UUID uuid : votes.keySet()) {
					if (votes.get(uuid).equalsIgnoreCase(map.getMapName())) {
						mapVotes++;
					}
				}

				EZLogger.debug("Map " + map.getDisplayName() + " has " + mapVotes + " votes");

				voteCount.put(map.getMapName(), mapVotes);
			}

			String mapName = null;
			int maxVotes = -1;

			List<String> keys = new ArrayList<String>(voteCount.keySet());
			Collections.shuffle(keys);
			for (String key : keys) {
				int votes = voteCount.get(key);

				if (votes > maxVotes) {
					mapName = key;
				}
			}

			if (mapName != null) {
				return this.getMap(mapName);
			}
		}

		return null;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent e) {
		if (votes.containsKey(e.getPlayer().getUniqueId())) {
			votes.remove(e.getPlayer().getUniqueId());
		}

		if (playerVoteInventory.containsKey(e.getPlayer().getUniqueId())) {
			playerVoteInventory.remove(e.getPlayer().getUniqueId());
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoinGameLobby(PlayerJoinGameLobbyEvent e) {
		ItemStack item = new ItemBuilder(Material.COMPASS).setName(ChatColor.GOLD + "" + ChatColor.BOLD + "Map selector").build();
		item = NBTEditor.set(item, 1, "ezcore", "mapselector");

		e.getPlayer().getInventory().addItem(item);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		if (e.getItemDrop().getItemStack().getType() == Material.COMPASS) {
			if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
				if (NBTEditor.contains(e.getItemDrop().getItemStack(), "ezcore", "mapselector")) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getCurrentItem() != null) {
			if (e.getCurrentItem().getType() == Material.COMPASS) {
				if (e.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
					if (NBTEditor.contains(e.getCurrentItem(), "ezcore", "mapselector")) {
						e.setCancelled(true);
						return;
					}
				}
			}
		}
		
		if (e.getInventory().getHolder() instanceof MapVoteInventoryHolder) {
			e.setCancelled(true);
			if (e.getClickedInventory() != null) {
				if (e.getClickedInventory().getHolder() instanceof MapVoteInventoryHolder) {
					MapVoteInventoryHolder holder = (MapVoteInventoryHolder) e.getClickedInventory().getHolder();

					Player p = (Player) e.getWhoClicked();

					if (holder.getMapSlots().containsKey(e.getSlot())) {
						String mapName = holder.getMapSlots().get(e.getSlot());

						boolean changed = false;

						if (e.getClick() == ClickType.MIDDLE) {
							if (p.hasPermission("ezcore.gamelobby.forcemap")) {
								if (forcedMap == mapName) {
									forcedMap = null;
									Bukkit.getServer().broadcast(ChatColor.GREEN + "Set forced map to null", "ezcore.gamelobby.forcemap");
									EZLogger.info("Set forced map to null");
								} else {
									forcedMap = mapName;
									Bukkit.getServer().broadcast(ChatColor.GREEN + "Set forced map to " + mapName, "ezcore.gamelobby.forcemap");
									EZLogger.info("Set forced map to " + mapName);
								}
							}
						}

						if (votes.containsKey(p.getUniqueId())) {
							if (votes.get(p.getUniqueId()).equals(mapName)) {
								return;
							}
							votes.remove(p.getUniqueId());
							changed = true;
						}

						votes.put(p.getUniqueId(), mapName);

						p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
						p.sendMessage(ChatColor.GREEN + (changed ? "You changed your vote to " : "You voted for ") + this.getMap(mapName).getDisplayName());

						updateInventory(p);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getItem() != null) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (e.getItem().getType() == Material.COMPASS) {
					if (NBTEditor.contains(e.getItem(), "ezcore", "mapselector")) {
						showPlayer(e.getPlayer());
					}
				}
			}
		}
	}
}