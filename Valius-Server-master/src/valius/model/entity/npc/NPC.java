package valius.model.entity.npc;

import java.awt.Point;

import lombok.Getter;
import lombok.Setter;
import valius.model.Location;
import valius.model.entity.Entity;
import valius.model.entity.HealthStatus;
import valius.model.entity.npc.bosses.zulrah.Zulrah;
import valius.model.entity.npc.combat.CombatScript;
import valius.model.entity.npc.combat.CombatScriptHandler;
import valius.model.entity.npc.combat.Hit;
import valius.model.entity.npc.combat.NPCCombatStats;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Hitmark;
import valius.util.Buffer;
import valius.util.Misc;
import valius.world.World;

public class NPC extends Entity {
	
	@Getter @Setter private CombatScript combatScript;

	@Getter private NPCCombatStats stats;
	
	// private Hitmark hitmark = null;
	// private Hitmark secondHitmark = null;
	public int npcType;
	public int summonedBy;
	public int prevX, prevY;
	public int makeX, makeY, minHit, maxHit, defence, attack, moveX, moveY, direction, walkingType;
	public int spawnX, spawnY;
	public int viewX, viewY;
	public int hp;
	public int lastX, lastY;
	public boolean summoner = false;
	public long singleCombatDelay = 0;
	public boolean teleporting = false;

	public long lastRandomlySelectedPlayer = System.currentTimeMillis();

	private boolean transformUpdateRequired;
	int transformId;
	public Location targetedLocation;

	/**
	 * attackType: 0 = melee, 1 = range, 2 = mage
	 */
	public long lastSpecialAttack;

	public boolean spawnedMinions;

	public CombatType attackType;

	public int projectileId, endGfx, spawnedBy, hitDelayTimer, hitDiff, animNumber, actionTimer, enemyX, enemyY;
	public boolean applyDead, isDead, needRespawn, respawns;
	public boolean walkingHome, underAttack;
	public int freezeTimer, attackTimer, killerId, killedBy, oldIndex, underAttackBy;
	public long lastDamageTaken;
	@Getter @Setter public int targetingDelay = 0;
	@Setter public boolean randomWalk;
	@Setter public boolean neverWalkHome;
	public boolean dirUpdateRequired;
	public boolean animUpdateRequired;
	public boolean hitUpdateRequired;
	public boolean updateRequired;
	public boolean forcedChatRequired;
	public boolean faceToUpdateRequired;
	public int firstAttacker;
	public String forcedText;
	public int stage;
	public int totalAttacks;
	private boolean facePlayer = true;
	private int projectileDelay = 0;
	@Getter @Setter public boolean running;

	private NPCDefinitions definition;

	private long lastRandomWalk;
	private long lastRandomWalkHome;

	private long randomWalkDelay;
	private long randomStopDelay;

	public NPC(int _npcId, int _npcType, NPCDefinitions definition) {
		super(_npcId, definition.getNpcName());
		this.definition = definition;
		npcType = _npcType;
		direction = -1;
		isDead = false;
		applyDead = false;
		actionTimer = 0;
		randomWalk = true;
		this.stats = NPCCombatStats.getStatsFor(_npcId);
		if (definition != null) {
			this.combatScript = CombatScriptHandler.getScript(definition);
			if (this.combatScript != null) {
				this.combatScript.init(this);
			}
		}		
	}

	public int followerMax() {
		if (npcType == 9)
			return 2;
		else
			return 1;
	}

	public int followerRange() {
		if (npcType == 9)
			return 2;
		else
			return 1;
	}

	public boolean AttackNPC() {// NPC VS NPC
		if (NPCHandler.npcs[index] == null)
			return false;
		int EnemyX = NPCHandler.npcs[index].getX();
		int EnemyY = NPCHandler.npcs[index].getY();
		int EnemyHP = NPCHandler.npcs[index].hp;

		int hitDiff = 0;
		turnNpc(EnemyX, EnemyY);
		hitDiff = Misc.random(followerMax());
		int hitTimer = 4000;
		int Player = 0;
		Player plr = (Player) PlayerHandler.players[Player];

		if (plr.goodDistance(EnemyX, EnemyY, getX(), getY(), followerRange()) == true) {
			if (System.currentTimeMillis() - lastHit > nextHit) {
				if (NPCHandler.npcs[index].isDead == true || NPCHandler.npcs[index].hp <= 0 || EnemyHP <= 0) {
					ResetAttackNPC();
				} else {
					if ((EnemyHP - hitDiff) < 0) {
						hitDiff = EnemyHP;
					}
					if (npcType == 9) {
						animNumber = 386;
						hitTimer = 2000;
					} else {
						hitTimer = 3500;
					}
					nextHit = hitTimer;
					lastHit = System.currentTimeMillis();
					animUpdateRequired = true;
					updateRequired = true;
					NPCHandler.npcs[index].hitDiff = hitDiff;
					NPCHandler.npcs[index].hp -= hitDiff;
					// World.getWorld().getNpcHandler().npcs[index].attackNpc = npcId;
					NPCHandler.npcs[index].updateRequired = true;
					NPCHandler.npcs[index].hitUpdateRequired = true;
					actionTimer = 7;
				}
				return true;
			}
		}
		return false;
	}

