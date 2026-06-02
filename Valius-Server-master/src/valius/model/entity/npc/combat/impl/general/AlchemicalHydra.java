/**
 * 
 */
package valius.model.entity.npc.combat.impl.general;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import valius.clip.Region;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.Location;
import valius.model.entity.Entity;
import valius.model.entity.HealthStatus;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCClipping;
import valius.model.entity.npc.NPCDefinitions;
import valius.model.entity.npc.NPCDumbPathFinder;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.npc.combat.CombatScript;
import valius.model.entity.npc.combat.Hit;
import valius.model.entity.npc.combat.Projectile;
import valius.model.entity.npc.combat.ScriptSettings;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.PathFinder;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Hitmark;
import valius.util.Misc;
import valius.world.World;

/**
 * Handles the Alchemical Hydra combat script.
 * @author ReverendDread
 * Mar 28, 2019
 * TODO make hydra not {@see ethos.model.entity.npc.NPC#susceptibleTo(ethos.model.entity.HealthStatus) }.
 * TODO change lightning npcs to proper ones
 */
@ScriptSettings(
	npcNames = { "Alchemical Hydra" },
	npcIds = { }
)
@SuppressWarnings("unused")
public class AlchemicalHydra extends CombatScript {
	
	//Projectiles
	private Projectile POISON_PROJECTILE = new Projectile(1644, 50, 25, 0, 120, 0, 50);
	private Projectile RANGED_PROJECTILE = new Projectile(1662, 50, 25, 0, 100, 0, 50);
	private Projectile MAGIC_PROJECTILE = new Projectile(1663, 50, 25, 0, 100, 0, 50);
	private Projectile FIRE_PROJECTILE = new Projectile(1667, 50, 25, 0, 100, 0, 50);
	
	//Boundarys used for fires
	private static final Boundary SOUTH = new Boundary(1365, 10257, 1368, 10264);
	private static final Boundary NORTH = new Boundary(1365, 10271, 1368, 10271);	
	private static final Boundary EAST = new Boundary(1370, 10266, 1377, 10269);
	private static final Boundary WEST = new Boundary(1356, 10266, 1363, 10269);
	
	//Arena boundary
	private static final Boundary AREA = new Boundary(1356, 10257, 1377, 10278);
	private static final Location CENTER = new Location(1364, 10265);
	
	//Where firewalk ball goes
	private static final Boundary NORTH_WEST = new Boundary(1364, 10270, 1364, 10270);
	private static final Boundary NORTH_EAST = new Boundary(1369, 10270, 1369, 10270);
	private static final Boundary SOUTH_WEST = new Boundary(1364, 10265, 1364, 10265);
	private static final Boundary SOUTH_EAST = new Boundary(1369, 10265, 1369, 10265);
	
	//Lightning npc spawn locs
	private static final Location[] LIGHTNING_SPAWNS = { 
			new Location(1362, 10274), 
			new Location(1373, 10273), 
			new Location(1372, 10261),
			new Location(1360, 10262),
	};
	
	private static final int POISON_SPLASH = 1645;
	private static final int POISON_POOL_MIN = 1654, POISON_POOL_MAX = 1661;
	private static final int FLOOR_FIRE = 1668;
	private static final int MIDDLE_HEAD = 0;
	private static final int LEFT_HEAD = 1;
	private static final int RIGHT_HEAD = 2;
	
	private static final int[][] ATTACK_ANIMS = { 
			//Poison phase
			{
				8234,
				8235,
				8236
			}, 
			//Lightning phase
			{
				8241,
				8242,
				8243
			}, 
			//Flame phase
			{
				8248,
				8249,
				8250
			}, 
			//Enranged phase
			{		
				8255,
				8255,
				8256
			} 
	};
	
	/**
	 * The hydras current phase.
	 */
	private Phases phase = Phases.POISON;
	
	/**
	 * If the hydra has damage reduction.
	 */
	private boolean damageReduction = true, fireAttacking, buffed = false;
	
	/**
	 * Number of attacks since last special attack.
	 */
	private int attackCount = 0;
	
