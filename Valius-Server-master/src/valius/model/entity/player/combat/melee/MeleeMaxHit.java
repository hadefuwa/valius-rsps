package valius.model.entity.player.combat.melee;

import java.util.stream.IntStream;

import valius.Config;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Player;
import valius.model.entity.player.skills.Skill;
import valius.model.items.EquipmentSet;
import valius.util.Misc;

public class MeleeMaxHit {

	/**
	 * @param c
	 * @param special
	 * @return
	 */
	@SuppressWarnings("unused")
	public static double calculateBaseDamage(Player c, boolean special) {
		double base = 0;
		int attBonus = c.playerBonus[10]; // attack
		int attack = c.getSkills().getLevel(Skill.ATTACK); // attack
		int strength = c.getSkills().getLevel(Skill.STRENGTH); // strength
		double defBonus = c.playerBonus[10]; // defense
		int defense = c.getSkills().getLevel(Skill.DEFENCE); // defense
		int attlvlForXP = c.getSkills().getActualLevel(Skill.ATTACK); // attack
		int strlvlForXP = c.getSkills().getActualLevel(Skill.STRENGTH); // strength
		int deflvlForXP = c.getSkills().getActualLevel(Skill.DEFENCE); // defense
		int lvlForXP = c.getSkills().getActualLevel(Skill.STRENGTH); 
		double effective = getEffectiveStr(c);
		double specialBonus = getSpecialStr(c);
		double strengthBonus = c.playerBonus[10];
		base = (13 + effective + (c.getCombat().strBonus / 8) + ((effective * c.getCombat().strBonus) / 64)) / 10;
		if (c.npcIndex > 0) {
			NPC npc = NPCHandler.npcs[c.npcIndex];
			if (npc != null) {
				if (c.getSlayer().getTask().isPresent()) {
					if (npc != null && c.getSlayer().getTask().get().matches(npc.getDefinition().getNpcName()) || npc.npcType == 7413) {
						boolean SLAYER_HELM = IntStream.of(c.SLAYER_HELMETS).anyMatch(i -> c.getItems().isWearingItem(i));
						if (!c.getItems().isWearingItem(4081) && SLAYER_HELM || c.getItems().isWearingItem(8901)) {
							base *= 1.15;
						}
					}
				}
				if (c.getItems().isWearingItem(4081, c.playerAmulet)) {
					if (Misc.linearSearch(Config.UNDEAD_IDS, npc.npcType) != -1) {
						base *= 1.15;
					}
				}
				if (c.getItems().isWearingItem(12018, c.playerAmulet)) {
					if (Misc.linearSearch(Config.UNDEAD_IDS, npc.npcType) != -1) {
						base *= 1.20;
					}
				}
				if (c.getItems().isWearingItem(19675, c.playerWeapon) && c.getArcLightCharge() > 0) {
					if (Misc.linearSearch(Config.DEMON_IDS, npc.npcType) != -1) {
						base *= 1.70;
					}
				}
				
				if (c.getItems().isWearingItem(33528)) {//annihilation
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
						base *= 1.25;
						c.hitCount++;
					}
				}
			}
		}

		if (EquipmentSet.DHAROK.isWearingBarrows(c)) {
			base *= ((c.getSkills().getActualLevel(Skill.HITPOINTS) - c.getHealth().getAmount()) * .01) + 1;
		}
		if (hasObsidianEffect(c) || c.fullVoidMelee()) {
			base = (base * 1.10);
		}
		if (c.inWild() && c.getItems().isWearingItem(22545)) {
			base = (base * 1.602);
		}
		if (c.inWild() && c.getItems().isWearingItem(33779)) {
			base = (base * 1.702);
		}
		if (c.inWild() && c.getItems().isWearingItem(33780)) {
			base = (base * 1.702);
		}
		if (c.inWild() && c.getItems().isWearingItem(33778)) {
			base = (base * 1.702);
		}
		if (c.inWild() && c.getItems().isWearingItem(33780)) {
			base = (base * 2.15);
		}
		if (c.inWild() && c.getItems().isWearingItem(33778)) {
			base = (base * 2.15);
		}
		if (c.inWild() && c.getItems().isWearingItem(33379)) {
			base = (base * 2.15);
		}
		if (c.fullVoidSupremeMelee()) {
			base = (base * 1.10);
		}
		if (c.summonId == 33930) {
			base = (base * 1.03);
		}
		
		
		//Starter sword
		if (c.getItems().isWearingItem(33893)) {
			int attackLevel = c.getSkills().getLevel(Skill.ATTACK);
			base = base + (attackLevel * .05 + 1);
		}
		
