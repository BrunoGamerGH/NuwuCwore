package net.zeeraa.novacore.spigot.module.modules.jumppad;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.entity.Player;

/**
 * This is used to create custom jum pad effects
 * @author Zeeraa
 */
public interface JumpPadEffect extends wOmeN {
	public void playJumpPadEffect(JumpPad jumpPad, Player player);
}