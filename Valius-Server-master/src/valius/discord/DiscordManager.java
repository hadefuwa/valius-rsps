package valius.discord;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

import sx.blah.discord.handle.obj.IChannel;

public class DiscordManager {
	
	private static ScheduledExecutorService eventQueue = Executors.newScheduledThreadPool(4);
	
	public static void init() {
		eventQueue.scheduleAtFixedRate(DiscordManager::sendOutMessages, 2, 2, TimeUnit.SECONDS);
	}
	
	private static void sendOutMessages() {
		activeChannels
		.stream()
		.sorted((firstChannel, secondChannel) -> (int) (firstChannel.getLastSend() - secondChannel.getLastSend()))
		.filter(chatQueue -> !chatQueue.getMessages().isEmpty())
		.limit(4)
		.forEach(chatQueue -> chatQueue.sendMessages());
	}
	
	private static List<ChatQueue> activeChannels = Lists.newCopyOnWriteArrayList();

	public static void sendMessage(IChannel channel, String message) {
		Optional<ChatQueue> foundQueue = activeChannels.stream().filter(queue -> Objects.equals(queue.getChannel(), channel)).findFirst();
		if(!foundQueue.isPresent()) {
			ChatQueue newQueue = new ChatQueue(channel);
			activeChannels.add(newQueue);
			foundQueue = Optional.ofNullable(newQueue);
		}
		
		foundQueue.ifPresent(chatQueue -> {
			chatQueue.post(message);
		});
	}

}