	/**
	 * If the hydra is using magic attacks.
	 */
	private boolean magic;
	
	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#attack(ethos.model.entity.npc.NPC, ethos.model.entity.Entity)
	 */
	@Override
	public int attack(NPC npc, Entity target) {
		
		//Handle special attacks in here.
		if (attackCount == 3) {
			if (phase == Phases.POISON || phase == Phases.ENRAGED) {	
				attackCount++;
						
				return handlePoisonAttack(npc, target);
			} else if (phase == Phases.FLAME) {
				attackCount++;
				
				return handleFlameAttack(npc, target);
			} else if (phase == Phases.LIGHTNING) {
				attackCount++;
				
				return handleLightningAttack(npc, target);
			}
		}
		if(attackCount != 0 && attackCount % 3 == 0 || phase == Phases.ENRAGED)
			magic = !magic;
		//Normal attacks always use left/right head attacks
		npc.startAnimation(ATTACK_ANIMS[phase.ordinal()][(magic ? RIGHT_HEAD : LEFT_HEAD)]);
		//Handle normal attacks here.
		if (magic) {
			if (phase == Phases.POISON || phase == Phases.LIGHTNING)
				handleHit(npc, target, CombatType.MAGE, MAGIC_PROJECTILE, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MAGE, buffed ? 26 : 17), 3));
			handleHit(npc, target, CombatType.MAGE, Projectile.copy(MAGIC_PROJECTILE).setSpeed(105).setStartHeight(45), new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MAGE, buffed ? 26 : 17), 3));		
		} else {

			if (phase == Phases.POISON)
				handleHit(npc, target, CombatType.RANGE, RANGED_PROJECTILE, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.RANGE, buffed ? 26 : 17), 3));
			handleHit(npc, target, CombatType.RANGE, Projectile.copy(RANGED_PROJECTILE).setSpeed(105).setStartHeight(45), new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.RANGE, buffed ? 26 : 17), 3));	
		}
		attackCount++;
		
		return 6;
	}
	
	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#process(ethos.model.entity.npc.NPC, ethos.model.entity.Entity)
	 */
	@Override
	public void process(NPC npc, Entity target) {
		super.process(npc, target);
		
		//Check for damage reduction spraying area //TODO fix this from not registering
		boolean sprayed = Boundary.isIn(npc, phase.getBoundary());
		if (sprayed) {
			damageReduction = false;
		}
		
		//Check for being on the wrong vent
		for (Phases phase : Phases.values()) {
			if (Boundary.isIn(npc, phase.getBoundary()) && (phase != this.phase)) {
				buffed = true;
			}
		}

		//Check for phase switches
		switchPhases(npc);
	}
	
	@Override
	public void init(NPC npc) {
		npc.getHealth().resolveStatus(HealthStatus.POISON, Integer.MAX_VALUE);
		npc.getHealth().resolveStatus(HealthStatus.VENOM, Integer.MAX_VALUE);
	}
	
	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#getDamageReduction(ethos.model.entity.npc.NPC)
	 */
	@Override
	public double getDamageReduction(NPC npc) {
		return damageReduction ? 0.25 : 1;
	}

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#getAttackDistance(ethos.model.entity.npc.NPC)
	 */
	@Override
	public int getAttackDistance(NPC npc) {
		return (phase == Phases.FLAME && fireAttacking ? 16 : 4); //should be more ? TODO
	}

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#ignoreProjectileClipping()
	 */
	@Override
	public boolean ignoreProjectileClipping() {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#isAggressive()
	 */
	@Override
	public boolean isAggressive(NPC npc) {
		return true;
	}
	
	@Override
	public void handleDeath(NPC npc, Entity source) {
		npc.requestTransform(8622);
	}
	
	/**
	 * Handles switching to next phase.
	 * 
	 * @param npc
	 * 			the npc.
	 */
	private boolean switchPhases(NPC npc) {
		//Checks health for next phase change.
		if (npc.getHealth().getAmount() <= phase.getHealth()) {
			//Stops last phase from transforming
			if (phase.ordinal() == Phases.values().length) {
				return false;
			}
			Phases next = Phases.values()[phase.ordinal() + 1];
			//Has to transform first to do the correct animation.
			npc.requestTransform(next.getNpcId());
			npc.startAnimation(next.getTransformation());
			attackCount = 0;
			buffed = false;
			//Set phase to next one.
			phase = next;
			//Enable the damage reduction again
			damageReduction = true;
			//Return that we changed phases.
			return true;
		}
		return false;
	}
	
	/**
	 * Handles the poison | enraged phase special attack.
	 * 
	 * @param npc
	 * 			the npc.
	 * @param target
	 * 			the target.
	 * 
	 * TODO fix projectiles being shot to an offset tile for some reason.
	 */
	private final int handlePoisonAttack(NPC npc, Entity target) {	
		CycleEvent event = null;
		npc.startAnimation(ATTACK_ANIMS[phase.ordinal()][MIDDLE_HEAD]);
		if (phase == Phases.ENRAGED && Misc.random(2) == 1) {
			final Location location = Location.of(target);
			sendProjectileToTile(npc, target, POISON_PROJECTILE);
			event = new CycleEvent() {

				int duration = 0;
				
				@Override
				public void execute(CycleEventContainer container) {
					if (duration == 1) {
						if (target.isPlayer()) {
							target.asPlayer().getPA().stillGfx(POISON_SPLASH, location.getX(), location.getY(), 0, 0);
						}
					} else if (duration == 2) {
						if (target.isPlayer()) {
							target.asPlayer().getPA().stillGfx(Misc.random(POISON_POOL_MIN, POISON_POOL_MAX), location.getX(), location.getY(), 0, 0);
						}
					} else if (duration >= 1 && duration < 17) {
						if (!npc.isDead && target.isPlayer() && target.asPlayer().getLocation().equals(location)) {
							target.appendDamage(Misc.random(1, 12), Hitmark.HIT);
							target.getHealth().proposeStatus(HealthStatus.POISON, 6, Optional.of(npc));
						}
					} else if (duration >= 17) {
						container.stop();
					}
					container.setTick(1);
					duration++;
				}

			};
		} else {		
			List<Location> pools = new ArrayList<Location>(5);
			int count = 0;
			List<Location> tiles = Location.of(target.getX(), target.getY(), target.getHeight()).getSurrounding(3);
			while (pools.size() < 5 && count++ < 50) {
				Location loc = tiles.get(Misc.random(tiles.size() - 1));
				if (loc.withinBoundary(AREA) && Misc.random(2) == 1 && !pools.contains(loc)) {
					pools.add(loc);
				}
			}
			pools.forEach((pool) -> {
				sendProjectileToTile(npc, target, pool, POISON_PROJECTILE);
			});
			event = new CycleEvent() {
	
				int count = 0;
				
				@Override
				public void execute(CycleEventContainer container) {
					pools.forEach((pool) -> {
						if (count == 1) {
							target.asPlayer().getPA().stillGfx(POISON_SPLASH, pool.getX(), pool.getY(), 0, 0);
						} else if (count == 2) {
							target.asPlayer().getPA().stillGfx(Misc.random(POISON_POOL_MIN, POISON_POOL_MAX), pool.getX(), pool.getY(), 0, 0);
						} else if (count >= 2 && count < 18) {
							if (!npc.isDead && !target.asPlayer().isDead() && target.asPlayer().getLocation().equals(pool)) {
								target.appendDamage(Misc.random(1, 12), Hitmark.HIT);
								target.getHealth().proposeStatus(HealthStatus.POISON, 6, Optional.of(npc));
							}					
						} else if (count >= 18) {
							container.stop();
						}
					});
					container.setTick(1);
					count++;
				}
		
			};
		}
		CycleEventHandler.getSingleton().addEvent(-1, npc, event, 3);
		return 6;
	}
	
	/**
	 * Handles the flame phase special attack.
	 * 
	 * @param npc
	 * 			the npc.
	 * @param target
	 * 			the target.
	 * TODO
	 */
	private final int handleFlameAttack(NPC npc, Entity target) {
		fireAttacking = true;
		handleWalkingEvent(npc, target);
		return 6;
	}
	
	/**
	 * Handles event of hydra walking to the center of the room.
	 * @param npc
	 * @param target
	 */
	private final void handleWalkingEvent(NPC npc, Entity target) {
		setCanAttack(false);
		resetCombat(npc);
		CycleEvent walkingEvent = new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				if (target.isPlayer() && target.asPlayer().isDead()) {
					container.stop();
					return;
				}
				if (!npc.getLocation().equalsIgnoreHeight(CENTER)) { //makes hydra walk to center of room if hes not there already.
					NPCDumbPathFinder.walkTowards(npc, CENTER.getX(), CENTER.getY());
				} else {
					npc.teleport(CENTER.getX(), CENTER.getY(), npc.getHeight());
					handleFirewallEvent(npc, target);
					container.stop();
				}
			}
			
		};
		CycleEventHandler.getSingleton().addEvent(-1, npc, walkingEvent, 1);
	}
	
	/**
	 * Handles the event of hydra shooting fire walls on the ground.
	 * @param npc
	 * @param target
	 */
	private final void handleFirewallEvent(NPC npc, Entity target) {
		Boundary[] zones = calculateFireBoundarys(target);
		CycleEvent fireEvent = new CycleEvent() {

			int duration = 0;
			
			@Override
			public void execute(CycleEventContainer container) {
				if (target.isPlayer() && target.asPlayer().isDead()) {
					container.stop();
					return;
				}
				if (duration == 0) {
					if (target.isPlayer())
						target.asPlayer().freezeTimer = 12;
					npc.turnNpc(Boundary.centre(zones[0]).getX(), Boundary.centre(zones[0]).getY());
				} else if (duration == 2) {
					npc.startAnimation(ATTACK_ANIMS[phase.ordinal()][MIDDLE_HEAD]);
				} else if (duration == 4) {
					if (target.isPlayer()) {
						if (zones[0].equals(NORTH)) {
							for (int height = 0; height < 8; height++) {
								for (int width = 0; width < 4; width++) {
									target.asPlayer().getPA().stillGfx(1668, zones[0].getMinimumX() + width, zones[0].getMinimumY() + height, 0, 0);
								}
							}
						}
						if (zones[0].equals(EAST)) {
							for (int height = 0; height < 8; height++) {
								for (int width = 0; width < 4; width++) {
									target.asPlayer().getPA().stillGfx(1668, zones[0].getMinimumX() + height, zones[0].getMinimumY() + width, 0, 0);
								}
							}
						}
						if (zones[0].equals(WEST)) {
							for (int height = 0; height < 8; height++) {
								for (int width = 0; width < 4; width++) {
									target.asPlayer().getPA().stillGfx(1668, zones[0].getMaximumX() - height, zones[0].getMinimumY() + width, 0, 0);
								}
							}
						}
						if (zones[0].equals(SOUTH)) {
							for (int height = 0; height < 8; height++) {
								for (int width = 0; width < 4; width++) {
									target.asPlayer().getPA().stillGfx(1668, zones[0].getMinimumX() + width, zones[0].getMaximumY() - height, 0, 0);
								}
							}
						}
					}
				} else if (duration == 6) {
					npc.turnNpc(Boundary.centre(zones[1]).getX(), Boundary.centre(zones[1]).getY());	
				} else if (duration == 8) {
					npc.startAnimation(ATTACK_ANIMS[phase.ordinal()][MIDDLE_HEAD]);
				} else if (duration == 10) {
					if (target.isPlayer()) {
						if (zones[1].equals(NORTH)) {
							for (int height = 0; height < 8; height++) {
								for (int width = 0; width < 4; width++) {
									target.asPlayer().getPA().stillGfx(1668, zones[1].getMinimumX() + width, zones[1].getMinimumY() + height, 0, 0);
								}
							}
						}
						if (zones[1].equals(EAST)) {
							for (int height = 0; height < 8; height++) {
								for (int width = 0; width < 4; width++) {
									target.asPlayer().getPA().stillGfx(1668, zones[1].getMinimumX() + height, zones[1].getMinimumY() + width, 0, 0);
								}
							}
						}
						if (zones[1].equals(WEST)) {
							for (int height = 0; height < 8; height++) {
								for (int width = 0; width < 4; width++) {
									target.asPlayer().getPA().stillGfx(1668, zones[1].getMaximumX() - height, zones[1].getMaximumY() - width, 0, 0);
								}
							}
						}
						if (zones[1].equals(SOUTH)) {
							for (int height = 0; height < 8; height++) {
								for (int width = 0; width < 4; width++) {
									target.asPlayer().getPA().stillGfx(1668, zones[1].getMinimumX() + width, zones[1].getMaximumY() - height, 0, 0);
								}
							}
						}
					}
				} else if (duration == 12) {
					npc.faceEntity(target.getIndex());
					npc.startAnimation(ATTACK_ANIMS[phase.ordinal()][MIDDLE_HEAD]);
					sendProjectileToTile(npc, target, zones[2].getMinLocation(), FIRE_PROJECTILE);
					handleFirewalk(npc, target, zones[2]);
				} else if (duration >= 61) {
					container.stop();
				}
				if (Boundary.isIn(target, zones[0], (duration >= 10 ? zones[1] : Boundary.EMPTY)) && duration >= 4 && duration <= 60) {
					target.appendDamage(Misc.random(1, 20), Hitmark.HIT);
				}
				duration++;
			}
			
		};
		CycleEventHandler.getSingleton().addEvent(-1, npc, fireEvent, 1);
	}
	
	/**
	 * Handles the fire that follows the target for 14 tiles.
	 * @param npc
	 * @param target
	 * @param initialStep TODO
	 */
	private final void handleFirewalk(NPC npc, Entity target, Boundary initialStep) {
		CycleEventHandler.getSingleton().addEvent(-1, npc, new CycleEvent() {

			int duration;
			ArrayList<Location> tiles = new ArrayList<Location>();
			Location loc = initialStep.getMinLocation();
			
			@Override
			public void execute(CycleEventContainer container) {
				if (target.isPlayer() && target.asPlayer().isDead()) {
					container.stop();
					return;
				}
				if (duration >= 60) {
					container.stop();
					return;
				} else if (duration >= 30 && duration <= 44 && !tiles.isEmpty()) {
					tiles.remove(0); //removes the first loc always
				}
				for (Location tile : tiles) {
					if (target.getLocation().equalsIgnoreHeight(tile)) {
						target.appendDamage(Misc.random(1, 20), Hitmark.HIT);
					}
				}
				if (duration > 0 && duration <= 14) {
					int[] steps = Region.getNextStep(loc.getX(), loc.getY(), target.getX(), target.getY(), target.getHeight(), 1, 1);
					loc = Location.of(steps[0], steps[1]);
					if (!tiles.contains(loc)) {		
						target.asPlayer().getPA().stillGfx(1668, loc.getX(), loc.getY(), 0, 0);	
						tiles.add(loc);
					}
				} else if (duration > 14) {
					fireAttacking = false;
					setCanAttack(true);
				}
				duration++;
			}					
			
		}, 1);
	}
	
	/**
	 * Calculates which zones need to be on fire depending on where the target is.
	 * 
	 * @param target
	 * 			the target.
	 * @return
	 */
	private final Boundary[] calculateFireBoundarys(Entity target) {
		Location center = Boundary.centre(AREA);
		Boundary[] zones = new Boundary[] { NORTH, EAST, NORTH_EAST }; //defaults
		if (target.getX() >= center.getX() && target.getY() >= center.getY()) { //NORTH_EAST
			zones = new Boundary[] { NORTH, EAST, NORTH_EAST };
		} else if (target.getX() >= center.getX() && target.getY() <= center.getY()) { //SOUTH_EAST
			zones = new Boundary[] { SOUTH, EAST, SOUTH_EAST };
		} else if (target.getX() <= center.getX() && target.getY() >= center.getY()) { //NORTH_WEST
			zones = new Boundary[] { NORTH, WEST, NORTH_WEST };
		} else if (target.getX() <= center.getX() && target.getY() <= center.getY()) { //SOUTH_WEST
			zones = new Boundary[] { SOUTH, WEST, SOUTH_WEST };
		}
		return zones;
	}
	
	/**
	 * Handles the lightning phase special attack.
	 * 
	 * @param npc
	 * 			the npc.
	 * @param target
	 * 			the target.
	 * TODO
	 */
	private final int handleLightningAttack(NPC npc, Entity target) {
		CycleEvent event = new CycleEvent() {

			int duration = 0;
			Location[] steps = LIGHTNING_SPAWNS;
			
			@Override
			public void execute(CycleEventContainer container) {
				if (target.isPlayer() && target.asPlayer().isDead()) {
					container.stop();
					return;
				}
				if (duration == 0) { //spawn the niggas
					for (Location spawn : LIGHTNING_SPAWNS) {
						sendProjectileToTile(npc, target, spawn, new Projectile(1665, 50, 0, 0, 100, 0, 50));
					}
				} else if (duration <= 14) { //make them follow the player & do damage if needed
					for (int index = 0; index < steps.length; index++) {
						int[] step = Region.getNextStep(steps[index].getX(), steps[index].getY(), target.getX(), target.getY(), target.getHeight(), 1, 1);						
						steps[index] = Location.of(step[0], step[1], target.getHeight());
						if (target.isPlayer()) {
							target.asPlayer().getPA().stillGfx(1666, steps[index].getX(), steps[index].getY(), 0, 0);
						}
						if (target.isPlayer() && steps[index].equals(target.getLocation())) {
							target.asPlayer().freezeTimer = 6;
							target.appendDamage(Misc.random(5, 20), Hitmark.HIT);
						}
					}
				} else if (duration >= 15) {	
					container.stop();
				}
				duration++;
			}

		};
		CycleEventHandler.getSingleton().addEvent(-1, npc, event, 1);
		return 6;
	}
	
	/**
	 * Holds data related to a phase.
	 * 
	 * @author ReverendDread
	 * Mar 28, 2019
	 */
	private enum Phases {
		
		POISON(8615, -1, 825, new Boundary(1369, 10261, 1373, 10265)), 
		
		LIGHTNING(8619, 8238, 550, new Boundary(1369, 10270, 1373, 10274)),
		
		FLAME(8620, 8245, 275, new Boundary(1360, 10270, 1364, 10274)),
		
		ENRAGED(8621, 8252, -1, AREA);	
		
		@Getter private int npcId, health;
		@Getter private int transformation;
		@Getter private Boundary boundary;
		
		private Phases(int npcId, int transformation, int health, Boundary boundary) {
			this.npcId = npcId;
			this.transformation = transformation;
			this.health = health;
			this.boundary = boundary;
		}
		
	}

}