	public boolean ResetAttackNPC() {
		// isUnderAttackNpc = false;
		// isAttackingNPC = false;
		// attacknpc = -1;
		randomWalk = true;
		updateRequired = true;
		return true;
	}

	public long lastHit;
	public int nextHit;

	public void followNPC() {
		// int follow = followPlayer;
		int enemyX = NPCHandler.npcs[index].getX();
		int enemyY = NPCHandler.npcs[index].getY();
		turnNpc(enemyX, enemyY);
		randomWalk = false;
		if (enemyY == getY() && enemyX == getX()) {
			moveX = World.getWorld().getNpcHandler().GetMove(getX(), enemyX);
			moveY = World.getWorld().getNpcHandler().GetMove(getY(), enemyY + 1);
		}
		if (enemyY < getY()) {
			moveX = World.getWorld().getNpcHandler().GetMove(getX(), enemyX);
			moveY = World.getWorld().getNpcHandler().GetMove(getY(), enemyY + 1);
		} else if (enemyY > getY()) {
			moveX = World.getWorld().getNpcHandler().GetMove(getX(), enemyX);
			moveY = World.getWorld().getNpcHandler().GetMove(getY(), enemyY - 1);
		} else if (enemyX < getX()) {
			moveX = World.getWorld().getNpcHandler().GetMove(getX(), enemyX + 1);
			moveY = World.getWorld().getNpcHandler().GetMove(getY(), enemyY);
		} else if (enemyX > getX()) {
			moveX = World.getWorld().getNpcHandler().GetMove(getX(), enemyX - 1);
			moveY = World.getWorld().getNpcHandler().GetMove(getY(), enemyY);
		}
		getNextNPCMovement();
		updateRequired = true;
	}

	/**
	 * Teleport
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void teleport(int x, int y, int z) {
		teleporting = true;
		this.setX(x);
		this.setY(y);
		setHeight(z);
	}

	public boolean insideOf(int x, int y) {
		for (Point p : getActorTiles()) {
			if (p.x == x && p.y == y) {
				return true;
			}
		}

		return false;
	}

	public Point[] getActorTiles() {
		Point[] tiles = new Point[getSize() == 1 ? 1 : (int) Math.pow(getSize(), 2)];
		int index = 0;

		for (int i = 1; i < getSize() + 1; i++) {
			for (int k = 0; k < NPCClipping.SIZES[i].length; k++) {
				int x3 = getX() + NPCClipping.SIZES[i][k][0];
				int y3 = getY() + NPCClipping.SIZES[i][k][1];
				tiles[index] = new Point(x3, y3);
				index++;
			}
		}
		return tiles;
	}

	/**
	 * Gets the exact distance from this actor.
	 * 
	 * @param x
	 * @param y
	 * @return the exact distance between ponits.
	 */
	public double getDistance(int x, int y) {
		double low = 9999;

		if (insideOf(x, y))
			return 0;

		for (Point p : getBorder()) {
			double dist = Misc.distance(x, y, p.x, p.y);
			if (dist < low) {
				low = dist;
			}
		}

		return low;
	}

