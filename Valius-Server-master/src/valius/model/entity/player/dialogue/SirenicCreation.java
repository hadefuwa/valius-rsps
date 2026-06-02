package valius.model.entity.player.dialogue;

import valius.content.quest.dialogue.DialogueChain;
import valius.content.quest.dialogue.impl.OptionDialogue;
import valius.model.entity.player.Player;
import valius.model.entity.player.skills.Skill;

/**
 * 
 * @author Divine | 4:47:20 p.m. | Nov. 16, 2019
 *
 */

public class SirenicCreation {

	static DialogueChain chain = DialogueChain.builder()
			.add(0, OptionDialogue
					.builder()
					.option("Sirenic mask", plr -> {
						if (!(plr.getSkills().getLevel(Skill.CRAFTING) >= 90)) {
							plr.sendMessage("You need 90 Crafting to do this.");
							return -1;
						}
						if (plr.getItems().playerHasItem(33912, 50) && plr.getItems().playerHasItem(33913, 1)) {
							plr.getItems().deleteItem(33912, 50);
							plr.getItems().deleteItem(33913, 1);
							plr.getItems().addItem(33918, 1);
							plr.sirenicMaskCharge = 5000;
							plr.sendMessage("You create a Sirenic Mask.");
						} else {
							plr.sendMessage("You need " + Math.subtractExact(50, plr.getItems().getItemAmount(33912))   + " more scales to create the Sirenic Helmet.");
						}
						return -1;
					})
					.option("Sirenic body", plr -> {
						if (!(plr.getSkills().getLevel(Skill.CRAFTING) >= 90)) {
							plr.sendMessage("You need 90 Crafting to do this.");
							return -1;
						}
						if (plr.getItems().playerHasItem(33912, 100) && plr.getItems().playerHasItem(33913, 1)) {
							plr.getItems().deleteItem(33912, 100);
							plr.getItems().deleteItem(33913, 1);
							plr.getItems().addItem(33919, 1);
							plr.sirenicBodyCharge = 5000;
							plr.sendMessage("You create a Sirenic body.");
						} else {
							plr.sendMessage("You need " + Math.subtractExact(100, plr.getItems().getItemAmount(33912))   + " more scales to create the Sirenic body.");
						}
						return -1;
					})
					.option("Sirenic chaps", plr -> {
						if (!(plr.getSkills().getLevel(Skill.CRAFTING) >= 90)) {
							plr.sendMessage("You need 90 Crafting to do this.");
							return -1;
						}
						if (plr.getItems().playerHasItem(33912, 80) && plr.getItems().playerHasItem(33913, 1)) {
							plr.getItems().deleteItem(33912, 80);
							plr.getItems().deleteItem(33913, 1);
							plr.getItems().addItem(33920, 1);
							plr.sirenicChapsCharge = 5000;
							plr.sendMessage("You create some Sirenic chaps.");
						} else {
							plr.sendMessage("You need " + Math.subtractExact(80, plr.getItems().getItemAmount(33912))   + " more scales to create the Sirenic chaps.");
						}
						return -1;
					})
					.option("Next Page", plr -> {
						return 1;
					})
				)
			
			.add(1, OptionDialogue
					.builder()
					.option("Sirenic boots", plr -> {
						if (!(plr.getSkills().getLevel(Skill.CRAFTING) >= 90)) {
							plr.sendMessage("You need 90 Crafting to do this.");
							return -1;
						}
						if (plr.getItems().playerHasItem(33912, 30) && plr.getItems().playerHasItem(33913, 1)) {
							plr.getItems().deleteItem(33912, 30);
							plr.getItems().deleteItem(33913, 1);
							plr.getItems().addItem(33917, 1);
							plr.sendMessage("You create some Sirenic boots.");
						} else {
							plr.sendMessage("You need " + Math.subtractExact(30, plr.getItems().getItemAmount(33912))   + " more scales to create the Sirenic boots.");
						}
						return -1;
					})
					.option("Sirenic gloves", plr -> {
						if (!(plr.getSkills().getLevel(Skill.CRAFTING) >= 90)) {
							plr.sendMessage("You need 90 Crafting to do this.");
							return -1;
						}
						if (plr.getItems().playerHasItem(33912, 25) && plr.getItems().playerHasItem(33913, 1)) {
							plr.getItems().deleteItem(33912, 25);
							plr.getItems().deleteItem(33913, 1);
							plr.getItems().addItem(33929, 1);
							plr.sendMessage("You create some Sirenic gloves.");
						} else {
							plr.sendMessage("You need " + Math.subtractExact(25, plr.getItems().getItemAmount(33912))   + " more scales to create the Sirenic gloves.");
						}
						return -1;
					})
					.option("Previous Page", plr -> {
						return 0;
					})
				)
			
			
			.build();

	public static void startDialogue(Player player, int npcType) {
		if (npcType == -1) {
			chain.open(player);
		}
	}
}
