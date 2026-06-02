package valius.model.entity.player.dialogue;

import valius.content.cluescroll.ClueScrollRiddle;
import valius.content.quest.dialogue.DialogueChain;
import valius.content.quest.dialogue.impl.NpcDialogue;
import valius.content.quest.dialogue.impl.OptionDialogue;
import valius.model.entity.player.Player;
import valius.util.Misc;

/**
 * 
 * @author Divine | 6:10:28 a.m. | Nov. 10, 2019
 *
 */


//Dialogue for trading in clue scrolls
public class Watson {

	
	static DialogueChain chain = DialogueChain.builder()
			
			.add(0, NpcDialogue
					.builder()
					.npcId(7303)
					.lines("Hello, I can combine Clue scrolls", "to create higher tier ones for you.", "An Easy, Medium and Hard clue will get you an Elite clue.")
					.nextDialogue(1)
				)
			.add(1, NpcDialogue
					.builder()
					.npcId(7303)
					.lines("An Easy, Medium, Hard & Elite clue will get you", "A Master clue.")
					.nextDialogue(2)
					)
			.add(2, OptionDialogue 
				.builder()
					.option("Trade in an Easy, Medium & Hard clue for an Elite clue.", plr -> {
						for (int easy : ClueScrollRiddle.EASY_CLUES) {
							for (int medium : ClueScrollRiddle.MEDIUM_CLUES) {
								for (int hard : ClueScrollRiddle.HARD_CLUES) {
									if (plr.getItems().playerHasItem(easy, 1) && plr.getItems().playerHasItem(medium, 1) && plr.getItems().playerHasItem(hard, 1)) {
										plr.getItems().deleteItem(easy, 1);
										plr.getItems().deleteItem(medium, 1);
										plr.getItems().deleteItem(hard, 1);
										plr.getItems().addItem(Misc.randomElementOf(ClueScrollRiddle.ELITE_CLUES), 1);
										plr.sendMessage("You trade in your scrolls for a Clue scroll (Elite).");
										return -1;
									}
								}
							}
						}
					return -1;
				})
					.option("Trade in an Easy, Medium, Hard & Elite clue for a Master clue.", plr -> {
						for (int easy : ClueScrollRiddle.EASY_CLUES) {
							for (int medium : ClueScrollRiddle.MEDIUM_CLUES) {
								for (int hard : ClueScrollRiddle.HARD_CLUES) {
									for (int elite : ClueScrollRiddle.ELITE_CLUES) {
										if (plr.getItems().playerHasItem(easy, 1) && plr.getItems().playerHasItem(medium, 1) 
												&& plr.getItems().playerHasItem(hard, 1) && plr.getItems().playerHasItem(elite, 1)) {
											plr.getItems().deleteItem(easy, 1);
											plr.getItems().deleteItem(medium, 1);
											plr.getItems().deleteItem(hard, 1);
											plr.getItems().deleteItem(elite, 1);
											plr.getItems().addItem(Misc.randomElementOf(ClueScrollRiddle.MASTER_CLUES), 1);
											plr.sendMessage("You trade in your scrolls for a Clue scroll (Master).");
											return -1;
										}
									}
								}
							}
						}
					return -1;
				})
				.option("Nevermind.", plr -> {
					return -1;
				})
					)
			
			.build();
	
	public static void startDialogue(Player player, int npcType) {
		if (npcType == 7303) {
			chain.open(player);
		}
	}
	
}
