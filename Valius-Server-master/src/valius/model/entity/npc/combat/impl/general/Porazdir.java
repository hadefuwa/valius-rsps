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
 * @author Created by Crank Mar 16, 2019
 * 12:23:25 AM
 */

@ScriptSettings(
	npcNames = { "Porazdir" },
	npcIds = { 7860 }	
)
public class Porazdir extends CombatScript {
	
	NPC PORAZDIR = WildernessBossHandler.getActiveNPC();

	@Override
	public int attack(NPC npc, Entity target) {
		int chance = Misc.random(1, 5);
		if (chance > 3) {
			if (Boundary.isIn(target, Boundary.PURSUIT_AREAS)) {
				int damage = getRandomMaxHit(npc, target, CombatType.MAGE, 43);
				npc.startAnimation(7838);
				handleHit(npc, target, CombatType.MAGE, new Projectile(1514, 30, 30, 15, 40, 0, 16), new Graphic(78, 0),
						new Hit(Hitmark.HIT, damage, 2));
			}
		} else {
			int damage = getRandomMaxHit(npc, target, CombatType.MELEE, 26);
			npc.startAnimation(7840);
			handleHit(npc, target, CombatType.MELEE, null, null, new Hit(Hitmark.HIT, damage, 1));
		}
		return 4;
	}
	
	public static void PorazdirDeath() {
		WildernessBossHandler.giveRewards();
	}
	
	@Override
	public int getAttackDistance(NPC npc) {
		return npc.attackType == CombatType.MELEE ? 4 : 20;
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
		return true;
	}
	
}
