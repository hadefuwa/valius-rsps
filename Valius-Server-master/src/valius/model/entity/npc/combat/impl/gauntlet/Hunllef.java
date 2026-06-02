/**
 * 
 */
package valius.model.entity.npc.combat.impl.gauntlet;

import valius.clip.WorldObject;
import valius.content.gauntlet.GauntletRoom;
import valius.content.gauntlet.GauntletType;
import valius.content.gauntlet.TheGauntlet;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.Location;
import valius.model.entity.Entity;
import valius.model.entity.instance.Instance;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCDumbPathFinder;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.npc.combat.CombatScript;
import valius.model.entity.npc.combat.Graphic;
import valius.model.entity.npc.combat.Hit;
import valius.model.entity.npc.combat.Projectile;
import valius.model.entity.npc.combat.ScriptSettings;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

import lombok.Setter;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Hitmark;
import valius.model.entity.player.combat.melee.CombatPrayer;
import valius.util.Misc;

/**
 * @author ReverendDread
 * Sep 7, 2019
 */
@ScriptSettings( npcIds = { 9021, 9022, 9023, 9024, 9035, 9036, 9037, 9038 })
public class Hunllef extends CombatScript {

	private static final Projectile MAGIC_PROJ = new Projectile(1701, 40, 35, 0, 100, 0, 50); 
	private static final Graphic MAGIC_SPLASH = new Graphic(1703, 50);
	private static final Projectile RANGE_PROJ = new Projectile(1705, 40, 35, 0, 100, 0, 50);
	
	private static final int ATTACK = 8419, SWITCH_STYLES = 8418;
	
	private static Boundary[][] floorPatterns = {
		{
			new Boundary(new Rectangle(4, 4, 6, 6)),
		},
		{
			new Boundary(new Rectangle(2, 2, 12, 3)),
			new Boundary(new Rectangle(2, 11, 12, 3)),
		},
		{
			new Boundary(new Rectangle(2, 2, 3, 12)),
			new Boundary(new Rectangle(11, 2, 3, 12)),
		},
		{
			new Boundary(new Rectangle(3, 3, 4, 4)),
			new Boundary(new Rectangle(9, 3, 4, 4)),
			new Boundary(new Rectangle(3, 9, 4, 4)),
			new Boundary(new Rectangle(9, 9, 4, 4))
		},
		{
			new Boundary(new Rectangle(2, 2, 12, 2)),
			new Boundary(new Rectangle(2, 12, 12, 2)),
			new Boundary(new Rectangle(2, 2, 2, 12)),
			new Boundary(new Rectangle(12, 2, 2, 12)),
		},
		{
			new Boundary(new Rectangle(6, 6, 4, 4)),
			new Boundary(new Rectangle(2, 2, 3, 3)),
			new Boundary(new Rectangle(11, 2, 3, 3)),
			new Boundary(new Rectangle(11, 11, 3, 3)),
			new Boundary(new Rectangle(2, 11, 3, 3)),
		},
		{ 
			new Boundary(0, 0, 16, 16).inset(5)		
		},
	};
	
	//npc 9025 is tornado, 9039 for corrupted
	
	//8416, 8417, 8418, 8419, 8420, 8421, 8423, 8424, 8427, 8428
	
	@Setter private CombatType type = Misc.randomTypeOfList(Arrays.asList(CombatType.RANGE, CombatType.MAGE)); //start with random combat type.
	private ArrayList<NPC> tornados = Lists.newArrayList();
	private int attack;
	private int stompCooldown = 3;
	private int floorCooldown = 10;
	private boolean tornadoTime;
	
	@Override
	public void init(NPC npc) {
	
	}
	
	@Override
	public int attack(NPC npc, Entity target) {
		if (changeStyles(npc, target)) {
			tornadoTime = !tornadoTime;
			return 5;
		}
		return basic_attack(npc, target);
	}
	
