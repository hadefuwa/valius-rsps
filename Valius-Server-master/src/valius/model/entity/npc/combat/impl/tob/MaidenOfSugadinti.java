package valius.model.entity.npc.combat.impl.tob;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;

import com.google.common.base.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import valius.clip.Region;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.Location;
import valius.model.entity.Entity;
import valius.model.entity.HealthStatus;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCDumbPathFinder;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.npc.combat.CombatScript;
import valius.model.entity.npc.combat.Graphic;
import valius.model.entity.npc.combat.Hit;
import valius.model.entity.npc.combat.Projectile;
import valius.model.entity.npc.combat.ScriptSettings;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Hitmark;
import valius.model.minigames.theatre.Theatre;
import valius.util.Misc;

/**
 * Handles Maiden of Sagadinti combat.
 * @author ReverendDread
 * 
 * TODO add blood spider animations (death anim)
 * TODO add blood spawn animations (death anim)
 * 
 * Apr 7, 2019
 */
@ScriptSettings(
	npcIds = { 8360, 8361, 8362, 8363, 8364, 8365 }
)
@Slf4j
public class MaidenOfSugadinti extends CombatScript {

	private static final int NORMAL_ATTACK = 8092;
	private static final int MULTI_ATTACK = 8091;
	private static final int BLOOD_SPIDER = 8366, BLOOD_SPAWN = 8367;
	private static final int SPIDER_BLOWUP = 8097;
	
	private static final Projectile BLOOD_PROJ = new Projectile(1578, 50, 25, 0, 100, 0, 50);
	private static final Projectile VORTEX_PROJ = new Projectile(1577, 50, 25, 0, 100, 0, 50);
	private static final int BLOOD_SPLASH = 1576;
	private static final int BLOOD_SPLAT = 1579;
	
	private Deque<NPC> spiders = new ArrayDeque<NPC>();
	
	private int phase = 0;
	
	private static final Location[] SPIDER_SPAWNS = {
		//North
		new Location(3173, 4457),
		new Location(3177, 4457),
		new Location(3181, 4457),
		new Location(3185, 4457),
		new Location(3186, 4455),
		//South
		new Location(3186, 4437),
		new Location(3185, 4435),
		new Location(3182, 4435),
		new Location(3178, 4435),
		new Location(3174, 4435)
	};
	
	@Override
	public void init(NPC npc) {
		npc.getHealth().resolveStatus(HealthStatus.POISON, Integer.MAX_VALUE);
		npc.getHealth().resolveStatus(HealthStatus.VENOM, Integer.MAX_VALUE);
		npc.getDefinition().setSize(6);
		npc.setNeverWalkHome(true);
		npc.attackType = CombatType.MAGE;
		npc.setNoRespawn(true);
	}
	
