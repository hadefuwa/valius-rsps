package valius.model.entity.player.combat.range;

import java.util.stream.IntStream;

import valius.Config;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.npc.combat.CombatScript;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.Hitmark;
import valius.model.entity.player.combat.monsterhunt.MonsterHunt.Npcs;
import valius.model.entity.player.skills.Skill;
import valius.model.items.ItemUtility;
import valius.util.Misc;

public class RangeMaxHit extends RangeData {

	public static int calculateRangeDefence(Player c) {
		int defenceLevel = c.getSkills().getLevel(Skill.DEFENCE) ;
		if (c.prayerActive[0]) {
			defenceLevel += c.getSkills().getActualLevel(Skill.DEFENCE) * 0.05;
		} else if (c.prayerActive[5]) {
			defenceLevel += c.getSkills().getActualLevel(Skill.DEFENCE) * 0.1;
		} else if (c.prayerActive[13]) {
			defenceLevel += c.getSkills().getActualLevel(Skill.DEFENCE) * 0.15;
		} else if (c.prayerActive[26]) {
			defenceLevel += c.getSkills().getActualLevel(Skill.DEFENCE) * 0.2;
		} else if (c.prayerActive[27]) {
			defenceLevel += c.getSkills().getActualLevel(Skill.DEFENCE) * 0.25;
		} else if (c.prayerActive[28]) {
			defenceLevel += c.getSkills().getActualLevel(Skill.DEFENCE) * 0.25;
		}
		return defenceLevel + (c.playerBonus[9] / 2);
	}

	public static int calculateRangeAttack(Player c) {
		int rangeLevel = c.getSkills().getLevel(Skill.RANGED);
		if (c.playerIndex > 0) {
			rangeLevel *= c.specAccuracy;
		}
		if (c.fullVoidRange()) {
			rangeLevel += c.getSkills().getActualLevel(Skill.RANGED) * 0.1;
		}
		if (c.fullVoidSupremeRange()) {
			rangeLevel += c.getSkills().getActualLevel(Skill.RANGED) * 0.15;
		}
		if (c.prayerActive[3]) {
			rangeLevel *= 1.05;
		} else if (c.prayerActive[11]) {
			rangeLevel *= 1.10;
		} else if (c.prayerActive[19]) {
			rangeLevel *= 1.15;
		} else if (c.prayerActive[27]) {
			rangeLevel *= 1.23;
		}
		if (c.npcIndex > 0 && c.getSlayer().getTask().isPresent()) {
			NPC npc = NPCHandler.npcs[c.npcIndex];
			if (npc != null && c.getSlayer().getTask().get().matches(npc.getDefinition().getNpcName()) || npc.npcType == 7413) {
				boolean SLAYER_HELM = IntStream.of(c.IMBUED_SLAYER_HELMETS).anyMatch(i -> c.getItems().isWearingItem(i));
				if (!c.getItems().isWearingItem(4081) && SLAYER_HELM) {
					rangeLevel *= 1.15;
				}
			}
			if (c.getItems().isWearingItem(4081, c.playerAmulet)) {
				if (Misc.linearSearch(Config.UNDEAD_IDS, npc.npcType) != -1) {
					rangeLevel *= 1.15;
				}
			}
			if (c.getItems().isWearingItem(12018, c.playerAmulet)) {
				if (Misc.linearSearch(Config.UNDEAD_IDS, npc.npcType) != -1) {
					rangeLevel *= 1.20;
				}
			}

		}

		if (c.fullVoidRange() && c.specAccuracy > 1.15) {
			rangeLevel *= 1.75;
		}
		return (int) (rangeLevel + (c.playerBonus[4] * 1.95));
	}
	
	public static boolean wearingCrystalBow(Player c) {
		return c.playerEquipment[c.playerWeapon] == 23901 || c.playerEquipment[c.playerWeapon] == 23902 || c.playerEquipment[c.playerWeapon] == 23903 || c.playerEquipment[c.playerWeapon] == 23983;
	}

