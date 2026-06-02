/**
 * 
 */
package valius.model.entity.npc.combat.impl.general;

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
 * @author ReverendDread
 * Apr 12, 2019
 */
@ScriptSettings(
	npcIds = { 8610, 8611 }
)
public class Wyrm extends CombatScript {

	private static final int MELEE_ATTACK = 8270, MAGIC_ATTACK = 8271;
	private static final int PRE = 8610, POST = 8611;
	private static final int TRANSFORM = 8268;
	private static final Graphic GRAPHICS = new Graphic(1635);
	private static final Projectile PROJECTILE = new Projectile(1634, 50, 30, 0, 100, 0, 50);
	
	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#attack(ethos.model.entity.npc.NPC, ethos.model.entity.Entity)
	 */
	@Override
	public int attack(NPC npc, Entity target) {
		if (npc.npcType == PRE) {
			npc.requestTransform(POST);
			npc.startAnimation(TRANSFORM);
			npc.npcType = POST;
			return 2;
		}
		boolean melee = npc.getLocation().withinDistance(target.getLocation(), 3);
		npc.startAnimation(melee ? MELEE_ATTACK : MAGIC_ATTACK);
		handleHit(npc, target, melee ? CombatType.MELEE : CombatType.MAGE, melee ? null : PROJECTILE, melee ? null : GRAPHICS, new Hit(Hitmark.HIT, 
				getRandomMaxHit(npc, target, melee ? CombatType.MELEE : CombatType.MAGE, 13), 2));
		return 4;
	}
	
	
	@Override
	public void handleDeath(NPC npc, Entity source) {
		npc.npcType = PRE;
	}

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#getAttackDistance(ethos.model.entity.npc.NPC)
	 */
	@Override
	public int getAttackDistance(NPC npc) {
		return 8;
	}

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#ignoreProjectileClipping()
	 */
	@Override
	public boolean ignoreProjectileClipping() {
		return false;
	}

}
