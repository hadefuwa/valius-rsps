package valius.model.entity.player.dialogue;

import valius.content.quest.dialogue.DialogueChain;
import valius.content.quest.dialogue.DialogueEmote;
import valius.content.quest.dialogue.impl.NpcDialogue;
import valius.content.quest.dialogue.impl.OptionDialogue;
import valius.content.quest.dialogue.impl.PlayerDialogue;
import valius.model.entity.player.Player;

/**
 * 
 * @author Divine | 5:23:05 a.m. | Oct. 13, 2019
 *
 */


/*
 * An NPC guide for the starter dungeon
 */
public class StarterDungeonGuide {
	
static DialogueChain chain = DialogueChain.builder()
			
			.add(0, NpcDialogue
					.builder()
					.npcId(3396)
					.lines("Hello, I'm the Guide for this dungeon.", "I'll let you know anything you need to know", "about the Starter Dungeon!")
					.nextDialogue(1)
				)
			.add(1, OptionDialogue
					.builder()
					.option("Where should i start?", plr -> {
						return 2;
					})
					.option("Tell me about the Armor drops i can get here.", plr -> {
						return 5;
					})
					.option("Tell me about the Weapon drops i can get here.", plr -> {
						return 13;
					})
					.option("Tell me about the Elder Mystery boxes.", plr -> {
						return 20;
					})
					)
			
			.add(2, PlayerDialogue
			.builder()
			.emote(DialogueEmote.DISORIENTED_LEFT)
			.lines("Where should i start?")
			.nextDialogue(3)
			)
			
			.add(3, NpcDialogue
					.builder()
					.npcId(3396)
					.lines("Ok, First off we have 5 rooms. Each room", "has a monster slightly stronger than the last",
						   "BUT fighting stronger monsters will offer", "more loot and better chances for @blu@Rare Loot</col>.")
					.nextDialogue(4)
				)
			
			.add(4, NpcDialogue
					.builder()
					.npcId(3396)
					.lines("Here is the order from weakest to strongest:", "@blu@Mutated rats, Elder trolls, Dark centaurs,",
						   "@blu@Undead warriors & Shaded beasts.</col> If you're a New Player", "you would start off by fighting the @blu@Mutated rats</col>.")
					.nextDialogue(1)
				)
			/*
			 * end of where should i start
			 */
			
			.add(5, PlayerDialogue
					.builder()
					.emote(DialogueEmote.DISORIENTED_LEFT)
					.lines("Tell me about the armor drops i can get here.")
					.nextDialogue(6)
					)
			.add(6, NpcDialogue
					.builder()
					.npcId(3396)
					.lines("Depending on the level of monster", "Drop rates will differ. The higher the level",
						   "The better your chances are to recieve", "more loot and have better chances for @blu@Rare Armor</col>.")
					.nextDialogue(7)
				)
			.add(7, NpcDialogue
					.builder()
					.npcId(3396)
					.lines("All monsters drop Fury, Grace and Nature armor pieces", "within this dungeon. ", "These sets consists of 4 pieces: ",
						   "A Helmet, Platebody, Platelegs and Boots.")
					.nextDialogue(8)
				)
			.add(8, NpcDialogue
					.builder()
					.npcId(3396)
					.lines("The boots are NOT needed to receive the Set buff.", "Each pair will block 5% of all incoming damage.")
					.nextDialogue(9)
				)
			.add(9, NpcDialogue
					.builder()
					.npcId(3396)
					.lines("While wearing the Helmet, Plate and Legs of @red@Fury", "A buff will be given to all Fury weapons",
							"causing active fury to hit 3-6 times", "and do 4-12 dmg every 1-3 ticks.")
					.nextDialogue(10)
				)
			.add(10, NpcDialogue
					.builder()
					.npcId(3396)
					.lines("While wearing the Helmet, Plate and Legs of @blu@Grace", "A buff will be given to all Grace weapons",
							"adding +10% blocked damage and +10% damage", "sent back at the monster (35% total)")
					.nextDialogue(11)
				)
			.add(11, NpcDialogue
					.builder()
					.npcId(3396)
					.lines("While wearing the Helmet, Plate and Legs of @gre@Nature", "A buff will be given to all Nature weapons",
							"Healing the player for 3-8 health each time.")
					.nextDialogue(12)
				)
			.add(12, NpcDialogue
					.builder()
					.npcId(3396)
					.lines("Check the magnifying glass to the bottom right of", "Your inventory for more information on Drops.",
							"Once there, click 'View NPC Drops'.")
					.nextDialogue(1)
				)
			/*
			 * end of armor info
			 */
			
