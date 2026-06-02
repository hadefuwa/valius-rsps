package valius.model.entity.player.dialogue;

import valius.content.ScaleDismantler;
import valius.content.quest.dialogue.DialogueChain;
import valius.content.quest.dialogue.DialogueEmote;
import valius.content.quest.dialogue.impl.NpcDialogue;
import valius.content.quest.dialogue.impl.OptionDialogue;
import valius.content.quest.dialogue.impl.PlayerDialogue;
import valius.model.entity.player.Player;

/**
 * 
 * @author Divine | 3:59:15 a.m. | Sep. 26, 2019
 *
 */


/*
 * Handles recharging and enchanting crystal seeds
 */
public class CrystalWeaponElf {
	
	static DialogueChain chain = DialogueChain.builder()
			
			.add(0, NpcDialogue
					.builder()
					.npcId(9170)
					.emote(DialogueEmote.CALM)
					.lines("Hello, I can enchant your Weapon and Armor seeds", 
						   "into Crystal Bows, Halberds, Shields or Armor.",
						   "I will also be able to recharge @blu@(inactive)</col> Equipment")
					.nextDialogue(1)
				)
			
			.add (1, OptionDialogue
					.builder()
					.option("I'd like to look at Weapon Enchantments.", plr -> {
						return 2;
						})
					.option("I'd like to look at Armor Enchantments.", plr -> {
							return 3;
							})
					.option("I'd like to recharge an inactive piece of equipment.", plr -> {
						return 4;
						})
					.option("I'd like to look at Tool Enchantments.", plr -> {
						return 5;
						}))
			
			//2
			.add(2, PlayerDialogue
					.builder()
					.onDialogueOpen(ScaleDismantler::dismantle)
					.lines("I'd like to look at Weapon Enchantments.")
					.nextDialogue(6))
			
			.add(3, PlayerDialogue
					.builder()
					.lines("I'd like to look at Armor Enchantments.")
					.nextDialogue(8))
			
			.add(4, PlayerDialogue
					.builder()
					.lines("I'd like to recharge an inactive piece of equipment.")
					.nextDialogue(10))
			
			.add(5, PlayerDialogue
					.builder()
					.lines("I'd like to look at Tool Enchantments.")
					.nextDialogue(17))
			
			/*
			 * weapon enchanting
			 */
			.add (6, OptionDialogue
					.builder()
					.option("Enchant into Crystal bow.", plr -> {
						if (plr.getItems().playerHasItem(4207, 1)) {
							if (plr.getItems().playerHasItem(23962, 40)) {
								plr.getItems().deleteItem(4207, 1);
								plr.getItems().deleteItem(23962, 40);
								plr.getItems().addItem(23983, 1);
								plr.getDH().sendItemStatement("The elf Sings a song and your Weapon seed transforms into a Crystal Bow.", 23983);
								plr.cBowArrowCount = 0;
							}
						} else {
							return 7;
						}
						return -1;
					})
					.option("Enchant into Crystal halberd.", plr -> {
						if (plr.getItems().playerHasItem(4207, 1)) {
							if (plr.getItems().playerHasItem(23962, 40)) {
								plr.getItems().deleteItem(4207, 1);
								plr.getItems().deleteItem(23962, 40);
								plr.getItems().addItem(23987, 1);
								plr.getDH().sendItemStatement("The elf Sings a song and your Weapon seed transforms into a Crystal Halberd.", 23987);
								plr.cHallyCount = 0;
							}
						} else {
							return 7;
						}
							return -1;
							})
					.option("Enchant into Crystal shield.", plr -> {
						if (plr.getItems().playerHasItem(4207, 1)) {
							if (plr.getItems().playerHasItem(23962, 40)) {
								plr.getItems().deleteItem(4207, 1);
								plr.getItems().deleteItem(23962, 40);
								plr.getItems().addItem(23991, 1);
								plr.getDH().sendItemStatement("The elf Sings a song and your Weapon seed transforms into a Crystal Shield.", 23991);
								plr.cShieldCount = 0;
							}
						} else {
							return 7;
						}
						return -1;
					})
					.option("Nevermind.", plr -> {
						return -1;
						}))
			
			.add(7, NpcDialogue
					.builder()
					.npcId(9170)
					.emote(DialogueEmote.CALM)
					.lines("You will need a Crystal weapon seed", "and 40 Crystal shards to do this.")
				)
			
