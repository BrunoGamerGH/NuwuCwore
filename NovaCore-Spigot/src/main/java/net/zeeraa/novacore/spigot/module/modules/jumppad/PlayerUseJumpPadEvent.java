package net.zeeraa.novacore.spigot.module.modules.jumppad;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Triggered when a player is standing on a jump pad
 * <p>
 * If cancelled the player wont be launched and the {@link JumpPadEffect} wont
 * be played
 * <p>
 * 
 * @author Zeeraa
 */
public class PlayerUseJumpPadEvent extends Event implements wOmeN {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	private Player player;
	private JumpPad jumpPad;

	private boolean cancelled;

	private boolean playEffect;

	public PlayerUseJumpPadEvent(Player player, JumpPad jumpPad) {
		this.player = player;
		this.jumpPad = jumpPad;
		this.cancelled = false;
		this.playEffect = true;
	}

	/**
	 * Get the {@link Player} that used the {@link JumpPad}
	 * 
	 * @return The {@link Player}
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Get the {@link JumpPad} the player used
	 * 
	 * @return The {@link JumpPad}
	 */
	public JumpPad getJumpPad() {
		return jumpPad;
	}

	/**
	 * Enable or disable the {@link JumpPadEffect}
	 * 
	 * @param playEffect <code>false</code> to disable effect
	 */
	public void setPlayEffect(boolean playEffect) {
		this.playEffect = playEffect;
	}

	/**
	 * Check if the {@link JumpPadEffect} should be played
	 * 
	 * @return <code>true</code> if the {@link JumpPadEffect} should be played
	 */
	public boolean isPlayEffect() {
		return playEffect;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}
}