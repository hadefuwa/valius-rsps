package valius.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.Setter;
import valius.clip.Region;
import valius.content.gauntlet.RoomDirection;
import valius.model.entity.Entity;
import valius.model.entity.player.Boundary;
import valius.util.Misc;

/**
 * Representing a specific location.
 * 
 * @author Emiel
 *
 */
public class Location {

	@Getter @Setter private int x, y, z;

	public Location(int x, int y) {
		this.x = x;
		this.y = y;
		z = 0;
	}

	public Location(int x, int y, int h) {
		this.x = x;
		this.y = y;
		this.z = h;
	}

	/**
	 * Absolute distance between this Coordiante and another.
	 * 
	 * @param other The other Coordiante.
	 * @return The distance between the 2 Coordinates.
	 */
	public int getDistance(Location other) {
		return (int) Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2) + Math.pow(z - other.z, 2));
	}

	/**
	 * Absolute distance between 2 Coordiantes.
	 * 
	 * @param c1 The first Coordiante.
	 * @param c2 The the second Coordiante.
	 * @return The distance between the 2 Coordinates.
	 */
	public static int getDistance(Location c1, Location c2) {
		return (int) Math.sqrt(Math.pow(c1.x - c2.x, 2) + Math.pow(c1.y - c2.y, 2) + Math.pow(c1.z - c2.z, 2));
	}

	/**
	 * Checks if this position is within distance of another position.
	 * 
	 * @param position
	 *            the position to check the distance for.
	 * @param distance
	 *            the distance to check.
	 * @return true if this position is within the distance of another position.
	 */
	public boolean withinDistance(Location position, int distance) {
		if (this.z != position.z)
			return false;
		return Math.abs(position.getX() - this.getX()) <= distance && Math.abs(position.getY() - this.getY()) <= distance;
	}
	
	/**
	 * Checks if this position is within distance of another position.
	 * 
	 * @param position
	 *            the position to check the distance for.
	 * @param distance
	 *            the distance to check.
	 * @return true if this position is within the distance of another position.
	 */
	public boolean withinDistanceIgnoreHeight(Location position, int distance) {
		return Math.abs(position.getX() - this.getX()) <= distance && Math.abs(position.getY() - this.getY()) <= distance;
	}
	
	public boolean withinBoundary(Boundary bounds) {
		return (this.x >= bounds.getMinimumX() && this.x <= bounds.getMaximumX() && this.y >= bounds.getMinimumY() && this.y <= bounds.getMaximumY());
	}

	public static Location of(int x, int y, int z) {
		return new Location(x, y, z);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + z;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (z != other.z)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	

	@Override
	public String toString() {
		return "Coordinate [x=" + x + ", y=" + y + ", h=" + z + "]";
	}

	public Location copy() {
		return new Location(x, y, z);
	}

	public List<Location> getSurrounding(int size) {
		List<Location> surround = Lists.newArrayList();
		for(int x = -size; x < size; x++) {
			for(int y = -size; y < size; y++) {
				surround.add(new Location(this.x + x, this.y + y, z));
			}
		}
		return surround;
	}

	public Location translate(int x, int y, int z) {
		return new Location(this.x + x, this.y + y, this.z + z);
	}

	public Location center(int size) {
		return translate((int) Math.ceil(size / 2.0), (int) Math.ceil(size / 2.0), 0);
	}

	public static Location of(int x, int y) {
		return new Location(x, y, 0);
	}
	
	public static Location of(Location loc) {
		return new Location(loc.getX(), loc.getY(), loc.getZ());
	}
	
	public static Location of(Entity entity) {
		return new Location(entity.getX(), entity.getY(), entity.getHeight());
	}
	
	public Location getRandomNear(int size, int radius) {
		return Misc.randomTypeOfList(getSurrounding(radius)
				.stream()
				.filter(baseLoc -> baseLoc.getSurrounding(size)
											.stream()
											.allMatch(loc -> !loc.getRegion().isBlocked(loc.getX(), loc.getY(), loc.getZ()) && !getRegion().solidObjectExists(loc.getX(), loc.getY(), loc.getZ())))
				.collect(Collectors.toList()));
	}

	public boolean equalsIgnoreHeight(Location loc) {
		return loc.getX() == x && loc.getY() == y;
	}
	
	/**
	 * Gets the local x coordinate relative to a specific region.
	 *
	 * @param location The region the coordinate will be relative to.
	 * @return The local x coordinate.
	 */
	public int getLocalX(Location location) {
		return this.x - 8 * location.getRegionX();
	}

	/**
	 * Gets the local y coordinate relative to a specific region.
	 *
	 * @param location The region the coordinate will be relative to.
	 * @return The local y coordinate.
	 */
	public int getLocalY(Location location) {
		return this.y - 8 * location.getRegionY();
	}
	
	/**
	 * Gets the region x coordinate.
	 *
	 * @return The region x coordinate.
	 */
	public int getRegionX() {
		return (this.x >> 3) - 6;
	}

	/**
	 * Gets the region y coordinate.
	 *
	 * @return The region y coordinate.
	 */
	public int getRegionY() {
		return (this.y >> 3) - 6;
	}

	/**
	 * Gets the local x coordinate relative to a specific region.
	 *
	 * @return The local x coordinate.
	 */
	public int getLocalX() {
		return this.x - 8 * getRegionX();
	}

	/**
	 * Gets the local y coordinate relative to a specific region.
	 *
	 * @return The local y coordinate.
	 */
	public int getLocalY() {
		return this.y - 8 * getRegionY();
	}
	
    public int getXInChunk() {
    	return x & 0x7;
    }

    public int getYInChunk() {
    	return y & 0x7;
    }

	public Region getRegion() {
		return Region.getRegion(x, y);
	}
	
	public static Location centerOf(Location loc, int size) {
		return Location.of(loc.getCoordFaceX(size), loc.getCoordFaceY(size), loc.getZ());
	}
	
	public Location centerOf(int size) {
		return Location.of(getCoordFaceX(size), getCoordFaceY(size), getZ());
	}
	
	public int getCoordFaceX(int sizeX) {
		return getCoordFaceX(sizeX, -1, -1);
	}
	
	public int getCoordFaceX(int sizeX, int sizeY, int rotation) {
		return x + ((rotation == 1 || rotation == 3 ? sizeY : sizeX) - 1) / 2;
	}
	
	public int getCoordFaceY(int sizeY) {
		return getCoordFaceY(-1, sizeY, -1);
	}

	public int getCoordFaceY(int sizeX, int sizeY, int rotation) {
		return y + ((rotation == 1 || rotation == 3 ? sizeX : sizeY) - 1) / 2;
	}

	public static final int getCoordFaceX(int x, int sizeX, int sizeY, int rotation) {
		return x + ((rotation == 1 || rotation == 3 ? sizeY : sizeX) - 1) / 2;
	}

	public static final int getCoordFaceY(int y, int sizeX, int sizeY, int rotation) {
		return y + ((rotation == 1 || rotation == 3 ? sizeX : sizeY) - 1) / 2;
	}

	public Location getDifference(Location other) {
		return new Location(x - other.getX(), y - other.getY(), z - other.getZ());
	}
	
	public Location getLocationForDirection(int direction) {
		switch (direction) {
			case RoomDirection.NORTH:
				return translate(0, 1, 0);
			case RoomDirection.SOUTH:
				return translate(0, -1, 0);
			case RoomDirection.EAST:
				return translate(1, 0, 0);
			case RoomDirection.WEST:
				return translate(-1, 0, 0);
			default:
				return copy();	
		}
	}

	public int getXInRegion() {
		return x % 64;
	}
	
	public int getYInRegion() {
		return y % 64;
	}

}