			/*
			 * armor enchanting
			 */
			.add (6, OptionDialogue
					.builder()
					.option("Enchant into Crystal helmet.", plr -> {
						if (plr.getItems().playerHasItem(23956, 1)) {
							if (plr.getItems().playerHasItem(23962, 50)) {
								plr.getItems().deleteItem(23956, 1);
								plr.getItems().deleteItem(23962, 50);
								plr.getItems().addItem(23971, 1);
								plr.getDH().sendItemStatement("The elf Sings a song and your Armor seed transforms into a Crystal helmet.", 239);
							}
						} else {
							return 9;
						}
						return -1;
					})
					.option("Enchant into Crystal platebody.", plr -> {
						if (plr.getItems().playerHasItem(23956, 1)) {
							if (plr.getItems().playerHasItem(23962, 50)) {
								plr.getItems().deleteItem(23956, 1);
								plr.getItems().deleteItem(23962, 50);
								plr.getItems().addItem(23975, 1);
								plr.getDH().sendItemStatement("The elf Sings a song and your Armor seed transforms into a Crystal platebody.", 23975);
							}
						} else {
							return 9;
						}
							return -1;
							})
					.option("Enchant into Crystal platelegs.", plr -> {
						if (plr.getItems().playerHasItem(23956, 1)) {
							if (plr.getItems().playerHasItem(23962, 50)) {
								plr.getItems().deleteItem(23956, 1);
								plr.getItems().deleteItem(23962, 50);
								plr.getItems().addItem(23979, 1);
								plr.getDH().sendItemStatement("The elf Sings a song and your Armor seed transforms into Crystal platelegs.", 23979);
							}
						} else {
							return 9;
						}
						return -1;
					})
					.option("Nevermind.", plr -> {
						return -1;
						}))
			

			.add(9, NpcDialogue
					.builder()
					.npcId(9170)
					.emote(DialogueEmote.CALM)
					.lines("You will need a Crystal armor seed", "and 50 Crystal shards to do this.")
				)
			
			/*
			 * recharge equipment
			 */
			.add(10, OptionDialogue
					.builder()
					.option("Recharge inactive weapon", plr -> {
						return 11;
			})
					.option("Recharge inactive tool", plr -> {
						return 12;
			})
					.option("Recharge inactive armor", plr -> {
						return 13;
			})
					.option("Nevermind.", plr -> {
						return -1;
						}))
			
			/*
			 * recharge weapons
			 */
			.add(11, OptionDialogue
					.builder()
					.option("Recharge inactive Crystal bow", plr -> {
						if (plr.getItems().playerHasItem(23985, 1)) {
						if (plr.getItems().playerHasItem(23962, 40)) {
							plr.getItems().deleteItem(23985, 1);
							plr.getItems().deleteItem(23962, 40);
							plr.getItems().addItem(23983, 1);
							plr.getDH().sendItemStatement("The elf recharges your Crystal bow.", 23983);
							plr.cBowArrowCount = 0;
						}
					} else {
						return 14;
					}
					return -1;
				})					
					.option("Recharge inactive Crystal halberd", plr -> {
						if (plr.getItems().playerHasItem(23989, 1)) {
							if (plr.getItems().playerHasItem(23962, 40)) {
								plr.getItems().deleteItem(23989, 1);
								plr.getItems().deleteItem(23962, 40);
								plr.getItems().addItem(23987, 1);
								plr.getDH().sendItemStatement("The elf recharges your Crystal halberd.", 23983);
								plr.cHallyCount = 0;
							}
						} else {
							return 14;
						}
						return -1;
					})
					.option("Recharge inactive Crystal shield", plr -> {
						if (plr.getItems().playerHasItem(23993, 1)) {
							if (plr.getItems().playerHasItem(23962, 40)) {
								plr.getItems().deleteItem(23993, 1);
								plr.getItems().deleteItem(23962, 40);
								plr.getItems().addItem(23991, 1);
								plr.getDH().sendItemStatement("The elf recharges your Crystal shield.", 23983);
								plr.cShieldCount = 0;
							}
						} else {
							return 14;
						}
						return -1;
					})
					.option("Recharge inactive Blade of saeldor", plr -> {
						if (plr.getItems().playerHasItem(23997, 1)) {
							if (plr.getItems().playerHasItem(23962, 40)) {
								plr.getItems().deleteItem(23997, 1);
								plr.getItems().deleteItem(23962, 40);
								plr.getItems().addItem(23995, 1);
								plr.getDH().sendItemStatement("The elf recharges your Blade of saeldor.", 23983);
								plr.saeldorCount = 0;
							}
						} else {
							return 14;
						}
						return -1;
					}))
			
			/*
			 * recharge tools
			 */
			.add(12, OptionDialogue
					.builder()
					.option("Recharge inactive Crystal axe", plr -> {
						if (plr.getItems().playerHasItem(23675, 1)) {
						if (plr.getItems().playerHasItem(23962, 40)) {
							plr.getItems().deleteItem(23675, 1);
							plr.getItems().deleteItem(23962, 40);
							plr.getItems().addItem(23673, 1);
							plr.getDH().sendItemStatement("The elf recharges your Crystal axe.", 23673);
							plr.cBowArrowCount = 0;
						}
					} else {
						return 15;
					}
					return -1;
				})					
					.option("Recharge inactive Crystal pickaxe", plr -> {
						if (plr.getItems().playerHasItem(23682, 1)) {
							if (plr.getItems().playerHasItem(23962, 40)) {
								plr.getItems().deleteItem(23682, 1);
								plr.getItems().deleteItem(23962, 40);
								plr.getItems().addItem(23680, 1);
								plr.getDH().sendItemStatement("The elf recharges your Crystal pickaxe.", 23680);
							}
						} else {
							return 15;
						}
						return -1;
					})
					.option("Recharge inactive Crystal harpoon", plr -> {
						if (plr.getItems().playerHasItem(23764, 1)) {
							if (plr.getItems().playerHasItem(23962, 40)) {
								plr.getItems().deleteItem(23764, 1);
								plr.getItems().deleteItem(23962, 40);
								plr.getItems().addItem(23762, 1);
								plr.getDH().sendItemStatement("The elf recharges your Crystal harpoon.", 23762);
								plr.cShieldCount = 0;
							}
						} else {
							return 15;
						}
						return -1;
					}))
			