	/**
	 * Gets the border around the edges of the actor.
	 * 
	 * @return the border around the edges of the actor, depending on the actor's
	 *         size.
	 */
	public Point[] getBorder() {
		int x = getX();
		int y = getY();
		int size = getSize();
		if (size <= 1) {
			return new Point[] { new Point(x, y) };
		}

		Point[] border = new Point[(size) + (size - 1) + (size - 1) + (size - 2)];
		int j = 0;

		border[0] = new Point(x, y);

		for (int i = 0; i < 4; i++) {
			for (int k = 0; k < (i < 3 ? (i == 0 || i == 2 ? size : size) - 1
					: (i == 0 || i == 2 ? size : size) - 2); k++) {
				if (i == 0)
					x++;
				else if (i == 1)
					y++;
				else if (i == 2)
					x--;
				else if (i == 3) {
					y--;
				}
				border[(++j)] = new Point(x, y);
			}
		}

		return border;
	}

	/**
	 * Determines if the npc can face another player
	 * 
	 * @return {@code true} if the npc can face players
	 */
	public boolean canFacePlayer() {
		return facePlayer;
	}

	/**
	 * Makes the npcs either able or unable to face other players
	 * 
	 * @param facePlayer
	 *            {@code true} if the npc can face players
	 */
	public void setFacePlayer(boolean facePlayer) {
		this.facePlayer = facePlayer;
	}

	/**
	 * Sends the request to a client that the npc should be transformed into
	 * another.
	 * 
	 * @param Id
	 *            the id of the new npc
	 */
	public void requestTransform(int id) {
		transformId = id;
		npcType = id;
		this.stats = NPCCombatStats.getStatsFor(npcType);
		transformUpdateRequired = true;
		updateRequired = true;
	}

	public void appendTransformUpdate(Buffer str) {
		str.writeWordBigEndianA(transformId);
	}

	public void updateNPCMovement(Buffer str) {
		if (direction == -1) {
			if (updateRequired) {
				str.writeBits(1, 1);
				str.writeBits(2, 0);
			} else {
				str.writeBits(1, 0);
			}
		} else { //walking only
			str.writeBits(1, 1);
			str.writeBits(2, running ? 2 : 1);
			str.writeBits(3, Misc.xlateDirectionToClient[direction]);
			if (running)
				str.writeBits(3, Misc.xlateDirectionToClient[direction]);
			str.writeBits(1, updateRequired ? 1 : 0);
		}
	}

	/**
	 * Text update
	 **/

	public void forceChat(String text) {
		forcedText = text;
		forcedChatRequired = true;
		updateRequired = true;
	}

	public int mask80var1 = 0;
	public int mask80var2 = 0;
	protected boolean mask80update = false;

	public void appendMask80Update(Buffer str) {
		str.writeWord(mask80var1);
		str.writeDWord(mask80var2);
	}

	public void gfx100(int gfx) {
		mask80var1 = gfx;
		mask80var2 = 6553600;
		mask80update = true;
		updateRequired = true;
	}
	
	public void gfx100(int gfx, int height) {
		mask80var1 = gfx;
		mask80var2 = 65536 * height;
		mask80update = true;
		updateRequired = true;
	}

	public void gfx0(int gfx) {
		mask80var1 = gfx;
		mask80var2 = 65536;
		mask80update = true;
		updateRequired = true;
	}

	public int getOffset() {
		return (int) Math.floor(NPCHandler.getNpcDef()[this.npcType].size / 2);
	}

	public static int getSpeedForDistance(int distance) {
		switch (distance) {
		case 1:
			return 90;
		case 2:
			return 95;
		case 3:
			return 100;
		case 4:
			return 105;
		case 5:
			return 115;
		case 6:
			return 125;
		case 7:
			return 135;
		case 8:
			return 150;
		default:
			return 150;
		}
	}

	public void appendAnimUpdate(Buffer str) {
		str.writeWordBigEndian(animNumber);
		str.writeByte(1);
	}

	public int FocusPointX = -1, FocusPointY = -1;
	public int face = 0;

	private void appendSetFocusDestination(Buffer str) {
		str.writeWordBigEndian(FocusPointX);
		str.writeWordBigEndian(FocusPointY);
	}

	public void turnNpc(int i, int j) {
		FocusPointX = 2 * i + 1;
		FocusPointY = 2 * j + 1;
		updateRequired = true;

	}

	public void appendFaceEntity(Buffer str) {
		str.writeWord(face);
		str.writeWord(summonedBy);
	}

	public void faceEntity(int index) {
		if (!facePlayer) {
			if (face == -1) {
				return;
			}
			face = -1;
		} else {
			face = index + 32768;
		}
		dirUpdateRequired = true;
		updateRequired = true;
	}
	
