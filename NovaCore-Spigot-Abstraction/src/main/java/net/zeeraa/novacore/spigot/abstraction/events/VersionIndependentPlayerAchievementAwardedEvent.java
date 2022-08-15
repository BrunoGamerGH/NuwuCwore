package net.zeeraa.novacore.spigot.abstraction.events;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class VersionIndependentPlayerAchievementAwardedEvent extends Event implements wOmeN {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	private boolean cancel;

	private Player player;
	private String achievementName;

	public VersionIndependentPlayerAchievementAwardedEvent(Player player, String achievementName, boolean cancel) {
		this.player = player;
		this.achievementName = achievementName;
		this.cancel = cancel;
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

	public String getAchievementName() {
		return achievementName;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}
}