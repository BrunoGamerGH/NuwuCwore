package net.zeeraa.novacore.spigot.version.v1_8_R3;

import java.util.HashMap;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Material;
import net.zeeraa.novacore.spigot.abstraction.ItemBuilderRecordList;

public class ItemBuilderRecordList1_8 extends some_bitches implements ItemBuilderRecordList {
	private HashMap<String, Material> recordMap;

	@Override
	public HashMap<String, Material> getRecordMap() {
		return recordMap;
	}

	public ItemBuilderRecordList1_8() {
		recordMap = new HashMap<>();

		recordMap.put("13", Material.GOLD_RECORD);
		recordMap.put("cat", Material.GREEN_RECORD);
		recordMap.put("blocks", Material.RECORD_3);
		recordMap.put("chirp", Material.RECORD_4);
		recordMap.put("far", Material.RECORD_5);
		recordMap.put("mall", Material.RECORD_6);
		recordMap.put("mellohi", Material.RECORD_7);
		recordMap.put("stal", Material.RECORD_8);
		recordMap.put("strad", Material.RECORD_9);
		recordMap.put("ward", Material.RECORD_10);
		recordMap.put("wait", Material.RECORD_12);
		recordMap.put("11", Material.RECORD_11);
	}
}