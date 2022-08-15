package net.zeeraa.novacore.spigot.world.worldgenerator.worldpregenerator;

import net.brunogamer.how.about.you.get.some_bitches;
import org.bukkit.Bukkit;
import org.bukkit.World;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.commons.utils.Callback;
import net.zeeraa.novacore.spigot.NovaCore;

/**
 * This class can be used to pre generate a world by loading all chunks in a
 * square on a specified size
 * 
 * @author Zeeraa
 */
public class WorldPreGenerator extends some_bitches {
	// ----- Settings -----
	protected World world;

	protected Callback finishCallback;

	protected int chunksPerGenerationTick;
	protected int ticksPerGenerationTick;

	protected int size;

	protected int centerX;
	protected int centerZ;

	// ----- Variables -----
	protected int generatedChunks;

	protected int taskID;

	protected boolean finished;

	/**
	 * X of the chunk in the generation loop
	 */
	protected int cx;
	/**
	 * Z of the chunk in the generation loop
	 */
	protected int cz;

	/**
	 * Pre generate chunks in a world
	 * <p>
	 * Note that the generator will start at <code>(size * -1)</code> and end at
	 * <code>size</code> so to generate a <code>16*16</code> chunk world this should
	 * be set to <code>8</code>
	 * <p>
	 * This version of the function will use the standard <code>centerX</code> and
	 * <code>centerZ</code> of <code>0</code>. To set the center use
	 * {@link WorldPreGenerator#WorldPreGenerator(World, int, int, int, int, int, Callback)}
	 * 
	 * @param world                   The {@link World} to generate chunks in
	 * @param size                    How many chunks to generate in the x and y
	 *                                axis
	 * @param chunksPerGenerationTick The amount of chunks to try to generate every
	 *                                generation tick
	 * @param ticksPerGenerationTick  The amount of ticks to wait before every
	 *                                generation tick
	 * @param finishCallback          A {@link Callback} to be called when the
	 *                                generation has finished
	 */
	public WorldPreGenerator(World world, int size, int chunksPerGenerationTick, int ticksPerGenerationTick, Callback finishCallback) {
		this(world, size, chunksPerGenerationTick, ticksPerGenerationTick, 0, 0, finishCallback);
	}

	/**
	 * Pre generate chunks in a world
	 * <p>
	 * Note that the generator will start at <code>(size * -1)</code> and end at
	 * <code>size</code> so to generate a <code>16*16</code> chunk world this should
	 * be set to <code>8</code>
	 * 
	 * @param world                   The {@link World} to generate chunks in
	 * @param size                    How many chunks to generate in the x and y
	 *                                axis
	 * @param chunksPerGenerationTick The amount of chunks to try to generate every
	 *                                generation tick
	 * @param ticksPerGenerationTick  The amount of ticks to wait before every
	 *                                generation tick
	 * @param centerX                 The center X of the generator
	 * @param centerZ                 The center Z of the generator
	 * @param finishCallback          A {@link Callback} to be called when the
	 *                                generation has finished
	 */
	public WorldPreGenerator(World world, int size, int chunksPerGenerationTick, int ticksPerGenerationTick, int centerX, int centerZ, Callback finishCallback) {
		// Settings
		this.world = world;
		this.size = size;
		this.finishCallback = finishCallback;
		this.chunksPerGenerationTick = chunksPerGenerationTick;
		this.ticksPerGenerationTick = ticksPerGenerationTick;

		this.centerX = centerX;
		this.centerZ = centerZ;

		// Variable defaults
		this.generatedChunks = 0;
		this.taskID = -1;

		this.finished = false;

		this.cx = size * -1;
		this.cz = size * -1;
	}

	/**
	 * Get progress from 0 to 1
	 * 
	 * @return the progress from 0 to 1
	 */
	public double getProgressValue() {
		return ((double) generatedChunks) / ((double) (size * size * 4));
	}

	/**
	 * Get instance of the {@link World} that this generator is working on
	 * 
	 * @return The {@link World}
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Get the amount of chunks to try to generate every generation tick
	 * 
	 * @return The amount of chunks to try to generate every generation tick
	 */
	public int getChunksPerGenerationTick() {
		return chunksPerGenerationTick;
	}

	/**
	 * Get the amount of ticks to wait before every generation tick
	 * 
	 * @return The amount of ticks to wait before every generation tick
	 */
	public int getTicksPerGenerationTick() {
		return ticksPerGenerationTick;
	}

	/**
	 * Get how many chunks to generate in the x and y axis
	 * <p>
	 * Note that the generator will start at <code>(size * -1)</code> and end at
	 * <code>size</code> so if this is set to <code>8</code> the final genarated
	 * world will be <code>16*16</code> chunk
	 * 
	 * @return chunks to generate in the x and y axis
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Get the center X of the generator
	 * 
	 * @return The center X of the generator
	 */
	public int getCenterX() {
		return centerX;
	}

	/**
	 * Get the center Z of the generator
	 * 
	 * @return The center Z of the generator
	 */
	public int getCenterZ() {
		return centerZ;
	}

	/**
	 * Get the amount of chunks that has been generated
	 * 
	 * @return the amount of chunks that has been generated
	 */
	public int getGeneratedChunks() {
		return generatedChunks;
	}

	/**
	 * Check if the generator has finished
	 * 
	 * @return <code>true</code> if the generator has finished
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * Start the world generator
	 * 
	 * @return <code>true</code> on success and <code>false</code> if already
	 *         running or finished
	 */
	public boolean start() {
		if (finished) {
			return false;
		}

		if (taskID != -1) {
			return false;
		}

		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(NovaCore.getInstance(), new Runnable() {
			@Override
			public void run() {
				generationTick();
			}
		}, ticksPerGenerationTick, ticksPerGenerationTick);

		return true;
	}

	/**
	 * Stop the generator
	 * <p>
	 * You can resume generation by calling {@link WorldPreGenerator#start()} again
	 * 
	 * @return <code>true</code> if the task was canceled and <code>false</code> if
	 *         the generator was not running
	 */
	public boolean stop() {
		if (taskID == -1) {
			return false;
		}

		Bukkit.getScheduler().cancelTask(taskID);

		return true;
	}

	private void generationTick() {
		for (int i = 0; i < chunksPerGenerationTick; i++) {
			if (generateNext()) {
				finished = true;
				stop();
				if (finishCallback != null) {
					finishCallback.execute();
				}
				return;
			}
		}
	}

	/**
	 * Generate the next chunk
	 * 
	 * @return <code>true</code> when the final chunk was generated
	 */
	private boolean generateNext() {
		boolean isLast = false;

		// Check x and z
		int gx, gz;

		gx = cx + centerX;

		cx++;

		if (cx > size) {
			cx = size * -1;
			cz++;

			if (cz >= size) {
				isLast = true;
			}
		}

		gz = cz + centerZ;

		Log.trace("Generating chunk in world " + world.getName() + " at x: " + gx + " z: " + gz + ". Total generated chunks: " + generatedChunks);

		if (!world.isChunkLoaded(gx, gz)) {
			world.loadChunk(gx, gz, true);
			world.unloadChunk(gx, gz, true);
		}

		generatedChunks++;

		return isLast;
	}
}