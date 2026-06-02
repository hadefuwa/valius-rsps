package valius.model.entity.player.combat.melee;

import java.util.Objects;

import valius.Config;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.combat.AttackNPC;
import valius.model.entity.player.combat.AttackPlayer;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Hitmark;
import valius.model.entity.player.skills.Skill;
import valius.model.multiplayer_session.MultiplayerSessionType;
import valius.model.multiplayer_session.duel.DuelSession;
import valius.model.multiplayer_session.duel.DuelSessionRules.Rule;
import valius.util.Misc;
import valius.world.World;

public class MeleeExtras {

	public static void applySmite(Player c, int index, int damage) {
		if (!c.prayerActive[23])
			return;
		if (damage <= 0)
			return;
		if (PlayerHandler.players[index] != null) {
			Player c2 = PlayerHandler.players[index];
			c2.getSkills().decreaseLevelOrMin(damage / 4, 0, Skill.PRAYER);
			c2.getPA().refreshSkill(5);
		}
	}
	
	public static boolean checkMultiScytheReqsNPC(int i) {
		if (NPCHandler.npcs[i] == null) {
			return false;
		}
		return true;
	}

	public static boolean checkMultiScytheReqs(Player c, int i) {
		if (PlayerHandler.players[i] == null) {
			return false;
		}
		if (i == c.getIndex())
			return false;
		if (!PlayerHandler.players[i].inWild()) {
			return false;
		}
		if (Config.COMBAT_LEVEL_DIFFERENCE) {
			int combatDif1 = c.getCombat().getCombatDifference(c.combatLevel, PlayerHandler.players[i].combatLevel);
			if (combatDif1 > c.wildLevel || combatDif1 > PlayerHandler.players[i].wildLevel) {
				c.sendMessage("Your combat level difference is too great to attack that player here.");
				return false;
			}
		}

		if (Config.SINGLE_AND_MULTI_ZONES) {
			if (!PlayerHandler.players[i].inMulti()) { // single combat zones
				if (PlayerHandler.players[i].underAttackBy != c.getIndex() && PlayerHandler.players[i].underAttackBy != 0) {
					return false;
				}
				if (PlayerHandler.players[i].getIndex() != c.underAttackBy && c.underAttackBy != 0) {
					c.sendMessage("You are already in combat.");
					return false;
				}
			}
		}
		return true;
	}
	
	public static void appendMultiScytheNPC(Player c, int npcId) {
		if (NPCHandler.npcs[npcId] != null) {
			NPC n = NPCHandler.npcs[npcId];
			if (n.isDead) {
				return;
			}
			if (n.getHeight() != c.getHeight()) {
				return;
			}
			if (checkMultiScytheReqsNPC(npcId)) {
				c.barrageCount++;
				c.multiAttacking = true;
				NPCHandler.npcs[npcId].underAttackBy = c.getIndex();
				NPCHandler.npcs[npcId].underAttack = true;
				if (Misc.random(c.getCombat().calculateMeleeAttack()) > Misc.random(NPCHandler.npcs[npcId].defence)) {

					int damage = (Misc.random(c.getCombat().calculateMeleeMaxHit()))/2;
					if (n.getHealth().getAmount() - damage < 0) {
						damage = n.getHealth().getAmount();
					}
					if (damage == 0) {
						return;
					}
					if (n.npcType == 7413) {
						n.appendDamage(c, c.getCombat().calculateMeleeMaxHit(), Hitmark.HIT);
					} else {
						AttackPlayer.addCombatXP(c, CombatType.MELEE, damage / 3);
						n.appendDamage(c, damage, Hitmark.HIT);
					}
					c.totalDamageDealt += damage;
				} else {
					
				}
			}
		}
	}

	public static void appendVengeanceNPC(Player c, int damage, int i) {
		if (damage <= 0)
			return;
		if (!c.vengOn)
			return;
		
		if (NPCHandler.npcs[i] != null) {
			c.forcedText = "Taste vengeance!";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			c.vengOn = false;
			if ((NPCHandler.npcs[i].getHealth().getAmount() - damage) > 0) {
				damage = (int) (damage * 0.75);
				if (damage > NPCHandler.npcs[i].getHealth().getAmount()) {
					damage = NPCHandler.npcs[i].getHealth().getAmount();
				}
				NPCHandler.npcs[i].appendDamage(c, damage, damage > 0 ? Hitmark.HIT : Hitmark.MISS);
			}
		}
		c.updateRequired = true;
	}

	public static void appendVengeance(Player c, int otherPlayer, int damage) {
		if (damage <= 0)
			return;
		Player o = PlayerHandler.players[otherPlayer];
		o.forcedText = "Taste vengeance!";
		o.forcedChatUpdateRequired = true;
		o.updateRequired = true;
		o.vengOn = false;
		if ((o.getHealth().getAmount() - damage) > 0) {
			damage = (int) (damage * 0.75);
			c.appendDamage(damage, damage > 0 ? Hitmark.HIT : Hitmark.MISS);
			c.addDamageTaken(o, damage);
		}
		c.updateRequired = true;
	}

