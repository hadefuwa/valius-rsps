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
import valius.util.Misc;

/**
 * @author Patrity
 *
 */
@ScriptSettings(
	npcNames = { },
	npcIds = { 7792, 7793, 7794, 7795 }
)
public class Wyverns extends CombatScript {

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#attack(ethos.model.entity.npc.NPC, ethos.model.entity.Entity)
	 */
	@Override
	public int attack(NPC npc, Entity target) {
		boolean melee = target.distanceToPoint(npc.getX(), npc.getY()) <= 2;
		int damage = getRandomMaxHit(npc, target, melee ? CombatType.MELEE : CombatType.RANGE, melee ? 10 : 16);
		if (npc.asNPC().npcType == 7795) //ancient
			damage = getRandomMaxHit(npc, target, melee ? CombatType.MELEE : CombatType.RANGE, melee ? 10 : 16);
		if (npc.asNPC().npcType == 7794) //spitting
			damage = getRandomMaxHit(npc, target, melee ? CombatType.MELEE : CombatType.RANGE, melee ? 9 : 13);
		if (npc.asNPC().npcType == 7793) //taloned
			damage = getRandomMaxHit(npc, target, melee ? CombatType.MELEE : CombatType.RANGE, melee ? 12 : 10);
		if (npc.asNPC().npcType == 7792) //long-tailed
			damage = getRandomMaxHit(npc, target,CombatType.MELEE, 13);
		npc.startAnimation(64);
		if(!melee && npc.asNPC().npcType != 7792) {
			npc.attackType = CombatType.RANGE;
			npc.startAnimation(7654);
			handleHit(npc, target, CombatType.RANGE, new Projectile(396, 0, 30, 0, 100, 0, 50), new Hit(Hitmark.HIT, damage, 4));
			
		} else {
			if (Misc.random(1, 5) == 1) {
				damage = getRandomMaxHit(npc, target, CombatType.DRAGON_FIRE, 44);
				npc.startAnimation(7653);
				if (npc.asNPC().npcType == 7795) {
					handleHit(npc, target, CombatType.DRAGON_FIRE, new Projectile(136, 85, 35, 0, 100, 0, 50), new Hit(Hitmark.HIT, damage, 1));
				} else {
					handleHit(npc, target, CombatType.DRAGON_FIRE, new Projectile(136, 45, 35, 0, 100, 0, 50), new Hit(Hitmark.HIT, damage, 1));
				}
			} else {
				npc.startAnimation(7651);
				handleHit(npc, target, CombatType.MELEE, new Hit(Hitmark.HIT, damage, 1));
			}
		}
		return 5;
	}

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#getAttackDistance(ethos.model.entity.npc.NPC)
	 */
	@Override
	public int getAttackDistance(NPC npc) {
		if (npc.asNPC().npcType == 7792)
			return 1;
		else
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
			npc.attackType = CombatType.RANGE;
		} else {
			npc.attackType = CombatType.MELEE;
		}
	}

}