	public void faceEntity(Entity entity) {
		if (!facePlayer || entity == null) {
			if (face == -1) {
				return;
			}
			face = -1;
		} else {
			face = entity.getIndex() + (entity.isPlayer() ? 32768 : 0);
		}
		dirUpdateRequired = true;
		updateRequired = true;
	}

	public void appendFaceToUpdate(Buffer str) {
		str.writeWordBigEndian(viewX);
		str.writeWordBigEndian(viewY);
	}

	public void appendNPCUpdateBlock(Buffer str) {
		if (!updateRequired)
			return;
		int updateMask = 0;
		if (animUpdateRequired)
			updateMask |= 0x10;
		if (hitUpdateRequired2)
			updateMask |= 8;
		if (mask80update)
			updateMask |= 0x80;
		if (dirUpdateRequired)
			updateMask |= 0x20;
		if (forcedChatRequired)
			updateMask |= 1;
		if (hitUpdateRequired)
			updateMask |= 0x40;
		if (transformUpdateRequired)
			updateMask |= 2;
		if (FocusPointX != -1)
			updateMask |= 4;

		str.writeByte(updateMask);

		if (animUpdateRequired)
			appendAnimUpdate(str);
		if (hitUpdateRequired2)
			appendHitUpdate2(str);
		if (mask80update)
			appendMask80Update(str);
		if (dirUpdateRequired)
			appendFaceEntity(str);
		if (forcedChatRequired) {
			str.writeString(forcedText);
		}
		if (hitUpdateRequired)
			appendHitUpdate(str);
		if (transformUpdateRequired)
			appendTransformUpdate(str);
		if (FocusPointX != -1)
			appendSetFocusDestination(str);
	}

	public void clearUpdateFlags() {
		updateRequired = false;
		forcedChatRequired = false;
		hitUpdateRequired = false;
		hitUpdateRequired2 = false;
		animUpdateRequired = false;
		dirUpdateRequired = false;
		transformUpdateRequired = false;
		mask80update = false;
		forcedText = null;
		moveX = 0;
		moveY = 0;
		direction = -1;
		FocusPointX = -1;
		FocusPointY = -1;
		teleporting = false;
	}

	public int getNextWalkingDirection() {
		int dir;
		dir = Misc.direction(getX(), getY(), (getX() + moveX), (getY() + moveY));
		if (dir == -1)
			return -1;
		if (teleporting)
			return -1;
		dir >>= 1;
		setX(getX() + moveX);
		setY(getY() + moveY);
		return dir;
	}

	public void startAnimation(int animationId) {
		animNumber = animationId;
		animUpdateRequired = true;
		updateRequired = true;
	}

	public void getNextNPCMovement() {
		direction = -1;
		if (freezeTimer == 0) {
			direction = getNextWalkingDirection();
		
		}
		updateRequired = true;
	}

	@Override
	public void appendHitUpdate(Buffer str) {
		if (getHealth().getAmount() <= 0) {
			isDead = true;
		}
		if (hitmark1 != null && !hitmark1.isMiss() && hitDiff == 0) {
			hitDiff = 0;
			hitmark1 = Hitmark.MISS;
		}
		str.writeByteC(hitDiff);
		if (hitmark1 != null) {
			str.writeByteS(hitmark1.getId());
		} else {
			str.writeByteS(0);
		}
		str.writeWord(health.getAmount());
		str.writeWord(health.getMaximum());
	}

	public int hitDiff2 = 0;
	public boolean hitUpdateRequired2 = false;
	public int walkHomeAttempts;
	public int npcId;
	private boolean noRespawn;

	@Override
	public void appendHitUpdate2(Buffer str) {
		if (getHealth().getAmount() <= 0) {
			isDead = true;
		}
		if (hitmark2 != null && !hitmark2.isMiss() && hitDiff2 == 0) {
			hitDiff2 = 0;
			hitmark2 = Hitmark.MISS;
		}
		str.writeByteA(hitDiff2);
		if (hitmark2 != null) {
			str.writeByteC(hitmark2.getId());
		} else {
			str.writeByteC(0);
		}
		str.writeWord(getHealth().getAmount());
		str.writeWord(getHealth().getMaximum());
	}

	public int appendDamage(Player player, int damage, Hitmark h) {
		appendDamage(damage, h);
		addDamageTaken(player, damage);
		return damage;
	}
	