	@Override
	public void process(NPC npc, Entity target) {
		if (target != null && Instance.is(npc, TheGauntlet.class) && Instance.is(target, TheGauntlet.class)) {
			TheGauntlet gauntlet = (TheGauntlet) npc.getInstance();
			GauntletRoom room = gauntlet.getDungeon().getRoom(npc.getLocation()).get();
			if (room != null && Boundary.isIn(npc, room.getBoundary()) && Boundary.isIn(target, room.getBoundary())) {
				boolean stomp = npc.collides(npc.getX(), npc.getY(), npc.getSize(), target.getX(), target.getY(), target.getSize());
				stompCooldown--;
				floorCooldown--;
				if (stomp && stompCooldown <= 0) {
					handleHit(npc, target, CombatType.SPECIAL, new Hit(Hitmark.HIT, Misc.random(30, 50), 1));
					stompCooldown = 3;
				}
				if (floorCooldown <= 0) {
					doFloors(npc, target);
					floorCooldown = 30;
				}
			}
		}
		super.process(npc, target);
	}
	
	@Override
	public void handleRecievedHit(NPC npc, Entity source, Damage damage) {
		if (damage.getCombatType() == type)
			damage.setAmount(0);
	}

	@Override
	public int getAttackDistance(NPC npc) {
		return 16;
	}
	
	@Override
	public boolean isAggressive(NPC npc) {
		return true;
	}
	
	@Override
	public boolean ignoreCollision() {
		return false;
	}
	
