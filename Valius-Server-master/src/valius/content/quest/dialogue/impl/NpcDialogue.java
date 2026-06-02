package valius.content.quest.dialogue.impl;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

import valius.content.quest.dialogue.Dialogue;
import valius.content.quest.dialogue.DialogueEmote;
import valius.model.entity.npc.NPCDefinitions;
import valius.model.entity.player.Player;
import valius.util.Misc;

public class NpcDialogue extends Dialogue {

	private static final int[] NPC_CHAT_INTERFACES = { 4882, 4887, 4893, 4900, };
	private List<String> lines = Lists.newLinkedList();
	private DialogueEmote emote = DialogueEmote.CONTENT;
	private transient Consumer<Player> onOpenEvent;
	private int npcId;
	private transient NPCDefinitions def;
	
	public static NpcDialogue builder() {
		return new NpcDialogue();
	}
	
	public NpcDialogue lines(String... lines) {
		this.lines = Stream.of(lines).collect(Collectors.toList());
		return this;
	}
	
	public NpcDialogue emote(DialogueEmote emote) {
		this.emote = emote;
		return this;
	}
	
	public NpcDialogue onDialogueOpen(Consumer<Player> event) {
		this.onOpenEvent = event;
		return this;
	}
	
	public NpcDialogue npcId(int npcId) {
		this.npcId = npcId;
		return this;
	}
	
	@Override
	public void apply(Player player) {
		if(def == null) {
			this.def = NPCDefinitions.get(npcId);
		}
		super.apply(player);
		if(onOpenEvent != null)
			onOpenEvent.accept(player);
		int baseInterfaceId = NPC_CHAT_INTERFACES[lines.size() - 1];
		player.getPA().sendFrame200(baseInterfaceId + 1, emote.getEmoteId());
		player.getPA().sendFrame126(Misc.capitalize(def.getNpcName()), baseInterfaceId + 2);
		IntStream.range(0, lines.size()).forEach(val -> player.getPA().sendFrame126(lines.get(val), baseInterfaceId + 3 + val));
		player.getPA().sendFrame75(npcId, baseInterfaceId + 1);
		player.getPA().sendFrame164(baseInterfaceId);
	}

}
