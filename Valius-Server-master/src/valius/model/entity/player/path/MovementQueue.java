/**
 * 
 */
package valius.model.entity.player.path;

import java.util.ArrayDeque;
import java.util.Deque;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import valius.clip.Region;
import valius.event.CycleEventHandler;
import valius.model.Location;
import valius.model.entity.Entity;
import valius.model.entity.player.Player;
import valius.util.Misc;
import valius.world.World;

/**
 * @author ReverendDread
 * Jun 1, 2019
 */
@RequiredArgsConstructor @Slf4j
public class MovementQueue {

	/**
	 * The maximum size of the queue. If any additional steps are added, they are
	 * discarded.
	 */
	private static final int MAXIMUM_SIZE = 100;
	
	/**
	 * The event id of {@link EntityFollowEvent}
	 */
	private static final int FOLLOWING_EVENT_ID = Byte.MAX_VALUE;
	
	/**
	 * The entity whos movement queue this is.
	 */
	private final Entity entity;
	
	/**
	 * The {@link CycleEvent} which handles following.
	 */
	private EntityFollowEvent followingEvent;
	
	/**
	 * The queue of directions.
	 */
	private final Deque<Point> points = new ArrayDeque<Point>();
	
	/**
	 * The current {@link MovementStatus}.
	 */
	private boolean blockMovement = false;

	/**
	 * Are we currently moving?
	 */
	@Getter private boolean isMoving = false;
	
	/**
	 * Checks if we can walk from one position to another.
	 *
	 * @param from
	 * @param to
	 * @param size
	 * @return
	 */
	public static boolean canWalk(Location from, Location to, int size) {
		return Region.canMove(from, to, size, size);
	}
	
	/**
	 * Steps away from a Gamecharacter
	 *
	 * @param character The gamecharacter to step away from
	 */
	public static void clippedStep(Entity character) {
		if (character.getMovementQueue().canWalk(-1, 0)) {
			character.getMovementQueue().walkStep(-1, 0);
		} else if (character.getMovementQueue().canWalk(1, 0)) {
			character.getMovementQueue().walkStep(1, 0);
		} else if (character.getMovementQueue().canWalk(0, -1)) {
			character.getMovementQueue().walkStep(0, -1);
		} else if (character.getMovementQueue().canWalk(0, 1)) {
			character.getMovementQueue().walkStep(0, 1);
		}
	}
	
	/**
	 * Adds the first step to the queue, attempting to connect the server and client
	 * position by looking at the previous queue.
	 *
	 * @param clientConnectionPosition The first step.
	 * @return {@code true} if the queues could be connected correctly,
	 *         {@code false} if not.
	 */
	public boolean addFirstStep(Location clientConnectionPosition) {
		reset();
		addStep(clientConnectionPosition);
		return true;
	}

	/**
	 * Adds a step to walk to the queue.
	 *
	 * @param x       X to walk to
	 * @param y       Y to walk to
	 * @param clipped Can the step walk through objects?
	 */
	public void walkStep(int x, int y) {
		Location position = this.entity.getLocation().copy();
		position.setX(position.getX() + x);
		position.setY(position.getY() + y);
		addStep(position);
	}
	
	/**
	 * Adds a step.
	 *
	 * @param x           The x coordinate of this step.
	 * @param y           The y coordinate of this step.
	 * @param heightLevel
	 * @param flag
	 */
	private void addStep(int x, int y, int heightLevel) {
		if (!canMove()) {
			return;
		}

		if (this.points.size() >= MAXIMUM_SIZE) {
			return;
		}

		final Point last = getLast();
		final int deltaX = x - last.position.getX();
		final int deltaY = y - last.position.getY();
		final Direction direction = Direction.fromDeltas(deltaX, deltaY);
		if (direction != Direction.NONE) {
			this.points.add(new Point(new Location(x, y, heightLevel), direction));
		}
	}
	
