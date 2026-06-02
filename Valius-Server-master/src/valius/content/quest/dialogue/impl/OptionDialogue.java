package valius.content.quest.dialogue.impl;

import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import valius.content.quest.dialogue.Dialogue;
import valius.model.entity.player.Player;

@Slf4j
public class OptionDialogue extends Dialogue {
	
	private static final int[] DIALOGUE_INTERFACE_IDS = {13758, 2459, 2469, 2480, 2492};
	public static OptionDialogue builder() {
		return new OptionDialogue();
	}

	private String title = "Select an Option";
	private transient Consumer<Player> onOpenEvent;
	private transient LinkedList<Optional<Function<Player, Integer>>> optionActions = Lists.newLinkedList();
	private LinkedList<String> optionValues = Lists.newLinkedList();
	
	public OptionDialogue option(String question, Function<Player, Integer> func) {
		optionActions.add(Optional.ofNullable(func));
		optionValues.add(question);
		return this;
	}
	
	public OptionDialogue action(int index, Function<Player, Integer> func) {
		if(optionActions.size() < optionValues.size()) {
			IntStream.range(optionActions.size(), optionValues.size()).forEach(val -> optionActions.add(null));
		}
		optionActions.set(index, Optional.ofNullable(func));
	
		return this;
	}
	
	public OptionDialogue onDialogueOpen(Consumer<Player> event) {
		this.onOpenEvent = event;
		return this;
	}
	
	public OptionDialogue title(String title) {
		this.title = title;
		return this;
	}
	
	public void next(Player player, int option) {
		int nextDialogue = -1;
		try {
			nextDialogue = optionActions.get(option - 1).get().apply(player);
			log.info("next dialogue is {}", nextDialogue);
		} catch(Exception ex) {
			//option doesn't exist
			log.warn("Dialogue option {} [{}] was selected, but no next dialogue was defined!", option, optionValues.get(option));
		}
		chain.get(nextDialogue).apply(player);
	}
	@Override
	public void apply(Player player) {
		super.apply(player);
		if(onOpenEvent != null)
			onOpenEvent.accept(player);
		final int baseInterface = DIALOGUE_INTERFACE_IDS[optionValues.size() - 1];
		player.getPA().sendFrame126(title, baseInterface + 1);
		IntStream.range(0, optionActions.size()).forEach(val -> player.getPA().sendFrame126(optionValues.get(val), baseInterface + 2 + val));
		player.getPA().sendFrame164(baseInterface);
	}
}
