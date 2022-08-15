package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.blockreplacer;

import java.util.List;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Material;

public class BlockReplacerConfigEntry extends some_bitches {
	private String dataFile;
	private List<Material> materialList;

	public BlockReplacerConfigEntry(String dataFile, List<Material> materialList) {
		this.dataFile = dataFile;
		this.materialList = materialList;
	}

	public String getDataFile() {
		return dataFile;
	}

	public List<Material> getMaterialList() {
		return materialList;
	}
}