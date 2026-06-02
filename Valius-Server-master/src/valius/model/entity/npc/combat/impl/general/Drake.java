package valius.model.entity.npc.combat.impl.general;

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
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Hitmark;
import valius.util.Misc;

/**
 * 
 * @author Divine
 * Apr. 12, 2019 5:25:28 a.m.
 */

@ScriptSettings(
		npcNames = { "Drake" },
		npcIds = { }
	)
public class Drake extends CombatScript {
	
	private static final int RANGED_ATTACK = 8276, SPECIAL_ATTACK = 8275;
	private static final Projectile DRAGONFIRE = new Projectile(1637, 30, 0, 0, 150, 1, 50);
	private static final Projectile RANGED_PROJ = new Projectile(1636, 30, 30, 0, 100, 1, 50);
	
	public int attackCount = 0;
	
	@Override
	public void init(NPC npc) {
		npc.requestTransform(8612);
	}
	
	@Override
	public int attack(NPC npc, Entity target) {	
		if (attackCount < 8) {
			npc.startAnimation(RANGED_ATTACK);
			handleHit(npc, target, CombatType.RANGE, RANGED_PROJ, null, new Hit(Hitmark.HIT,
					getRandomMaxHit(npc, target, CombatType.RANGE, 15), 2));
		} else {
			attackCount = 0;
			npc.startAnimation(SPECIAL_ATTACK);
			sendProjectileToTile(npc, target, DRAGONFIRE);
			final Location tile = target.getLocation();
			CycleEvent event = new CycleEvent() {

				boolean hit;
				int tick = 0;
				
				@Override
				public void execute(CycleEventContainer container) {
					if (tick == 0) {
						if (target.getLocation().equals(tile) && target.isPlayer()) {
							hit = true;
						}
						target.asPlayer().getPA().stillGfx(1638, tile.getX(), tile.getY(), 50, 0);
					}
					if (hit && tick <= 3) {
						int damage = getRandomMaxHit(npc, target, CombatType.DRAGON_FIRE, 15);
						boolean prayer = target.asPlayer().protectingMagic();
						boolean anti = target.asPlayer().isDragonfireShieldActive() || target.asPlayer().lastAntifirePotion > 0;
						int corrected = prayer && anti ? 0 : prayer || anti ? damage / 2 : damage;
						target.appendDamage(corrected, Hitmark.HIT);
					} else {
						container.stop();
						return;
					}
					container.setTick(1);
					tick++;
				}
				
			};
			CycleEventHandler.getSingleton().addEvent(-1, npc, event, 6);
			return 6;
		}
		attackCount++;
		return 5;
	}

	@Override
	public void handleDeath(NPC npc, Entity entity) {
		npc.requestTransform(8613);
	}
	
	@Override
	public int getAttackDistance(NPC npc) {
		return 8;
	}


	@Override
	public boolean ignoreProjectileClipping() {
		return true;
	}

}
