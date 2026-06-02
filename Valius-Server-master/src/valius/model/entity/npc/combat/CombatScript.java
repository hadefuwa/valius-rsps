/**
 * 
 */
package valius.model.entity.npc.combat;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import valius.Config;
import valius.clip.PathChecker;
import valius.clip.Region;
import valius.content.SkillcapePerks;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.Location;
import valius.model.entity.Entity;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCClipping;
import valius.model.entity.npc.NPCDumbPathFinder;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Hitmark;
import valius.model.entity.player.skills.Skill;
import valius.util.Misc;

/**
 * @author ReverendDread
 * Mar 9, 2019
 */
@Slf4j
@Getter
@Setter
public abstract class CombatScript {
	
	/**
	 * If the npc can attack back.
	 */
	private boolean canAttack = true;
	
	/**
	 * Handles an attack for an {@link NPC} vs another {@link Entity}.
	 * 
	 * @param npc
	 * 			the npc.
	 * @param source
	 * 			the target.
	 * @return
	 * 			the delay of the attack in ticks.
	 */
	public abstract int attack(final NPC npc, final Entity target);
	
	/**
	 * Gets the attack distance for the npc.
	 * 
	 * @param npc
	 * 			the npc.
	 * @return
	 * 			the attack distance.
	 */
	public abstract int getAttackDistance(final NPC npc);
	
	/**
	 * If the npc ignores projectile clipping, to prevent safe spotting.
	 * 
	 * @return
	 * 			true to ignore clipping, false otherwise.
	 */
	public boolean ignoreProjectileClipping() {
		return false;
	}
	
	/**
	 * Gets the max follow distance before returning to respawn tile area.
	 * 
	 * @param npc
	 * 			the npc.
	 * @return
	 */
	public int getFollowDistance(final NPC npc) {
		return 16;
	}
	
	/**
	 * If the npc always follows the target.
	 * @param npc TODO
	 * @return
	 */
	public boolean followClose(NPC npc) {
		return false;
	}
	
	/**
	 * If script will ignore npc collision so player can flinch it.
	 * @return
	 */
	public boolean ignoreCollision() {
		return true;
	}
	
	/**
	 * Handles the death of the npc.
	 * @param npc
	 * 			the npc that is dying.
	 * @param killer
	 * 			the killer of the npc.
	 */
	public void handleDeath(NPC npc, Entity killer) {

	}
	
	/**
	 * Called upon initializing the script.
	 * @param npc
	 * 			the npc.
	 */
	public void init(NPC npc) {
		
	}
	
	/**
	 * Handles melee hits for npcs.
	 * 
	 * @param npc
	 * 			the npc.
	 * @param target
	 * 			the target.
	 * @param projectile 
	 * 			the projectile.
	 * @param hit
	 * 			the hit.
	 */
	public final void handleHit(final NPC npc, final Entity target, CombatType type, Projectile projectile, Graphic endGraphic, Hit hit, Runnable afterhit) {
		//System.out.println("handleHit: npc - " + npc.npcType + ", type - " + type.name() + ", hit - " + hit.getDamage());
		sendProjectile(npc, target, projectile);
		npc.attackType = type;
		applyHitmark(npc, target, type, hit, endGraphic);
		if (afterhit != null) {
			CycleEventHandler.getSingleton().addEvent(new CycleEventContainer(0, npc, (container) -> {
				afterhit.run();
				container.stop();
			}, hit.getDelay()));
		}
		
	}
	
	/**
	 * Handles melee hits for npcs.
	 * 
	 * @param npc
	 * 			the npc.
	 * @param target
	 * 			the target.
	 * @param projectile 
	 * 			the projectile.
	 * @param hit
	 * 			the hit.
	 */
	public final void handleHit(final NPC npc, final Entity target, CombatType type, Projectile projectile, Graphic endGraphic, Hit hit) {
		handleHit(npc, target, type, projectile, endGraphic, hit, null);
	}
	
	/**
	 * Handles melee hits for npcs.
	 * 
	 * @param npc
	 * 			the npc.
	 * @param target
	 * 			the target.
	 * @param hit
	 * 			the hit.
	 */
	public final void handleHit(final NPC npc, final Entity target, CombatType type, Projectile projectile, Hit hit) {
		handleHit(npc, target, type, projectile, null, hit, null);
	}
	
