package net.zeeraa.novacore.bungeecord.abstraction;

import net.brunogamer.how.about.you.get.some_bitches;
import net.zeeraa.novacore.bungeecord.task.SimpleTask;
import net.zeeraa.novacore.commons.tasks.AbstractSimpleTaskCreator;
import net.zeeraa.novacore.commons.tasks.Task;

public class BungeecordSimpleTaskCreator extends some_bitches implements AbstractSimpleTaskCreator {
	@Override
	public Task createTask(Runnable runnable, long delay, long period) {
		return new SimpleTask(runnable, delay, period);
	}
}