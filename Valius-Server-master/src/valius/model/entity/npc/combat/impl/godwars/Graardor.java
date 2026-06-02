/**
 * 
 */
package valius.model.entity.npc.combat.impl.godwars;

import valius.model.entity.Entity;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.combat.CombatScript;
import valius.model.entity.npc.combat.Hit;
import valius.model.entity.npc.combat.Projectile;
import valius.model.entity.npc.combat.ScriptSettings;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Hitmark;
import valius.util.Misc;

/**
 * @author Patrity & Divine
 *
 */
@ScriptSettings(
	npcIds = { 2215 }
)
public class Graardor extends CombatScript {

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#attack(ethos.model.entity.npc.NPC, ethos.model.entity.Entity)
	 */
	@Override
	public int attack(NPC npc, Entity target) {
		int forceChat = Misc.random(1, 5);
		//boolean melee = target.distanceToPoint(npc.getX(), npc.getY()) <= 3;
		boolean melee = true;
		if (Misc.random(1, 4) == 4)
			melee = false;
		if (target.distanceToPoint(npc.getX(), npc.getY()) >= 3)
			melee = false;
		
		if (forceChat == 1) {
			ForceChat(npc);
		}
		
		if(!melee) {
			int rangeDamage = getRandomMaxHit(npc, target, CombatType.RANGE, 35);
			npc.startAnimation(7021);
			npc.attackType = CombatType.RANGE;
			handleHit(npc, target, CombatType.RANGE, new Projectile(1202, 0, 20, 0, 100, 0, 50), new Hit(Hitmark.HIT, rangeDamage, 4));
		} else {
			int meleeDamage = getRandomMaxHit(npc, target, CombatType.MELEE, 65);
			npc.startAnimation(7018);
			handleHit(npc, target, CombatType.MELEE, new Hit(Hitmark.HIT, meleeDamage, 1));
		}
		return 6;
	}
	
	/*
	 * Overhead chat messages for Graardor
	 */
	void ForceChat(NPC npc) {
		int message = Misc.random(1-11);
		System.out.println("Graardor Forced Chat test.");
		switch (message) {
		case 1:
			npc.forceChat("Break their bones!");
			break;
		case 2:
			npc.forceChat("Death to our enemies!");
			break;
		case 3:
			npc.forceChat("Brargh!");
			break;
		case 4:
			npc.forceChat("For the glory of Bandos!");
			break;
		case 5:
			npc.forceChat("Split their skulls!");
			break;
		case 6:
			npc.forceChat("We feast on the bones of our enemies tonight!");
			break;
		case 7:
			npc.forceChat("CHAAARGE!");
			break;
		case 8:
			npc.forceChat("Crush them underfoot!");
			break;
		case 9:
			npc.forceChat("All glory to Bandos!");
			break;
		case 10:
			npc.forceChat("GRRRAAAAAR!");
			break;
		case 11:
			npc.forceChat("FOR THE GLORY OF THE BIG HIGH WAR GOD!");
			break;

		}
	}

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#getAttackDistance(ethos.model.entity.npc.NPC)
	 */
	@Override
	public int getAttackDistance(NPC npc) {
		return npc.attackType == CombatType.MELEE ? 4 : 8;
	}

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#ignoreProjectileClipping()
	 */
	@Override
	public boolean ignoreProjectileClipping() {
		return false;
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
		return 20;
	}
	
	@Override
	public boolean followClose(NPC npc) {
		return true;
	}

}