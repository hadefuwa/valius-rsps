package valius.model.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.Setter;
import valius.model.Location;
import valius.model.entity.instance.Instance;
import valius.model.entity.npc.NPC;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Hitmark;
import valius.model.entity.player.path.Direction;
import valius.model.entity.player.path.MovementQueue;
import valius.model.minigames.raids.Raids;
import valius.model.minigames.theatre.Theatre;
import valius.model.minigames.xeric.Xeric;
import valius.util.Buffer;

/**
 * Represents a game-world presence that exists among others of the same nature.
 * The objective is to allow multiple entities to share common similarities and
 * allow simple but effective reference in doing so.
 * 
 * @author Jason MacKeigan
 * @date Mar 27, 2015, 2015, 8:00:45 PM
 */
@Getter @Setter
public abstract class Entity {
	
	protected void appendSoundUpdate(Buffer str) {
		str.writeByte(soundDelay);
		str.writeWord(soundEffect);
		soundDelay = 0;
		soundEffect = -1;
	}
	
	public Optional<Instance> optionalInstance(){
		return Optional.ofNullable(instance);
	}

	protected int soundEffect = -1;
	protected int soundDelay;
	/**
	 * The current instanced area.
	 */
	private Instance instance;

	/**
	 * The current raids instance.
	 */
	private Raids raidsInstance;
	
	/**
	 * The current theatre of blood instance.
	 */
	protected Theatre theatreInstance;

	/**
	 * The current xeric instance.
	 */
	private Xeric xeric;
	
	/**
	 * The visual alias or name of the entity that describes and separates this
	 * entity from others of the same nature.
	 */
	protected String name;

	/**
	 * The absolute x-position of the entity
	 */
	protected int x;

	/**
	 * The absolute y-position of the entity
	 */
	protected int y;

	/**
	 * The absolute height of the entity
	 */
	protected int height;

	/**
	 * The index in the list that the player resides
	 */
	protected final int index;
	
	/**
	 * The size of the entity
	 */
	protected int size;

	/**
	 * A mapping of all damage that has been taken by other entities in the game
	 */
	protected Map<Entity, List<Damage>> damageTaken = new HashMap<>();

	/**
	 * The {@link Entity} that has been determined the killer of this {@link Entity}
	 */
	protected Entity killer;

	/**
	 * The health of the entity
	 */
	protected Health health;
	
	/**
	 * The movement queue.
	 */
	protected final MovementQueue movementQueue = new MovementQueue(this);
	
	/**
	 * If placement is needed.
	 */
	private boolean needsPlacement;
	
	/**
	 * The last known location of the entity.
	 */
	private Location lastKnownLocation;
	
	/**
	 * Previous location.
	 */
	private Location previousLocation;
	
	/**
	 * The walk direction.
	 */
	private Direction walkingDirection = Direction.NONE, runningDirection = Direction.NONE;
	
	/**
	 * TO-DO
	 */
	protected Hitmark hitmark1 = null;
	protected Hitmark hitmark2 = null;
	public boolean updateRequired = true;
	public int hitDiff2;
	public int hitDiff = 0;
	public boolean hitUpdateRequired2;
	public boolean hitUpdateRequired = false;

	/**
	 * Creates a new {@link Entity} object with a specified index value representing
	 * where in their respective array they reside. An {@link Entity} is an object
	 * that exists within the game-world. A {@link Player} or {@link NPC} are all
	 * examples of entities.
	 * 
	 * @param index
	 *            the index in the list where this {@link Entity} resides.
	 */
	public Entity(int index, String name) {
		this.index = index;
		this.name = name;
	}

	/**
	 * Adds some damage value to the entities list of taken damage
	 * 
	 * @param entity
	 *            the entity that dealt the damage
	 * @param damage
	 *            the total damage taken
	 */
	public void addDamageTaken(Entity entity, int damage) {
		if (entity == null || damage <= 0) {
			return;
		}
		Damage combatDamage = new Damage(damage);
		if (damageTaken.containsKey(entity)) {
			damageTaken.get(entity).add(new Damage(damage));
		} else {
			damageTaken.put(entity, new ArrayList<>(Arrays.asList(combatDamage)));
		}
	}

	/**
	 * Clears any and all damage that has been taken by the entity
	 */
	public void resetDamageTaken() {
		damageTaken.clear();
	}

	/**
	 * Sends some information to the Stream regarding a possible new hit on the
	 * entity.
	 * 
	 * @param str
	 *            the stream for the entity
	 */
	protected abstract void appendHitUpdate(Buffer str);

