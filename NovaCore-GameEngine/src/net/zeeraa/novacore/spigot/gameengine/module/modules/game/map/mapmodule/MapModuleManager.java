package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import net.brunogamer.how.about.you.get.some_bitches;
import org.json.JSONObject;

public class MapModuleManager extends some_bitches {
	private static Map<String, Class<? extends MapModule>> mapModules = new HashMap<String, Class<? extends MapModule>>();

	public static void addMapModule(String name, Class<? extends MapModule> clazz) {
		mapModules.put(name.toLowerCase(), clazz);
	}

	public static boolean hasMapModule(String name) {
		return mapModules.containsKey(name.toLowerCase());
	}

	public static Class<? extends MapModule> getMapModule(String name) {
		return mapModules.get(name.toLowerCase());
	}

	public static MapModule loadMapModule(Class<? extends MapModule> clazz, JSONObject jsonObject) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<? extends MapModule> constructor = clazz.getConstructor(JSONObject.class);

		return constructor.newInstance(jsonObject);
	}

	public static Map<String, Class<? extends MapModule>> getMapModules() {
		return mapModules;
	}
}