/**
 * 
 */
package valius.model.entity.npc.combat.impl.wilderness;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import valius.model.entity.Entity;
import valius.model.entity.HealthStatus;
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
 * handjob 4652
 * ubergay attack 7173
 * stomp 7183
 * magic 7550
 * 
 * @author ReverendDread
 * Oct 20, 2019
 */
@ScriptSettings(npcIds = { 3383 })
public class ShadowLord extends CombatScript {

	private static final int MELEE = 4652, SUPER = 7173, STOMP = 7183, MAGIC = 7550;
	
	private static final Projectile MAGIC_PROJ = new Projectile(3010, 60, 30, 0, 110, 0, 40);
	private static final Graphic MAGIC_GFX = new Graphic(3009, 30);
	
	private static final Projectile SPECIAL_PROJ = new Projectile(3011, 60, 0, 0, 130, 0, 40);
	private static final Graphic SPECIAL_GFX = new Graphic(3012);
	
	@Override
	public void init(NPC npc) {
		npc.getHealth().isNotSusceptibleTo(HealthStatus.VENOM);
		npc.getHealth().isNotSusceptibleTo(HealthStatus.POISON);
	}
	
	@Override
	public int attack(NPC npc, Entity target) {	
		int random = Misc.random(100);
		List<Entity> targets = getPossibleTargets(npc, true);
		boolean melee = targets.stream().anyMatch(player -> player.withinDistanceOfCenter(npc, 1));
		if (melee && (random >= 0 && random <= 89)) {
			int meleeChance = Misc.random(2);
			if (meleeChance == 1) {
				List<Entity> adjacentPlayers = targets.stream().filter(player -> player.withinDistanceOfCenter(npc, 1)).collect(Collectors.toList());
				npc.startAnimation(STOMP);
				npc.gfx100(SPECIAL_GFX.getId(), SPECIAL_GFX.getHeight());
				adjacentPlayers.forEach(player -> {
					player.asPlayer().lastSpear = System.currentTimeMillis();
					player.asPlayer().getPA().getSpeared(npc.getX(), npc.getY(), 3);
					handleHit(npc, player, CombatType.MELEE, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MELEE, 70), 2));
				});
				return 7;
			} else {
				npc.startAnimation(MAGIC);
				targets.stream().forEach(player -> {
					handleHit(npc, player, CombatType.MAGE, MAGIC_PROJ, MAGIC_GFX, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MAGE, 30), 4));
				});	
			}
			return 5;
		} else if (random >= 0 && random <= 50) {
			npc.startAnimation(MAGIC);
			targets.stream().forEach(player -> {
				handleHit(npc, player, CombatType.MAGE, MAGIC_PROJ, MAGIC_GFX, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MAGE, 30), 4));
			});	
			return 5;
		} else if (random >= 51 && random <= 89) {
			npc.startAnimation(MELEE);
			targets.stream().forEach(player -> {
				handleHit(npc, player, CombatType.MELEE, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MAGE, 30), 2));
			});	
		} else if (random >= 90) {
			npc.startAnimation(SUPER);
			targets.stream().forEach(player -> {
				handleDodgableAttack(npc, player, CombatType.SPECIAL, SPECIAL_PROJ, SPECIAL_GFX, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.SPECIAL, 90), 5));			
			});
			return 7;
		}
		return 5;
	}

	@Override
	public int getAttackDistance(NPC npc) {
		return 16;
	}
	
}
