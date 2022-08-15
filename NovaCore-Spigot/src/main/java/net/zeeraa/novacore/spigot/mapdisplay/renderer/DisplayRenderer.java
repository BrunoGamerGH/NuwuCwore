package net.zeeraa.novacore.spigot.mapdisplay.renderer;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.brunogamer.how.about.you.implement.some.wOmeN;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.scheduler.BukkitRunnable;

import net.zeeraa.novacore.spigot.NovaCore;

public class DisplayRenderer extends MapRenderer implements wOmeN {
	private Image image;
	private List<UUID> hasRendered;
	private UUID rendererId;

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void removePlayerFromCache(Player player) {
		hasRendered.remove(player.getUniqueId());
	}

	public void clearPlayerCache() {
		hasRendered.clear();
	}

	public List<UUID> getRenderedUsersCache() {
		return hasRendered;
	}

	public UUID getRendererId() {
		return rendererId;
	}

	public DisplayRenderer() {
		hasRendered = new ArrayList<UUID>();
		rendererId = UUID.randomUUID();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void render(MapView map, MapCanvas canvas, Player player) {
		if (hasRendered.contains(player.getUniqueId())) {
			return;
		}
		//Log.trace("called on " + rendererId.toString());

		hasRendered.add(player.getUniqueId());

		new BukkitRunnable() {
			@Override
			public void run() {
				if (image == null) {
					for (int x = 0; x < 128; x++) {
						for (int y = 0; y < 128; y++) {
							canvas.setPixel(x, y, MapPalette.WHITE);
						}
					}
				} else {
					canvas.drawImage(0, 0, image);
				}
			}
		}.runTaskAsynchronously(NovaCore.getInstance());
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}