	/**
	 * Handles melee hits for npcs.
	 * 
	 * @param npc
	 * 			the npc.
	 * @param target
	 * 			the target.
	 * @param hit
	 * 			the hit.
	 */
	public final void handleHit(final NPC npc, final Entity target, CombatType type, Graphic endGraphic, Hit hit) {
		handleHit(npc, target, type, null, endGraphic, hit, null);
	}
	
	/**
	 * Handles melee hits for npcs.
	 * 
	 * @param npc
	 * 			the npc.
	 * @param target
	 * 			the target.
	 * @param hit
	 * 			the hit.
	 */
	public final void handleHit(final NPC npc, final Entity target, CombatType type, Hit hit) {
		handleHit(npc, target, type, null, null, hit, null);
	}
	
	/**
	 * Checks attack distance for npc, can be overriden for special cases such as multi combat type scripts.
	 * @return
	 */
	public void attackStyleChange(final NPC npc, final Entity target) {
	
	}
	
	/**
	 * Handles a received hit from a source entity.
	 * @param npc
	 * 			the npc.
	 * @param source
	 * 			the hit source.
	 */
	public void handleRecievedHit(final NPC npc, final Entity source, final Damage damage) {
		
	}
	
	/**
	 * Gets the monsters damage reduction amount.
	 * @return
	 */
	public double getDamageReduction(final NPC npc) {
		return 1.0;
	}
	
	/**
	 * If the npc is aggressive or not.
	 * @param npc TODO
	 * @return
	 */
	public boolean isAggressive(NPC npc) {
		return false;
	}
	
