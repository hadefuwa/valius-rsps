package valius.model.entity.player.dialogue;

import valius.content.quest.dialogue.DialogueChain;
import valius.content.quest.dialogue.impl.NpcDialogue;
import valius.content.quest.dialogue.impl.PlayerDialogue;
import valius.model.entity.player.Player;

public class Scoop {
	
	static DialogueChain chain = DialogueChain.builder()
			
			.add(0, NpcDialogue
					.builder()
					.npcId(6020)
					.lines("Want some icecream?", "I got some icecream here!", "Want some?")
					.nextDialogue(1)
				)
			.add(1, PlayerDialogue
					.builder()
					.lines("Erm... No thanks, you enjoy it.")
					)
			.build();
	
	public static void startDialogue(Player player, int npcType) {
		if (npcType == 6020) {
			chain.open(player);
		}
	}
}
