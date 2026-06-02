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
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Hitmark;
import valius.util.Misc;

/**
 * 
 * @author Divine
 * Apr. 23, 2019 5:04:39 a.m.
 */

/*
 * Void Knight Champion Combat handled here
 */

@ScriptSettings(
		npcNames = { "Void Knight Champion" },
		npcIds = { 6014 }
	)

public class VoidKnightChampion extends CombatScript {
//death = 2241
	
	private Projectile NORMAL_SPELL_PROJECTILE = new Projectile(1607, 50, 25, 0, 100, 0, 50);
	//private Projectile FREEZE_PROJECTILE = new Projectile(500, 50, 20, 5, 50, 0, 50);
	private Projectile VOLCANO_PROJECTILE = new Projectile(1604, 50, 25, 0, 100, 0, 50);
	private int VOLCANO_GROUND_GFX = 1420;
	private Graphic MELEE_GFX =  new Graphic(1605);
	//private static final Boundary CHAMPION = new Boundary(1560, 3930, 1660, 4030);
	
	@Override
	public int attack(NPC npc, Entity target) {
		int attack = Misc.random(1, 12);
		boolean melee_range = target.withinDistance(npc, 3);
		
		if (!melee_range) {
			attack = Misc.random(11, 12);
		}

		if (attack <= 8 && melee_range) {
			npc.startAnimation(2068);
			handleHit(npc, target, CombatType.MELEE, null, MELEE_GFX, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MELEE, 30), 3));
		} else if (attack > 8 && attack <= 10 || attack == 11 && !melee_range) {
				npc.startAnimation(811);
				handleHit(npc, target, CombatType.MAGE, NORMAL_SPELL_PROJECTILE, new Graphic(1608), new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MAGE, 25), 5));
		} if (attack == 12 && !melee_range) {
				npc.startAnimation(811);
				CycleEvent event = new CycleEvent() {

					@Override
					public void execute(CycleEventContainer container) {
						List<Entity> targets = getPossibleTargets(npc, true);
						Location target_location = target.getLocation();
						
						for (Entity target : targets) {
							if (target == null || target.asPlayer().isDead || !target.withinDistance(npc, 20)) {
								continue;
							}
							
							if (container.getTotalTicks() == 1) {
								handleDodgableAttack(npc, target, CombatType.SPECIAL, VOLCANO_PROJECTILE, null, new Hit(Hitmark.HIT, Misc.random(50, 60), 6, true));
								target.asPlayer().getPA().createPlayersStillGfx(VOLCANO_GROUND_GFX, target_location.getX(), target_location.getY(), 1, 2);
								container.stop();
						} 
					}
				}
			};
				CycleEventHandler.getSingleton().addEvent(-1, npc, event, 1);
			}
		
		return 5;
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
		return 10;
	}

	@Override
	public boolean followClose(NPC npc) {
		return false;
	}

}
