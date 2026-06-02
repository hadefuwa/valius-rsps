package valius.model.entity.npc.combat.impl.tob;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import valius.clip.PathChecker;
import valius.clip.Region;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.Location;
import valius.model.entity.Entity;
import valius.model.entity.HealthStatus;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCDumbPathFinder;
import valius.model.entity.npc.combat.CombatScript;
import valius.model.entity.npc.combat.Graphic;
import valius.model.entity.npc.combat.Hit;
import valius.model.entity.npc.combat.Projectile;
import valius.model.entity.npc.combat.ScriptSettings;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Hitmark;
import valius.model.minigames.theatre.Theatre;
import valius.util.Misc;

/**
 * 
 * @author ReverendDread
 * Apr 20, 2019
 */
@ScriptSettings(
	npcIds = { 8359 }
)
public class PestilentBloat extends CombatScript {
	
	private static final Location[] corners = {
			new Location(3288, 4440), //bottom left
			new Location(3288, 4451), //top left
			new Location(3299, 4451), //top right
			new Location(3299, 4440) //bottom right
	};
	
	private static final int SLEEPING = 8082; //sleep animation
	private int corner = 0; //corner flag
	@Getter @Setter private int stepsTillStop; //steps till bloat stops walking and then sleeps
	private boolean DR = true; //damage reduction
	@Getter @Setter private boolean stopped; //if bloat has stopped walking
	@Getter @Setter private boolean sleeping;
	private List<Entity> targets;
	private static final int MIN_FLESH = 1570, MAX_FLESH = 1573;
	private static final Boundary ARENA = new Boundary(3288, 4440, 3303, 4455);
	private static final Projectile FLYS = new Projectile(1569, 45, 28, 0, 100, 0, 50);
	private boolean flesh;
	
	@Override
	public void init(NPC npc) {
		setCanAttack(false); //this will stop following when he finds a target.
		npc.setFacePlayer(false); //so he doesnt try to look at the attacking players
		npc.getDefinition().setSize(5); //TODO adjust pathing locations for size 5
		npc.getHealth().isNotSusceptibleTo(HealthStatus.POISON);
		npc.getHealth().isNotSusceptibleTo(HealthStatus.VENOM);
		stepsTillStop = Misc.random(40, 80);
		npc.setNeverWalkHome(true);
		npc.setNoRespawn(true);
	}
	
	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#attack(ethos.model.entity.npc.NPC, ethos.model.entity.Entity)
	 */
	@Override
	public int attack(NPC npc, Entity target) {
		return 1;
	}
	
	@Override
	public void process(NPC npc, Entity target) {
		super.process(npc, target);
		if(npc.isDead)
			return;
		npc.freezeTimer = 0;
		targets = getPossibleTargets(npc, true);
		if (corner >= corners.length) {
			corner = 0;
		}
		if (!npc.getLocation().equalsIgnoreHeight(corners[corner]) && !isStopped()) { //hasnt reached the corner yet
			NPCDumbPathFinder.walkTowards(npc, corners[corner].getX(), corners[corner].getY());
			if (stepsTillStop-- <= 0) {
				setStopped(true);
			}
			if (npc.getLocation().equalsIgnoreHeight(corners[corner]) && !isSleeping()) {
				corner++;
			}
		} else {
			if (isStopped() && getStepsTillStop() <= 0 && !isSleeping()) {
				DR = false;
				shutdown(npc);
			}
		}
		if (!isSleeping()) {
			checkForLineOfSight(npc);
		}
		boolean present = targets.stream().anyMatch(t -> Boundary.isIn(t, Boundary.BLOAT_ARENA));
		if (present && !flesh && !isSleeping()) {
			fleshFall(npc, targets.get(0));
		}
	}
	
	@Override
	public void handleDeath(NPC npc, Entity source) {
		List<Entity> players = getPossibleTargets(npc, true);
		players.forEach(player -> {
			//player.asPlayer().killedBloat = true;
			player.asPlayer().sendMessage("You have defeated the Pestilent Bloat!");			
			Theatre instance = player.asPlayer().getTheatreInstance();
			if (instance != null) {
				player.asPlayer().sendMessage("Current time: "+instance.getTimeElapsed()+".");
			}
			player.asPlayer().sendMessage("You now have "+player.asPlayer().theatrePoints+" points!");
		});
	}
	
	@Override
	public double getDamageReduction(NPC npc) {
		return DR ? 0 : 1;
	}

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#getAttackDistance(ethos.model.entity.npc.NPC)
	 */
	@Override
	public int getAttackDistance(NPC npc) {
		return 20;
	}

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#ignoreProjectileClipping()
	 */
	@Override
	public boolean ignoreProjectileClipping() {
		return true;
	}
	
	/**
	 * Handles line of sight attacks
	 * @param npc
	 */
	private final void checkForLineOfSight(NPC npc) {
		for (Entity target : targets) {
			if (!PathChecker.isProjectilePathClear(npc.getX(), npc.getY(), npc.getHeight(), target.getX(), target.getY())
					&& !PathChecker.isProjectilePathClear(target.getX(), target.getY(), target.getHeight(), npc.getX(), npc.getY())) {
				continue;
			}
			if (target.isPlayer()) {
				handleHit(npc, target, CombatType.SPECIAL, FLYS, new Graphic(1569), new Hit(Hitmark.HIT, Misc.random(1, 23), 3));
			}
		}
	}
	
	/**
	 * Handles falling flesh
	 * @param npc
	 */
	private final void fleshFall(NPC npc, Entity target) {
		flesh = true;
		CycleEvent event = new CycleEvent() {

			int cycle = 0;
			List<Location> locs = ARENA.getRandomLocations(Misc.random(10, 20), npc.getHeight());
			
			@Override
			public void execute(CycleEventContainer container) {
				if(npc.isDead) {
					container.stop();
					return;
				}
				if (cycle == 0) {
					for (Location loc : locs) {
						if (target.isPlayer()) {
							target.asPlayer().getPA().createPlayersStillGfx(Misc.random(MIN_FLESH, MAX_FLESH), loc.getX(), loc.getY(), 0, 0);
						}
					}
				} else if (cycle == 4) {
					for (Entity target : targets) {
						for (Location loc : locs) {
							if (target.getLocation().equalsIgnoreHeight(loc)) {
								target.appendDamage(Misc.random(20, 30), Hitmark.HIT);
							}
						}
					}
				} else if (cycle >= 8) {
					flesh = false;
					container.stop();
				}
				cycle++;
			}
			
		};
		CycleEventHandler.getSingleton().addEvent(-1, npc, event, 1);
	}
	
	/**
	 * Handles the sleeping bit
	 * @param npc
	 */
	private final void shutdown(NPC npc) {
		npc.startAnimation(SLEEPING);
		setSleeping(true);
		CycleEvent event = new CycleEvent() {

			int duration = 0;
			
			@Override
			public void execute(CycleEventContainer container) {
				if (duration >= 29) {
					setStopped(false);
					setStepsTillStop(Misc.random(40, 80));
					setSleeping(false);
					DR = true;
					container.stop();
				}
				duration++;
			}
			
		};
		CycleEventHandler.getSingleton().addEvent(-1, npc, event, 1);
	}
	
}
