package valius.model.entity.npc.combat.impl.tob;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

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
import valius.model.entity.npc.combat.Hit;
import valius.model.entity.npc.combat.Projectile;
import valius.model.entity.npc.combat.ScriptSettings;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.EquipmentSlot;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Hitmark;
import valius.model.minigames.theatre.Theatre;
import valius.util.Misc;
import valius.world.World;
import valius.world.objects.GlobalObject;

/**
 * 8369 is starting npc.
 * @author ReverendDread
 * Apr 20, 2019
 */
@ScriptSettings(
	npcIds = { 8369, 8370, 8371, 8372, 8373, 8374, 8375 }
)
public class Verzik extends CombatScript {
	
	private static final Location CENTRE = new Location(3167, 4311);
	private static final int OUT_OF_CHAIR = 8111;
	private static final int CHAIR_ATTACK = 8109;
	private static final Projectile ELECTRIC = new Projectile(1580, 100, 0, 0, 220, 0, 50);
	private static final Projectile BOMBS = new Projectile(1583, 100, 0, 0, 130, 0, 50);
	private static final Projectile NORMAL = new Projectile(1585, 100, 0, 0, 130, 0, 50);
	private static final Projectile SPIDER_PROJ = new Projectile(1586, 100, 0, 0, 130, 0, 50);
	private static final Projectile RANGED_PROJ = new Projectile(1560, 25, 30, 0, 100, 0, 50);
	private static final Projectile BLOOD_PROJ = new Projectile(1578, 50, 25, 0, 100, 0, 50);
	private static final Location SPIDER_SPAWN = new Location(3171, 4315);
	private static final Boundary ARENA = new Boundary(3154, 4303, 3182, 4322);
	private int bombCount = 0;
	private int electricCount = 0;
	
	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#init(ethos.model.entity.npc.NPC)
	 */
	@Override
	public void init(NPC npc) {
		npc.getHealth().resolveStatus(HealthStatus.POISON, Integer.MAX_VALUE);
		npc.getHealth().resolveStatus(HealthStatus.VENOM, Integer.MAX_VALUE);
		npc.setNoRespawn(true);
		npc.setNeverWalkHome(true);
		npc.attackType = CombatType.MAGE;
	}
	
	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#attack(ethos.model.entity.npc.NPC, ethos.model.entity.Entity)
	 */
	@Override
	public int attack(NPC npc, Entity target) {
		List<Entity> targets = getPossibleTargets(npc, true);
		final Location tile = target.getLocation();
		if (npc.npcType == 8370) {
			npc.startAnimation(CHAIR_ATTACK);
			for (Entity t : targets) {
				if (t == null || t.asPlayer().isDead || !t.withinDistance(npc, 32) || !Boundary.isIn(t, ARENA)) {
					continue;
				}
				final Location t_tile = t.getLocation();
				handleDodgableAttack(npc, t, CombatType.MAGE, ELECTRIC.setSpeed(220), null, new Hit(Hitmark.HIT, getRandomMaxHit(npc, t, CombatType.SPECIAL, 60), 7, true), new CycleEvent() {
	
					@Override
					public void execute(CycleEventContainer container) {			
						if (container.getTotalTicks() == 8) {
							target.asPlayer().getPA().createPlayersStillGfx(1582, t_tile.getX(), t_tile.getY(), 0, 0);
							Stream<Entity> ts = targets.stream().filter(n -> !n.equals(t) && n.getLocation().withinDistanceIgnoreHeight(t_tile, 1));
							ts.forEach(n -> {
								handleHit(npc, n, CombatType.MAGE, new Hit(Hitmark.HIT, getRandomMaxHit(npc, n, CombatType.SPECIAL, 60), 0, true));
							});
							container.stop();
						}
					}
					
				});		
			}
		}
		if (npc.npcType == 8372) {
			npc.startAnimation(8114);
			if (bombCount < 4) {
				for (Entity t : targets) {
					if (t == null || t.asPlayer().isDead || !t.withinDistance(npc, 32) || !Boundary.isIn(t, ARENA)) {
						continue;
					}
					final Location t_tile = t.getLocation();
					handleDodgableAttack(npc, t, CombatType.MAGE, BOMBS, null, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.SPECIAL, 60), 4, true), new CycleEvent() {
		
						@Override
						public void execute(CycleEventContainer container) {			
							if (container.getTotalTicks() == 5) {
								target.asPlayer().getPA().createPlayersStillGfx(1584, t_tile.getX(), t_tile.getY(), 0, 0);
								Stream<Entity> ts = targets.stream().filter(n -> !n.equals(t) && n.getLocation().withinDistanceIgnoreHeight(t_tile, 1));
								ts.forEach(t -> {
									handleHit(npc, t, CombatType.MAGE, new Hit(Hitmark.HIT, getRandomMaxHit(npc, t, CombatType.SPECIAL, 60), 0, true));
								});
								container.stop();
							}
						}			
						
					});		
				}
				bombCount++;
			} else {				
				if (electricCount < 2) {
					for (Entity t : targets) {					
						if (t == null || t.asPlayer().isDead || !t.withinDistance(npc, 32) || !Boundary.isIn(t, ARENA)) {
							continue;
						}
						handleHit(npc, t, CombatType.MAGE, NORMAL, new Hit(Hitmark.HIT, getRandomMaxHit(npc, t, CombatType.MAGE, target.getHealth().getAmount() < 2 ? 1 : 20), 3));
					}
					electricCount++;
					bombCount = 0;
				} else {
					handleDodgableAttack(npc, target, CombatType.MAGE, SPIDER_PROJ, null, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.SPECIAL, 60), 4), new CycleEvent() {
						
						NPC healer;
						NPC bomber;
						
						@Override
						public void execute(CycleEventContainer container) {			
							if (container.getTotalTicks() == 5 && healer == null && bomber == null) {
								healer = NPCHandler.spawnNpc(8384, tile.getX(), tile.getY(), npc.getHeight(), 0, 180, 11, 1, 1);
								healer.attackTimer = Integer.MAX_VALUE;
								healer.startAnimation(8079);
								healer.setNoRespawn(true);
								healer.faceEntity(npc);
								bomber = NPCHandler.spawnNpc(8385, SPIDER_SPAWN.getX(), SPIDER_SPAWN.getY(), npc.getHeight(), 0, 200, 11, 1, 1);
								bomber.killerId = target.getIndex();
								bomber.startAnimation(8098);
								bomber.setNoRespawn(true);						
							}
							if (container.getTotalTicks() >= 5 && healer != null) {
								if (healer.getHealth().getAmount() < healer.getHealth().getMaximum()) {
									healer.appendDamage(1000, Hitmark.HIT);
								}
							}
							if (container.getTotalTicks() >= 15) {
								if (bomber != null)
									bomber.appendDamage(1000, Hitmark.HIT);
								if (healer != null)
									healer.appendDamage(1000, Hitmark.HIT);
								container.stop();
							}
							if ((container.getTotalTicks() % 2 == 0) && healer != null && !healer.isDead) {
								sendProjectileToTile(healer, target, npc.getLocation(), BLOOD_PROJ);
								Damage heal = new Damage(6);
								heal.setHitmark(Hitmark.HEAL_PURPLE);
								npc.appendDamage(healer, heal);
							}			
						}		
						
					});
					electricCount = 0;
				}
			}
		}
		if (npc.npcType == 8374) {
			int random = Misc.random(0, 2);
			if (random == 0) {
				npc.startAnimation(8123);
				handleHit(npc, target, CombatType.MELEE, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.MELEE, 40), 2));
			} else if (random == 1) {
				npc.startAnimation(8124);
				for (Entity t : targets) {
					if (t == null || t.asPlayer().isDead || !Boundary.isIn(t, ARENA)) {
						continue;
					}
					handleHit(npc, t, CombatType.MAGE, ELECTRIC.setSpeed(100), new Hit(Hitmark.HIT, getRandomMaxHit(npc, t, CombatType.MAGE, 40), 2));
				}
			} else if (random == 2) {
				npc.startAnimation(8125);
				for (Entity t : targets) {
					if (t == null || t.asPlayer().isDead || !Boundary.isIn(t, ARENA)) {
						continue;
					}
					handleHit(npc, target, CombatType.RANGE, RANGED_PROJ, new Hit(Hitmark.HIT, getRandomMaxHit(npc, target, CombatType.RANGE, 40), 2));
				}
			}
		}
		return npc.npcType == 8373 || npc.npcType == 8370 || npc.npcType == 8374 ? 7 : 4;
	}
	
	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#process(ethos.model.entity.npc.NPC, ethos.model.entity.Entity)
	 */
	@Override
	public void process(NPC npc, Entity target) {
		if (npc.npcType == 8371 || npc.npcType == 8372) {
			if (!npc.getLocation().equalsIgnoreHeight(CENTRE)) {
				NPCDumbPathFinder.walkTowards(npc, CENTRE.getX(), CENTRE.getY(), true);
			} else {
				npc.requestTransform(8372);
			}
		}
		npc.freezeTimer = 0;
		super.process(npc, target);
	}
	
	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#attack(ethos.model.entity.npc.NPC, ethos.model.entity.Entity, ethos.model.entity.player.combat.Damage)
	 */
	@Override
	public void handleRecievedHit(NPC npc, Entity source, Damage damage) {
		if (npc.npcType == 8370) {
			damage.setHitmark(Hitmark.DAWNBRINGER);
		}
		if (npc.npcType == 8371 || npc.npcType == 8375) {
			damage.setHitmark(Hitmark.MISS);
			damage.setAmount(0);
		}
		if (npc.getHealth().getAmount() - damage.getAmount() <= 0) { //would of died
			if (transform(npc)) {
				npc.getHealth().setAmount(npc.getHealth().getMaximum());
			}
		}
	}
	
	private boolean transform(NPC npc) {
		if (npc.npcType == 8370) {
			List<Entity> targets = getPossibleTargets(npc, true);
			setCanAttack(false);
			npc.startAnimation(OUT_OF_CHAIR);
			npc.killerId = 0;
			npc.faceEntity(0);
			targets.forEach(t -> {
				t.asPlayer().getItems().deleteItem(22516, 28);
				if (t.asPlayer().getItems().isWearingItem(22516)) {
					t.asPlayer().getItems().deleteEquipment(-1, EquipmentSlot.WEAPON.getIndex());
				}
				t.asPlayer().getCombat().resetPlayerAttack();
			});
			CycleEvent event = new CycleEvent() {

				@Override
				public void execute(CycleEventContainer container) {
					npc.requestTransform(8371);
					World.getWorld().getGlobalObjects().add(new GlobalObject(32737, 3167, 4324, npc.asNPC().getHeight(), 0, 10));
					npc.startAnimation(-1);
					setCanAttack(true);
					container.stop();
				}
				
			};
			CycleEventHandler.getSingleton().addEvent(-1, npc, event, 4);
			return true;
		} else if (npc.npcType == 8372) {
			setCanAttack(false);
			npc.startAnimation(8119);
			npc.requestTransform(8374);
			CycleEvent event = new CycleEvent() {

				@Override
				public void execute(CycleEventContainer container) {
					npc.startAnimation(-1);
					npc.forceChat("Behold my true nature!");
					setCanAttack(true);
					container.stop();
				}
				
			};
			CycleEventHandler.getSingleton().addEvent(-1, npc, event, 4);
			return true;
		} else if (npc.npcType == 8374) {
			setCanAttack(false);
			npc.requestTransform(8375);
			World.getWorld().getGlobalObjects().add(new GlobalObject(32738, 3167, 4324, npc.getHeight(), 0, 10));
			List<Player> players = npc.getTheatreInstance().getPlayers();
			players.forEach(player -> {
				Theatre instance = player.getTheatreInstance();
				if (instance != null) {
					instance.getStopwatch().stop();
					player.sendMessage("You have completed Theate of Blood in "+instance.getTimeElapsed()+".");
				}
				player.sendMessage("You have defeated Verzik Vitur!");
				player.sendMessage("You now have "+player.asPlayer().theatrePoints+" points!");
				player.lastNpcAttacked = 0;
				/*
				 * pet chance
				 */
				int zikChance = Misc.random(1,500);
				if (zikChance == 250) {
					if (player.summonId == -1) {
						player.summonId = 8337;
					} else {
						player.getItems().addItemUnderAnyCircumstance(22473, 1);
					}
					GlobalMessages.send(player.playerName+" has received Lil Zik from Theatre of Blood!", GlobalMessages.MessageType.LOOT);
				}
				
				player.getCombat().resetPlayerAttack();
				
			});
			CycleEvent event = new CycleEvent() {

				@Override
				public void execute(CycleEventContainer container) {
					npc.actionTimer = 0;
					npc.isDead = true;
					npc.applyDead = true;
					npc.needRespawn = false;
					container.stop();
				}
				
			};
			CycleEventHandler.getSingleton().addEvent(-1, npc, event, 4);
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#getAttackDistance(ethos.model.entity.npc.NPC)
	 */
	@Override
	public int getAttackDistance(NPC npc) {
		return npc.npcType == 8374 ? 4 : 32;
	}
	
	@Override
	public int getFollowDistance(NPC npc) {
		return (npc.npcType == 8373 || npc.npcType == 8374) ? 32 : 0;
	}
	
	@Override
	public boolean ignoreProjectileClipping() {
		return true;
	}
	
	@Override
	public boolean isAggressive(NPC npc) {
		return npc.npcType == 8369 ? false : true;
	}
	
	@Override
	public boolean followClose(NPC npc) {
		return npc.npcType == 8374;
	}

}