	/**
	 * Sends some information to the Stream regarding a possible new hit on the
	 * entity.
	 * 
	 * @param str
	 *            the stream for the entity
	 */
	protected abstract void appendHitUpdate2(Buffer str);

	/**
	 * Used to append some amount of damage to the entity and inflict on their total
	 * amount of health. The method will also display a new hitmark on that entity.
	 * 
	 * @param damage
	 *            the damage dealt
	 * @param hitmark
	 *            the hitmark to show with the damage
	 */
	public abstract void appendDamage(int damage, Hitmark hitmark);

	/**
	 * Determines if the entity is susceptible to a status based on their nature.
	 * For example some players when wearing certain equipment are exempt from venom
	 * or poison status. In other situations, NPC's are susceptible to venom.
	 * 
	 * @param status
	 *            the status the entity may not be susceptible to
	 * @return {code true} if the entity is not susceptible to a particular status
	 */
	public abstract boolean susceptibleTo(HealthStatus status);

	/**
	 * When an entity dies it is paramount that we know who dealt the most damage to
	 * that entity so that we can determine who will receive the drop.
	 * 
	 * @return the {@link Entity} that dealt the most damage to this {@link Entity}.
	 */
	public Entity calculateKiller() {
		final long VALID_TIMEFRAME = this instanceof NPC ? TimeUnit.MINUTES.toMillis(5) : TimeUnit.SECONDS.toMillis(90);
		Entity killer = null;
		int totalDamage = 0;

		for (Entry<Entity, List<Damage>> entry : damageTaken.entrySet()) {
			Entity tempKiller = entry.getKey();
			List<Damage> damageList = entry.getValue();
			int damage = 0;

			if (tempKiller == null) {
				continue;
			}
			
			if(tempKiller.isPlayer()) {
				Player p = tempKiller.asPlayer();
				if(p.disconnected || p.getSession() == null || !p.getSession().isConnected())
					continue;
				if(this.getRaidsInstance() != null){
					if(p.getRaidsInstance() == null || p.getRaidsInstance() != this.getRaidsInstance())
						continue;
				}
			}

			for (Damage d : damageList) {
				if (System.currentTimeMillis() - d.getTimestamp() < VALID_TIMEFRAME) {
					damage += d.getAmount();
				}
			}
			if (totalDamage == 0 || damage > totalDamage || killer == null) {
				totalDamage = damage;
				killer = tempKiller;
			}

			if (killer != null && killer instanceof Player && this instanceof NPC) {
				Player player = (Player) killer;
				NPC npc = (NPC) this;

				if ((player.getMode().isIronman() || player.getMode().isUltimateIronman() || player.getMode().isHcIronman() || player.getMode().isGroupIronman())
						&& !Boundary.isIn(player, Boundary.GODWARS_BOSSROOMS)
						&& !Boundary.isIn(player, Boundary.CORPOREAL_BEAST_LAIR)
						&& !Boundary.isIn(player, Boundary.DAGANNOTH_KINGS) && !Boundary.isIn(player, Boundary.TEKTON)
						&& !Boundary.isIn(player, Boundary.SKELETAL_MYSTICS)
						&& !Boundary.isIn(player, Boundary.RAID_MAIN)) {

					double percentile = ((double) totalDamage / (double) npc.getHealth().getMaximum()) * 100D;
					if (percentile < 75.0) {
						killer = null;
					}
				}
			}

		}
		return killer;
	}

	/**
	 * The status of the entities health whether it's normal, poisoned, or some
	 * other nature.
	 * 
	 * @return the status of the entities health
	 */
	public Health getHealth() {
		if (health == null) {
			health = new Health(this);
		}
		return health;
	}

	/**
	 * Retrieves the current hitmark
	 * 
	 * @return the hitmark
	 */
	public Hitmark getHitmark() {
		return hitmark1;
	}

	/**
	 * Retrieves the second hitmark
	 * 
	 * @return the second hitmark
	 */
	public Hitmark getSecondHitmark() {
		return hitmark2;
	}

	/**
	 * Checks if this entity is a {@link Player}
	 * @return
	 */
	public boolean isPlayer() {
		return this instanceof Player;
	}
	
	/**
	 * Checks if this entity is an {@link NPC}
	 * @return
	 */
	public boolean isNPC() {
		return this instanceof NPC;
	}
	
	/**
	 * Returns this entity as a {@link Player}
	 * @return
	 */
	public Player asPlayer() {
		return (Player) this;
	}
	
	/**
	 * Returns this entity as an {@link NPC}
	 * @return
	 */
	public NPC asNPC() {
		return (NPC) this;
	}
	
