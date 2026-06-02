package valius.content.cannon;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import valius.clip.PathChecker;
import valius.clip.Region;
import valius.model.Location;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.combat.AttackNPC;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Hitmark;
import valius.world.World;
import valius.world.objects.GlobalObject;

public class DwarfCannon {
	
	private static final int CANNON_RANGE = 12;

	public static final int CANNONBALL = 2;
	
	public static final int CANNON_BASE = 6;
	public static final int CANNON_STAND = 8;
	public static final int CANNON_BARRELS = 10;
	public static final int CANNON_FURNACE = 12;
	
	public static final int CANNON_OBJECT_ID = 6;
	public static final int COLLAPSED_CANNON_ID = 11869;
	
	private long logoutTime = -1;
	
	private Player player;
	
	public DwarfCannon(Player player) {
		this.player = player;
	}
	
	private GlobalObject cannon;
	private Location cannonLoc;
	private CannonDirection direction;
	private long setupTime = -1;
	private int fuel;
	private boolean broken;
	private boolean needsDestroy;
	
	public void destroy() {
		needsDestroy = true;
	}
	
	public void process() {
		if(cannon == null)
			return;
		if(broken || setupTime == -1) {
			if(direction != CannonDirection.NORTH) {
				sendAnimation();
				direction = direction.next();
			}
		
			if(broken && cannon.getObjectId() != COLLAPSED_CANNON_ID) {
				World.getWorld().getGlobalObjects().remove(cannon);
				cannonLoc = cannonLoc.translate(1, 1, 0);
				cannon = new GlobalObject(COLLAPSED_CANNON_ID, cannonLoc.getX(), cannonLoc.getY(), cannonLoc.getZ(), 0);
				direction = CannonDirection.NORTH;
				World.getWorld().getGlobalObjects().add(cannon);
			}
			return;
		}
		if (TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - setupTime) >= 25 || (logoutTime != -1 && TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - logoutTime) >= 5) || needsDestroy) {
			broken = true;
			needsDestroy = false;
			sendAnimation();
			player.getPA().sendStillGraphics(1180, 0, cannonLoc.getY() + 1, cannonLoc.getX() + 1, 4);
			player.sendMessage("Your cannon has collapsed!");
			return;
		}
		if(fuel <= 0) {
			fuel = 0;

			if(direction != CannonDirection.NORTH) {
				sendAnimation();
				direction = direction.next();
			}
			return;
		}
		Optional<NPC> validTarget = Optional.empty();
		if(!player.inMulti() && player.underAttackBy2 > 0) {
			validTarget = Optional.ofNullable(NPCHandler.getNpcForIndex(player.underAttackBy2));
			if(validTarget.isPresent()) {
				if(!direction.validArea(cannonLoc, validTarget.get().getLocation().center(validTarget.get().getSize()))) {
					validTarget = Optional.empty();
				}
			}
		} else {
			validTarget = NPCHandler.nonNullStream().filter(npc -> npc.getLocation().withinDistance(cannonLoc, CANNON_RANGE))
					.filter(npc -> npc.getDefinition().getNpcCombat() > 0 && npc.getDefinition().getNpcHealth() > 0)
					.filter(npc -> !npc.isDead)
					.filter(npc -> direction.validArea(cannonLoc, npc.getLocation().center(npc.getSize())))
					.filter(npc -> PathChecker.isProjectilePathClear(cannonLoc.getX(), cannonLoc.getY(), cannonLoc.getZ(), npc.getX(), npc.getY()))
					.findAny();
		}
				
		
		sendAnimation();
		validTarget.ifPresent(npc -> {
			int pX = cannonLoc.getX() + 1;
			int pY = cannonLoc.getY() + 1;
			Location npcCenter = npc.getLocation().center(npc.getSize());
			int nX = npcCenter.getX();
			int nY = npcCenter.getY();
			int offX = (pY - nY) * -1;
			int offY = (pX - nX) * -1;
			npc.updateRequired = true;
			npc.underAttackBy = player.getIndex();
			npc.lastDamageTaken = System.currentTimeMillis();
			npc.underAttack = true;
			fuel--;
			if(fuel <= 0) {
				player.sendMessage("@red@Your cannon has run out of ammunition.");
			}
			AttackNPC.calculateCombatDamage(player, npc, CombatType.CANNON, null);
			player.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 50, 53, 37, 37, 0, 5);
		});
		direction = direction.next();
	}
	
	public void sendAnimation() {
		int animId = broken && cannon != null && cannon.getObjectId() != COLLAPSED_CANNON_ID ? 471 : direction.getAnimationId();
			
		PlayerHandler.nonNullStream()
		.filter(otherPlr -> otherPlr.getLocation().withinDistance(cannonLoc, 40))
		.forEach(otherPlr -> {
			otherPlr.getPA().sendPlayerObjectAnimation(otherPlr, cannon.getX(), cannon.getY(), animId, cannon.getType(), cannon.getFace(), cannon.getHeight());
		
		});
	}
	
	public void placeCannon() {
		if(cannon != null) {
			player.sendMessage("You already have a cannon deployed!");
			return;
		}
		if(Boundary.isIn(player, Boundary.CANNON_NOT_ALLOWED)) {
			player.sendMessage("You can't set up a cannon here!");
			return;
		}
		boolean hasCannon = player.getItems().playerHasAllItems(CANNON_BASE, CANNON_STAND, CANNON_BARRELS, CANNON_FURNACE);
		if(hasCannon) {
			Location plrPos = player.getLocation().copy();
			boolean canPlace = plrPos.getSurrounding(1).stream().allMatch(loc -> !loc.getRegion().isBlocked(loc.getX(), loc.getY(), loc.getZ()) && !loc.getRegion().solidObjectExists(loc.getX(), loc.getY(), loc.getZ()));
			if(!canPlace) {
				player.sendMessage("You can't set up a cannon here!");
				return;
			}
			player.getItems().deleteItems(CANNON_BASE, CANNON_STAND, CANNON_BARRELS, CANNON_FURNACE);
			cannonLoc = player.getLocation().translate(-1, -1, 0);
			cannon = new GlobalObject(CANNON_OBJECT_ID, cannonLoc.getX(), cannonLoc.getY(), cannonLoc.getZ(), 0);
			World.getWorld().getGlobalObjects().add(cannon);
			direction = CannonDirection.NORTH;
			fuel = 0;
			setupTime = System.currentTimeMillis();
			broken = false;
			needsDestroy = false;
			CannonManager.register(player, this);
		}
	}
	
	public void addAmmo(GlobalObject object) {
		if(cannon == null)
			return;
	
		if(!object.equals(cannon)) {
			player.sendMessage("This is not your cannon.");
			return;
		}
		if(broken) {
			player.sendMessage("This cannon has collapsed.");
			return;
		}
		int fuelAmount = player.getItems().getItemAmount(CANNONBALL);
		int max = 30 - fuel;
		if(fuelAmount == 0) {
			player.sendMessage("You have run out of cannonballs");
			return;
		}
		if(fuelAmount > max)
			fuelAmount = max;
		
	
		player.startAnimation(827);
		player.getItems().deleteItem(CANNONBALL, fuelAmount);
		fuel += fuelAmount;
	}
	


	public void emptyFuel(GlobalObject object) {
		if(cannon == null)
			return;
		if(!object.equals(cannon)) {
			player.sendMessage("This is not your cannon.");
			return;
		}
		
		if(fuel <= 0) {
			player.sendMessage("Your cannon has no fuel!");
			return;
		}

		if(!player.getItems().playerHasItem(CANNONBALL) && player.getItems().freeSlots() < 1) {
			player.sendMessage("You don't have enough inventory space to do that.");
			return;
		}
		int fuelAmt = fuel;
		fuel = 0;
		player.startAnimation(827);
		player.getItems().addItem(CANNONBALL, fuelAmt);
	}

	
	public void pickupCannon(GlobalObject object) {
		if(cannon == null)
			return;
		if(!object.equals(cannon)) {
			player.sendMessage("This is not your cannon.");
			return;
		}
		int spaceRequired = 4;
		if(fuel > 0 && !player.getItems().playerHasItem(CANNONBALL))
			spaceRequired++;
		if(player.getItems().freeSlots() < spaceRequired) {
			player.sendMessage("You need at least " + spaceRequired + " free slots to do that.");
			return;
		}
		World.getWorld().getGlobalObjects().remove(cannon);
		player.startAnimation(827);
		cannon = null;
		player.getItems().addItem(CANNON_BASE, 1);
		player.getItems().addItem(CANNON_STAND, 1);
		player.getItems().addItem(CANNON_BARRELS, 1);
		player.getItems().addItem(CANNON_FURNACE, 1);
		if(fuel > 0) {
			player.getItems().addItem(CANNONBALL, fuel);
		}

		fuel = 0;
		broken = false;
		setupTime = -1;
	}

	public void onLogout() {
		this.logoutTime = System.currentTimeMillis();
	}

	public void onLogin(Player player) {
		this.player = player;
		this.logoutTime = -1;
	}
}
