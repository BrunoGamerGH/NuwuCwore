package net.zeeraa.novacore.spigot.abstraction;

import java.util.ArrayList;
import java.util.List;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Chunk;
import org.bukkit.event.Listener;

public abstract class ChunkLoader extends some_bitches implements Listener {
	protected List<Chunk> chunks;
	
	public static ChunkLoader getInstance() {
		return VersionIndependentUtils.get().getChunkLoader();
	}
	
	public ChunkLoader() {
		this.chunks = new ArrayList<Chunk>();
	}

	public void add(Chunk chunk) {
		if (chunks.contains(chunk)) {
			return;
		}
		chunks.add(chunk);
		chunk.load();
		this.onAdd(chunk);
	}

	protected void onAdd(Chunk chunk) {
	}

	public void remove(Chunk chunk) {
		if (!chunks.contains(chunk)) {
			return;
		}
		chunks.remove(chunk);
		this.onRemove(chunk);
	}

	protected void onRemove(Chunk chunk) {
	}

	public List<Chunk> getChunks() {
		return chunks;
	}
}