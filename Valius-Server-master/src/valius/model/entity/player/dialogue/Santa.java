package valius.model.entity.player.dialogue;


import valius.content.quest.dialogue.DialogueChain;
import valius.content.quest.dialogue.impl.NpcDialogue;
import valius.content.quest.dialogue.impl.OptionDialogue;
import valius.model.entity.player.Player;

public class Santa {
	
	static DialogueChain chain = DialogueChain.builder()
			
			.add(0, NpcDialogue
					.builder()
					.npcId(3413)
					.lines("Ho! Ho! Ho! Merry christmas!", "I can tell you all about the event this year.")
					.nextDialogue(1)
				)
			.add(1, OptionDialogue
					.builder()
					.option("Tell me about obtaining Ornaments (Currency).", plr -> {
						return 2;
					})
					.option("Tell me about Christmas Presents.", plr -> {
						return 3;
					})
					.option("Nevermind.", plr -> {
						return 3;
					})
					)
			.add(2, NpcDialogue
					.builder()
					.npcId(3413)
					.lines("Ornaments can be obtained through skilling,", "fighting monsters and bosses, daily and hourly rewards", "and by defeating my evil twin, Anti-Santa.")
					.nextDialogue(1)
				)
			.add(3, NpcDialogue
					.builder()
					.npcId(3413)
					.lines("Christmas presents can be purchased from my holiday shop.", "You can receive some useful holiday items such",
							"as Santa's Wand, Santa's Rapier and some custom pets", "With some unique perks assisting with the holidays.")
					.nextDialogue(1)
				)
			.build();
	
	public static void startDialogue(Player player, int npcType) {
		if (npcType == 3413) {
			chain.open(player);
		}
	}
}
