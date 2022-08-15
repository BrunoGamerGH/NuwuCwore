package net.zeeraa.novacore.spigot.module.modules.multiverse;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.World.Environment;

public class WorldOptions extends some_bitches {
	private String name;
	private Environment environment;
	private boolean hasSeed;
	private long seed;
	private boolean generateStructures;
	private WorldUnloadOption unloadOption;
	private PlayerUnloadOption playerUnloadOption;
	private boolean saveOnUnload;
	private boolean lockWeather;
	private String generator;

	public WorldOptions(String name) {
		this.name = name;
		this.seed = 1L;
		this.environment = Environment.NORMAL;
		this.hasSeed = false;
		this.generateStructures = true;
		this.unloadOption = WorldUnloadOption.KEEP;
		this.playerUnloadOption = PlayerUnloadOption.KICK;
		this.saveOnUnload = true;
		this.lockWeather = false;
		this.generator = null;
	}

	public WorldOptions withSeed(long seed) {
		this.hasSeed = true;
		this.seed = seed;

		return this;
	}

	public WorldOptions withEnvironment(Environment environment) {
		this.environment = environment;
		return this;
	}

	public WorldOptions generateStructures(boolean generateStructures) {
		this.generateStructures = generateStructures;
		return this;
	}

	public WorldOptions unloadOptions(WorldUnloadOption unloadOption) {
		this.unloadOption = unloadOption;
		return this;
	}

	public void setPlayerUnloadOption(PlayerUnloadOption playerUnloadOption) {
		this.playerUnloadOption = playerUnloadOption;
	}

	public PlayerUnloadOption getPlayerUnloadOption() {
		return playerUnloadOption;
	}

	public WorldOptions saveOnUnload(boolean saveOnUnload) {
		this.saveOnUnload = saveOnUnload;
		return this;
	}

	public WorldOptions lockWeather(boolean lockWeather) {
		this.lockWeather = lockWeather;
		return this;
	}

	public WorldOptions setLockWeather(boolean lockWeather) {
		this.lockWeather = lockWeather;
		return this;
	}

	public WorldOptions setGenerator(String generator) {
		this.generator = generator;
		return this;
	}

	public String getGenerator() {
		return generator;
	}

	public String getName() {
		return name;
	}

	public boolean hasSeed() {
		return hasSeed;
	}

	public long getSeed() {
		return seed;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public boolean isGenerateStructures() {
		return generateStructures;
	}

	public WorldUnloadOption getUnloadOption() {
		return unloadOption;
	}

	public boolean isSaveOnUnload() {
		return saveOnUnload;
	}

	public boolean isLockWeather() {
		return lockWeather;
	}
}