	/**
	 * Returns true if this entity is within 16 tiles of the given position.
	 * @param absX
	 * @param getY
	 * @param getHeightLevel
	 * @return
	 */
	public boolean withinDistance(int absX, int getY, int getHeightLevel) {
		if (this.getHeight() != getHeightLevel)
			return false;
		int deltaX = this.getX() - absX, deltaY = this.getY() - getY;
		return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;
	}
	
	/**
	 * Returns true if this entity is within 16 tiles of the given position.
	 * @param absX
	 * @param getY
	 * @param getHeightLevel
	 * @return
	 */
	public boolean withinDistanceOfCenter(Entity entity, int distance) {
		if (this.getHeight() != entity.getHeight())
			return false;
		int deltaX = this.getCenter().getX() - entity.getCenter().getX(), deltaY = this.getCenter().getY() - entity.getCenter().getY();
		return deltaX <= distance && deltaX >= -distance && deltaY <= distance && deltaY >= -distance;
	}
	
	
	/**
	 * Returns true if this entity is within the given distance of the given position from another entity.
	 * @param absX
	 * @param getY
	 * @param getHeightLevel
	 * @return
	 */
	public boolean withinDistance(Entity other, int distance) {
		if (this.getHeight() != other.getHeight())
			return false;
		int deltaX = this.getX() - other.getX(), deltaY = this.getY() - other.getY();
		return deltaX <= distance && deltaX >= -distance && deltaY <= distance && deltaY >= -distance;
	}

	/**
	 * Returns the distance to the given point.
	 * @param pointX
	 * @param pointY
	 * @return
	 */
	public int distanceToPoint(int pointX, int pointY) {
		return (int) Math.sqrt(Math.pow(getX() - pointX, 2) + Math.pow(getY() - pointY, 2));
	}

	/**
	 * Returns the distance to the given point.
	 * @param pointX
	 * @param pointY
	 * @param pointZ
	 * @return
	 */
	public int distanceToPoint(int pointX, int pointY, int pointZ) {
		return (int) Math.sqrt(Math.pow(getX() - pointX, 2) + Math.pow(getY() - pointY, 2) + Math.pow(Math.abs(getHeight()) - pointZ, 2));
	}
	
	/**
	 * Gets a boundary instance of the entitys tiles.
	 * @return
	 */
	public Boundary getBoundary() {
		int x = getLocation().getX();
		int y = getLocation().getY();
		int size = getSize() - 1;
		return new Boundary(x, y, x + size, y + size);
	}
	
	/**
	 * Checks if the given coordinates collide with eachother.
	 * @param pointX
	 * @param pointY
	 * @param pointZ
	 * @return
	 */
	public boolean collides(int targetX, int targetY, int targetSize) {
		int distanceX = x - targetX;
		int distanceY = y - targetY;
		return distanceX < targetSize && distanceX > -size && distanceY < targetSize && distanceY > -size;
	}

	/**
	 * Checks if the given coordinates collide with eachother.
	 * @param pointX
	 * @param pointY
	 * @param pointZ
	 * @return
	 */
	public boolean collides(int x, int y, int size, int targetX, int targetY, int targetSize) {
		int distanceX = x - targetX;
		int distanceY = y - targetY;
		return distanceX < targetSize && distanceX > -size && distanceY < targetSize && distanceY > -size;
	}
	
	public Location getCenter() {
		return Location.centerOf(getLocation(), getSize());
	}
	
	/**
	 * Returns a {@link Location} instance of the entity's coordinates.
	 * @return
	 */
	public Location getLocation() {
		return Location.of(x, y, height);
	}
	
	/**
	 * Sets a new instanced area for the entity.
	 * @param instance
	 */
	public void setInstance(Instance instance) {
		if(instance == null) {
			this.instance = null;
		} else {
			if(this.instance != null)
				this.instance.leave(this);
			this.instance = instance;
			this.instance.enter(this);
		}
	}
	
	/**
	 * Checks if the entity is in the same instanced area as another.
	 * @param other
	 * @return
	 */
	public boolean sameInstance(Entity other) {
		if(other == null)
			return false;
		if(this.instance != null && other.instance == null)
			return false;
		if(this.instance == null && other.instance != null)
			return false;
		if(this.instance == null && other.instance == null)
			return true;
		return other.instance == this.instance;
	}
	
	/**
	 * Sets the entity's current region's position.
	 *
	 * @param lastKnownLocation The location in which the player first entered the current region.
	 * 
	 * @return The Entity instance.
	 */
	public Entity setLastKnownLocation(Location lastKnownLocation) {
		this.lastKnownLocation = lastKnownLocation;
		return this;
	}
	
}
