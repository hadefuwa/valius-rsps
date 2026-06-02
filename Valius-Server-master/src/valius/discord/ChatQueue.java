package valius.discord;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.RequestBuffer;
import valius.world.World;


@Getter
@RequiredArgsConstructor
public class ChatQueue {

	private final IChannel channel;
	private long lastSend;
	private List<ChatMessage> messages = Lists.newCopyOnWriteArrayList();

	public void post(String message) {
		messages.add(new ChatMessage(message));
	}

	public void sendMessages() {
		boolean isLog = channel.getName().contains("-logs");
		if(isLog) {
			if(System.currentTimeMillis() - lastSend < 5000)
				return;
		}

		if(!channel.getShard().isReady()) {
			return;
		}
		lastSend = System.currentTimeMillis();
		RequestBuffer.request(() -> {
			if (!World.getWorld().isLocalWorld()) {
				try{
					List<ChatMessage> pendingMessages = Lists.newArrayList(messages).stream().limit(10).collect(Collectors.toList());
					if(pendingMessages.isEmpty())
						return;
					String message = "```\n" + pendingMessages.stream().map(e -> e.toString()).collect(Collectors.joining("\n")) + "```";
					if(message.length() > 2000) {
						message = message.substring(0, 1999);
					}
					channel.sendMessage(message);
					messages.removeAll(pendingMessages);
				} catch (Exception e){
					System.err.println("Message could not be sent with error: ");
					e.printStackTrace();
				}
			}
		});
	}

}