	/**
	 * Handles basic attacks.
	 * @param npc
	 * @param target
	 * @return
	 */
	private int basic_attack(NPC npc, Entity target) {		
		npc.startAnimation(ATTACK);
		if (type == CombatType.MAGE) {
			handleHit(npc, target, type, MAGIC_PROJ, MAGIC_SPLASH, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, type, 50), 3), () -> {			
				if (Misc.random(5) == 1) {
					if (target.isPlayer() && CombatPrayer.resetOverHeads(target.asPlayer())) {
						target.asPlayer().sendMessage("Your overhead prayers have been disabled.");
					}
				}
			});
		} else if (type == CombatType.RANGE) {
			handleHit(npc, target, type, RANGE_PROJ, null, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, type, 50), 3));
		}	
		attack++;
		return 5;
	}
	
	private void doFloors(NPC npc, Entity target) {
		Boundary[] boundaries = Misc.randomElementOf(floorPatterns);	
		if (target.isPlayer() && target.getInstance() instanceof TheGauntlet) {
			TheGauntlet gauntlet = (TheGauntlet) npc.getInstance();
			GauntletRoom room = gauntlet.getDungeon().getRoom(npc.getLocation()).get();
			CycleEvent event = new CycleEvent() {
				
				int tick = 0;
				
				@Override
				public void execute(CycleEventContainer container) {
					if (npc.isDead || !Instance.is(target, TheGauntlet.class)) {
						container.stop();
						return;
					}
					List<WorldObject> objects = Lists.newArrayList();
					if (tick == 0) {				
						Stream.of(boundaries).forEach(boundary -> {
							boundary.stream().filter(Objects::nonNull).forEach(tile -> {
								objects.add(new WorldObject(36150, tile.getX() + room.getLocation().getX(), tile.getY() + room.getLocation().getY(), 1, 22, 0));
							});
						});
						target.asPlayer().getPA().sendBulkObjects(objects.toArray(new WorldObject[0]));
					} else if (tick >= 8 && tick < 28) {
						if (tick == 8) {
							Stream.of(boundaries).forEach(boundary -> {
								boundary.stream().filter(Objects::nonNull).forEach(tile -> {
									objects.add(new WorldObject(36151, tile.getX() + room.getLocation().getX(), tile.getY() + room.getLocation().getY(), 1, 22, 0));
								});
							});
							target.asPlayer().getPA().sendBulkObjects(objects.toArray(new WorldObject[0]));
						}
						Stream.of(boundaries).forEach(boundary -> {
							Boundary newBoundary = boundary.translate(room.getLocation().getX(), room.getLocation().getY(), 0);
							if (Boundary.isInExclusive(target, newBoundary)) {
								target.appendDamage(10, Hitmark.HIT);
							}
						});
					} else if (tick == 29) {
						Stream.of(boundaries).forEach(boundary -> {
							boundary.stream().filter(Objects::nonNull).forEach(tile -> {
								objects.add(new WorldObject(36149, tile.getX() + room.getLocation().getX(), tile.getY() + room.getLocation().getY(), 1, 22, 0));
							});
						});
						target.asPlayer().getPA().sendBulkObjects(objects.toArray(new WorldObject[0]));
						container.stop();
					}
					tick++;
				}
			};
			CycleEventHandler.getSingleton().addEvent(0, target, event, 1);
		}
	}
	
	/**
	 * Handles tornado attack
	 * @param npc
	 * @param target
	 */
	private void tornados(NPC npc, Entity target) {
		Boundary playerBoundary = target.getBoundary().expand(3, 3);
		if (npc.getInstance() instanceof TheGauntlet) {
			TheGauntlet gauntlet = (TheGauntlet) target.getInstance();
			Optional<GauntletRoom> room = gauntlet.getDungeon().getRoom(npc.getLocation());
			if (room.isPresent()) {
				int allowedSpawns = npc.getHealth().getPercentage() > 0.75D ? 2 : npc.getHealth().getPercentage() > 0.50D ? 3 : 4;
				Boundary roomBoundary = room.get().getBoundary().inset(2);
				List<Location> tiles = roomBoundary.getRandomLocations(allowedSpawns, npc.getHeight()).stream().filter(tile -> !tile.withinBoundary(playerBoundary)).collect(Collectors.toList());
				for (int index = 0; index < allowedSpawns; index++) {
					Location loc = Misc.randomTypeOfList(tiles);
					if (loc != null) {
						NPC tornado = NPCHandler.spawn(gauntlet.getType().equals(GauntletType.NORMAL) ? 9025 : 9039, loc.getX(), loc.getY(), npc.getHeight(), 0, 0, 0, 0, 0, false);
						tornado.setInstance(target.getInstance());
						tornado.faceEntity(target);
						tornados.add(tornado);
					}
				}
				CycleEvent event = (container) -> {
					boolean shouldKill = container.getTotalTicks() >= 20 || npc.isDead || !Instance.is(target, TheGauntlet.class);
					if (!shouldKill) {	
						for (NPC tornado : tornados) {
							tornado.walkingHome = false;
							tornado.randomWalk = false;
							tornado.neverWalkHome = true;
							boolean collides = tornado.getLocation().equals(target.getLocation());
							if (collides) {
								handleHit(tornado, target, CombatType.SPECIAL, new Hit(Hitmark.HIT, Misc.random(1, 2), 1, true));
							} else
								NPCDumbPathFinder.walkTowards(tornado, target.getX(), target.getY());
						}
					} else {
						tornados.stream().forEach(NPCHandler::destroy);
						tornados.clear();
						container.stop();
					}
				};
				CycleEventHandler.getSingleton().addEvent(0, npc, event, 1);
			}
		}
	}
	
	/**
	 * Handles style changes.
	 * @param npc
	 */
	private boolean changeStyles(NPC npc, Entity target) {
		boolean change = attack > 0 && attack % 4 == 0;
		if (change) {
			CycleEvent event = new CycleEvent() {
				
				int ticks = 0;
				
				@Override
				public void execute(CycleEventContainer container) {
					if (ticks == 0) {
						npc.startAnimation(SWITCH_STYLES);
					} else if (ticks == 3) {
						CombatType nextType = type == CombatType.MAGE ? CombatType.RANGE : CombatType.MAGE;
						setType(nextType);
						npc.requestTransform(getIdForStyle(nextType));
						//if (tornadoTime) 
							tornados(npc, target);
						attack = 0;
						container.stop();
					}
					ticks++;
				}
				
			};
			CycleEventHandler.getSingleton().addEvent(new CycleEventContainer(0, npc, event, 1));
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	private int getIdForStyle(CombatType type) {
		switch (type) {
			case RANGE:
				return 9022;
			case MAGE:
				return 9023;
			default:
				return 9021;
		}
	}

}
