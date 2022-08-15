package net.zeeraa.novacore.spigot.gameengine.module.modules.game;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.entity.Player;

public interface CombatTagMessage extends wOmeN {
	public void showTaggedMessage(Player player);

	public void showNoLongerTaggedMessage(Player player);
}