package valius.model.entity.npc.combat.impl.starterdungeon;

import valius.model.entity.Entity;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.combat.CombatScript;
import valius.model.entity.npc.combat.Hit;
import valius.model.entity.npc.combat.ScriptSettings;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Hitmark;

/**
 * 
 * @author Divine | 11:29:03 p.m. | Oct. 15, 2019
 *
 */

@ScriptSettings(npcIds = { 8709 })

//T5
public class ShadedBeast extends CombatScript {
	
	@Override
	public int attack(NPC npc, Entity target) {
		npc.startAnimation(5617);
		handleHit(npc, target, CombatType.MELEE, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MELEE, 14), 1));
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
