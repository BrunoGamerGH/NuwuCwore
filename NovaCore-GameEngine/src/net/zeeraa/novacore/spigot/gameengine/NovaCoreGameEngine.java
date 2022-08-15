package net.zeeraa.novacore.spigot.gameengine;

import java.io.File;
import java.io.IOException;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.json.JSONException;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.commons.utils.JSONFileUtils;
import net.zeeraa.novacore.spigot.command.CommandRegistry;
import net.zeeraa.novacore.spigot.gameengine.command.commands.gamelobby.NovaCoreCommandGameLobby;
import net.zeeraa.novacore.spigot.gameengine.debugtriggers.GameEngineDebugTriggers;
import net.zeeraa.novacore.spigot.gameengine.lootdrop.medical.MedicalSupplyDropManager;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.GameManager;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModuleManager;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.blockloot.BlockLoot;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.blockreplacer.BlockReplacer;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.chestloot.ChestLoot;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.chunkloader.Chunkloader;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.farmlandprotection.FarmlandProtection;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.fireresistance.FireReistance;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.gamerule.Gamerule;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.giveitems.GiveItemInstant;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.giveitems.GiveItemSlow;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.graceperiod.falldamagegraceperiod.FallDamageGracePeriodMapModule;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.graceperiod.graceperiod.GracePeriodMapModule;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.handcraftingtable.HandCraftingTable;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.instantvoidkill.InstantVoidKill;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.lootdrop.LootDropMapModule;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.lootdrop.medical.MedicalSupplyDropMapModule;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.mapprotection.MapProtection;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.noweather.NoWeather;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.potioneffect.AddPotionEffect;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.settime.SetTime;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.simplemapdecay.SimpleBoxDecay;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.startmessage.StartMessage;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.worldborder.WorldborderMapModule;
import net.zeeraa.novacore.spigot.gameengine.module.modules.gamelobby.GameLobby;
import net.zeeraa.novacore.spigot.language.LanguageReader;
import net.zeeraa.novacore.spigot.module.ModuleManager;
import net.zeeraa.novacore.spigot.novaplugin.NovaPlugin;

public class NovaCoreGameEngine extends NovaPlugin implements wOmeN {
	private static NovaCoreGameEngine instance;

	public static NovaCoreGameEngine getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		this.getDataFolder().mkdir();

		Log.info("NovaCoreGameEngine", "Loading language files...");
		try {
			LanguageReader.readFromJar(this.getClass(), "/lang/en-us.json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		NovaCoreGameEngine.instance = this;

		Log.info("NovaCoreGameEngine", "Adding debug triggers...");
		GameEngineDebugTriggers.init();

		Log.info("NovaCoreGameEngine", "Loading modules...");
		ModuleManager.loadModule(this, GameManager.class);
		ModuleManager.loadModule(this, GameLobby.class);
		ModuleManager.loadModule(this, MedicalSupplyDropManager.class);

		Log.info("NovaCoreGameEngine", "Loading map modules...");
		MapModuleManager.addMapModule("novacore.chestloot", ChestLoot.class);
		MapModuleManager.addMapModule("novacore.lootdrop", LootDropMapModule.class);
		MapModuleManager.addMapModule("novacore.lootdrop.medical", MedicalSupplyDropMapModule.class);
		MapModuleManager.addMapModule("novacore.mapprotection", MapProtection.class);
		MapModuleManager.addMapModule("novacore.handcraftingtable", HandCraftingTable.class);
		MapModuleManager.addMapModule("novacore.worldborder", WorldborderMapModule.class);
		MapModuleManager.addMapModule("novacore.settime", SetTime.class);
		MapModuleManager.addMapModule("novacore.startmessage", StartMessage.class);
		MapModuleManager.addMapModule("novacore.graceperiod", GracePeriodMapModule.class);
		MapModuleManager.addMapModule("novacore.falldamagegraceperiod", FallDamageGracePeriodMapModule.class);
		MapModuleManager.addMapModule("novacore.simpleboxdecay", SimpleBoxDecay.class);
		MapModuleManager.addMapModule("novacore.blockloot", BlockLoot.class);
		MapModuleManager.addMapModule("novacore.blockreplacer", BlockReplacer.class);
		MapModuleManager.addMapModule("novacore.noweather", NoWeather.class);
		MapModuleManager.addMapModule("novacore.gamerule", Gamerule.class);
		MapModuleManager.addMapModule("novacore.addpotioneffect", AddPotionEffect.class);
		MapModuleManager.addMapModule("novacore.giveitem.slow", GiveItemSlow.class);
		MapModuleManager.addMapModule("novacore.giveitem.instant", GiveItemInstant.class);
		MapModuleManager.addMapModule("novacore.instantvoidkill", InstantVoidKill.class);
		MapModuleManager.addMapModule("novacore.fireresistance", FireReistance.class);
		MapModuleManager.addMapModule("novacore.farmlandprotection", FarmlandProtection.class);
		MapModuleManager.addMapModule("novacore.chunkloader", Chunkloader.class);

		// Legacy modules
		MapModuleManager.addMapModule("novauniverse.survivalgames.medicalsupplydrop", MedicalSupplyDropMapModule.class);

		CommandRegistry.registerCommand(new NovaCoreCommandGameLobby());

		File overridesFile = new File(this.getDataFolder().getAbsolutePath() + File.separator + "overrides.json");
		if (overridesFile.exists()) {
			Log.info("NovaCoreGameEngine", "Found overrides.json");
			try {
				JSONObject overrides = JSONFileUtils.readJSONObjectFromFile(overridesFile);

				if (overrides.has("name_override")) {
					String name = overrides.getString("name_override");
					GameManager.getInstance().setDisplayNameOverride(name);
					Log.info("NovaCoreGameEngine", "Using name override: " + name);
				}
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				Log.error("NovaCoreGameEngine", "Failed to read overrides.json. " + e.getClass().getName() + " " + e.getMessage());
			}
		}

		Log.success("NovaCoreGameEngine", "Game engine enabled");
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}