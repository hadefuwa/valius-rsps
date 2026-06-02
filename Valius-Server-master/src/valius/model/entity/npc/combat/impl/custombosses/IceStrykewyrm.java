package valius.model.entity.npc.combat.impl.custombosses;

import java.util.List;

import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.Location;
import valius.model.entity.Entity;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.combat.CombatScript;
import valius.model.entity.npc.combat.Graphic;
import valius.model.entity.npc.combat.Hit;
import valius.model.entity.npc.combat.Projectile;
import valius.model.entity.npc.combat.ScriptSettings;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Hitmark;
import valius.util.Misc;

/**
 * 
 * @author Divine
 * Jul. 8, 2019 1:08:50 p.m.
 */

@ScriptSettings(
		npcNames = { "Ice strykewyrm" },
		npcIds = { 3830, 3831 }
	)

public class IceStrykewyrm extends CombatScript {
	
	private static final int UNDER = 3831, MAIN = 3830;
	private static final int TRANSFORM_ANIM = 5073;

	@Override
	public int attack(NPC npc, Entity target) {
		
		/*
		 * wyrm transforming to attack
		 */
		if (npc.npcType == UNDER) {
			npc.requestTransform(MAIN);
			npc.startAnimation(TRANSFORM_ANIM);
			npc.npcType = MAIN;
			npc.setSize(3);
			return 5;
		}
		boolean meleeDistance = target.asPlayer().goodDistance(npc.getX(), npc.getY(), target.getX(), target.getY(), npc.getSize() + 1);
		
		/*
		 * If the player is standing under the Wyrm
		 */
		if(Boundary.isIn(target, npc.getBoundary())) {
			CycleEvent event = new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
						if (!Boundary.isIn(target, npc.getBoundary()) || target == null || target.asPlayer().isDead) {
							container.stop();
							return;
						} else {
							target.asPlayer().appendDamage(10, Hitmark.HIT);
						}	
				}
			};
			CycleEventHandler.getSingleton().addEvent(-1, npc, event, 3);
		}
		
		/*
		 * Freezing + healing for the Wyrm
		 */
		int freezeChance = Misc.random(1, 20);
		if (freezeChance == 5) {
			int freezeTime = Misc.random(5, 8);
			target.asPlayer().freezeTimer = freezeTime;
			npc.getHealth().increase(freezeTime);
		}
		
		int randomAttack = Misc.random(1, 20);
		if (meleeDistance && randomAttack > 15){//melee attack
			npc.startAnimation(5068);
			handleHit(npc, target, CombatType.MELEE, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MELEE, 30), 3));
		} else if (randomAttack >= 1 && randomAttack <= 15) {//magic attack 
			npc.startAnimation(5069);
			handleHit(npc, target, CombatType.MAGE, new Projectile(1347, 100, 30, 0, 100, 0, 50), new Graphic(1154), new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MAGE, 50), 5));
		} 
		return 6;
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
		return 0;
	}

	@Override
	public boolean followClose(NPC npc) {
		return false;
	}

}
