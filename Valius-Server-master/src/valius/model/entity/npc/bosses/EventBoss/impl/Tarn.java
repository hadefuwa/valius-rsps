package valius.model.entity.npc.bosses.EventBoss.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import valius.model.entity.HealthStatus;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.bosses.EventBoss.EventBossChest;
import valius.model.entity.npc.bosses.EventBoss.EventBossHandler;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.combat.CombatType;
import valius.util.Misc;
import valius.world.World;

/**
 * 
 * @author Divine | Dec. 31, 2018 , 9:59:37 p.m.
 *
 */

public class Tarn {

	public static void getCombatMode() {
		NPC tarn = EventBossHandler.getActiveNPC();
		
		if (tarn.isDead) {
			return;
		}
		int random = Misc.random(10);
		if(random < 8) {
			loadSpell(tarn);
		} else {
			loadMelee(tarn);
		}
	}


	/**
	 * Spawns the event chest at the location of the event boss & gives players the key
	 * @param p
	 */
	public static void tarnDeath() {
		GlobalMessages.send("The Event Boss Enraged Tarn has been defeated!", GlobalMessages.MessageType.EVENT);
		List<String> givenToIP = Lists.newArrayList();
		PlayerHandler.nonNullStream().filter(p -> p.EventBossDamage > 0).forEach(p -> { 
			if(!givenToIP.contains(p.connectedFrom)) {
				if (p.EventBossDamage >= 200) {
					p.getItems().addItemUnderAnyCircumstance(EventBossChest.EVENT_KEY, 1);
					p.sendMessage("You receive an Event key!");
					givenToIP.add(p.connectedFrom);
				} else if (p.EventBossDamage < 200) {
					p.sendMessage("You must deal @red@200+</col> damage to receive a key!");
				}
			} else {
				p.sendMessage("You can only receive 1 event key per IP");
			}
			p.EventBossDamage = 0;
		});
		EventBossChest.SpawnChest();
	}
	
	public static void performFreeze() {
		NPC tarn = EventBossHandler.getActiveNPC();
		int nX = tarn.getX();
		int nY = tarn.getY();
		//c.sendMessage(pX + " "+ pY);
		int centerX = nX + 2;
		int centerY = nY + 2;
		List<Player> localPlayers = PlayerHandler.nonNullStream().filter(player -> player.isWithinDistance(tarn.getLocation(), 10)).collect(Collectors.toList());
		
		localPlayers.stream().forEach(player -> {
			int offX = (nX - player.getX()) * -1;
			int offY = (nY - player.getY()) * -1;
			
			player.getPA().createPlayersProjectile(centerX, centerY, offX, offY, 40, 100, 1002, 22, 1, -player.getIndex() - 1, 15, 0);
			int damage = Misc.random(35);
			if(player.protectingMagic()) {
				int rnd = Misc.random(3);
				if(rnd == 1) {
					damage = 0;
				} else if(rnd == 0) {
					damage /= 2;
				} else {
					damage /= 1 + Misc.random(4);
				}
			}
			if (10 + Misc.random(player.getCombat().mageDef()) > Misc.random(150)) {
				damage = 0;
			}
			if (player.getHealth().getAmount() - damage < 0) {
				damage = player.getHealth().getAmount();
			}
			if(damage > 0) {
				player.freezeTimer = 5;
				player.sendMessage("You have been frozen!");
				player.gfx100(400);
			} else {
				player.gfx100(85);
			}
			World.getWorld().getNpcHandler().playerDamage(player, tarn.getIndex(), damage, -1);
			//player.getCombat().appendVengeanceNPC(damage,  tarn.getIndex());
		});
	}

	public static void performMelee(Player player) {

		int poisonChance = Misc.random(4);
		if(poisonChance == 0) {
			player.getHealth().proposeStatus(HealthStatus.POISON, 1 + Misc.random(4), Optional.of(EventBossHandler.getActiveNPC()));
			player.sendMessage("You have been poisoned by Tarn's Sting.");
		}
	}

	public static void loadMelee(NPC tarn) {
		tarn.attackType = CombatType.MELEE;
		tarn.hitDelayTimer = 5;
		tarn.maxHit = 25;
	}
	public static void loadSpell(NPC tarn) {
		tarn.attackType = CombatType.MAGE;
		tarn.hitDelayTimer = 2;
		tarn.maxHit = 35;
	}


}