	@Override
	public int attack(NPC npc, Entity target) {
		npc.startAnimation(NORMAL_ATTACK);
		log.info("Doing attack {}", target);
		if (Misc.random(10) == 0) {
			bloodSpawns(npc, target);
		} else {
			handleHit(npc, target, CombatType.MAGE, VORTEX_PROJ, null, 
					new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MAGE, 36), 3));
		}
		return 10;
	}
	
	@Override
	public void process(NPC npc, Entity target) {
		super.process(npc, target);
		
		Player closest =  getClosestPlayer(npc);
        if(closest == null)
            return;
        npc.killerId = closest.getIndex(); //Sets npcs target to closest player to it, should never be null.
        
		if (target == null)
			return;
		npc.killerId = getClosestPlayer(npc).getIndex(); //Sets npcs target to closest player to it, should never be null.
		double percent = npc.getHealth().getPercentage();
		if (percent <= 0.7D && phase == 0 || percent <= 0.5D && phase == 1 || percent <= 0.3D && phase == 2) {
			nylocas(npc, target);
			phase++;
		}
	}
	
	@Override
	public void handleDeath(NPC npc, Entity source) {
		List<Entity> players = getPossibleTargets(npc, true);
		players.forEach(player -> {
			//player.asPlayer().killedMaiden = true;
			player.asPlayer().sendMessage("You have defeated the Maiden of Sugadinti.");			
			Theatre instance = player.asPlayer().getTheatreInstance();
			if (instance != null) {
				player.asPlayer().sendMessage("Current time: "+instance.getTimeElapsed()+".");
			}
			player.asPlayer().sendMessage("You now have "+player.asPlayer().theatrePoints+" points!");
		});
	}
	
	@Override
	public int getAttackDistance(NPC npc) {
		return 24;
	}

	@Override
	public boolean ignoreProjectileClipping() {
		return false;
	}
	
	@Override
	public int getFollowDistance(NPC npc) {
		return 0;
	}
	
	@Override
	public boolean followClose(NPC npc) {
		return false;
	}
	
	@Override
	public boolean isAggressive(NPC npc) {
		return true;
	}
	
	/**
	 * Handles spawning random blood spawns.
	 * @param npc
	 * @param target
	 */
	private final void bloodSpawns(final NPC npc, final Entity target) {
		final Location loc = target.getLocation();
		CycleEvent event = new CycleEvent() {

			CopyOnWriteArraySet<BloodSplat> splats = new CopyOnWriteArraySet<BloodSplat>();
			NPC spawn;
			
			@Override
			public void execute(CycleEventContainer container) {
				if (container.getTotalTicks() == 2) {
					container.setTick(2);
					if (spawn == null) {
						spawn = NPCHandler.spawnNpc(BLOOD_SPAWN, loc.getX(), loc.getY(), loc.getZ(), 1, 120, 0, 1, 1);
						spawn.setNeverWalkHome(true);
						spawn.setNoRespawn(true);
						target.asPlayer().getPA().createPlayersStillGfx(BLOOD_SPLASH, loc.getX(), loc.getY(), 0, 0);
					}
					if (spawn.isDead || npc.isDead) {
						spawn.actionTimer = 0;
						spawn.isDead = true;
						spawn.applyDead = true;
						container.stop();
						return;
					}
					boolean exists = splats.stream().anyMatch((splat) -> splat.location.equals(spawn.getLocation()));
					if (target.isPlayer() && !exists) {
						target.asPlayer().getPA().createPlayersStillGfx(BLOOD_SPLAT, spawn.getX(), spawn.getY(), 0, 0);
						splats.add(new BloodSplat(spawn.getLocation()));
					}
					for (BloodSplat splat : splats) {
						if (splat.age <= 8 && splat.getLocation().equals((target.getLocation()))) {
							target.appendDamage(Misc.random(5, 15), Hitmark.HIT);
						} else {
							splats.remove(splat);
						}
						splat.age++;
					}
				}
			}
			
		};
		handleDodgableAttack(npc, target, CombatType.SPECIAL, BLOOD_PROJ, new Graphic(1576), new Hit(Hitmark.HIT, Misc.random(5, 25), 3), event);
	}
	
	/**
	 * Handles spawning Nylocas Matomenos to heal the boss.
	 * @param npc
	 */
	private final void nylocas(final NPC npc, final Entity target) {
		CycleEvent event = new CycleEvent() {

			int cycles = 0;
			
			@Override
			public void execute(CycleEventContainer container) {
				if (npc == null) {
					container.stop();
					return;
				}				
				if (cycles < SPIDER_SPAWNS.length) {
					Location location = SPIDER_SPAWNS[cycles];
					NPC spider = NPCHandler.spawnNpc(BLOOD_SPIDER, location.getX(), location.getY(), npc.getHeight(), 0, 200, 11, 100, 100);
					spider.faceEntity(npc);
					spiders.add(spider);
				} else if (cycles >= SPIDER_SPAWNS.length) {
					NPC spider = spiders.poll();
					if (npc.isDead) { //maiden is dead
						spiders.forEach(nigger -> {
							nigger.actionTimer = 0;
							nigger.isDead = true;
							nigger.applyDead = true;
						});	
						container.stop();
					} else if (spider != null && !spider.isDead && spider.killerId == 0 && spider.freezeTimer == 0) {
						sendProjectileToTile(spider, target, npc.getLocation().center(npc.getSize()), BLOOD_PROJ);
						npc.appendDamage(Misc.random(10, 15), Hitmark.HEAL_PURPLE);
						spiders.add(spider);
					}
				}
				cycles++;
			}
			
		};
		CycleEventHandler.getSingleton().addEvent(-1, npc, event, 1);
	}
	
	/**
	 * Gets the closest player to the npc.
	 * @param npc
	 * @return
	 */
	private final Player getClosestPlayer(NPC npc) {
		Player target = null;
		for (Player player : PlayerHandler.players) {
			if (player == null || player.isDead() || player.isInvisible() || !player.isWithinDistance(npc.getLocation(), 32))
				continue;
			if (target == null || (target.getLocation().getDistance(npc.getLocation()) > player.getLocation().getDistance(npc.getLocation()))) {
				target = player;
			}
		}
		return target;
	}
	
	@RequiredArgsConstructor @Getter
	protected class BloodSplat {
		
		private final Location location;
		private int age;
		
	}
	
}
