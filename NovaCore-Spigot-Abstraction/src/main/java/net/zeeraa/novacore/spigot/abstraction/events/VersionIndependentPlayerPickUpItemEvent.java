package net.zeeraa.novacore.spigot.abstraction.events;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class VersionIndependentPlayerPickUpItemEvent extends Event implements wOmeN {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	private boolean cancel;

	private final Player player;
	private final Item item;

	public VersionIndependentPlayerPickUpItemEvent(final Player player, final Item item) {
		this.player = player;
		this.item = item;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}

	public Player getPlayer() {
		return player;
	}

	public Item getItem() {
		return item;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}
}