	public int appendDamage(Entity source, Damage damage) {
		if (combatScript != null) {
			damage.setAmount((int) Math.round(damage.getAmount() * combatScript.getDamageReduction(this)));
			combatScript.handleRecievedHit(this, source, damage);
		}
		appendDamage(damage.getAmount(), damage.getHitmark());
		addDamageTaken(source, damage.getAmount());
		return damage.getAmount();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/*
	 * public NPCDefinition getDefinition() { return definition; }
	 */

	public boolean inMulti() {
		if (Boundary.isIn(this, Zulrah.BOUNDARY) || Boundary.isIn(this, Boundary.CORPOREAL_BEAST_LAIR)
				|| Boundary.isIn(this, Boundary.KRAKEN_CAVE) || Boundary.isIn(this, Boundary.SCORPIA_LAIR)
				|| Boundary.isIn(this, Boundary.CERBERUS_BOSSROOMS) || Boundary.isIn(this, Boundary.INFERNO) || Boundary.isIn(this, Boundary.XERIC)
				|| Boundary.isIn(this, Boundary.SKOTIZO_BOSSROOM) || Boundary.isIn(this, Boundary.LIZARDMAN_CANYON)
				|| Boundary.isIn(this, Boundary.BANDIT_CAMP_BOUNDARY) || Boundary.isIn(this, Boundary.ABYSSAL_SIRE)
				|| Boundary.isIn(this, Boundary.COMBAT_DUMMY) || Boundary.isIn(this, Boundary.TEKTON)
				|| Boundary.isIn(this, Boundary.SKELETAL_MYSTICS) || Boundary.isIn(this, Boundary.RAID_MAIN)
				|| Boundary.isIn(this, Boundary.ICE_DEMON) || Boundary.isIn(this, Boundary.CATACOMBS) || Boundary.isIn(this, Boundary.SMOKE_DEVILS)) {
			return true;
		}

		if (Boundary.isIn(this, Boundary.KALPHITE_QUEEN) && getHeight() == 0) {
			return true;
		}

		if ((getX() >= 3136 && getX() <= 3327 && getY() >= 3519 && getY() <= 3607)
				|| (getX() >= 3190 && getX() <= 3327 && getY() >= 3648 && getY() <= 3839)
				|| (getX() >= 3200 && getX() <= 3390 && getY() >= 3840 && getY() <= 3967)
				|| (getX() >= 2261 && getX() <= 2283 && getY() >= 4054 && getY() <= 4076)//vorkath
				|| (getX() >= 2992 && getX() <= 3007 && getY() >= 3912 && getY() <= 3967)
				|| (getX() >= 2946 && getX() <= 2959 && getY() >= 3816 && getY() <= 3831)
				|| (getX() >= 3008 && getX() <= 3199 && getY() >= 3856 && getY() <= 3903)
				|| (getX() >= 2824 && getX() <= 2944 && getY() >= 5258 && getY() <= 5369)
				|| (getX() >= 3008 && getX() <= 3071 && getY() >= 3600 && getY() <= 3711)
				|| (getX() >= 3072 && getX() <= 3327 && getY() >= 3608 && getY() <= 3647)
				|| (getX() >= 2624 && getX() <= 2690 && getY() >= 2550 && getY() <= 2619)
				|| (getX() >= 2371 && getX() <= 2422 && getY() >= 5062 && getY() <= 5117)
				|| (getX() >= 2896 && getX() <= 2927 && getY() >= 3595 && getY() <= 3630)
				|| (getX() >= 2892 && getX() <= 2932 && getY() >= 4435 && getY() <= 4464)
				|| (getX() >= 2256 && getX() <= 2287 && getY() >= 4680 && getY() <= 4711)
				|| (getX() >= 2962 && getX() <= 3006 && getY() >= 3621 && getY() <= 3659)
				|| (getX() >= 3155 && getX() <= 3214 && getY() >= 3755 && getY() <= 3803)
				|| (getX() >= 1889 && getX() <= 1912 && getY() >= 4396 && getY() <= 4413)
				|| (getX() >= 3154 && getX() <= 3182 && getY() >= 4303 && getY() <= 4327)
				|| (getX() >= 3717 && getX() <= 3772 && getY() >= 5765 && getY() <= 5820)) {
			return true;
		}
		return false;
	}

	public boolean inWild() {
		if (getX() > 2941 && getX() < 3392 && getY() > 3518 && getY() < 3966
				|| getX() > 2941 && getX() < 3392 && getY() > 9918 && getY() < 10366) {
			return true;
		}
		return false;
	}

	public boolean inRaids() {
		return (getX() > 3210 && getX() < 3368 && getY() > 5137 && getY() < 5759);
	}
	public boolean inXeric() {
		return (getX() > 2685 && getX() < 2743 && getY() > 5440 && getY() < 5495);
	}

	@Override
	public int getSize() {
		if (definition == null)
			return 1;
		return definition.getSize();
	}

	/**
	 * An object containing specific information about the NPC such as the combat
	 * level, default maximum health, the name, etcetera.
	 * 
	 * @return the {@link NPCDefintions} object associated with this NPC
	 */
	public NPCDefinitions getDefinition() {
		return definition;
	}

	public int getProjectileDelay() {
		return projectileDelay;
	}

	public void setProjectileDelay(int delay) {
		projectileDelay = delay;
	}

	@Override
	public void appendDamage(int damage, Hitmark hitmark) {
		if (damage < 0) {
			damage = 0;
			hitmark = Hitmark.MISS;
		}
		if (hitmark == Hitmark.HEAL_PURPLE) {
			getHealth().increase(damage);
		}
		if (getHealth().getAmount() - damage < 0) {
			damage = getHealth().getAmount();
		}
		if (hitmark != Hitmark.HEAL_PURPLE)
			getHealth().reduce(damage);
		if (!hitUpdateRequired) {
			hitUpdateRequired = true;
			hitDiff = damage;
			hitmark1 = hitmark;
		} else if (!hitUpdateRequired2) {
			hitUpdateRequired2 = true;
			hitDiff2 = damage;
			hitmark2 = hitmark;
		}
		updateRequired = true;
	}

	@Override
	public boolean susceptibleTo(HealthStatus status) {
		switch (npcType) {
		case 2042:
		case 2043:
		case 2044:
		case 6720:
		case 7413:
		case 7544:
		case 5129:
		case 4922:
		case 7604:
		case 7605:
		case 7606:
			return false;
		}
		return true;
	}

	public long getLastRandomWalk() {
		return lastRandomWalk;
	}

	public long getLastRandomWalkhome() {
		return lastRandomWalkHome;
	}

	public void setLastRandomWalkHome(long lastRandomWalkHome) {
		this.lastRandomWalkHome = lastRandomWalkHome;
	}

	public long getRandomStopDelay() {
		return randomStopDelay;
	}

	public void setRandomStopDelay(long randomStopDelay) {
		this.randomStopDelay = randomStopDelay;
	}

	public void setLastRandomWalk(long lastRandomWalk) {
		this.lastRandomWalk = lastRandomWalk;
	}

	public long getRandomWalkDelay() {
		return randomWalkDelay;
	}

	public void setRandomWalkDelay(long randomWalkDelay) {
		this.randomWalkDelay = randomWalkDelay;
	}

	public void setNoRespawn(boolean b) {
		this.noRespawn = b;
	}
	
	public boolean isNoRespawn() {
		return noRespawn;
	}

	public boolean isDragon() {
		switch (npcType) {
		case 137:
		case 139:
		case 239:
		case 241:
		case 242:
		case 243:
		case 244:
		case 245:
		case 246:
		case 247:
		case 248:
		case 249:
		case 250:
		case 251:
		case 252:
		case 253:
		case 254:
		case 255:
		case 256:
		case 257:
		case 258:
		case 259:
		case 260:
		case 261:
		case 262:
		case 263:
		case 264:
		case 265:
		case 266:
		case 267:
		case 268:
		case 269:
		case 270:
		case 271:
		case 272:
		case 273:
		case 274:
		case 275:
		case 1871:
		case 1872:
		case 2642:
		case 2918:
		case 2919:
		case 4000:
		case 4385:
		case 5194:
		case 5872:
		case 5873:
		case 5878:
		case 5879:
		case 5880:
		case 5881:
		case 5882:
		case 6500:
		case 6501:
		case 6502:
		case 6593:
		case 6636:
		case 6652:
		case 7039:
		case 7253:
		case 7254:
		case 7255:
		case 7273:
		case 7274:
		case 7275:
		case 8027:
		case 7553:
		case 7554:
		case 7555:
		case 8609:
			return true;
		}
		return false;
	}
	
	public void kill() {
		isDead = true;
		applyDead = true;
		actionTimer = 0;
	}
	
	@Override
	public boolean isNPC() {
		return true;
	}
	
}
