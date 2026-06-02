package valius.model.entity.player.dialogue;

import valius.content.quest.dialogue.DialogueChain;
import valius.content.quest.dialogue.impl.OptionDialogue;
import valius.model.entity.player.Player;

/**
 * 
 * @author Divine | 4:47:45 p.m. | Nov. 16, 2019
 *
 */

public class ValiusImp {
	static DialogueChain chain = DialogueChain.builder()
			.add(0, OptionDialogue
					.builder()
					.option("Imp (Melee damage boost)", plr -> {
						if (plr.getItems().playerHasItem(33930)) {
							plr.sendMessage("You already have this Imp.");
						} else {
							plr.getItems().deleteItem(33931, 1);
							plr.getItems().deleteItem(33932, 1);
							plr.getItems().addItem(33930, 1);
							plr.sendMessage("Your Imp is now boosting Melee damage by 3%");
						}
						return -1;
					})
					.option("Imp (Range damage boost)", plr -> {
						if (plr.getItems().playerHasItem(33931)) {
							plr.sendMessage("You already have this Imp.");
						} else {
							plr.getItems().deleteItem(33930, 1);
							plr.getItems().deleteItem(33932, 1);
							plr.getItems().addItem(33931, 1);
							plr.sendMessage("Your Imp is now boosting Range damage by 3%");
						}
						return -1;
					})
					.option("Imp (Magic damage boost)", plr -> {
						if (plr.getItems().playerHasItem(33932)) {
							plr.sendMessage("You already have this Imp.");
						} else {
							plr.getItems().deleteItem(33931, 1);
							plr.getItems().deleteItem(33930, 1);
							plr.getItems().addItem(33932, 1);
							plr.sendMessage("Your Imp is now boosting Magic damage by 3%");
						}
						return -1;
					})
					.nextDialogue(1)
				)
			
			
			.build();

	public static void startDialogue(Player player, int npcType) {
		if (npcType == 3410) {
			chain.open(player);
		}
	}
}
