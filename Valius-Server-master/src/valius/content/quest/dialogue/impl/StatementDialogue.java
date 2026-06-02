package valius.content.quest.dialogue.impl;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

import valius.content.quest.dialogue.Dialogue;
import valius.model.entity.player.Player;

public class StatementDialogue extends Dialogue {

	private static final int[] STATEMENT_INTERFACES = { 356, 359, 363, 368, 374};
	private List<String> lines = Lists.newLinkedList();
	private String continueMessage = "Click here to continue";

	private transient Consumer<Player> onOpenEvent;
	public static StatementDialogue builder() {
		return new StatementDialogue();
	}
	
	public StatementDialogue lines(String... lines) {
		this.lines = Stream.of(lines).collect(Collectors.toList());
		return this;
	}
	
	public StatementDialogue onDialogueOpen(Consumer<Player> event) {
		this.onOpenEvent = event;
		return this;
	}
	@Override
	public void apply(Player player) {
		super.apply(player);

		if(onOpenEvent != null)
			onOpenEvent.accept(player);
		int baseInterfaceId = STATEMENT_INTERFACES[lines.size() - 1];
		IntStream.range(0, lines.size()).forEach(val -> player.getPA().sendFrame126(lines.get(val), baseInterfaceId + 1 + val));
		player.getPA().sendFrame126(continueMessage, baseInterfaceId + lines.size());
		player.getPA().sendFrame164(baseInterfaceId);
	}

}
