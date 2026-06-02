package valius.model.entity.player.dialogue;

import valius.content.ScaleDismantler;
import valius.content.quest.dialogue.DialogueChain;
import valius.content.quest.dialogue.impl.NpcDialogue;
import valius.content.quest.dialogue.impl.OptionDialogue;
import valius.content.quest.dialogue.impl.PlayerDialogue;
import valius.model.entity.player.Player;

public class ZulrahDismantler {
	
	static DialogueChain chain = DialogueChain.builder()
			
			.add(0, NpcDialogue
					.builder()
					.npcId(2039)
					.lines("Hello friend, I'll give you 20,000 scales for each rare item",
							"you find form Zulrah. Items i'll take are as follows:",
							"@red@Tanzanite fang, Serpentine visage, Serpentine helm", 
							"@red@Toxic blowpipe (empty) and the Magic fang.")
					.nextDialogue(1)
				)
			
			.add(1, NpcDialogue
					.builder()
					.npcId(2039)
					.lines("Would you like to trade in all Zulrah items in your inventory?")
					.nextDialogue(2)
					)
			
			.add(2, OptionDialogue
					.builder()
					.option("Yes please.", plr -> {
						return 3;
						}).option("No thank you.", plr -> {
							return 4;
							}))
			
			.add(3, PlayerDialogue
					.builder()
					.onDialogueOpen(ScaleDismantler::dismantle)
					.lines("Yes please."))
			
			.add(4, PlayerDialogue
					.builder()
					.lines("No thank you."))
			
			.build();
	
	public static void startDialogue(Player player, int npcType) {
		if (npcType == 2039) {
			chain.open(player);
		}
	}
}
