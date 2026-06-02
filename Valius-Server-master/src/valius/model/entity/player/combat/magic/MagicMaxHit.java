package valius.model.entity.player.combat.magic;

import java.util.stream.IntStream;

import valius.Config;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.skills.Skill;
import valius.util.Misc;

public class MagicMaxHit {

	public static int mageAttack(Player c) {
		double equipmentBonus = c.playerBonus[3];
		double magicLevel = c.getSkills().getLevel(Skill.MAGIC);
		double magicPrayer = c.prayerActive[4] ? 1.05 : c.prayerActive[12] ? 1.10 : c.prayerActive[20] ? 1.15 : 1.0;
		double accuracy = (((equipmentBonus + magicLevel) * 1.4) * magicPrayer);
		double modifier = 1.60;
		if (c.fullVoidMage()) {
			modifier *= 1.1;
		}
		if (c.fullVoidSupremeMage()) {
			modifier *= 1.2;
		}
		if (c.npcIndex > 0 && c.getSlayer().getTask().isPresent()) {
			NPC npc = NPCHandler.npcs[c.npcIndex];
			if (npc != null && c.getSlayer().getTask().get().matches(npc.getDefinition().getNpcName()) || npc.npcType == 7413) {
				if (!c.getItems().isWearingItem(4081) && c.getItems().isWearingItem(11865)) {
					modifier *= 1.15;
				}
			}
			if (c.inWild() && c.getItems().isWearingItem(22555)) {
				modifier *= 2.0;
				}
			
			if (c.inWild() && c.getItems().isWearingItem(33784) || c.getItems().isWearingItem(33785) || c.getItems().isWearingItem(33786)) {
				modifier *= 2.10;
				}
			if (c.getItems().isWearingItem(4081, c.playerAmulet)) {
				if (Misc.linearSearch(Config.UNDEAD_IDS, npc.npcType) != -1) {
					modifier *= 1.15;
				}
			}
			
			if (c.getItems().isWearingItem(33250) || c.getItems().isWearingItem(33251) ||//goliath gloves
					c.getItems().isWearingItem(33252) || c.getItems().isWearingItem(33253)) {
					if (Misc.random(100) <= 10) {
					if (Misc.random(10) <= 5) {
						modifier = (modifier * 1.25);
					}
						modifier = (modifier * 1.25);
					}
				}
			
			if (c.getItems().isWearingItem(4710, c.playerWeapon)) {
				modifier *= 1.05;
			}
			if (c.getItems().isWearingItem(12018, c.playerAmulet)) {
				if (Misc.linearSearch(Config.UNDEAD_IDS, npc.npcType) != -1) {
					modifier *= 1.15;
				}
			}
		}
		return (int) (accuracy * modifier);
	}

	public static int mageDefence(Player c) {
		double prayerDefence = c.prayerActive[0] ? 1.05 : c.prayerActive[5] ? 1.10 : c.prayerActive[13] ? 1.15 : c.prayerActive[24] ? 1.20 : c.prayerActive[25] ? 1.25 : 1.0;
		double defence = Math.floor((c.getSkills().getLevel(Skill.DEFENCE)  * prayerDefence) * .2);
		double magicDefence = Math.floor(c.getSkills().getLevel(Skill.MAGIC) * .5);
		defence += magicDefence + c.playerBonus[8];
		return (int) defence;
	}