	/**
	 * Adds a step to the queue.
	 *
	 * @param step The step to add.
	 * @oaram flag
	 */
	public void addStep(Location step) {
		
		if (!canMove()) {
			return;
		}

		final Point last = getLast();
		final int x = step.getX();
		final int y = step.getY();
		int deltaX = x - last.position.getX();
		int deltaY = y - last.position.getY();
		final int max = Math.max(Math.abs(deltaX), Math.abs(deltaY));
		for (int i = 0; i < max; i++) {
			if (deltaX < 0) {
				deltaX++;
			} else if (deltaX > 0) {
				deltaX--;
			}
			if (deltaY < 0) {
				deltaY++;
			} else if (deltaY > 0) {
				deltaY--;
			}
			addStep(x - deltaX, y - deltaY, step.getZ());
		}
	}

	public boolean canMove() {
		if (this.entity.isNeedsPlacement()) {
			return false;
		}
		if (this.entity.isPlayer() && this.entity.asPlayer().freezeDelay > 0 || this.blockMovement) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if the entity can walk to the given coordinates.
	 * @param deltaX
	 * @param deltaY
	 * @return
	 */
	public boolean canWalk(int deltaX, int deltaY) {
		if (!canMove()) {
			return false;
		}
		final Location to = new Location(this.entity.getLocation().getX() + deltaX, this.entity.getLocation().getY() + deltaY, this.entity.getLocation().getZ());
		if (this.entity.getLocation().getZ() == -1 && to.getZ() == -1) {
			return true;
		}
		return canWalk(this.entity.getLocation(), to, this.entity.getSize());
	}
	
	/**
	 * Handles region change from walking into a new region.
	 */
	public void handleRegionChange() {
		Player player = (Player) this.entity;
		final int diffX = this.entity.getLocation().getX() - player.getLastKnownLocation().getRegionX() * 8;
		final int diffY = this.entity.getLocation().getY() - player.getLastKnownLocation().getRegionY() * 8;
		boolean regionChanged = false;
		if (diffX < 16) {
			regionChanged = true;
		} else if (diffX >= 88) {
			regionChanged = true;
		}
		if (diffY < 16) {
			regionChanged = true;
		} else if (diffY >= 88) {
			regionChanged = true;
		}
		if (regionChanged || player.getHeight() != player.getLocation().getZ()) {
			//if (player.allowMapUpdate()) {
				player.getPA().sendMapRegion();
				player.setHeight(player.getLocation().getZ());
			//}
		}
	}
	
	private void drainRunEnergy() {
		Player player = (Player) this.entity;
		if (player.isRunning()) {
			//player.setRunEnergy(player.getRunEnergy() - 1);
			if (player.getRunEnergy() <= 0) {
				player.setRunEnergy(0);
				player.setRunning(false);
				player.getPA().setConfig(173, 0);
			}
			player.getPA().sendFrame126(Integer.toString(player.getRunEnergy()), 149);
		}
	}

	/**
	 * Gets the last point.
	 *
	 * @return The last point.
	 */
	private Point getLast() {
		final Point last = this.points.peekLast();
		if (last == null) {
			return new Point(this.entity.getLocation(), Direction.NONE);
		}
		return last;
	}
	
	/**
	 * Checks if the entity has steps.
	 * @return
	 */
	public boolean hasSteps() {
		return !this.points.isEmpty();
	}
	
	/**
	 * Processes the movement queue.
	 *
	 * Polls through the queue of steps and handles them.
	 *
	 */
	public void process() {

		// Make sure movement isnt restricted..
		if (!canMove()) {
			reset();
			return;
		}

		// Poll through the actual movement queue and
		// begin moving.
		Point walkPoint = null;
		Point runPoint = null;

		walkPoint = this.points.poll();

		if (isRunToggled()) {
			runPoint = this.points.poll();
		}

		Location previousPosition = this.entity.getLocation();
		boolean moved = false;

		if (walkPoint != null && walkPoint.direction != Direction.NONE) {
			Location next = walkPoint.position;
			if (validateStep(next)) {			
				this.entity.setX(next.getX());
				this.entity.setY(next.getY());
				this.entity.setWalkingDirection(walkPoint.direction);
				moved = true;
			} else {
				reset();
				return;
			}
		}

		if (runPoint != null && runPoint.direction != Direction.NONE) {
			Location next = runPoint.position;
			previousPosition = next;
			if (validateStep(next)) {
				this.entity.setX(next.getX());
				this.entity.setY(next.getY());				
				this.entity.setRunningDirection(runPoint.direction);
				if (this.entity.isPlayer()) {			
					this.entity.asPlayer().runningDistanceTravelled++;
				}
				moved = true;
			} else {
				reset();
				return;
			}
		}	

		// Handle movement-related events such as
		// region change and energy drainage.
		if (this.entity.isPlayer()) {
			if (moved) {
				handleRegionChange();
				drainRunEnergy();
				this.entity.asPlayer().setPreviousLocation(previousPosition);
			}
		}
		
		//log.info("X {}, Y {}", this.entity.getLocation().getLocalX(), this.entity.getLocation().getLocalY());

		this.isMoving = moved;
	}
	
	private boolean validateStep(Location next) {
		if (this.followingEvent != null && next.equals(this.followingEvent.getFollowing().getLocation())) {
			return false;
		}
		return true;
	}
	
	/**
	 * Stops the movement.
	 */
	public MovementQueue reset() {
		this.points.clear();
		this.isMoving = false;
		return this;
	}
	
	/**
	 * Starts a new {@link CharacterFollowTask} which starts following the given
	 * {@link Actor}.
	 *
	 * @param follow
	 */
	public void follow(Entity follow) {
		if (follow == null) {
			resetFollowing();
			return;
		}
		if (this.followingEvent == null || !CycleEventHandler.getSingleton().isAlive(this.entity, FOLLOWING_EVENT_ID)) {
			this.followingEvent = new EntityFollowEvent(this.entity, follow);
			CycleEventHandler.getSingleton().addEvent(FOLLOWING_EVENT_ID, this.entity, this.followingEvent, 1);
		} else {
			this.followingEvent.setFollowing(follow);
		}
	}
	
	/**
	 * Checks if we're currently following the given {@link Actor}.
	 *
	 * @param character
	 * @return
	 */
	public boolean isFollowing(Entity character) {
		if (this.followingEvent != null) {
			return this.followingEvent.getFollowing().equals(character);
		}
		return false;
	}

	/**
	 * Stops any following which might be active.
	 */
	public void resetFollowing() {
		if (this.followingEvent != null) {
			CycleEventHandler.getSingleton().stopEvents(this.entity, FOLLOWING_EVENT_ID);
			if (this.entity.isPlayer()) {
				this.entity.asPlayer().followId = 0;
				this.entity.asPlayer().followId2 = 0;
				this.entity.asPlayer().mageFollow = false;
			}
		}
		this.followingEvent = null;
	}
	
	/**
	 * Returns the entitys running state.
	 * @return
	 */
	public boolean isRunToggled() {
		return this.entity.isPlayer() && entity.asPlayer().isRunning();
	}
	
	/**
	 * Sets the movement block state.
	 * @param blockMovement
	 * @return
	 */
	public MovementQueue setBlockMovement(boolean blockMovement) {
		this.blockMovement = blockMovement;
		return this;
	}
	
	/**
	 * Gets the size of the queue.
	 *
	 * @return The size of the queue.
	 */
	public int size() {
		return this.points.size();
	}
	
	@RequiredArgsConstructor
	public static final class Point {

		private final Location position;
		private final Direction direction;

		@Override
		public String toString() {
			return Point.class.getName() + " [direction=" + this.direction + ", position=" + this.position + "]";
		}

	}
	
}
