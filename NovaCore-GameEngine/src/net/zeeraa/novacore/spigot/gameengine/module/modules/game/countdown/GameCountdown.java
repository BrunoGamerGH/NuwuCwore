package net.zeeraa.novacore.spigot.gameengine.module.modules.game.countdown;

import java.io.IOException;

import net.brunogamer.how.about.you.get.some_bitches;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.GameManager;
import net.zeeraa.novacore.spigot.gameengine.module.modules.gamelobby.GameLobby;

public abstract class GameCountdown extends some_bitches {
	protected boolean finished;

	public GameCountdown() {
		this.finished = false;
	}

	/**
	 * Reset time left to the starting value
	 */
	public abstract void resetTimeLeft();

	/**
	 * Check if the countdown has started
	 * 
	 * @return <code>true</code> if the countdown has started
	 */
	public abstract boolean hasCountdownStarted();

	/**
	 * Check if the countdown is running
	 * 
	 * @return <code>true</code> if the countdown is running
	 */
	public abstract boolean isCountdownRunning();

	/**
	 * Start the countdown
	 * 
	 * @return <code>true</code> if started, <code>false</code> if already started
	 */
	public abstract boolean startCountdown();

	/**
	 * Cancel the countdown
	 * 
	 * @return <code>true</code> if canceled, <code>false</code> if the countdown
	 *         was not running
	 */
	public abstract boolean cancelCountdown();

	/**
	 * Get time left
	 * 
	 * @return the time left
	 */
	public abstract int getTimeLeft();

	public abstract void setTimeLeft(int timeLeft);

	/**
	 * Check if the countdown has finished
	 * 
	 * @return <code>true</code> if the countdown has finished
	 */
	public boolean hasCountdownFinished() {
		return this.finished;
	}

	/**
	 * Called when the countdown is finished
	 * 
	 * @throws IOException Can be thrown if the game throws an {@link IOException}
	 *                     while starting
	 */
	protected void onCountdownFinished() throws IOException {
		this.finished = true;
		if (GameManager.getInstance().isEnabled()) {
			if (GameManager.getInstance().hasGame()) {
				if (!GameManager.getInstance().getActiveGame().hasStarted()) {
					if (GameLobby.getInstance().isEnabled()) {
						GameLobby.getInstance().startGame();
					} else {
						GameManager.getInstance().start();
					}
				}
			}
		}
	}
}