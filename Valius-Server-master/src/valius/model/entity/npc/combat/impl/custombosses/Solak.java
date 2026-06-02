package valius.model.entity.npc.combat.impl.custombosses;

import java.util.List;

import com.google.common.collect.Lists;

import valius.model.entity.Entity;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.combat.CombatScript;
import valius.model.entity.npc.combat.Graphic;
import valius.model.entity.npc.combat.Hit;
import valius.model.entity.npc.combat.Projectile;
import valius.model.entity.npc.combat.ScriptSettings;
import valius.model.entity.npc.combat.impl.custombosses.drops.NightmareDrops;
import valius.model.entity.npc.combat.impl.custombosses.drops.SolakDrops;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Hitmark;
import valius.util.Misc;

/**
 * 
 * @author Divine | 3:30:12 a.m. | Nov. 14, 2019
 *
 */


@ScriptSettings( npcIds = { 3407 } )

public class Solak extends CombatScript {
	
	//Gfx TODO
	private Projectile MAGE_PROJECTILE = new Projectile(1304, 50, 15, 0, 130, 0, 10);
	private Projectile ROCK_PROJECTILE = new Projectile(1302, 30, 30, 0, 110, 0, 50);
	private Projectile VINE_PROJECTILE = new Projectile(970, 10, 15, 0, 130, 0, 50);
	private Graphic MAGE_END = new Graphic(1305);
	private Graphic ROCK_END = new Graphic(1303);
	private Graphic VINE_END = new Graphic(8);
	
	@Override
	public int attack(NPC npc, Entity target) {
		npc.getStats().getAttack();
		npc.getStats().getStrength();
		npc.getStats().getStabAttack();
		npc.getStats().getSlashAttack();
		npc.getStats().getDefence();
		npc.getStats().getRanged();
		npc.getStats().getMagic();
		npc.getStats().getCrushAttack();
		npc.getStats().getRangedAttack();
		npc.getStats().getRangeDefence();
		npc.getStats().getRangedStrength();
		npc.getStats().getCrushDefence();
		npc.getStats().getMagicDefence();
		npc.getStats().getSlashDefence();
		npc.getStats().getStabDefence();
		npc.getStats().getMagicStrength();
		npc.getStats().getRangedStrength();
		npc.getStats().getMagicAttack();
		
		
        
      //This will allow players to know when phase 2 has begun.
        boolean PHASE2 = npc.getHealth().getAmount() <= 5000;
        

        int phaseChance = Misc.random(1, 10);
        int randomAttack = Misc.random(10);
        boolean meleeDistance = target.asPlayer().goodDistance(npc.getX(), npc.getY(), target.getX(), target.getY(), npc.getSize());
        
        //Melee attack/dodgeable range attack
		if (PHASE2 && phaseChance <= 2 || !PHASE2 && phaseChance == 5) {
			npc.startAnimation(5895);
			
			if (meleeDistance) {//dmg taken if too close to solak
				handleHit(npc, target, CombatType.MELEE, null, null, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MELEE, 60), 3));
			}
			//dodgeable vine attack
			handleDodgableAttack(npc, target, CombatType.RANGE, VINE_PROJECTILE, VINE_END, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.RANGE, Misc.random(40, 60)), 4));
			return 8;
		}
		
		//Magic attack
				if (randomAttack <= 6) {
					npc.startAnimation(5894);
					if (Boundary.isIn(target, Boundary.SOLAK_AREA)) {
						handleHit(npc, target, CombatType.MAGE, MAGE_PROJECTILE, MAGE_END, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MAGE, 48), 4));	
					}
				} else {//Range attack
					npc.startAnimation(5896);
					handleHit(npc, target, CombatType.RANGE, ROCK_PROJECTILE, ROCK_END, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.RANGE, Misc.random(5, 15)), 3));
				}
				return 8; 
	}
	
	@Override
	public void handleDeath(NPC npc, Entity entity) {
		List<String> givenToIP = Lists.newArrayList();
		PlayerHandler.nonNullStream().filter(p -> p.SolakDamage > 0).forEach(p -> { 
			if(!givenToIP.contains(p.connectedFrom)) {
				if (p.SolakDamage >= 10) {
					SolakDrops.execute(p);
					givenToIP.add(p.connectedFrom);
				} else if (p.SolakDamage < 10) {
					p.sendMessage("You must deal @red@10+</col> damage to receive a drop!");
				}
			} else {
				p.sendMessage("You can only receive 1 drop per IP");
			}
			p.SolakDamage = 0;
		});
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
		return true;
	}

	@Override
	public boolean followClose(NPC npc) {
		return false;
	}

}