			/*
			 * recharge armor
			 */
			.add(13, OptionDialogue
					.builder()
					.option("Recharge inactive Crystal helm", plr -> {
						if (plr.getItems().playerHasItem(23973, 1)) {
						if (plr.getItems().playerHasItem(23962, 50)) {
							plr.getItems().deleteItem(23973, 1);
							plr.getItems().deleteItem(23962, 50);
							plr.getItems().addItem(23971, 1);
							plr.getDH().sendItemStatement("The elf recharges your Crystal helm.", 23971);
							plr.cBowArrowCount = 0;
						}
					} else {
						return 16;
					}
					return -1;
				})					
					.option("Recharge inactive Crystal body", plr -> {
						if (plr.getItems().playerHasItem(23977, 1)) {
							if (plr.getItems().playerHasItem(23962, 50)) {
								plr.getItems().deleteItem(23977, 1);
								plr.getItems().deleteItem(23962, 50);
								plr.getItems().addItem(23975, 1);
								plr.getDH().sendItemStatement("The elf recharges your Crystal body.", 23975);
							}
						} else {
							return 16;
						}
						return -1;
					})
					.option("Recharge inactive Crystal legs", plr -> {
						if (plr.getItems().playerHasItem(23981, 1)) {
							if (plr.getItems().playerHasItem(23962, 50)) {
								plr.getItems().deleteItem(23981, 1);
								plr.getItems().deleteItem(23962, 50);
								plr.getItems().addItem(23979, 1);
								plr.getDH().sendItemStatement("The elf recharges your Crystal legs.", 23979);
								plr.cShieldCount = 0;
							}
						} else {
							return 16;
						}
						return -1;
					}))
			
			.add(14, NpcDialogue
					.builder()
					.npcId(9170)
					.emote(DialogueEmote.CALM)
					.lines("You will need an inactive crystal weapon", "and 40 Crystal shards to do this.")
				)
			
			.add(15, NpcDialogue
					.builder()
					.npcId(9170)
					.emote(DialogueEmote.CALM)
					.lines("You will need an inactive crystal tool", "and 40 Crystal shards to do this.")
				)
			
			.add(16, NpcDialogue
					.builder()
					.npcId(9170)
					.emote(DialogueEmote.CALM)
					.lines("You will need inactive crystal armor", "and 50 Crystal shards to do this.")
				)
			
			/*
			 * tool enchanting
			 */
			.add (17, OptionDialogue
					.builder()
					.option("Enchant Crystal tool seed into a Crystal axe.", plr -> {
						if (plr.getItems().playerHasItem(23953, 1)) {
							if (plr.getItems().playerHasItem(23962, 40)) {
								plr.getItems().deleteItem(23953, 1);
								plr.getItems().deleteItem(23962, 40);
								plr.getItems().addItem(23673, 1);
								plr.getDH().sendItemStatement("The elf Sings a song and your Tool seed transforms into a Crystal axe.", 23673);
								plr.cBowArrowCount = 0;
							}
						} else {
							return 18;
						}
						return -1;
					})
					.option("Enchant Crystal tool seed into a Crystal pickaxe.", plr -> {
						if (plr.getItems().playerHasItem(23953, 1)) {
							if (plr.getItems().playerHasItem(23962, 40)) {
								plr.getItems().deleteItem(23953, 1);
								plr.getItems().deleteItem(23962, 40);
								plr.getItems().addItem(23680, 1);
								plr.getDH().sendItemStatement("The elf Sings a song and your Tool seed transforms into a Crystal pickaxe.", 23680);
								plr.cHallyCount = 0;
							}
						} else {
							return 18;
						}
							return -1;
							})
					.option("Enchant Crystal tool seed into a Crystal harpoon.", plr -> {
						if (plr.getItems().playerHasItem(23953, 1)) {
							if (plr.getItems().playerHasItem(23962, 40)) {
								plr.getItems().deleteItem(23953, 1);
								plr.getItems().deleteItem(23962, 40);
								plr.getItems().addItem(23762, 1);
								plr.getDH().sendItemStatement("The elf Sings a song and your Tool seed transforms into a Crystal harpoon.", 23762);
								plr.cShieldCount = 0;
							}
						} else {
							return 18;
						}
						return -1;
					})
					.option("Nevermind.", plr -> {
						return -1;
						}))
			
			.add(18, NpcDialogue
					.builder()
					.npcId(9170)
					.emote(DialogueEmote.CALM)
					.lines("You will need a Crystal tool seed",  "and 120 crystal shards to do this.")
				)
			
			.build();
	
	public static void startDialogue(Player player, int npcType) {
		if (npcType == 9170) {
			chain.open(player);
		}
	}
}