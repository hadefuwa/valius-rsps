package valius.model.entity.npc.combat.impl.tob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import lombok.Data;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.Location;
import valius.model.entity.Entity;
import valius.model.entity.HealthStatus;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.combat.CombatScript;
import valius.model.entity.npc.combat.Graphic;
import valius.model.entity.npc.combat.Hit;
import valius.model.entity.npc.combat.Projectile;
import valius.model.entity.npc.combat.ScriptSettings;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Hitmark;
import valius.model.minigames.theatre.Theatre;
import valius.util.Misc;

/**
 * 
 * @author ReverendDread
 * Apr 20, 2019
 */
@ScriptSettings(
	npcIds = { 8338, 8339, 8340, 8341 }
)
public class Xarpus extends CombatScript {
	
	//courners as locations, check if player is within 6 tiles of it
	
	//exhumed spawn 25 times
	//exhumed close gfx 1549
	//attack anim = 8059
	//take off ground = 8061
	
	private int phase = 0; //0 = healing, 1 = poison splats, 2 = death stare;
	private CycleEventContainer healingContainer;
	private CycleEventContainer staringContainer;
	private List<PoisonPool> poison = new ArrayList<PoisonPool>();
	private int exumedCount = 0;
	private Boundary ARENA = new Boundary(3163, 4380, 3177, 4394);
	private static final Projectile HEALING = new Projectile(1550, 0, 0, 0, 120, 0, 50);
	private static final Projectile POISON_PROJECTILE = new Projectile(1555, 50, 25, 0, 120, 0, 50);
	private Location STARING_QUAD;
	private int oldestPool = 0;
	
	private static final Location[] QUAD = {
			new Location(3163, 4380, 1),
			new Location(3163, 4380, 1),
			new Location(3177, 4394, 1),
			new Location(3177, 4380, 1)
	};
	
	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#init(ethos.model.entity.npc.NPC)
	 */
	@Override
	public void init(NPC npc) {
		setCanAttack(false);
		npc.getHealth().resolveStatus(HealthStatus.POISON, Integer.MAX_VALUE);
		npc.getHealth().resolveStatus(HealthStatus.VENOM, Integer.MAX_VALUE);
		STARING_QUAD = QUAD[Misc.random(0, QUAD.length - 1)];
		npc.setNoRespawn(true);
		npc.setNeverWalkHome(true);
	}
	
	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#attack(ethos.model.entity.npc.NPC, ethos.model.entity.Entity)
	 */
	@Override
	public int attack(NPC npc, Entity target) {		
		List<Entity> targets = getPossibleTargets(npc, true);
		if (phase == 1) { //poison pools		
			npc.startAnimation(8059);
			final Location tile = target.getLocation();
			CycleEvent event = new CycleEvent() {

				Entity next = target;
				
				@Override
				public void execute(CycleEventContainer container) {
					if (container.getTotalTicks() == 1) {
						sendProjectileToTile(npc, target, tile, POISON_PROJECTILE);
					} else if (container.getTotalTicks() == 4) {
						boolean exists = poison.stream().anyMatch(pool -> pool.getLocation().equalsIgnoreHeight(tile));
						if (!exists) {
							if (poison.size() > 25) {
								poison.set(oldestPool, new PoisonPool(Misc.random(1654, 1661), tile));
								if (oldestPool > 25) {
									oldestPool = 0;
								}
							} else
								poison.add(new PoisonPool(Misc.random(1654, 1661), tile));
						}
						target.asPlayer().getPA().createPlayersStillGfx(1556, tile.getX(), tile.getY(), 0, 0);
					} else if (container.getTotalTicks() == 5) {
						int tries = 0;
						while (next == target && tries++ < 50 && targets.size() > 1) {
							next = Misc.randomTypeOfList(targets);
						}
						sendProjectile(target, tile, next, POISON_PROJECTILE);
					} else if (container.getTotalTicks() == 9) {
						final Location nextTile = next.getLocation();
						boolean exists = poison.stream().anyMatch(pool -> pool.getLocation().equalsIgnoreHeight(nextTile));
						if (!exists) {
							if (poison.size() > 25) {
								poison.set(oldestPool, new PoisonPool(Misc.random(1654, 1661), nextTile));
								oldestPool++;
								if (oldestPool > 25) {
									oldestPool = 0;
								}
							} else
								poison.add(new PoisonPool(Misc.random(1654, 1661), nextTile));
						}
						target.asPlayer().getPA().createPlayersStillGfx(1556, nextTile.getX(), nextTile.getY(), 0, 0);
						container.stop();
					}
				}
				
			};
			CycleEventHandler.getSingleton().addEvent(-1, npc, event, 1);			
		}
		return 6;
	}
	
	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#process(ethos.model.entity.npc.NPC, ethos.model.entity.Entity)
	 */
	@Override
	public void process(NPC npc, Entity target) {
		super.process(npc, target);
		List<Entity> targets = getPossibleTargets(npc, true);
		Optional<Entity> u = targets.stream().filter(t -> t.withinDistance(npc, 6)).findAny();
		if (phase == 0 && exumedCount < 10 && u.isPresent()) {
			if (healingContainer == null) {	
				if (!targets.isEmpty()) {
					CycleEvent event = new CycleEvent() {
						
						private Location exumed;
						
						@Override
						public void execute(CycleEventContainer container) {	
							if (container.getTotalTicks() == 1) {
								int x = Misc.random(ARENA.getMinimumX(), ARENA.getMaximumX()), y = Misc.random(ARENA.getMinimumY(), ARENA.getMaximumY());
								exumed = Location.of(x, y);
								exumedCount++;
							} else if (container.getTotalTicks() > 1 && container.getTotalTicks() <= 16) {
								boolean plugged = false;
								for (Entity entity : targets) {
									if (entity.getLocation().equalsIgnoreHeight(exumed)) {
										plugged = true;
									}
								}
								if (targets.get(0).isPlayer()) {
									targets.get(0).asPlayer().getPA().createPlayersStillGfx(1549, exumed.getX(), exumed.getY(), 0, 0);
								}
								if (!plugged && exumed != null) {
									sendProjectile(targets.get(0), exumed, npc.getLocation().center(npc.getSize()), HEALING);
									npc.getHealth().setMaximum(npc.getHealth().getMaximum() + 15);
									Damage damage = new Damage(15);
									damage.setHitmark(Hitmark.HEAL_PURPLE);
									damage.setTicks(4);
									npc.appendDamage(npc, damage);
								}
							} else if (container.getTotalTicks() >= 17) {
								if (exumedCount >= 10) {
									setCanAttack(true);
									npc.requestTransform(8340);
									npc.startAnimation(8061);
									npc.killerId = Misc.randomTypeOfList(targets).getIndex();
									phase = 1;
								}
								container.stop();
							}
						}
						
					};
					healingContainer = new CycleEventContainer(-1, npc, event, 1);
					CycleEventHandler.getSingleton().addEvent(healingContainer);
				}
			} else {
				if (!healingContainer.isRunning()) {
					healingContainer = null;
				}
			}
		}
		if (phase == 2) {
			if (staringContainer == null) {
				CycleEvent event = new CycleEvent() {

					@Override
					public void execute(CycleEventContainer container) {
						if (container.getTotalTicks() == 1) {
							STARING_QUAD = getRandomFocus();
							npc.turnNpc(STARING_QUAD.getX(), STARING_QUAD.getY());
						} else if (container.getTotalTicks() >= 12) {
							container.stop();
						}
					}
					
				};
				staringContainer = new CycleEventContainer(-1, npc, event, 1);
				CycleEventHandler.getSingleton().addEvent(staringContainer);
			} else {
				if (!staringContainer.isRunning()) {
					staringContainer = null;
				}
			}
		}		
		if (phase == 1 || phase == 2) {
			for (PoisonPool pool : poison) {
				if (pool == null)
					continue;		
				Entity creator = targets.stream().filter(c -> !c.asPlayer().isDead).findFirst().orElse(null);
				if (creator != null)
					creator.asPlayer().getPA().createPlayersStillGfx(pool.getGfx(), pool.getLocation().getX(), pool.getLocation().getY(), 0, 0);	
				for (Entity t : targets) {
					if (t == null || t.asPlayer().isDead)
						continue;			
					if (t.getLocation().equalsIgnoreHeight(pool.getLocation())) {
						handleHit(npc, t, CombatType.SPECIAL, new Hit(Hitmark.POISON, Misc.random(6, 8), 1));
					}
				}
			}
		}
		if (phase == 3) {
			poison.clear();
		}
	}
	
