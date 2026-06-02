package valius.model.entity.player.dialogue;


import valius.content.quest.dialogue.DialogueChain;
import valius.content.quest.dialogue.DialogueEmote;
import valius.content.quest.dialogue.impl.NpcDialogue;
import valius.content.quest.dialogue.impl.OptionDialogue;
import valius.content.quest.dialogue.impl.PlayerDialogue;
import valius.model.entity.player.Player;

/**
 * 
 * @author Divine
 * Jun. 22, 2019 3:31:02 a.m.
 */


public class StarSprite {
	
		
		static DialogueChain chain = DialogueChain.builder()
		.add(0, NpcDialogue
				.builder()
				.npcId(3821)
				.lines("Hello, strange creature.")
				.nextDialogue(1)
			)
		
		.add(1, OptionDialogue
				.builder()
				.title("Star Sprite")
				.option("What are you? Where are you from?", plr -> {
					return 8;
				})
				.option("Hello, strange glowing creature.", plr -> {
					return 6;
				})
				.option("Do you want your Stardust back?", plr -> {
					return 4;
				})
				.option("I'm not strange.", plr -> {
					return 20;
				})
			)
		
		.add(4, PlayerDialogue
				.builder()
				.emote(DialogueEmote.CONTENT)
				.lines("Do you want your stardust back?.")
				.nextDialogue(5)
				)
		
		.add(5,  NpcDialogue
				.builder()
				.npcId(3821)
				.lines("Yes Please! I'll need it to power a new Star", "I'll give you a reward for 300 star dust.")
				.nextDialogue(22)
				)
		
		.add(6, PlayerDialogue
				.builder()
				.emote(DialogueEmote.LAUGHING)
				.lines("Hello, strange glowing creature.")
				.nextDialogue(7)
				)
		
		.add(7, NpcDialogue
				.builder()
				.npcId(3821)
				.lines("Isn't that funny? One of the things", "i find odd about you is that you DON'T glow.")
				.nextDialogue(1)
				)
		
		.add(8, PlayerDialogue
				.builder()
				.emote(DialogueEmote.DISORIENTED_LEFT)
				.lines("What are you? Where did you come from?")
				.nextDialogue(10)
				)
		
		.add(10, NpcDialogue
				.builder()
				.npcId(3821)
				.lines("I'm a star sprite! I was in my star in the sky",
						"when it lost control and crashed into the ground. With half",
						"my star sticking in the ground, i became stuck. Fortunately,",
						"i was mined out by the kind creatures of your race.")
				.nextDialogue(11)
				)
		
		.add(11, OptionDialogue
				.builder()
				.option("What's a star sprite?", plr -> {
					return 12;
				})
				.option("What are you going to do without your star?", plr -> {
					return 14;
				})
				.option("I thought stars were huge balls of burning gas.", plr -> {
					return 16;
				})
				.option("Well, I'm glad you're okay.", plr -> {
					return 18;
				})
				)
		
		.add(12, PlayerDialogue
				.builder()
				.emote(DialogueEmote.CALM)
				.lines("What's a star sprite?")
				.nextDialogue(13)
				)
		
		.add(13, NpcDialogue
				.builder()
				.npcId(3821)
				.lines("We're what makes the stars in the sky shine.", "I made this star shine when it was in the sky.")
				.nextDialogue(1)
				)
		
		.add(14, PlayerDialogue
				.builder()
				.emote(DialogueEmote.DISTRESSED)
				.lines("What are you going to do without your star?")
				.nextDialogue(15)
				)
		
		.add(15, NpcDialogue
				.builder()
				.npcId(3821)
				.lines("Don't worry about me. I'm sure I'll find some good rocks", "around here and get back up into the sky in no time.")
				.nextDialogue(11)
				)
		
		.add(16, PlayerDialogue
				.builder()
				.emote(DialogueEmote.CALM)
				.lines("I thought stars were huge balls of burning gas.")
				.nextDialogue(17)
				)
		
		.add(17, NpcDialogue
				.builder()
				.npcId(3821)
				.lines("Most of them are, but a lot of shooting stars on this", "plane of the multiverse are rocks with star sprites in them.")
				.nextDialogue(11)
				)
		
		.add(18, PlayerDialogue
				.builder()
				.lines("Well, I'm glad you're okay.")
				.nextDialogue(19)
				)
		
		.add(19, NpcDialogue
				.builder()
				.npcId(3821)
				.lines("Thank you.")
				)
		
		.add(20, PlayerDialogue
				.builder()
				.lines("I'm not strange.")
				.nextDialogue(21)
				)
		
		.add(21, NpcDialogue
				.builder()
				.npcId(3821)
				.lines("Hehe. If you say so.")
				)

		.add(22, OptionDialogue
				.builder()
				.option("Alright!", plr -> {
					return 24;
				})
				.option("Nevermind.", plr -> {
					return -1;
				})
				)
		
		.add(24, PlayerDialogue
				.builder()
				.lines("Alright!")
				.onDialogueOpen(plr -> {
					if (plr.getItems().playerHasItem(33423,  300)) {
					plr.getItems().deleteItem(33423, 300);
					plr.getItems().addItemUnderAnyCircumstance(33422, 1);
					plr.sendMessage("You receive a box from the Star sprite. Good luck!");
				} else {
					plr.sendMessage("You need more Star dust.");
				}
			})
					)
		
		.build();
				
	
	
	public static void startDialogue(Player player, int npcType) {
		if (npcType == 3821) {
			chain.open(player);
		}
	}
}
