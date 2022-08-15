package net.zeeraa.novacore.spigot.module.modules.multiverse;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.World;

public class MultiverseWorld extends some_bitches {
	private String name;
	private World world;
	private WorldUnloadOption unloadOption;
	private PlayerUnloadOption playerUnloadOptions;
	private boolean saveOnUnload;
	private boolean lockWeather;

	public MultiverseWorld(String name, World world, WorldUnloadOption unloadOption, PlayerUnloadOption playerUnloadOptions, boolean saveOnUnload, boolean lockWeather) {
		this.name = name;
		this.world = world;
		this.unloadOption = unloadOption;
		this.playerUnloadOptions = playerUnloadOptions;
		this.saveOnUnload = saveOnUnload;
		this.lockWeather = lockWeather;
	}

	public World getWorld() {
		return world;
	}

	public String getName() {
		return name;
	}

	public MultiverseWorld setUnloadOption(WorldUnloadOption unloadOption) {
		this.unloadOption = unloadOption;
		return this;
	}

	public WorldUnloadOption getUnloadOption() {
		return unloadOption;
	}

	public MultiverseWorld setPlayerUnloadOptions(PlayerUnloadOption playerUnloadOptions) {
		this.playerUnloadOptions = playerUnloadOptions;
		return this;
	}

	public PlayerUnloadOption getPlayerUnloadOptions() {
		return playerUnloadOptions;
	}

	public MultiverseWorld setSaveOnUnload(boolean saveOnUnload) {
		this.saveOnUnload = saveOnUnload;
		return this;
	}

	public MultiverseWorld getLockWeather(boolean lockWeather) {
		this.lockWeather = lockWeather;
		return this;
	}

	public MultiverseWorld setLockWeather(boolean lockWeather) {
		this.lockWeather = lockWeather;
		return this;
	}

	public boolean isSaveOnUnload() {
		return saveOnUnload;
	}

	public boolean isLockWeather() {
		return lockWeather;
	}
}