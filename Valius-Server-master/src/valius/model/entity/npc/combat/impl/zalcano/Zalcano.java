/**
 * 
 */
package valius.model.entity.npc.combat.impl.zalcano;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import valius.event.CycleEventHandler;
import valius.model.Location;
import valius.model.entity.Entity;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.combat.CombatScript;
import valius.model.entity.npc.combat.ScriptSettings;
import valius.model.entity.player.combat.Hitmark;
import valius.util.Misc;
import valius.world.World;
import valius.world.objects.GlobalObject;

/**
 * @author ReverendDread
 * Sep 19, 2019
 */
@ScriptSettings(npcIds = { 9049, 9050 })
public class Zalcano extends CombatScript {

	private static final int GLOWING = 36192, NORMAL = 36193, DEPLETED = 36194;
	private static final int ORANGE_SYMBOL = 36199, BLUE_SYMBOL = 36200; //27 tick duration
	private static final int ATTACKABLE = 9049, MINABLE = 9050;
	private static final int ATTACK_SPEED = 5;
	
	/** Rock formation locations */
	private static final Location[] FORMATIONS = {
		new Location(3040, 6057),
		new Location(3040, 6040),
		new Location(3025, 6057),
		new Location(3025, 6040),
	};
	
	/** Symbol spawn locations */
	private static final Location[] SYMBOL_SPOTS = {		
		new Location(3035, 6055),
		new Location(3035, 6050),
		new Location(3035, 6045),
		new Location(3035, 6040),	
		
		new Location(3030, 6055),
		new Location(3030, 6050),
		new Location(3030, 6045),
		new Location(3030, 6040),
		
		new Location(3038, 6053),
		new Location(3027, 6043),	
		new Location(3027, 6053),
		new Location(3038, 6043),	
	};
	
	/** List of symbols that are spawned */
	private List<GlobalObject> symbols = Lists.newArrayList(); 
	
	/** Current attack tick */
	private int tick;
	/** Symbol attack cooldown */
	private int symbolCooldown;
	
	@Override
	public void init(NPC npc) {
		setCanAttack(false);
	}
	
	@Override
	public int attack(NPC npc, Entity target) {
		return 0;
	}
	
	@Override
	public void process(NPC npc, Entity target) {
		super.process(npc, target);
		if (!getPossibleTargets(npc, true).isEmpty()) {
			if (tick > 0 && (tick % ATTACK_SPEED == 1)) {
				if (symbolCooldown <= 0) {
					spawn_symbols(npc);
					symbolCooldown = 50;
				}
			}
		}
		symbolCooldown--;
		tick++;
	}

	@Override
	public int getAttackDistance(NPC npc) {
		return 16;
	}
	
	@Override
	public boolean isAggressive(NPC npc) {
		return false;
	}
	
	private void spawn_symbols(NPC npc) {
		npc.startAnimation(8433);
		Arrays.asList(SYMBOL_SPOTS).stream().forEach(loc -> {
			GlobalObject obj = new GlobalObject(Misc.random(3) == 1 ? BLUE_SYMBOL : ORANGE_SYMBOL, loc.getX(), loc.getY(), 0, 0, 10, 27, -1);
			World.getWorld().getGlobalObjects().add(obj);
			symbols.add(obj);
		});
		CycleEventHandler.getSingleton().addEvent(npc, (container) -> {
			List<Entity> players = getPossibleTargets(npc, true);
			if (container.getTotalTicks() < 27 && !players.isEmpty()) {
				symbols.stream().filter(GlobalObject::exists).forEach(object -> {
					players.stream().forEach(player -> {
						if (object.getObjectId() == ORANGE_SYMBOL && object.collides(player.getLocation())) {
							player.appendDamage(3, Hitmark.HIT);
							player.asPlayer().setRunEnergy(player.asPlayer().getRunEnergy() - 3);					
						}
					});
				});
			}
		}, 1);
	}

}