	public static int magiMaxHit(Player c) {
		double damage = MagicData.MAGIC_SPELLS[c.oldSpellId][6];
		double damageMultiplier = 1;
		NPC npc = NPCHandler.npcs[c.npcIndex];
		
		if (c.summonId == 33932) {
			damageMultiplier += .03;
		}
		
		switch (c.playerEquipment[c.playerWeapon]) {
			case 33095://chaotic staff
				damageMultiplier += .57;
				break;
				
				
				//Starter wand
			case 33894:
					int magicLevel = c.getSkills().getLevel(Skill.MAGIC);
					damageMultiplier += magicLevel / 4 * .01;
				break;
			
			case 33279://necrolord staff
			case 33112:
			case 33553:
				damageMultiplier += .66;
				break;
			case 33277://inf staff
			case 33761:
			case 33530:
			case 33535:
				damageMultiplier += .72;
				break;
			case 21006:
				damageMultiplier += .73;
				break;
			case 4710:
				damageMultiplier += .27;
				break;
			case 12899:
				damageMultiplier += .48;
				break;
			case 11907:
				damageMultiplier += .63;
				break;
			case 33673:
			case 22323:
				damageMultiplier += .69;
				break;
			case 6914:
				damageMultiplier += .04;
				break;
			case 20604:
				damageMultiplier += .15;
				break;
			case 33013:
				if (c.inWild()) {
				damageMultiplier += .25;
				}
				break;
			
			case 11791:
			case 22296:
			case 12904:
			case 33346:
				damageMultiplier += 0.42;
				break;
		}
		switch (c.playerEquipment[c.playerShield]) {
			case 20714:// Tome of fire
				if(c.spellId == 3 || c.spellId == 7 || c.spellId == 11 || c.spellId == 15 || c.autocastId == 15 || c.autocastId == 3 || c.autocastId == 7 || c.autocastId == 11){
					damageMultiplier += .5;
				}
				break;
			case 18346:// Tome of frost
				if(c.spellId == 1 || c.spellId == 5 || c.spellId == 9 || c.spellId == 13 || c.autocastId == 1 || c.autocastId == 5 || c.autocastId == 9 || c.autocastId == 13){
					damageMultiplier += .5;
				}

		}
		if (c.playerEquipment[c.playerAmulet] == 12002 || c.playerEquipment[c.playerAmulet] == 19720) {
			damageMultiplier += .10;
		}
		if(c.fullVoidMage()) {
			damageMultiplier +=0.10;
		}
		if (c.fullVoidSupremeMage()) {
			damageMultiplier += 0.15;
		}
		
		if (c.playerEquipment[c.playerCape] == 21791 || c.playerEquipment[c.playerCape] == 21793 || c.playerEquipment[c.playerCape] == 21795 ||c.playerEquipment[c.playerCape] == 21793 ||c.playerEquipment[c.playerCape] == 21776 || c.playerEquipment[c.playerCape] == 21780 || c.playerEquipment[c.playerCape] == 21784) {
			damageMultiplier +=0.02;
		}
		if (c.inWild() && c.getItems().isWearingItem(22555)) {
			damageMultiplier *= 1.45;
			}
		if (Boundary.isIn(c, Boundary.RAIDROOMS) && c.getItems().isWearingItem(33470) || 
				Boundary.isIn(c, Boundary.XERIC) && c.getItems().isWearingItem(33470)) {
			damageMultiplier += 1.33;
		} else if (c.getItems().isWearingItem(33470)){
			damageMultiplier += .73;
		}
		if (c.inWild() && c.getItems().isWearingItem(33784) || c.getItems().isWearingItem(33785) || c.getItems().isWearingItem(33786)) {
			damageMultiplier *= 1.55;
			}
		if (c.playerEquipment[c.playerHat] == 21018) {
			damageMultiplier += 0.02;
		}
		if (c.playerEquipment[c.playerHands] == 19544) {
			damageMultiplier += 0.05;
		}
		if (c.playerEquipment[c.playerChest] == 21021) {
			damageMultiplier += 0.02;
		}
		if (c.playerEquipment[c.playerLegs] == 21024) {
			damageMultiplier += 0.02;
		}
		if (c.npcIndex > 0 && c.getSlayer().getTask().isPresent()) {
			if (npc != null && c.getSlayer().getTask().get().matches(npc.getDefinition().getNpcName()) || npc.npcType == 7413) {
				boolean SLAYER_HELM = IntStream.of(c.IMBUED_SLAYER_HELMETS).anyMatch(i -> c.getItems().isWearingItem(i));
				if (SLAYER_HELM) {
					damageMultiplier += .15;
				}
			}
		}
		
		if (c.npcIndex > 0) {
		if (c.getItems().isWearingItem(33530)) {//obliteration
			int berserkAmount = 5;//amount of times berserk can hit
			
			if (c.disconnected || npc.isDead || c.hitCount >= berserkAmount ) {
				c.hitCount = 0;
				c.sendMessage("You are no longer going Berserk.");
			}
			
			if (c.hitCount < 1) {
				if (Misc.random(1, 8) == 5) {
					if (npc.getDefinition().getNpcName().contains("demon") || npc.getDefinition().getNpcName().contains("skot") || npc.getDefinition().getNpcName().contains("sire")
							|| npc.getDefinition().getNpcName().contains("corp") || npc.getDefinition().getNpcName().contains("kril") || Misc.linearSearch(Config.DEMON_IDS, npc.npcType) != -1) {
			c.hitCount++;
				}
			}
		}
			if (c.hitCount == 0) {
				damageMultiplier *= 1.50;
			}
			if (c.hitCount == 1) {
				c.gfx100(246);
				c.startAnimation(1056);
				c.sendMessage("Your rage builds and you start going Berserk!");
				c.forcedChat("RRRRAAARRRRGGGHHHHHHH!!!");
			}
			if (c.hitCount >= 1) {
				damageMultiplier *= 2.25;
				c.hitCount++;
			}
		}
	}
		
		if (c.npcIndex > 0) {
			if (c.getItems().isWearingItem(33535)) {//obliteration blood
				int berserkAmount = 5;//amount of times berserk can hit
				
				if (c.disconnected || npc.isDead || c.hitCount >= berserkAmount ) {
					c.hitCount = 0;
					c.sendMessage("You are no longer going Berserk.");
				}
				
				if (c.hitCount < 1) {
					if (Misc.random(1, 8) == 5) {
						if (npc.getDefinition().getNpcName().contains("demon") || npc.getDefinition().getNpcName().contains("skot") || npc.getDefinition().getNpcName().contains("sire")
								|| npc.getDefinition().getNpcName().contains("corp") || npc.getDefinition().getNpcName().contains("kril") || Misc.linearSearch(Config.DEMON_IDS, npc.npcType) != -1) {
				c.hitCount++;
					}
				}
			}
				if (c.hitCount == 0) {
					damageMultiplier *= 1.50;
				}
				if (c.hitCount == 1) {
					c.gfx100(246);
					c.startAnimation(1056);
					c.sendMessage("Your rage builds and you start going Berserk!");
					c.forcedChat("RRRRAAARRRRGGGHHHHHHH!!!");
				}
				if (c.hitCount >= 1) {
					damageMultiplier *= 2.25;
					c.hitCount++;
				}
			}
		}
		switch (MagicData.MAGIC_SPELLS[c.oldSpellId][0]) {
		case 12037:
			if (c.getItems().isWearingAnyItem(21255)) {
				damage += (c.getSkills().getLevel(Skill.MAGIC) / 6) + 5;
			} else {
				damage += c.getSkills().getLevel(Skill.MAGIC) / 10;
			}
			break;
		}

		damage *= damageMultiplier;
		return (int) damage;
	}
}