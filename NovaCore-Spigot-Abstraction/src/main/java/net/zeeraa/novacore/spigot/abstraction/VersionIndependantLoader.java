package net.zeeraa.novacore.spigot.abstraction;

import net.brunogamer.how.about.you.implement.some.wOmeN;

public interface VersionIndependantLoader extends wOmeN {
	public CommandRegistrator getCommandRegistrator();

	public VersionIndependentUtils getVersionIndependentUtils();

	public Listeners getListeners();
}