	/**
	 * Applies the hit info to the npc's next attack.
	 * 
	 * @param npc
	 * 			the npc.
	 * @param type TODO
	 * @param hit
	 * 			the hit.
	 */
	public final void applyHitmark(final NPC npc, final Entity target, CombatType type, final Hit hit, final Graphic graphic) {
		if (hit != null && npc.getLocation().withinDistance(new Location(target.getX(), target.getY(), target.getHeight()), 32)) {
			CycleEvent damageEvent = new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (target.getHealth().getAmount() - hit.getDamage() < 0) {
						hit.setDamage(target.getHealth().getAmount());
					}
					if (target.isPlayer()) {
						target.asPlayer().logoutDelay = System.currentTimeMillis();
						if (!hit.isIgnoresPrayer() && ((target.asPlayer().protectingMagic() && type == CombatType.MAGE) || 
								(target.asPlayer().protectingMelee() && type == CombatType.MELEE) || 
									(target.asPlayer().protectingRange() && type == CombatType.RANGE))) {
							hit.setDamage(0);
						}
					}
					if (hit.getDamage() > 0) {
						
						if (target.isPlayer()) {
							

							//Dark lord armor
							if (target.asPlayer().getItems().isWearingItem(33795) && target.asPlayer().getItems().isWearingItem(33794) && target.asPlayer().getItems().isWearingItem(33793)) {
								if (Misc.linearSearch(Config.REV_IDS, npc.npcType) != -1) {
									target.asPlayer().appendDamage(0, Hitmark.MISS);
									container.stop();
									return;
								}
							} 
							
							//Bracelet of ethereum
							else if (target.asPlayer().getItems().isWearingItem(21816)) {
								if (target.asPlayer().ethereumCharge <= 0) {
									target.asPlayer().getItems().deleteEquipment(21816, 9);
									target.asPlayer().getItems().wearItem(21817, 1, 9);
								} else if (Misc.linearSearch(Config.REV_IDS, npc.npcType) != -1) {
									target.asPlayer().ethereumCharge--;
									target.asPlayer().appendDamage(0, Hitmark.MISS);
									container.stop();
									return;
								}
							}
							
						}
						
						target.appendDamage(hit.getDamage(), (hit.getDamage() > 0 ? hit.getType() : Hitmark.MISS));
						target.addDamageTaken(npc, hit.getDamage());
					}	
					container.stop();
				}		
			};
			CycleEventHandler.getSingleton().addEvent(new CycleEventContainer(0, npc, damageEvent, hit.getDelay()));
			if (graphic != null) {
				//Handles end graphics being sent when hit is received.
				CycleEvent gfxEvent = new CycleEvent() {
		
					@Override
					public void execute(CycleEventContainer container) {
						if (target.isPlayer()) {
							target.asPlayer().gfx(graphic.getId(), graphic.getHeight());
						} else {
							target.asNPC().gfx100(graphic.getId(), graphic.getHeight());
						}
						container.stop();
					}
					
				};
				//Add the cycle event
				CycleEventHandler.getSingleton().addEvent(new CycleEventContainer(0, npc, gfxEvent, hit.getDelay()));
			}
		}
		//Ring of life effect.
		if (target.isPlayer()) {
			int hitpoints = target.asPlayer().getHealth().getAmount() - hit.getDamage();
			if (hitpoints > 0 && hitpoints < target.asPlayer().getHealth().getMaximum() / 10) {
				boolean defenceCape = SkillcapePerks.DEFENCE.isWearing(target.asPlayer());
				boolean maxCape = SkillcapePerks.isWearingMaxCape(target.asPlayer());
				if (target.asPlayer().getItems().isWearingItem(2570) || defenceCape || (maxCape && target.asPlayer().getRingOfLifeEffect())) {
					if (System.currentTimeMillis() - target.asPlayer().teleBlockDelay < target.asPlayer().teleBlockLength) {
						target.asPlayer().sendMessage("The ring of life effect does not work as you are teleblocked.");
						return;
					}
					if (defenceCape || maxCape) {
						target.asPlayer().sendMessage("Your cape activated the ring of life effect and saved you!");
					} else {
						target.asPlayer().getItems().deleteEquipment(2570, target.asPlayer().playerRing);
						target.asPlayer().sendMessage("Your ring of life saved you!");
					}
					target.asPlayer().getPA().spellTeleport(3087, 3499, 0, false);
				}
			}
		}
	}
	
	/**
	 * Handles combat processing for combat scripts.
	 * 
	 * @param npc
	 * 			the npc.
	 * @param target
	 * 			the target, can be null
	 */
	public void process(final NPC npc, final Entity target) {
		if (Objects.isNull(target))
			return;
		handleFollowing(npc, target);
		attackStyleChange(npc, target);
	}
	
	/**
	 * Handles special following for combat scripts.
	 * 
	 * @param npc
	 * 			the npc.
	 * @param target
	 * 			the target.
	 */
	public void handleFollowing(final NPC npc, final Entity target) {
		
		if (Objects.isNull(target))
			return;
		
		npc.randomWalk = false;
		
		if (!isCanAttack())
			return;
		
		int size = ignoreCollision() ? 1 : npc.getSize();
		boolean collides = target.collides(npc.getX(), npc.getY(), size, target.getX(), target.getY(), 1);
		
		if (collides) {
			stepAway(npc);
		}
		
		if (!followClose(npc) && npc.getDistance(target.getX(), target.getY()) <= ((double) getAttackDistance(npc)) + (npc.getSize() > 1 ? 0.5 : 0.0)) {
			return;
		}
		
		if ((npc.getX() < (npc.makeX + getFollowDistance(npc)) && npc.getX() > (npc.makeX - getFollowDistance(npc)) &&
			npc.getY() < (npc.makeY + getFollowDistance(npc)) && npc.getY() > (npc.makeY - getFollowDistance(npc)))) {
			if (npc.getHeight() == target.getHeight()) {
				NPCDumbPathFinder.follow(npc, target);
			}
		} else {
			npc.faceEntity(0);
			npc.randomWalk = true;
			npc.underAttack = false;
			//TODO make npc walk back to respawn tile when away from it, when it loses target.
		}
	}
	
	/**
	 * Handles an attack that is dodgeable by the target if moved.
	 * 
	 * @param npc
	 * 			the npc.
	 * @param type TODO
	 * @param projectile
	 * 			the projectile.
	 * @param graphic
	 * 			the hit gfx.
	 * @param hit
	 * 			the hit.
	 * @param targets
	 * 			the targets.
	 */
	public final void handleDodgableAttack(final NPC npc, final Entity target, CombatType type, final Projectile projectile, final Graphic graphic, final Hit hit) {
		if (npc == null || target == null)
			return;	
		final Location hitLoc = new Location(target.getX(), target.getY(), target.getHeight());
		if (target.isPlayer()) {
			sendProjectileToTile(npc, target, projectile);
		}
		CycleEvent event = new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				if (target.getX() != hitLoc.getX() || target.getY() != hitLoc.getY() || target.getHeight() != hitLoc.getZ()) {
					container.stop();
					return;
				}
				hit.setDelay(2);
				applyHitmark(npc, target, type, hit, graphic);
				container.stop();
			}
			
		};
		int sync = projectile.getHitSyncToTicks(npc.getLocation(), new Location(target.getX(), target.getY(), target.getHeight()));
		CycleEventHandler.getSingleton().addEvent(new CycleEventContainer(0, npc, event, sync));
	}
	
	/**
	 * Handles an attack that is dodgeable by the target if moved.
	 * 
	 * @param npc
	 * 			the npc.
	 * @param type TODO
	 * @param projectile
	 * 			the projectile.
	 * @param graphic
	 * 			the hit gfx.
	 * @param hit
	 * 			the hit.
	 * @param targets
	 * 			the targets.
	 */
	public final void handleDodgableAttack(final NPC npc, final Entity target, CombatType type, final Projectile projectile, final Graphic graphic, final Hit hit, CycleEvent onhit) {
		if (npc == null || target == null)
			return;	
		final Location hitLoc = target.getLocation();
		if (target.isPlayer()) {
			sendProjectileToTile(npc, target, projectile);
		}
		CycleEvent damage = new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				if (!target.getLocation().equals(hitLoc)) {
					container.stop();
					return;
				}
				hit.setDelay(0);
				applyHitmark(npc, target, type, hit, graphic);
				container.stop();				
			}
			
		};
		CycleEventHandler.getSingleton().addEvent(-1, npc, damage, hit.getDelay());
		CycleEventHandler.getSingleton().addEvent(-1, npc, onhit, 1);
	}
	
	/**
	 * Handles stepping away.
	 * 
	 * @param npc
	 * 			the npc.
	 */
	public final void stepAway(final NPC npc) {
		int[][] points = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
		for (int[] delta : points) {
			int dir = NPCClipping.getDirection(delta[0], delta[1]);
			if (NPCDumbPathFinder.canMoveTo(npc, dir)) {
				NPCDumbPathFinder.walkTowards(npc, npc.getX() + NPCClipping.DIR[dir][0], npc.getY() + NPCClipping.DIR[dir][1]);
				break;
			}
		}
	}
	
	/**
	 * Sends a projectile from the npc to the target.
	 * 
	 * @param npc
	 * @param target
	 * @param projectile
	 * TODO finish projectiles
	 */
	public void sendProjectile(final Entity from, final Entity to, Projectile projectile) {
		if (projectile == null || !from.isPlayer())
			return;
		int offX = (to.getX() - from.getX()) * -1;
		int offY = (to.getY() - from.getY()) * -1;
		int size = to.isNPC() ? (int) Math.ceil((double) to.asNPC().getSize() / 2.0) : 1;
		int centerX = from.getX() + size;
		int centerY = from.getY() + size;
		from.asPlayer().getPA().createPlayersProjectile(centerX, centerY, offX, offY, projectile.getAngle(), projectile.getSpeed(), projectile.getGfx(), projectile.getStartHeight(), projectile.getEndHeight(), -to.getIndex() - 1, 65, projectile.getDelay());  
	}
	
	/**
	 * Sends a projectile from the npc to the target.
	 * 
	 * @param npc
	 * @param target
	 * @param projectile
	 * TODO finish projectiles
	 */
	public void sendProjectile(final NPC npc, final Entity target, Projectile projectile) {
		if (projectile == null || !target.isPlayer())
			return;
		int size = (int) Math.ceil((double)npc.getSize() / 2.0);
		int centerX = npc.getX() + size;
		int centerY = npc.getY() + size;
		int offX = (npc.getX() - target.getX()) * -1;
		int offY = (npc.getY() - target.getY()) * -1;
		target.asPlayer().getPA().createPlayersProjectile(centerX, centerY, offX, offY, projectile.getAngle(), projectile.getSpeed(), projectile.getGfx(), projectile.getStartHeight(), projectile.getEndHeight(), -target.getIndex() - 1, 65, projectile.getDelay());  
	}
	
	/**
	 * Sends a projectile from the npc to the targets location.
	 * @param npc
	 * @param target
	 * @param projectile
	 */
	public void sendProjectileToTile(final NPC npc, final Entity target, Projectile projectile) {
		if (projectile == null || !target.isPlayer()) 
			return;
		final Location loc = new Location(target.getX(), target.getY(), target.getHeight());
		int size = (int) Math.ceil((double) npc.getSize() / 2.0);
		int centerX = npc.getX() + size;
		int centerY = npc.getY() + size;
		int offsetY = (npc.getX() - loc.getX()) * -1;
		int offsetX = (npc.getY() - loc.getY()) * -1;
		target.asPlayer().getPA().createPlayersProjectile(centerX, centerY, offsetX, offsetY, projectile.getAngle(), projectile.getSpeed(), projectile.getGfx(), projectile.getStartHeight(), projectile.getEndHeight(), -1, 65, projectile.getDelay());
	}
	
	/**
	 * Sends a projectile from a location to another location.
	 * @param npc
	 * @param target
	 * @param projectile
	 */
	public void sendProjectile(final Entity creator, final Location from, final Location target, Projectile projectile) {
		if (projectile == null || !creator.isPlayer()) 
			return;
		int centerX = from.getX();
		int centerY = from.getY();
		int offsetX = (centerY - (target.getY())) * -1;
		int offsetY = (centerX - (target.getX())) * -1;
		creator.asPlayer().getPA().createPlayersProjectile(centerX, centerY, offsetX, offsetY, projectile.getAngle(), projectile.getSpeed(), projectile.getGfx(), projectile.getStartHeight(), projectile.getEndHeight(), -1, 65, projectile.getDelay());
	}
	
	/**
	 * Sends a projectile from a location to another location.
	 * @param npc
	 * @param target
	 * @param projectile
	 */
	public void sendProjectile(final Entity creator, final Location from, final Entity target, Projectile projectile) {
		if (projectile == null || !creator.isPlayer()) 
			return;
		int centerX = from.getX();
		int centerY = from.getY();
		int offsetX = (centerY - (target.getY())) * -1;
		int offsetY = (centerX - (target.getX())) * -1;
		creator.asPlayer().getPA().createPlayersProjectile(centerX, centerY, offsetX, offsetY, projectile.getAngle(), projectile.getSpeed(), projectile.getGfx(), projectile.getStartHeight(), projectile.getEndHeight(), -target.getIndex() - 1, 65, projectile.getDelay());
	}
	
	/**
	 * Sends a projectile from the npc to the targets location.
	 * @param npc
	 * @param target
	 * @param projectile
	 */
	public void sendProjectileToTile(final NPC npc, final Entity target, final Location location, Projectile projectile) {
		if (projectile == null || !target.isPlayer()) 
			return;
		int size = (int) Math.ceil((double)npc.getSize() / 2.0);
		int centerX = npc.getX() + size;
		int centerY = npc.getY() + size;
		int offsetX = (centerY - location.getY()) * -1;
		int offsetY = (centerX - location.getX()) * -1;
		target.asPlayer().getPA().createPlayersProjectile(centerX, centerY, offsetX, offsetY, projectile.getAngle(), projectile.getSpeed(), projectile.getGfx(), projectile.getStartHeight(), projectile.getEndHeight(), -1, 65, projectile.getDelay());
	}
	
	/**
	 * Handles attacking for an npc.
	 * @param npc
	 * 			the npc.
	 * @param target
	 * 			the target.
	 * @return
	 * 			if the attack can be performed.
	 */
	public final boolean handleAttack(final NPC npc, final Entity target) {
		if (!checkConditions(npc, target)) {
			return false;
		}
		//Distance from target to npc.
		int distance = target.distanceToPoint(npc.getX(), npc.getY());
		boolean hasDistance = npc.getDistance(target.getX(), target.getY()) <= ((double) getAttackDistance(npc)) + (npc.getSize() > 1 ? 0.5 : 0.0);
		if (ignoreProjectileClipping()) {
			if (distance < 10) {
				if (target.isPlayer()) { //Player target
					target.asPlayer().getPA().removeAllWindows();
					npc.oldIndex = target.getIndex();
					target.asPlayer().underAttackBy2 = npc.getIndex();
					target.asPlayer().singleCombatDelay2 = System.currentTimeMillis();
				} else { //NPC target TODO
					
				}
			}
		}
		if (hasDistance) {
			if (npc.attackType == CombatType.MAGE || npc.attackType == CombatType.RANGE) {
				if (!PathChecker.isProjectilePathClear(npc.getX(), npc.getY(), npc.getHeight(), target.getX(), target.getY())
						|| !PathChecker.isProjectilePathClear(target.getX(), target.getY(), npc.getHeight(), npc.getX(), npc.getY())) {
					return false;
				}
			} else if (npc.attackType == null || npc.attackType == CombatType.MELEE) {
				if (!PathChecker.isMeleePathClear(npc.getX(), npc.getY(), npc.getHeight(), target.getX(), target.getY())) {
					return false;
				}
			}
			npc.faceEntity(target.getIndex());
			npc.attackTimer = attack(npc, target);
		}
		return true;
	}
	
	/**
	 * Checks the preconditions to initiating combat.
	 * @param npc
	 * 			the npc.
	 * @param target
	 * 			the target.
	 * @return
	 * 			true if combat can be initiated, false otherwise.
	 */
	public final boolean checkConditions(final NPC npc, final Entity target) {
		boolean isPlayer = target.isPlayer();
		if (npc.isDead || npc.getHealth().getAmount() <= 0) {
			resetCombat(npc);
			return false;
		}
		if (npc.getInstance() != target.getInstance()) {
			return false;
		}
		if (!isCanAttack()) {
			return false;
		}
		if (isPlayer && target.asPlayer().isInvisible()) {
			return false;
		}
		if (isPlayer && target.asPlayer().getBankPin().requiresUnlock()) {
			target.asPlayer().getBankPin().open(2);
			return false;
		}
		if (npc.inMulti() && npc.underAttackBy > 0 && npc.underAttackBy != target.getIndex()) {
			resetCombat(npc);
			return false;
		}
		if (npc.inMulti() && (isPlayer && (target.asPlayer().underAttackBy > 0 && target.asPlayer().underAttackBy2 != npc.getIndex())
				|| (isPlayer && (target.asPlayer().underAttackBy2 > 0 && target.asPlayer().underAttackBy2 != npc.getIndex())))) {
			resetCombat(npc);
			return false;
		}
		if (npc.getHeight() != target.getHeight()) {
			resetCombat(npc);
			return false;
		}
		return true;
	}
	
	/**
	 * Creates a list of possible targets.
	 * @param playersOnly
	 * 			if only players are allowed to be targeted.
	 * @return
	 */
	public final List<Entity> getPossibleTargets(final NPC npc, boolean playersOnly) {
		List<Entity> targets = PlayerHandler.nonNullStream()
				.filter(player -> !player.isDead && player.getHealth().getAmount() > 0)
				.filter(player -> player.withinDistance(npc, 20))
				.filter(player -> ignoreCollision() || Region.isPathClear(npc.getX(), npc.getY(), npc.getHeight(), player.getX(), player.getY()))
				.collect(Collectors.toList());
	
		if (!playersOnly) {
			for (NPC n : NPCHandler.npcs) {
				if (n == null || n.isDead || !n.withinDistance(npc, 20))
					continue;
				if (ignoreCollision() || Region.isPathClear(npc.getX(), npc.getY(), npc.getHeight(), n.getX(), n.getY())) {
					targets.add(n);
				}
			}
		}
		return targets;
	}
	
	/**
	 * Resets the npcs combat.
	 * @param npc
	 * 			the npc.
	 */
	public final void resetCombat(final NPC npc) {
		npc.killerId = 0;
		npc.faceEntity(0);
		npc.underAttack = false;
		npc.lastRandomlySelectedPlayer = 0;
	}
	
	/**
	 * Gets a random max hit using calculated defensive stats.
	 * @param npc
	 * 			the npc.
	 * @param target
	 * 			the target.
	 * @param type
	 * 			the combat type.
	 * @param maxhit
	 * 			the max hit.
	 * @return
	 */
	public final int getRandomMaxHit(final NPC npc, final Entity target, CombatType type, int maxhit) {
		double attack = npc.attack; //npc.getStats().getAttackForStyle(npc, type);
		double defence;
		if (target.isPlayer()) { //player as target
			Player player = target.asPlayer();
			int def = 0;
			switch (type) {
				case MAGE:
					def = player.getCombat().mageDef();
					break;
				case MELEE:
					def= player.getCombat().calculateMeleeDefence();
					break;
				case RANGE:
					def = player.getCombat().calculateRangeDefence();
					break;
				default:
					break;			
			}
			defence = player.getSkills().getLevel(Skill.DEFENCE) + (2 * def);
		} else { //npc as target
			NPC n = target.asNPC();
			defence = n.defence; //n.getStats().getDefenceForStyle(npc, type);
		}
		double probability = (attack / defence) * 100;
		if (probability > 0.90)
			probability = 0.90;
		else if (probability < 0.05)
			probability = 0.05;
		if (probability < Math.random())
			return 0;
		return Misc.random(maxhit); //experiment with using magic str possibly?
	}
	
	/**
	 * Creates a new instance of this class
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public CombatScript newInstance() throws InstantiationException, IllegalAccessException {
		return this.getClass().newInstance();
	}
	
}
