package valius.model.entity.npc.combat.impl.eventboss.bosses;

import java.util.List;

import com.google.common.collect.Lists;

import valius.model.entity.Entity;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.combat.CombatScript;
import valius.model.entity.npc.combat.Hit;
import valius.model.entity.npc.combat.Projectile;
import valius.model.entity.npc.combat.ScriptSettings;
import valius.model.entity.npc.combat.impl.eventboss.drop.ColossalChickenDrops;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Hitmark;
/**
 * 
 * @author Divine
 * Apr. 16, 2019 5:03:36 a.m.
 */

/*
 * Handles combat for the Event boss: Colossal Chicken (Easter!)
 */

@ScriptSettings(
		npcNames = { "Colossal Chicken" },
		npcIds = { }
)
public class ColossalChicken extends CombatScript {
	
	@Override
	public int attack(NPC npc, Entity target) {
		npc.startAnimation(8324);
		PlayerHandler.getPlayers().forEach((player) -> {
			if (Boundary.isIn(player, Boundary.EVENT_AREAS)) {
				handleHit(npc, target, CombatType.MAGE, new Projectile(1528, 30, 30, 15, 40, 0, 16), null,
						new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.RANGE, 15), 4));
				npc.forceChat("BWAK BWAK!");
			}
		});
		return 4;
	}

	public void handleDeath(NPC npc, Entity target) {
		List<String> givenToIP = Lists.newArrayList();
		PlayerHandler.getPlayers().forEach(player -> {
			if (!givenToIP.contains(player.connectedFrom)) {
				if (Boundary.isIn(player, Boundary.EVENT_AREAS)) {
					ColossalChickenDrops.execute(player);
					givenToIP.add(player.connectedFrom);
				}
			}
		});
	}
	
	@Override
	public int getAttackDistance(NPC npc) {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public boolean ignoreProjectileClipping() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public int getFollowDistance(NPC npc) {
		return 8;
	}
	
	@Override
	public boolean isAggressive(NPC npc) {
		return true;
		
	}

}
