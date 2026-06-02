package valius.model.entity.npc.combat.impl.starterdungeon;

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
 * 
 * @author Divine | 11:29:14 p.m. | Oct. 15, 2019
 *
 */

@ScriptSettings(npcIds = { 5126 })

//T1
public class MutatedRat extends CombatScript {
	
	@Override
	public int attack(NPC npc, Entity target) {
		int randomAttack = Misc.random(1, 4);
		
		if (randomAttack == 1) {
			npc.startAnimation(6514);
			handleHit(npc, target, CombatType.RANGE, new Projectile(1078, 50, 35, 0, 100, 1, 50), null, new Hit(Hitmark.HIT, 1, 3));
			return 4;
		} else {
			npc.startAnimation(6513);
			handleHit(npc, target, CombatType.MELEE, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MELEE, 1), 2));
		}
		return 4;
	}

	@Override
	public void handleDeath(NPC npc, Entity entity) {
	}
	
	@Override
	public int getAttackDistance(NPC npc) {
		return 20;
	}
	
	@Override
	public boolean ignoreProjectileClipping() {
		return true;
	}

	@Override
	public int getFollowDistance(NPC npc) {
		return 20;
	}
	
	@Override
	public boolean ignoreCollision() {
		return false;
	}
	
	@Override
	public boolean isAggressive(NPC npc) {
		return false;
	}

	@Override
	public boolean followClose(NPC npc) {
		return true;
	}

}

