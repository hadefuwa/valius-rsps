/**
 * 
 */
package valius.model.entity.npc.combat.impl.xeric;

import valius.model.entity.Entity;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.combat.CombatScript;
import valius.model.entity.npc.combat.Graphic;
import valius.model.entity.npc.combat.Hit;
import valius.model.entity.npc.combat.Projectile;
import valius.model.entity.npc.combat.ScriptSettings;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Hitmark;

/**
 * @author Patrity
 *
 */
@ScriptSettings(
	npcNames = { },
	npcIds = { 7538 }
)
public class Vespine extends CombatScript {

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#attack(ethos.model.entity.npc.NPC, ethos.model.entity.Entity)
	 */
	@Override
	public int attack(NPC npc, Entity target) {
		int damage = getRandomMaxHit(npc, target, CombatType.RANGE, 20);
		npc.startAnimation(7455);
		handleHit(npc, target, CombatType.RANGE, new Projectile(328, 100, 40, 0, 100, 0, 50), new Graphic (331, 0), new Hit(Hitmark.HIT, damage, 4));		
		return 5;
	}

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#getAttackDistance(ethos.model.entity.npc.NPC)
	 */
	@Override
	public int getAttackDistance(NPC npc) {
		// TODO Auto-generated method stub
		return 8;
	}

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#ignoreProjectileClipping()
	 */
	@Override
	public boolean ignoreProjectileClipping() {
		// TODO Auto-generated method stub
		return true;
	}

}
