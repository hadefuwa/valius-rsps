package valius.model.entity.npc.combat.impl.eventboss.bosses;

import java.util.List;

import com.google.common.collect.Lists;

import valius.content.EventMysteryBox;
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
import valius.model.entity.player.Boundary;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Hitmark;
import valius.util.Misc;

/**
 * 
 * @author Divine | 4:31:34 a.m. | Sep. 9, 2019
 *
 */

@ScriptSettings(
		npcNames = { "Yearly Mimic" },
		npcIds = { 3842 }
	)

public class ValiusMimic extends CombatScript {

	/*
	 * The boss created for the 1 year of Valius being online
	 */
	@Override
	public int attack(NPC npc, Entity target) {
		
		//List of players within the boundaries
        List<Entity> targets = getPossibleTargets(npc, true);
		
			//Main magic attack data
		Projectile MAGIC_PROJECTILE1 = new Projectile(1437, 50, 25, 0, 100, 0, 50);
		Graphic MAGIC_GRAPHIC1 = new Graphic(1440);
		Hit MAGIC_HIT1 = new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MAGE, 10), 4);
		
		int attackChance = Misc.random(1, 20);
		boolean meleeDistance = target.asPlayer().goodDistance(npc.getX(), npc.getY(), target.getX(), target.getY(), npc.getSize() + 1);
		
		if (attackChance <= 5 && meleeDistance) {
			npc.startAnimation(8308);
			handleHit(npc, target, CombatType.MELEE, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MELEE, 10), 3));
			return 5;
		}
		
		else if (attackChance > 5 && attackChance <= 18) {//main magic attack
			if (targets != null) {
				npc.startAnimation(8309);
			this.handleHit(npc, target, CombatType.MAGE, MAGIC_PROJECTILE1, MAGIC_GRAPHIC1, MAGIC_HIT1);
			}
		}
		
		else if (attackChance > 18) {//troll attack
			npc.startAnimation(8309);
			CycleEvent event = new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					
					if (container.getTotalTicks() <= 1) {
						targets.forEach(plr -> {
							plr.asPlayer().forcedChat("Eek eek!");
						});
					}
					if(container.getTotalTicks() >= 10) {
						target.asPlayer().npcId2 = -1;
						target.asPlayer().isNpc = false;
						target.asPlayer().updateRequired = true;
						target.asPlayer().morphed = false;
						target.asPlayer().setAppearanceUpdateRequired(true);
						container.stop();
					} else if (container.getTotalTicks() == 1 && target.getLocation().withinBoundary(Boundary.YEARLY_MIMIC)) {
						target.asPlayer().npcId2 = 1817;
						target.asPlayer().isNpc = true;
						target.asPlayer().updateRequired = true;
						target.asPlayer().morphed = false;
						target.asPlayer().setAppearanceUpdateRequired(true);
				}
			}
		};
			CycleEventHandler.getSingleton().addEvent(-1, npc, event, 1);
		}
		
		return 5;
	}

	@Override
	public void handleDeath(NPC npc, Entity entity) {
		
		npc.startAnimation(8310);
		List<String> givenToIP = Lists.newArrayList();
		PlayerHandler.nonNullStream().filter(p -> p.MimicDamage > 0).forEach(p -> { 
			if(!givenToIP.contains(p.connectedFrom)) {
				if (p.MimicDamage >= 200) {
					p.sendMessage("you receive an Event mystery box!");
					p.getItems().addItemUnderAnyCircumstance(EventMysteryBox.MYSTERY_BOX, 1);
					p.MimicDamage = 0;
					givenToIP.add(p.connectedFrom);
				}
			} else {
				p.sendMessage("You can only receive 1 key per IP");
			}
			p.MimicDamage = 0;
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
		return 0;
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
