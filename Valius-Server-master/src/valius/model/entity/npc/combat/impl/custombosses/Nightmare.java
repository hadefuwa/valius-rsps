package valius.model.entity.npc.combat.impl.custombosses;


import java.util.List;

import com.google.common.collect.Lists;

import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.Entity;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.combat.CombatScript;
import valius.model.entity.npc.combat.Graphic;
import valius.model.entity.npc.combat.Hit;
import valius.model.entity.npc.combat.Projectile;
import valius.model.entity.npc.combat.ScriptSettings;
import valius.model.entity.npc.combat.impl.custombosses.drops.NightmareDrops;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Hitmark;
import valius.util.Misc;

/**
 * 
 * @author Divine
 * Jul. 27, 2019 1:46:56 a.m.
 */



/*
 * Nightmare boss Combat Script
*/

@ScriptSettings(
		npcNames = { "Nightmare" },
		npcIds = { 3833 }
	)

public class Nightmare extends CombatScript {
	
	private Projectile MAGE_PROJECTILE = new Projectile(986, 50, 15, 0, 110, 0, 10);
	private Projectile VORTEX_PROJECTILE = new Projectile(1242, 30, 30, 0, 130, 0, 50);
	//private Projectile CORRUPT_WALL = new Projectile(1347, 100, 30, 0, 100, 0, 50);
	private Graphic VORTEX = new Graphic(1243);
	
	//Original coordinates
	int MAIN_X = 1869, MAIN_Y = 4952;
    
	@Override
	public int attack(NPC npc, Entity target) {
		
		npc.setSize(3);
		
		//List of players within the boundaries
        List<Entity> targets = getPossibleTargets(npc, true);
        
        //This will allow players to know when phase 2 has begun.
        boolean PHASE2 = npc.getHealth().getAmount() <= 10000;
        
        //Vortex that hits up to 3 players 3 different times, selecting different players each time
        //TODO CycleEvent for dodgeable attack (timed gfx)
		if (PHASE2 && Misc.random(1, 2) == 2) {
        	npc.forceChat("RRAAARRGGHHHH!!!");
			CycleEvent event = new CycleEvent() {

				int hitTimes = 0;

				@Override
				public void execute(CycleEventContainer container) {
					Misc.shuffle(targets)
					.stream()
					.limit(3)
					.forEach(plr -> {
						if (hitTimes == 1 || hitTimes == 2 || hitTimes == 3) {
							handleDodgableAttack(npc, plr, CombatType.SPECIAL, VORTEX_PROJECTILE, VORTEX, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.SPECIAL, Misc.random(5, 15)), 4));
						}
					});
					if (hitTimes >= 4) {
						container.stop();
					}
					container.setTick(1);
					hitTimes++;
				}
			};
			CycleEventHandler.getSingleton().addEvent(-1, npc, event, 1);
			return 2;
		}
        
		int ATTACK_TYPE = Misc.random(1, 10);
        
        //Magic attack that hits 5 players maximum within the boundaries(all if there is not 5)
		if (ATTACK_TYPE <= 9) {
			npc.startAnimation(7193);
			Misc.shuffle(targets)
			.stream()
			.limit(5)
			.forEach(plr -> {
				if (plr.asPlayer().protectingMagic()) {
					handleHit(npc, plr, CombatType.MAGE, MAGE_PROJECTILE, new Graphic(985), new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MAGE, 30), 4));
				} else {
				handleHit(npc, plr, CombatType.MAGE, MAGE_PROJECTILE, new Graphic(985), new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MAGE, 60), 4));
				}
			});
		} 
		
		//Jump attack that targets a random player within the boundaries and hits all players under the npc when teleported
		else if (ATTACK_TYPE > 9) {
			CycleEvent event = new CycleEvent() {
				int duriation = 0;
				
				@Override
				public void execute(CycleEventContainer container) {
					
					if (target.isPlayer() && target.asPlayer().isDead()) {
						container.stop();
						return;
					}
					
					if (duriation == 0) {
						npc.startAnimation(7152);
					}
					else if (duriation == 3) {
						npc.startAnimation(6946);
						npc.teleport(target.getX(), target.getY(), target.getHeight());
							Misc.shuffle(targets)
							.stream()
							.forEach(plr -> {
								if (Boundary.isIn(plr, npc.getBoundary())) {
							plr.appendDamage(Misc.random(25, 60), Hitmark.HIT);
								}
						});
						} else if (duriation == 6) {
							npc.startAnimation(7152);
						} else if (duriation == 8) {
							npc.teleport(MAIN_X, MAIN_Y, 2);
							npc.startAnimation(6946);
						} 
						if (duriation >= 8) {
							container.stop();
						}
					container.setTick(1);
					duriation++;
				}
			};
			CycleEventHandler.getSingleton().addEvent(-1, npc, event, 8);
		}
		return 6;
	}

	@Override
	public void handleDeath(NPC npc, Entity entity) {
		npc.startAnimation(7196);
		List<String> givenToIP = Lists.newArrayList();
		PlayerHandler.nonNullStream().filter(p -> p.NightmareDamage > 0).forEach(p -> { 
			if(!givenToIP.contains(p.connectedFrom)) {
				if (p.NightmareDamage >= 200) {
					p.getItems().addItemUnderAnyCircumstance(NightmareDrops.NIGHTMARE_KEY, 1);
					p.sendMessage("You receive a Nightmare key!");
					givenToIP.add(p.connectedFrom);
				} else if (p.NightmareDamage < 200) {
					p.sendMessage("You must deal @red@200+</col> damage to receive a key!");
				}
			} else {
				p.sendMessage("You can only receive 1 key per IP");
			}
			p.NightmareDamage = 0;
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
		return 1;
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
		return true;
	}

}
