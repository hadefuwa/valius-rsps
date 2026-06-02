package valius.event.impl;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.text.WordUtils;

import valius.discord.DiscordBot;
import valius.event.Event;
import valius.model.entity.player.PlayerHandler;
import valius.util.Misc;

public class DidYouKnowEvent extends Event<Object> {

	/**
	 * The amount of time in game cycles (600ms) that the event pulses at
	 */
	private static final int INTERVAL = Misc.toCyclesOrDefault(15, 20, TimeUnit.MINUTES);

	/**
	 * A {@link Collection} of messages that are to be displayed
	 */
	private final List<String> MESSAGES = Misc.jsonArrayToList(Paths.get("Data", "json", "did_you_know.json"), String[].class);

	/**
	 * The index or position in the list that we're currently at
	 */
	private int position;
	
	/**
	 * for valius bot updates
	 */
	private int i = 0;
	/**
	 * Creates a new event to cycle through messages for the entirety of the runtime
	 */
	public DidYouKnowEvent() {
		super(new String(), new Object(), INTERVAL);
		i++;
		if (i == 4) {
			i = 0;
		}
	}

	@Override
	public void execute() {
		position++;
		if (position >= MESSAGES.size()) {
			position = 0;
		}
		List<String> messages = Arrays.asList(WordUtils.wrap(MESSAGES.get(position), 80));
		messages.set(0, "<img=20> [@yel@<shad>News</shad>@bla@] @yel@<shad>" + messages.get(0));
		PlayerHandler.nonNullStream().forEach(player -> {
			/*if (player.getBankPin().getPin().length() == 0) {
				player.sendMessage("@red@You currently do not have a bank-pin set on your account! You are at risk.");
			}*/
			if (player.didYouKnow)
				messages.forEach(m -> player.sendMessage(m));
		});
	}

}
