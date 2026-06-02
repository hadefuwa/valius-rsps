package valius.content.quest.dialogue.impl;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

import valius.content.quest.dialogue.Dialogue;
import valius.content.quest.dialogue.DialogueEmote;
import valius.model.entity.player.Player;
import valius.util.Misc;

public class PlayerDialogue extends Dialogue {

	private static final int[] PLAYER_CHAT_INTERFACES = { 968, 973, 979, 986, };
	private List<String> lines = Lists.newLinkedList();
	private transient Consumer<Player> onOpenEvent;
	private DialogueEmote emote = DialogueEmote.CONTENT;
	
	public static PlayerDialogue builder() {
		return new PlayerDialogue();
	}
	
	public PlayerDialogue lines(String... lines) {
		this.lines = Stream.of(lines).collect(Collectors.toList());
		return this;
	}
	
	public PlayerDialogue onDialogueOpen(Consumer<Player> event) {
		this.onOpenEvent = event;
		return this;
	}
	
	public PlayerDialogue emote(DialogueEmote emote) {
		this.emote = emote;
		return this;
	}
	
	@Override
	public void apply(Player player) {
		super.apply(player);
		if(onOpenEvent != null)
			onOpenEvent.accept(player);
		int baseInterfaceId = PLAYER_CHAT_INTERFACES[lines.size() - 1];
		player.getPA().sendFrame200(baseInterfaceId + 1, emote.getEmoteId());
		player.getPA().sendFrame126(Misc.capitalize(player.playerName), baseInterfaceId + 2);
		IntStream.range(0, lines.size()).forEach(val -> player.getPA().sendFrame126(lines.get(val), baseInterfaceId + 3 + val));
		player.getPA().sendFrame185(baseInterfaceId + 1);
		player.getPA().sendFrame164(baseInterfaceId);
	}

}
