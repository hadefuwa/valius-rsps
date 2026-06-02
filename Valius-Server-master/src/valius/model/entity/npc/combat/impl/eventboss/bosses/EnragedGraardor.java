/**
 * 
 */
package valius.model.entity.npc.combat.impl.eventboss.bosses;


import java.util.List;

import com.google.common.collect.Lists;

import valius.model.entity.Entity;
import valius.model.entity.HealthStatus;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.bosses.EventBoss.EventBossChest;
import valius.model.entity.npc.bosses.EventBoss.EventBossHandler;
import valius.model.entity.npc.combat.CombatScript;
import valius.model.entity.npc.combat.Hit;
import valius.model.entity.npc.combat.Projectile;
import valius.model.entity.npc.combat.ScriptSettings;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Hitmark;
import valius.util.Misc;

/**
 * @author Patrity & Divine
 *
 */
@ScriptSettings(
	npcNames = { },
	npcIds = { 5462 }
)
public class EnragedGraardor extends CombatScript {
	
	private static final int EVENT_KEY = 13302;
	
	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#attack(ethos.model.entity.npc.NPC, ethos.model.entity.Entity)
	 */
	@Override
	public int attack(NPC npc, Entity target) {
		
		boolean melee = true;
		
		if (Misc.random(1, 4) == 4)
			melee = false;
		if (target.distanceToPoint(npc.getX(), npc.getY()) >= 3)
			melee = false;
		
		if(!melee) {
			npc.startAnimation(7021);
			PlayerHandler.getPlayers().forEach((player) -> {
				if (Boundary.isIn(player, Boundary.EVENT_AREAS)) { //Problem with using this is if their in another event area, they'll take damage.
					Hit hit = new Hit(Hitmark.HIT, getRandomMaxHit(npc, player, CombatType.RANGE, 35), 5);
					handleDodgableAttack(npc, player, CombatType.RANGE, new Projectile(1202, 50, 20, 0, 50, 0, 50), null, hit);
				}
			});
		} else {
			int meleeDamage = getRandomMaxHit(npc, target, CombatType.MELEE, 65);
			npc.startAnimation(7018);
			handleHit(npc, target, CombatType.MELEE, new Hit(Hitmark.HIT, meleeDamage, 1));
		}
		return 6;
	}
	
	@Override
	public void init(NPC npc) {
		npc.getHealth().resolveStatus(HealthStatus.POISON, Integer.MAX_VALUE);
		npc.getHealth().resolveStatus(HealthStatus.VENOM, Integer.MAX_VALUE);
	}
	
	/**
	 * Spawns the event chest at the location of the event boss & gives players the key
	 * @param p
	 */
	public static void graardorDeath() {
		GlobalMessages.send("The Event Boss Enraged Graardor has been defeated!", GlobalMessages.MessageType.EVENT);
		List<String> givenToIP = Lists.newArrayList();
		PlayerHandler.nonNullStream().filter(p -> p.EventBossDamage > 0).forEach(p -> { 
			if(!givenToIP.contains(p.connectedFrom)) {
				if (p.EventBossDamage >= 200) {
					p.getItems().addItemUnderAnyCircumstance(EVENT_KEY, 1);
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

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#getAttackDistance(ethos.model.entity.npc.NPC)
	 */
	@Override
	public int getAttackDistance(NPC npc) {
		return npc.attackType == CombatType.MELEE ? 4 : 15;
	}

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#ignoreProjectileClipping()
	 */
	@Override
	public boolean ignoreProjectileClipping() {
		return true;
	}
	
	@Override
	public void attackStyleChange(NPC npc, Entity target) {
		int distance = npc.distanceToPoint(target.getX(), target.getY());
		if (distance >= 3) {
			npc.attackType = CombatType.RANGE;
		} else {
			npc.attackType = CombatType.MELEE;
		}
	}
	
	@Override
	public int getFollowDistance(NPC npc) {
		return 15;
	}
	
	@Override
	public boolean followClose(NPC npc) {
		return true;
	}

}