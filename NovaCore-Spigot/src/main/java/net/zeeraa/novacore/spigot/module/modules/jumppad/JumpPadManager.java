package net.zeeraa.novacore.spigot.module.modules.jumppad;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.json.JSONArray;
import org.json.JSONException;
import net.zeeraa.novacore.commons.tasks.Task;
import net.zeeraa.novacore.commons.utils.JSONFileUtils;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.command.CommandRegistry;
import net.zeeraa.novacore.spigot.module.NovaModule;
import net.zeeraa.novacore.spigot.module.modules.jumppad.command.JumpPadCommand;
import net.zeeraa.novacore.spigot.tasks.SimpleTask;

public class JumpPadManager extends NovaModule implements Listener, wOmeN {
	private static JumpPadManager instance;
	private List<JumpPad> jumpPads;
	private List<Player> isOnJumpPad;

	private JumpPadCommand jumpPadCommand;

	private JumpPadEffect jumpPadEffect;

	private SimpleTask task;

	public static JumpPadManager getInstance() {
		return instance;
	}

	public JumpPadManager() {
		super("Novacore.JumpPadManager");
	}

	@Override
	public void onLoad() {
		JumpPadManager.instance = this;
		this.jumpPads = new ArrayList<JumpPad>();
		this.isOnJumpPad = new ArrayList<Player>();
		this.task = null;

		this.jumpPadCommand = new JumpPadCommand();

		this.jumpPadEffect = new DefaultJumpPadEffect();
	}

	@Override
	public void onEnable() throws Exception {
		if (!CommandRegistry.isRegistered(jumpPadCommand)) {
			CommandRegistry.registerCommand(jumpPadCommand);
		}

		Task.tryStopTask(task);

		task = new SimpleTask(NovaCore.getInstance(), new Runnable() {
			@Override
			public void run() {
				Bukkit.getServer().getOnlinePlayers().forEach(player -> {
					if (player.getGameMode() == GameMode.SPECTATOR) {
						return;
					}

					if (player.getGameMode() == GameMode.CREATIVE && player.isFlying()) {
						return;
					}

					boolean isPlayerOnJumpPad = false;
					for (JumpPad pad : jumpPads) {
						if (pad.isInRange(player)) {
							isPlayerOnJumpPad = true;

							if (!isOnJumpPad.contains(player)) {
								isOnJumpPad.add(player);

								PlayerUseJumpPadEvent event = new PlayerUseJumpPadEvent(player, pad);

								Bukkit.getPluginManager().callEvent(event);

								if (!event.isCancelled()) {
									player.setVelocity(pad.getVelocity());
									if (event.isPlayEffect()) {
										if (jumpPadEffect != null) {
											jumpPadEffect.playJumpPadEffect(pad, player);
										}
									}
								}
							}

							break;
						}

						if (!isPlayerOnJumpPad) {
							if (isOnJumpPad.contains(player)) {
								isOnJumpPad.remove(player);
							}
						}
					}					
				});
			}
		}, 5L, 5L);
		task.start();
	}

	public List<JumpPad> getJumpPads() {
		return jumpPads;
	}

	public void loadJumpPads(File file, Plugin owner) throws JSONException, IOException {
		JSONArray array = JSONFileUtils.readJSONArrayFromFile(file);

		for (int i = jumpPads.size() - 1; i >= 0; i--) {
			if (jumpPads.get(i).getOwner().getName().equalsIgnoreCase(owner.getName())) {
				jumpPads.remove(i);
			}
		}

		for (int i = 0; i < array.length(); i++) {
			jumpPads.add(JumpPad.fromJson(array.getJSONObject(i), owner));
		}
	}

	public void saveJumpPads(File file, Plugin owner) throws IOException {
		JSONArray array = new JSONArray();

		jumpPads.forEach(pad -> {
			if (pad.getOwner().getName().equalsIgnoreCase(owner.getName())) {
				array.put(pad.toJson());
			}
		});

		JSONFileUtils.saveJson(file, array, 4);
	}

	@Override
	public void onDisable() throws Exception {
		Task.tryStopTask(task);
		isOnJumpPad.clear();
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void opnPlayerQuit(PlayerQuitEvent e) {
		if (isOnJumpPad.contains(e.getPlayer())) {
			isOnJumpPad.remove(e.getPlayer());
		}
	}
}