	@Override
	public void handleRecievedHit(final NPC npc, final Entity source, final Damage damage) {
		double health = (double) ((double) npc.getHealth().getAmount() / npc.getHealth().getMaximum()) * 100;
		if (health <= 22.5D && phase != 2) {
			setCanAttack(false);
			phase = 2;
			npc.forceChat("Reeeeeeeeeeeeeeeeeeeeeeeeeeee");
			npc.killerId = 0;
		}
		if (phase == 2) {
			if (source.getLocation().withinDistance(STARING_QUAD, 7)) {
				handleHit(npc, source, CombatType.SPECIAL, POISON_PROJECTILE, new Graphic(1556), new Hit(Hitmark.POISON, 50 + (int) Math.ceil((damage.getAmount() / 2)), 3));
			}
		}
		super.handleRecievedHit(npc, source, damage);
	}
	
	@Override
	public void handleDeath(NPC npc, Entity source) {
		npc.requestTransform(8341);
		List<Entity> players = getPossibleTargets(npc, true);
		players.forEach(player -> {
			//player.asPlayer().killedXarpus = true;
			player.asPlayer().sendMessage("You have defeated Xarpus!");
			Theatre instance = player.asPlayer().getTheatreInstance();
			if (instance != null) {
				player.asPlayer().sendMessage("Current time: "+instance.getTimeElapsed()+".");
			}
			player.asPlayer().sendMessage("You now have "+player.asPlayer().theatrePoints+" points!");
		});
	}
	
	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#getFollowDistance(ethos.model.entity.npc.NPC)
	 */
	@Override
	public int getFollowDistance(NPC npc) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#getAttackDistance(ethos.model.entity.npc.NPC)
	 */
	@Override
	public int getAttackDistance(NPC npc) {
		return 8;
	}

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#isAggressive()
	 */
	@Override
	public boolean isAggressive(NPC npc) {
		return phase == 0 || phase == 2 ? false : true;	
	}
	
	/**
	 * Gets the next random focus point, excludes last one.
	 * @return
	 */
	public Location getRandomFocus() {
		Location random = QUAD[Misc.random(0, QUAD.length - 1)];
		if (random.equalsIgnoreHeight(STARING_QUAD)) {
			return getRandomFocus();
		} else {
			return random;
		}
	}
	
	@Data
	public class PoisonPool {
		
		public final int gfx;
		public final Location location;
		
	}
	
}
