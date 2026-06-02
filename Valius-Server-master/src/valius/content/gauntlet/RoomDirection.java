package valius.content.gauntlet;

public class RoomDirection {
	public static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;
	
	public static final int[] ALL = {NORTH, EAST, SOUTH, WEST};

	/**
	 * @param directionOf
	 * @return
	 */
	public static int getInverse(int direction) {
		switch(direction) {
		case NORTH:
			return SOUTH;
		case SOUTH:
			return NORTH;
		case EAST:
			return WEST;
		case WEST:
			return EAST;
		}
		return direction;
	}
}
