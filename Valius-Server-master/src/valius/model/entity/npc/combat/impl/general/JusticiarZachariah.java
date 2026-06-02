package valius.model.entity.npc.combat.impl.general;

import valius.model.entity.Entity;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.bosses.wildernessboss.WildernessBossHandler;
import valius.model.entity.npc.combat.CombatScript;
import valius.model.entity.npc.combat.Graphic;
import valius.model.entity.npc.combat.Hit;
import valius.model.entity.npc.combat.Projectile;
import valius.model.entity.npc.combat.ScriptSettings;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Hitmark;
import valius.util.Misc;

/**
 * 
 * @author Created by Crank Mar 17, 2019
 * 12:17:37 AM
 */

@ScriptSettings(
		npcNames = { "Justiciar Zachariah" },
		npcIds = { 7858 }
)

public class JusticiarZachariah extends CombatScript {
	
	NPC JUSTICIAR = WildernessBossHandler.getActiveNPC();

	@Override
	public int attack(NPC npc, Entity target) {
		int chance = Misc.random(1, 5);
		if (chance > 3) {
			if (Boundary.isIn(target, Boundary.PURSUIT_AREAS)) {
				int damage = getRandomMaxHit(npc, target, CombatType.MAGE, 43);
				handleHit(npc, target, CombatType.MAGE, new Projectile(-1, 30, 30, 15, 40, 0, 16), new Graphic(1515, 0), new Hit(Hitmark.HIT, damage, 2));
			}
		} else {
			int damage = getRandomMaxHit(npc, target, CombatType.MELEE, 26);
			npc.startAnimation(7853);
			handleHit(npc, target, CombatType.MELEE, null, null, new Hit(Hitmark.HIT, damage, 1));
		}
		return 4;
	}
	
	public static void JusticiarDeath() {
		WildernessBossHandler.giveRewards();
	}

	@Override
	public int getAttackDistance(NPC npc) {
		return npc.attackType == CombatType.MELEE ? 4 : 15;
	}

	@Override
	public boolean ignoreProjectileClipping() {
		return true;
	}

	@Override
	public int getFollowDistance(NPC npc) {
		return 10;
	}

	@Override
	public boolean followClose(NPC npc) {
		return false;
	}

}
