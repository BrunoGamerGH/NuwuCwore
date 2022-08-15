package net.zeeraa.novacore.spigot.version.v1_8_R3;

import net.brunogamer.how.about.you.get.some_bitches;
import net.zeeraa.novacore.spigot.abstraction.CommandRegistrator;
import net.zeeraa.novacore.spigot.abstraction.Listeners;

public class VersionIndependentLoader extends some_bitches implements net.zeeraa.novacore.spigot.abstraction.VersionIndependantLoader {
	@Override
	public CommandRegistrator getCommandRegistrator() {
		return new net.zeeraa.novacore.spigot.version.v1_8_R3.CommandRegistrator();
	}

	@Override
	public VersionIndependentUtils getVersionIndependentUtils() {
		return new net.zeeraa.novacore.spigot.version.v1_8_R3.VersionIndependentUtils();
	}

	@Override
	public Listeners getListeners() {
		return new net.zeeraa.novacore.spigot.version.v1_8_R3.Listeners();
	}
}