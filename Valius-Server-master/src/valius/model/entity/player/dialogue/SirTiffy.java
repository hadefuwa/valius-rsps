package valius.model.entity.player.dialogue;

import valius.content.ScaleDismantler;
import valius.content.quest.dialogue.DialogueChain;
import valius.content.quest.dialogue.DialogueEmote;
import valius.content.quest.dialogue.impl.NpcDialogue;
import valius.content.quest.dialogue.impl.OptionDialogue;
import valius.content.quest.dialogue.impl.PlayerDialogue;
import valius.model.entity.player.Player;

public class SirTiffy {
	
	static DialogueChain chain = DialogueChain.builder()
			
			.add(0, NpcDialogue
					.builder()
					.npcId(8045)
					.emote(DialogueEmote.DISTRESSED)
					.lines("Hello lad, I've come all the way from Falador", 
						   "with an Urgent message from the King.",
						   "The city is under siege from a Giant monster and we are",
						   "in dire need of assistance.")
					.nextDialogue(1)
				)
			
			.add (1, OptionDialogue
					.builder()
					.option("I'll assist, What do you guys need?", plr -> {
						return 2;
						})
					.option("Maybe someone else could help?", plr -> {
							return 3;
							}))
			
			.add(2, PlayerDialogue
					.builder()
					.onDialogueOpen(ScaleDismantler::dismantle)
					.lines("I'll assist, What do you guys need?")
					.nextDialogue(4))
			
			.add(3, PlayerDialogue
					.builder()
					.lines("Maybe someone else could help?"))
			
			.add(4, NpcDialogue
					.builder()
					.npcId(8045)
					.emote(DialogueEmote.DISTRESSED_CONTINUED)
					.lines("Perfect! Head over to the northern gates of Falador.",
						   "I have a few weapons that you'll want to fight this monster.",
						   "here, choose one!")
					.nextDialogue(5))
			
			.add (5, OptionDialogue
					.builder()
					.option("Anger Sword", plr -> {
						plr.getItems().addItemUnderAnyCircumstance(7806, 1);
						return 6;
						})
					.option("Anger Battleaxe", plr -> {
						plr.getItems().addItemUnderAnyCircumstance(7807, 1);
						return 6;
						})
					.option("Anger Mace", plr -> {
						plr.getItems().addItemUnderAnyCircumstance(7808, 1);
							return 6;
							})
					.option("Anger Staff", plr -> {
						plr.getItems().addItemUnderAnyCircumstance(7809, 1);
						return 6;
						}))
			
			.add(6, PlayerDialogue
					.builder()
					.lines("Thanks for the weapon! I'll head to Falador to assist now.")
					.nextDialogue(7))
			
			.add(6, NpcDialogue
					.builder()
					.npcId(8045)
					.lines("I'll see you there!"))
			
			.build();
	
	public static void startDialogue(Player player, int npcType) {
		if (npcType == 8045) {
			chain.open(player);
		}
	}
}
