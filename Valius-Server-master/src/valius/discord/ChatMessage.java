package valius.discord;

import java.util.concurrent.TimeUnit;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ChatMessage {
	
	public ChatMessage(String message) {
		this.message = message;
		this.time = System.currentTimeMillis();
	}
	
	private String message;
	private long time;
	
	@Override
	public String toString() {
		return message;
	}

}
