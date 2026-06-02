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

@ScriptSettings(
	npcNames = {}, 
	npcIds = { 7563 }
)

/*
 * anims 7422 dragonfire 7421 range/bite? 7424 ground slam
 */
public class Muttadile extends CombatScript {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ethos.model.entity.npc.combat.CombatScript#attack(ethos.model.entity.npc.NPC,
	 * ethos.model.entity.Entity)
	 */
	@Override
	public int attack(NPC npc, Entity target) {

		boolean dragonFire = false;
		if (Misc.random(1, 100) >= 80)
			dragonFire = true;
		int damage = getRandomMaxHit(npc, target, dragonFire ? CombatType.DRAGON_FIRE : CombatType.MAGE,
				dragonFire ? 30 : 38);

		if (!dragonFire) {
			npc.startAnimation(7421);
			npc.attackType = CombatType.MAGE;
			handleHit(npc, target, CombatType.MAGE, new Projectile(1293, 20, 0, 0, 100, 0, 50), new Graphic(1294, 0),
					new Hit(Hitmark.HIT, damage, 4));

		} else {
			npc.startAnimation(7422);
			handleHit(npc, target, CombatType.DRAGON_FIRE, new Projectile(393, 20, 40, 0, 100, 0, 50),
					new Graphic(430, 0), new Hit(Hitmark.HIT, damage, 4));
			target.asPlayer().sendMessage("You've been hit by Muttadile's Dragonfire!");
		}
		return 5;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ethos.model.entity.npc.combat.CombatScript#getAttackDistance(ethos.model.
	 * entity.npc.NPC)
	 */
	@Override
	public int getAttackDistance(NPC npc) {
		return 8;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ethos.model.entity.npc.combat.CombatScript#ignoreProjectileClipping()
	 */
	@Override
	public boolean ignoreProjectileClipping() {
		return true;
	}
}