		if (c.getItems().isWearingItem(33090) || c.getItems().isWearingItem(33091) ||//goliath gloves
			c.getItems().isWearingItem(33092) || c.getItems().isWearingItem(33093)) {
			if (Misc.random(100) <= 10) {
			if (Misc.random(10) <= 5) {
				base = (base * 1.25);
			}
				base = (base * 1.25);
				defBonus = (defBonus * 0.10);
			}
		}
		if (c.prayerActive[1]) {
			strength += (int) (lvlForXP * .05);
		} else if (c.prayerActive[6]) {
			strength += (int) (lvlForXP * .1);
		} else if (c.prayerActive[14]) {
			strength += (int) (lvlForXP * .15);
		} else if (c.prayerActive[25]) {
			strength += (int) (lvlForXP * .18);
		} else if (c.prayerActive[26]) {
			strength += (int) (lvlForXP * .23);
		}
		return Math.floor(base);
	}

	public static double getEffectiveStr(Player c) {
		return ((c.getSkills().getLevel(Skill.STRENGTH)) * getPrayerStr(c)) + getStyleBonus(c);
	}

	public static int getStyleBonus(Player c) {
		return c.fightMode == 2 ? 3 : c.fightMode == 3 ? 1 : c.fightMode == 4 ? 3 : 0;
	}

	public static double getPrayerStr(Player c) {
		if (c.prayerActive[1])
			return 1.05;
		else if (c.prayerActive[6])
			return 1.1;
		else if (c.prayerActive[14])
			return 1.15;
		else if (c.prayerActive[25])
			return 1.18;
		else if (c.prayerActive[26])
			return 1.23;
		return 1;
	}

	public static final double[][] special = { { 5698, 1.05 }, { 5680, 1.05 }, { 1231, 1.05 }, { 1215, 1.05 }, { 3204, 0.90 }, { 1305, 1.15 }, { 1434, 1.45 }, { 11802, 1.375 },
			{ 11804, 1.21 }, { 11806, 1.10 }, { 11808, 1.10 }, { 861, 1.1 }, { 4151, 1.1 }, { 10887, 1.2933 }, { 12926, 1.35 } };

	public static double getSpecialStr(Player c) {
		for (double[] slot : special) {
			if (c.playerEquipment[3] == slot[0])
				return slot[1];
		}
		return 1;
	}

	public static final int[] obsidianWeapons = { 746, 747, 6523, 6525, 6526, 6527, 6528 };

	public static boolean hasObsidianEffect(Player c) {
		if (c.playerEquipment[2] != 11128)
			return false;

		for (int weapon : obsidianWeapons) {
			if (c.playerEquipment[3] == weapon)
				return true;
		}
		return false;
	}

	public static boolean hasVoid(Player c) {
		return c.playerEquipment[c.playerHat] == 11665 && c.playerEquipment[c.playerLegs] == 8840 || c.playerEquipment[c.playerLegs] == 13073 && c.playerEquipment[c.playerChest] == 8839 || c.playerEquipment[c.playerChest] == 13072 && c.playerEquipment[c.playerHands] == 8842;
	}

	public static int bestMeleeDef(Player c) {
		if (c.playerBonus[5] > c.playerBonus[6] && c.playerBonus[5] > c.playerBonus[7]) {
			return 5;
		}
		if (c.playerBonus[6] > c.playerBonus[5] && c.playerBonus[6] > c.playerBonus[7]) {
			return 6;
		}
		return c.playerBonus[7] <= c.playerBonus[5] || c.playerBonus[7] <= c.playerBonus[6] ? 5 : 7;
	}

	public static int calculateMeleeDefence(Player c) {
		int defenceLevel = c.getSkills().getLevel(Skill.DEFENCE);
		int i = c.playerBonus[bestMeleeDef(c)];
		if (c.prayerActive[0]) {
			defenceLevel += c.getSkills().getActualLevel(Skill.DEFENCE) * 0.05;
		} else if (c.prayerActive[5]) {
			defenceLevel += c.getSkills().getActualLevel(Skill.DEFENCE) * 0.1;
		} else if (c.prayerActive[13]) {
			defenceLevel += c.getSkills().getActualLevel(Skill.DEFENCE) * 0.15;
		} else if (c.prayerActive[25]) {
			defenceLevel += c.getSkills().getActualLevel(Skill.DEFENCE) * 0.2;
		} else if (c.prayerActive[26]) {
			defenceLevel += c.getSkills().getActualLevel(Skill.DEFENCE) * 0.25;
		}
		return (int) (defenceLevel + (i * 0.03));// 4
	}

	public static int bestMeleeAtk(Player c) {
		if (c.playerBonus[0] > c.playerBonus[1] && c.playerBonus[0] > c.playerBonus[2]) {
			return 0;
		}
		if (c.playerBonus[1] > c.playerBonus[0] && c.playerBonus[1] > c.playerBonus[2]) {
			return 1;
		}
		return c.playerBonus[2] <= c.playerBonus[1] || c.playerBonus[2] <= c.playerBonus[0] ? 0 : 2;
	}

	public static int calculateMeleeAttack(Player c) {
		int attackLevel = c.getSkills().getLevel(Skill.ATTACK);
		if (c.prayerActive[2]) {
			attackLevel += c.getSkills().getActualLevel(Skill.ATTACK) * 0.05;
		} else if (c.prayerActive[7]) {
			attackLevel += c.getSkills().getActualLevel(Skill.ATTACK) * 0.1;
		} else if (c.prayerActive[15]) {
			attackLevel += c.getSkills().getActualLevel(Skill.ATTACK) * 0.15;
		} else if (c.prayerActive[25]) {
			attackLevel += c.getSkills().getActualLevel(Skill.ATTACK) * 0.15;
		} else if (c.prayerActive[26]) {
			attackLevel += c.getSkills().getActualLevel(Skill.ATTACK) * 0.2;
		}
		if (c.playerEquipment[c.playerWeapon] == 4153 || c.playerEquipment[c.playerWeapon] == 12848) {
			attackLevel -= c.getSkills().getActualLevel(Skill.ATTACK) * 0.15;
		}
		if (c.playerEquipment[c.playerWeapon] == 4718 && c.playerEquipment[c.playerHat] == 4716 && c.playerEquipment[c.playerChest] == 4720
				&& c.playerEquipment[c.playerLegs] == 4722) {
			attackLevel -= c.getSkills().getActualLevel(Skill.ATTACK) * 0.15;
		}
		if (c.fullVoidMelee()) {
			attackLevel += c.getSkills().getActualLevel(Skill.ATTACK) * 0.10;
		}
		if (c.fullVoidSupremeMelee()) {
			attackLevel += c.getSkills().getActualLevel(Skill.ATTACK) * 0.15;
		}
		if (c.getItems().isWearingItem(20784)) {
			attackLevel += c.getSkills().getActualLevel(Skill.ATTACK) * 0.20;
		}
		if (c.getItems().isWearingItem(22545)) {
			attackLevel += c.getSkills().getActualLevel(Skill.ATTACK) * 0.20;
		}
		if (c.getItems().isWearingItem(33778)) {
			attackLevel += c.getSkills().getActualLevel(Skill.ATTACK) * 0.20;
		}
		if (c.getItems().isWearingItem(33780)) {
			attackLevel += c.getSkills().getActualLevel(Skill.ATTACK) * 0.20;
		}
		if (c.getItems().isWearingItem(33779)) {
			attackLevel += c.getSkills().getActualLevel(Skill.ATTACK) * 0.20;
		}
		
		
		if (c.debugMessage)
			c.sendMessage("Accuracy from whip: "+ attackLevel +"");
		if (c.getItems().isWearingItem(19675, c.playerWeapon) && c.getArcLightCharge() > 0) {
			if (c.debugMessage)
					c.sendMessage("Accuracy on reg: "+ attackLevel +"");
			NPC npc = NPCHandler.npcs[c.npcIndex];
			if (Misc.linearSearch(Config.DEMON_IDS, npc.npcType) != -1) {
				attackLevel += c.getSkills().getActualLevel(Skill.ATTACK) * 0.80;
				if (c.debugMessage)
					c.sendMessage("Accuracy on demon: "+ attackLevel +"");
			}
		}
		if (c.npcIndex > 0 && c.getSlayer().getTask().isPresent()) {
			NPC npc = NPCHandler.npcs[c.npcIndex];
			if (npc != null && c.getSlayer().getTask().get().matches(npc.getDefinition().getNpcName()) || npc.npcType == 7413) {
				boolean SLAYER_HELM = IntStream.of(c.SLAYER_HELMETS).anyMatch(i -> c.getItems().isWearingItem(i));
				if (!c.getItems().isWearingItem(4081) && SLAYER_HELM || c.getItems().isWearingItem(8901)) {
					attackLevel += c.getSkills().getActualLevel(Skill.ATTACK) * 0.15;
				}
			}
			if (c.getItems().isWearingItem(4081, c.playerAmulet)) {
				if (Misc.linearSearch(Config.UNDEAD_IDS, npc.npcType) != -1) {
					attackLevel += c.getSkills().getActualLevel(Skill.ATTACK) * 0.15;
				}
			}
			if (c.getItems().isWearingItem(12018, c.playerAmulet)) {
				if (Misc.linearSearch(Config.UNDEAD_IDS, npc.npcType) != -1) {
					attackLevel += c.getSkills().getActualLevel(Skill.ATTACK) * 0.20;
				}
			}
		}
		attackLevel *= c.specAccuracy;
		int i = c.playerBonus[bestMeleeAtk(c)];
		i += c.bonusAttack;
		if (hasObsidianEffect(c) || c.fullVoidMelee()) {
			i *= 1.10;
		}
		if (c.fullVoidSupremeMelee()) {
			i *= 1.20;
		}
		return (int) (attackLevel + (attackLevel * 0.20) + (i + i * 0.10));
	}
}