	public static void applyRecoilNPC(Player c, int damage, int i) {
		if (damage <= 0) 
			return;
		if (damage > 0 && (c.playerEquipment[c.playerRing] == 2550 || c.playerEquipment[c.playerRing] == 19550)) {
			int recDamage = damage / 10;
			if (recDamage < 1 && damage > 0) {
				recDamage = 1;
			}
			if (NPCHandler.npcs[i].getHealth().getAmount() <= 0 || NPCHandler.npcs[i].isDead) {
				return;
			}
			NPCHandler.npcs[i].appendDamage(c, recDamage, Hitmark.HIT);
			if (c.playerEquipment[c.playerRing] == 2550) {
				removeRecoil(c);
				c.recoilHits += recDamage;
			}
		}
	}

	public static void applyRecoil(Player c, int damage, int i) {
		if (damage <= 0) 
			return;
		if (Boundary.isIn(c, Boundary.DUEL_ARENA)) {
			DuelSession session = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
			if (Objects.nonNull(session)) {
				if (session.getRules().contains(Rule.NO_RINGS)) {
					return;
				}
			}
		}
		if (damage > 0 && (PlayerHandler.players[i].playerEquipment[c.playerRing] == 2550 || PlayerHandler.players[i].playerEquipment[c.playerRing] == 19550)) {
			int recDamage = damage / 10 + 1;
			c.appendDamage(recDamage, recDamage > 0 ? Hitmark.HIT : Hitmark.MISS);
			c.addDamageTaken(PlayerHandler.players[i], recDamage);
			c.updateRequired = true;
			if (c.playerEquipment[c.playerRing] == 2550) {
				removeRecoil(c);
				c.recoilHits += damage;
			}
		}
	}

	public static void removeRecoil(Player c) {
		if (c.recoilHits >= 200) {
			if (c.playerEquipment[c.playerRing] == 2550) {
				c.getItems().removeItem(2550, c.playerRing);
				c.getItems().deleteItem(2550, c.getItems().getItemSlot(2550), 1);
				c.sendMessage("Your ring of recoil shaters!");
				c.recoilHits = 0;
			}
		} else {
			c.recoilHits++;
		}
	}
	
	/*
	 * Handles removing kills from the Bracelet of slaughter under it is destroyed
	 */
	
	public static void removeSlaughter(Player c) {
		if (c.SlaughterKills >= 30) {
			if (c.playerEquipment[c.playerHands] == 21183) {
				c.getItems().removeItem(21183, c.playerHands);
				c.getItems().deleteItem(21183, c.getItems().getItemSlot(21183), 1);
				c.sendMessage("@pur@Your Bracelet of slaughter has turned to dust!");
				c.SlaughterKills = 0;
			}
		} else {
			c.SlaughterKills++;
		}
	}

	public static void graniteMaulSpecial(Player c) {
		if (c.playerIndex > 0 && c.getHealth().getAmount() > 0) {
			Player o = PlayerHandler.players[c.playerIndex];
			if (c.goodDistance(c.getX(), c.getY(), o.getX(), o.getY(), c.getCombat().getRequiredDistance())) {
				if (c.getCombat().checkReqs()) {
					if (c.getCombat().checkSpecAmount(4153) || c.getCombat().checkSpecAmount(12848)) {
						boolean hit = Misc.random(c.getCombat().calculateMeleeAttack()) > Misc.random(o.getCombat().calculateMeleeDefence());
						int damage = 0;
						if (hit)
							damage = Misc.random(c.getCombat().calculateMeleeMaxHit());
						if (o.prayerActive[18])
							damage *= .6;
						if (o.getHealth().getAmount() - damage <= 0) {
							damage = o.getHealth().getAmount();
						}
						if (o.getHealth().getAmount() > 0) {
							o.appendDamage(damage, damage > 0 ? Hitmark.HIT : Hitmark.MISS);
							c.startAnimation(1667);
							o.gfx100(337);
							o.addDamageTaken(c, damage);
						}
					}
				}
			}
		} else if (c.npcIndex > 0) {
			int x = NPCHandler.npcs[c.npcIndex].getX();
			int y = NPCHandler.npcs[c.npcIndex].getY();
			if (c.goodDistance(c.getX(), c.getY(), x, y, NPCHandler.npcs[c.npcIndex].getSize()) && AttackNPC.isAttackable(c, c.npcIndex)) {
				if (c.getCombat().checkSpecAmount(4153) || c.getCombat().checkSpecAmount(12848)) {
					int damage = Misc.random(c.getCombat().calculateMeleeMaxHit());
					if (NPCHandler.npcs[c.npcIndex].getHealth().getAmount() - damage < 0) {
						damage = NPCHandler.npcs[c.npcIndex].getHealth().getAmount();
					}
					if (NPCHandler.npcs[c.npcIndex].getHealth().getAmount() > 0) {
						NPCHandler.npcs[c.npcIndex].appendDamage(c, damage, Hitmark.HIT);
						c.startAnimation(1667);
						c.gfx100(337);
					}
				}
			}
		}
	}
}