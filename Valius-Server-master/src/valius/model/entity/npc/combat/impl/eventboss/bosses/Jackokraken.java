package valius.model.entity.npc.combat.impl.eventboss.bosses;

import java.util.List;

import com.google.common.collect.Lists;

import valius.content.EventMysteryBox;
import valius.content.HalloweenMysteryBox;
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
		npcNames = { "Jack-o-Kraken" },
		npcIds = { 3845 }
	)

public class Jackokraken extends CombatScript {

	/*
	 * The halloween boss event
	 */
	@Override
	public int attack(NPC npc, Entity target) {
		
		//List of players within the boundaries
        List<Entity> targets = getPossibleTargets(npc, true);
		
			//Main magic attack data
		Projectile MAGIC_PROJECTILE1 = new Projectile(3004, 50, 25, 0, 100, 0, 50);
		Hit MAGIC_HIT1 = new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MAGE, 10), 5);
		
		int attackChance = Misc.random(1, 20);
		boolean meleeDistance = target.asPlayer().goodDistance(npc.getX(), npc.getY(), target.getX(), target.getY(), npc.getSize() + 1);
		
		if (attackChance <= 5 && meleeDistance) {
			npc.startAnimation(3991);
			handleHit(npc, target, CombatType.MELEE, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MELEE, 8), 3));
			return 5;
		}
		
		else if (attackChance > 5 && attackChance <= 19) {//main magic attack
			if (targets != null) {
				npc.startAnimation(3991);
			this.handleHit(npc, target, CombatType.MAGE, MAGIC_PROJECTILE1, null, MAGIC_HIT1);
			}
		}
		
		else if (attackChance > 19) {//troll attack
			npc.startAnimation(3991);
			CycleEvent event = new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					
					if (container.getTotalTicks() <= 1) {
						targets.forEach(plr -> {
							plr.asPlayer().forcedChat("Screech!");
						});
					}
					if(container.getTotalTicks() >= 15) {
						target.asPlayer().npcId2 = -1;
						target.asPlayer().isNpc = false;
						target.asPlayer().updateRequired = true;
						target.asPlayer().morphed = false;
						target.asPlayer().setAppearanceUpdateRequired(true);
						container.stop();
					} else if (container.getTotalTicks() == 1 && target.getLocation().withinBoundary(Boundary.JACK_O_KRAKEN)) {
						target.asPlayer().npcId2 = 3847;
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
		
		List<String> givenToIP = Lists.newArrayList();
		PlayerHandler.nonNullStream()
		.filter(p -> p.JackokrakenDamage > 0)
		.forEach(p -> { 
			if(!givenToIP.contains(p.connectedFrom)) {
				if (p.JackokrakenDamage >= 100) {
					p.sendMessage("you receive a Halloween mystery box!");
					p.getItems().addItemUnderAnyCircumstance(HalloweenMysteryBox.MYSTERY_BOX, 1);
					p.JackokrakenDamage = 0;
				}
			}
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