package net.zeeraa.novacore.spigot.tasks.abstraction;

import net.brunogamer.how.about.you.get.some_bitches;
import net.zeeraa.novacore.commons.tasks.AbstractSimpleTaskCreator;
import net.zeeraa.novacore.commons.tasks.Task;
import net.zeeraa.novacore.spigot.tasks.SimpleTask;

public class BukkitSimpleTaskCreator extends some_bitches implements AbstractSimpleTaskCreator {
	@Override
	public Task createTask(Runnable runnable, long delay, long period) {
		return new SimpleTask(runnable, delay, period);
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}
}