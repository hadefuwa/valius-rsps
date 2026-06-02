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
import valius.util.Misc;

/**
 * @author Patrity
 *
 */
@ScriptSettings(
		npcNames = { },
		npcIds = { 7585 }
	)
public class IceDemon extends CombatScript {

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#attack(ethos.model.entity.npc.NPC, ethos.model.entity.Entity)
	 */
	@Override
	public int attack(NPC npc, Entity target) {
		int barrageChance = Misc.random(1, 100);
		boolean melee = target.distanceToPoint(npc.getX(), npc.getY()) <= 2;
		int damage = getRandomMaxHit(npc, target, melee ? CombatType.MELEE : CombatType.MAGE, melee ? 28 : 30);
		npc.startAnimation(64);
		if(barrageChance >= 90 || !melee) {
			npc.attackType = CombatType.MAGE;
			handleHit(npc, target, CombatType.MAGE, new Projectile(368, 0, 15, 0, 100, 0, 50), new Graphic (369, 0), new Hit(Hitmark.HIT, damage, 4));
			target.asPlayer().sendMessage("You have been frozen!");
			target.asPlayer().freezeTimer = 8;
		} else {
			handleHit(npc, target, CombatType.MELEE, new Hit(Hitmark.HIT, damage, 1));
		}
		return 5;
	}

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#getAttackDistance(ethos.model.entity.npc.NPC)
	 */
	@Override
	public int getAttackDistance(NPC npc) {
		return npc.attackType == CombatType.MELEE ? 1 : 8;
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
		if (distance > 1) {
			npc.attackType = CombatType.MAGE;
		} else {
			npc.attackType = CombatType.MELEE;
		}
	}

}