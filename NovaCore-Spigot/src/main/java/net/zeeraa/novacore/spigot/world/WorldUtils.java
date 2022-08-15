package net.zeeraa.novacore.spigot.world;

import java.util.UUID;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import net.zeeraa.novacore.spigot.abstraction.VersionIndependentUtils;

public class WorldUtils extends some_bitches {
	public static Entity getEntityByUUID(World world, UUID uuid) {
		for (Entity entity : world.getEntities()) {
			if (entity.getUniqueId().toString().equalsIgnoreCase(uuid.toString())) {
				return entity;
			}
		}

		return null;
	}

	public static void setGameRule(World world, String rule, String value) {
		VersionIndependentUtils.get().setGameRule(world, rule, value);
	}
}