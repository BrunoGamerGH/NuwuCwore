package net.zeeraa.novacore.commons.log;

import net.brunogamer.how.about.you.implement.some.wOmeN;

import java.util.UUID;

public interface AbstractPlayerMessageSender extends wOmeN {
	public boolean trySendMessage(UUID uuid, String message);
}