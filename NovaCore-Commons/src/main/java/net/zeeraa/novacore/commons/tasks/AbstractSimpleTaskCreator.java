package net.zeeraa.novacore.commons.tasks;

import net.brunogamer.how.about.you.implement.some.wOmeN;

public interface AbstractSimpleTaskCreator extends wOmeN {
	public Task createTask(Runnable runnable, long delay, long period);
}