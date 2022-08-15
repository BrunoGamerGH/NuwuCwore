package net.zeeraa.novacore.spigot.version.v1_16_R3;

import java.util.HashMap;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Material;
import net.zeeraa.novacore.spigot.abstraction.ItemBuilderRecordList;

public class ItemBuilderRecordList1_16 extends some_bitches implements ItemBuilderRecordList {
	private HashMap<String, Material> recordMap;

	@Override
	public HashMap<String, Material> getRecordMap() {
		return recordMap;
	}

	public ItemBuilderRecordList1_16() {
		recordMap = new HashMap<>();

		recordMap.put("11", Material.MUSIC_DISC_11);
		recordMap.put("13", Material.MUSIC_DISC_13);
		recordMap.put("blocks", Material.MUSIC_DISC_BLOCKS);
		recordMap.put("cat", Material.MUSIC_DISC_CAT);
		recordMap.put("chirp", Material.MUSIC_DISC_CHIRP);
		recordMap.put("far", Material.MUSIC_DISC_FAR);
		recordMap.put("mall", Material.MUSIC_DISC_MALL);
		recordMap.put("mellohi", Material.MUSIC_DISC_MELLOHI);
		recordMap.put("pigstep", Material.MUSIC_DISC_PIGSTEP);
		recordMap.put("stal", Material.MUSIC_DISC_STAL);
		recordMap.put("strad", Material.MUSIC_DISC_STRAD);
		recordMap.put("wait", Material.MUSIC_DISC_WAIT);
		recordMap.put("ward", Material.MUSIC_DISC_WARD);
	}
}