	public static int maxHit(Player c) {
		int rangeLevel = c.getSkills().getLevel(Skill.RANGED);
		int rangedStrength = 
			
			/**
			 * If a player IS using a blowpipe
			 * We grab the strength from the ammo which is saved on players accounts
			 */
			c.playerEquipment[c.playerWeapon] == 12926 ? getRangeStr(c.getToxicBlowpipeAmmo()) : 
				
			/**
			 * If a player IS using a crystal bow
			 * We grab the strength from the weapon slot
			 */
			wearingCrystalBow(c) ? getRangeStr(c.playerEquipment[c.playerWeapon]) :
				
			/**
			 * If none of the above is applicable
			 * We grab the strength from the weapon slot and divide it by 2
			 */
			getRangeStr(c.playerEquipment[c.playerWeapon]) + getRangeStr(c.playerEquipment[c.playerArrows]);
			
		double b = 1.00;
		if (c.prayerActive[3]) {
			b *= 1.05;
		} else if (c.prayerActive[11]) {
			b *= 1.10;
		} else if (c.prayerActive[19]) {
			b *= 1.15;
		} else if (c.prayerActive[27]) {
			b *= 1.23;
		}
		if (c.fullVoidRange()) {
			b *= 1.10;
		}
		if (c.fullVoidSupremeRange()) {
			b *= 1.15;
		}
		if (Boundary.isIn(c, Boundary.RAIDROOMS) && c.getItems().isWearingItem(20997) || 
				Boundary.isIn(c, Boundary.XERIC) && c.getItems().isWearingItem(20997)) {
			b*= 1.37;
		
		}
		if (Boundary.isIn(c, Boundary.RAIDROOMS) && c.getItems().isWearingItem(33752) || 
				Boundary.isIn(c, Boundary.XERIC) && c.getItems().isWearingItem(33752)) {
			b*= 1.37;
		
		}
		if (Boundary.isIn(c, Boundary.RAIDROOMS) && c.getItems().isWearingItem(33671) || 
				Boundary.isIn(c, Boundary.XERIC) && c.getItems().isWearingItem(33671)) {
			b*= 1.37;
		
		}
		if (Boundary.isIn(c, Boundary.RAIDROOMS) && c.getItems().isWearingItem(33562) || 
				Boundary.isIn(c, Boundary.XERIC) && c.getItems().isWearingItem(33562)) {
			b*= 1.37;
		
		}
		if (Boundary.isIn(c, Boundary.RAIDROOMS) && c.getItems().isWearingItem(33119) || 
				Boundary.isIn(c, Boundary.XERIC) && c.getItems().isWearingItem(33119)) {
			b*= 1.38;
	
		}
		//cursed twisted bow
		if (Boundary.isIn(c, Boundary.RAIDROOMS) && c.getItems().isWearingItem(33525) || 
				Boundary.isIn(c, Boundary.XERIC) && c.getItems().isWearingItem(33525)) {
			b*= 1.37;
		
		}
		
		//Blood Twisted bow
		if (Boundary.isIn(c, Boundary.RAIDROOMS) && c.getItems().isWearingItem(33424) || 
				Boundary.isIn(c, Boundary.XERIC) && c.getItems().isWearingItem(33424)) {
			b*= 1.37;
	
		}
		if (Boundary.isIn(c, Boundary.RAIDROOMS) && c.getItems().isWearingItem(33124) || 
				Boundary.isIn(c, Boundary.XERIC) && c.getItems().isWearingItem(33124)) {//twisted crossbow
			b*= 1.35;
		
		}
		if (Boundary.isIn(c, Boundary.RAIDROOMS) && c.getItems().isWearingItem(33578) || //blood twisted crossbow
				Boundary.isIn(c, Boundary.XERIC) && c.getItems().isWearingItem(33578)) {
			b*= 1.35;
		
		}
		
		NPC npc = NPCHandler.npcs[c.npcIndex];
		
		if (c.getItems().isWearingItem(33529)) {//decimation
			int berserkAmount = 5;//amount of times berserk can hit
			
			if (c.disconnected || npc.isDead || c.hitCount >= berserkAmount ) {
				c.hitCount = 0;
				c.sendMessage("You are no longer going Berserk.");
			}
			
			if (c.hitCount < 1) {
				if (Misc.random(1, 10) == 5) {
					if (npc.getDefinition().getNpcName().contains("demon") || npc.getDefinition().getNpcName().contains("skot") || npc.getDefinition().getNpcName().contains("sire")
							|| npc.getDefinition().getNpcName().contains("corp") || npc.getDefinition().getNpcName().contains("kril") || Misc.linearSearch(Config.DEMON_IDS, npc.npcType) != -1) {
			c.hitCount++;
				}
			}
		}
			if (c.hitCount == 0) {
			}
			if (c.hitCount == 1) {
				c.gfx100(246);
				c.startAnimation(1056);
				c.sendMessage("Your rage builds and you start going Berserk!");
				c.forcedChat("RRRRAAARRRRGGGHHHHHHH!!!");
			}
			if (c.hitCount >= 1) {
				b *= 1.25;
				c.hitCount++;
			}
		}
		
		if (c.getItems().isWearingItem(33250) || c.getItems().isWearingItem(33251) ||//goliath gloves
				c.getItems().isWearingItem(33252) || c.getItems().isWearingItem(33253)) {
				if (Misc.random(100) <= 10) {
				if (Misc.random(10) <= 5) {
					b = (b * 1.25);
				}
					b = (b * 1.25);
				}
			}
		
		if (c.summonId == 33931) {
			b *= 1.03;
		}
		
		//Starter bow
		if (c.getItems().isWearingItem(33891)) {
			int rangedLevel = c.getSkills().getLevel(Skill.RANGED);
			b *= (rangedLevel * .01 + 0.45);
		}
		
		if (c.getItems().isWearingItem(4081, c.playerAmulet)) {
			if (Misc.linearSearch(Config.UNDEAD_IDS, npc.npcType) != -1) {
				rangeLevel *= 1.15;
			}
		}
		if (c.getItems().isWearingItem(12018, c.playerAmulet)) {
			if (Misc.linearSearch(Config.UNDEAD_IDS, npc.npcType) != -1) {
				rangeLevel *= 1.20;
			}
		}
		
		
		
		
		if (c.getItems().isWearingItem(13237)) {//pegs
			b*= 1.02;
		}
		
		if (c.getItems().isWearingItem(19547) || c.getItems().isWearingItem(22249) ) {//anguish
			b*= 1.02;
		}
		
		if (c.getItems().isWearingItem(21898) || c.getItems().isWearingItem(22109) ) {//anguish
			b*= 1.02;
		}
		
		
		if (c.getItems().isWearingItem(22550) && c.inWild()) {
			b*= 2.15;
		}
		if (c.getItems().isWearingItem(33781) && c.inWild() || c.getItems().isWearingItem(33782) && c.inWild() || c.getItems().isWearingItem(33783) && c.inWild())  {
			b*= 2.40;
			}
//			if (c.getItems().isWearingItem(22550)) {
//				b*= 1.45;
//			}
//			if (c.getItems().isWearingItem(33781) || c.getItems().isWearingItem(33781) || c.getItems().isWearingItem(33781)) {
//				b*= 1.55;
//			}
		if (c.npcIndex > 0 && c.getSlayer().getTask().isPresent()) {
			if (npc != null && c.getSlayer().getTask().get().matches(npc.getDefinition().getNpcName()) || npc.npcType == 7413) {
				boolean SLAYER_HELM = IntStream.of(c.IMBUED_SLAYER_HELMETS).anyMatch(i -> c.getItems().isWearingItem(i));
				if (SLAYER_HELM) {
					b *= 1.15;
				}
			}
		}
		double e = Math.floor(rangeLevel * b);
		if (c.fightMode == 0) {
			e = (e + 3.0);
		}
		double darkbow = 1.0;
		if (c.usingSpecial) {
			if (c.playerEquipment[3] == 11235 || c.playerEquipment[3] == 12765 || c.playerEquipment[3] == 12766 || c.playerEquipment[3] == 12767 || c.playerEquipment[3] == 12768) {
				if (Arrow.matchesMaterial(c.lastArrowUsed, Arrow.DRAGON)) {
					darkbow = 1.05;
				} else {
					darkbow = 1.01;
				}
			}
		}
		double max = (1.3 + e / 10 + rangedStrength / 80 + e * rangedStrength / 640) * darkbow;
		if (c.usingSpecial) {
			max *= c.specDamage;
		}
		return (int) max;
	}
}