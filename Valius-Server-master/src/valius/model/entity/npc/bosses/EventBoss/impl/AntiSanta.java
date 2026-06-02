package valius.model.entity.npc.bosses.EventBoss.impl;

import java.util.List;

import com.google.common.collect.Lists;

import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Hitmark;
import valius.util.Misc;

/**
 * 
 * @author Divine | Dec. 31, 2018 , 9:59:08 p.m.
 *
 */

public class AntiSanta {
	
	public static int specialAmount = 0;
	
	public static void ANTISANTASpecial(Player player) {
		NPC ANTISANTA = NPCHandler.getNpc(5001);
		
		if (ANTISANTA.isDead) {
			return;
		}
		boolean distanceToSanta = player.goodDistance(ANTISANTA.getX(), ANTISANTA.getY(), player.getX(), player.getY(), 3);
		int randomattack = Misc.random(10);
		
		if (randomattack <= 8) {
			PlayerHandler.nonNullStream().filter(p -> Boundary.isIn(p, Boundary.EVENT_AREAS))
			.forEach(p -> {
			ANTISANTA.attackType = CombatType.MAGE;
			ANTISANTA.projectileId = 316;
			ANTISANTA.endGfx = 318;
			ANTISANTA.maxHit = 5;
			ANTISANTA.hitDelayTimer = 3;
			ANTISANTA.faceEntity(0);
			ANTISANTA.appendDamage(Misc.random(10) + 5, Hitmark.HIT);
			p.freezeTimer = 10;
			});
		} else if (distanceToSanta && randomattack > 8) {
			PlayerHandler.nonNullStream().filter(p -> Boundary.isIn(p, Boundary.EVENT_AREAS))
			.forEach(p -> {
			ANTISANTA.attackType = CombatType.MELEE;
			ANTISANTA.projectileId = -1;
			ANTISANTA.endGfx = -1;
			ANTISANTA.maxHit = 10;
			ANTISANTA.faceEntity(0);
			ANTISANTA.appendDamage(Misc.random(15) + 5, Hitmark.HIT);
			});
		}
		
		//Messages depending on HP
		if (ANTISANTA.getHealth().getAmount() < 1250 && specialAmount == 0) {
			NPCHandler.npcs[ANTISANTA.getIndex()].forceChat("Give me your Presents!");
			specialAmount++;
		} else if ( ANTISANTA.getHealth().getAmount() < 1000 && specialAmount == 1) {
			NPCHandler.npcs[ANTISANTA.getIndex()].forceChat("Freeze!");
			specialAmount++;
		} else if (ANTISANTA.getHealth().getAmount() < 850 && specialAmount == 2) {
			NPCHandler.npcs[ANTISANTA.getIndex()].forceChat("No one will be getting Presents this year!");
			specialAmount++;
		}  else if (ANTISANTA.getHealth().getAmount() < 600 && specialAmount == 3) {
			NPCHandler.npcs[ANTISANTA.getIndex()].forceChat("You can't defeat me!");
			specialAmount++;
		}  else if (ANTISANTA.getHealth().getAmount() < 400 && specialAmount == 4) {
			NPCHandler.npcs[ANTISANTA.getIndex()].forceChat("Christmas will be ruined this year!");
			specialAmount++;
		}  else if (ANTISANTA.getHealth().getAmount() < 100 && specialAmount == 5) {
			NPCHandler.npcs[ANTISANTA.getIndex()].forceChat("You cannot kill the Anti-Santa!");
			specialAmount++;
		}  else if (ANTISANTA.getHealth().getAmount() < 5 && specialAmount == 6) {
			NPCHandler.npcs[ANTISANTA.getIndex()].forceChat("I'll be back soon!");
			specialAmount++;
		}
	}
	
	public static void rewardPlayers(Player player) { 
		List<String> givenToIP = Lists.newArrayList(); 
		PlayerHandler.nonNullStream().filter(p -> Boundary.isIn(p, Boundary.EVENT_AREAS)).forEach(p -> { 
			if(!givenToIP.contains(p.connectedFrom)) {
			int randomreward = Misc.random(101);
			if (p.summonId == 33133 || p.summonId == 33134) {
				int orn_amt = Misc.random(100, 1000);
			p.getItems().addItemUnderAnyCircumstance(33962, orn_amt);
			p.sendMessage("For having an Anti follower you receive an extra " + orn_amt + " Ornaments.");
			}
			if (randomreward >= 0 && randomreward <= 85) {
				p.sendMessage("You have defeated Anti Santa for now...");
				p.sendMessage("You receive some Ornaments to give to santa for rewards!");
				p.getItems().addItemUnderAnyCircumstance(33962, Misc.random(250, 1000));
			} else if (randomreward > 85 && randomreward <= 95) {
				p.sendMessage("You have defeated Anti Santa for now...");
				p.sendMessage("You receive some Ornaments to give to santa for rewards!");
				p.getItems().addItemUnderAnyCircumstance(33962, Misc.random(250, 1500));
			} else if (randomreward > 95 && randomreward <= 98) {
				p.sendMessage("You have defeated Anti Santa for now...");
				p.sendMessage("You receive a Pet Anti-Santa!");
				GlobalMessages.send(player.playerName+" has received a Pet Anti Santa from the Event Boss!", GlobalMessages.MessageType.LOOT);
				p.getItems().addItemUnderAnyCircumstance(33134, 1);
				p.getItems().addItemUnderAnyCircumstance(33962, Misc.random(250, 2000));
			} else if (randomreward > 98) {
				p.sendMessage("You have defeated Anti Santa for now...");
				p.sendMessage("You receive a Pet Anti Imp!");
				GlobalMessages.send(player.playerName+" has received a Pet Anti Imp from the Event Boss!", GlobalMessages.MessageType.LOOT);
				p.getItems().addItemUnderAnyCircumstance(33133, 1);
				p.getItems().addItemUnderAnyCircumstance(33962, Misc.random(250, 2000));
			}
			givenToIP.add(p.connectedFrom);
			}
		});
	}
}
