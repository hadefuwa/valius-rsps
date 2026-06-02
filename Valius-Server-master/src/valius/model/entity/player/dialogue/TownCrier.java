package valius.model.entity.player.dialogue;

import valius.content.quest.dialogue.DialogueChain;
import valius.content.quest.dialogue.impl.NpcDialogue;
import valius.content.quest.dialogue.impl.PlayerDialogue;
import valius.model.entity.player.Player;

public class TownCrier {
	
	static DialogueChain chain = DialogueChain.builder()
			
			.add(0, NpcDialogue
					.builder()
					.npcId(279)
					.lines("I'm here to share the latest updates and news for Valius!")
					.nextDialogue(1)
				)
			.add(1, PlayerDialogue
					.builder()
					.lines("Thanks for the information!")
					.onDialogueOpen(plr -> {
						plr.getPA().sendFrame126("https://valius.net/community/index.php?/forum/3-updates/", 12000);
					})
					)
			.build();
	
	public static void startDialogue(Player player, int npcType) {
		if (npcType == 279) {
			chain.open(player);
		}
	}
}
