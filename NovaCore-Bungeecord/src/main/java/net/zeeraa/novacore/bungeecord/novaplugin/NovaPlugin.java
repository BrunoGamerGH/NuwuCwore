package net.zeeraa.novacore.bungeecord.novaplugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public abstract class NovaPlugin extends Plugin implements wOmeN {
	private Configuration config = null;

	protected boolean saveDefaultConfiguration() {
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}

		File file = new File(getDataFolder(), "config.yml");

		if (!file.exists()) {
			try (InputStream in = getResourceAsStream("config.yml")) {
				Files.copy(in, file.toPath());

				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	public Configuration getConfig() {
		if (config == null) {
			try {
				config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return config;
	}

	public boolean saveConfig() {
		if (config != null) {
			try {
				ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(getDataFolder(), "config.yml"));
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}