///**
// * 
// */
//package valius.model.entity.npc.combat.impl.corporeal;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//import com.google.common.collect.Lists;
//
//import valius.event.CycleEvent;
//import valius.event.CycleEventContainer;
//import valius.event.CycleEventHandler;
//import valius.model.Location;
//import valius.model.entity.Entity;
//import valius.model.entity.HealthStatus;
//import valius.model.entity.npc.NPC;
//import valius.model.entity.npc.NPCHandler;
//import valius.model.entity.npc.combat.CombatScript;
//import valius.model.entity.npc.combat.Hit;
//import valius.model.entity.npc.combat.Projectile;
//import valius.model.entity.npc.combat.ScriptSettings;
//import valius.model.entity.player.Boundary;
//import valius.model.entity.player.PlayerHandler;
//import valius.model.entity.player.combat.CombatType;
//import valius.model.entity.player.combat.Damage;
//import valius.model.entity.player.combat.Hitmark;
//import valius.model.items.ItemDefinition;
//import valius.util.Misc;
//
///**
// * @author ReverendDread
// * Jul 15, 2019
// */
//@ScriptSettings (npcNames = { "Corporeal Beast" } )
//public class CorporealBeast extends CombatScript {
//	
//	private static final int MELEE_ANIM = 1682, MAGIC_ANIM = 1680;
//	private static final Projectile MAGIC_PROJ = new Projectile(314, 50, 25, 0, 120, 1, 50);
//	private static final Projectile SPECIAL_PROJ = new Projectile(316, 50, 0, 0, 140, 1, 50);
//	private static final Projectile SPECIAL_SUB_PROJ = new Projectile(315, 0, 0, 0, 120, 0, 50);
//	private static final Projectile DARK_CORE_PROJ = new Projectile(319, 0, 0, 0, 120, 0, 50);
//	
//	private NPC core;
//	private int stompDelay;
//	
//	@Override
//	public void init(NPC npc) {
//		npc.getHealth().isNotSusceptibleTo(HealthStatus.POISON);
//		npc.getHealth().isNotSusceptibleTo(HealthStatus.VENOM);
//		npc.getDefinition().setSize(5);
//	}
//	
//	@Override
//	public int attack(NPC npc, Entity target) {
//		boolean melee = target.withinDistanceOfCenter(npc, 3) && Misc.random(2) == 1;
//		boolean special = Misc.random(3) == 1;
//		if (melee) {
//			handleHit(npc, target, CombatType.MELEE, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MELEE, 51), 1));
//			npc.startAnimation(MELEE_ANIM);
//		} else if (special) {
//			final Location position = target.getLocation();
//			List<Location> spots = Lists.newArrayList();
//			CycleEvent event = new CycleEvent() {
//				
//				@Override
//				public void execute(CycleEventContainer container) {	
//					List<Location> possibleSpots = position.getSurrounding(3);
//					Collections.shuffle(possibleSpots);
//					spots.addAll(possibleSpots.stream().limit(5).collect(Collectors.toList()));
//					for (Location loc : spots) {
//						sendProjectile(target, position, loc, SPECIAL_SUB_PROJ);
//					}
//					container.stop();
//				}
//				
//			};
//			CycleEvent collideCheck = new CycleEvent() {
//				
//				@Override
//				public void execute(CycleEventContainer container) {	
//					List<Entity> targets = getPossibleTargets(npc, true).stream().filter(p -> !p.asPlayer().isDead).collect(Collectors.toList());						
//					targets.stream().					
//					filter(entity -> {
//						List<Location> locs = entity.getLocation().getSurrounding(1);
//						for (Location loc : locs) {
//							if (spots.contains(loc))
//								return true;
//						}
//						return false;
//					}).					
//					forEach(p -> handleHit(npc, target, CombatType.SPECIAL, null, null, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.SPECIAL, 50), 1)));					
//					if (target != null) {
//						spots.forEach(spot -> target.asPlayer().getPA().createPlayersStillGfx(317, spot.getX(), spot.getY(), spot.getZ(), 3));
//					}
//					container.stop();
//				}
//				
//			};
//			CycleEventHandler.getSingleton().addEvent(npc, event, 4);
//			CycleEventHandler.getSingleton().addEvent(npc, collideCheck, 8);
//			npc.startAnimation(MAGIC_ANIM);
//			handleDodgableAttack(npc, target, CombatType.MAGE, SPECIAL_PROJ, null, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MAGE, 65), 3, false));			
//		} else { //magic attack
//			handleHit(npc, target, CombatType.MAGE, MAGIC_PROJ, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MAGE, 65), 4));
//			npc.startAnimation(MAGIC_ANIM);
//		}
//		if (Misc.random(20) == 1) {
//			if (core == null || core.isDead) {
//				core = NPCHandler.spawnNpc(320, npc.getCenter().getX(), npc.getCenter().getY(), npc.getHeight(), 0, 25, 13, 1, 10);
//			}
//		}
//		return 6;
//	}
//	
//	@Override
//	public void process(NPC npc, Entity target) {
//		List<Entity> targets = getPossibleTargets(npc, true).stream().filter(player -> Boundary.isIn(player, Boundary.CORPOREAL_BEAST_LAIR)).collect(Collectors.toList());
//		if (targets.isEmpty()) {
//			if (core != null)
//				core.kill();
//			npc.getHealth().reset();
//		}
//		if (!targets.isEmpty()) {
//			boolean playersUnder = targets.stream().filter(player -> !player.asPlayer().isDead).anyMatch(player -> player.withinDistanceOfCenter(npc, 1));
//			if (playersUnder && stompDelay <= 0) {
//				targets.stream()
//				.filter(player -> player.withinDistance(npc, 1)).
//				forEach(player ->
//				handleHit(npc, target, CombatType.MELEE, null, null, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MELEE, 51), 1)));	
//				npc.startAnimation(1686);
//				npc.gfx100(318);
//				stompDelay = 5;
//			}
//		}
//		stompDelay--;
//		if (Objects.nonNull(core) && !core.isDead) {
//			boolean onPlayer = targets.stream().anyMatch(p -> p.getLocation().withinDistance(core.getLocation(), 1));
//			if (!onPlayer && core.getTargetingDelay() == 0) {
//				Entity random = Misc.randomTypeOfList(targets);
//				if (random != null) {
//					final Location loc = random.getLocation();
//					if (Boundary.isIn(random.getLocation(), Boundary.CORPOREAL_BEAST_LAIR)) {
//						sendProjectile(random, core.getLocation(), random.getLocation(), DARK_CORE_PROJ);
//						CycleEventHandler.getSingleton().addEvent(npc, new CycleEvent() {
//							
//							int duration = 0;
//							
//							@Override
//							public void execute(CycleEventContainer container) {								
//								if (duration == 1) {
//									core.teleport(2973, 4387, 2);
//								} else if (duration == 3) {
//									if (Boundary.isIn(loc, Boundary.CORPOREAL_BEAST_LAIR)) {
//										core.teleport(loc.getX(), loc.getY(), loc.getZ());	
//									} else {
//										core.kill();
//									}
//									container.stop();
//								}
//								duration++;
//							}
//							
//						}, 1);
//						core.setTargetingDelay(5);
//					}
//				}
//			} else {
//				if (core.getTargetingDelay() == 0) {
//					PlayerHandler.nonNullStream().filter(player -> !player.isDead && player.getLocation().withinDistance(core.getLocation(), 1)).forEach(player -> {
//						int healing = Misc.random(5) + 5;
//						int damage = Misc.random(1, 13);
//						handleHit(npc, target, CombatType.SPECIAL, null, null, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.SPECIAL, damage), 1));
//						npc.getHealth().increase(healing);
//					});
//					core.setTargetingDelay(core.getHealth().getStatus().equals(HealthStatus.POISON) ? 10 : 3);
//				}
//			}
//		}		
//		super.process(npc, target);
//	}
//	
//	@Override
//	public void handleRecievedHit(NPC npc, Entity source, Damage damage) {
//		if (Objects.nonNull(source)) {
//			if (damage.getEquipment().isPresent() && source.isPlayer()) {	
//				ItemDefinition weapon = ItemDefinition.forId(damage.getEquipment().get()[source.asPlayer().playerWeapon]);
//				String name = weapon.getName().toLowerCase();
//				if (!name.contains("spear") && !name.contains("halberd") && !name.contains("hasta")
//						&& !name.contains("decimat") && !name.contains("annih") && !name.contains("obliterat") && !name.contains("boogie")) {
//					damage.setAmount((int) (damage.getAmount() * 0.5));
//				}
//			}
//			if (source.isPlayer()) {
//				source.asPlayer().corpDamage += damage.getAmount();
//			}
//			if (damage.getAmount() >= 32 && source.isPlayer()) {
//				npc.killerId = source.getIndex(); //switch target to player who hit >= 32 damage
//				if (Misc.random(1, 8) == 1) {
//					if (core == null || core.isDead) {
//						core = NPCHandler.spawnNpc(320, npc.getCenter().getX(), npc.getCenter().getY(), npc.getHeight(), 0, 25, 13, 1, 10);
//					}
//				}
//			}
//		}
//	}
//	
//	@Override
//	public void handleDeath(NPC npc, Entity source) {
//		if (Objects.nonNull(core) && !core.isDead) {
//			core.kill();
//		}
//	}
//	
//	@Override
//	public boolean isAggressive(NPC npc) {
//		return true;
//	}
//
//	@Override
//	public int getAttackDistance(NPC npc) {
//		return 16;
//	}
//
//}