			.add(13, PlayerDialogue
					.builder()
					.emote(DialogueEmote.DISORIENTED_LEFT)
					.lines("Tell me about Weapon drops i can get here.")
					.nextDialogue(14)
					)
			.add(14, NpcDialogue
					.builder()
					.npcId(3396)
					.lines("Depending on the level of monster", "Drop rates will differ. The higher the level",
						   "The better your chances are to recieve", "more loot and have better chances for @blu@Rare Weapons</col>.")
					.nextDialogue(15)
				)
			.add(15, NpcDialogue
					.builder()
					.npcId(3396)
					.lines("All monsters drop Fury, Grace and Nature weapon pieces.", "Each weapon is created with 2 pieces ",
						   "There is Staff, Sword and Bow to be made!")
					.nextDialogue(16)
				)
			.add(16, NpcDialogue
					.builder()
					.npcId(3396)
					.lines("Using Dyes found in Elder Mystery Boxes", "Players can dye the weapons to create",
						   "Grace and Nature weapons.")
					.nextDialogue(17)
				)
			.add(17, NpcDialogue
					.builder()
					.npcId(3396)
					.lines("Flamburst fury weapons have a 1:10 chance to", "Activate fury for 2-5 hits.",
						   "during this time, the monster will take", "3-9 damage every 2-4 ticks.")
					.nextDialogue(18)
				)
			.add(17, NpcDialogue
					.builder()
					.npcId(3396)
					.lines("Flamburst grace weapons have a 1:10 chance to", "to block 25% of incoming damage",
						   "and project it back onto the monster.")
					.nextDialogue(18)
				)
			.add(18, NpcDialogue
					.builder()
					.npcId(3396)
					.lines("Flamburst nature weapons have a 1:10 chance to", "heal health for 3-6 hits.",
						   "during this time, the player is healed", "for 2-6 health every 2-5 ticks.")
					.nextDialogue(19)
				)
			.add(19, NpcDialogue
					.builder()
					.npcId(3396)
					.lines("Check the magnifying glass to the bottom right of", "Your inventory for more information on Drops.",
							"Once there, click 'View NPC Drops'.")
					.nextDialogue(1)
				)
			/*
			 * end of weapon info
			 */
			
			.add(20, PlayerDialogue
					.builder()
					.emote(DialogueEmote.DISORIENTED_LEFT)
					.lines("Tell me about Elder mystery boxes.")
					.nextDialogue(21)
					)
			.add(21, NpcDialogue
					.builder()
					.npcId(3396)
					.lines("Elder mystery boxes are dropped as Uncommon rewards", "From all the monsters within this dungeon.",
							"The offer great rewards such as:", "Fury, Grace & Nature Wings, the Occult cape")
					.nextDialogue(22)
				)
			.add(22, NpcDialogue
					.builder()
					.npcId(3396)
					.lines("And Nature + Grace weapon dyes.", "The Wings offer a 5% XP boost while training prayer.",
							"The Occult cape offers 5% to Drop rate bonuses.", "Weapon Dyes are used to Dye the weapons")
					.nextDialogue(23)
				)
			.add(23, NpcDialogue
					.builder()
					.npcId(3396)
					.lines("Found within this dungeon.")
					.nextDialogue(1)
				)
			
			.build();
	
	public static void startDialogue(Player player, int npcType) {
		if (npcType == 3396) {
			chain.open(player);
		}
	}
	
}
