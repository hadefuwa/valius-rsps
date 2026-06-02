package valius.model.entity.npc.combat.impl.general;


import valius.model.entity.Entity;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.combat.CombatScript;
import valius.model.entity.npc.combat.Graphic;
import valius.model.entity.npc.combat.Hit;
import valius.model.entity.npc.combat.Projectile;
import valius.model.entity.npc.combat.ScriptSettings;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Hitmark;
import valius.util.Misc;

/**
 * 
 * @author Divine | 1:12:30 a.m. | Nov. 23, 2019
 *
 */

@ScriptSettings(
		npcNames = { "Chaos druid" },
		npcIds = { 512 }
	)

public class ChaosDruid extends CombatScript {

	private static final Projectile BIND = new Projectile(178, 20, 5, 0, 100, 0, 10);
	private static final Graphic BIND_END = new Graphic(179);

	@Override
	public int attack(NPC npc, Entity target) {
		
		int attackChance = Misc.random(1,10);
		
		if (attackChance <= 8) {
			handleHit(npc, target, CombatType.MELEE, null, null, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MELEE, Misc.random(2)), 0));
		} else {
			npc.gfx100(177);
			npc.startAnimation(710);
			handleHit(npc, target, CombatType.MAGE, BIND, BIND_END, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MAGE, 0), 2));
			target.asPlayer().freezeTimer = 5;
		}
		
		return 3;
	}

	public void handleDeath(NPC npc, Entity entity, Player player) {
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
